package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithEMailNotFound;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookCreateNewActivity extends AbstractActivity<BudgetBookModel> {

	private EditText budgetBookName;
	private List<EditText> budgetBookOwner;
	private List<String> userNameList;

	private Button addUser;
	private Button save;


    @Override
    protected BudgetBookModel createModel() {
        return new BudgetBookModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_budget_book_create_new;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initComponents();
		initEventHandling();
	}

	private void initComponents() {
		budgetBookName = (EditText) findViewById(R.id.budgetBookName);
		// budgetBookOwner = (EditText) findViewById(R.id.budgetBookOwnerName);
		budgetBookOwner = new ArrayList<EditText>();
		budgetBookOwner.add((EditText) this
				.findViewById(R.id.budgetBookOwnerName));
		budgetBookOwner.get(0).setText(WebServiceProvider.getUsername(),
				TextView.BufferType.EDITABLE);
		budgetBookOwner.get(0).setEnabled(false);

		userNameList = new ArrayList<String>();

		addUser = (Button) findViewById(R.id.b_add_budgetbook_owner);
		save = (Button) findViewById(R.id.b_save_budgetbook);
	}

	private void initEventHandling() {
		addUser.setOnClickListener(new AddExtraUserFieldButtonListener());
		save.setOnClickListener(new SaveBudgetBookButtonListener());
	}

	private void switchToBudgetBookOverview() {
		switchActivity(BudgetBookOverviewActivity.class);
	}


	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class AddExtraUserFieldButtonListener implements
			OnClickListener {

		private static final String TAG = "BudgetBookCreateNewActivity";

		public void onClick(View aView) {
			try {
				LinearLayout layout = (LinearLayout) findViewById(R.id.userEditTexts);
				EditText temp = new EditText(BudgetBookCreateNewActivity.this);
				temp.setLayoutParams(new LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

				budgetBookOwner.add(temp);

				layout.addView(temp);
			} catch (Exception e) {
				Log.d(TAG, "Failed to create new EditText" + e);
			}
		}
	}

	private final class SaveBudgetBookButtonListener implements OnClickListener {

		public void onClick(View aView) {

			try {
				BudgetBookService service = ServiceRepository.getBudgetBookService();

				// fügt den ersten benutzer hinzu, im normal fall ist dies der
				// angemeldete benutzer
				for (EditText budgetBookOwnerEntry : budgetBookOwner) {
					userNameList.add(budgetBookOwnerEntry.getText().toString());
				}
				service.createNew(budgetBookName.getText().toString(),
						userNameList);

				switchToBudgetBookOverview();
			} catch (UserWithEMailNotFound e) {
				Toast.makeText(getApplicationContext(),
						"Benutzer bzw. Email wurde nicht gefunden",
						Toast.LENGTH_LONG).show();
			} catch (NotFoundExcpetion e) {
				Toast.makeText(getApplicationContext(),
						"Benutzer nicht gefunden!", Toast.LENGTH_LONG).show();
			}
		}
	}
}
