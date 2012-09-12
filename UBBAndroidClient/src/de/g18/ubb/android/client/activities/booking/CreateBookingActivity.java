package de.g18.ubb.android.client.activities.booking;

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
import de.g18.ubb.android.client.binding.BindingHelper;
import de.g18.ubb.android.client.binding.EditTextType;
import de.g18.ubb.android.client.shared.PresentationModel;
import de.g18.ubb.android.client.shared.adapter.CategoryAdapter;
import de.g18.ubb.android.client.shared.adapter.EnumAdapter;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;

public class CreateBookingActivity extends AbstractValidationFormularActivity<Booking,
                                                                              PresentationModel<Booking>,
                                                                              BookingCreateValidator> {

	private Button datePickerButton;

	private DatePickerFragment dateFragment;

	private EnumAdapter<BookingType> bookingTypeAdapter;
	private ArrayAdapter<Category> categoryAdapter;


    @Override
    protected PresentationModel<Booking> createModel() {
        return new PresentationModel<Booking>(new Booking());
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
        categoryAdapter = new CategoryAdapter(this, getApplicationStateStore().getBudgetBookModel().getBean().getCategories());
        bookingTypeAdapter = new EnumAdapter<BookingType>(this, BookingType.class);

		initBindings();
		initComponents();
		initEventHandling();
	}

	private void initBindings() {
	    BindingHelper helper = new BindingHelper(this);
	    helper.bindEditText(getModel().getModel(Booking.PROPERTY_AMOUNT), R.BookingCreate.betrag_input, EditTextType.FLOAT);
	    helper.bindAdapterView(getModel().getModel(Booking.PROPERTY_CATEGORY), R.BookingCreate.category_spinner);
	    helper.bindAdapterView(getModel().getModel(Booking.PROPERTY_TYPE), R.BookingCreate.booking_type_spinner);
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

	private void initComponents() {
		datePickerButton = (Button) findViewById(R.BookingCreate.datePicker_Button);

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
        getModel().setValue(Booking.PROPERTY_BOOKING_TIME, dateFragment.getDate());
        Booking myBooking = ServiceRepository.getBookingService().saveAndLoad(getModel().getBean());
        BudgetBook myBook = getApplicationStateStore().getBudgetBookModel().getBean();
        myBook.getBookings().add(myBooking);
        myBook = ServiceRepository.getBudgetBookService().saveAndLoad(myBook);
        getApplicationStateStore().getBudgetBookModel().setBean(myBook);
        return StringUtil.EMPTY;
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
