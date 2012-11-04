package pt.traincompany.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import pt.traincompany.utility.Utility;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultExtended extends Activity {
	
	
	private ArrayList<Integer> route_ids = new ArrayList<Integer>();
	private ArrayList<String> stop_starts = new ArrayList<String>();
	private ArrayList<String> stop_ends = new ArrayList<String>();
	ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		setContentView(R.layout.activity_search_result);

		setTitle(bundle.getString("from") + " > " + bundle.getString("to"));

		try {
			int pos = bundle.getInt("position");

			JSONArray info = (JSONArray) Utility.search_results.get(pos);

			TextView from = (TextView) findViewById(R.id.search_result2_from);
			TextView to = (TextView) findViewById(R.id.search_result2_to);
			TextView duration = (TextView) findViewById(R.id.search_result2_duration);
			TextView price = (TextView) findViewById(R.id.search_result2_price);

			from.setText(info.getString(1));
			to.setText(info.getString(2));
			duration.setText(info.getString(3));
			price.setText(info.getDouble(4) + "€");

			JSONArray stations = (JSONArray) info.get(5);
			TableLayout tl = (TableLayout) findViewById(R.id.search_results2_stations);
			
			for (int i = 0; i < stations.length(); i += 3) {
				route_ids.add(stations.getInt(i));
				//double price_temp = stations.getDouble(i+1);
				//int route_id = stations.getDouble(i);
				JSONArray stations_temp = stations.getJSONArray(i+2);
				
				for (int j = 0; j < stations_temp.length(); j++) {
					if(j == 0)
						stop_starts.add(((JSONArray) stations_temp.get(j)).getString(0));
					else if(j == stations_temp.length()-1)
						stop_ends.add(((JSONArray) stations_temp.get(j)).getString(0));
					
					TableRow tr = new TableRow(this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					tr.setGravity(Gravity.CENTER_HORIZONTAL);

					TextView b = new TextView(this);
					b.setText(((JSONArray) stations_temp.get(j)).getString(0));
					b.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					tr.addView(b);
					
					TextView b2 = new TextView(this);
					b2.setText(((JSONArray) stations_temp.get(j)).getString(1));
					b2.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					tr.addView(b2);

					tl.addView(tr,
							new TableLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
				}
				
				if(stations.length() > 3 && i+3 != stations.length()){
					TableRow tr = new TableRow(this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					tr.setGravity(Gravity.CENTER_HORIZONTAL);

					TextView b = new TextView(this);
					b.setText("Transição entre comboios...");
					b.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));

					tr.addView(b);

					tl.addView(tr,
							new TableLayout.LayoutParams(
									LayoutParams.WRAP_CONTENT,
									LayoutParams.WRAP_CONTENT));
				}
				
			}

		} catch (JSONException e) {
			Toast.makeText(SearchResultExtended.this, "Alguma coisa correu mal, tente novamente...", Toast.LENGTH_LONG).show();
			this.finish();
		}
	
		Button buyTicket = (Button) findViewById(R.id.btnBuyTicket);
		buyTicket.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        dialog = ProgressDialog.show(SearchResultExtended.this, "", 
		                "A comunicar com o servidor...", true);
				final Calendar c = Calendar.getInstance();
		        int mYear = c.get(Calendar.YEAR);
		        int mMonth = c.get(Calendar.MONTH);
		        int mDay = c.get(Calendar.DAY_OF_MONTH);
		        c.set(mYear, mMonth, mDay);
		        
		        DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerTicket);
		        
		        final Calendar c2 = Calendar.getInstance();
		        c2.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		        
		        if(c2.after(c)) {
		        	CheckTicket checkTicket = new CheckTicket(c2);
					new Thread(checkTicket).start();
		        }
		        else {
		        	makeToast("Não pode comprar bilhete para essa data...");
		        }
		        	
			}
		});
	}
	
	class CheckTicket implements Runnable {
		Calendar c2;
		
		public CheckTicket(Calendar c2) {
			this.c2 = c2;
		}

		public void run() {
			
			for(int i = 0; i < route_ids.size(); i++)
			{
				Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY).buildUpon();
				uri.path(Configurations.VERIFYTICKET)
					.appendQueryParameter("route_id", route_ids.get(i) + "")
					.appendQueryParameter("stop_start", stop_starts.get(i) + "")
					.appendQueryParameter("stop_end", stop_ends.get(i) + "")
					.appendQueryParameter("date",c2.get(Calendar.DAY_OF_MONTH) + "-" + c2.get(Calendar.MONTH) + "-" + c2.get(Calendar.YEAR) + "-")
					.appendQueryParameter("format", Configurations.FORMAT).build();
				
				String response = null;
				try {
					response = Connection.getJSONLine(uri.build());
					JSONArray response_array =new JSONArray(response);
					if(response_array.getString(0).equals("no")) {
						makeToast("Já não há bilhetes para esta viagem...");
						return;
					}
				}
				catch(Exception e) {
					makeToast("A comunicação com o servidor falhou...");
				}
			}

			makeToast("Pode comprar!");
			// compra bilhete
			
		}
	}
	
	private void makeToast(final String message) {
		runOnUiThread(new Runnable() {
			public void run() {
				if(dialog != null) dialog.dismiss();
				Toast.makeText(SearchResultExtended.this, message, Toast.LENGTH_LONG).show();
		}});
	}
}
