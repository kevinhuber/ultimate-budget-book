package de.g18.ubb.common.domain;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Identifiable {

    public static final String PROPERTY_ID = "id";

    void setId(Long aNewValue);
    Long getId();
}
