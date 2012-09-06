package de.g18.ubb.android.client.activities.category;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryParcableCreator implements Parcelable.Creator<CategoryModel>{

	public CategoryModel createFromParcel(Parcel source) {
		return new CategoryModel(source);
	}

	public CategoryModel[] newArray(int size) {
		return new CategoryModel[size];
	}

}
