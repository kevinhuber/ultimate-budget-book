package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class CategorieCreateActivity extends AbstractValidationFormularActivity<Category, CategoryCreateValidator> {

	private ArrayList<BudgetBookModel> transferredData;
	public BudgetBook bb;


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
        return R.layout.activity_categorie_create;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");
		Long i  = transferredData.get(0).getId();
		try {
            bb = ServiceRepository.getBudgetBookService().load(i);
        } catch (NotFoundExcpetion e) {
            throw new IllegalStateException("BudgetBook with id '" + i + "' has not been found!", e);
        }

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
        Category c = ServiceRepository.getCategoryService().saveAndLoad(getModel());
        List<Category> categories = bb.getCategories();
        categories.add(c);
        bb.setCategories(categories);
        bb = ServiceRepository.getBudgetBookService().saveAndLoad(bb);
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        super.postSubmit();

        if (!isSubmitSuccessfull()) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), CategoryOverviewActivity.class);
        intent.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
        startActivity(intent);
    }
}
