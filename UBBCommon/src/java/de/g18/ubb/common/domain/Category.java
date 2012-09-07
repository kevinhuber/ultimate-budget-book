package de.g18.ubb.common.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Entität für Kategorien.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Entity
public final class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_BUDGET_BOOK = "budgetBook";

    private String name;
    private BudgetBook budgetBook;


    public Category() {
    }

    public void setName(String aNewValue) {
        String oldValue = getName();
        name = aNewValue;
        fireChange(PROPERTY_NAME, oldValue, getName());
    }

    @Column(length = 120, nullable = false)
    public String getName() {
        return name;
    }

    public void setBudgetBook(BudgetBook aNewValue) {
        BudgetBook oldValue = getBudgetBook();
        budgetBook = aNewValue;
        fireChange(PROPERTY_BUDGET_BOOK, oldValue, getBudgetBook());
    }

    @ManyToOne(cascade = CascadeType.REFRESH,
                 fetch = FetchType.EAGER,
              optional = false)
    public BudgetBook getBudgetBook() {
        return budgetBook;
    }

    // -------------------------------------------------------------------------
    // Heler
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId()
                                    + ", name=" + getName()
                                    + ", budgetBook=" + getBudgetBook() + "]";
    }
}
