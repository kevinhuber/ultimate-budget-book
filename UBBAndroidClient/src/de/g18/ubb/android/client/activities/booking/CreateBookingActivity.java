package de.g18.ubb.android.client.activities.booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookDetailActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.android.client.activities.category.CategoryOverviewActivity;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.repository.ServiceRepository;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

public class CreateBookingActivity extends FragmentActivity {

	private ArrayList<BudgetBookModel> transferredData;

	private Spinner category_spinner;
	private Spinner booking_type_spinner;

	private Button datePickerButton;
	private Button saveBookingButton;

	private DialogFragment dateFragment;

	private Date bookingDate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_booking);
		loadExtraContent("SingleBudgetBook");

		initComponents();
		initEventHandling();

		addItemsOnBookingTypeSpinner();
		addItemsOnCategorySpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_create_booking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadExtraContent(String key) {
		Bundle b = getIntent().getExtras();
		transferredData = b.getParcelableArrayList(key);
	}

	private BudgetBook getCurrentBudgetBook() {
		BudgetBook budgetBook = ServiceRepository.getBudgetBookService()
				.loadSinglebudgetBookById(transferredData.get(0).getId());
		return budgetBook;
	}

	private List<Category> getAllCategorysForCurrentBudgetBook() {
		return ServiceRepository.getCategoryService().getAll(
				getCurrentBudgetBook());
	}

	public void addItemsOnCategorySpinner() {
		List<String> list = new ArrayList<String>();

		category_spinner = (Spinner) findViewById(R.BookingCreate.category_spinner);
		for (Category singleCategory : getAllCategorysForCurrentBudgetBook()) {
			list.add(singleCategory.getName());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category_spinner.setAdapter(dataAdapter);
	}

	public void addItemsOnBookingTypeSpinner() {

		booking_type_spinner = (Spinner) findViewById(R.BookingCreate.booking_type_spinner);
		List<String> list = new ArrayList<String>();
		List<BookingType> enumList = Arrays.asList(BookingType.values());
		for (BookingType singleBookingType : enumList) {
			list.add(singleBookingType.name().toLowerCase());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		booking_type_spinner.setAdapter(dataAdapter);
	}

	private void initComponents() {
		datePickerButton = (Button) findViewById(R.BookingCreate.datePicker_Button);
		saveBookingButton = (Button) findViewById(R.BookingCreate.save_booking);

	}

	private void initEventHandling() {
		datePickerButton.setOnClickListener(new DatePickerButtonListener());
		saveBookingButton.setOnClickListener(new SaveBookingButtonListener());
	}

	public void showDatePickerDialog(View v) {
		dateFragment = new DatePickerFragment();
		dateFragment.show(getSupportFragmentManager(), "datePicker");
	}

	private void switchToBudgetBookDetailActivity() {
		Intent i = new Intent(getApplicationContext(),
				BudgetBookDetailActivity.class);
		startActivity(i);
	}

	private void saveBooking() {
		// TODO: implement the save process
		// bookingDate = ((DatePickerFragment) dateFragment).getDate();
		// Booking newBooking = new Booking();
		//
		// newBooking.setType(BookingType.SPENDING);
		// newBooking.setAmount(45);
		// newBooking.setCreateUser(getCurrentBudgetBook().getAssignedUser()
		// .get(0));
		// newBooking.setCategory(getAllCategorysForCurrentBudgetBook().get(0));
		// newBooking.setBookingTime(new Date());
	}

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class DatePickerButtonListener implements OnClickListener {

		public void onClick(View aView) {
			showDatePickerDialog(aView);
		}
	}

	private final class SaveBookingButtonListener implements OnClickListener {

		public void onClick(View aView) {
			saveBooking();
			switchToBudgetBookDetailActivity();
		}
	}

}
