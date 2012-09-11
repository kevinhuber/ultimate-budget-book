package de.g18.ubb.android.client.activities.budgetbook;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

public class BudgetBookCreateNewValidator extends AbstractValidator<BudgetBookCreateNewModel> {

    public BudgetBookCreateNewValidator(BudgetBookCreateNewModel aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
    	if (StringUtil.isEmpty(getModel().getName())) {
			return ValidationUtil
					.createMustNotBeEmptyMessage(BudgetBookResource.PROPERTY_NAME);
		}
    	return ValidationUtil.createEmptyMessage();
    }
}
