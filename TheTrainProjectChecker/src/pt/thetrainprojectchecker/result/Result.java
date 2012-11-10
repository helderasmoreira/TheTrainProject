package pt.thetrainprojectchecker.result;

import pt.thetrainprojectchecker.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class Result extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Bundle bundle = getIntent().getExtras();
		ImageView tx = (ImageView) findViewById(R.id.editText1);
		if (bundle.getBoolean("success")) {
			tx.setImageResource(R.drawable.approve);

			TextView departure = (TextView) findViewById(R.id.departureTime);
			departure.setText(bundle.getString("departureTime"));

			TextView arrival = (TextView) findViewById(R.id.arrivalTime);
			arrival.setText(bundle.getString("arrivalTime"));

			TextView data = (TextView) findViewById(R.id.date);
			data.setText(bundle.getString("date"));

			TextView from = (TextView) findViewById(R.id.from);
			from.setText(bundle.getString("departure"));

			TextView to = (TextView) findViewById(R.id.to);
			to.setText(bundle.getString("arrival"));

			TextView price = (TextView) findViewById(R.id.price);
			price.setText(bundle.getString("price") + " €");

			TextView duration = (TextView) findViewById(R.id.duration);
			duration.setText(bundle.getString("duration"));

			TextView id = (TextView) findViewById(R.id.id);
			id.setText(bundle.getString("id"));

		} else {
			tx.setImageResource(R.drawable.disapprove);
			TableLayout tl = (TableLayout) findViewById(R.id.tableLayout1);
			tl.setVisibility(View.GONE);
		}
	}
}
