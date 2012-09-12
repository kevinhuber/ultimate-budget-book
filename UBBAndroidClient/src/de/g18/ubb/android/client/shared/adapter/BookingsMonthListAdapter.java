package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.BookingsMonthListAdapter.BookingTag;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;

public final class BookingsMonthListAdapter extends AbstractAdapter<Booking, BookingTag> {

	public BookingsMonthListAdapter(Context aContext) {
        this(aContext, new ArrayList<Booking>());
    }

    public BookingsMonthListAdapter(Context aContext, List<Booking> aBooking) {
        super(aContext, R.layout.budgetbookbooking_row, aBooking);
    }

    @Override
    protected BookingTag createTag(View aConvertView) {
        BookingTag tag = new BookingTag();
        tag.name = (TextView) aConvertView.findViewById(R.BudgetBookBookingRowLayout.name);
        tag.amount = (TextView) aConvertView.findViewById(R.BudgetBookBookingRowLayout.amount);
        return tag;
    }

    @Override
    protected void updateTag(BookingTag aTag, Booking aEntry) {
    	// Wenn der Wert der Buchung negativ ist, wird dies entsprechend farbig hervorgehoben
    	if (aEntry.getAmount() < 1) {
			aTag.amount.setTextColor(Color.RED);
		} else {
			aTag.amount.setTextColor(Color.BLACK);
		}

        aTag.name.setText(aEntry.getBookingName());
        aTag.amount.setText(Float.toString(aEntry.getAmount()) + " " + UBBConstants.CURRENCY_EURO_SIGN);
    }

    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    static final class BookingTag {
        TextView name;
        TextView amount;
    }
}
