package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategorieCreateActivity extends Activity {

	private ArrayList<BudgetBookModel> transferredData;
	public BudgetBook bb;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_create);
        
        Button b = (Button) findViewById(R.id.b_categorie_create);
        b.setOnClickListener(new CreateListener());
        
        Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");
		Long i  = transferredData.get(0).getId();
		bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
    }

    
    public class CreateListener implements OnClickListener {

		public void onClick(View v) {
			EditText et = (EditText) findViewById(R.id.e_categorie_create);
			String categorie = et.getText().toString();
			
			Category c = new Category();
			c.setBudgetBook(bb);
			c.setName(categorie);
			ServiceRepository.getCategoryService().saveAndLoad(c);
			
			
			Intent intent = new Intent(getApplicationContext(),
					CategoryOverviewActivity.class);
			intent.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
			startActivity(intent);
		}
    	
    }

}
