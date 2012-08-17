package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserService /*extends StoreService<User>,
                                     LoadService<User>*/ {

    public static final String RESTFUL_SERVICE_NAME = "UserService";

    @GET
    List<User> getAll();

    @PUT
    User saveAndLoad(User aEntity);

    @POST
    User loadById(Long aId) throws NotFoundExcpetion;
}
