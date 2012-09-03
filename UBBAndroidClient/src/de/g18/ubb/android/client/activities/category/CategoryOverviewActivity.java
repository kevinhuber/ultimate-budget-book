package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryOverviewActivity extends AbstractActivity<CategoryOverviewModel> {

    private Button createNewCategoryButton;

	private ArrayList<BudgetBookModel> transferredData;

	private ArrayAdapter<Category> adapter;


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

		Long i  = transferredData.get(0).getId();
		BudgetBook bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
        List<Category> catList = ServiceRepository.getCategoryService().getAll(bb);
		adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, catList);

		initComponents();
		initEventHandling();
	}

	private void initComponents() {
        createNewCategoryButton = (Button) findViewById(R.CategoryOverviewLayout.createButton);

		Spinner sp = (Spinner) findViewById(R.CategoryOverviewLayout.categoriesSpinner);
		sp.setAdapter(adapter);
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
