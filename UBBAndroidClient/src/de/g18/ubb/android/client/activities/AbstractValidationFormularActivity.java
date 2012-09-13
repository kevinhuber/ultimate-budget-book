package de.g18.ubb.android.client.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import de.g18.ubb.android.client.action.AbstractWaitAction;
import de.g18.ubb.android.client.validation.Validator;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * Abstrakte Oberklasse für Activities, welche ein Model an ein Formular binden und die Eingaben vor dem "abschicken"
 * validieren.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractValidationFormularActivity<_Model extends AbstractModel, _Validator extends Validator<_Model>>
                          extends AbstractValidationActivity<_Model, _Validator> {

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

    /**
     * True, falls keine Fehler beim ausführen von {@link #submit()} aufgetreten sind.
     */
    protected final boolean isSubmitSuccessfull() {
        return StringUtil.isEmpty(getSubmitErrorMessage());
    }

    /**
     * Gibt den String, der von {@link #submit()} zurückgegeben wurde zurück.
     */
    protected final String getSubmitErrorMessage() {
        return submitErrorMessage;
    }

    /**
     * Kann überschrieben werden, um Aktionen vor dem ausführen von {@link #submit()} durchzuführen.
     */
    protected void preSubmit() {
    }

    /**
     * Kann überschrieben werden, um Aktionen nach dem ausführen von {@link #submit()} durchzuführen.
     */
    protected void postSubmit() {
        showValidationMessages();
        if (isSubmitSuccessfull()) {
            finish();
        }
    }

    /**
     * Zeigt Validierungs- oder Fehlermeldungen in einem {@link Toast} an, falls es welche geben sollte.
     */
    protected void showValidationMessages() {
        if (StringUtil.isNotEmpty(getSubmitErrorMessage())) {
            Toast.makeText(getApplicationContext(), getSubmitErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Gibt den {@link Button} zurück, welcher an {@link #submit()} gekoppelt ist.
     */
    protected final Button getSubmitButton() {
        if (submitButton == null) {
            submitButton = (Button) findViewById(getSubmitButtonId());
        }
        return submitButton;
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    /**
     * Gibt die ID des {@link Button}s zurück, welcher zum "absenden" verwendet werden soll.
     */
    protected abstract int getSubmitButtonId();

    /**
     * Methode, die nach dem drücken des "absenden"-{@link Button}s in einer {@link AsyncTask} ausgeführt wird.
     */
    protected abstract String submit();

    /**
     * Gibt den Text zurück, der wärend der ausführung von {@link #submit()} angezeigt wird.
     */
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
