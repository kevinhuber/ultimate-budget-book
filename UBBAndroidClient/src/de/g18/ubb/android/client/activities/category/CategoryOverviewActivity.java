package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitTask;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryOverviewActivity extends AbstractActivity<CategoryOverviewModel> {

    private Button createNewCategoryButton;
    private ListView lv;

	private ArrayList<BudgetBookModel> transferredData;
//	public ApplicationStateStore ass;

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
		Long i  = transferredData.get(0).getId();

		ApplicationStateStore ass = ApplicationStateStore.getInstance();
		ass.setBb(ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i));
		ass.setTransferredData(transferredData);

		initComponents();
		initEventHandling();

        new CategoryLoadTask().run();
	}

	private void initComponents() {
        createNewCategoryButton = (Button) findViewById(R.CategoryOverviewLayout.createButton);

        lv = (ListView) findViewById(R.CategoryOverviewLayout.categoriesListView);
        lv.setAdapter(adapter);
	}

	private void initEventHandling() {
        createNewCategoryButton.setOnClickListener(new CreateButtonListener());
        lv.setOnItemClickListener(new CategorySelectionHandler());

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

    private final class CategorySelectionHandler extends AbstractWaitTask implements OnItemClickListener {

		private Category selectedItem;
        private Intent intentToStart;


        public CategorySelectionHandler() {
            super(CategoryOverviewActivity.this, "Detailansicht wird geladen...");
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            selectedItem = (Category) arg0.getAdapter().getItem(arg2);
            ApplicationStateStore ass = ApplicationStateStore.getInstance();
            ass.setCategory(selectedItem);
//            Toast.makeText(getApplicationContext(), selectedItem.getName(),
//            		Toast.LENGTH_LONG).show();
            run();
        }

        @Override
        protected void execute() {
            intentToStart = new Intent(getApplicationContext(), CategoryChangeActivity.class);

//            intentToStart.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
            // erstelle das model (parcable)

//            CategoryModel bbm = new CategoryModel();
//            bbm.mapCategoryToModel(selectedItem);

            // hier ist es  möglich mehrere daten einer anderen activity zu übergeben
//            ArrayList<CategoryModel> dataList = new ArrayList<CategoryModel>();
//            dataList.add(bbm);
//            intentToStart.putParcelableArrayListExtra("CategoryModel", dataList);

        }

        @Override
        protected void postExecute() {
            startActivity(intentToStart);
        }
    }
}
