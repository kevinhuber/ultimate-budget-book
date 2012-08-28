package de.g18.ubb.android.client.activities;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidationActivity<_Model extends AbstractModel,
                                                 _Validator extends AbstractValidator<_Model>> extends AbstractActivity {

    private _Model model;
    private _Validator validator;


    public final _Model getModel() {
        if (model == null) {
            model = createModel();
        }
        return model;
    }

    public final _Validator getValidator() {
        if (validator == null) {
            validator = createValidator();
        }
        return validator;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _Model createModel();

    protected abstract _Validator createValidator();
}
