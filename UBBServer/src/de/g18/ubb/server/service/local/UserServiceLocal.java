package de.g18.ubb.server.service.local;

import javax.ejb.Local;
import javax.ws.rs.PUT;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithGivenEmailNotFound;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local
public interface UserServiceLocal extends UserService {

	User loadByEMail(String aEmail) throws NotFoundExcpetion;

    @PUT
    User saveAndLoad(User aEntity);
}
