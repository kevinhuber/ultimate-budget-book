package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivityValidator extends AbstractValidator<MainActivityModel> {

    public MainActivityValidator(MainActivityModel aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        if (StringUtil.isEmpty(getModel().getEMail())) {
            return ValidationUtil.createMustNotBeEmptyMessage(MainActivityResource.FIELD_EMAIL);
        }
        if (!ValidationUtil.isValidEMail(getModel().getEMail())) {
            return ValidationUtil.createInvalidEMailFormatMessage(getModel().getEMail());
        }
        if (StringUtil.isEmpty(getModel().getPassword())) {
            return ValidationUtil.createMustNotBeEmptyMessage(MainActivityResource.FIELD_PASSWORD);
        }
        return ValidationUtil.createEmptyMessage();
    }
}
