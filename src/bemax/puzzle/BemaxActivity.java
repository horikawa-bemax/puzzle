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

	/**
	 * コンストラクタ
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* bemax_layout を適用する */
		setContentView(R.layout.bemax_layout);
		
		/* ホームページへのボタンの初期設定 */
		webButton = (Button)findViewById(R.id.hpButton);
		webButton.setOnClickListener(this);
		
		/* facebookへのボタンの初期設定 */
		fbButton = (Button)findViewById(R.id.fbButton);
		fbButton.setOnClickListener(this);
	}

	/**
	 * ボタンが押されたときの処理
	 */
	public void onClick(View v) {
		/* ホームページへのボタンが押された場合 */
		if(v == webButton){
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.be-max.ac.jp/"));
			startActivity(intent);

		}
		/* facebookへのボタンが押された場合 */
		else if(v == fbButton){
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/bemax.jp"));
			startActivity(intent);
		}
	}
}
