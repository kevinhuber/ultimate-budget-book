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
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.binding.BindingHelper;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.exception.UserWithEMailNotFound;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class BudgetBookCreateNewActivity extends AbstractValidationFormularActivity<BudgetBookCreateNewModel,
                                                                                    PresentationModel<BudgetBookCreateNewModel>,
                                                                                    BudgetBookCreateNewValidator> {

	private List<EditText> budgetBookOwner;

	private Button addUser;


    @Override
    protected PresentationModel<BudgetBookCreateNewModel> createModel() {
        return new PresentationModel<BudgetBookCreateNewModel>(new BudgetBookCreateNewModel());
    }

    @Override
    protected BudgetBookCreateNewValidator createValidator() {
        return new BudgetBookCreateNewValidator(getModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_budget_book_create_new;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initComponents();
		initBindings();
		initEventHandling();
	}

    private void initComponents() {
		budgetBookOwner = new ArrayList<EditText>();
		budgetBookOwner.add((EditText) this
				.findViewById(R.BudgetBookCreateLayout.user));
		budgetBookOwner.get(0).setText(WebServiceProvider.getUsername(), TextView.BufferType.EDITABLE);
		budgetBookOwner.get(0).setEnabled(false);

		addUser = (Button) findViewById(R.BudgetBookCreateLayout.addUserButton);
	}

    private void initBindings() {
        BindingHelper helper = new BindingHelper(this);
        helper.bindEditText(getModel().getModel(BudgetBookCreateNewModel.PROPERTY_NAME), R.BudgetBookCreateLayout.name);
    }

	private void initEventHandling() {
		addUser.setOnClickListener(new AddExtraUserFieldButtonListener());
	}

    @Override
    protected int getSubmitButtonId() {
        return R.BudgetBookCreateLayout.createButton;
    }

    @Override
    protected String getSubmitWaitMessage() {
        return "Haushaltsbuch wird erstellt...";
    }

    @Override
    protected String submit() {
        try {
            BudgetBookService service = ServiceRepository.getBudgetBookService();

            // fügt den ersten benutzer hinzu, im normal fall ist dies der
            // angemeldete benutzer
            for (EditText budgetBookOwnerEntry : budgetBookOwner) {
                getModel().getBean().getAssignedUsers().add(budgetBookOwnerEntry.getText().toString());
            }
            service.createNew((String) getModel().getValue(BudgetBookCreateNewModel.PROPERTY_NAME),
                              getModel().getBean().getAssignedUsers());
        } catch (UserWithEMailNotFound e) {
            return "Es wurde kein Benutzer mit der E-Mail '" + e.getEMail() + "' gefunden!";
        }
        return StringUtil.EMPTY;
    }


	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class AddExtraUserFieldButtonListener implements OnClickListener {

		public void onClick(View aView) {
			try {
				LinearLayout layout = (LinearLayout) findViewById(R.BudgetBookCreateLayout.userFieldsContainer);
				EditText temp = new EditText(BudgetBookCreateNewActivity.this);
				temp.setLayoutParams(new LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

				budgetBookOwner.add(temp);

				layout.addView(temp);
			} catch (Exception e) {
				Log.d(BudgetBookCreateNewActivity.class.getSimpleName(), "Failed to create new EditText" + e);
			}
		}
	}
}
