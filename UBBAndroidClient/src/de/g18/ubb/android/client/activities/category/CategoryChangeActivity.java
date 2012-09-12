package de.g18.ubb.android.client.activities.category;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.binding.BindingHelper;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class CategoryChangeActivity extends AbstractValidationFormularActivity<Category,
                                                                               PresentationModel<Category>,
                                                                               CategoryValidator> {

	private Button b_delete;


    @Override
    protected PresentationModel<Category> createModel() {
        return new PresentationModel<Category>(getApplicationStateStore().getCategory());
    }

    @Override
    protected CategoryValidator createValidator() {
        return new CategoryValidator(getModel());
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
        BindingHelper helper = new BindingHelper(this);
        helper.bindEditText(getModel().getModel(Category.PROPERTY_NAME), R.CategoryChangeLayout.nameEditText);
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
//
//        if (!isSubmitSuccessfull()) {
//            return;
//        }
//        switchActivity(CategoryOverviewActivity.class);
    }


	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class DeleteButtonListener implements OnClickListener{

		public void onClick(View v) {
			ServiceRepository.getCategoryService().remove(getModel().getBean());
			getApplicationStateStore().setCategory(null);
			switchActivity(CategoryOverviewActivity.class);
		}
	}
}
