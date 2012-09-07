package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.shiro.SecurityUtils;
import org.hibernate.Query;
import org.jboss.resteasy.spi.NotFoundException;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.domain.UserExtract;
import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.remote.UserServiceRemote;
import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.common.util.StringUtil;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
 * Implementierung des {@link UserService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local(UserServiceLocal.class)
@Remote(UserServiceRemote.class)
@Stateless
public class UserServiceImpl extends AbstractPersistanceBean<User> implements UserServiceLocal,
                                                                              UserServiceRemote {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public boolean isEMailInUse(String aEMail) {
        try {
            loadExtractByEMail(aEMail);
        } catch (NotFoundExcpetion e) {
            return false;
        }
        return true;
    }

	@Override
	public User loadByEMail(String aEmail) throws NotFoundExcpetion {
		if (StringUtil.isEmpty(aEmail)) {
			throw new NotFoundException(aEmail);
		}

        Query q = getHibernateSession()
            .createQuery(
                "SELECT u FROM " + getEntityClass().getSimpleName() + " u "
              + " WHERE u." + User.PROPERTY_EMAIL + "= :email")
            .setString("email", aEmail);
        return (User) uniqueResult(q);
	}

    @Override
    public UserExtract loadExtractByEMail(String aEmail) throws NotFoundExcpetion {
        if (StringUtil.isEmpty(aEmail)) {
            throw new NotFoundException(aEmail);
        }

        Query q = getHibernateSession()
            .createQuery(
                "SELECT u FROM " + UserExtract.class.getSimpleName() + " u "
              + " WHERE u." + User.PROPERTY_EMAIL + "= :email")
            .setString("email", aEmail);
        return (UserExtract) uniqueResult(q);
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public boolean register(String aEMail, String aUsername, String aPassword) {
        if (isEMailInUse(aEMail)) {
            return false;
        }

        User user = new User();
        user.setEmail(aEMail);
        user.setName(aUsername);
        setPassword(user, aPassword);
        saveAndLoad(user);
        return true;
    }

    /**
     * Erstellt aus dem übergebenen Passwort einen Hash und speichert diesem im User.
     */
    private void setPassword(User aUser, String aPassword) {
        String newPasswordHash = HashUtil.toMD5(aPassword, aUser.getSalt());
        aUser.setPasswordHash(newPasswordHash);
    }
}
