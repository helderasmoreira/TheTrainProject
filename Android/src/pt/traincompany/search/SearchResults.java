package pt.traincompany.search;

import pt.traincompany.main.R;
import pt.traincompany.utility.Utility;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchResults extends Activity {
	
	String from, to;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		
		Bundle bundle = getIntent().getExtras();
		from = bundle.getString("from");
		to = bundle.getString("to");
		
		setTitle(from + " > " + to);
		
		SearchResultAdapter adapter = new SearchResultAdapter(this, R.layout.search_result_row,
				Utility.search_data);

		ListView list = (ListView) findViewById(R.id.search_results);

		View header = (View) getLayoutInflater().inflate(
				R.layout.search_result_header, null);

		list.addHeaderView(header);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position > 0) {
						
					Bundle b = new Bundle();
					b.putInt("position", position-1);
					b.putString("from", from);
					b.putString("to", to);

					Intent i = new Intent(SearchResults.this, SearchResultExtended.class);
					i.putExtras(b);
					startActivity(i);

				}
			}
		});
	}
}
