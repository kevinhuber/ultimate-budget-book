package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithEMailNotFound;

/**
 * Service-Interface, welches Methoden zum arbeiten mit einem {@link BudgetBook} ermölicht.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BudgetBookService {

    /**
     * Name unter dem der Service erreichbar ist.
     */
    public static final String RESTFUL_SERVICE_NAME = "BudgetBookService";

    public static final String METHOD_CREATE_NEW = "createNew";
    public static final String METHOD_GET_ALL_FOR_CURRENT_USER = "getAllForCurrentUser";
    public static final String METHOD_LOAD = "load";
    public static final String METHOD_SAVE_AND_LOAD = "saveAndLoad";

    /**
     * Erstellt ein neues {@link BudgetBook} anhand der übergebenen Parameter und gibt dieses zurück.
     *
     * @throws UserWithEMailNotFound, falls aUserNameList eine EMail-Adresse enthält, zu der es keinen {@link User} gibt.
     * @throws NotFoundExcpetion,
     */
    @POST
    @Path(METHOD_CREATE_NEW)
    BudgetBook createNew(@HeaderParam("bookname") String aName,
                         @HeaderParam("username") List<String> aUserEMails) throws UserWithEMailNotFound;

    /**
     * Gibt eine Liste mit {@link BudgetBook}s zurück, die dem aufrufenden Benutzer zugeordnet sind.
     */
    @GET
    @Path(METHOD_GET_ALL_FOR_CURRENT_USER)
    List<BudgetBook> getAllForCurrentUser();

    /**
     * Lädt ein {@link BudgetBook} anhand der übergebenen Id aus der Datenbank und gibt es zurück.
     */
    @POST
    @Path(METHOD_LOAD)
    BudgetBook load(Long aId) throws NotFoundExcpetion;

    /**
     * Persistiert die übergebene Entität in der Datenbank und gibt die Persistierte Entität wieder zurück.
     */
    @PUT
    @Path(METHOD_SAVE_AND_LOAD)
    BudgetBook saveAndLoad(BudgetBook aEntity);
}
