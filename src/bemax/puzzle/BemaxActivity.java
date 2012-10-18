package bemax.puzzle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BemaxActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bemax_layout);
	}

	protected void onStop() {
		super.onStop();
		Log.d("Bemax-activity","Stop");
	}

	protected void onPause() {
		super.onPause();
		Log.d("Bemax-activity","Pause");
		finish();
	}

	protected void onDestroy() {
		Log.d("Bemax-activity","Destroy");
		super.onDestroy();
	}


}
