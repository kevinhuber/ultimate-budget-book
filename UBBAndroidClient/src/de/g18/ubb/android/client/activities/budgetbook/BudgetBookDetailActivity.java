package de.g18.ubb.android.client.activities.budgetbook;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BudgetBookDetailActivity extends AbstractActivity {
	
	private Button delete; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budget_book_create_new);

		initComponents();
		initEventHandling();
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.menu.menu_addBooking:
	                Toast.makeText(this, "Menü - Neuer Eintrag wurde ausgewählt", Toast.LENGTH_SHORT).show();
	                break;

	            case R.menu.menu_categoryBooking:
	                switchToCategoryOverview();
	                break;

	            default:
	                break;
	        }
	        return true;
	    }

	private void switchToCategoryOverview() {
		switchActivity(CategoryOverviewActivity.class);
	}

	private void initComponents() {
	}

	private void initEventHandling() {
		delete.setOnClickListener(new DeleteBudgetBookButtonListener());
	}


	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------


	private final class DeleteBudgetBookButtonListener implements OnClickListener {

		public void onClick(View aView) {

		}
	}

}
