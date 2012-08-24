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
import de.g18.ubb.common.domain.BudgetBook;


/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BudgetBookAdapter extends ArrayAdapter<BudgetBook> {

    public BudgetBookAdapter(Context aContext, List<BudgetBook> aBooks) {
        super(aContext, R.layout.budgetbook_row, aBooks);
    }

    @Override
    public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
        BudgetBookHolder holder = null;

        if (aConvertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            aConvertView = inflater.inflate(R.layout.budgetbook_row, aParent, false);

            holder = new BudgetBookHolder();
            holder.name = (TextView) aConvertView.findViewById(R.BudgetBookRowLayout.name);
            holder.amount = (TextView) aConvertView.findViewById(R.BudgetBookRowLayout.amount);

            aConvertView.setTag(holder);
        } else {
            holder = (BudgetBookHolder) aConvertView.getTag();
        }

        BudgetBook book = getItem(aPosition);
        holder.name.setText(book.getName());
        holder.amount.setText("100 €");

        return aConvertView;
    }

    private static final class BudgetBookHolder {
        TextView name;
        TextView amount;
    }
}
