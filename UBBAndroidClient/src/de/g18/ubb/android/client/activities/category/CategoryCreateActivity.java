package de.g18.ubb.android.client.activities.category;

import java.util.List;

import android.os.Bundle;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class CategoryCreateActivity extends AbstractValidationFormularActivity<Category, CategoryCreateValidator> {

    @Override
    protected int getSubmitButtonId() {
        return R.CategoryCreateLayout.createButton;
    }

    @Override
    protected CategoryCreateValidator createValidator() {
        return new CategoryCreateValidator(getModel());
    }

    @Override
    protected Category createModel() {
        return new Category();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_create;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		initBindings();
    }

	private void initBindings() {
	    bind(Category.PROPERTY_NAME, R.CategoryCreateLayout.name);
	}

    @Override
    protected String getSubmitWaitMessage() {
        return "Kategorie wird erstellt...";
    }

    @Override
    protected String submit() {
        Category category = ServiceRepository.getCategoryService().saveAndLoad(getModel());

        BudgetBook budgetBook = getApplicationStateStore().getBudgetBook();
        List<Category> categories = budgetBook.getCategories();
        categories.add(category);
        budgetBook.setCategories(categories);

        budgetBook = ServiceRepository.getBudgetBookService().saveAndLoad(budgetBook);
        getApplicationStateStore().setBudgetBook(budgetBook);
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
}
