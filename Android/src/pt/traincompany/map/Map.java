package pt.traincompany.map;

import pt.traincompany.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Map extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map);
		WebView wb = (WebView) findViewById(R.id.webView1);
		wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.loadUrl("file:///android_asset/mapa_rede.png");
	}
}
