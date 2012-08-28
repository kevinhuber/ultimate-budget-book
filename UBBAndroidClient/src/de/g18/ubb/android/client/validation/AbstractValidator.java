package de.g18.ubb.android.client.validation;

import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidator<_Model extends AbstractModel> {

    private _Model model;
    private String validationResult;


    public AbstractValidator(_Model aModel) {
        model = aModel;
    }

    public final String validate() {
        validationResult = computeValidationResult();
        return getValidationResult();
    }

    protected abstract String computeValidationResult();

    public final boolean hasErrors() {
        return StringUtil.isNotEmpty(validationResult);
    }

    public final String getValidationResult() {
        return validationResult;
    }

    public final _Model getModel() {
        return model;
    }
}
