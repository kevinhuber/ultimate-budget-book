package de.g18.ubb.android.client.validation;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Validator<_Model extends AbstractModel> {

    String validate();

    String getValidationResult();

    _Model getModel();

    boolean hasErrors();
}
