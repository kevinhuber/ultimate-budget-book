package de.g18.ubb.android.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button login = (Button) findViewById(R.id.b_login);
        login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				login(v);
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void login(View v){
    	EditText e_loginname = (EditText)findViewById(R.id.e_loginname);
    	String name = e_loginname.getText().toString();
    	
    	EditText e_password = (EditText)findViewById(R.id.e_loginpassword);
    	String password = e_password.getText().toString();
    	
    	Toast t = Toast.makeText(getApplicationContext(), "Toast: Name:" + name +
    			" Passwort: " + password , Toast.LENGTH_SHORT);
		t.show();
    }
    
}
