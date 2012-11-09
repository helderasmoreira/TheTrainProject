package pt.traincompany.tickets;

import pt.traincompany.main.R;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MyTickets extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tickets);

		TabHost tabHost = getTabHost();

		TabSpec unpaid = tabHost.newTabSpec("unpaid");
		unpaid.setIndicator("Por pagar",
				null);
		Intent i = new Intent(this, MyTicketsUnpaid.class);
		unpaid.setContent(i);

		TabSpec paid = tabHost.newTabSpec("paid");
		paid.setIndicator("Pagos",
				null);
		Intent i2 = new Intent(this, MyTicketsPaid.class);
		paid.setContent(i2);

		tabHost.addTab(paid);
		tabHost.addTab(unpaid);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabName) {
				if (tabName.equals("unpaid")) {
					ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
					if (activeNetworkInfo == null) {
						getTabHost().setCurrentTab(0);
					}
				}
			}
		});

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
