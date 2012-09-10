package de.g18.ubb.android.client.shared.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.BookingsAdapter.BookingTag;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;

public final class BookingsAdapter extends AbstractAdapter<Booking, BookingTag> {

    public BookingsAdapter(Context aContext) {
        super(aContext, R.layout.budgetbookbooking_row);
    }

    public BookingsAdapter(Context aContext, List<Booking> aBooking) {
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
        aTag.name.setText(aEntry.getCreateUser().getName());
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
