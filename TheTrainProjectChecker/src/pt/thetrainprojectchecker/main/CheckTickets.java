package pt.thetrainprojectchecker.main;

import pt.thetrainprojectchecker.database.DatabaseHelper;
import pt.thetrainprojectchecker.result.Result;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CheckTickets extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_tickets);

		Button qrCode = (Button) findViewById(R.id.btnQRCode);
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
	
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
               
                DatabaseHelper databaseHelper = new DatabaseHelper(CheckTickets.this);
				SQLiteDatabase db = databaseHelper.getWritableDatabase();
				
				Cursor cursor = db.query("Ticket", new String[] {"id"}, "id = ?", new String[] {contents}, null, null, null);
				Bundle bundle = new Bundle();
				if (cursor.moveToFirst())
					bundle.putBoolean("success", true);
				else
					bundle.putBoolean("success", false);
				db.close();
				Intent i = new Intent(CheckTickets.this, Result.class);
				i.putExtras(bundle);
				startActivity(i);  
            }
        }
	}

}
