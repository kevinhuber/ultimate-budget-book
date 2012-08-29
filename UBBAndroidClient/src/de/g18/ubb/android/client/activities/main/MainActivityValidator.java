package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.validation.AbstractValidator;
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
            return createMustNotBeEmptyMessage(MainActivityResource.FIELD_EMAIL);
        }
        if (StringUtil.isEmpty(getModel().getPassword())) {
            return createMustNotBeEmptyMessage(MainActivityResource.FIELD_PASSWORD);
        }
        return createEmptyMessage();
    }
}
