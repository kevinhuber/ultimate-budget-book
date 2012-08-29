package de.g18.ubb.android.client.activities.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.android.client.activities.register.RegisterActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.UserLogin;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MainActivity extends AbstractValidationFormularActivity<UserLogin, UserLoginValidator> {

    static {
        WebServiceProvider.register();
        WebServiceProvider.setServerAddress(UBBConstants.EMULATOR_SERVER_ADDRESS);
    }


    private CheckBox stayLoggedInCheckBox;
    private Button registerButton;

    private EditText serverAddress;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected UserLogin createModel() {
        return new UserLogin();
    }

    @Override
    protected UserLoginValidator createValidator() {
        return new UserLoginValidator(getModel());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getModel().setEMail(getPreferences().getUsername());
        getModel().setPassword(getPreferences().getPassword());

        initComponents();
        bindComponents();
        initEventHandling();

        addDebugComponents();
    }

    private void initComponents() {
        stayLoggedInCheckBox = (CheckBox) findViewById(R.MainLayout.stayLoggedIn);
        registerButton = (Button) findViewById(R.MainLayout.register);
    }

    private void bindComponents() {
        bind(UserLogin.PROPERTY_PASSWORD, R.MainLayout.password);
        bind(UserLogin.PROPERTY_EMAIL, R.MainLayout.email);
    }

    private void initEventHandling() {
        registerButton.setOnClickListener(new RegisternButtonListener());
    }

    private void addDebugComponents() {
        String address = getPreferences().getServerAddress();
        WebServiceProvider.setServerAddress(address);

        serverAddress = new EditText(this);
        serverAddress.setText(address);
        serverAddress.setLayoutParams(
                new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                 android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout buttonContainer = (LinearLayout) findViewById(R.MainLayout.buttonContainer);
        buttonContainer.addView(serverAddress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Toast.makeText(this, "Menü Einstellungen wurde ausgewählt", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_category:
                switchActivity(CategoryOverviewActivity.class);
                break;

            default:
                break;
        }
        return true;
    }

    private String getServerAddress() {
        return serverAddress == null ? UBBConstants.EMULATOR_SERVER_ADDRESS
                                     : serverAddress.getText().toString();
    }

    @Override
    protected int getSubmitButtonId() {
        return R.MainLayout.login;
    }

    @Override
    protected void preSubmit() {
        String serverAddress = getServerAddress();
        getPreferences().saveServerAddress(serverAddress);
        WebServiceProvider.setServerAddress(serverAddress);
    }

    @Override
    protected String submit() {
        boolean loginSuccessfull = WebServiceProvider.authentificate(getModel().getEMail(),
                                                                     getModel().getPassword());
        if (!loginSuccessfull) {
            return "Login fehlgeschlagen!";
        }
        return StringUtil.EMPTY;
    }

    private boolean saveLogin() {
        return stayLoggedInCheckBox.isChecked();
    }

    @Override
    protected void postSubmit() {
        super.postSubmit();

        if (!isSubmitSuccessfull()) {
            return;
        }

        if (saveLogin()) {
            getPreferences().saveLoginData(getModel().getEMail(),
                                           getModel().getPassword());
        }
        switchActivity(BudgetBookOverviewActivity.class);
    }

    @Override
    protected String getSubmitWaitMessage() {
        return "Anmeldung läuft...";
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
