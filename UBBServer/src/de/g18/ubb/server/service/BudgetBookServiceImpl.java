package de.g18.ubb.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.hibernate.Query;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.UserExtract;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithEMailNotFound;
import de.g18.ubb.common.service.remote.BudgetBookServiceRemote;
import de.g18.ubb.common.util.StringUtil;
import de.g18.ubb.server.service.local.BudgetBookServiceLocal;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
 * Implementierung des {@link BudgetBookService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local(BudgetBookServiceLocal.class)
@Remote(BudgetBookServiceRemote.class)
@Stateless
public class BudgetBookServiceImpl extends AbstractPersistanceBean<BudgetBook> implements BudgetBookServiceLocal,
                                                                                          BudgetBookServiceRemote {

	@EJB
	private UserServiceLocal userService;


	@Override
	protected Class<BudgetBook> getEntityClass() {
		return BudgetBook.class;
	}

	@Override
	public BudgetBook createNew(String aName, List<String> aUserEMails) throws UserWithEMailNotFound {
		if (StringUtil.isEmpty(aName) || aUserEMails == null) {
			throw new IllegalArgumentException(
					"Benutzername und Haushaltsbuchname dürfen nicht leer sein");
		}

		BudgetBook b = new BudgetBook();
		List<UserExtract> assignedUsers = new ArrayList<UserExtract>();

		for (String userEmail : aUserEMails) {
			if (userService.isEMailInUse(userEmail)) {
				try {
					assignedUsers.add(userService.loadExtractByEMail(userEmail));
				} catch (NotFoundExcpetion e) {
					throw new UserWithEMailNotFound(userEmail, e);
				}
			}
		}

		UserExtract currentUser = new UserExtract(getCurrentUser());
		if (!assignedUsers.contains(currentUser)) {
			assignedUsers.add(currentUser);
		}
		b.setAssignedUser(assignedUsers);
		b.setName(aName);
		return saveAndLoad(b);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BudgetBook> getAllForCurrentUser() {
		Query q = getHibernateSession().createQuery(
				"SELECT b FROM " + getEntityClass().getSimpleName() + " b "
						+ "  LEFT JOIN b." + BudgetBook.PROPERTY_ASSIGNED_USER
						+ " assignedUsers "
						+ " WHERE :currentUser IN (assignedUsers)").setEntity(
				"currentUser", getCurrentUser());
		return q.list();
	}
}
