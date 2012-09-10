package de.g18.ubb.android.client.activities.budgetbook;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.common.domain.Booking;

public final class BookingsAdapter extends ArrayAdapter<Booking> {

    public BookingsAdapter(Context aContext) {
        super(aContext, R.layout.budgetbookbooking_row);
    }

    public BookingsAdapter(Context aContext, List<Booking> aBooking) {
        super(aContext, R.layout.budgetbookbooking_row, aBooking);
    }

    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
    	BudgetBookBookingHolder holder = null;

        if (aConvertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            aConvertView = inflater.inflate(R.layout.budgetbookbooking_row, aParent, false);

            holder = new BudgetBookBookingHolder();
            holder.name = (TextView) aConvertView.findViewById(R.BudgetBookBookingRowLayout.name);
            holder.amount = (TextView) aConvertView.findViewById(R.BudgetBookBookingRowLayout.amount);

            aConvertView.setTag(holder);
        } else {
            holder = (BudgetBookBookingHolder) aConvertView.getTag();
        }

        Booking booking = getItem(aPosition);
        holder.name.setText(booking.getCreateUser().getName());
        holder.amount.setText(Float.toString(booking.getAmount()));

        return aConvertView;
    }

    private static final class BudgetBookBookingHolder {
        TextView name;
        TextView amount;
    }
}
