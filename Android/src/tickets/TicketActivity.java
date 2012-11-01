package tickets;

import pt.traincompany.main.R;
import pt.traincompany.main.R.layout;
import pt.traincompany.main.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class TicketActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        
        Ticket ticket = (Ticket) getIntent().getExtras().get("ticket");
        Toast.makeText(getApplicationContext(), ticket.to,
				Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ticket, menu);
        return true;
    }
}
