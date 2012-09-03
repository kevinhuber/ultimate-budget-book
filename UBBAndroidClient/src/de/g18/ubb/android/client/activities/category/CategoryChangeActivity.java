package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookAdapter;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookDetailActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryChangeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_change);

        ListView lv = (ListView) findViewById(R.id.lv_categories);
        filledWithCategories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_category_change, menu);
        return true;
    }

    public void filledWithCategories(){
    	List<BudgetBook> books = ServiceRepository.getBudgetBookService().getAllForCurrentUser();
        BudgetBookAdapter adapter = new BudgetBookAdapter(this, books);

        ListView budgetBooksListView = (ListView) findViewById(R.id.lv_categories);
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

    public void onClick(View v){
    	switch (v.getId()) {
		case R.id.b_categorie_change_create:
			Intent i = new Intent(getApplicationContext(), CategorieCreateActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
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

}
