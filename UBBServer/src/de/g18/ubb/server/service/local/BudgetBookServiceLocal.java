package de.g18.ubb.server.service.local;

import javax.ejb.Local;

import de.g18.ubb.common.service.BudgetBookService;

/**
 * Local-Interface für den {@link BudgetBookService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local
public interface BudgetBookServiceLocal extends BudgetBookService {
}
