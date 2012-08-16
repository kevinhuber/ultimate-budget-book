package de.g18.ubb.common.domain;

import javax.persistence.Entity;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Entity
public final class TestEntity extends AbstractEntity {

    public static final String PROPERTY_TEXT = "text";

    private String text;


    public TestEntity() {
    }

    public void setText(String aNewValue) {
        String oldValue = getText();
        text = aNewValue;
        fireChange(PROPERTY_TEXT, oldValue, getText());
    }

    public String getText() {
        return text;
    }
}
