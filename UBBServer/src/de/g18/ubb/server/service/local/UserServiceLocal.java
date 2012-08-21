package de.g18.ubb.server.service.local;

import javax.ejb.Local;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local
public interface UserServiceLocal extends UserService {

	User loadByEMail(String aEmail) throws NotFoundExcpetion;
}
