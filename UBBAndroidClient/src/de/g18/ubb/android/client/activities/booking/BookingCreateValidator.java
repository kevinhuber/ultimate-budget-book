package de.g18.ubb.android.client.activities.booking;

import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BookingCreateValidator extends AbstractValidator<Booking, PresentationModel<Booking>> {

    public BookingCreateValidator(PresentationModel<Booking> aModel) {
        super(aModel);
    }

    public String computeValidationResult() {
        return StringUtil.EMPTY;
    }
}
