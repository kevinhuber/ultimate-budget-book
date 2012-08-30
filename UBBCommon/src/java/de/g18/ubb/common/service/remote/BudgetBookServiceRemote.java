package de.g18.ubb.common.service.remote;

import javax.ejb.Remote;
import javax.ws.rs.Path;

import de.g18.ubb.common.service.BudgetBookService;

/**
 * Remote-Interface für den {@link BudgetBookService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Remote
@Path("/" + BudgetBookServiceRemote.RESTFUL_SERVICE_NAME)
public interface BudgetBookServiceRemote extends BudgetBookService {
}
