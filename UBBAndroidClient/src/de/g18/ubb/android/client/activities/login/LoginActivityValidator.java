package de.g18.ubb.android.client.activities.login;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.android.client.validation.Validator;
import de.g18.ubb.common.util.StringUtil;

/**
 * {@link Validator} für die {@link LoginActivity}.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class LoginActivityValidator extends AbstractValidator<LoginActivityModel> {

    public LoginActivityValidator(LoginActivityModel aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        if (StringUtil.isEmpty(getModel().getEMail())) {
            return ValidationUtil.createMustNotBeEmptyMessage(LoginResource.FIELD_EMAIL);
        }
        if (!ValidationUtil.isValidEMail(getModel().getEMail())) {
            return ValidationUtil.createInvalidEMailFormatMessage(getModel().getEMail());
        }
        if (StringUtil.isEmpty(getModel().getPassword())) {
            return ValidationUtil.createMustNotBeEmptyMessage(LoginResource.FIELD_PASSWORD);
        }
        return ValidationUtil.createEmptyMessage();
    }
}
