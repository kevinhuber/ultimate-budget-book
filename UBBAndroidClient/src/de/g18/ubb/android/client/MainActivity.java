package de.g18.ubb.android.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.utils.Preferences;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.repository.ServiceRepository;

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

    private final class LoginButtonListener implements OnClickListener {

        public void onClick(View aView) {
            if (!login()) {
                Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                return;
            }

            if (saveLogin()) {
			    // speicher Login für keine erneute eingabe
            	preferences.savePreferences(usernameEditText.getText().toString(), passwordEditText.getText().toString());
			}
            try {
                ServiceRepository.getBookingService().loadById(1000L);
            } catch (NotFoundExcpetion e) {
                Log.e(MainActivity.class.getSimpleName(), "NFE has been caught!", e);
            }
            switchToBudgetBookOverview();
        }

        private boolean login() {
            return WebServiceProvider.authentificate(getEMail(), getPassword());
        }

        private boolean saveLogin(){
        	return stayLoggedInCheckBox.isChecked();
        }
    }

    private final class RegisternButtonListener implements OnClickListener {

        public void onClick(View aView) {
            startActivity(new Intent(getBaseContext(), RegisterActivity.class));
        }
    }
}
