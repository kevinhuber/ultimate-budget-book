package de.g18.ubb.android.client;

import java.text.MessageFormat;
import java.util.ArrayList;
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
import de.g18.ubb.common.domain.User;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.HashUtil;
import de.g18.ubb.common.util.ObjectUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MainActivity extends Activity {

    static {
        ServiceRepository.setProvider(new WebServiceProvider());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.b_login);
        login.setOnClickListener(new LoginButtonListener());
        
        Button register = (Button) findViewById(R.id.b_loginregister);
        register.setOnClickListener(new RegisterButtonListener());
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
        Toast.makeText(this, "Menü Einstellungen wurde ausgewählt", Toast.LENGTH_SHORT)
            .show();
        break;
      case R.id.menu_category:
    	switchToCategoryOverview();
        break;

      default:
        break;
      }

      return true;
    }

    private void login(View aView) {
    	EditText e_loginname = (EditText)findViewById(R.id.e_loginname);
    	String name = e_loginname.getText().toString();

    	EditText e_password = (EditText)findViewById(R.id.e_loginpassword);
    	String password = e_password.getText().toString();

    	Toast t = Toast.makeText(getApplicationContext(), "Toast: Name:" + name +
    			" Passwort: " + password , Toast.LENGTH_SHORT);
		t.show();
    }

    private void executeTestCase() {
        log("Creating new BudgetBook...");
        BudgetBook b = new BudgetBook();

        List<User> assignedUsers = new ArrayList<User>();
        assignedUsers.add(getTestUser());
        b.setAssignedUser(assignedUsers);
        b.setName("BudgetBook #" + Math.random());

        log("Saving BudgetBook ''{0}''...", b.getName());
        BudgetBookService service = ServiceRepository.getBudgetBookService();
        b = service.saveAndLoad(b);

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

    private User getTestUser() {
        log("Resolving test user...");
        List<User> users = ServiceRepository.getUserService().getAll();
        for (User u : users) {
            if (ObjectUtil.equals("TestUser", u.getName())) {
                log("Test user is ''{0}''...", users.get(0));
                return u;
            }
        }

        User testUser = createNewPersistedUser();
        log("Created new TestUser ''{0}''...", testUser);
        return testUser;
    }

    private User createNewPersistedUser() {
        User user = new User();
        user.setName("TestUser");
        user.setEmail("test@user.de");
        user.setPasswordHash(HashUtil.toMD5("password", user.getSalt()));
        return ServiceRepository.getUserService().saveAndLoad(user);
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
            User testUser = getTestUser();
            if (!ServiceRepository.getUserService().login(testUser.getEmail(), "password")) {
                Toast.makeText(getApplicationContext(), "Login with TestUser and password failed!", Toast.LENGTH_LONG).show();
                return;
            }
            new TestServiceTask().execute();
//            login(aView);
            switchToBudgetBookOverview();
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
