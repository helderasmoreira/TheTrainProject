package pt.traincompany.account;

import java.util.ArrayList;
import java.util.HashMap;

import pt.traincompany.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CardManagement extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_management);
        
        ListView list = (ListView) findViewById(R.id.creditCards);
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("creditCardNumber", "123456");
        mylist.add(map);
        map = new HashMap<String, String>();
        map.put("creditCardNumber", "654321");
        mylist.add(map);

        SimpleAdapter adapter = new SimpleAdapter(this, mylist, R.layout.creditcard_row,
                    new String[] {"creditCardNumber", "removeCard"}, new int[] {R.id.creditCardNumber, R.id.removeCard});
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card_management, menu);
        return true;
    }
    
    public void removeHandler(View v) 
    {

        RelativeLayout vwParentRow = (RelativeLayout)v.getParent();
        TextView child = (TextView)vwParentRow.getChildAt(0);
        Toast.makeText(getApplicationContext(), child.getText(),
				Toast.LENGTH_SHORT).show();
      
    }
}
