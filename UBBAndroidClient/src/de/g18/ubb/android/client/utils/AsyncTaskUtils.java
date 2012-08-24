package de.g18.ubb.android.client.utils;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class AsyncTaskUtils {

    private AsyncTaskUtils() {
        // prevent instantiation
    }


    public static <_Params, _Progress, _Result> AsyncTask<_Params, _Progress, _Result> dispatchExecution(AsyncTask<_Params, _Progress, _Result> aTask,
                                                                                                         _Params... aExecutionParameters) {
        if (Build.VERSION.SDK_INT >= 11) {
            return AsyncTaskCompatibilityExecutor.dispatchExecution(aTask, aExecutionParameters);
        }
        return aTask.execute(aExecutionParameters);
    }

    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    @TargetApi(11)
    private static final class AsyncTaskCompatibilityExecutor {

        public static <_Params, _Progress, _Result> AsyncTask<_Params, _Progress, _Result> dispatchExecution(AsyncTask<_Params, _Progress, _Result> aTask,
                                                                                                             _Params... aExecutionParameters) {
            return aTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, aExecutionParameters);
        }
    }
}
