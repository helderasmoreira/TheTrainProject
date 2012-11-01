package pt.traincompany.main;

import pt.traincompany.account.AccountManager;
import pt.traincompany.main.R;
import tickets.MyTickets;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		final ImageView search = (ImageView) findViewById(R.id.btnSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "search",
						Toast.LENGTH_SHORT).show();
			}
		});

		final ImageView historic = (ImageView) findViewById(R.id.btnHistoric);
		historic.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "historic",
						Toast.LENGTH_SHORT).show();
			}
		});

		final ImageView myTickets = (ImageView) findViewById(R.id.btnMyTickets);
		myTickets.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Home.this, MyTickets.class);
				Home.this.startActivity(myIntent);
			}
		});

		final ImageView login = (ImageView) findViewById(R.id.btnLogin);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Home.this, AccountManager.class);
				Home.this.startActivity(myIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
}
