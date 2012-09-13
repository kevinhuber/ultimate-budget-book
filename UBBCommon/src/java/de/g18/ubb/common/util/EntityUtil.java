package de.g18.ubb.common.util;

import de.g18.ubb.common.domain.Identifiable;

/**
 * Util-Klasse für {@link Identifiable}-Objekte.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class EntityUtil {

    private EntityUtil() {
        // prevent instantiation
    }

    /**
     * Null-Safe Prüfung ob die "id" der übergebenen Entität leer ist.
     * @return true, falls die "id" der Entität nicht gesetzt ist
     */
    public static boolean isNotPersistent(Identifiable aEntity) {
        return !isPersistent(aEntity);
    }

    /**
     * Null-Safe Prüfung ob die "id" der übergebenen Entität gesetzt ist.
     * @return true, falls die "id" der Entität gesetzt ist
     */
    public static boolean isPersistent(Identifiable aEntity) {
        return aEntity != null && aEntity.getId() != null;
    }
}
