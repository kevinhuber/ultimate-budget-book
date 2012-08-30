package de.g18.ubb.common.service.remote;

import javax.ejb.Remote;
import javax.ws.rs.Path;

import de.g18.ubb.common.service.BookingService;

/**
 * Remote-Interface für den {@link BookingService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Remote
@Path("/" + BookingServiceRemote.RESTFUL_SERVICE_NAME)
public interface BookingServiceRemote extends BookingService {
}
