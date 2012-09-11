package de.g18.ubb.android.client.activities.booking;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BookingCreateValidator extends AbstractValidator<Booking> {

    /**
     * @param aModel
     */
    public BookingCreateValidator(Booking aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        return StringUtil.EMPTY;
    }
}
