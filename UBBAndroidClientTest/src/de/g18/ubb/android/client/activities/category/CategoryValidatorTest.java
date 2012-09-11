package de.g18.ubb.android.client.activities.category;

import java.util.Arrays;

import de.g18.ubb.android.client.shared.ApplicationStateStore;
import de.g18.ubb.android.client.validation.AbstractValidatorTestCase;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class CategoryValidatorTest extends AbstractValidatorTestCase<Category, CategoryValidator> {

    private static final String ALREAD_USED_NAME = "alreadyUsed";


    @Override
    protected void setUpTestCase() throws Exception {
        Category c = new Category();
        c.setName(ALREAD_USED_NAME);

        BudgetBook bb = new BudgetBook();
        bb.setCategories(Arrays.asList(c));
        ApplicationStateStore.getInstance().setBudgetBook(bb);
    }

    @Override
    protected Category createValidModel() {
        Category c = new Category();
        c.setName("name");
        return c;
    }

    @Override
    protected CategoryValidator createValidator() {
        return new CategoryValidator(getModel());
    }

    public void testNameNotEmpty() {
        getModel().setName(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(CategoryResource.FIELD_NAME);
    }

    public void testNameAlreadyUsed() {
        getModel().setName(ALREAD_USED_NAME);
        assertValidationResult(CategoryResource.VALIDATION_NAME_ALREADY_USED);
    }
}
