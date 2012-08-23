package de.g18.ubb.android.client.communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class WebServiceDispatcher implements java.lang.reflect.InvocationHandler {

    private final Object service;

    public WebServiceDispatcher(Object aService) {
        service = aService;
    }

    public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) throws Throwable {
        AsyncTask<Void, Void, Object> dispatchedInvoker = new DispatchedMethodTask(aMethod, aArgs).execute();
        try {
            return dispatchedInvoker.get();
        } catch (InterruptedException e) {
            Log.e(getClass().getSimpleName(), "Async Service Call was interrupted!", e);
            throw new IllegalStateException("Async Service Call was interrupted!", e);
        } catch (ExecutionException e) {
            Log.e(getClass().getSimpleName(), "Async Service Call failed!", e);
            throw new IllegalStateException("Async Service Call failed!", e);
        }
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class DispatchedMethodTask extends AsyncTask<Void, Void, Object> {

        private final Method methodToInvoke;
        private final Object[] invocationArgs;


        public DispatchedMethodTask(Method aMethodToInvoke, Object[] aInvocationArgs) {
            methodToInvoke = aMethodToInvoke;
            invocationArgs = aInvocationArgs;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                return methodToInvoke.invoke(service, invocationArgs);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Service has throwed a Exception!", e.getTargetException());
            } catch (Exception e) {
                throw new IllegalStateException("Exception while calling Service!", e);
            }
        }
    }
}
