package de.g18.ubb.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Entität für Kategorien.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Entity
public final class Category extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_NAME = "name";

    private String name;


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

    // -------------------------------------------------------------------------
    // Heler
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return getClass().getName() + "[id=" + getId()
                                    + ", name=" + getName() + "]";
    }
}
