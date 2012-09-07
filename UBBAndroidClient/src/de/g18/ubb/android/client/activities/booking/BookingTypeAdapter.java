package de.g18.ubb.android.client.activities.booking;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.common.domain.enumType.BookingType;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BookingTypeAdapter extends ArrayAdapter<BookingType> {

    public BookingTypeAdapter(Context aContext) {
        super(aContext, R.layout.booking_type_row);
    }

    public BookingTypeAdapter(Context aContext, List<BookingType> aCategories) {
        super(aContext, R.layout.booking_type_row, aCategories);
    }

    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
        BookingTypeHolder holder = null;

        if (aConvertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            aConvertView = inflater.inflate(R.layout.booking_type_row, aParent, false);

            holder = new BookingTypeHolder();
            holder.name = (TextView) aConvertView.findViewById(R.BookingTypeRowLayout.name);

            aConvertView.setTag(holder);
        } else {
            holder = (BookingTypeHolder) aConvertView.getTag();
        }

        BookingType bookingType = getItem(aPosition);
        holder.name.setText(bookingType.name());

        return aConvertView;
    }

    private static final class BookingTypeHolder {
        TextView name;
    }
}
