package pt.traincompany.main;

import org.json.JSONArray;

import pt.traincompany.account.AccountManager;
import pt.traincompany.account.CardManagement;
import pt.traincompany.map.Map;
import pt.traincompany.search.Search;
import pt.traincompany.search.SearchResultExtended;
import pt.traincompany.searchHistory.MyHistory;
import pt.traincompany.searchHistory.SearchHistoryHelper;
import pt.traincompany.tickets.MyTickets;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import pt.traincompany.utility.Utility;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity {
	
	ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);
		
		this.deleteDatabase(Configurations.THE_TRAIN_PROJECT_DB);
		SearchHistoryHelper helper = new SearchHistoryHelper(Home.this);
		Configurations.databaseHelper = helper;
		
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
				SearchHistoryHelper helper = new SearchHistoryHelper(Home.this);
				SQLiteDatabase db = helper.getWritableDatabase();
				Cursor cursor = db.query("SearchHistory",
						new String[] { "departure, arrival, hours, date" }, "departure IS NOT NULL AND arrival IS NOT NULL", null, null,
						null, "date DESC");
				
				if(!cursor.moveToFirst())
					Toast.makeText(Home.this, "Não tem histórico...", Toast.LENGTH_SHORT).show();
				else {
					Intent myIntent = new Intent(Home.this, MyHistory.class);
					Home.this.startActivity(myIntent);
				}
			}
		});

		final ImageView myTickets = (ImageView) findViewById(R.id.btnMyTickets);
		myTickets.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog = ProgressDialog.show(Home.this, "",
						"A comunicar com o servidor...", true);
				GetTicketsByUserId gt = new GetTicketsByUserId();
				new Thread(gt).start();
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
		
		final ImageView about = (ImageView) findViewById(R.id.btnAbout);
		about.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String ms1 = "Esta aplicação foi desenvolvida por Hélder Moreira e Tiago Babo na unidade curricular de Computação Móvel, na Faculdade de Engenharia da Universidade do Porto.";
				new AlertDialog.Builder(Home.this)
						.setTitle("About")
						.setMessage(ms1)
						.setPositiveButton("Fechar",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		});
		
		final ImageView map = (ImageView) findViewById(R.id.btnMap);
		map.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(Home.this, Map.class);
				Home.this.startActivity(myIntent);
			}
		});
		
		if(Utility.from_cancel_ticket) {
			Utility.from_cancel_ticket = false;
			myTickets.performClick();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}
	
	class GetTicketsByUserId implements Runnable {

		public void run() {

			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.GETTICKETSBYID);
			uri.appendQueryParameter("format", Configurations.FORMAT);
			uri.appendQueryParameter("user_id", Configurations.userId + "");

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());
				JSONArray info = new JSONArray(response);
				if(info.length() > 0) {
					Utility.tickets = info;
					dialog.dismiss();
					Intent myIntent = new Intent(Home.this, MyTickets.class);
					Home.this.startActivity(myIntent);
				}
				else {
					makeToast("Não tem bilhetes...");
				}
					
			}
			catch(Exception e) {}
		}
	}
	
	private void makeToast(final String message) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (dialog != null)
					dialog.dismiss();
				Toast.makeText(Home.this, message,
						Toast.LENGTH_LONG).show();
			}
		});
	}
}
