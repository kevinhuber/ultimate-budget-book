package de.g18.ubb.android.client.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.R.id;
import de.g18.ubb.android.client.R.layout;
import de.g18.ubb.android.client.R.menu;
import de.g18.ubb.android.client.action.AbstractWaitAction;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.android.client.activities.register.RegisterActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.utils.Preferences;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MainActivity extends Activity {

    static {
        WebServiceProvider.register();
    }


    private EditText usernameEditText;
    private EditText passwordEditText;

    private CheckBox stayLoggedInCheckBox;
    private Button loginButton;
    private Button registerButton;
    private Preferences preferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new Preferences(getSharedPreferences("userdetails", MODE_PRIVATE));

        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        usernameEditText = (EditText) findViewById(R.id.e_loginname);
        passwordEditText = (EditText) findViewById(R.id.e_loginpassword);

        stayLoggedInCheckBox = (CheckBox) findViewById(R.id.checkbox_stayLoggedIn);

        loginButton = (Button) findViewById(R.id.b_login);
        registerButton = (Button) findViewById(R.id.b_loginregister);

        usernameEditText.setText(preferences.getUserNamePreferences());
        passwordEditText.setText(preferences.getPasswordPreferences());
    }

    private void initEventHandling() {
        loginButton.setOnClickListener(new LoginButtonListener());
        registerButton.setOnClickListener(new RegisternButtonListener());
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
                switchToCategoryOverview();
                break;

            default:
                break;
        }
        return true;
    }

    private String getEMail() {
        return usernameEditText.getText().toString();
    }

    private String getPassword() {
        return passwordEditText.getText().toString();
    }

    private void switchToBudgetBookOverview() {
        Intent myIntent = new Intent(getApplicationContext(), BudgetBookOverviewActivity.class);
        startActivityForResult(myIntent, 0);
    }

    private void switchToCategoryOverview() {
        Intent myIntent = new Intent(getApplicationContext(), CategoryOverviewActivity.class);
        startActivityForResult(myIntent, 0);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class LoginButtonListener extends AbstractWaitAction {

        private boolean loginSuccessfull;


        public LoginButtonListener() {
            super(MainActivity.this, "Anmeldung läuft...");
        }

        @Override
        protected void execute() {
            loginSuccessfull = login();
        }

        private boolean login() {
            return WebServiceProvider.authentificate(getEMail(), getPassword());
        }

        private boolean saveLogin(){
        	return stayLoggedInCheckBox.isChecked();
        }

        @Override
        protected void postExecute() {
            if (!loginSuccessfull) {
                Toast.makeText(getApplicationContext(), "Login fehlgeschlagen!", Toast.LENGTH_LONG).show();
                return;
            }

            if (saveLogin()) {
                // speicher Login für keine erneute eingabe
                preferences.savePreferences(getEMail(), getPassword());
            }
            switchToBudgetBookOverview();
        }
    }

    private final class RegisternButtonListener implements OnClickListener {

        public void onClick(View aView) {
            startActivity(new Intent(getBaseContext(), RegisterActivity.class));
        }
    }
}
