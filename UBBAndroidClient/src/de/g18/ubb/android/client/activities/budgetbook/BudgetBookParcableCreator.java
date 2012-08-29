package de.g18.ubb.android.client.activities.budgetbook;

import android.os.Parcel;
import android.os.Parcelable;

public class BudgetBookParcableCreator implements Parcelable.Creator<BudgetBookModel>{

	public BudgetBookModel createFromParcel(Parcel source) {
		return new BudgetBookModel(source);
	}

	public BudgetBookModel[] newArray(int size) {
		return new BudgetBookModel[size];
	}

}
