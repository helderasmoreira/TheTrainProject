package pt.thetrainprojectchecker.main;

import pt.thetrainprojectchecker.database.DatabaseHelper;
import pt.thetrainprojectchecker.result.Result;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class CheckTickets extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_tickets);

		ImageView qrCode = (ImageView) findViewById(R.id.btnQRCode);
		qrCode.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);
				} catch (Exception e) {

					Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
					Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
					startActivity(marketIntent);
				}
			}
		});
		
		ImageView manualSearch = (ImageView) findViewById(R.id.btnSearch);
		manualSearch.setOnClickListener(new OnClickListener() {


			public void onClick(View v) {

				final EditText input = new EditText(CheckTickets.this);
				
				new AlertDialog.Builder(CheckTickets.this)
			    .setTitle("Pesquisa manual")
			    .setMessage("Insira o ID do bilhete")
			    .setView(input)
			    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			         public void onClick(DialogInterface dialog, int whichButton) {
			             testTicket(input.getText().toString()); 

			         }
			    }).show();
			}
			
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                testTicket(contents);  
            }
        }
	}

	private void testTicket(String contents) {
		DatabaseHelper databaseHelper = new DatabaseHelper(CheckTickets.this);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		
		Cursor cursor = db.query("Ticket", new String[] {"departure, arrival, departureTime, arrivalTime, duration, price, date, id"}, "id = ?", new String[] {contents}, null, null, null);
		Bundle bundle = new Bundle();
		if (cursor.moveToFirst()) {
			bundle.putBoolean("success", true);
			bundle.putString("departure", cursor.getString(0));
			bundle.putString("arrival", cursor.getString(1));
			bundle.putString("departureTime", cursor.getString(2));
			bundle.putString("arrivalTime", cursor.getString(3));
			bundle.putString("duration", cursor.getString(4));
			bundle.putString("price", cursor.getString(5));
			bundle.putString("date", cursor.getString(6));
			bundle.putString("id", cursor.getString(7));
		}
		else
			bundle.putBoolean("success", false);
		db.close();
		Intent i = new Intent(CheckTickets.this, Result.class);
		i.putExtras(bundle);
		startActivity(i);
	}

}
