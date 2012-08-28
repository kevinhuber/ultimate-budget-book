package de.g18.ubb.android.client.activities.register;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitAction;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.utils.Preferences;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

public class RegisterActivity extends AbstractActivity {

    private Button register;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
        initEventHandling();
    }

    private void initComponents() {
        register = (Button) findViewById(R.RegisterLayout.register);
    }

    private void initEventHandling() {
        register.setOnClickListener(new RegisterButtonListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class RegisterButtonListener extends AbstractWaitAction {

        private String username;
        private String eMail;
        private String password;
        private String passwordCheck;

        private String result;


        public RegisterButtonListener() {
            super(RegisterActivity.this, "Registrierung wird abgeschlossen...");
        }

        @Override
        protected void preExecute() {
            EditText e_anzeigename = (EditText) findViewById(R.RegisterLayout.name);
            username = e_anzeigename.getText().toString();

            EditText e_email = (EditText) findViewById(R.RegisterLayout.email);
            eMail = e_email.getText().toString();

            EditText e_password1 = (EditText) findViewById(R.RegisterLayout.password);
            password = e_password1.getText().toString();

            EditText e_password2 = (EditText) findViewById(R.RegisterLayout.passwordCheck);
            passwordCheck = e_password2.getText().toString();
        }

        @Override
        protected void execute() {
        	result = register(username, eMail, password, passwordCheck);
        }

        private String register(String aUsername, String aEMail, String aPassword, String aPasswordCheck) {
            if (checkNotEmpty(aUsername) && checkNotEmpty(aEMail)) {
                if (!ServiceRepository.getUserService().existsUserWithEMail(aEMail)) {
                    if (checkPw(aPassword, aPasswordCheck)) {
                        ServiceRepository.getUserService().register(aEMail, aUsername, aPassword);
                        WebServiceProvider.authentificate(aEMail, aPassword);

                        Preferences p = new Preferences(getSharedPreferences("userdetails", MODE_PRIVATE));
                        p.saveLoginData(aEMail, aPassword);

                        switchActivity(BudgetBookOverviewActivity.class);
                        return "Registration erfolgreich!!!";
                    } else {
                        return "Passwörter stimmen nicht überein!";
                    }
                } else {
                    return "EMail wird bereits verwendet!";
                }
            }
            return "Wie konnte das den passieren, du PENIS?";
        }

        private boolean checkPw(String pw1, String pw2){
            return ObjectUtil.equals(pw1, pw2);
        }

        private boolean checkNotEmpty(String input){
            return !StringUtil.isEmpty(input);
        }

        @Override
        protected void postExecute() {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}
