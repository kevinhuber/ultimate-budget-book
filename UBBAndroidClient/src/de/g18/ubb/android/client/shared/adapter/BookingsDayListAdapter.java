package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.BookingsDayListAdapter.BookingTag;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;

public final class BookingsDayListAdapter extends AbstractAdapter<Booking, BookingTag> {

    private int mItemIndex = -1;

	public BookingsDayListAdapter(Context aContext) {
        this(aContext, new ArrayList<Booking>());
    }

    public BookingsDayListAdapter(Context aContext, List<Booking> aBooking) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = super.getView(position, convertView, parent);
        if (position == mItemIndex) {
            view.setSelected(true);
            view.setPressed(true);
            view.setBackgroundColor(Color.parseColor("#FF9912"));
        }

        return view;
    }

    public void setSelectItem(int index) {
        mItemIndex = index;
    }

    public int getSelectItem() {
        return mItemIndex;
    }

    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    static final class BookingTag {
        TextView name;
        TextView amount;
    }
}
