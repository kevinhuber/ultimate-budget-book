package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.CategoryAdapter.CategoryTag;
import de.g18.ubb.common.domain.Category;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class CategoryAdapter extends AbstractAdapter<Category, CategoryTag> {

    public CategoryAdapter(Context aContext) {
        this(aContext, new ArrayList<Category>());
    }

    public CategoryAdapter(Context aContext, List<Category> aCategories) {
        super(aContext, R.layout.category_row, aCategories);
        setDropDownViewResource(R.layout.category_dropdown_row);
    }

    @Override
    protected CategoryTag createTag(View aConvertView) {
        CategoryTag tag = new CategoryTag();
        tag.name = (TextView) aConvertView.findViewById(R.CategoryRowLayout.name);
        return tag;
    }

    @Override
    protected void updateTag(CategoryTag aTag, Category aEntry) {
        aTag.name.setText(aEntry.getName());
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    static final class CategoryTag {
        TextView name;
    }
}
