package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitTask;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class BudgetBookOverviewActivity extends AbstractActivity<BudgetBookOverviewModel> {

    private Button createButton;
    private ListView budgetBooksListView;


    @Override
    protected BudgetBookOverviewModel createModel() {
        return new BudgetBookOverviewModel(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_budget_book_overview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
        initEventHandling();

        new BudgetBookLoadTask().run();
    }

    private void initComponents() {
        createButton = (Button) findViewById(R.BudgetBookOverviewLayout.createButton);

        budgetBooksListView = (ListView) findViewById(R.BudgetBookOverviewLayout.budgetBooksListView);
        getModel().bindBooksAdapter(budgetBooksListView);
    }

    private void initEventHandling() {
        createButton.setOnClickListener(new CreateNewBudgetBookButtonListener());
        budgetBooksListView.setOnItemClickListener(new BudgetBookSelectionHandler());
    }

    private void switchToBudgetBookCreateNew() {
    	switchActivity(BudgetBookCreateNewActivity.class);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class CreateNewBudgetBookButtonListener implements OnClickListener {

        public void onClick(View aView) {
            switchToBudgetBookCreateNew();
        }
    }

    private final class BudgetBookLoadTask extends AbstractWaitTask {

        private List<BudgetBook> books;


        public BudgetBookLoadTask() {
            super(BudgetBookOverviewActivity.this, "Haushaltsbücher werden geladen...");
        }

        @Override
        protected void execute() {
            books = ServiceRepository.getBudgetBookService().getAllForCurrentUser();
        }

        @Override
        protected void postExecute() {
            getModel().setBudgetBooks(books);
        }
    }

    private final class BudgetBookSelectionHandler extends AbstractWaitTask implements OnItemClickListener {

        private BudgetBook selectedItem;
        private Intent intentToStart;


        public BudgetBookSelectionHandler() {
            super(BudgetBookOverviewActivity.this, "Detailansicht wird geladen...");
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            selectedItem = (BudgetBook) arg0.getAdapter().getItem(arg2);
            run();
        }

        @Override
        protected void execute() {
            intentToStart = new Intent(getApplicationContext(), BudgetBookDetailActivity.class);
            // erstelle das model (parcable)
            BudgetBookCreateNewModel bbm = new BudgetBookCreateNewModel();
            bbm.mapBudgetBookToModel(selectedItem);

            // hier ist es  möglich mehrere daten einer anderen activity zu übergeben
            ArrayList<BudgetBookCreateNewModel> dataList = new ArrayList<BudgetBookCreateNewModel>();
            dataList.add(bbm);
            intentToStart.putParcelableArrayListExtra("BudgetBookModel", dataList);
        }

        @Override
        protected void postExecute() {
            startActivity(intentToStart);
        }
    }
}
