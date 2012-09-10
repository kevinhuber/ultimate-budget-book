package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractAdapter<_EntryType, _TagType> extends ArrayAdapter<_EntryType> {

    private final int layoutId;


    public AbstractAdapter(Context aContext, int aLayoutId) {
        this(aContext, aLayoutId, new ArrayList<_EntryType>());
    }

    public AbstractAdapter(Context aContext, int aLayoutId, List<_EntryType> aObjects) {
        super(aContext, aLayoutId, aObjects);
        layoutId = aLayoutId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
        if (aConvertView == null) {
            aConvertView = createConvertView(aParent);
            _TagType tag = createTag(aConvertView);
            aConvertView.setTag(tag);
        }
        _EntryType entry = getItem(aPosition);
        updateTag((_TagType) aConvertView.getTag(), entry);

        return aConvertView;
    }

    private View createConvertView(ViewGroup aParent) {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        return inflater.inflate(layoutId, aParent, false);
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _TagType createTag(View aConvertView);

    protected abstract void updateTag(_TagType aTag, _EntryType aEntry);
}
