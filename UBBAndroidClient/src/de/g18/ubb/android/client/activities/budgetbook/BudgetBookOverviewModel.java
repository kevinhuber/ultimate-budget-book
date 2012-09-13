package de.g18.ubb.android.client.activities.budgetbook;

import java.util.Collection;

import android.widget.ListView;
import de.g18.ubb.android.client.shared.adapter.BudgetBookAdapter;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.domain.BudgetBook;

/**
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public final class BudgetBookOverviewModel extends AbstractModel {

    private static final long serialVersionUID = 1L;


    private final BudgetBookAdapter budgetBooksAdapter;


    public BudgetBookOverviewModel(BudgetBookOverviewActivity aActivity) {
        budgetBooksAdapter = new BudgetBookAdapter(aActivity);
    }

    public void setBudgetBooks(Collection<BudgetBook> aBudgetBooks) {
        budgetBooksAdapter.clear();
        for (BudgetBook b : aBudgetBooks) {
            budgetBooksAdapter.add(b);
        }
    }

    public void bindBooksAdapter(ListView aView) {
        aView.setAdapter(budgetBooksAdapter);
    }
}
