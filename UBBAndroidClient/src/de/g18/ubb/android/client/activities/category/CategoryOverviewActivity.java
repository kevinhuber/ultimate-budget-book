package de.g18.ubb.android.client.activities.category;

import de.g18.ubb.android.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CategoryOverviewActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_overview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_category_overview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.b_category_create:
			Toast.makeText(this, "Kategorie neuerstellen ausgewählt",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent(getApplicationContext(), CategorieCreateActivity.class);
			startActivity(i);
			break;
		case R.id.category_change:
			Toast.makeText(this, "Kategorie löschen ausgewählt",
					Toast.LENGTH_SHORT).show();
			Intent x = new Intent(getApplicationContext(), CategoryChangeActivity.class);
			startActivity(x);
			break;

		default:
			break;
		}

		return true;
	}

}
