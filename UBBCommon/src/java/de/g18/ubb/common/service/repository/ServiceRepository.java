package de.g18.ubb.common.service.repository;

import de.g18.ubb.common.service.BookingService;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.CategoryService;
import de.g18.ubb.common.service.UserService;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class ServiceRepository {

    private static ServiceProvider serviceProvider;


    public static BookingService getBookingService() {
        return lookup(BookingService.class);
    }

    public static BudgetBookService getBudgetBookService() {
        return lookup(BudgetBookService.class);
    }

    public static CategoryService getCategoryService() {
        return lookup(CategoryService.class);
    }

    public static UserService getUserService() {
        return lookup(UserService.class);
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private static <_Service> _Service lookup(Class<_Service> aServiceClass) {
        if (serviceProvider == null) {
            throw new IllegalStateException("No ServiceProvider set!");
        }
        return serviceProvider.lookup(aServiceClass);
    }

    public static void setProvider(ServiceProvider aProvider) {
        if (serviceProvider != null) {
            throw new IllegalStateException("Provider is already set! " + serviceProvider);
        }
        serviceProvider = aProvider;
    }
}
