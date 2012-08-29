package de.g18.ubb.common.domain;

import org.junit.Test;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BookingTest extends AbstractEntityTest<Booking> {

    @Override
    protected Booking createEntity() {
        return new Booking();
    }

    @Test
    public void testEquals() {
        // TODO implementieren!
    }
}
