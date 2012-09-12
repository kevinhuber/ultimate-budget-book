package de.g18.ubb.android.client.activities.budgetbook;

import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BudgetBookCreateNewValidator extends AbstractValidator<BudgetBookCreateNewModel,
                                                                    PresentationModel<BudgetBookCreateNewModel>> {

    public BudgetBookCreateNewValidator(PresentationModel<BudgetBookCreateNewModel> aModel) {
        super(aModel);
    }

    public String computeValidationResult() {
        return StringUtil.EMPTY;
    }
}
