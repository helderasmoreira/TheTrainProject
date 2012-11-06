package pt.traincompany.searchHistory;

import pt.traincompany.utility.Configurations;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearchHistoryHelper extends SQLiteOpenHelper {

	public final String CREATE = "CREATE TABLE SearchHistory (id INTEGER PRIMARY KEY AUTOINCREMENT, departure TEXT, arrival TEXT, hours INTEGER, minutes INTEGER, date TEXT)";
	public final String FILLDUMMY = "INSERT INTO SearchHistory(departure, arrival, hours, minutes, date) VALUES (NULL, NULL, NULL, NULL, DATETIME('now'))";

	public SearchHistoryHelper(Context context) {
		super(context, Configurations.THE_TRAIN_PROJECT_DB, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
		for(int i = 1; i<20; i++)
			db.execSQL(FILLDUMMY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS SearchHistory");
		onCreate(db);

	}
}
