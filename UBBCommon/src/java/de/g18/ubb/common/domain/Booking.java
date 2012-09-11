package de.g18.ubb.common.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.g18.ubb.common.domain.enumType.BookingType;

/**
 * Entität für Buchungen, die einem Haushaltsbuch hinzugefügt werden können.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Entity
public final class Booking extends AbstractAuditEntity {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_TYPE = "type";
    public static final String PROPERTY_AMOUNT = "amount";
    public static final String PROPERTY_CATEGORY = "category";
    public static final String PROPERTY_BOOKING_TIME = "bookingTime";
    public static final String PROPERTY_BOOKING_NAME = "bookingName";

    private BookingType type;
    private float amount;
    private Category category;
    private Date bookingTime;
    private String bookingName;


    public Booking() {
    }

    public void setType(BookingType aNewValue) {
        BookingType oldValue = getType();
        type = aNewValue;
        fireChange(PROPERTY_TYPE, oldValue, getType());
    }

    @Enumerated(EnumType.STRING)
    @Column(length = BookingType.MAX_ENTRY_LENGTH)
    public BookingType getType() {
        return type;
    }

    public void setAmount(float aNewValue) {
        float oldValue = getAmount();
        amount = aNewValue;
        fireChange(PROPERTY_AMOUNT, oldValue, getAmount());
    }

    public float getAmount() {
        return amount;
    }

    public void setCategory(Category aNewValue) {
        Category oldValue = getCategory();
        category = aNewValue;
        fireChange(PROPERTY_CATEGORY, oldValue, getCategory());
    }

    @ManyToOne(cascade = CascadeType.REFRESH,
                 fetch = FetchType.EAGER)
    public Category getCategory() {
        return category;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public void setBookingTime(Date aNewValue) {
        Date oldValue = getBookingTime();
        bookingTime = aNewValue;
        fireChange(PROPERTY_BOOKING_TIME, oldValue, getBookingTime());
    }

    public Date getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingName(String aNewValue) {
        String oldValue = getBookingName();
        bookingName = aNewValue;
        fireChange(PROPERTY_BOOKING_NAME, oldValue, getBookingName());
    }

    public String getBookingName() {
        return bookingName;
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId()
                                    + ", createTime=" + getCreateTime()
                                    + ", createUser=" + getCreateUser()
                                    + ", changeTime=" + getChangeTime()
                                    + ", changeUser=" + getChangeUser()
                                    + ", type=" + getType()
                                    + ", amount=" + getAmount()
                                    + ", category=" + getCategory()
                                    + ", bookingTime=" + getBookingTime() + "]";
    }
}
