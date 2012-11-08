package pt.traincompany.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import pt.traincompany.main.R;
import pt.traincompany.searchHistory.SearchHistoryHelper;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import pt.traincompany.utility.Utility;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Search extends Activity {
	
	ProgressDialog dialog;
	TimePicker dp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		// configs timepicker
		TimePicker hour = (TimePicker) findViewById(R.id.timePicker1);
		hour.setIs24HourView(true);

		// adds stations
		dialog = ProgressDialog.show(Search.this, "", 
                "A comunicar com o servidor...", true);
		GetStations gs = new GetStations();
    	new Thread(gs).start();
    	
    	dp = (TimePicker) findViewById(R.id.timePicker1);
		dp.setCurrentHour(0);
		dp.setCurrentMinute(0);
		
    	Button search = (Button) findViewById(R.id.btnSearch);
    	search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.show();
				SearchServer search = new SearchServer();
				new Thread(search).start();
			}
		});
	}
	
	public void updateDatabase() {

		SearchHistoryHelper helper = new SearchHistoryHelper(Search.this);

		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("departure", (String) ((Spinner) findViewById(R.id.spinner1)).getSelectedItem());
		cv.put("arrival", (String) ((Spinner) findViewById(R.id.spinner2)).getSelectedItem());
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, dp.getCurrentHour());
		c.set(Calendar.MINUTE, dp.getCurrentMinute());

		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
		cv.put("hours", tf.format(c.getTime()));
		cv.put("date",Calendar.getInstance().getTimeInMillis());
		
		db.update("SearchHistory", cv, "date = (SELECT min(date) FROM SearchHistory)", null);

		db.close();
	}
	
	class SearchServer implements Runnable {

		public void run() {
			String from = (String) ((Spinner) findViewById(R.id.spinner1)).getSelectedItem();
			String to = (String) ((Spinner) findViewById(R.id.spinner2)).getSelectedItem();
			if(from.equals(to)) {
				dialog.dismiss();
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(Search.this, "A estação de origem não pode ser igual à de destino.", Toast.LENGTH_SHORT).show();
				}});
				return;
			}
			updateDatabase();
				
			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY).buildUpon();
			uri.path(Configurations.GETROUTE)
				.appendQueryParameter("from", from)
				.appendQueryParameter("to", to)
				.appendQueryParameter("hour", dp.getCurrentHour()+":"+dp.getCurrentMinute())
				.appendQueryParameter("format", Configurations.FORMAT).build();
			
			String response = null;
			try {
				response = Connection.getJSONLine(uri.build());
				Utility.search_results = new JSONArray(response);
				if(Utility.search_results.length() == 0)
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(Search.this, "Não foram encontradas viagens...", Toast.LENGTH_SHORT).show();	
						}});
				else {
					Utility.search_data = new SearchResult[Utility.search_results.length()];
	
					for(int i = 0; i < Utility.search_results.length(); i++) {
						JSONArray route = (JSONArray) Utility.search_results.get(i);
						Utility.search_data[i] = new SearchResult(route.getString(1), route.getString(2), route.getString(3), route.getDouble(4));
					}
					
					Bundle b = new Bundle();
					b.putString("from", from);
					b.putString("to", to);
					
					Intent myIntent = new Intent(Search.this, SearchResults.class);
					myIntent.putExtras(b);
					Search.this.startActivity(myIntent);
				}
			}
			catch(Exception e) {
				communicationProblem();
			}
			
			dialog.dismiss();
			
			
		}

	}
	
	private void communicationProblem() {
		dialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(Search.this, "A comunicação com o servidor falhou...", Toast.LENGTH_LONG).show();
		}});
	}
	
	class GetStations implements Runnable {
	
		public void run() {
			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY).buildUpon();
			uri.path(Configurations.GETSTATIONS)
					.appendQueryParameter("format", Configurations.FORMAT).build();

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());
				JSONArray info = new JSONArray(response);
				List<String> stations = new ArrayList<String>();
				for(int i = 0; i < info.length(); i++) {
					stations.add((String) info.get(i));
				}
				
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search.this,
						android.R.layout.simple_spinner_item, stations);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				runOnUiThread(new Runnable() {
					public void run() {
						Spinner spinner = (Spinner) findViewById(R.id.spinner1);
						spinner.setAdapter(adapter);
						
						Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
						spinner2.setAdapter(adapter);
						
						dialog.dismiss();
				}});
				
			} catch (Exception e) { 
				communicationProblem();
				Search.this.finish();
			}
		}
	}
}
