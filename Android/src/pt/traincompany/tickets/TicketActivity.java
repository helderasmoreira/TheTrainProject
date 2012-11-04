package pt.traincompany.tickets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketActivity extends Activity {

	Ticket ticket;
	Bitmap bitmap;
	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);

		Ticket t = (Ticket) getIntent().getExtras().get("ticket");
		this.ticket = t;

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

		ImageView paid = (ImageView) findViewById(R.id.paid);
		if (ticket.paid)
			paid.setImageResource(R.drawable.more);
		else
			paid.setImageResource(R.drawable.ic_launcher);
		
		dialog = ProgressDialog.show(TicketActivity.this, "",
				"A gerar identificador...", true);

		new Thread(new Runnable() {
			public void run() {

				URL newurl;
				try {
					newurl = new URL(Configurations.QRCODE + ticket.id);
					bitmap = BitmapFactory.decodeStream(newurl
							.openConnection().getInputStream());
				} catch (MalformedURLException e) {
					e.printStackTrace();
					dialog.dismiss();
					Toast.makeText(TicketActivity.this,
							"Ocorreu um erro ao gerar o QR Code...",
							Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					e.printStackTrace();
					dialog.dismiss();
					Toast.makeText(TicketActivity.this,
							"Ocorreu um erro ao gerar o QR Code...",
							Toast.LENGTH_LONG).show();
				}

				runOnUiThread(new Runnable() {
					public void run() {
						ImageView qrCode = (ImageView) findViewById(R.id.qrCode);
						qrCode.setImageBitmap(bitmap);
						dialog.dismiss();
					}
				});
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_ticket, menu);
		return true;
	}
}
