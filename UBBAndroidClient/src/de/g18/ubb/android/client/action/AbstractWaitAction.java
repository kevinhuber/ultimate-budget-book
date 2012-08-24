package de.g18.ubb.android.client.action;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractWaitAction implements OnClickListener {

    private final Context context;
    private final String detailMessage;

    private ProgressDialog dialog;


    public AbstractWaitAction(Context aContext, String aDetailMessage) {
        context = aContext;
        detailMessage = aDetailMessage;
    }

    public final void onClick(View aView) {
        dialog = ProgressDialog.show(context, "Bitte warten...", detailMessage, true, false);
        preExecute();
        new ActionExecutor().execute();
    }

    /**
     * Achtung: In dieser Methode dürfen keine GUI-Aktionen ausgeführt werden,
     * dies muss in {@link #preExecute()} und {@link #postExecute()} geschehen!
     */
    protected abstract void execute();

    protected void preExecute() {
    }

    protected void postExecute() {
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class ActionExecutor extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            AbstractWaitAction.this.execute();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            AbstractWaitAction.this.postExecute();
        }
    }
}
