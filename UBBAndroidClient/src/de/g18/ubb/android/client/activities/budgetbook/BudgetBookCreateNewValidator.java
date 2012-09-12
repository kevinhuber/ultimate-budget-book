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
		if (!checkIfEmailIsValid()) {
		    return ValidationUtil.createInvalidEMailFormatMessage(getCurrentUser());
		}
		if (!checkIfUserExists()) {
			return BudgetBookResource.MESSAGE_ADD_NOT_EXISTING_USER.formatted(getCurrentUser());
		}
		return ValidationUtil.createEmptyMessage();
	}

	private boolean checkIfEmailIsValid() {
		for (String user : getModel().getAssignedUsers()) {
			boolean emailIsValid = ValidationUtil.isValidEMail(user);
			if (!emailIsValid) {
				setCurrentUser(user);
				return false;
			}
		}
        return true;
	}

	private boolean checkIfUserExists() {
		for (String user : getModel().getAssignedUsers()) {
			boolean userExists = ServiceRepository.getUserService().isEMailInUse(user);
			if (!userExists) {
				setCurrentUser(user);
                return false;
			}
		}
		return true;
	}
}
