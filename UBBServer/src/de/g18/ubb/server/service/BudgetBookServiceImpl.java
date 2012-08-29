package de.g18.ubb.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithGivenEmailNotFound;
import de.g18.ubb.common.service.remote.BudgetBookServiceRemote;
import de.g18.ubb.common.util.StringUtil;
import de.g18.ubb.server.service.local.BudgetBookServiceLocal;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
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
    public BudgetBook createNew(String aName, List<String> aUserNameList) throws UserWithGivenEmailNotFound {
    	if (StringUtil.isEmpty(aName) || aUserNameList == null) {
			throw new IllegalArgumentException("Benutzername und Haushaltsbuchname dürfen nicht leer sein");
		}

        BudgetBook b = new BudgetBook();
        List<User> assignedUsers = new ArrayList<User>();

    	for (String userEmail : aUserNameList) {
    		if (userService.existsUserWithEMail(userEmail)) {
				try {
					assignedUsers.add(userService.loadByEMail(userEmail));
				} catch (NotFoundExcpetion e) {
					throw new UserWithGivenEmailNotFound(userEmail, e);
				}
    		}
    	}

    	if (!assignedUsers.contains(getCurrentUser())) {
    		assignedUsers.add(getCurrentUser());
		}
        b.setAssignedUser(assignedUsers);
        b.setName(aName);
        return saveAndLoad(b);
    }
}
