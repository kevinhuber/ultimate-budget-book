package de.g18.ubb.common.service.exception;

import de.g18.ubb.common.service.UserService;

/**
 * Exception die vom {@link UserService} geworfen wird, sollte ein User über eine EMail nicht gefunden werden.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class UserWithEMailNotFound extends ServiceException {

    private static final long serialVersionUID = 2L;

    private String eMail;


    public UserWithEMailNotFound(String aEMail) {
        super(createMessage(aEMail));
        eMail = aEMail;
    }

    public UserWithEMailNotFound(String aEMail, Throwable aCause) {
        super(createMessage(aEMail), aCause);
        eMail = aEMail;
    }

    private static String createMessage(String aEMail) {
        return "No User has been found with email '" + aEMail + "'!";
    }

    public String getEMail() {
        return eMail;
    }
}
