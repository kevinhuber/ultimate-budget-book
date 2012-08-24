package de.g18.ubb.android.client;

import java.util.List;

import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.CategoryService;
import de.g18.ubb.common.service.repository.ServiceRepository;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class CategoryChangeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_change);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        ListView lv = (ListView) findViewById(R.id.lv_categories);
//        String[] values = {"a", "b" , "c"};
        
//        List<Category> l = ServiceRepository.getCategoryService().getAll(book);
        
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//        		  android.R.layout.simple_list_item_1, android.R.id.text1, values);
//
//    	lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_category_change, menu);
        return true;
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
