package de.g18.ubb.common.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.Identifiable;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface StoreService<_Entity extends Identifiable> {

    @POST
    _Entity saveAndLoad(_Entity aEntity);
}
