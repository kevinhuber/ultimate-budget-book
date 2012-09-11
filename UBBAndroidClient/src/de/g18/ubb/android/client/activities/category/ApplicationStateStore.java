package de.g18.ubb.android.client.activities.category;

import java.util.ArrayList;

import de.g18.ubb.android.client.activities.budgetbook.BudgetBookCreateNewModel;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;


public class ApplicationStateStore {

	private BudgetBook bb;
	private Category category;

	private static ApplicationStateStore instance;
	private ArrayList<BudgetBookCreateNewModel> transferredData;

	private ApplicationStateStore(){

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

	public BudgetBook getBb() {
		return bb;
	}

	public void setBb(BudgetBook bb) {
		this.bb = bb;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ArrayList<BudgetBookCreateNewModel> getTransferredData() {
		return transferredData;
	}

	public void setTransferredData(ArrayList<BudgetBookCreateNewModel> transferredData) {
		this.transferredData = transferredData;
	}

}
