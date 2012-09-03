package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookDetailActivity extends
		AbstractActivity<BudgetBookOverviewModel> {

	private Button delete;
	private Button add;

	private ArrayList<BudgetBookModel> transferredData;
	private TextView budgetBookDetails;
	private TextView budgetBookBookings;

	// maps to: 0 = day, 1 = month and 2 = year // default = 0
	private int dynamicViewLayoutID = 0;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private boolean viewMonthSet = false;
	private boolean viewYearSet = false;
	private boolean viewDaySet = false;

	private GestureDetector gestureDetector;

	protected BudgetBookBookingsAdapter adapter;

	@Override
	protected BudgetBookOverviewModel createModel() {
		return new BudgetBookOverviewModel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_budget_book_details, menu);
		return true;
	}

	protected int getdynamicLinearLayoutID() {
		return dynamicViewLayoutID;
	}

	protected void setdynamicLinearLayoutID(int aNewValue) {
		dynamicViewLayoutID = aNewValue;
	}

	protected int getLinearLayoutID() {
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

	protected ListView getMyListView() {
		ListView list = (ListView) findViewById(R.BudgetBook.bookings);
		return list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new BudgetBookBookingsAdapter(this,
				getAllBookingsForCurrentBudgetBook());
		loadExtraContent("BudgetBookModel");

		initComponents();
		initGestureComponent();

		showDayDetailsOnView();
		initEventHandling();
	}

	private void initGestureComponent() {
		gestureDetector = new GestureDetector(this,
				new BudgetBookDetailGestureDetector());
		ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBook.details);
		vf.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		});
	}

	private void loadExtraContent(String key) {
		Bundle b = getIntent().getExtras();
		transferredData = b.getParcelableArrayList(key);
	}

	private List<Booking> getAllBookingsForCurrentBudgetBook() {
		BudgetBook budgetBook = ServiceRepository.getBudgetBookService()
				.loadSinglebudgetBookById(transferredData.get(0).getId());
		return budgetBook.getBookings();
	}
	
	private BudgetBook getCurrentBudgetBook() {
		BudgetBook budgetBook = ServiceRepository.getBudgetBookService()
				.loadSinglebudgetBookById(transferredData.get(0).getId());
		return budgetBook;
	}
	
	private List<Category> getAllCategorysForCurrentBudgetBook() {
		return ServiceRepository.getCategoryService()
				.getAll(getCurrentBudgetBook());
	}

	private void showDayDetailsOnView() {
		if (!viewDaySet) {
			LinearLayout lView = (LinearLayout) findViewById(getLinearLayoutID());

			budgetBookDetails = new TextView(this);
			budgetBookDetails.setText(transferredData.get(0).getName());
			budgetBookBookings = new TextView(this);
			if (!getAllBookingsForCurrentBudgetBook().isEmpty()) {
				// budgetBookBookings.setText("Erster Booking Eintrag" +
				// getAllBookingsForCurrentBudgetBook().get(0).toString());
				budgetBookBookings = null;
				// BudgetBookBookingsAdapter adapter = new
				// BudgetBookBookingsAdapter(this,
				// getAllBookingsForCurrentBudgetBook());
				ListView listView = (ListView) findViewById(R.BudgetBook.bookings);
				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO: logik iomplementieren und weiterreichen an die
						// budgetbookbooking
						// switchToBudgetBookDetailActivity(arg0.getAdapter().getItem(arg2));
						Toast.makeText(getApplicationContext(),
								"Item angewählt", Toast.LENGTH_SHORT).show();
					}
				});
			} else {
				budgetBookBookings.setText("Keine Beiträge vorhanden");
			}

			lView.addView(budgetBookDetails, 1); // position auf 1 setzen
			lView.addView(budgetBookBookings, 2);

			viewDaySet = true;
		}
	}

	private void showMonthDetailsOnView() {
		if (!viewMonthSet) {
			LinearLayout lView = (LinearLayout) findViewById(getLinearLayoutID());
			budgetBookDetails = new TextView(this);
			budgetBookDetails.setText(transferredData.get(0).getName());
			lView.addView(budgetBookDetails, 1); // position auf 1 setzen
			viewMonthSet = true;
		}
	}

	private void showYearDetailsOnView() {
		if (!viewYearSet) {
			LinearLayout lView = (LinearLayout) findViewById(getLinearLayoutID());
			budgetBookDetails = new TextView(this);
			budgetBookDetails.setText(transferredData.get(0).getName());
			lView.addView(budgetBookDetails, 1); // position auf 1 setzen
			viewYearSet = true;
		}
	}

	private void showDetailsOnView() {
		// über getDynamicLinearLayoutID() wissen wir in welcher view wir uns
		// befinden
		switch (dynamicViewLayoutID) {
		case 0:
			showDayDetailsOnView(); // per default immer initialisiert
			break;
		case 1:
			showMonthDetailsOnView(); // wird dynamisch initialisiert
			break;
		case 2:
			showYearDetailsOnView(); // wird dynamisch initialisiert
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

	private void switchToCategoryOverview() {
		Intent i = new Intent(getApplicationContext(),
				CategoryOverviewActivity.class);
		i.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
		startActivity(i);
	}
	private Booking addBooking() {
		Booking newBooking = new Booking();
		
		newBooking.setType(BookingType.SPENDING);
		newBooking.setAmount(45);
		newBooking.setCategory(getAllCategorysForCurrentBudgetBook().get(0));
		newBooking.setBookingTime(new Date());
		
		return newBooking;
	}

	private void initComponents() {
		delete = (Button) findViewById(R.BudgetBookDetails.deleteEntry);
		add = (Button) findViewById(R.BudgetBookDetails.addEntry);
	}

	private void initEventHandling() {
		delete.setOnClickListener(new DeleteBudgetBookButtonListener());
		add.setOnClickListener(new AddBudgetBookBookingButtonListener());
		
	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class DeleteBudgetBookButtonListener implements
			OnClickListener {

		public void onClick(View aView) {
			SparseBooleanArray checkedItemPositions = getMyListView()
					.getCheckedItemPositions();
			int itemCount = getMyListView().getCount();

			for (int i = itemCount - 1; i >= 0; i--) {
				if (checkedItemPositions.get(i)) {
					adapter.remove(getAllBookingsForCurrentBudgetBook().get(i));
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

	private final class AddBudgetBookBookingButtonListener implements
			OnClickListener {

		public void onClick(View aView) {
			int itemCount = getMyListView().getCount();

			for (int i = itemCount - 1; i >= 0; i--) {
				adapter.add(addBooking());
			}
			adapter.notifyDataSetChanged();
		}
	}

	class BudgetBookDetailGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBook.details);
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
				return false;
			}
			// rechts nach links
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				vf.setInAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_right));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_left));
				vf.showNext();
				setdynamicLinearLayoutID(vf.getDisplayedChild());
				showDetailsOnView();
				// links nach rechts
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				vf.setInAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_left));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_right));
				vf.showPrevious();
				setdynamicLinearLayoutID(vf.getDisplayedChild());
				showDetailsOnView();
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

}
