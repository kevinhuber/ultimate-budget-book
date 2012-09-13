package de.g18.ubb.android.client.activities.budgetbook;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class BudgetBookCreateNewValidator extends AbstractValidator<BudgetBookCreateNewModel> {

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
					.createMustNotBeEmptyMessage(BudgetBookResource.FIELD_NAME);
		}
		if (!checkIfEmailIsValid()) {
		    return ValidationUtil.createInvalidEMailFormatMessage(getCurrentUser());
		}
		if (!checkIfUserExists()) {
			return BudgetBookResource.MESSAGE_ADD_NOT_EXISTING_USER.formatted(getCurrentUser());
		}
		return ValidationUtil.createEmptyMessage();
	}

	/**
	 * Überprüft ob eine Email valide ist
	 *
	 * @return Gibt true zurück wenn die Email-Addresse valide ist
	 */
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

	/**
	 * Überprüft ob der Benutzer bereits exestiert
	 *
	 * @return Gibt true zurück wenn es schon einen benutzer mit dieser Email gibt
	 */
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
