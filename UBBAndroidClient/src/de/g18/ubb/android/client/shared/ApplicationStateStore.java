package de.g18.ubb.android.client.shared;

import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;

public class ApplicationStateStore {

	private BudgetBook budgetBook;
	private Category category;

	private static ApplicationStateStore instance;


	private ApplicationStateStore(){
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

	public BudgetBook getBudgetBook() {
		return budgetBook;
	}

	public void setBudgetBook(BudgetBook aNewValue) {
		budgetBook = aNewValue;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category aNewValue) {
		category = aNewValue;
	}
}
