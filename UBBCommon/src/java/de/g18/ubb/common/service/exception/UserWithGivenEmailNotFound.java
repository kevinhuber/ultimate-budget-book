package de.g18.ubb.common.service.exception;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class UserWithGivenEmailNotFound extends ServiceException {

    private static final long serialVersionUID = 1L;

    public UserWithGivenEmailNotFound(String aEMail) {
        super(createMessage(aEMail));
    }

    public UserWithGivenEmailNotFound(String aEMail, Throwable aCause) {
        super(createMessage(aEMail), aCause);
    }

    private static String createMessage(String aEMail) {
        return "No User has been found with email '" + aEMail + "'!";
    }
}
