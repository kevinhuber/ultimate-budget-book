package de.g18.ubb.android.client.activities.category;

import java.util.List;

import de.g18.ubb.android.client.shared.ApplicationStateStore;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class CategoryValidator extends AbstractValidator<Category> {

    public CategoryValidator(Category aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        if (StringUtil.isEmpty(getModel().getName())) {
            return ValidationUtil.createMustNotBeEmptyMessage(CategoryResource.FIELD_NAME);
        }
        if (isCategoryNameAlreadyUsed(getModel().getName())) {
            return CategoryResource.VALIDATION_NAME_ALREADY_USED.formatted();
        }
        return StringUtil.EMPTY;
    }

    private boolean isCategoryNameAlreadyUsed(String aName) {
        List<Category> categories = ApplicationStateStore.getInstance().getBudgetBook().getCategories();
        for (Category c : categories) {
            if (ObjectUtil.equals(aName, c.getName())) {
                return true;
            }
        }
        return false;
    }
}
