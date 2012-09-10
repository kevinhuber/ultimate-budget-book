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
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BudgetBookAdapter extends ArrayAdapter<BudgetBook> {

    private static final int LAYOUT_ID = R.layout.budgetbook_row;


    public BudgetBookAdapter(Context aContext) {
		super(aContext, LAYOUT_ID);
	}

	public BudgetBookAdapter(Context aContext, List<BudgetBook> aBooks) {
		super(aContext, LAYOUT_ID, aBooks);
	}

	@Override
	public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
		BudgetBookHolder holder = null;

		if (aConvertView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			aConvertView = inflater.inflate(LAYOUT_ID, aParent, false);

			holder = new BudgetBookHolder();
			holder.name = (TextView) aConvertView.findViewById(R.BudgetBookRowLayout.name);
			holder.amount = (TextView) aConvertView.findViewById(R.BudgetBookRowLayout.amount);

			aConvertView.setTag(holder);
		} else {
			holder = (BudgetBookHolder) aConvertView.getTag();
		}

		BudgetBook book = getItem(aPosition);
		holder.name.setText(book.getName());

		float ammount = calculateAmmount(book.getBookings());
		holder.amount.setText(ammount + UBBConstants.CURRENCY_EURO_SIGN);

		return aConvertView;
	}

    private float calculateAmmount(List<Booking> aBookingList) {
        float currentAmmount = 0;
        for (Booking booking : aBookingList) {
            currentAmmount += booking.getAmount();
        }
        return currentAmmount;
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	private static final class BudgetBookHolder {
		TextView name;
		TextView amount;
	}
}
