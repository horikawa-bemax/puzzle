package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * メインアクティビティ
 * @author horikawa
 */
public class MainActivity extends Activity implements OnClickListener, OnTouchListener{
	private SurfaceView puzView;
	private ImageView bunner;
	private Button button;
	private Puzzle puzzle;

	/**
	 * このアクティビティが作成されたとき
	 */
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* パズルビューの初期化 */


        /* ボタンの初期化 */



        /* バナーの初期化 */



        /* パズルを作成 */

    }

    /**
     * オプションメニューが生成されたとき
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * このアクティビティが終了したとき
     */
    protected void onDestroy() {
		super.onDestroy();
		Log.d("Activity-Action","Destroy");

		/* パズルのメインルーチンを終了させる */
		puzzle.setLoop(false);
	}

	/**
     * ボタンがクリックされたとき
     */
	public void onClick(View v) {
		/* パズルをシャッフルする */
		puzzle.shuffle();
	}

	/**
	 * バナーがタッチされたとき
	 */
	public boolean onTouch(View v, MotionEvent event) {
		/* いったんパズルを終了 */
		puzzle.setLoop(false);

		/* ビーマックスActivityを呼び出す */
		Intent intent = new Intent(this, BemaxActivity.class);
		startActivity(intent);

		return false;
	}

	/* アクティビティの状態をログに書き出すメソッド群 */

	protected void onStart() {
		super.onStart();
		Log.d("Activity-Action","Start");
	}

	protected void onStop() {
		super.onStop();
		Log.d("Activity-Action","Stop");
	}

	protected void onPause() {
		super.onPause();
		Log.d("Activity-Action","Pause");
	}

	protected void onRestart() {
		super.onRestart();
		Log.d("Activity-Action","Restart");
	}

	protected void onResume() {
		super.onResume();
		Log.d("Activity-Action","Resume");
	}
}
