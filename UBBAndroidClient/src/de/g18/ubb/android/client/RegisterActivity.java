package de.g18.ubb.android.client;

import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Button register = (Button) findViewById(R.id.b_register);
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

    private final class RegisterButtonListener implements OnClickListener {

        public void onClick(View aView) {
        	EditText e_anzeigename = (EditText) findViewById(R.id.e_anzeigename);
        	String aUsername = e_anzeigename.getText().toString();
        	
        	EditText e_email = (EditText) findViewById(R.id.e_email);
        	String aEMail = e_email.getText().toString();
        	
        	EditText e_password1 = (EditText) findViewById(R.id.e_password1);
        	String password1 = e_password1.getText().toString();
        	
        	EditText e_password2 = (EditText) findViewById(R.id.e_password2);
        	String password2 = e_password2.getText().toString();
        	
        	TestServiceTask t = new TestServiceTask(aUsername, aEMail, password1, password2);
        	AsyncTask<Void, Void, String> dispachedTask = t.execute();
        	try {
				String result = dispachedTask.get();
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Fehler: Registrierung konnte nicht abgeschlossen werden!", Toast.LENGTH_LONG).show();
				Log.e(getClass().getSimpleName(), "Error while registering user!", e);
			}
        }
    }
    

    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class TestServiceTask extends AsyncTask<Void, Void, String> {
    	
    	private final String username;
    	private final String eMail;
    	private final String password;
    	private final String passwordCheck;
    	

    	public TestServiceTask(String aUsername, String aEMail, String aPassword, String aPasswordCheck) {
    		username = aUsername;
    		eMail = aEMail;
    		password = aPassword;
    		passwordCheck = aPasswordCheck;
    	}
    	
        @Override
        protected String doInBackground(Void... params) {
        	if (checkNotEmpty(username) && (checkNotEmpty(eMail))) {
        		if (!ServiceRepository.getUserService().existsUserWithEMail(eMail)) {
        			if (checkPw(password, passwordCheck)) {
        				ServiceRepository.getUserService().register(eMail, username, password);
        				WebServiceProvider.authentificate(username, password);
        				
        				Intent i = new Intent(getApplicationContext(), BudgetBookOverviewActivity.class);
        				startActivity(i);
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
    }
}
