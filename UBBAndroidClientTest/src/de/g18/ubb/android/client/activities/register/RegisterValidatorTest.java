package de.g18.ubb.android.client.activities.register;

import de.g18.ubb.android.client.communication.MockServiceProvider;
import de.g18.ubb.android.client.mock.service.MockUserServiceImpl;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidatorTestCase;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class RegisterValidatorTest extends AbstractValidatorTestCase<RegisterModel,
                                                                     PresentationModel<RegisterModel>,
                                                                     RegisterValidator> {

    private static final String VALID_PASSWORD = "validPassword";
    private static final String INVALID_PASSWORD = VALID_PASSWORD + "invalidPart";


    @Override
    public void setUpTestCase() throws Exception {
        MockServiceProvider.register();
    }

    @Override
    protected PresentationModel<RegisterModel> createValidModel() {
        RegisterModel bean = new RegisterModel();
        bean.setUsername("test");
        bean.setEMail("test@mail.com");
        bean.setPassword(VALID_PASSWORD);
        bean.setPasswordCheck(VALID_PASSWORD);
        return new PresentationModel<RegisterModel>(bean);
    }

    @Override
    protected RegisterValidator createValidator() {
        return new RegisterValidator(getModel());
    }

    public void testUsernameMandatory() {
        getModel().setValue(RegisterModel.PROPERTY_USERNAME, StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_USERNAME);
    }

    public void testEMailMandatory() {
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_EMAIL);
    }

    public void testEMailFormat() {
        String email = "invalidemailaddressde";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.de";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemail@address.";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.de";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@.";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "validemail@address.de";
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, email);
        assertValidationSuccessfull();
    }

    public void testPasswordMandatory() {
        getModel().setValue(RegisterModel.PROPERTY_PASSWORD, StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_PASSWORD);
    }

    public void testPasswordCheckMandatory() {
        getModel().setValue(RegisterModel.PROPERTY_PASSWORD_CHECK, StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_PASSWORD_CHECK);
    }

    public void testPasswordsMustBeEqual() {
        getModel().setValue(RegisterModel.PROPERTY_PASSWORD, INVALID_PASSWORD);
        assertValidationResult(RegisterResource.VALIDATION_PASSWORDS_MUST_BE_EQUAL);

        getModel().setValue(RegisterModel.PROPERTY_PASSWORD, VALID_PASSWORD);
        assertValidationSuccessfull();

        getModel().setValue(RegisterModel.PROPERTY_PASSWORD_CHECK, INVALID_PASSWORD);
        assertValidationResult(RegisterResource.VALIDATION_PASSWORDS_MUST_BE_EQUAL);
    }

    public void testEMailMustBeUnique() {
        getModel().setValue(RegisterModel.PROPERTY_EMAIL, MockUserServiceImpl.REGISTERED_USER_EMAIL);
        assertValidationResult(RegisterResource.VALIDATION_EMAIL_ALREADY_USED);
    }
}
