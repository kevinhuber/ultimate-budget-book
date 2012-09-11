package de.g18.ubb.android.client.activities.category;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class CategoryChangeValidator extends AbstractValidator<Category> {

    public CategoryChangeValidator(Category aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        return StringUtil.EMPTY;
    }
}
