package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.g18.ubb.android.client.R;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryChangeActivity extends Activity{

	private  ArrayList<CategoryModel> dataList;
	public Category bb;
	public Long categorID;
	public Category category;

	private TextView tv;
	private Button b_change;
	private Button b_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_change);

        Bundle bundle = getIntent().getExtras();
        dataList = bundle.getParcelableArrayList("CategoryModel");
        categorID  = dataList.get(0).getId();
//		bb = ServiceRepository.getCategoryService().loadSinglebudgetBookById(i);

        initComponents();
        initEventHandling();
    }

    private void initComponents() {
    	setCateName();
    	b_change = (Button) findViewById(R.id.b_over_change);
    	b_delete = (Button) findViewById(R.id.b_overchange_delete);
    }

    private void initEventHandling() {
        b_change.setOnClickListener(new ChangeButtonListener());
        b_delete.setOnClickListener(new DeleteButtonListener());

	}

	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class ChangeButtonListener implements OnClickListener{

		public void onClick(View v) {

			Toast.makeText(getApplicationContext(), "change",
            		Toast.LENGTH_LONG).show();
		}
	}

	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class DeleteButtonListener implements OnClickListener{

		public void onClick(View v) {
			try {
				ServiceRepository.getCategoryService().removeById(categorID);
			} catch (NotFoundExcpetion e) {
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(), "delete",
            		Toast.LENGTH_LONG).show();
//			Intent intent = new Intent(getApplicationContext(), CategoryOverviewActivity.class);
//			startActivity(intent);
		}
	}

    public void setCateName(){
    	tv = (TextView) findViewById(R.id.tv_overchange);
    	if (dataList != null) {
			tv.setText(dataList.get(0).getName());
		} else {
			tv.setText("Leider Falsch");
		}
    }
}
