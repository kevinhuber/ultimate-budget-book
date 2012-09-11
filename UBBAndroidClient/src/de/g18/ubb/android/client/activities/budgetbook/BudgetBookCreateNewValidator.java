package de.g18.ubb.android.client.activities.budgetbook;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BudgetBookCreateNewValidator extends AbstractValidator<BudgetBookCreateNewModel> {

    public BudgetBookCreateNewValidator(BudgetBookCreateNewModel aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        return StringUtil.EMPTY;
    }
}
