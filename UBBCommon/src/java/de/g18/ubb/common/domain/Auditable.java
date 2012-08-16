package de.g18.ubb.common.domain;

import java.util.Date;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Auditable extends Identifiable {

    public static final String PROPERTY_CREATE_TIME = "createTime";
    public static final String PROPERTY_CREATE_USER = "createUser";
    public static final String PROPERTY_CHANGE_TIME = "changeTime";
    public static final String PROPERTY_CHANGE_USER = "changeUser";

    void setCreateTime(Date aNewValue);
    Date getCreateTime();

    void setCreateUser(User aNewValue);
    User getCreateUser();

    void setChangeTime(Date aNewValue);
    Date getChangeTime();

    void setChangeUser(User aNewValue);
    User getChangeUser();
}
