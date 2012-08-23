package de.g18.ubb.android.client;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookOverviewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_book_overview);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        fillBudgetBooksView();
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
        List<BudgetBook> books = ServiceRepository.getBudgetBookService().getAll();
        ArrayAdapter<BudgetBook> adapter = new ArrayAdapter<BudgetBook>(this, android.R.layout.simple_list_item_1,
                                                                        books);

        ListView budgetBooksListView = (ListView) findViewById(R.id.budgetBooks);
        budgetBooksListView.setAdapter(adapter);
    }
}
