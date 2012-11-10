package pt.traincompany.main;

import pt.traincompany.utility.Configurations;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public final String CREATESEARCHHISTORY = "CREATE TABLE SearchHistory (id INTEGER PRIMARY KEY AUTOINCREMENT, departure TEXT, arrival TEXT, hours TEXT, date BIGINT, userId INTEGER)";
	public final String CREATETICKET = "CREATE TABLE Ticket (id INTEGER PRIMARY KEY, date TEXT, departure TEXT, arrival TEXT, duration TEXT, price DOUBLE, departureTime TEXT, arrivalTime TEXT, userId INTEGER)";
	
	
	public DatabaseHelper(Context context) {
		super(context, Configurations.THE_TRAIN_PROJECT_DB, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATESEARCHHISTORY);
		db.execSQL(CREATETICKET);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS SearchHistory");
		db.execSQL("DROP TABLE IF EXISTS Ticket");
		onCreate(db);
	}
}
