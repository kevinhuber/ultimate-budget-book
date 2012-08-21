package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.shiro.SecurityUtils;
import org.hibernate.Query;
import org.jboss.resteasy.spi.NotFoundException;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.remote.UserServiceRemote;
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

    @Override
    public boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }
}