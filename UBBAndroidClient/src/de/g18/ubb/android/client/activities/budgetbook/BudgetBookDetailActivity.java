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

	@Override
	protected BudgetBookOverviewModel createModel() {
		return new BudgetBookOverviewModel();
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
		initEventHandling();
	}

	private void loadExtraContent(String key) {
		Bundle b = getIntent().getExtras();
		transferredData = b.getParcelableArrayList(key);
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
	private BudgetBook getSelectedBudgetBook(int id) {
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
