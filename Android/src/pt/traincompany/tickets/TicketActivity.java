package pt.traincompany.tickets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);

		Ticket ticket = (Ticket) getIntent().getExtras().get("ticket");

		TextView id = (TextView) findViewById(R.id.id);
		id.setText(ticket.id + "");

		TextView departure = (TextView) findViewById(R.id.departureTime);
		departure.setText(ticket.departureTime + "");

		TextView arrival = (TextView) findViewById(R.id.arrivalTime);
		arrival.setText(ticket.arrivalTime + "");

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

		ImageView qrCode = (ImageView) findViewById(R.id.qrCode);
		URL newurl;
		try {
			newurl = new URL(Configurations.QRCODE + ticket.id);
			Bitmap bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			qrCode.setImageBitmap(bitmap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_ticket, menu);
		return true;
	}
}
