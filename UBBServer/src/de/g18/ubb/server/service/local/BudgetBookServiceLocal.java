package de.g18.ubb.server.service.local;

import javax.ejb.Local;
import javax.ws.rs.PUT;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.BudgetBookService;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local
public interface BudgetBookServiceLocal extends BudgetBookService {

    @PUT
    BudgetBook saveAndLoad(BudgetBook aEntity);
}
