package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
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

		return false;
	}
}
