package pt.thetrainprojectchecker.result;

import pt.thetrainprojectchecker.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        ImageView tx = (ImageView) findViewById(R.id.editText1);
        if (getIntent().getExtras().getBoolean("success"))
        	tx.setImageResource(R.drawable.approve);
        else
        	tx.setImageResource(R.drawable.disapprove);
    }
}
