package de.g18.ubb.android.client.activities.register;


import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class RegisterValidator extends AbstractValidator<RegisterModel, PresentationModel<RegisterModel>> {

    public RegisterValidator(PresentationModel<RegisterModel> aModel) {
        super(aModel);
    }

    public String computeValidationResult() {
        String username = (String) getModel().getValue(RegisterModel.PROPERTY_USERNAME);
        if (StringUtil.isEmpty(username)) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_USERNAME);
        }

        String eMail = (String) getModel().getValue(RegisterModel.PROPERTY_EMAIL);
        if (StringUtil.isEmpty(eMail)) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_EMAIL);
        }
        if (!ValidationUtil.isValidEMail(eMail)) {
            return ValidationUtil.createInvalidEMailFormatMessage(eMail);
        }

        String password = (String) getModel().getValue(RegisterModel.PROPERTY_PASSWORD);
        if (StringUtil.isEmpty(password)) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_PASSWORD);
        }

        String passwordCheck = (String) getModel().getValue(RegisterModel.PROPERTY_PASSWORD_CHECK);
        if (StringUtil.isEmpty(passwordCheck)) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_PASSWORD_CHECK);
        }
        if (!ObjectUtil.equals(password, passwordCheck)) {
            return RegisterResource.VALIDATION_PASSWORDS_MUST_BE_EQUAL.formatted();
        }
        if (ServiceRepository.getUserService().isEMailInUse(eMail)) {
            return RegisterResource.VALIDATION_EMAIL_ALREADY_USED.formatted();
        }
        return ValidationUtil.createEmptyMessage();
    }
}
