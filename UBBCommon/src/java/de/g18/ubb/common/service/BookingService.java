package de.g18.ubb.common.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * Service-Interface, welches Methoden zum arbeiten mit {@link Booking}s ermölicht.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookingService {

    /**
     * Name unter dem der Service erreichbar ist.
     */
    public static final String RESTFUL_SERVICE_NAME = "BookingService";

    public static final String METHOD_LOAD = "load";
    public static final String METHOD_REMOVE = "remove";
    public static final String METHOD_REMOVE_BY_ID = "removeById";
    public static final String METHOD_SAVE_AND_LOAD = "saveAndLoad";

    /**
     * Lädt ein {@link Booking} anhand der übergebenen Id aus der Datenbank und gibt es zurück.
     *
     * TODO (huber): Bedenklich!
     */
    @POST
    @Path(METHOD_LOAD)
    Booking load(Long aId) throws NotFoundExcpetion;

    /**
     * Löscht die übergebene {@link Booking} aus der Datenbank.
     *
     * TODO (huber): Bedenklich!
     */
    @POST
    @Path(METHOD_REMOVE)
    void remove(Booking aBooking);

    /**
     * Löscht eine {@link Booking} anhand der übergebenen id aus der Datenbank.
     *
     * TODO (huber): Bedenklich!
     */
    @POST
    @Path(METHOD_REMOVE_BY_ID)
    void removeById(Long aId) throws NotFoundExcpetion;

    /**
     * Persistiert die übergebene Entität in der Datenbank und gibt die Persistierte Entität wieder zurück.
     *
     * TODO (huber): Bedenklich!
     */
    @PUT
    @Path(METHOD_SAVE_AND_LOAD)
    Booking saveAndLoad(Booking aEntity);
}
