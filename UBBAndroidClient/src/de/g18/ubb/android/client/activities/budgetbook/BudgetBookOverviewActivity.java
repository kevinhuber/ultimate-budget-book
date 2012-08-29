package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.category.CategorieCreateActivity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookOverviewActivity extends AbstractActivity<BudgetBookOverviewModel> {

    private Button createButton;


    @Override
    protected BudgetBookOverviewModel createModel() {
        return new BudgetBookOverviewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_budget_book_overview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        initEventHandling();

        // TODO (huber): In AsyncTask auslagern
        fillBudgetBooksView();
    }

    private void initComponents() {
        createButton = (Button) findViewById(R.id.newBudgetBook);
    }

    private void initEventHandling() {
        createButton.setOnClickListener(new CreateNewBudgetBookButtonListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_budget_book_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillBudgetBooksView() {
        List<BudgetBook> books = ServiceRepository.getBudgetBookService().getAllForCurrentUser();
        BudgetBookAdapter adapter = new BudgetBookAdapter(this, books);

        ListView budgetBooksListView = (ListView) findViewById(R.id.budgetBooks);
        budgetBooksListView.setAdapter(adapter);
        budgetBooksListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switchToBudgetBookDetailActivity(arg0.getAdapter().getItem(arg2));
			}

			private void switchToBudgetBookDetailActivity(Object aBook) {
				Intent i = new Intent(getApplicationContext(), BudgetBookDetailActivity.class);
				// erstelle das model (parcable)
				BudgetBookModel bbm = new BudgetBookModel();
				BudgetBook bb = (BudgetBook) aBook;
				bbm.mapBudgetBookToModel(bb);
				// hier ist es  möglich mehrere daten einer anderen activity zu übergeben
				ArrayList<BudgetBookModel> dataList = new ArrayList<BudgetBookModel>();
				dataList.add(bbm);
				i.putParcelableArrayListExtra("BudgetBookModel", dataList);
				
				startActivity(i);
			}
		});
    }
    
    private void switchToBudgetBookCreateNew() {
    	switchActivity(BudgetBookCreateNewActivity.class);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class CreateNewBudgetBookButtonListener implements OnClickListener {

        public void onClick(View aView) {
            switchToBudgetBookCreateNew();
        }
    }
}
