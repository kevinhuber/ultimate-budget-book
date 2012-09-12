package de.g18.ubb.android.client.activities.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.activities.register.RegisterActivity;
import de.g18.ubb.android.client.binding.BindingHelper;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MainActivity extends AbstractValidationFormularActivity<MainActivityModel,
                                                                           PresentationModel<MainActivityModel>,
                                                                           MainActivityValidator> {

    static {
        WebServiceProvider.register();
    }


    private Button registerButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected PresentationModel<MainActivityModel> createModel() {
        return new PresentationModel<MainActivityModel>(new MainActivityModel());
    }

    @Override
    protected MainActivityValidator createValidator() {
        return new MainActivityValidator(getModel());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getModel().setValue(MainActivityModel.PROPERTY_EMAIL, getPreferences().getUsername());
        getModel().setValue(MainActivityModel.PROPERTY_PASSWORD, getPreferences().getPassword());
        getModel().setValue(MainActivityModel.PROPERTY_STAY_LOGGED_IN, true);
        getModel().setValue(MainActivityModel.PROPERTY_SERVER_ADDRESS, getPreferences().getServerAddress());
        WebServiceProvider.setServerAddress((String) getModel().getValue(MainActivityModel.PROPERTY_SERVER_ADDRESS));

        initComponents();
        bindComponents();
        initEventHandling();
    }

    private void initComponents() {
        registerButton = (Button) findViewById(R.MainLayout.register);
    }

    private void bindComponents() {
        BindingHelper helper = new BindingHelper(this);
        helper.bindEditText(getModel().getModel(MainActivityModel.PROPERTY_PASSWORD), R.MainLayout.password);
        helper.bindEditText(getModel().getModel(MainActivityModel.PROPERTY_EMAIL), R.MainLayout.email);
        helper.bindCheckBox(getModel().getModel(MainActivityModel.PROPERTY_STAY_LOGGED_IN), R.MainLayout.stayLoggedIn);
        helper.bindEditText(getModel().getModel(MainActivityModel.PROPERTY_SERVER_ADDRESS), R.MainLayout.serverAddress);
    }

    private void initEventHandling() {
        registerButton.setOnClickListener(new RegisternButtonListener());
    }

    @Override
    protected int getSubmitButtonId() {
        return R.MainLayout.login;
    }

    @Override
    protected void preSubmit() {
        String serverAddress = (String) getModel().getValue(MainActivityModel.PROPERTY_SERVER_ADDRESS);
        getPreferences().saveServerAddress(serverAddress);
        WebServiceProvider.setServerAddress(serverAddress);
    }

    @Override
    protected String submit() {
        boolean loginSuccessfull = WebServiceProvider.authentificate((String) getModel().getValue(MainActivityModel.PROPERTY_EMAIL),
                                                                     (String) getModel().getValue(MainActivityModel.PROPERTY_PASSWORD));
        if (!loginSuccessfull) {
            return MainActivityResource.MESSAGE_LOGIN_FAILED.formatted();
        }
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        showValidationMessages();
        if (!isSubmitSuccessfull()) {
            return;
        }

        if ((Boolean) getModel().getValue(MainActivityModel.PROPERTY_STAY_LOGGED_IN)) {
            getPreferences().saveLoginData((String) getModel().getValue(MainActivityModel.PROPERTY_EMAIL),
                                           (String) getModel().getValue(MainActivityModel.PROPERTY_PASSWORD));
        }
        switchActivity(BudgetBookOverviewActivity.class);
    }

    @Override
    protected String getSubmitWaitMessage() {
        return MainActivityResource.MESSAGE_LOGIN.formatted();
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class RegisternButtonListener implements OnClickListener {

        public void onClick(View aView) {
            switchActivity(RegisterActivity.class);
        }
    }
}
