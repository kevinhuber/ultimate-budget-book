package de.g18.ubb.android.client.validation;

import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * Abstrakte implementierung der {@link Validator}-Interfaces.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidator<_Model extends AbstractModel> implements Validator<_Model> {

    private _Model model;
    private String validationResult;


    public AbstractValidator(_Model aModel) {
        model = aModel;
    }

    public final String validate() {
        validationResult = computeValidationResult();
        return getValidationResult();
    }

    public final boolean hasErrors() {
        return StringUtil.isNotEmpty(validationResult);
    }

    public final String getValidationResult() {
        return validationResult;
    }

    public final _Model getModel() {
        return model;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract String computeValidationResult();
}
