package de.g18.ubb.common.service.remote;

import javax.ejb.Remote;
import javax.ws.rs.Path;

import de.g18.ubb.common.service.CategoryService;

/**
 * Remote-Interface für den {@link CategoryService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Remote
@Path("/" + CategoryServiceRemote.RESTFUL_SERVICE_NAME)
public interface CategoryServiceRemote extends CategoryService {
}
