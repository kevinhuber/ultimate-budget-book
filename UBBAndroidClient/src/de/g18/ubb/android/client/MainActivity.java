package de.g18.ubb.android.client;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.repository.ServiceRepository;

public class MainActivity extends Activity {

    static {
        ServiceRepository.setProvider(new WebServiceProvider());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new HelloServiceTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class HelloServiceTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            Log.w(getClass().getSimpleName(), "Creating new BudgetBook...");
            BudgetBook b = new BudgetBook();
            b.setName("BudgetBook #" + Math.random());

            Log.w(getClass().getSimpleName(), "Call Service to save BudgetBook (" +  b.getName() + ")...");
            BudgetBookService service = ServiceRepository.getBudgetBookService();
            service.saveAndLoad(b);

            Log.w(getClass().getSimpleName(), "Listing all saved BudgetBooks...");
            List<BudgetBook> books = service.getAll();
            StringBuffer result = new StringBuffer("\n");
            for (BudgetBook book : books) {
                result.append(book.toString() + "\n");
            }
            Log.w(getClass().getSimpleName(), result.toString());
            return result.toString();
        }

        @Override
        protected void onPostExecute(String results) {
            Toast t = Toast.makeText(getApplicationContext(), "msg -> " + results, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
