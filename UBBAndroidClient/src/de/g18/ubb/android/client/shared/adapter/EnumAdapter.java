package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.EnumAdapter.EnumTag;
import de.g18.ubb.common.resource.Resource;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class EnumAdapter<_E extends Enum<_E>> extends AbstractAdapter<_E, EnumTag> {

    public EnumAdapter(Context aContext) {
        this(aContext, new ArrayList<_E>());
    }

    public EnumAdapter(Context aContext, Class<_E> aEnumClass) {
        this(aContext, aEnumClass.getEnumConstants());
    }

    public EnumAdapter(Context aContext, _E... aEnums) {
        this(aContext, Arrays.asList(aEnums));
    }

    public EnumAdapter(Context aContext, List<_E> aEnums) {
        super(aContext, R.layout.enum_row, aEnums);
        setDropDownViewResource(R.layout.enum_dropdown_row);
    }

    @Override
    protected EnumTag createTag(View aConvertView) {
        EnumTag tag = new EnumTag();
        tag.name = (TextView) aConvertView.findViewById(R.EnumLayout.name);
        return tag;
    }

    @Override
    protected void updateTag(EnumTag aTag, _E aEntry) {
        String text;
        if (aEntry instanceof Resource) {
            text = ((Resource) aEntry).formatted();
        } else {
            text = aEntry.name();
        }
        aTag.name.setText(text);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    static final class EnumTag {
        TextView name;
    }
}
