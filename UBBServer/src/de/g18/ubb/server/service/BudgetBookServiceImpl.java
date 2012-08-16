package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.remote.BudgetBookServiceRemote;
import de.g18.ubb.server.service.local.BudgetBookServiceLocal;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local(BudgetBookServiceLocal.class)
@Remote(BudgetBookServiceRemote.class)
@Stateless
public class BudgetBookServiceImpl extends AbstractPersistanceBean<BudgetBook> implements BudgetBookServiceLocal,
                                                                                          BudgetBookServiceRemote {

    @Override
    protected Class<BudgetBook> getEntityClass() {
        return BudgetBook.class;
    }
}
