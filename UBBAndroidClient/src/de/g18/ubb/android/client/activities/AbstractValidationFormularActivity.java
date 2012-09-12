package de.g18.ubb.android.client.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import de.g18.ubb.android.client.action.AbstractWaitAction;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.validation.Validator;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidationFormularActivity<_Bean extends AbstractModel,
                                                         _Model extends PresentationModel<_Bean>,
                                                         _Validator extends Validator<_Bean, _Model>>
                          extends AbstractValidationActivity<_Bean, _Model, _Validator> {

    private Button submitButton;
    private String submitErrorMessage;


    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);

        initEventHandling();
    }

    private void initEventHandling() {
        getSubmitButton().setOnClickListener(new SubmitAction());
    }

    protected final boolean isSubmitSuccessfull() {
        return StringUtil.isEmpty(getSubmitErrorMessage());
    }

    protected final String getSubmitErrorMessage() {
        return submitErrorMessage;
    }

    protected void preSubmit() {
    }

    protected void postSubmit() {
        showValidationMessages();
        finish();
    }

    protected final void showValidationMessages() {
        if (isSubmitSuccessfull()) {
            return;
        }
        Toast.makeText(getApplicationContext(), getSubmitErrorMessage(), Toast.LENGTH_LONG).show();
    }

    protected final Button getSubmitButton() {
        if (submitButton == null) {
            submitButton = (Button) findViewById(getSubmitButtonId());
        }
        return submitButton;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract int getSubmitButtonId();

    protected abstract String submit();

    protected abstract String getSubmitWaitMessage();


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class SubmitAction extends AbstractWaitAction {

        public SubmitAction() {
            super(AbstractValidationFormularActivity.this, getSubmitWaitMessage());
        }

        @Override
        protected void preExecute() {
            preSubmit();
        }

        @Override
        protected void execute() {
            getValidator().validate();
            if (getValidator().hasErrors()) {
                submitErrorMessage = getValidator().getValidationResult();
                return;
            }
            submitErrorMessage = submit();
        }

        @Override
        protected void postExecute() {
            postSubmit();
        }
    }
}
