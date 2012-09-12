package de.g18.ubb.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Entität für Haushaltsbücher.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Entity
public final class BudgetBook extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_CATEGORIES = "categories";
    public static final String PROPERTY_BOOKINGS = "bookings";
    public static final String PROPERTY_ASSIGNED_USER = "assignedUser";

    private String name;
    private List<Category> categories;
    private List<Booking> bookings;
    private List<UserExtract> assignedUser;


    public BudgetBook() {
    }

    public void setName(String aNewValue) {
        String oldValue = getName();
        name = aNewValue;
        fireChange(PROPERTY_NAME, oldValue, getName());
    }

    public String getName() {
        return name;
    }

    public void setCategories(List<Category> aNewValue) {
        categories = aNewValue;
        fireChange(PROPERTY_CATEGORIES, null, getCategories());
    }

    @OneToMany(cascade = CascadeType.REFRESH,
                 fetch = FetchType.LAZY)
    public List<Category> getCategories() {
        if (categories == null) {
            categories = new ArrayList<Category>();
        }
        return categories;
    }

    public void setBookings(List<Booking> aNewValue) {
        bookings = aNewValue;
        fireChange(PROPERTY_BOOKINGS, null, getBookings());
    }

    @OneToMany(cascade = CascadeType.REFRESH,
                 fetch = FetchType.LAZY)
    public List<Booking> getBookings() {
        if (bookings == null) {
            bookings = new ArrayList<Booking>();
        }
        return bookings;
    }

    @ManyToMany(cascade = CascadeType.REFRESH,
                  fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    public List<UserExtract> getAssignedUser() {
        if (assignedUser == null) {
            assignedUser = new ArrayList<UserExtract>();
        }
        return assignedUser;
    }

    public void setAssignedUser(List<UserExtract> aNewValue) {
        assignedUser = aNewValue;
        fireChange(PROPERTY_ASSIGNED_USER, null, getAssignedUser());
    }

    // -------------------------------------------------------------------------
    // Heler
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId()
                                    + ", name=" + getName()
                                    + ", bookings.size()=" + getBookings().size()
                                    + ", assignedUser.size()=" + getAssignedUser().size() + "]";
    }
}
