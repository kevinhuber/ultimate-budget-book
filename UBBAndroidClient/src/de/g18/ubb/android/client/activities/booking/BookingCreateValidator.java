package de.g18.ubb.android.client.activities.booking;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.android.client.validation.Validator;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.util.StringUtil;

/**
 * {@link Validator} für die {@link CreateBookingActivity}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BookingCreateValidator extends AbstractValidator<Booking> {

	public BookingCreateValidator(Booking aModel) {
		super(aModel);
	}

	@Override
	protected String computeValidationResult() {
		if (StringUtil.isEmpty(getModel().getBookingName())) {
			return ValidationUtil.createMustNotBeEmptyMessage(BookingResource.FIELD_NAME);
		}
		if (getModel().getAmount() <= 0.0F) {
            return BookingResource.VALIDATION_AMMOUNT_MUST_BE_POSITIV.formatted();
		}
		return StringUtil.EMPTY;
	}
}
