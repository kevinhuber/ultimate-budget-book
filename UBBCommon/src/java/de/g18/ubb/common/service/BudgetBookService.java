package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.BudgetBook;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BudgetBookService extends StoreService<BudgetBook>,
                                           LoadService<BudgetBook>,
                                           RemoveService<BudgetBook> {

    public static final String RESTFUL_SERVICE_NAME = "BudgetBookService";

    @GET
    List<BudgetBook> getAll(); // TODO (huber): ENTFERNEN!!!!
}
