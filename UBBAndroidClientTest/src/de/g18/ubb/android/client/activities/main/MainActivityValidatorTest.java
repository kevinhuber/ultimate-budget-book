package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.communication.MockServiceProvider;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidatorTestCase;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivityValidatorTest  extends AbstractValidatorTestCase<MainActivityModel,
                                                                          PresentationModel<MainActivityModel>,
                                                                          MainActivityValidator> {

    @Override
    public void setUpTestCase() throws Exception {
        MockServiceProvider.register();
    }

    @Override
    protected PresentationModel<MainActivityModel> createValidModel() {
        MainActivityModel bean = new MainActivityModel();
        bean.setEMail("test@mail.com");
        bean.setPassword("validPassword");
        return new PresentationModel<MainActivityModel>(bean);
    }

    @Override
    protected MainActivityValidator createValidator() {
        return new MainActivityValidator(getModel());
    }

    public void testEMailMandatory() {
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(MainActivityResource.FIELD_EMAIL);
    }

    public void testEMailFormat() {
        String email = "invalidemailaddressde";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.de";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemail@address.";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.de";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@.";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "validemail@address.de";
        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, email);
        assertValidationSuccessfull();
    }

    public void testPasswordMandatory() {
        getModel().setValue(MainActivityModel.PROPERTY_PASSWORD, StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(MainActivityResource.FIELD_PASSWORD);
    }
}
