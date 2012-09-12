package de.g18.ubb.android.client.activities.booking;

import de.g18.ubb.android.client.activities.register.RegisterResource;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
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
		if (StringUtil.isEmpty(getModel().getBookingName())) {
			return ValidationUtil
					.createMustNotBeEmptyMessage(BookingResource.FIELD_NAME);
		}
		if (getModel().getAmount() == 0.0F) {
			return ValidationUtil
					.createMustNotBeEmptyMessage(BookingResource.FIELD_BETRAG);
		}
		if (getModel().getType().name() == "REVENUE") {
			if (getModel().getAmount() < 0.0F) {
				return BookingResource.MESSAGE_CREATE_AMOUNT_REVENUE_NEGATIVE
						.formatted();
			}
		}
		if (getModel().getType().name() == "SPENDING") {
			if (getModel().getAmount() > 0.0F) {
				return BookingResource.MESSAGE_CREATE_AMOUNT_SPENDING_POSITIVE
						.formatted();
			}
		}
		return ValidationUtil.createEmptyMessage();
	}
}
