package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
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
		Log.d("Activity-Action","Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* パズルの初期化 */
        puzView = (SurfaceView)findViewById(R.id.puzzle_view);
        puzzle = new Puzzle(puzView);

        /* ボタンの初期化 */
        button = (Button)findViewById(R.id.reset_button);
        button.setOnClickListener(this);

        /* バナーの初期化 */
        bunner = (ImageView)findViewById(R.id.bunner);
        bunner.setOnTouchListener(this);
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
		Log.d("Activity-Action","Destroy");

		/* パズルのメインルーチンを終了させる */
		puzzle.setLoop(false);
		super.onDestroy();
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
		/* ビーマックスActivityを呼び出す */
		Intent intent = new Intent();
		intent.setClassName("bemax.puzzle", "bemax.puzzle.BemaxActivity");
		startActivity(intent);

		return false;
	}

	/* アクティビティの状態をログに書き出すメソッド群 */

	protected void onStart() {
		Log.d("Activity-Action","Start");
		super.onStart();
	}

	protected void onStop() {
		Log.d("Activity-Action","Stop");
		super.onStop();
	}

	protected void onPause() {
		Log.d("Activity-Action","Pause");
		super.onPause();
	}

	protected void onRestart() {
		Log.d("Activity-Action","Restart");
		super.onRestart();
	}

	protected void onResume() {
		Log.d("Activity-Action","Resume");
		super.onResume();
	}
}
