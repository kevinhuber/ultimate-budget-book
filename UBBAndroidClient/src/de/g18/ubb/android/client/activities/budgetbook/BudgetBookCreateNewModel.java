package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.domain.BudgetBook;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BudgetBookCreateNewModel extends AbstractModel implements Parcelable {

	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_ASSIGNED_USERS = "assignedUsers";

	private static final String TAG = "BudgetBookModel";

	// braucht der compiler, sonst gibts was ...
	public static BudgetBookParcableCreator CREATOR = new BudgetBookParcableCreator();

	private String name;
	private Long id;

	private List<String> assignedUsers;


	public BudgetBookCreateNewModel(){
		this(null);
	}

	public BudgetBookCreateNewModel(Parcel source) {
		/*
         * Reconstruct from the Parcel - FIFO
         */
        Log.v(TAG, "BudgetBookModel(Parcel source): und wieder alles zurück");
        id = source == null ? -1 : source.readLong();
        name = source == null ? null : source.readString();
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

    public void setAssignedUsers(List<String> aNewValue) {
        List<String> oldValue = getAssignedUsers();
        assignedUsers = aNewValue;
        fireChange(PROPERTY_ASSIGNED_USERS, oldValue, getAssignedUsers());
    }

    public List<String> getAssignedUsers() {
        if (assignedUsers == null) {
            assignedUsers = new ArrayList<String>();
        }
        return assignedUsers;
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
