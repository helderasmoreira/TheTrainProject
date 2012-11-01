package pt.traincompany.tickets;

import pt.traincompany.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class TicketActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);

		Ticket ticket = (Ticket) getIntent().getExtras().get("ticket");

		TextView id = (TextView) findViewById(R.id.id);
		id.setText(ticket.id + "");

		TextView data = (TextView) findViewById(R.id.date);
		data.setText(ticket.date);

		TextView from = (TextView) findViewById(R.id.from);
		from.setText(ticket.from);

		TextView to = (TextView) findViewById(R.id.to);
		to.setText(ticket.to);

		TextView price = (TextView) findViewById(R.id.price);
		price.setText(ticket.price + " €");

		TextView duration = (TextView) findViewById(R.id.duration);
		duration.setText(ticket.duration + " min");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_ticket, menu);
		return true;
	}
}
