package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookingService {

    public static final String RESTFUL_SERVICE_NAME = "BookingService";

    @POST
    @Path("loadBooking")
    Booking loadById(Long aId) throws NotFoundExcpetion;

    @DELETE
    @Path("removeBooking")
    void remove(Booking aCategory);

    @DELETE
    @Path("removeById")
    void removeById(Long aId) throws NotFoundExcpetion;

    @PUT
    Booking saveAndLoad(Booking aEntity);
}
