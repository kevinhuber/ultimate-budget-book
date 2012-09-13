package de.g18.ubb.common.service.repository;

import de.g18.ubb.common.service.BookingService;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.CategoryService;
import de.g18.ubb.common.service.UserService;

/**
 * Service-Sammel-Klasse, die durch einen {@link ServiceProvider} den Zugriff auf Services auf einem Server ermöglicht.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ServiceRepository {

    /**
     * {@link ServiceProvider} der genutzt wird um Services aufzulösen.
     */
    private static ServiceProvider serviceProvider;


    private ServiceRepository() {
        // prevent instantiation
    }

    /**
     * Gibt eine Instanz des {@link BookingService} zurück.
     */
    public static BookingService getBookingService() {
        return lookup(BookingService.class);
    }

    /**
     * Gibt eine Instanz des {@link BudgetBookService} zurück.
     */
    public static BudgetBookService getBudgetBookService() {
        return lookup(BudgetBookService.class);
    }

    /**
     * Gibt eine Instanz des {@link CategoryService} zurück.
     */
    public static CategoryService getCategoryService() {
        return lookup(CategoryService.class);
    }

    /**
     * Gibt eine Instanz des {@link UserService} zurück.
     */
    public static UserService getUserService() {
        return lookup(UserService.class);
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    /**
     * Löst die übergebene Service-Klasse über den {@link ServiceProvider} auf.
     */
    private static <_Service> _Service lookup(Class<_Service> aServiceClass) {
        if (serviceProvider == null) {
            throw new IllegalStateException("No ServiceProvider set!");
        }
        return serviceProvider.lookup(aServiceClass);
    }

    /**
     * Registriert den übergebenen {@link ServiceProvider} zum auflösen von Service-Klassen.
     * Diese Methode kann nur einmal während der Laufzeit aufgerufen werden.
     * Mehrmaliges Aufrufen dieser Methode führt zu einer {@link IllegalStateException}!
     */
    public static void setProvider(ServiceProvider aProvider) {
        if (serviceProvider != null) {
            throw new IllegalStateException("ServiceProvider is already set! " + serviceProvider);
        }
        serviceProvider = aProvider;
    }
}
