package de.g18.ubb.android.client.activities.category;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.shared.adapter.CategoryAdapter;
import de.g18.ubb.common.domain.Category;

public class CategoryOverviewActivity extends AbstractActivity<CategoryOverviewModel> {

    private Button createNewCategoryButton;
    private ListView lv;

	private CategoryAdapter adapter;


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
		adapter = new CategoryAdapter(this);

		initComponents();
		initEventHandling();
	}

	@Override
	protected void onResume() {
	    super.onResume();

        List<Category> categories = getApplicationStateStore().getBudgetBook().getCategories();
        for (Category b : categories) {
            adapter.add(b);
        }
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
		    switchActivity(CategoryCreateActivity.class);
		}
	}

    private final class CategorySelectionHandler implements OnItemClickListener {

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Category selectedItem = (Category) arg0.getAdapter().getItem(arg2);
            getApplicationStateStore().setCategory(selectedItem);
            switchActivity(CategoryChangeActivity.class);
        }
    }
}
