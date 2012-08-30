package de.g18.ubb.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Abstrakte implementierung des {@link Auditable} Interfaces.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@MappedSuperclass
public abstract class AbstractAuditEntity extends AbstractEntity implements Auditable {

    private static final long serialVersionUID = 1L;

    private Date createTime;
    private User createUser;

    private Date changeTime;
    private User changeUser;


    public AbstractAuditEntity() {
    }

    @Override
    public final void setCreateTime(Date aNewValue) {
        Date oldValue = getCreateTime();
        createTime = aNewValue;
        fireChange(PROPERTY_CREATE_TIME, oldValue, getCreateTime());
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public final Date getCreateTime() {
        return createTime;
    }

    @Override
    @Column(nullable = false)
    public final void setCreateUser(User aNewValue) {
        User oldValue = getCreateUser();
        createUser = aNewValue;
        fireChange(PROPERTY_CREATE_USER, oldValue, getCreateUser());
    }

    @Override
    @Column(nullable = false)
    public final User getCreateUser() {
        return createUser;
    }

    @Override
    public final void setChangeTime(Date aNewValue) {
        Date oldValue = getChangeTime();
        changeTime = aNewValue;
        fireChange(PROPERTY_CHANGE_TIME, oldValue, getChangeTime());
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    public final Date getChangeTime() {
        return changeTime;
    }

    @Override
    public final void setChangeUser(User aNewValue) {
        User oldValue = getChangeUser();
        changeUser = aNewValue;
        fireChange(PROPERTY_CHANGE_USER, oldValue, getChangeUser());
    }

    @Override
    public final User getChangeUser() {
        return changeUser;
    }

    // -------------------------------------------------------------------------
    // Heler
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId()
                                    + ", createTime=" + getCreateTime()
                                    + ", createUser=" + getCreateUser()
                                    + ", changeTime=" + getChangeTime()
                                    + ", changeUser=" + getChangeUser() + "]";
    }
}
