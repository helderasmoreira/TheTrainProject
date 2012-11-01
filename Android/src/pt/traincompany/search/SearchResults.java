package pt.traincompany.search;

import pt.traincompany.main.R;
import pt.traincompany.utility.Utility;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SearchResults extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);

		SearchResultAdapter adapter = new SearchResultAdapter(this, R.layout.search_result_row,
				Utility.search_data);

		ListView list = (ListView) findViewById(R.id.search_results);

		View header = (View) getLayoutInflater().inflate(
				R.layout.search_result_header, null);

		list.addHeaderView(header);
		list.setAdapter(adapter);
	}
}
