package pt.traincompany.searchHistory;

import java.util.ArrayList;

import pt.traincompany.main.R;
import pt.traincompany.search.SearchResult;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyHistory extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_history);

		ArrayList<SearchQuery> a = readDatabase();

	}

	public ArrayList<SearchQuery> readDatabase() {

		ArrayList<SearchQuery> history = new ArrayList<SearchQuery>();

		SearchHistoryHelper helper = new SearchHistoryHelper(MyHistory.this);
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query("SearchHistory",
				new String[] { "departure, arrival, hours, minutes" }, "departure IS NOT NULL AND arrival IS NOT NULL", null, null,
				null, "date DESC");

		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				SearchQuery s = new SearchQuery(cursor.getString(0),
						cursor.getColumnName(1), cursor.getColumnName(2));
				history.add(s);
			}
		}

		db.close();

		return history;

	}

}
