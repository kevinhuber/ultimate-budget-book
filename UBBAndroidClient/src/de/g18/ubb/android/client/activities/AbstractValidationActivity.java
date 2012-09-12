package de.g18.ubb.android.client.activities;

import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.Validator;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidationActivity<_Bean extends AbstractModel,
                                                 _Model extends PresentationModel<_Bean>,
                                                 _Validator extends Validator<_Bean, _Model>> extends AbstractActivity<_Bean,
                                                                                                                       _Model> {

    private _Validator validator;


    public final _Validator getValidator() {
        if (validator == null) {
            validator = createValidator();
        }
        return validator;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _Validator createValidator();
}
