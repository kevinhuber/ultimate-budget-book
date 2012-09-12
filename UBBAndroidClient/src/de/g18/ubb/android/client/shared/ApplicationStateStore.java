package de.g18.ubb.android.client.shared;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;

public class ApplicationStateStore {

	private PresentationModel<BudgetBook> budgetBookModel;
	private PresentationModel<Category> categoryModel;

	private static ApplicationStateStore instance;


	private ApplicationStateStore() {
	    // prevent instantiation
	}

	public static ApplicationStateStore getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	private static synchronized void createInstance() {
		if (instance != null) {
			return;
		}
		instance = new ApplicationStateStore();
	}

	public PresentationModel<BudgetBook> getBudgetBookModel() {
	    if (budgetBookModel == null) {
	        budgetBookModel = new PresentationModel<BudgetBook>();
	    }
		return budgetBookModel;
	}

    public PresentationModel<Category> getCategoryModel() {
        if (categoryModel == null) {
            categoryModel = new PresentationModel<Category>();
        }
        return categoryModel;
	}
}
