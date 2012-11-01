package pt.traincompany.account;

import pt.traincompany.main.R;
import android.os.Bundle;
import android.app.Activity;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_management);

		Card card_data[] = new Card[] { new Card("132456"),
				new Card("11232456"), new Card("132123456"),
				new Card("132432156"), new Card("13241356"), };

		CardAdapter adapter = new CardAdapter(this, R.layout.creditcard_row,
				R.drawable.ic_launcher, card_data);

		ListView list = (ListView) findViewById(R.id.creditCards);
		list.setAdapter(adapter);
		
		 final Button addCard = (Button) findViewById(R.id.btnAddCard);
		 addCard.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent myIntent = new Intent(CardManagement.this, AddCard.class);
					CardManagement.this.startActivity(myIntent);
				}
			});
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_card_management, menu);
		return true;
	}
}
