package de.g18.ubb.android.client.activities.budgetbook;

import java.util.List;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitTask;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.booking.ContextDrivenBookingsLists;
import de.g18.ubb.android.client.activities.booking.CreateBookingActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.android.client.shared.adapter.BookingsAdapter;
import de.g18.ubb.android.client.shared.adapter.BookingsDayListAdapter;
import de.g18.ubb.android.client.shared.adapter.BookingsMonthListAdapter;
import de.g18.ubb.android.client.shared.adapter.BookingsWeekListAdapter;
import de.g18.ubb.android.client.shared.adapter.BookingsYearListAdapter;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;

public class BudgetBookDetailActivity extends AbstractActivity<BudgetBook> {

	private DynamicLayoutId dynamicViewLayoutID;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private boolean viewMonthSet;
	private boolean viewYearSet;
	private boolean viewWeekSet;
	private boolean viewDaySet;

	private boolean choicesMade;

	private GestureDetector gestureDetector;

	protected BookingsDayListAdapter dayAdapter;
	protected BookingsWeekListAdapter weekAdapter;
	protected BookingsMonthListAdapter monthAdapter;
	protected BookingsYearListAdapter yearAdapter;

	@Override
	protected BudgetBook createModel() {
		return getApplicationStateStore().getBudgetBook();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_budget_book_details, menu);
		return true;
	}

	protected DynamicLayoutId getDynamicLinearLayoutID() {
		return dynamicViewLayoutID;
	}

	protected void setDynamicLinearLayoutID(DynamicLayoutId aNewValue) {
		dynamicViewLayoutID = aNewValue;
	}

	protected int getLinearLayoutID() {
		switch (getDynamicLinearLayoutID()) {
		case DAY:
			return R.BudgetBookDetailsLayout.dayContainer;

		case WEEK:
			return R.BudgetBookDetailsLayout.weekContainer;

		case MONTH:
			return R.BudgetBookDetailsLayout.monthContainer;

		case YEAR:
			return R.BudgetBookDetailsLayout.yearContainer;

		default:
			return R.BudgetBookDetailsLayout.dayContainer;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_budget_book_detail;
	}

	protected ListView getMyDayListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsDayListView);
		return list;
	}
	
	protected ListView getMyWeekListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsWeekListView);
		return list;
	}
	
	protected ListView getMyMonthListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsMonthListView);
		return list;
	}
	
	protected ListView getMyYearListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsYearListView);
		return list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAdaptersForFilteredViews();
		
		setDynamicLinearLayoutID(DynamicLayoutId.DAY);

		initBindings();
		initGestureComponent();

		new BookingsLoadTask().run();
	}

	private void setAdaptersForFilteredViews() {
		dayAdapter = new BookingsDayListAdapter(this);
		weekAdapter = new BookingsWeekListAdapter(this);
		monthAdapter = new BookingsMonthListAdapter(this);
		yearAdapter = new BookingsYearListAdapter(this);
	}

	private void initBindings() {
		bind(BudgetBook.PROPERTY_NAME, R.BudgetBookDetailsLayout.nameLabel);
	}

	private void initGestureComponent() {
		gestureDetector = new GestureDetector(this,
				new BudgetBookDetailGestureDetector());
		ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBookDetailsLayout.detailsViewFlipper);
		vf.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
	}

	private void showDayDetailsOnView() {
		updateDayDetailsView();
		if (!viewDaySet) {
			viewDaySet = true;
		}
	}

	private void showWeekDetailsOnView() {
		updateWeekDetailsView();
		if (!viewWeekSet) {
			viewWeekSet = true;
		}
	}

	private void showMonthDetailsOnView() {
		if (!viewMonthSet) {
			viewMonthSet = true;
		}
	}

	private void showYearDetailsOnView() {
		if (!viewYearSet) {
			viewYearSet = true;
		}
	}

	private void showDetailsOnView() {
		// über getDynamicLinearLayoutID() wissen wir in welcher view wir uns
		// befinden
		switch (getDynamicLinearLayoutID()) {
		case DAY:
			if (viewDaySet) {
				updateDayDetailsView();
			}
			showDayDetailsOnView(); // per default immer initialisiert
			break;

		case WEEK:
			if (viewWeekSet) {
				updateWeekDetailsView();
			}
			showWeekDetailsOnView(); // per default immer initialisiert
			break;

		case MONTH:
			if (viewMonthSet) {
				updateMonthDetailsView();
			}
			showMonthDetailsOnView(); // wird dynamisch initialisiert
			break;

		case YEAR:
			if (viewYearSet) {
				updateYearDetailsView();
			}
			showYearDetailsOnView(); // wird dynamisch initialisiert
			break;

		default:
			showDayDetailsOnView();
			break;
		}
	}

	private void updateDayDetailsView() {
		if (dayAdapter.isEmpty()) {
			((TextView) findViewById(R.BudgetBookDetailsLayout.noBookingsLabel))
					.setVisibility(View.VISIBLE);
		} else {
			((TextView) findViewById(R.BudgetBookDetailsLayout.noBookingsLabel))
					.setVisibility(View.INVISIBLE);
			//ListView listView = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsDayListView);
			ListView listView = getMyDayListView();
			if (!choicesMade) {
				listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
				// löscht alle vorher getroffenen auswahlen
				listView.clearChoices();
			}

			listView.setAdapter(this.dayAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
					switchToDetailBookingActivity(arg0.getAdapter().getItem(arg2));
					((BookingsAdapter) arg0.getAdapter()).setSelectItem(arg2);
					choicesMade = true;
				}
			});
		}
	}

	private void updateWeekDetailsView() {

	}

	private void updateMonthDetailsView() {

	}

	private void updateYearDetailsView() {

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.menu_addBooking:
			switchActivity(CreateBookingActivity.class);
			break;

		case R.menu.menu_categoryBooking:
			switchActivity(CategoryOverviewActivity.class);
			break;

		default:
			break;
		}
		return true;
	}

	private void switchToDetailBookingActivity(Object item) {

	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class BudgetBookDetailGestureDetector extends
			SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBookDetailsLayout.detailsViewFlipper);
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
				setDynamicLinearLayoutID(DynamicLayoutId.getByChildId(vf
						.getDisplayedChild()));
				showDetailsOnView();
				// links nach rechts
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				vf.setInAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_left));
				vf.setOutAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_right));
				vf.showPrevious();
				setDynamicLinearLayoutID(DynamicLayoutId.getByChildId(vf
						.getDisplayedChild()));
				showDetailsOnView();
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

	private final class BookingsLoadTask extends AbstractWaitTask {

		private List<Booking> bookings, dayBookings, weekBookings, monthBookings, yearBookings;

		public BookingsLoadTask() {
			super(BudgetBookDetailActivity.this, "Buchungen werden geladen...");
		}

		@Override
		protected void execute() {
			ContextDrivenBookingsLists contextDrivenModel = new ContextDrivenBookingsLists(getModel().getBookings());
			bookings = getModel().getBookings();
			dayBookings = contextDrivenModel.getDayBookingsList();
			weekBookings = contextDrivenModel.getWeekyBookingsList();
			monthBookings = contextDrivenModel.getMonthBookingsList();
			yearBookings = contextDrivenModel.getYearBookingsList();
		}

		@Override
		protected void postExecute() {
			dayAdapter.setData(dayBookings);
			weekAdapter.setData(weekBookings);
			monthAdapter.setData(monthBookings);
			yearAdapter.setData(yearBookings);
			showDayDetailsOnView();
		}
	}

	private enum DynamicLayoutId {

		DAY(0), WEEK(1), MONTH(2), YEAR(3);

		private final int childId;

		private DynamicLayoutId(int aChildId) {
			childId = aChildId;
		}

		public int getChildId() {
			return childId;
		}

		public static DynamicLayoutId getByChildId(int aChildId) {
			for (DynamicLayoutId entry : values()) {
				if (aChildId == entry.getChildId()) {
					return entry;
				}
			}
			return null;
		}
	}
}
