package de.g18.ubb.android.client.activities.category;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class CategoryChangeActivity extends AbstractValidationFormularActivity<Category, CategoryChangeValidator> {

	private TextView tv;
	private Button b_delete;


    @Override
    protected Category createModel() {
        return getApplicationStateStore().getCategory();
    }

    @Override
    protected CategoryChangeValidator createValidator() {
        return new CategoryChangeValidator(getModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_change;
    }

    @Override
    protected int getSubmitButtonId() {
        return R.CategoryChangeLayout.changeButton;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        initBindings();
        initEventHandling();
    }

    private void initBindings() {
        bind(Category.PROPERTY_NAME, R.CategoryChangeLayout.nameEditText);
    }

    private void initComponents() {
    	b_delete = (Button) findViewById(R.CategoryChangeLayout.deleteButton);
    }

    private void initEventHandling() {
        b_delete.setOnClickListener(new DeleteButtonListener());
	}

    @Override
    protected String getSubmitWaitMessage() {
        return "Änderungen werden gespeichert";
    }

    @Override
    protected String submit() {
        Category c = ServiceRepository.getCategoryService().saveAndLoad(getApplicationStateStore().getCategory());
        getApplicationStateStore().setCategory(c);
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        super.postSubmit();

        if (!isSubmitSuccessfull()) {
            return;
        }
        switchActivity(CategoryOverviewActivity.class);
    }


	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class DeleteButtonListener implements OnClickListener{

		public void onClick(View v) {
			ServiceRepository.getCategoryService().remove(getModel());
			getApplicationStateStore().setCategory(null);
			switchActivity(CategoryOverviewActivity.class);
		}
	}
}
