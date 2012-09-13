package de.g18.ubb.android.client.activities.category;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitAction;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

/**
 * {@link Activity} zum ändern einer bestehenden {@link Category}.
 *
 * @author Daniel Fels
 */
public class CategoryChangeActivity extends AbstractValidationFormularActivity<Category, CategoryValidator> {

	private Button b_delete;


    @Override
    protected Category createModel() {
        return getApplicationStateStore().getCategory();
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


	// -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private final class DeleteButtonListener extends AbstractWaitAction {

        public DeleteButtonListener() {
            super(CategoryChangeActivity.this, "Kategorie wird gelöscht...");
        }

        @Override
        protected void execute() {
            BudgetBook budgetBook = getApplicationStateStore().getBudgetBook();
            List<Category> categories = budgetBook.getCategories();
            categories.remove(getModel());
            budgetBook.setCategories(categories);

            budgetBook = ServiceRepository.getBudgetBookService().saveAndLoad(budgetBook);

            getApplicationStateStore().setCategory(null);
            getApplicationStateStore().setBudgetBook(budgetBook);

            finish();
        }
	}
}
