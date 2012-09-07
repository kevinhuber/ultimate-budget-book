package de.g18.ubb.server.service.local;

import javax.ejb.Local;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.domain.UserExtract;
import de.g18.ubb.common.service.UserService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * Local-Interface für den {@link UserService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local
public interface UserServiceLocal extends UserService {

	User loadByEMail(String aEmail) throws NotFoundExcpetion;

    UserExtract loadExtractByEMail(String aEmail) throws NotFoundExcpetion;
}
