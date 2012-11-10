package pt.traincompany.account;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import pt.traincompany.main.Home;
import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import pt.traincompany.utility.Utility;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CardManagement extends Activity {

	public ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_management);

		if (Configurations.cardsLoaded) {
			CardAdapter adapter = new CardAdapter(CardManagement.this,
					R.layout.creditcard_row, R.drawable.ic_launcher,
					Utility.user_cards.toArray(new Card[Utility.user_cards
							.size()]));

			ListView list = (ListView) findViewById(R.id.creditCards);
			list.setAdapter(adapter);
		} else {
			dialog = ProgressDialog.show(CardManagement.this, "",
					"A comunicar com o servidor...", true);
			dialog.setCancelable(true);
			GetCardsByUserId get = new GetCardsByUserId();
			new Thread(get).start();
		}
		final Button addCard = (Button) findViewById(R.id.btnAddCard);
		addCard.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				AddCard dialog = new AddCard(CardManagement.this);
				dialog.setContentView(R.layout.activity_add_card);
				dialog.setTitle("Adicionar cartão");
				dialog.show();

			}
		});

		final Button logout = (Button) findViewById(R.id.btnLogout);
		logout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Configurations.userId = 0;
				Configurations.username = "";
				Configurations.name = "";
				Utility.user_cards = new ArrayList<Card>();
				Configurations.cardsLoaded = false;
				
				Editor editor = PreferenceManager.getDefaultSharedPreferences(CardManagement.this).edit();
				editor.clear();
				editor.commit();

				Toast.makeText(CardManagement.this,
						"Logout efetuado com sucesso.", Toast.LENGTH_LONG)
						.show();

				CardManagement.this.finish();
			}
		});
	}

	public void removeCardHandler(final View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				CardManagement.this);

		builder.setPositiveButton("Eliminar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						RelativeLayout vwParentRow = (RelativeLayout) v
								.getParent();
						TextView child = (TextView) vwParentRow.getChildAt(0);

						RemoveCard rc = new RemoveCard(child.getText()
								.toString());
						new Thread(rc).start();
					}
				});

		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});

		builder.setMessage("Tem a certeza que pretende eliminar o cartão?");
		builder.setTitle("Confirmação");

		AlertDialog dialog = builder.create();
		dialog.show();
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

				for (int i = 0; i < info.length(); i++) {
					JSONObject card = info.getJSONObject(i);
					String number = card.getString("number");
					int id = card.getInt("id");
					Card c = new Card(id, number);
					Utility.user_cards.add(c);
				}

				Configurations.cardsLoaded = true;

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

	class RemoveCard implements Runnable {

		public String number;

		public RemoveCard(String number) {
			this.number = number;
		}

		public void run() {

			runOnUiThread(new Runnable() {
				public void run() {
					dialog = ProgressDialog.show(CardManagement.this, "",
							"A comunicar com o servidor...", true);
				}
			});

			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.REMOVECARD);
			uri.appendQueryParameter("format", Configurations.FORMAT);
			uri.appendQueryParameter("number", number);

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());

				JSONObject info = new JSONObject(response);
				String status = info.getString("status");

				if (status.equals("OK")) {

					runOnUiThread(new Runnable() {
						public void run() {

							dialog.dismiss();
							
							for (Card c : Utility.user_cards)
								if (c.number.equals(number))
									Utility.user_cards.remove(c);

							CardAdapter adapter = new CardAdapter(
									CardManagement.this,
									R.layout.creditcard_row,
									R.drawable.ic_launcher,
									Utility.user_cards
											.toArray(new Card[Utility.user_cards
													.size()]));

							ListView list = (ListView) findViewById(R.id.creditCards);
							list.setAdapter(adapter);

							Toast.makeText(CardManagement.this,
									"Cartão removido com sucesso!",
									Toast.LENGTH_LONG).show();

						}
					});
				} else {
					runOnUiThread(new Runnable() {
						public void run() {

							dialog.dismiss();
							Toast.makeText(
									CardManagement.this,
									"Não foi possível remover o cartão. Tente novamente.",
									Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Exception e) {
				communicationProblem();
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
