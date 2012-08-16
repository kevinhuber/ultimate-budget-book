package de.g18.ubb.common.service;

import de.g18.ubb.common.domain.Booking;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface BookingService extends StoreService<Booking>,
                                        LoadService<Booking>,
                                        RemoveService<Booking> {

    public static final String RESTFUL_SERVICE_NAME = "BookingService";
}
