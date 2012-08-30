package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategorieCreateActivity extends Activity {

	private ArrayList<BudgetBookModel> transferredData;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_create);
        
        Button b = new Button(getApplicationContext());
        b.setOnClickListener(new CreateListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_categorie_create, menu);
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
    
    public class CreateListener implements OnClickListener {

		public void onClick(View v) {
			EditText et = (EditText) findViewById(R.id.e_categorie_create);
			String categorie = et.getText().toString();
			// speichere Kategorie
			
			
			Bundle b = getIntent().getExtras();
			transferredData = b.getParcelableArrayList("SingleBudgetBook");
			Long i  = transferredData.get(0).getId();
			BudgetBook bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
			
			
		}
    	
    }

}
