package bemax.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebBackForwardList;
import android.widget.Button;

public class BemaxActivity extends Activity implements OnClickListener{
	private Button webButton, fbButton;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bemax_layout);
		webButton = (Button)findViewById(R.id.hpButton);
		webButton.setOnClickListener(this);
		fbButton = (Button)findViewById(R.id.fbButton);
		fbButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == webButton){
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.be-max.ac.jp/"));
			startActivity(intent);

		}else if(v == fbButton){
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/bemax.jp"));
			startActivity(intent);
		}
	}

}
