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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.action.AbstractWaitTask;
import de.g18.ubb.android.client.activities.AbstractActivity;
import de.g18.ubb.android.client.activities.booking.BookingDetailsActivity;
import de.g18.ubb.android.client.activities.booking.ContextDrivenBookingsLists;
import de.g18.ubb.android.client.activities.booking.CreateBookingActivity;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.android.client.shared.adapter.BookingsAdapter;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;

/**
 * Stelllt eine Activity zur Verfügung um die Details eines haushaltsbuches darzustellen.
 *
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public class BudgetBookDetailActivity extends AbstractActivity<BudgetBook> {

	private DynamicLayoutId dynamicViewLayoutID;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private GestureDetector gestureDetector;

    private OverviewNameModel nameModel;

	private BookingsAdapter dayAdapter, allAdapter, yearAdapter, monthAdapter, weekAdapter;


	@Override
	protected BudgetBook createModel() {
		return getApplicationStateStore().getBudgetBook();
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameModel = new OverviewNameModel();

        dayAdapter = new BookingsAdapter(this);
        weekAdapter = new BookingsAdapter(this);
        monthAdapter = new BookingsAdapter(this);
        yearAdapter = new BookingsAdapter(this);
        allAdapter = new BookingsAdapter(this);

        setDynamicLinearLayoutID(DynamicLayoutId.DAY);

        initBindings();
        initGestureComponent();
    }

    private void initBindings() {
        bind(BudgetBook.PROPERTY_NAME, R.BudgetBookDetailsLayout.nameLabel);
        bind(nameModel, OverviewNameModel.PROPERTY_NAME, R.BudgetBookDetailsLayout.title);
    }

    private void initGestureComponent() {
        gestureDetector = new GestureDetector(this, new BudgetBookDetailGestureDetector());
        ViewFlipper vf = (ViewFlipper) findViewById(R.BudgetBookDetailsLayout.detailsViewFlipper);
        vf.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        reset();
        new BookingsLoadTask().run();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_budget_book_details, menu);
		return true;
	}

	/**
	 * Hält die Information über die aktuell angezeigte {@link ListView}
	 *
	 * @return liefert einen Enum zurück der Aufschluss darüber gibt welche {@link ListView} aktuell angezeigt wird.
	 */
	protected DynamicLayoutId getDynamicLinearLayoutID() {
		return dynamicViewLayoutID;
	}

	protected void setDynamicLinearLayoutID(DynamicLayoutId aNewValue) {
		dynamicViewLayoutID = aNewValue;
	}

	/**
	 * Liefert die Layout ID des {@link LinearLayout} zurück,
	 * in dem wiederum die {@link ListView} angezeigt wird
	 */
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

		case ALL:
			return R.BudgetBookDetailsLayout.allBookingsContainer;

		default:
			return R.BudgetBookDetailsLayout.dayContainer;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_budget_book_detail;
	}

	/**
	 * Gibt die {@link ListView} die zur Anzeige der Buchungen eines Tages genutzt wird zurück
	 *
	 * @return {@link ListView}
	 */
	protected ListView getMyDayListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsDayListView);
		return list;
	}

	/**
	 * Gibt die {@link ListView} die zur Anzeige der Buchungen einer Woche genutzt wird zurück
	 *
	 * @return {@link ListView}
	 */
	protected ListView getMyWeekListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsWeekListView);
		return list;
	}

	/**
	 * Gibt die {@link ListView} die zur Anzeige der Buchungen eines Monats genutzt wird zurück
	 *
	 * @return {@link ListView}
	 */
	protected ListView getMyMonthListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsMonthListView);
		return list;
	}

	/**
	 * Gibt die {@link ListView} die zur Anzeige der Buchungen eines Jahres genutzt wird zurück
	 *
	 * @return {@link ListView}
	 */
	protected ListView getMyYearListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsYearListView);
		return list;
	}

	/**
	 * Gibt die {@link ListView} die zur Anzeige aller Buchungen genutzt wird zurück
	 *
	 * @return {@link ListView}
	 */
	protected ListView getMyAllBookingsListView() {
		ListView list = (ListView) findViewById(R.BudgetBookDetailsLayout.bookingsAllBookingsListView);
		return list;
	}

	private void showDetailsOnView() {
		// über getDynamicLinearLayoutID() wissen wir in welcher view wir uns
		// befinden
		switch (getDynamicLinearLayoutID()) {
		case DAY:
			updateDayDetailsView();
			break;

		case WEEK:
			updateWeekDetailsView();
			break;

		case MONTH:
			updateMonthDetailsView();
			break;

		case YEAR:
			updateYearDetailsView();
			break;

		case ALL:
			updateAllDetailsView();
			break;

		default:
			updateDayDetailsView();
			break;
		}
	}

	/**
	 * Aktuallisiert die Tagesanischt und die dort angezeigten Buchungen
	 */
	private void updateDayDetailsView() {
	    nameModel.setName("Tagesansicht");
		if (dayAdapter.isEmpty()) {
			findViewById(R.BudgetBookDetailsLayout.noDayBookingsLabel).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.BudgetBookDetailsLayout.noDayBookingsLabel).setVisibility(View.GONE);
			ListView listView = getMyDayListView();
			listView.setAdapter(dayAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					getApplicationStateStore().setBooking((Booking)arg0.getAdapter().getItem(arg2));
					switchToDetailBookingActivity();
				}
			});
		}
	}

	/**
	 * Aktuallisiert die Wochenansicht und die dort angezeigten Buchungen
	 */
	private void updateWeekDetailsView() {
        nameModel.setName("Wochenansicht");
		if (weekAdapter.isEmpty()) {
			findViewById(R.BudgetBookDetailsLayout.noWeekBookingsLabel).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.BudgetBookDetailsLayout.noWeekBookingsLabel).setVisibility(View.GONE);

			ListView listView = getMyWeekListView();
			listView.setAdapter(weekAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
					getApplicationStateStore().setBooking((Booking)arg0.getAdapter().getItem(arg2));
					switchToDetailBookingActivity();
				}
			});
		}
	}

	/**
	 * Aktuallisiert die Monatsansicht und die dort angezeigten Buchungen
	 */
	private void updateMonthDetailsView() {
        nameModel.setName("Monatsansicht");
		if (monthAdapter.isEmpty()) {
			findViewById(R.BudgetBookDetailsLayout.noMonthBookingsLabel).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.BudgetBookDetailsLayout.noMonthBookingsLabel).setVisibility(View.GONE);

			ListView listView = getMyMonthListView();
			listView.setAdapter(monthAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
					getApplicationStateStore().setBooking((Booking)arg0.getAdapter().getItem(arg2));
					switchToDetailBookingActivity();
				}
			});
		}
	}

	/**
	 * Aktuallisiert die Jahresansicht und die dort angezeigten Buchungen
	 */
	private void updateYearDetailsView() {
        nameModel.setName("Jahresansicht");
		if (yearAdapter.isEmpty()) {
			findViewById(R.BudgetBookDetailsLayout.noYearBookingsLabel).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.BudgetBookDetailsLayout.noYearBookingsLabel).setVisibility(View.GONE);

			ListView listView = getMyYearListView();
			listView.setAdapter(yearAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					getApplicationStateStore().setBooking((Booking)arg0.getAdapter().getItem(arg2));
					switchToDetailBookingActivity();
				}
			});
		}
	}

	/**
	 * Aktuallisiert die Gesamtansicht und die dort angezeigten Buchungen
	 */
	private void updateAllDetailsView() {
        nameModel.setName("Gesamtansicht");
		if (allAdapter.isEmpty()) {
			findViewById(R.BudgetBookDetailsLayout.noBookingsLabel).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.BudgetBookDetailsLayout.noBookingsLabel).setVisibility(View.GONE);

			ListView listView = getMyAllBookingsListView();
			listView.setAdapter(allAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
					getApplicationStateStore().setBooking((Booking)arg0.getAdapter().getItem(arg2));
					switchToDetailBookingActivity();
				}
			});
		}
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

	private void switchToDetailBookingActivity() {
		switchActivity(BookingDetailsActivity.class);
	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class BudgetBookDetailGestureDetector extends SimpleOnGestureListener {

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

		private List<Booking> bookings, dayBookings, weekBookings,
				monthBookings, yearBookings;

		public BookingsLoadTask() {
			super(BudgetBookDetailActivity.this, "Buchungen werden geladen...");
		}

		@Override
		protected void execute() {
			ContextDrivenBookingsLists contextDrivenModel = new ContextDrivenBookingsLists(getModel().getBookings());
			dayBookings = contextDrivenModel.getDayBookingsList();
			weekBookings = contextDrivenModel.getWeekyBookingsList();
			monthBookings = contextDrivenModel.getMonthBookingsList();
			yearBookings = contextDrivenModel.getYearBookingsList();
			bookings = getModel().getBookings(); // all bookings
		}

		@Override
		protected void postExecute() {
			dayAdapter.setData(dayBookings);
			weekAdapter.setData(weekBookings);
			monthAdapter.setData(monthBookings);
			yearAdapter.setData(yearBookings);
			allAdapter.setData(bookings);
			showDetailsOnView();
		}
	}

	/**
	 * Hält alle möglichen {@link ListView} Zustände fest
	 * @author Sebastian.Kopatz
	 *
	 */
	private enum DynamicLayoutId {

		DAY(0), WEEK(1), MONTH(2), YEAR(3), ALL(4);

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

	private static final class OverviewNameModel extends AbstractModel {

        private static final long serialVersionUID = 1L;

        public static final String PROPERTY_NAME = "name";

	    private String name;


	    public OverviewNameModel() {
	    }

	    public void setName(String aNewValue) {
	        String oldValue = getName();
	        name = aNewValue;
	        fireChange(PROPERTY_NAME, oldValue, getName());
	    }

	    public String getName() {
	        return name;
	    }
	}
}
