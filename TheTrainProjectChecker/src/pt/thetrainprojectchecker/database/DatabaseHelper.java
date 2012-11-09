package pt.thetrainprojectchecker.database;

import pt.thetrainprojectchecker.utility.Configurations;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public final String CREATETICKET = "CREATE TABLE Ticket (id INTEGER PRIMARY KEY, date TEXT, departure TEXT, arrival TEXT, duration TEXT, price DOUBLE, departureTime TEXT, arrivalTime TEXT)";
	
	public DatabaseHelper(Context context) {
		super(context, Configurations.THE_TRAIN_PROJECT_CHECKER_DB, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATETICKET);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	
}
