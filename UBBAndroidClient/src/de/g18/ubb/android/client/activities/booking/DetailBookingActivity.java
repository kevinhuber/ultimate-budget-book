package de.g18.ubb.android.client.activities.booking;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.common.domain.Booking;

public class DetailBookingActivity extends AbstractValidationFormularActivity<Booking, BookingCreateValidator>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_booking, menu);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String submit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSubmitWaitMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BookingCreateValidator createValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Booking createModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
