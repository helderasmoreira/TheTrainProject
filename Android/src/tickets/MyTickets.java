package tickets;

import pt.traincompany.main.R;
import pt.traincompany.main.R.layout;
import pt.traincompany.main.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyTickets extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_tickets, menu);
        return true;
    }
}
