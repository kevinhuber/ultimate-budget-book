package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface CategoryService {

    public static final String RESTFUL_SERVICE_NAME = "CategoryService";

    @DELETE
    void remove(Category aCategory);

    @DELETE
    void removeById(Long aId) throws NotFoundExcpetion;

    @PUT
    Category saveAndLoad(Category aEntity);
    
    @GET
    List<Category> getAll(BudgetBook book);
}
