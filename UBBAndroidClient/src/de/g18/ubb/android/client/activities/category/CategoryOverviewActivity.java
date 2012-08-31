package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.AbstractEntity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CategoryOverviewActivity extends Activity {

	private ArrayList<BudgetBookModel> transferredData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_overview);
		
		Button b = (Button) findViewById(R.id.b_category_create);
		b.setOnClickListener(new OverviewListener());
		
		Button c = (Button) findViewById(R.id.category_change);
		c.setOnClickListener(new OverviewListener());
		
		Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");
		Long i  = transferredData.get(0).getId();
		BudgetBook bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_category_overview, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.b_category_create:
//			Toast.makeText(this, "Kategorie neuerstellen ausgewählt",
//					Toast.LENGTH_SHORT).show();
//			Intent i = new Intent(getApplicationContext(), CategorieCreateActivity.class);
//			startActivity(i);
//			break;
//		case R.id.category_change:
//			Toast.makeText(this, "Kategorie löschen ausgewählt",
//					Toast.LENGTH_SHORT).show();
//			Intent x = new Intent(getApplicationContext(), CategoryChangeActivity.class);
//			startActivity(x);
//			break;
//
//		default:
//			break;
//		}
//
//		return true;
//	}
	
	public class OverviewListener implements OnClickListener{

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.b_category_create:
				Intent intent = new Intent(getApplicationContext(),
						CategorieCreateActivity.class);
				intent.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
				startActivity(intent);
//				Intent i = new Intent(getApplicationContext(), CategorieCreateActivity.class);
//				startActivity(i);
				break;
			case R.id.category_change:
				Intent intent1 = new Intent(getApplicationContext(),
						CategoryChangeActivity.class);
				intent1.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
				startActivity(intent1);
//				Intent x = new Intent(getApplicationContext(), CategoryChangeActivity.class);
//				startActivity(x);
				break;
			default:
				break;
			}
		}
		
	}

}
