package pt.traincompany.account;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import pt.traincompany.main.Home;
import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import pt.traincompany.utility.Utility;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CardManagement extends Activity {

	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_management);

		if (Utility.user_cards.size() > 0) {
			CardAdapter adapter = new CardAdapter(
					CardManagement.this, R.layout.creditcard_row,
					R.drawable.ic_launcher,
					Utility.user_cards
							.toArray(new Card[Utility.user_cards
									.size()]));

			ListView list = (ListView) findViewById(R.id.creditCards);
			list.setAdapter(adapter);
		} else {
			dialog = ProgressDialog.show(CardManagement.this, "",
					"A comunicar com o servidor...", true);
			GetCardsByUserId get = new GetCardsByUserId();
			new Thread(get).start();
		}
		final Button addCard = (Button) findViewById(R.id.btnAddCard);
		addCard.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(CardManagement.this, AddCard.class);
				CardManagement.this.startActivity(myIntent);
			}
		});

		final Button logout = (Button) findViewById(R.id.btnLogout);
		logout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Configurations.userId = 0;
				Configurations.username = "";
				Configurations.name = "";
				Utility.user_cards = new ArrayList<Card>();

				Toast.makeText(CardManagement.this,
						"Logout efetuado com sucesso.", Toast.LENGTH_LONG)
						.show();

				Intent myIntent = new Intent(CardManagement.this, Home.class);
				CardManagement.this.startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_card_management, menu);
		return true;
	}

	class GetCardsByUserId implements Runnable {

		public void run() {

			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.GETCARDSBYID);
			uri.appendQueryParameter("format", Configurations.FORMAT);
			uri.appendQueryParameter("userId", Configurations.userId + "");

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());

				JSONArray info = new JSONArray(response);

				if (info.length() > 0) {

					for (int i = 0; i < info.length(); i++) {
						JSONObject card = info.getJSONObject(i);
						String number = card.getString("number");
						int id = card.getInt("id");
						Card c = new Card(id, number);
						Utility.user_cards.add(c);
					}

				}

				runOnUiThread(new Runnable() {
					public void run() {
						dialog.dismiss();
						CardAdapter adapter = new CardAdapter(
								CardManagement.this, R.layout.creditcard_row,
								R.drawable.ic_launcher,
								Utility.user_cards
										.toArray(new Card[Utility.user_cards
												.size()]));

						ListView list = (ListView) findViewById(R.id.creditCards);
						list.setAdapter(adapter);
					}
				});
			} catch (Exception e) {
				communicationProblem();
				CardManagement.this.finish();
			}
		}

	}

	private void communicationProblem() {
		dialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(CardManagement.this,
						"A comunicação com o servidor falhou...",
						Toast.LENGTH_LONG).show();
			}
		});
	}
}
