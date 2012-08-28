package de.g18.ubb.android.client.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;
import de.g18.ubb.android.client.BuildConfig;
import de.g18.ubb.android.client.R;
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

    // DEBUG COMPONENTS (MAY BE NULL)
    private Spinner debug_serverAddressSpinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new Preferences(getSharedPreferences("userdetails", MODE_PRIVATE));

        initComponents();
        initEventHandling();

        if (BuildConfig.DEBUG) {
            addDebugComponents();
        }
    }

    private void initComponents() {
        usernameEditText = (EditText) findViewById(R.MainLayout.email);
        passwordEditText = (EditText) findViewById(R.MainLayout.password);

        stayLoggedInCheckBox = (CheckBox) findViewById(R.MainLayout.stayLoggedIn);

        loginButton = (Button) findViewById(R.MainLayout.login);
        registerButton = (Button) findViewById(R.MainLayout.register);

        usernameEditText.setText(preferences.getUsername());
        passwordEditText.setText(preferences.getPassword());
    }

    private void initEventHandling() {
        loginButton.setOnClickListener(new LoginButtonListener());
        registerButton.setOnClickListener(new RegisternButtonListener());
    }

    private void addDebugComponents() {
        debug_serverAddressSpinner = new Spinner(this);
        debug_serverAddressSpinner.setLayoutParams(
                new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                 android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                                                                WebServiceProvider.SERVER_ADDRESSES);
        debug_serverAddressSpinner.setAdapter(adapter);

        debug_serverAddressSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                WebServiceProvider.changeServerAddress((String) arg0.getSelectedItem());
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        LinearLayout buttonContainer = (LinearLayout) findViewById(R.MainLayout.buttonContainer);
        buttonContainer.addView(debug_serverAddressSpinner);
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
                preferences.saveLoginData(getEMail(), getPassword());
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
