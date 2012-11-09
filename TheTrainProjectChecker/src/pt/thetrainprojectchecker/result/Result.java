package pt.thetrainprojectchecker.result;

import pt.thetrainprojectchecker.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class Result extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        TextView tx = (TextView) findViewById(R.id.editText1);
        if (getIntent().getExtras().getBoolean("success"))
        	tx.setText("Success!");
        else
        	tx.setText("Inuccess!");
    }
}
