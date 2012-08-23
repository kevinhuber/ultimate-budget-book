package de.g18.ubb.android.client;

import java.text.MessageFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.repository.ServiceRepository;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MainActivity extends Activity {

    static {
        WebServiceProvider.register();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.b_login);
        login.setOnClickListener(new LoginButtonListener());

        Button register = (Button) findViewById(R.id.b_loginregister);
        register.setOnClickListener(new RegisternButtonListener());
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

    private boolean login() {
    	EditText usernameTextField = (EditText) findViewById(R.id.e_loginname);
    	String username = usernameTextField.getText().toString();

    	EditText passwordTextField = (EditText) findViewById(R.id.e_loginpassword);
    	String password = passwordTextField.getText().toString();

        return WebServiceProvider.authentificate(username, password);
    }

    private void executeTestCase() {
        BudgetBookService service = ServiceRepository.getBudgetBookService();

        log("Creating new BudgetBook...");
        service.createNew("BudgetBook #" + Math.random());

        log("Listing all saved BudgetBooks...");
        List<BudgetBook> books = service.getAll();
        for (BudgetBook book : books) {
            log("-> {0}", book);
        }
    }

    private void log(String aMessage, Object... aMessageParams) {
        String formattedMessage = MessageFormat.format(aMessage, aMessageParams);
        Log.w(getClass().getSimpleName(), formattedMessage);
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

            new TestServiceTask().execute();
            switchToBudgetBookOverview();
        }
    }

    private final class RegisternButtonListener implements OnClickListener {

        public void onClick(View aView) {
            startActivity(new Intent(getBaseContext(), RegisterActivity.class));
        }
    }

    private final class TestServiceTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            executeTestCase();
            return null;
        }
    }
}
