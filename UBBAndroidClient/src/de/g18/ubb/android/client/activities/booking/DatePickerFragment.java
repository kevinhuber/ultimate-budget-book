package de.g18.ubb.android.client.activities.booking;

import java.util.Calendar;
import java.util.Date;

import de.g18.ubb.android.client.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	public int myYear, myDay, myMonth;
	public Button datePickerButton;
	public BookingStateBucket instance;

	public int getMyYear() {
		return myYear;
	}

	public void setMyYear(int myYear) {
		this.myYear = myYear;
	}

	public int getMyDay() {
		return myDay;
	}

	public void setMyDay(int myDay) {
		this.myDay = myDay;
	}

	public int getMyMonth() {
		return myMonth;
	}

	public void setMyMonth(int myMonth) {
		this.myMonth = myMonth;
	}
	public void setDate(int day, int month, int year){
		setMyDay(day);
		setMyMonth(month);
		setMyYear(year);
	}
	
	//TODO: use long date
	@SuppressWarnings("deprecation")
	public Date getDate(){
		return new Date(getMyYear(), getMyMonth(), getMyDay());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		setDate(day, month, year);
		datePickerButton = (Button)this.getActivity().findViewById(R.BookingCreate.datePicker_Button);
		datePickerButton.setText(this.getDate().toString());
		instance = BookingStateBucket.getInstance();
		instance.setBookingDate(this.getDate());
	}
}