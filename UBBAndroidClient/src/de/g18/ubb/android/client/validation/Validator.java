package de.g18.ubb.android.client.validation;

import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public interface Validator<_Bean extends AbstractModel, _Model extends PresentationModel<_Bean>> {

    void validate();

    boolean hasErrors();

    String getValidationResult();

    _Model getModel();

    String computeValidationResult();
}
