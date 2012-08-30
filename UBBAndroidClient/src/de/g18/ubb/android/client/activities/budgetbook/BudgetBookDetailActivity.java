package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;

import org.hibernate.cfg.NotYetImplementedException;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookDetailActivity extends
		AbstractActivity<BudgetBookOverviewModel> {
	
	private Button delete;
	private Button buttonPrevious;
	private Button buttonNext;

	private ArrayList<BudgetBookModel> transferredData;
	private TextView budgetBookDetails;
	
	//maps to: 0 = day, 1 = month and 2 = year // default = 0
	private int dynamicViewLayoutID = 0;

	@Override
	protected BudgetBookOverviewModel createModel() {
		return new BudgetBookOverviewModel();
	}
	
	protected int getdynamicLinearLayoutID(){
		return dynamicViewLayoutID;
	}
	
	protected void setdynamicLinearLayoutID(int aNewValue){
		dynamicViewLayoutID = aNewValue;
	}
	
	protected int getLinearLayoutID(){
		switch (dynamicViewLayoutID) {
		case 0:
			return R.BudgetBook.daylinearLayout;
		case 1:
			return R.BudgetBook.monthlinearLayout;
		case 2:
			return R.BudgetBook.yearlinearLayout;

		default:
			return R.BudgetBook.daylinearLayout;
		}
	}
	@Override
	protected int getLayoutId() {
		return R.layout.activity_budget_book_detail;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initComponents();
		loadExtraContent("BudgetBookModel");
		// TODO: DebugToast entfernen
		Toast.makeText(
				this,
				"Der Adler " + transferredData.get(0).getName()
						+ " ist gelandet", Toast.LENGTH_SHORT).show();
		showDetailsOnView();
		initEventHandling();
	}

	private void loadExtraContent(String key) {
		Bundle b = getIntent().getExtras();
		transferredData = b.getParcelableArrayList(key);
	}
	
	private void showDayDetailsOnView(){
		LinearLayout lView = (LinearLayout)findViewById(getLinearLayoutID());
		//TODO: weitere Details wie benutzer und Einträge der Ansicht hinzufügen
	    budgetBookDetails = new TextView(this);
	    budgetBookDetails.setText(transferredData.get(0).getName());
	    lView.addView(budgetBookDetails);
	}
	
	private void showMonthDetailsOnView(){
		//TODO: implement logic
	}
	
	private void showYearDetailsOnView(){
		//TODO: implement logic
	}
	
	private void showDetailsOnView(){
		 // über getLinearLayoutID() wissen wir in welcher view wir uns befinden
		switch (dynamicViewLayoutID) {
		case 0:
			showDayDetailsOnView();
			break;
		case 1:
			showMonthDetailsOnView();
			break;
		case 2:
			showYearDetailsOnView();
			break;
		default:
			showDayDetailsOnView();
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.menu_addBooking:
			Toast.makeText(this, "Menü - Neuer Eintrag wurde ausgewählt",
					Toast.LENGTH_SHORT).show();
			break;

		case R.menu.menu_categoryBooking:
			switchToCategoryOverview();
			break;

		default:
			break;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private BudgetBook getSelectedBudgetBook(Long id) {
		BudgetBook book = ServiceRepository.getBudgetBookService()
				.loadSinglebudgetBookById(id);
		return book;
	}

	private void switchToCategoryOverview() {
		Intent i = new Intent(getApplicationContext(),
				BudgetBookDetailActivity.class);
		i.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
		startActivity(i);
	}

	private void initComponents() {
		delete = (Button) findViewById(R.BudgetBookDetails.deleteEntry);
		buttonNext = (Button) findViewById(R.BudgetBookDetails.Button_next);
		buttonPrevious = (Button) findViewById(R.BudgetBookDetails.Button_previous);
	}

	private void initEventHandling() {
		delete.setOnClickListener(new DeleteBudgetBookButtonListener());
		buttonPrevious
				.setOnClickListener(new PreviousBudgetBookViewButtonListener());
		buttonNext.setOnClickListener(new NextBudgetBookViewButtonListener());
	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class DeleteBudgetBookButtonListener implements
			OnClickListener {

		public void onClick(View aView) {
			throw new NotYetImplementedException("Todo: nach drücken auf delete sollen checkboxen dem layout hinzugefügt werden");
		}
	}

	private final class NextBudgetBookViewButtonListener implements
			OnClickListener {

		public void onClick(View view) {
			ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBook.details);
			//TODO: überprüfen der child id und setzen von setLinearLayoutID() current + 1
			//current --> vf.getChildAt(vf.getDisplayedChild()).getId();
			vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(),
					R.anim.slide_right));
			vf.showNext();
			
		}
	}

	private final class PreviousBudgetBookViewButtonListener implements
			OnClickListener {

		public void onClick(View view) {
			ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBook.details);
			vf.setAnimation(AnimationUtils.loadAnimation(view.getContext(),
					R.anim.slide_left));
			vf.showPrevious();
		}
	}
}
