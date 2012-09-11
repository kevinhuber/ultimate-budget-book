package de.g18.ubb.android.client.activities.booking;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookDetailActivity;
import de.g18.ubb.android.client.shared.adapter.CategoryAdapter;
import de.g18.ubb.android.client.shared.adapter.EnumAdapter;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class CreateBookingActivity extends AbstractValidationFormularActivity<Booking, BookingCreateValidator> {

	private Button datePickerButton;

	private DatePickerFragment dateFragment;
	private final String DATE_FORMAT =  "dd.MM.yyyy";
	private SimpleDateFormat sdf;

	private EnumAdapter<BookingType> bookingTypeAdapter;
	private ArrayAdapter<Category> categoryAdapter;

    @Override
    protected Booking createModel() {
        return new Booking();
    }

    @Override
    protected BookingCreateValidator createValidator() {
        return new BookingCreateValidator(getModel());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_booking;
    }

    @Override
    protected int getSubmitButtonId() {
        return R.BookingCreate.save_booking;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dateFragment = new DatePickerFragment();
		
		sdf = new SimpleDateFormat(DATE_FORMAT);
		
        categoryAdapter = new CategoryAdapter(this, getAllCategorysForCurrentBudgetBook());
//        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<BookingType> enumList = Arrays.asList(BookingType.values());
        bookingTypeAdapter = new EnumAdapter<BookingType>(this, enumList);
//        bookingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		initBindings();
		initComponents();
		initEventHandling();
	}

	private void initBindings() {
	    bind(Booking.PROPERTY_AMOUNT, R.BookingCreate.betrag_input);
        bind(Booking.PROPERTY_CATEGORY, R.BookingCreate.category_spinner);
        bind(Booking.PROPERTY_TYPE, R.BookingCreate.booking_type_spinner);
        bind(Booking.PROPERTY_BOOKING_NAME, R.BookingCreate.name_input);
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

	private List<Category> getAllCategorysForCurrentBudgetBook() {
		return getApplicationStateStore().getBudgetBook().getCategories();
	}

	private void initComponents() {
		datePickerButton = (Button) findViewById(R.BookingCreate.datePicker_Button);
		datePickerButton.setText(sdf.format(new Date()));

        Spinner categorySpinner = (Spinner) findViewById(R.BookingCreate.category_spinner);
        categorySpinner.setAdapter(categoryAdapter);

        Spinner bookingTypeSpinner = (Spinner) findViewById(R.BookingCreate.booking_type_spinner);
        bookingTypeSpinner.setAdapter(bookingTypeAdapter);
	}

	private void initEventHandling() {
		datePickerButton.setOnClickListener(new DatePickerButtonListener());
	}

	public void changeDatePickerButton(){
		datePickerButton.setText(dateFragment.getDate().toString());
	}

	public void showDatePickerDialog(View v) {
		dateFragment.show(getSupportFragmentManager(), "datePicker");
	}

    @Override
    protected String getSubmitWaitMessage() {
        return "Buchung wird erstellt...";
    }

    @Override
    protected String submit() {
        getModel().setBookingTime(dateFragment.getDate());
        Booking myBooking = ServiceRepository.getBookingService().saveAndLoad(getModel());
        BudgetBook myBook = getApplicationStateStore().getBudgetBook();
        myBook.getBookings().add(myBooking);
        myBook = ServiceRepository.getBudgetBookService().saveAndLoad(myBook);
        getApplicationStateStore().setBudgetBook(myBook);
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        super.postSubmit();

        if (!isSubmitSuccessfull()) {
            return;
        }
        switchActivity(BudgetBookDetailActivity.class);
    }
    

	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class DatePickerButtonListener implements OnClickListener {

		public void onClick(View aView) {
			showDatePickerDialog(aView);
		}
	}
}
