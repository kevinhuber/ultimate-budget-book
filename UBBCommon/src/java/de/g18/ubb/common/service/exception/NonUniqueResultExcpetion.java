package de.g18.ubb.common.service.exception;

import de.g18.ubb.common.domain.Identifiable;
import de.g18.ubb.common.util.StringUtil;

/**
 * TODO (huber): Entfernen / Unbenutzt
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class NonUniqueResultExcpetion extends ServiceException {

    private static final long serialVersionUID = 1L;


    public NonUniqueResultExcpetion(Class<? extends Identifiable> aEntityClass, Object aSearchKey) {
        super(createMessage(aEntityClass, aSearchKey));
    }

    public NonUniqueResultExcpetion(Class<? extends Identifiable> aEntityClass, Object aSearchKey, Throwable aCause) {
        super(createMessage(aEntityClass, aSearchKey), aCause);
    }

    private static String createMessage(Class<? extends Identifiable> aEntityClass, Object aSearchKey) {
        return createMessage(aEntityClass, StringUtil.toString(aSearchKey));
    }

    private static String createMessage(Class<? extends Identifiable> aEntityClass, String aSearchKey) {
        String entityName = aEntityClass.getSimpleName();
        return "No " + entityName + " has been found for key '" + aSearchKey + "'!";
    }
}
