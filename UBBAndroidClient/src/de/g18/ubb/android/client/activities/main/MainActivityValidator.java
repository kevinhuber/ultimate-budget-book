package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivityValidator extends AbstractValidator<MainActivityModel, PresentationModel<MainActivityModel>> {

    public MainActivityValidator(PresentationModel<MainActivityModel> aModel) {
        super(aModel);
    }

    public String computeValidationResult() {
        String eMail = (String) getModel().getValue(MainActivityModel.PROPERTY_EMAIL);
        if (StringUtil.isEmpty(eMail)) {
            return ValidationUtil.createMustNotBeEmptyMessage(MainActivityResource.FIELD_EMAIL);
        }
        if (!ValidationUtil.isValidEMail(eMail)) {
            return ValidationUtil.createInvalidEMailFormatMessage(eMail);
        }

        String password = (String) getModel().getValue(MainActivityModel.PROPERTY_PASSWORD);
        if (StringUtil.isEmpty(password)) {
            return ValidationUtil.createMustNotBeEmptyMessage(MainActivityResource.FIELD_PASSWORD);
        }
        return ValidationUtil.createEmptyMessage();
    }
}
