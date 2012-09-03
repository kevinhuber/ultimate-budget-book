package de.g18.ubb.android.client.activities.register;

import android.os.Bundle;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class RegisterActivity extends AbstractValidationFormularActivity<RegisterModel, RegisterValidator> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterModel createModel() {
        return new RegisterModel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindComponents();
    }

    private void bindComponents() {
        bind(RegisterModel.PROPERTY_USERNAME, R.RegisterLayout.name);
        bind(RegisterModel.PROPERTY_EMAIL, R.RegisterLayout.email);
        bind(RegisterModel.PROPERTY_PASSWORD, R.RegisterLayout.password);
        bind(RegisterModel.PROPERTY_PASSWORD_CHECK, R.RegisterLayout.passwordCheck);
    }

    @Override
    protected int getSubmitButtonId() {
        return R.RegisterLayout.register;
    }

    @Override
    protected String submit() {
        boolean registrationSuccessfull = ServiceRepository.getUserService().register(getModel().getEMail(),
                                                                                      getModel().getUsername(),
                                                                                      getModel().getPassword());
        if (!registrationSuccessfull) {
            return RegisterResource.MESSAGE_REGISTRATION_FAILED.formatted();
        }
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        super.postSubmit();

        if (!isSubmitSuccessfull()) {
            return;
        }
        WebServiceProvider.authentificate(getModel().getEMail(), getModel().getPassword());
        getPreferences().saveLoginData(getModel().getEMail(), getModel().getPassword());
        switchActivity(BudgetBookOverviewActivity.class);
    }

    @Override
    protected String getSubmitWaitMessage() {
        return RegisterResource.MESSAGE_REGISTRATION_IN_PROGRESS.formatted();
    }

    @Override
    protected RegisterValidator createValidator() {
        return new RegisterValidator(getModel());
    }
}
