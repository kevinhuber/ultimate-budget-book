package de.g18.ubb.android.client.activities.register;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class RegisterValidator extends AbstractValidator<RegisterModel> {

    public RegisterValidator(RegisterModel aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        if (StringUtil.isEmpty(getModel().getUsername())) {
            return "Benutzername darf nicht leer sein!";
        }
        if (StringUtil.isEmpty(getModel().getEMail())) {
            return "EMail darf nicht leer sein!";
        }
        if (StringUtil.isEmpty(getModel().getPassword())) {
            return "Passwort darf nicht leer sein!";
        }
        if (StringUtil.isEmpty(getModel().getPasswordCheck())) {
            return "Passwort-Wiederholung darf nicht leer sein!";
        }
        if (!ObjectUtil.equals(getModel().getPassword(), getModel().getPasswordCheck())) {
            return "Passwörter stimmen nicht überein!";
        }
        if (ServiceRepository.getUserService().existsUserWithEMail(getModel().getEMail())) {
            return "EMail wird bereits verwendet!";
        }
        return StringUtil.EMPTY;
    }
}
