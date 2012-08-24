package de.g18.ubb.android.client.activities.category;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.R.id;
import de.g18.ubb.android.client.R.layout;
import de.g18.ubb.android.client.R.menu;
import android.app.Activity;
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
		case R.id.menu_new:
			Toast.makeText(this, "Kategorie neuerstellen ausgewählt",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_delete:
			Toast.makeText(this, "Kategorie löschen ausgewählt",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

		return true;
	}

}
