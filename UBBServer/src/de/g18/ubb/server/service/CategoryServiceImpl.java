package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.CategoryService;
import de.g18.ubb.common.service.remote.CategoryServiceRemote;
import de.g18.ubb.server.service.local.CategoryServiceLocal;

/**
 * Implementierung des {@link CategoryService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local(CategoryServiceLocal.class)
@Remote(CategoryServiceRemote.class)
@Stateless
public class CategoryServiceImpl extends AbstractPersistanceBean<Category> implements CategoryServiceLocal,
                                                                                      CategoryServiceRemote {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}
