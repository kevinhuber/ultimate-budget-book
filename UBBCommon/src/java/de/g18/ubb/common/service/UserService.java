package de.g18.ubb.common.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.User;

/**
 * Service-Interface, welches Methoden zum arbeiten mit einem {@link User} ermölicht.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserService {

    /**
     * Name unter dem der Service erreichbar ist.
     */
    public static final String RESTFUL_SERVICE_NAME = "UserService";

    public static final String METHOD_IS_AUTHENTICATED = "isAuthenticated";
    public static final String METHOD_IS_EMAIL_IN_USE = "isEMailInUse";
    public static final String METHOD_REGISTER = "register";

    public static final String AUTHENTIFICATION_TEST_PATH = RESTFUL_SERVICE_NAME + "/" + METHOD_IS_AUTHENTICATED;


    /**
     * Prüft ob der aufrufende Client authentfiziert ist.
     *
     * @return True, falls der aufrufende Client authentifiziert ist.
     */
    @GET
    @Path(METHOD_IS_AUTHENTICATED)
    boolean isAuthenticated();

    /**
     * Prüft ob es für die übergebene EMail-Adresse bereits einen registrierten Benutzer gibt.
     *
     * @return True, falls es bereits einen Benutzer mit der übergebenen EMail-Adresse gibt.
     */
    @POST
    @Path(METHOD_IS_EMAIL_IN_USE)
    boolean isEMailInUse(String aEMail);

    /**
     * Registriert einen neuen Benutzer mit den übergebenen Parametern.
     *
     * @return True, falls die registration erfolgreich abgeschloßen wurde.
     */
    @POST
    @Path(METHOD_REGISTER)
    boolean register(@HeaderParam("email") String aEMail,
                     @HeaderParam("username") String aUsername,
                     @HeaderParam("password") String aPassword);
}
