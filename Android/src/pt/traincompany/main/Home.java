package pt.traincompany.main;

import pt.traincompany.account.AccountManager;
import pt.traincompany.account.CardManagement;
import pt.traincompany.search.Search;
import pt.traincompany.tickets.MyTickets;
import pt.traincompany.utility.Configurations;

import android.os.Bundle;
import android.preference.PreferenceManager;
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
		
		Configurations.userId = PreferenceManager.getDefaultSharedPreferences(Home.this).getInt("userId", 0);
		Configurations.username = PreferenceManager.getDefaultSharedPreferences(Home.this).getString("username", "");
		Configurations.name = PreferenceManager.getDefaultSharedPreferences(Home.this).getString("name", "");

		final ImageView search = (ImageView) findViewById(R.id.btnSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Home.this, Search.class);
				Home.this.startActivity(myIntent);
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
					
				Intent myIntent;
				
				
				if (Configurations.userId > 0)  
					myIntent = new Intent(Home.this, CardManagement.class);
				else
					myIntent = new Intent(Home.this, AccountManager.class);
				
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
