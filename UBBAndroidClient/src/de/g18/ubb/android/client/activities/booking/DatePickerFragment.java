package de.g18.ubb.android.client.activities.booking;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.utils.UBBConstants;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private Date date;

	private Button datePickerButton;


	public void setDate(Date aDate) {
		date = aDate;
	}

	public Date getDate() {
	    return date;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
	    int month = c.get(Calendar.MONTH);
	    int day = c.get(Calendar.DAY_OF_MONTH);

	    return new BookingDatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month, day);
		setDate(cal.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat(UBBConstants.DATE_FORMAT);
		datePickerButton = (Button) getActivity().findViewById(R.BookingCreate.datePicker_Button);
		datePickerButton.setText(sdf.format(getDate()));
	}

}