package de.g18.ubb.android.client.validation;

import junit.framework.Assert;
import junit.framework.TestCase;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.resource.Resource;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidatorTestCase<_Model extends AbstractModel,
                                                _Validator extends AbstractValidator<_Model>> extends TestCase {

    private _Model model;
    private _Validator validator;


    @Override
    protected final void setUp() throws Exception {
        model = createValidModel();
        validator = createValidator();
        setUpTestCase();
    }

    protected void setUpTestCase() throws Exception {
    }

    public final _Model getModel() {
        return model;
    }

    public final _Validator getValidator() {
        return validator;
    }

    public final void testValidSetUp() {
        assertValidationSuccessfull();
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    protected final void assertValidationSuccessfull() {
        getValidator().validate();
        Assert.assertEquals(StringUtil.EMPTY, getValidator().getValidationResult());
    }

    protected final void assertValidationMustNotBeEmpty(Resource aResource) {
        assertValidationResult(ValidationUtil.createMustNotBeEmptyMessage(aResource));
    }

    protected final void assertValidationResult(Resource aResource) {
        assertValidationResult(aResource.formatted());
    }

    protected final void assertValidationResult(String aErrorMessage) {
        getValidator().validate();
        Assert.assertEquals(aErrorMessage, getValidator().getValidationResult());
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _Model createValidModel();

    protected abstract _Validator createValidator();
}
