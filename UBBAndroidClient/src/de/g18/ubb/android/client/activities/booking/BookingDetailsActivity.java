package de.g18.ubb.android.client.activities.booking;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.R.layout;
import de.g18.ubb.android.client.R.menu;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.shared.adapter.CategoryAdapter;
import de.g18.ubb.android.client.shared.adapter.EnumAdapter;
import de.g18.ubb.android.client.utils.UBBConstants;
import de.g18.ubb.common.domain.Booking;
import de.g18.ubb.common.domain.Category;
import de.g18.ubb.common.domain.enumType.BookingType;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.StringUtil;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.text.Spanned;

public class BookingDetailsActivity extends AbstractValidationFormularActivity<Booking, BookingCreateValidator> {

	private EditText bookingName;
	private EditText bookingAmount;
	private EditText bookingCreateUser;
	private EditText bookingChangeUser;
	private EditText bookingCreateTime;
	private EditText bookingChangeTime;
	private DatePickerFragment dateFragment;
	private CategoryAdapter categoryAdapter;
	private SimpleDateFormat sdf;
	private EnumAdapter<BookingType> bookingTypeAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        dateFragment = new DatePickerFragment();
		dateFragment.setDate(Calendar.getInstance().getTime());
		sdf = new SimpleDateFormat(UBBConstants.DATE_FORMAT);
		
        categoryAdapter = new CategoryAdapter(this, getAllCategorysForCurrentBudgetBook());

        List<BookingType> enumList = Arrays.asList(BookingType.values());
        bookingTypeAdapter = new EnumAdapter<BookingType>(this, enumList);

        
        initComponents();
        initBindings();
    }

    private void initComponents() {
    	bookingName = (EditText) findViewById(R.BookingDetail.name_input);
    	bookingName.setText(getModel().getBookingName());
//    	bookingName.setFilters(new InputFilter[] {
//    		    new InputFilter() {
//    		        public CharSequence filter(CharSequence src, int start,
//    		                int end, Spanned dst, int dstart, int dend) {
//    		                return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
//    		        }
//    		    }
//    		});
    	
    	bookingAmount = (EditText) findViewById(R.BookingDetail.betrag_input);
    	bookingAmount.setText(String.valueOf(getModel().getAmount()));
    	
    	bookingCreateUser = (EditText) findViewById(R.BookingDetail.create_user_input);
    	bookingCreateUser.setText(getModel().getCreateUser().getName());
    	
    	bookingChangeUser = (EditText) findViewById(R.BookingDetail.change_user_input);
    	
    	if(getModel().getChangeUser() != null){
    		bookingChangeUser.setText(getModel().getChangeUser().getName());
    	}
    	
    	bookingCreateTime = (EditText) findViewById(R.BookingDetail.create_time_input);
    	bookingCreateTime.setText(getModel().getCreateTime().toString());
    	
    	bookingChangeTime = (EditText) findViewById(R.BookingDetail.changed_time_input);
    	if(getModel().getChangeTime() != null){
    		bookingChangeTime.setText(getModel().getChangeTime().toString());
    	}
    	
    	 Spinner categorySpinner = (Spinner) findViewById(R.BookingDetail.category_spinner);
         categorySpinner.setAdapter(categoryAdapter);

         Spinner bookingTypeSpinner = (Spinner) findViewById(R.BookingDetail.booking_type_spinner);
         bookingTypeSpinner.setAdapter(bookingTypeAdapter);
         //vorauswahl einbauen
         bookingTypeSpinner.setSelection(getModel().getType().ordinal());
    }
    
    private void initBindings() {
	    bind(Booking.PROPERTY_AMOUNT, R.BookingDetail.betrag_input);
        bind(Booking.PROPERTY_CATEGORY, R.BookingDetail.category_spinner);
        bind(Booking.PROPERTY_TYPE, R.BookingDetail.booking_type_spinner);
        bind(Booking.PROPERTY_BOOKING_NAME, R.BookingDetail.name_input);
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
		ServiceRepository.getBookingService().saveAndLoad(updatedBooking);
		return StringUtil.EMPTY;
	}

	@Override
	protected String getSubmitWaitMessage() {
		return "Buchung wird erstellt...";
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

}
