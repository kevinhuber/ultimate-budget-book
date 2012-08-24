package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithGivenEmailNotFound;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BudgetBookService {

    public static final String RESTFUL_SERVICE_NAME = "BudgetBookService";

    @POST
    BudgetBook createNew(@HeaderParam("bookname") String aName, @HeaderParam("username") List<String> aUserNameList) throws UserWithGivenEmailNotFound, NotFoundExcpetion;

    @GET
    List<BudgetBook> getAll(); // TODO (huber): ENTFERNEN!!!!

    @POST
    BudgetBook loadById(Long aId) throws NotFoundExcpetion;
}
