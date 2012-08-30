package de.g18.ubb.common.service.exception;

/**
 * Abstrakte Exception, die von Services geworfen wird.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;


    public ServiceException() {
    }

    public ServiceException(String aMessage) {
        super(aMessage);
    }

    public ServiceException(Throwable aCause) {
        super(aCause);
    }

    public ServiceException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }
}
