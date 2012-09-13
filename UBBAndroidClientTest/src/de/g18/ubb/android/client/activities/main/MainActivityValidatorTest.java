package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.activities.login.LoginActivityModel;
import de.g18.ubb.android.client.activities.login.LoginActivityValidator;
import de.g18.ubb.android.client.activities.login.LoginResource;
import de.g18.ubb.android.client.communication.MockServiceProvider;
import de.g18.ubb.android.client.validation.AbstractValidatorTestCase;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivityValidatorTest  extends AbstractValidatorTestCase<LoginActivityModel, LoginActivityValidator> {

    @Override
    public void setUpTestCase() throws Exception {
        MockServiceProvider.register();
    }

    @Override
    protected LoginActivityModel createValidModel() {
        LoginActivityModel model = new LoginActivityModel();
        model.setEMail("test@mail.com");
        model.setPassword("validPassword");
        return model;
    }

    @Override
    protected LoginActivityValidator createValidator() {
        return new LoginActivityValidator(getModel());
    }

    public void testEMailMandatory() {
        getModel().setEMail(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(LoginResource.FIELD_EMAIL);
    }

    public void testEMailFormat() {
        String email = "invalidemailaddressde";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.de";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemail@address.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.de";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "validemail@address.de";
        getModel().setEMail(email);
        assertValidationSuccessfull();
    }

    public void testPasswordMandatory() {
        getModel().setPassword(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(LoginResource.FIELD_PASSWORD);
    }
}
