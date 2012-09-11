package de.g18.ubb.android.client.activities.budgetbook;

import android.os.Parcel;
import android.os.Parcelable;

public class BudgetBookParcableCreator implements Parcelable.Creator<BudgetBookCreateNewModel>{

	public BudgetBookCreateNewModel createFromParcel(Parcel source) {
		return new BudgetBookCreateNewModel(source);
	}

	public BudgetBookCreateNewModel[] newArray(int size) {
		return new BudgetBookCreateNewModel[size];
	}

}
