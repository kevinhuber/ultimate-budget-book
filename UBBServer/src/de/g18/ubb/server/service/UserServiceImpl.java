package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.remote.UserServiceRemote;
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
}
