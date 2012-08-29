package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.communication.MockServiceProvider;
import de.g18.ubb.android.client.validation.AbstractValidatorTestCase;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivityValidatorTest  extends AbstractValidatorTestCase<MainActivityModel, MainActivityValidator> {

    @Override
    public void setUpTestCase() throws Exception {
        MockServiceProvider.register();
    }

    @Override
    protected MainActivityModel createValidModel() {
        MainActivityModel model = new MainActivityModel();
        model.setEMail("test@mail.com");
        model.setPassword("validPassword");
        return model;
    }

    @Override
    protected MainActivityValidator createValidator() {
        return new MainActivityValidator(getModel());
    }

    public void testEMailMandatory() {
        getModel().setEMail(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(MainActivityResource.FIELD_EMAIL);
    }

    public void testPasswordMandatory() {
        getModel().setPassword(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(MainActivityResource.FIELD_PASSWORD);
    }
}
