package de.g18.ubb.common.service;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface TestService {

    @GET
    @Produces("text/plain")
    String sayHello();
}
