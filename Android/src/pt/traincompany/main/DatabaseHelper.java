package pt.traincompany.main;

import java.util.Calendar;

import pt.traincompany.utility.Configurations;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public long offset = 0;
	public final String CREATESEARCHHISTORY = "CREATE TABLE SearchHistory (id INTEGER PRIMARY KEY AUTOINCREMENT, departure TEXT, arrival TEXT, hours TEXT, date BIGINT)";
	public final String CREATETICKET = "CREATE TABLE Ticket (id INTEGER PRIMARY KEY, date TEXT, from TEXT, to TEXT, duration TEXT, price DOUBLE, departureTime TEXT, arrivalTime TEXT)";
	public final String FILLDUMMY = "INSERT INTO SearchHistory(departure, arrival, hours, date) VALUES (NULL, NULL, NULL, NULL,"+  offset + ")";
	
	public DatabaseHelper(Context context) {
		super(context, Configurations.THE_TRAIN_PROJECT_DB, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATESEARCHHISTORY);
		db.execSQL(CREATETICKET);
		for(int i = 1; i<=20; i++) {
			offset = Calendar.getInstance().getTimeInMillis()+i;
			 String FILLDUMMY = "INSERT INTO SearchHistory(departure, arrival, hours, date) VALUES (NULL, NULL, NULL,"+  offset + ")";
			db.execSQL(FILLDUMMY);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS SearchHistory");
		db.execSQL("DROP TABLE IF EXISTS Ticket");
		onCreate(db);
	}
}
