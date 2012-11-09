package pt.traincompany.tickets;

import java.util.ArrayList;

import pt.traincompany.main.DatabaseHelper;
import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyTicketsPaid extends Activity {

	ProgressDialog dialog;
	ArrayList<Ticket> userTickets = new ArrayList<Ticket>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tickets_paid);

		dialog = ProgressDialog.show(MyTicketsPaid.this, "",
				"A comunicar com a base de dados local...", true);
		dialog.setCancelable(true);

		GetTicketsByUserId tickets = new GetTicketsByUserId();
		new Thread(tickets).start();

		final ListView list = (ListView) findViewById(R.id.myTicketsPaid);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position > 0) {
					Intent i = new Intent(MyTicketsPaid.this,
							TicketActivity.class);
					i.putExtra("ticket",
							(Ticket) list.getItemAtPosition(position));
					startActivity(i);
				}
			}
		});
		
		View header = (View) getLayoutInflater().inflate(
				R.layout.ticket_header, null);
		
		list.addHeaderView(header);

	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		dialog = ProgressDialog.show(MyTicketsPaid.this, "",
				"A comunicar com a base de dados local...", true);
		dialog.setCancelable(true);

		GetTicketsByUserId tickets = new GetTicketsByUserId();
		new Thread(tickets).start();

		final ListView list = (ListView) findViewById(R.id.myTicketsPaid);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position > 0) {
					Intent i = new Intent(MyTicketsPaid.this,
							TicketActivity.class);
					i.putExtra("ticket",
							(Ticket) list.getItemAtPosition(position));
					startActivity(i);
				}
			}
		});
	}

	class GetTicketsByUserId implements Runnable {

		public void run() {
			
			userTickets = new ArrayList<Ticket>();
			
			// FILL USER TICKETS FROM DB
			DatabaseHelper helper = Configurations.databaseHelper;
			SQLiteDatabase db = helper.getWritableDatabase();
			Cursor cursor = db
					.query("Ticket",
							new String[] { "id, date, departure, arrival, duration, price, departureTime, arrivalTime" },
							null, null, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					Ticket t = new Ticket(cursor.getInt(0),
							cursor.getString(6), cursor.getString(7),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(4), cursor.getString(3),
							cursor.getDouble(5),true);
					userTickets.add(t);
				} while (cursor.moveToNext());
			}

			db.close();

			runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
					TicketAdapter adapter = new TicketAdapter(
							MyTicketsPaid.this, R.layout.ticket_row,
							userTickets.toArray(new Ticket[userTickets.size()]));

					final ListView list = (ListView) findViewById(R.id.myTicketsPaid);

					list.setAdapter(adapter);
				}
			});

		}

	}

}
