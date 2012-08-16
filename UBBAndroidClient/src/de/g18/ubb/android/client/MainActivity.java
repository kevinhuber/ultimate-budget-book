package de.g18.ubb.android.client;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class MainActivity extends Activity {

    static {
        ServiceRepository.setProvider(new WebServiceProvider());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.b_login);
        login.setOnClickListener(new LoginButtonListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void login(View aView){
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
        if (!users.isEmpty()) {
            log("Test user is ''{0}''...", users.get(0));
            return users.get(0);
        }

        User testUser = createNewPersistedUser();
        log("Test user is ''{0}''...", testUser);
        return testUser;
    }

    private User createNewPersistedUser() {
        User user = new User();
        user.setName("SomeGeneratedUserName #" + Math.random());
        return ServiceRepository.getUserService().saveAndLoad(user);
    }

    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class LoginButtonListener implements OnClickListener {

        public void onClick(View aView) {
            executeTestCase();
//            login(aView);
        }
    }
}
