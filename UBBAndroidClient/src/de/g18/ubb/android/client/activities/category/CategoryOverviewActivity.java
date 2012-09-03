package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;

public class CategoryOverviewActivity extends Activity {

	private ArrayList<BudgetBookModel> transferredData;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_overview);

		Button b = (Button) findViewById(R.CategoryOverviewLayout.createButton);
		b.setOnClickListener(new CreateButtonListener());

		Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");
	}


	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class CreateButtonListener implements OnClickListener{

		public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CategorieCreateActivity.class);
            intent.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
            startActivity(intent);
		}
	}
}
