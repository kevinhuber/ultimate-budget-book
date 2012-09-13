package de.g18.ubb.android.client.activities;

import android.app.Activity;
import de.g18.ubb.android.client.validation.Validator;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * Abstrakte Oberklasse für Activities, die eine Validierung auf ein {@link AbstractModel} mit hilfe
 * eines {@link Validator}s ausführen.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidationActivity<_Model extends AbstractModel,
                                                 _Validator extends Validator<_Model>> extends AbstractActivity<_Model> {

    private _Validator validator;


    /**
     * Erstellt den {@link Validator}, der in dieser Activity verwendet werden soll.
     * Der {@link Validator} wird "Lazy" durch den aufruf von {@link #createValidator()} erstellt und zurückgegeben.
     */
    public final _Validator getValidator() {
        if (validator == null) {
            validator = createValidator();
        }
        return validator;
    }

    @Override
    protected void reset() {
        super.reset();
        resetValidator();
    }

    /**
     * Setzt den Validator zurück auf <code>null</code>
     */
    protected final void resetValidator() {
        validator = null;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    /**
     * Erstellt den {@link Validator}, welcher in dieser {@link Activity} verwendet werden soll.
     */
    protected abstract _Validator createValidator();
}
