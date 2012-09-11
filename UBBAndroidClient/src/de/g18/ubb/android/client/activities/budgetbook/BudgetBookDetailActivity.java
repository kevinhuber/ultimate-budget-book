package de.g18.ubb.android.client.activities.budgetbook;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import de.g18.ubb.android.client.activities.booking.CreateBookingActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.android.client.shared.adapter.BookingsAdapter;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class BudgetBookDetailActivity extends AbstractActivity<BudgetBook> {

	private ArrayList<BudgetBookCreateNewModel> transferredData;

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

	protected BookingsAdapter adapter;


	@Override
	protected BudgetBook createModel() {
		return new BudgetBook();
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

	protected ListView getMyListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsListView);
		return list;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadExtraContent("BudgetBookModel");
		adapter = new BookingsAdapter(this);
		setDynamicLinearLayoutID(DynamicLayoutId.DAY);
		getModel().setName(transferredData.get(0).getName());

		initBindings();
		initGestureComponent();

		new BookingsLoadTask().run();
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

	private void loadExtraContent(String key) {
		Bundle b = getIntent().getExtras();
		if (b != null) {
			transferredData = b.getParcelableArrayList(key);
		}
	}

	private List<Booking> getAllBookingsForCurrentBudgetBook() {
		BudgetBook budgetBook;
		try {
			budgetBook = ServiceRepository.getBudgetBookService().load(
					transferredData.get(0).getId());
		} catch (NotFoundExcpetion e) {
			throw new IllegalStateException("BudgetBook with id '"
					+ transferredData.get(0).getId() + "' has not been found!",
					e);
		}
		return budgetBook.getBookings();
	}

	private void showDayDetailsOnView() {
		updateDayDetailsView();
		if (!viewDaySet) {
			viewDaySet = true;
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
			showDayDetailsOnView(); // per default immer initialisiert
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

	private void updateYearDetailsView() {

	}

	private void updateMonthDetailsView() {

	}

	private void updateWeekDetailsView() {

	}

	private void updateDayDetailsView() {
		if (adapter.isEmpty()) {
            ((TextView) findViewById(R.BudgetBookDetailsLayout.noBookingsLabel)).setVisibility(View.VISIBLE);
		} else {
		    ((TextView) findViewById(R.BudgetBookDetailsLayout.noBookingsLabel)).setVisibility(View.INVISIBLE);
			ListView listView = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsListView);

			if (!choicesMade) {
				listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
				//löscht alle vorher getroffenen auswahlen
				listView.clearChoices();
			}

			listView.setAdapter(this.adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO: logik implementieren und weiterreichen an die
					// budgetbookbooking
					// switchToBudgetBookDetailActivity(arg0.getAdapter().getItem(arg2));
					//arg0.setBackgroundColor(Color.YELLOW);
					((BookingsAdapter)arg0.getAdapter()).setSelectItem(arg2);
					choicesMade = true;
					Toast.makeText(getApplicationContext(), "Item angewählt", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.menu_addBooking:
            switchToCreateBookingActivity();
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
		Intent i = new Intent(getApplicationContext(), CategoryOverviewActivity.class);
		i.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
		startActivity(i);
	}

	private void switchToCreateBookingActivity() {
		Intent i = new Intent(getApplicationContext(), CreateBookingActivity.class);
		i.putParcelableArrayListExtra("SingleBudgetBook", transferredData);
		startActivity(i);

	}


	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class BudgetBookDetailGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
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

		private List<Booking> bookings;

		public BookingsLoadTask() {
			super(BudgetBookDetailActivity.this, "Buchungen werden geladen...");
		}

		@Override
		protected void execute() {
			bookings = getAllBookingsForCurrentBudgetBook();
		}

		@Override
		protected void postExecute() {
			for (Booking b : bookings) {
				adapter.add(b);
			}
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
