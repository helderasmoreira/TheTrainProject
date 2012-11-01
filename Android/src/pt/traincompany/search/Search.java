package pt.traincompany.search;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pt.traincompany.main.Home;
import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;

public class Search extends Activity {
	
	ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		// configs timepicker
		TimePicker hour = (TimePicker) findViewById(R.id.timePicker1);
		hour.setIs24HourView(true);

		// adds stations
		dialog = ProgressDialog.show(Search.this, "", 
                "Retrieving information...", true);
		GetStations gs = new GetStations();
    	new Thread(gs).start();
		
    	Button search = (Button) findViewById(R.id.btnSearch);
    	search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.show();
				SearchServer search = new SearchServer();
				new Thread(search).start();
			}
		});
	}
	
	class SearchServer implements Runnable {

		public void run() {
			String from = (String) ((Spinner) findViewById(R.id.spinner1)).getSelectedItem();
			String to = (String) ((Spinner) findViewById(R.id.spinner2)).getSelectedItem();
			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY).buildUpon();
			uri.path(Configurations.GETROUTE)
				.appendQueryParameter("from", from)
				.appendQueryParameter("to", to)
				.appendQueryParameter("format", Configurations.FORMAT).build();
			
			String response = null;
			try {
				response = Connection.getJSONLine(uri.build());
				JSONArray info = new JSONArray(response);
				info = (JSONArray) info.get(0);
			}
			catch(Exception e) {}
			
			dialog.dismiss();
		}
		
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
				
			} catch (Exception e) { }
		}
	}
}
