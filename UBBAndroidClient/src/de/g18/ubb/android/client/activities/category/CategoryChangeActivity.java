package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import android.os.Bundle;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CategoryChangeActivity extends AbstractValidationFormularActivity<Category, CategoryCreateValidator> {

	private ArrayList<BudgetBookModel> transferredData;
	public BudgetBook bb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_change);

        Bundle bundle = getIntent().getExtras();
		transferredData = bundle.getParcelableArrayList("SingleBudgetBook");
		Long i  = transferredData.get(0).getId();
		bb = ServiceRepository.getBudgetBookService().loadSinglebudgetBookById(i);
		getModel().setBudgetBook(bb);

		initBindings();
    }

	private void initBindings() {
//		bind(Category.PROPERTY_NAME, R.CategoryCreateLayout.name);
	}

	@Override
	protected int getSubmitButtonId() {
		return 0;
	}

	@Override
	protected String submit() {
		return "bearbeite Kategorie....";
	}

	@Override
	protected String getSubmitWaitMessage() {
		return "Kategorie wird bearbeitet...";
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
		return  R.layout.activity_category_change;
	}

}
