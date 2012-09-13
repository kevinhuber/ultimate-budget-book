package de.g18.ubb.android.client.shared.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.shared.adapter.BudgetBookAdapter.BudgetBookTag;
import de.g18.ubb.android.client.utils.BookingHelper;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.BudgetBook;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BudgetBookAdapter extends AbstractAdapter<BudgetBook, BudgetBookTag> {

	public BudgetBookAdapter(Context aContext) {
		super(aContext, R.layout.budgetbook_row);
	}

	public BudgetBookAdapter(Context aContext, List<BudgetBook> aBooks) {
		super(aContext, R.layout.budgetbook_row, aBooks);
	}

    @Override
    protected BudgetBookTag createTag(View aConvertView) {
        BudgetBookTag tag = new BudgetBookTag();
        tag.name = (TextView) aConvertView.findViewById(R.BudgetBookRowLayout.name);
        tag.amount = (TextView) aConvertView.findViewById(R.BudgetBookRowLayout.amount);
        return tag;
    }

    @Override
    protected void updateTag(BudgetBookTag aTag, BudgetBook aEntry) {
        aTag.name.setText(aEntry.getName());

        float ammount = BookingHelper.calculateAmmountForCurrentDay(aEntry);
        if (ammount < 0.0F) {
            aTag.amount.setTextColor(Color.RED);
        } else {
            aTag.amount.setTextColor(Color.BLACK);
        }
        aTag.amount.setText(ammount + " " + UBBConstants.CURRENCY_EURO_SIGN);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

	static final class BudgetBookTag {
		TextView name;
		TextView amount;
	}
}
