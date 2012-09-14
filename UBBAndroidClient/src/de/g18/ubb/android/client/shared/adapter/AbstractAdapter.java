package de.g18.ubb.android.client.shared.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Abstrakte implementierung des {@link ArrayAdapter}s, zum darstellen von listen in einer {@link AdapterView}.
 *
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

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
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

    @TargetApi(11)
    public void setData(List<_EntryType> data) {
        clear();
        if (data != null) {
            //If the platform supports it, use addAll, otherwise add in loop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                addAll(data);
            } else {
                for (_EntryType item : data) {
                    add(item);
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    /**
     * Erstellt das Tag, welches an die übergebene View gebunden wird.
     */
    protected abstract _TagType createTag(View aConvertView);

    /**
     * Aktualisiert die Werte des übergebenen Tag mit denen ausdem übergebenen Entry.
     */
    protected abstract void updateTag(_TagType aTag, _EntryType aEntry);
}
