package de.g18.ubb.android.client.validation;

import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidator<_Bean extends AbstractModel,
                                        _Model extends PresentationModel<_Bean>> implements Validator<_Bean, _Model> {

    private _Model model;
    private String validationResult;


    public AbstractValidator(_Model aModel) {
        model = aModel;
    }

    public final void validate() {
        validationResult = computeValidationResult();
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
}
