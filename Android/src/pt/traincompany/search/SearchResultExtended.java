package pt.traincompany.search;

import org.json.JSONArray;
import org.json.JSONException;

import pt.traincompany.main.R;
import pt.traincompany.utility.Utility;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultExtended extends Activity {

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
			
			for (int i = 0; i < stations.length(); i += 2) {
				//double price_temp = stations.getDouble(i);
				JSONArray stations_temp = stations.getJSONArray(i+1);
				
				for (int j = 0; j < stations_temp.length(); j++) {

					

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
				
				if(stations.length() > 2 && i+2 != stations.length()){
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
	}
}
