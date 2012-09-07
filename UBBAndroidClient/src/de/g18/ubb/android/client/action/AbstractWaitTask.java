package de.g18.ubb.android.client.action;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import de.g18.ubb.android.client.utils.AsyncTaskUtils;

/**
 * Abstrakte Klasse, welche eine Aufgabe in einen seperaten Thread ausführt und während der Ausführung ein "WaitDialog"
 * anzeigt.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractWaitTask {

    private final Context context;
    private final String detailMessage;

    private ProgressDialog dialog;


    /**
     * Erstellt eine neue {@link AbstractWaitTask}.
     */
    public AbstractWaitTask(Context aContext, String aDetailMessage) {
        context = aContext;
        detailMessage = aDetailMessage;
    }

    /**
     * Führt die {@link AbstractWaitTask} aus und zeigt einen "Bitte Warten..." Dialog während der ausführung an.
     */
    public final void run() {
        dialog = ProgressDialog.show(context, "Bitte warten...", detailMessage, true, false);
        preExecute();
        AsyncTaskUtils.dispatchExecution(new ActionExecutor());
    }

    /**
     * Methode die in einem Seperaten Thread ausgeführt wird und so den GUI-Thread nicht unnötig belastet.
     *
     * Achtung: In dieser Methode dürfen keine GUI-Aktionen ausgeführt werden,
     * dies muss in {@link #preExecute()} und {@link #postExecute()} geschehen!
     */
    protected abstract void execute();

    /**
     * Wird vor dem ausführen der {@link #execute()}-Methode aufgerufen.
     */
    protected void preExecute() {
    }

    /**
     * Wird nach dem ausführen der {@link #execute()}-Methode aufgerufen.
     */
    protected void postExecute() {
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    /**
     * Klasse zum ausführen der {@link AbstractWaitTask#execute()}-Methode in einem seperaten Thread.
     */
    private final class ActionExecutor extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            AbstractWaitTask.this.execute();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            AbstractWaitTask.this.postExecute();
        }
    }
}
