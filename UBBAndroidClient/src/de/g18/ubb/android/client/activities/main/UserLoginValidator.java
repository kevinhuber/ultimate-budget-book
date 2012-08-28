package de.g18.ubb.android.client.activities.main;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.domain.UserLogin;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class UserLoginValidator extends AbstractValidator<UserLogin> {

    public UserLoginValidator(UserLogin aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        if (StringUtil.isEmpty(getModel().getEMail())) {
            return "EMail-Adresse darf nicht leer sein!";
        }
        if (StringUtil.isEmpty(getModel().getPassword())) {
            return "Passwort darf nicht leer sein!";
        }
        return StringUtil.EMPTY;
    }
}
