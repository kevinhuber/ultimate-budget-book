package de.g18.ubb.android.client.shared.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.g18.ubb.android.client.R;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class EnumAdapter<E extends Enum<E>> extends ArrayAdapter<E> {

    private static final int LAYOUT_ID = R.layout.booking_type_row;


    public EnumAdapter(Context aContext) {
        super(aContext, LAYOUT_ID);
    }

    public EnumAdapter(Context aContext, List<E> aEnums) {
        super(aContext, LAYOUT_ID, aEnums);
    }

    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
        EnumHolder holder = null;

        if (aConvertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            aConvertView = inflater.inflate(LAYOUT_ID, aParent, false);

            holder = new EnumHolder();
            holder.name = (TextView) aConvertView.findViewById(R.BookingTypeRowLayout.name);

            aConvertView.setTag(holder);
        } else {
            holder = (EnumHolder) aConvertView.getTag();
        }

        Enum<?> e = getItem(aPosition);
        holder.name.setText(e.name());

        return aConvertView;
    }

    private static final class EnumHolder {
        TextView name;
    }
}
