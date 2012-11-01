package pt.traincompany.tickets;

import pt.traincompany.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyTickets extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tickets);

		Ticket ticket_data[] = new Ticket[] {
				new Ticket(1,"9:00", "9:10","21/11", "Porto", 10, "S. Bento", 30.0, false),
				new Ticket(2,"9:00","9:10","22/11", "Porte", 10, "S. Benta", 20.0, true),
				new Ticket(3,"9:00","9:10","23/11", "Porta", 10, "S. Bente", 10.0, false), };

		TicketAdapter adapter = new TicketAdapter(this, R.layout.ticket_row,
				ticket_data);

		final ListView list = (ListView) findViewById(R.id.myTickets);
 
		View header = (View) getLayoutInflater().inflate(
				R.layout.ticket_header, null);

		list.addHeaderView(header);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position > 0) {
					Intent i = new Intent(MyTickets.this, TicketActivity.class);
					i.putExtra("ticket", (Ticket) list.getItemAtPosition(position));
					startActivity(i);
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_my_tickets, menu);
		return true;
	}
}
