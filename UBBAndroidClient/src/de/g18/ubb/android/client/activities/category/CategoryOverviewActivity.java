package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;

public class CategoryOverviewActivity extends AbstractActivity<CategoryOverviewModel> {

    private Button createNewCategoryButton;

	private ArrayList<BudgetBookModel> transferredData;


    @Override
    protected CategoryOverviewModel createModel() {
        return new CategoryOverviewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_overview;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");

		initComponents();
		initEventHandling();
	}

	private void initComponents() {
        createNewCategoryButton = (Button) findViewById(R.CategoryOverviewLayout.createButton);
	}

	private void initEventHandling() {
        createNewCategoryButton.setOnClickListener(new CreateButtonListener());
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
