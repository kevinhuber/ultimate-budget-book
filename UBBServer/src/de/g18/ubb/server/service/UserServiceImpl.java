package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.POST;

import org.hibernate.Query;
import org.jboss.resteasy.spi.NotFoundException;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.remote.UserServiceRemote;
import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;
import de.g18.ubb.server.service.local.UserServiceLocal;

/**
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
	public User loadBySessionId(String aJSessionId) throws NotFoundExcpetion {
    	if (StringUtil.isEmpty(aJSessionId)) {
			throw new NotFoundException(aJSessionId);
		}
		
		 Query q = getHibernateSession()
		            .createQuery("SELECT u FROM " + getEntityClass().getSimpleName() + " u " 
		            			  + "WHERE u." + User.PROPERTY_SESSION + "= :session")
		            .setString("session", aJSessionId);
		 return uniqueResult(q);
	}

    @Override
    public boolean login(String aEmail, String aPassword) {
    	try {
			User userResult = loadByEMail(aEmail);
			byte[] salt = userResult.getSalt();
			String typedInPassword = new String(HashUtil.toMD5(aPassword, salt));
			String userPassword = userResult.getPasswordHash();
			if (ObjectUtil.equals(typedInPassword, userPassword)) {
				userResult.setSession(getHttpRequest().getSession().getId());
				saveAndLoad(userResult);
				return true;
			} else {
				return false;
			}
		} catch (NotFoundExcpetion e) {
			return false;
		}
    }

	@Override
	public User loadByEMail(String aEmail) throws NotFoundExcpetion {
		if (StringUtil.isEmpty(aEmail)) {
			throw new NotFoundException(aEmail);
		}
		
		 Query q = getHibernateSession()
		            .createQuery("SELECT u FROM " + getEntityClass().getSimpleName() + " u " 
		            			  + "WHERE u." + User.PROPERTY_EMAIL + "= :email")
		            .setString("email", aEmail);
		 return uniqueResult(q);
	}
}