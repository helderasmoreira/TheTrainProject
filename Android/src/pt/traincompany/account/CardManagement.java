package pt.traincompany.account;

import pt.traincompany.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_card_management, menu);
		return true;
	}

	public void removeHandler(View v) {

		RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
		TextView child = (TextView) vwParentRow.getChildAt(0);
		Toast.makeText(getApplicationContext(), child.getText(),
				Toast.LENGTH_SHORT).show();

	}
}
