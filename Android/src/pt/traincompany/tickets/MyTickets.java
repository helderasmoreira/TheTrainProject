package pt.traincompany.tickets;

import pt.traincompany.main.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MyTickets extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tickets);

		TabHost tabHost = getTabHost();

		TabSpec unpaid = tabHost.newTabSpec("unpaid");
		unpaid.setIndicator("Unpaid",
				getResources().getDrawable(R.drawable.ic_launcher));
		Intent i = new Intent(this, MyTicketsUnpaid.class);
		unpaid.setContent(i);

		TabSpec paid = tabHost.newTabSpec("paid");
		paid.setIndicator("Paid",
				getResources().getDrawable(R.drawable.ic_launcher));
		Intent i2 = new Intent(this, MyTicketsPaid.class);
		paid.setContent(i2);

		tabHost.addTab(paid);
		tabHost.addTab(unpaid);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_my_tickets_paid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.load_paid_from_server:
			getTabHost().setCurrentTab(0);
			MyTicketsPaid activity = (MyTicketsPaid) this.getCurrentActivity();
			activity.loadFromServer();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
