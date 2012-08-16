package de.g18.ubb.common.service.remote;

import javax.ejb.Remote;
import javax.ws.rs.Path;

import de.g18.ubb.common.service.TestService;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Remote
@Path("/TestService")
public interface TestServiceRemote extends TestService {
}
