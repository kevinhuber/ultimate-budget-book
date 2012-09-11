package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.List;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BudgetBookCreateNewModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_ASSIGNED_USERS = "assignedUsers";

	private String name;
	private List<String> assignedUsers;


	public BudgetBookCreateNewModel(){
	}

	public void setName(String aNewValue) {
		String oldValue = getName();
		name = aNewValue;
		fireChange(PROPERTY_NAME, oldValue, getName());
	}

	public String getName() {
		return name;
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
}
