package pt.thetrainprojectchecker.main;

import org.json.JSONArray;
import org.json.JSONObject;


import pt.thetrainprojectchecker.database.DatabaseHelper;
import pt.thetrainprojectchecker.utility.Configurations;
import pt.thetrainprojectchecker.utility.Connection;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity { 
	
	ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        ImageView check = (ImageView) findViewById(R.id.btnCheckTickets);
        check.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent i = new Intent(Home.this, CheckTickets.class);
				startActivity(i);
			}
        });
        
        ImageView getTickets = (ImageView) findViewById(R.id.btnGetTickets);
        getTickets.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				dialog = ProgressDialog.show(Home.this, "",
						"A comunicar com o servidor...", true);
				dialog.setCancelable(true);
				GetTicketsByUserIdFromServer thread = new GetTicketsByUserIdFromServer();
				new Thread(thread).start();
			}
        });
    }
    
    class GetTicketsByUserIdFromServer implements Runnable {

		public void run() {

			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.GETTICKETS);
			uri.appendQueryParameter("format", Configurations.FORMAT);

			String response = null;

			try {

				response = Connection.getJSONLine(uri.build());
				JSONArray info = new JSONArray(response);
				
				DatabaseHelper databaseHelper = new DatabaseHelper(Home.this);
				SQLiteDatabase db = databaseHelper.getWritableDatabase();
				db.execSQL("DELETE FROM Ticket;");
				
				for (int i = 0; i < info.length(); i++) {
					JSONObject ticket = info.getJSONObject(i);

					ContentValues value = new ContentValues();
					value.put("id", ticket.getInt("id"));
					value.put("date", ticket.getString("date"));
					value.put("departure", ticket.getString("departure"));
					value.put("arrival", ticket.getString("arrival"));
					value.put("duration", ticket.getString("duration"));
					value.put("price", ticket.getDouble("price"));
					value.put("departureTime", ticket.getString("departureTime"));
					value.put("arrivalTime", ticket.getString("arrivalTime"));
					
					db.insert("Ticket", null, value);
					
				}

				db.close();
			
				runOnUiThread(new Runnable() {
					public void run() {
						dialog.dismiss();
						Toast.makeText(Home.this, "Dados carregados com sucesso", Toast.LENGTH_SHORT).show();
					}
				});

			} catch (Exception e) {
				communicationProblem();
			}
		}

	}
    
    private void communicationProblem() {
		dialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(Home.this,
						"A comunicação com o servidor falhou...",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
}
