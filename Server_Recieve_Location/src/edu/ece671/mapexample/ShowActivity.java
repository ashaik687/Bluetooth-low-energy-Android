package edu.ece671.mapexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowActivity extends Activity {

	TextView emer;
	TextView ppl;
	TextView pll;
	TextView closebeak;
	TextView time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
	
	
		Bundle myBundle = getIntent().getExtras();
	
		emer= (TextView)findViewById(R.id.emer);
		ppl= (TextView)findViewById(R.id.ppl);
		pll= (TextView)findViewById(R.id.pll);
		closebeak= (TextView)findViewById(R.id.closeb);
	

		String x = myBundle.getString("closebeak");
		closebeak.setText(x);
		emer.setText(myBundle.getString("emergen"));
		pll.setText(myBundle.getString("prior"));
		ppl.setText(myBundle.getString("vict"));
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
