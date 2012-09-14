package de.g18.ubb.android.client.validation;

import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * Interface für die Validierung eines {@link AbstractModel}s.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Validator<_Model extends AbstractModel> {

    /**
     * Führt eine Validierung auf das {@link AbstractModel} aus und gibt das Ergebnis der Validierung
     * als {@link String} zurück.
     */
    String validate();

    /**
     * Gibt das Ergebnis der Validierung zurück.
     */
    String getValidationResult();

    /**
     * Gibt das {@link AbstractModel} zurück, dass in diesem Validator validiert wird.
     */
    _Model getModel();

    /**
     * True, falls {@link #getValidationResult()} nicht {@link StringUtil#EMPTY} zurück gibt.
     */
    boolean hasErrors();
}
