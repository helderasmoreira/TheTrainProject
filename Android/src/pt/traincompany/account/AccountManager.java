package pt.traincompany.account;

import org.json.JSONObject;

import pt.traincompany.main.R;
import pt.traincompany.utility.Configurations;
import pt.traincompany.utility.Connection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountManager extends Activity {

	ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_manager);
		
		final Button login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				dialog = ProgressDialog.show(AccountManager.this, "",
						"A comunicar com o servidor...", true);

				EditText user = (EditText) findViewById(R.id.user);
				EditText pass = (EditText) findViewById(R.id.password);

				Login login = new Login(user.getText().toString(), pass
						.getText().toString());
				new Thread(login).start();

			}
		});
		
		final Button signup = (Button) findViewById(R.id.btnSignUp);
		signup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				dialog = ProgressDialog.show(AccountManager.this, "",
						"A comunicar com o servidor...", true);

				EditText user = (EditText) findViewById(R.id.user);
				EditText pass = (EditText) findViewById(R.id.password);

				SignUp signup = new SignUp(user.getText().toString(), pass
						.getText().toString());
				new Thread(signup).start();

			}
		});
	}

	private void communicationProblem() {
		dialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(AccountManager.this,
						"A comunicação com o servidor falhou...",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	class Login implements Runnable {

		public String username, password;

		public Login(String username, String password) {
			this.username = username;
			this.password = password;
		}

		public void run() {
			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.LOGIN);
			uri.appendQueryParameter("format", Configurations.FORMAT);
			uri.appendQueryParameter("username", username);
			uri.appendQueryParameter("password", password);

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());

				JSONObject info = new JSONObject(response);
				String status = info.getString("status");

				if (status.equals("OK")) {
					
					Configurations.userId = Integer.parseInt(info.getString("id")); 
					Configurations.username = username;
					Configurations.name = info.getString("name");
					
					Editor editor = PreferenceManager.getDefaultSharedPreferences(AccountManager.this).edit();
			        editor.putInt("userId", Configurations.userId);
			        editor.putString("username", Configurations.username);
			        editor.putString("name", Configurations.name);
			        editor.commit();
							
							
					runOnUiThread(new Runnable() {
						public void run() {

							dialog.dismiss();
							Toast.makeText(AccountManager.this,
									"Login efetuado com sucesso!",
									Toast.LENGTH_SHORT).show();
							
							AccountManager.this.finish();
						}
					});
				}
				else {
					runOnUiThread(new Runnable() {
						public void run() {
							dialog.dismiss();
							Toast.makeText(AccountManager.this,
									"Dados errados! Tente registar-se primeiro.",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			} catch (Exception e) {
				communicationProblem();
			}
		}
	}
	
	class SignUp implements Runnable {

		public String username, password;

		public SignUp(String username, String password) {
			this.username = username;
			this.password = password;
		}

		public void run() {
			Uri.Builder uri = Uri.parse("http://" + Configurations.AUTHORITY)
					.buildUpon();
			uri.path(Configurations.SIGNUP);
			uri.appendQueryParameter("format", Configurations.FORMAT);
			uri.appendQueryParameter("username", username);
			uri.appendQueryParameter("password", password);
			uri.build();

			String response = null;

			try {
				response = Connection.getJSONLine(uri.build());

				JSONObject info = new JSONObject(response);
				String status = info.getString("status");

				if (status.equals("OK")) {
					
					Configurations.userId = Integer.parseInt(info.getString("id")); 
					Configurations.username = username;
					Configurations.name = info.getString("name");
					
					Editor editor = PreferenceManager.getDefaultSharedPreferences(AccountManager.this).edit();
			        editor.putInt("userId", Configurations.userId);
			        editor.putString("username", Configurations.username);
			        editor.putString("name", Configurations.name);
			        editor.commit();
							
					runOnUiThread(new Runnable() {
						public void run() {

							dialog.dismiss();
							Toast.makeText(AccountManager.this,
									"Registo e login efetuado com sucesso!",
									Toast.LENGTH_SHORT).show();
							
							AccountManager.this.finish();
						}
					});
				}
				else {
					runOnUiThread(new Runnable() {
						public void run() {
							dialog.dismiss();
							Toast.makeText(AccountManager.this,
									"Já existe um utilizador com esse username.",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			} catch (Exception e) {
				communicationProblem();
			}
		}
	}
}
