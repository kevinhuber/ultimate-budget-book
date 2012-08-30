package de.g18.ubb.server.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.hibernate.Query;

import de.g18.ubb.common.domain.BudgetBook;
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

    @SuppressWarnings("unchecked")
	@Override
    public List<Category> getAll(BudgetBook book) {
    	Query q = getHibernateSession()
            .createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e "
                       + "WHERE e.budgetBook = :book")
            .setEntity("book", book);
         return q.list();
    }
}
