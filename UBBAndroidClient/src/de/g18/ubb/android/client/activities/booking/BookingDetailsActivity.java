package de.g18.ubb.android.client.activities.booking;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookDetailActivity;
import de.g18.ubb.android.client.shared.adapter.CategoryAdapter;
import de.g18.ubb.android.client.shared.adapter.EnumAdapter;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;

public class BookingDetailsActivity extends AbstractValidationFormularActivity<Booking, BookingCreateValidator> {

	private EditText bookingName, bookingChangeTime, bookingAmount, bookingCreateUser, bookingChangeUser, bookingCreateTime;
	private Spinner categorySpinner, bookingTypeSpinner;
	private DatePickerFragment dateFragment;
	private CategoryAdapter categoryAdapter;
	private SimpleDateFormat sdf;
	private EnumAdapter<BookingType> bookingTypeAdapter;
	private Button datePickerButton, changeMode;
	private boolean editMode;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFragment = new DatePickerFragment((Button) findViewById(R.BookingDetail.datePicker_Button));
		dateFragment.setDate(getModel().getBookingTime());
		sdf = new SimpleDateFormat(UBBConstants.DATE_FORMAT);
		
        categoryAdapter = new CategoryAdapter(this, getAllCategorysForCurrentBudgetBook());

        List<BookingType> enumList = Arrays.asList(BookingType.values());
        bookingTypeAdapter = new EnumAdapter<BookingType>(this, enumList);

        initBindings();
        initComponents();
        enableDisableElements();
        initEventHandling();
       
    }
    
    private void initBindings() {
    	bookingName = (EditText) bind(Booking.PROPERTY_BOOKING_NAME, R.BookingDetail.name_input);
        bookingAmount = (EditText) bind(Booking.PROPERTY_AMOUNT, R.BookingDetail.betrag_input);
        bookingCreateUser = (EditText) bind(Booking.PROPERTY_CREATE_USER, R.BookingDetail.create_user_input);
        bookingChangeTime = (EditText) bind(Booking.PROPERTY_CHANGE_TIME, R.BookingDetail.changed_time_input);
        bookingChangeUser = (EditText) bind(Booking.PROPERTY_CHANGE_USER, R.BookingDetail.change_user_input);
        bookingCreateTime = (EditText) bind(Booking.PROPERTY_CREATE_TIME, R.BookingDetail.create_time_input);
        categorySpinner = (Spinner) bind(Booking.PROPERTY_CATEGORY, R.BookingDetail.category_spinner);
        bookingTypeSpinner = (Spinner) bind(Booking.PROPERTY_TYPE, R.BookingDetail.booking_type_spinner);
	}
    
    private void initComponents() {
    	categorySpinner.setAdapter(categoryAdapter);
        bookingTypeSpinner.setAdapter(bookingTypeAdapter);
        
        datePickerButton = (Button) findViewById(R.BookingDetail.datePicker_Button);
		datePickerButton.setText(sdf.format(getModel().getBookingTime()));
		
		changeMode = (Button) findViewById(R.BookingDetail.change_booking);
    }
    
    private void enableDisableElements(){
    	setEditTextViewState(bookingName, editMode);
    	setEditTextViewState(bookingAmount, editMode);
    	setEditTextViewState(bookingCreateUser, editMode);
    	setEditTextViewState(bookingChangeTime, editMode);
    	setEditTextViewState(bookingChangeUser, editMode);
    	setEditTextViewState(bookingCreateTime, editMode);
    	setSpinnerViewState(categorySpinner, editMode);
    	setSpinnerViewState(bookingTypeSpinner, editMode);
    	setButtonViewState(datePickerButton, editMode);
    }
    
    private void setEditTextViewState(EditText aEditTextView,  boolean aBool){
    	aEditTextView.setEnabled(aBool);
    	aEditTextView.setFocusable(aBool);
    }
    
    private void setButtonViewState(Button aButtonView,  boolean aBool){
    	aButtonView.setEnabled(aBool);
    	aButtonView.setFocusable(aBool);
    }
    
    private void setSpinnerViewState(Spinner aSpinnerView, boolean aBool){
    	aSpinnerView.setEnabled(aBool);
    	aSpinnerView.setFocusable(aBool);
    }
    
	private List<Category> getAllCategorysForCurrentBudgetBook() {
		return getApplicationStateStore().getBudgetBook().getCategories();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_booking_details, menu);
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

	@Override
	protected int getSubmitButtonId() {
		return R.BookingDetail.update_booking;
	}

	@Override
	protected String submit() {
		Booking updatedBooking = getApplicationStateStore().getBooking();
		updatedBooking = ServiceRepository.getBookingService().saveAndLoad(updatedBooking);
		getApplicationStateStore().setBooking(updatedBooking);
		
		BudgetBook budgetBook;
		try {
			budgetBook = ServiceRepository.getBudgetBookService().load(getApplicationStateStore().getBudgetBook().getId());
		} catch (NotFoundExcpetion e) {
			return "Aktualisierung konnte nicht abgeschlossen werden!";
		}
		getApplicationStateStore().setBudgetBook(budgetBook);
		return StringUtil.EMPTY;
	}

	@Override
	protected String getSubmitWaitMessage() {
		return "Buchung wird aktualisiert...";
	}

	@Override
	protected BookingCreateValidator createValidator() {
		 return new BookingCreateValidator(getModel());
	}

	@Override
	protected Booking createModel() {
		return getApplicationStateStore().getBooking();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_booking_details;
	}
	
	public void showDatePickerDialog(View v) {
		dateFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void changeDatePickerButton(){
		datePickerButton.setText(dateFragment.getDate().toString());
	}
	
	private void initEventHandling() {
		datePickerButton.setOnClickListener(new DatePickerButtonListener());
		changeMode.setOnClickListener(new ChangeModeButtonListener());
	}

	
	// -------------------------------------------------------------------------
	// Inner Classes
	// -------------------------------------------------------------------------

	private final class DatePickerButtonListener implements OnClickListener {

		public void onClick(View aView) {
			showDatePickerDialog(aView);
		}
	}
	
	private final class ChangeModeButtonListener implements OnClickListener {

		public void onClick(View aView) {
			//toggle enable/disable
			if(editMode){
				editMode = false;
			}else {
				editMode = true;
			}
			enableDisableElements();
		}
	}

}
