package de.g18.ubb.android.client.shared;

import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;

/**
 * Singelton für den Zugriff auf die in der GUI angezeigten Objekte.
 *
 * @author Daniel Fels
 */
public class ApplicationStateStore {

	private BudgetBook budgetBook;
	private Category category;
	private Booking booking;

	private static ApplicationStateStore instance;


	private ApplicationStateStore() {
	    // prevent instantiation
	}

	public static ApplicationStateStore getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	private static synchronized void createInstance() {
		if (instance != null) {
			return;
		}
		instance = new ApplicationStateStore();
	}

	/**
	 * Gibt das {@link BudgetBook} zurück, mit dem momentan gearbeitet wird.
	 */
	public BudgetBook getBudgetBook() {
		return budgetBook;
	}

	/**
	 * Setzt das {@link BudgetBook}, mit dem gearbeitet werden soll.
	 */
	public void setBudgetBook(BudgetBook aNewValue) {
		budgetBook = aNewValue;
	}

    /**
     * Gibt die {@link Category} zurück, mit der momentan gearbeitet wird.
     */
	public Category getCategory() {
		return category;
	}

    /**
     * Setzt die {@link Category}, mit der gearbeitet werden soll.
     */
	public void setCategory(Category aNewValue) {
		category = aNewValue;
	}

    /**
     * Gibt das {@link Booking} zurück, mit dem momentan gearbeitet wird.
     */
	public Booking getBooking() {
		return booking;
	}

    /**
     * Setzt das {@link Booking}, mit dem gearbeitet werden soll.
     */
	public void setBooking(Booking aNewValue) {
		booking = aNewValue;
	}
}
