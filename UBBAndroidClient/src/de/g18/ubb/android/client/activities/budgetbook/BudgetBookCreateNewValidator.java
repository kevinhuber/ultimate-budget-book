package de.g18.ubb.android.client.activities.budgetbook;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class BudgetBookCreateNewValidator extends
		AbstractValidator<BudgetBookCreateNewModel> {

	private String currentUser;

	private void setCurrentUser(String aUser) {
		currentUser = aUser;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public BudgetBookCreateNewValidator(BudgetBookCreateNewModel aModel) {
		super(aModel);
	}

	@Override
	protected String computeValidationResult() {
		if (StringUtil.isEmpty(getModel().getName())) {
			return ValidationUtil
					.createMustNotBeEmptyMessage(BudgetBookResource.PROPERTY_NAME);
		}
		if (!checkIfUserExists()) {
			return BudgetBookResource.MESSAGE_ADD_NOT_EXISTING_USER.formatted();
		}
		if (!checkIfEmailIsValid()) {
			return ValidationUtil
					.createInvalidEMailFormatMessage(getCurrentUser());
		}
		return ValidationUtil.createEmptyMessage();
	}

	private boolean checkIfEmailIsValid() {
		boolean emailIsValid = true;

		while (emailIsValid) {
			for (String user : getModel().getAssignedUsers()) {
				emailIsValid = ValidationUtil.isValidEMail(user);
				if (!emailIsValid) {
					setCurrentUser(user);
				}
			}
		}
		if (emailIsValid) {
			return true;
		}
		return false;
	}

	private boolean checkIfUserExists() {
		boolean userExists = true;
		while (userExists) {
			for (String user : getModel().getAssignedUsers()) {
				userExists = ServiceRepository.getUserService().isEMailInUse(
						user);
				if (!userExists) {
					setCurrentUser(user);
				}
			}
		}

		if (userExists) {
			return true;
		}
		return false;
	}
}
