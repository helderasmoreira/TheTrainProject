package pt.traincompany.account;

import java.lang.reflect.Field;

import org.json.JSONObject;

import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import pt.traincompany.utility.Utility;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCard extends Activity {

	ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);

		DatePicker picker = (DatePicker) findViewById(R.id.expiryDate);
		try {
			Field f[] = picker.getClass().getDeclaredFields();
			for (Field field : f) {
				if (field.getName().equals("mDayPicker")) {
					field.setAccessible(true);
					Object dayPicker = field.get(picker);
					((View) dayPicker).setVisibility(View.GONE);
				}
			}
		} catch (SecurityException e) {
			Log.d("ERROR", e.getMessage());
		} catch (IllegalArgumentException e) {
			Log.d("ERROR", e.getMessage());
		} catch (IllegalAccessException e) {
			Log.d("ERROR", e.getMessage());
		}
		

		final Button addCard = (Button) findViewById(R.id.btnAddCard);
		addCard.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				dialog = ProgressDialog.show(AddCard.this, "",
						"A comunicar com o servidor...", true);

				EditText number = (EditText) findViewById(R.id.cardNumber);
				Spinner type = (Spinner) findViewById(R.id.cardType);
				
				DatePicker date = (DatePicker) findViewById(R.id.expiryDate);
				AddCardToUser ac = new AddCardToUser(number.getText().toString(), type.getSelectedItem().toString(), date.getMonth(), date.getYear());
				new Thread(ac).start();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_card, menu);
		return true;
	}
	
	private void communicationProblem() {
		dialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(AddCard.this,
						"A comunicação com o servidor falhou...",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	
	class AddCardToUser implements Runnable {

		public String number, type;
		public int month, year;

		public AddCardToUser(String number, String type, int month, int year) {
			this.number = number;
			this.type = type;
			this.month = month;
			this.year = year;
		}

		public void run() {
			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.ADDCARD);
			uri.appendQueryParameter("format", Configurations.FORMAT);
			uri.appendQueryParameter("number", number);
			uri.appendQueryParameter("type", type);
			uri.appendQueryParameter("user_id", Configurations.userId+"");
			uri.appendQueryParameter("validity", "1-"+month+"-"+year);

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());

				JSONObject info = new JSONObject(response);
				String status = info.getString("status");

				if (status.equals("OK")) {
					
					Card c = new Card(info.getInt("id"), number);
					Utility.user_cards.add(c);
					
					runOnUiThread(new Runnable() {
						public void run() {

							dialog.dismiss();
							Toast.makeText(AddCard.this,
									"Cartão adicinado com sucesso!",
									Toast.LENGTH_LONG).show();
							
							Intent i = new Intent(AddCard.this, CardManagement.class);
							startActivity(i);
						}
					});
				}
				else {
					runOnUiThread(new Runnable() {
						public void run() {
							dialog.dismiss();
							Toast.makeText(AddCard.this,
									"Já existe um cartão com esse número.",
									Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Exception e) {
				communicationProblem();
				AddCard.this.finish();
			}
		}
	}
}
