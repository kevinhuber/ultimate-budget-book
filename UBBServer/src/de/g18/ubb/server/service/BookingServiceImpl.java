package de.g18.ubb.server.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.POST;

import org.hibernate.Query;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.BookingService;
import de.g18.ubb.common.service.remote.BookingServiceRemote;
import de.g18.ubb.server.service.local.BookingServiceLocal;

/**
 * Implementierung des {@link BookingService}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local(BookingServiceLocal.class)
@Remote(BookingServiceRemote.class)
@Stateless
public class BookingServiceImpl extends AbstractPersistanceBean<Booking> implements BookingServiceLocal,
                                                                                    BookingServiceRemote {

    @Override
    protected Class<Booking> getEntityClass() {
        return Booking.class;
    }

}
