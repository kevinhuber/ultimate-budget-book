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
import de.g18.ubb.common.domain.Category;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context aContext) {
        super(aContext, R.layout.category_row);
    }

    public CategoryAdapter(Context aContext, List<Category> aCategories) {
        super(aContext, R.layout.category_row, aCategories);
    }

    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
        CategoryHolder holder = null;

        if (aConvertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            aConvertView = inflater.inflate(R.layout.category_row, aParent, false);

            holder = new CategoryHolder();
            holder.name = (TextView) aConvertView.findViewById(R.CategoryRowLayout.name);

            aConvertView.setTag(holder);
        } else {
            holder = (CategoryHolder) aConvertView.getTag();
        }

        Category category = getItem(aPosition);
        holder.name.setText(category.getName());

        return aConvertView;
    }

    private static final class CategoryHolder {
        TextView name;
    }
}
