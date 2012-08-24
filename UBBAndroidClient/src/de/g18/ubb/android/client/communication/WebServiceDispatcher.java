package de.g18.ubb.android.client.communication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;
import de.g18.ubb.android.client.utils.AsyncTaskUtils;
import de.g18.ubb.common.service.exception.ServiceException;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class WebServiceDispatcher implements InvocationHandler {

    private final Object service;


    public WebServiceDispatcher(Object aService) {
        service = aService;
    }

    public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) throws Throwable {
        AsyncTask<Void, Void, Object> task = AsyncTaskUtils.dispatchExecution(new DispatchedMethodTask(aMethod, aArgs));
        try {
            return task.get();
        } catch (InterruptedException e) {
            Log.e(getClass().getSimpleName(), "Async Service Call was interrupted!", e);
            throw new IllegalStateException("Async Service Call was interrupted!", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof DelegatingServiceException) {
                throw e.getCause();
            }
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
                if (e != null && e.getTargetException() instanceof ServiceException) {
                    throw new DelegatingServiceException((ServiceException) e.getTargetException());
                }
                throw new IllegalStateException("Service has throwed a Exception!", e.getTargetException());
            } catch (Exception e) {
                throw new IllegalStateException("Exception while calling Service!", e);
            }
        }
    }

    private static final class DelegatingServiceException extends RuntimeException {

        private static final long serialVersionUID = 1L;


        public DelegatingServiceException(ServiceException aServiceException) {
            super(aServiceException);
        }
    }
}
