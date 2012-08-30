package de.g18.ubb.server.service.local;

import javax.ejb.Local;

import de.g18.ubb.common.service.CategoryService;

/**
 * Local-Interface für den {@link CategoryService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local
public interface CategoryServiceLocal extends CategoryService {
}
