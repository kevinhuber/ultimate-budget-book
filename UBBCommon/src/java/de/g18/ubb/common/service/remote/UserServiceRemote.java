package de.g18.ubb.common.service.remote;

import javax.ejb.Remote;
import javax.ws.rs.Path;

import de.g18.ubb.common.service.UserService;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Remote
@Path("/" + UserServiceRemote.RESTFUL_SERVICE_NAME)
public interface UserServiceRemote extends UserService {
}
