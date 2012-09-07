package de.g18.ubb.android.client.activities.booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookDetailActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookModel;
import de.g18.ubb.android.client.activities.category.CategoryAdapter;
import de.g18.ubb.android.client.binding.BindingUtils;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class CreateBookingActivity extends FragmentActivity {

	private ArrayList<BudgetBookModel> transferredData;

	private Spinner category_spinner;
	private Spinner booking_type_spinner;

	private Button datePickerButton;
	private Button saveBookingButton;

	private DialogFragment dateFragment;

	private BookingStateBucket instance;
	private ArrayAdapter<BookingType> dataBookingTypeAdapter;
	private ArrayAdapter<Category> dataBookingCategoryAdapter;
	
	private Booking model;
	
	private EditText amountEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_booking);
		loadExtraContent("SingleBudgetBook");
		
		model = new Booking();
		
		initBindings();
		initComponents();
		initEventHandling();
		
		addItemsOnBookingTypeSpinner();
		addItemsOnCategorySpinner();
	}

	private void initBindings() {
		amountEditText = (EditText) findViewById(R.BookingCreate.betrag_input);
		BindingUtils.bind(amountEditText, model, Booking.PROPERTY_AMOUNT);
		
		category_spinner = (Spinner) findViewById(R.BookingCreate.category_spinner);
		BindingUtils.bind(category_spinner, model, Booking.PROPERTY_CATEGORY);
		
		booking_type_spinner = (Spinner) findViewById(R.BookingCreate.booking_type_spinner);
		BindingUtils.bind(booking_type_spinner, model, Booking.PROPERTY_TYPE);
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
		BudgetBook budgetBook;
        try {
            budgetBook = ServiceRepository.getBudgetBookService()
            		.load(transferredData.get(0).getId());
        } catch (NotFoundExcpetion e) {
            throw new IllegalStateException("BudgetBook with id '" + transferredData.get(0).getId() + "' has not been found!", e);
        }
		return budgetBook;
	}

	private List<Category> getAllCategorysForCurrentBudgetBook() {
		return ServiceRepository.getCategoryService().getAll(
				getCurrentBudgetBook());
	}

	public void addItemsOnCategorySpinner() {
		category_spinner = (Spinner) findViewById(R.BookingCreate.category_spinner);

		dataBookingCategoryAdapter = new CategoryAdapter(this, getAllCategorysForCurrentBudgetBook());
		dataBookingCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category_spinner.setAdapter(dataBookingCategoryAdapter);
	}

	public void addItemsOnBookingTypeSpinner() {

		booking_type_spinner = (Spinner) findViewById(R.BookingCreate.booking_type_spinner);
		List<BookingType> enumList = Arrays.asList(BookingType.values());

		dataBookingTypeAdapter = new BookingTypeAdapter(this, enumList);
		dataBookingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		booking_type_spinner.setAdapter(dataBookingTypeAdapter);
	}

	private void initComponents() {
		datePickerButton = (Button) findViewById(R.BookingCreate.datePicker_Button);
		saveBookingButton = (Button) findViewById(R.BookingCreate.save_booking);

	}

	private void initEventHandling() {
		datePickerButton.setOnClickListener(new DatePickerButtonListener());
		saveBookingButton.setOnClickListener(new SaveBookingButtonListener());
	}
	public void changeDatePickerButton(){
		datePickerButton.setText( ((DatePickerFragment) dateFragment).getDate().toString());
	}

	public void showDatePickerDialog(View v) {
		dateFragment = new DatePickerFragment();
		dateFragment.show(getSupportFragmentManager(), "datePicker");
	}

	private void switchToBudgetBookDetailActivity() {
		Intent i = new Intent(getApplicationContext(), BudgetBookDetailActivity.class);
		i.putParcelableArrayListExtra("BudgetBookModel", transferredData);
		startActivity(i);
	}

	private void saveBooking() {
		// TODO: implement the save process
		//Specify the desired date format
		
		 model.setBookingTime(BookingStateBucket.getInstance().getBookingDate());
		 ServiceRepository.getBookingService().saveAndLoad(model);
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
