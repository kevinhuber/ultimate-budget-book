package de.g18.ubb.android.client.validation;

import java.util.regex.Pattern;

import de.g18.ubb.android.client.resource.CommonValidationResource;
import de.g18.ubb.common.resource.Resource;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ValidationUtil {

    public static final String EMAIL_ADDRESS_VALIDATION_PATTERN = "^\\w+@\\w+\\.\\w+$";


    private ValidationUtil() {
        // prevent instantiation...
    }

    public static boolean isValidEMail(String eMail) {
        return Pattern.matches(EMAIL_ADDRESS_VALIDATION_PATTERN, eMail);
    }

    public static String createEmptyMessage() {
        return StringUtil.EMPTY;
    }

    public static String createMustNotBeEmptyMessage(Resource aResource) {
        return CommonValidationResource.MUST_NOT_BE_EMPTY.formatted(aResource.formatted());
    }

    public static String createInvalidEMailFormatMessage(String aEMailAddress) {
        return CommonValidationResource.INVALID_EMAIL_FORMAT.formatted(aEMailAddress);
    }
}
