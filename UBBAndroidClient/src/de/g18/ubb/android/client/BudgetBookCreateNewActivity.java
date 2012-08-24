package de.g18.ubb.android.client;

import java.util.ArrayList;
import java.util.List;

import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.repository.ServiceRepository;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class BudgetBookCreateNewActivity extends Activity {

	private EditText budgetBookName;
    private EditText budgetBookOwner;
    private List<String> userNameList;

    private Button addUser;
    private Button save;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budget_book_create_new);
		
		initComponents();
        initEventHandling();
	}
	
	private void initComponents() {
    	budgetBookName = (EditText) findViewById(R.id.budgetBookName);
    	budgetBookOwner = (EditText) findViewById(R.id.budgetBookOwnerName);
    	
    	
    	budgetBookOwner.setText(WebServiceProvider.getUsername(), TextView.BufferType.EDITABLE);
    	budgetBookOwner.setEnabled(false);
    	
    	userNameList = new ArrayList<String>();

    	addUser = (Button) findViewById(R.id.b_add_budgetbook_owner);
    	save = (Button) findViewById(R.id.b_save_budgetbook);
    }
	
	private void initEventHandling() {
		 addUser.setOnClickListener(new AddExtraUserFieldButtonListener());
		 save.setOnClickListener(new SaveBudgetBookButtonListener());
	}

	private void switchToBudgetBookOverview() {
		Intent myIntent = new Intent(getApplicationContext(),
				BudgetBookOverviewActivity.class);
		startActivityForResult(myIntent, 0);
	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class AddExtraUserFieldButtonListener implements
			OnClickListener {

		public void onClick(View aView) {
			
		}
	}

	private final class SaveBudgetBookButtonListener implements OnClickListener {

		public void onClick(View aView) {
			
            try {
            	BudgetBookService service = ServiceRepository.getBudgetBookService();
            	// fügt den ersten benutzer hinzu, im normal fall ist dies der angemeldete benutzer
            	userNameList.add(budgetBookOwner.getText().toString()) ;
            	
            	service.createNew(budgetBookName.getText().toString(), null);
            	
            	switchToBudgetBookOverview();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
