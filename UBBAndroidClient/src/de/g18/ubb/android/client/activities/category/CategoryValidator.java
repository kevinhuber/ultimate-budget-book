package de.g18.ubb.android.client.activities.category;

import java.util.List;

import de.g18.ubb.android.client.shared.ApplicationStateStore;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class CategoryValidator extends AbstractValidator<Category, PresentationModel<Category>> {

    public CategoryValidator(PresentationModel<Category> aModel) {
        super(aModel);
    }

    public String computeValidationResult() {
        String categoryName = (String) getModel().getValue(Category.PROPERTY_NAME);
        if (StringUtil.isEmpty(categoryName)) {
            return ValidationUtil.createMustNotBeEmptyMessage(CategoryResource.FIELD_NAME);
        }
        if (isCategoryNameAlreadyUsed(categoryName)) {
            return CategoryResource.VALIDATION_NAME_ALREADY_USED.formatted();
        }
        return StringUtil.EMPTY;
    }

    private boolean isCategoryNameAlreadyUsed(String aName) {
        List<Category> categories = ApplicationStateStore.getInstance().getBudgetBookModel().getBean().getCategories();
        for (Category c : categories) {
            if (ObjectUtil.equals(aName, c.getName())) {
                return true;
            }
        }
        return false;
    }
}
