package de.g18.ubb.android.client.activities.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.ApplicationStateStore;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryChangeActivity extends Activity {

	public Category bb;
	public Long categorID;
	public Category category;
	public EditText et;

	private TextView tv;
	private Button b_change;
	private Button b_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_change);

        initComponents();
        initEventHandling();
    }

    private void initComponents() {
    	setCateName();
    	b_change = (Button) findViewById(R.CategoryChangeLayout.changeButton);
    	b_delete = (Button) findViewById(R.CategoryChangeLayout.deleteButton);
    }

    private void initEventHandling() {
        b_change.setOnClickListener(new ChangeButtonListener());
        b_delete.setOnClickListener(new DeleteButtonListener());

	}

    public void setCateName() {
        tv = (TextView) findViewById(R.CategoryChangeLayout.nameEditText);
        ApplicationStateStore ass = ApplicationStateStore.getInstance();
        categorID = ass.getCategory().getId();
        if (ass.getCategory() != null) {
            tv.setText(ass.getCategory().getName());
        } else {
            tv.setText("Leider Falsch");
        }
    }


	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class ChangeButtonListener implements OnClickListener{

		public void onClick(View v) {
			ApplicationStateStore ass = ApplicationStateStore.getInstance();
			et = (EditText) findViewById(R.CategoryChangeLayout.nameEditText);
			String s = et.getText().toString();
			if (s.length() != 0) {
				ass.getCategory().setName(s);
				ServiceRepository.getCategoryService().saveAndLoad(ass.getCategory());

				Intent intent = new Intent(getApplicationContext(), CategoryOverviewActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Keine Eingabe!!!", Toast.LENGTH_LONG).show();
			}
		}
	}

	private final class DeleteButtonListener implements OnClickListener{

		public void onClick(View v) {
			ApplicationStateStore ass = ApplicationStateStore.getInstance();

			ServiceRepository.getCategoryService().remove(ass.getCategory());

			Intent intent = new Intent(getApplicationContext(), CategoryOverviewActivity.class);
			startActivity(intent);
		}
	}
}
