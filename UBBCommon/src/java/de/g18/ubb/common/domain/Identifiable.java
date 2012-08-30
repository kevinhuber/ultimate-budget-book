package de.g18.ubb.common.domain;

/**
 * Interface das alle Entitäten implementieren müssen.
 * Entitäten die diesen Interface implementieren sind dann über eine id als PK identifizierbar.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Identifiable {

    public static final String PROPERTY_ID = "id";

    void setId(Long aNewValue);
    Long getId();
}
