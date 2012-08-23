package de.g18.ubb.android.client;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BudgetBookCreateNewActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budget_book_create_new);

		Button addUser = (Button) findViewById(R.id.b_login);
		addUser.setOnClickListener(new AddExtraUserFieldButtonListener());

		Button save = (Button) findViewById(R.id.b_login);
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
			// TODO: implement
		}
	}

	private final class SaveBudgetBookButtonListener implements OnClickListener {

		public void onClick(View aView) {
			// TODO: implement
			switchToBudgetBookOverview();
		}
	}

}
