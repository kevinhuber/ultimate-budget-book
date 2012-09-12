package de.g18.ubb.android.client.activities.register;

import android.os.Bundle;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.shared.binding.BindingHelper;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class RegisterActivity extends AbstractValidationFormularActivity<RegisterModel,
                                                                         PresentationModel<RegisterModel>,
                                                                         RegisterValidator> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected PresentationModel<RegisterModel> createModel() {
        return new PresentationModel<RegisterModel>(new RegisterModel());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindComponents();
    }

    private void bindComponents() {
        BindingHelper helper = new BindingHelper(this);
        helper.bindEditText(getModel().getModel(RegisterModel.PROPERTY_USERNAME), R.RegisterLayout.name);
        helper.bindEditText(getModel().getModel(RegisterModel.PROPERTY_EMAIL), R.RegisterLayout.email);
        helper.bindEditText(getModel().getModel(RegisterModel.PROPERTY_PASSWORD), R.RegisterLayout.password);
        helper.bindEditText(getModel().getModel(RegisterModel.PROPERTY_PASSWORD_CHECK), R.RegisterLayout.passwordCheck);
    }

    @Override
    protected int getSubmitButtonId() {
        return R.RegisterLayout.register;
    }

    @Override
    protected String submit() {
        boolean registrationSuccessfull = ServiceRepository.getUserService().register((String) getModel().getValue(RegisterModel.PROPERTY_EMAIL),
                                                                                      (String) getModel().getValue(RegisterModel.PROPERTY_USERNAME),
                                                                                      (String) getModel().getValue(RegisterModel.PROPERTY_PASSWORD));
        if (!registrationSuccessfull) {
            return RegisterResource.MESSAGE_REGISTRATION_FAILED.formatted();
        }
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        showValidationMessages();
        if (!isSubmitSuccessfull()) {
            return;
        }
        WebServiceProvider.authentificate((String) getModel().getValue(RegisterModel.PROPERTY_EMAIL),
                                          (String) getModel().getValue(RegisterModel.PROPERTY_PASSWORD));
        getPreferences().saveLoginData((String) getModel().getValue(RegisterModel.PROPERTY_EMAIL),
                                       (String) getModel().getValue(RegisterModel.PROPERTY_PASSWORD));
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
