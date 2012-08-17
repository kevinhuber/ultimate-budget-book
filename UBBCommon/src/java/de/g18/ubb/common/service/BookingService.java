package de.g18.ubb.common.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface BookingService {

    public static final String RESTFUL_SERVICE_NAME = "BookingService";

    @POST
    Booking loadById(Long aId) throws NotFoundExcpetion;

    @DELETE
    void remove(Booking aCategory);

    @DELETE
    void removeById(Long aId) throws NotFoundExcpetion;

    @PUT
    Booking saveAndLoad(Booking aEntity);
}
