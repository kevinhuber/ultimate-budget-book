package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.service.remote.BookingServiceRemote;
import de.g18.ubb.server.service.local.BookingServiceLocal;

/**
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
