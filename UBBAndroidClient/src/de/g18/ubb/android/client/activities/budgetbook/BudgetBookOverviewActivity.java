package de.g18.ubb.android.client.activities.budgetbook;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.R.id;
import de.g18.ubb.android.client.R.layout;
import de.g18.ubb.android.client.R.menu;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookOverviewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_book_overview);

        Button create = (Button) findViewById(R.id.newBudgetBook);
        create.setOnClickListener(new CreateNewBudgetBookButtonListener());

        fillBudgetBooksView();
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

    private void switchToBudgetBookCreateNew() {
        Intent myIntent = new Intent(getApplicationContext(), BudgetBookCreateNewActivity.class);
        startActivity(myIntent);
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
