package de.g18.ubb.android.client.shared.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.EnumAdapter.EnumTag;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class EnumAdapter<_E extends Enum<_E>> extends AbstractAdapter<_E, EnumTag> {

    public EnumAdapter(Context aContext) {
        super(aContext, R.layout.booking_type_row);
    }

    public EnumAdapter(Context aContext, List<_E> aEnums) {
        super(aContext, R.layout.booking_type_row, aEnums);
    }

    @Override
    protected EnumTag createTag(View aConvertView) {
        EnumTag tag = new EnumTag();
        tag.name = (TextView) aConvertView.findViewById(R.BookingTypeRowLayout.name);
        return tag;
    }

    @Override
    protected void updateTag(EnumTag aTag, _E aEntry) {
        aTag.name.setText(aEntry.name());
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    static final class EnumTag {
        TextView name;
    }
}
