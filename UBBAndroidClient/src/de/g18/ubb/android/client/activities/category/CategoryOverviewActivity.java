package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitTask;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryOverviewActivity extends AbstractActivity<CategoryOverviewModel> {

    private Button createNewCategoryButton;

	private ArrayList<BudgetBookModel> transferredData;

	private OverviewAdapter adapter;


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

		adapter = new OverviewAdapter(this);

		Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");

		initComponents();
		initEventHandling();

        new CategoryLoadTask().run();
	}

	private void initComponents() {
        createNewCategoryButton = (Button) findViewById(R.CategoryOverviewLayout.createButton);


        ListView lv = (ListView) findViewById(R.id.lv_overview);
        lv.setAdapter(adapter);

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

    private final class CategoryLoadTask extends AbstractWaitTask {

        private List<Category> categories;


        public CategoryLoadTask() {
            super(CategoryOverviewActivity.this, "Kategorien werden geladen...");
        }

        @Override
        protected void execute() {
            Long i  = transferredData.get(0).getId();
            BudgetBook bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
            categories = ServiceRepository.getCategoryService().getAll(bb);
        }

        @Override
        protected void postExecute() {
            for (Category b : categories) {
                adapter.add(b);
            }
        }
    }
}
