package de.g18.ubb.common.service;

import de.g18.ubb.common.domain.Category;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface CategoryService extends StoreService<Category>,
                                         LoadService<Category>,
                                         RemoveService<Category> {

    public static final String RESTFUL_SERVICE_NAME = "CategoryService";
}
