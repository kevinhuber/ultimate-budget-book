package de.g18.ubb.android.client.activities.budgetbook;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.domain.BudgetBook;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BudgetBookModel extends AbstractModel implements Parcelable {

	private static final long serialVersionUID = 1L;

	private static final String PROPERTY_NAME = "name";

	private static final String PROPERTY_ID = "id";

	private static final String TAG = "BudgetBookModel";
	
	// braucht der compiler, sonst gibts was ...
	private static BudgetBookParcableCreator CREATOR;

	private String name;

	private Long id;

	public BudgetBookModel() {
	}

	public BudgetBookModel(Parcel source) {
		/*
         * Reconstruct from the Parcel - FIFO
         */
        Log.v(TAG, "BudgetBookModel(Parcel source): und wieder alles zurück");
        id = source.readLong();
        name = source.readString();
	}

	public void setName(String aNewValue) {
		String oldValue = getName();
		name = aNewValue;
		fireChange(PROPERTY_NAME, oldValue, getName());
	}

	public String getName() {
		return name;
	}

	public void setId(Long aNewValue) {
		Long oldValue = getId();
		id = aNewValue;
		fireChange(PROPERTY_ID, oldValue, getId());
	}

	public Long getId() {
		return id;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		Log.v(TAG, "writeToParcel..." + flags);
		dest.writeLong(id);
		dest.writeString(name);
	}
	
	public void mapBudgetBookToModel(BudgetBook aBudgetBook){
		setId(aBudgetBook.getId());
		setName(aBudgetBook.getName());
	}
}
