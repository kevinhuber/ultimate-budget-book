package de.g18.ubb.common.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.Category;

/**
 * Service-Interface, welches Methoden zum arbeiten mit einer {@link Category} ermölicht.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CategoryService {

    /**
     * Name unter dem der Service erreichbar ist.
     */
    public static final String RESTFUL_SERVICE_NAME = "CategoryService";

    public static final String METHOD_SAVE_AND_LOAD = "saveAndLoad";
    public static final String METHOD_GET_ALL = "getAll";


    /**
     * Persistiert die übergebene Entität in der Datenbank und gibt die Persistierte Entität wieder zurück.
     *
     * TODO (huber): Bedenklich!
     */
    @PUT
    @Path(METHOD_SAVE_AND_LOAD)
    Category saveAndLoad(Category aEntity);
}
