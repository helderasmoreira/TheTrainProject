package pt.thetrainprojectchecker.main;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        Button check = (Button) findViewById(R.id.btnCheckTickets);
        check.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent i = new Intent(Home.this, CheckTickets.class);
				startActivity(i);
			}
        });
    }
	
}
