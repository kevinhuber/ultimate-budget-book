package de.g18.ubb.common.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.Identifiable;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RemoveService<_Entity extends Identifiable> {

    @POST
    void removeById(Long aId) throws NotFoundExcpetion;

    @POST
    void remove(_Entity aEntity);
}
