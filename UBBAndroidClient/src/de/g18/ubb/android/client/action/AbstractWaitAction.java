package de.g18.ubb.android.client.action;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Erweitert die {@link AbstractWaitTask}, sodass sie an einen Button oder ähnliches angebunden werden kann.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractWaitAction extends AbstractWaitTask implements OnClickListener {

    public AbstractWaitAction(Context aContext, String aDetailMessage) {
        super(aContext, aDetailMessage);
    }

    public final void onClick(View aView) {
        run();
    }
}
