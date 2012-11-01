package tickets;

import pt.traincompany.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MyTickets extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tickets);

		Ticket ticket_data[] = new Ticket[] {
				new Ticket("21/11", "Porto", "S. Bento", 30.0, false),
				new Ticket("22/11", "Porte", "S. Benta", 20.0, true),
				new Ticket("23/11", "Porta", "S. Bente", 10.0, false), };

		TicketAdapter adapter = new TicketAdapter(this, R.layout.ticket_row,
				ticket_data);

		ListView list = (ListView) findViewById(R.id.myTickets);
		
		View header = (View)getLayoutInflater().inflate(R.layout.ticket_header, null);
		
		list.addHeaderView(header);
		list.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_my_tickets, menu);
		return true;
	}
}
