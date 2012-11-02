package sakasyo.xxx.puzzle;

import java.util.HashMap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * メインアクティビティ
 * @author Masaaki Horikawa
 */
public class MainActivity extends Activity implements OnClickListener{
	private SurfaceView puzView;
	private Button button;
	private Puzzle puzzle;
	private SoundPool soundEffect;
	private HashMap<Integer, Integer> seMap;
	private final int MODE_SELECT_CODE = 1001;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		/* パズル用ビューをレイアウトから取得 */


		/* シャッフルボタンをレイアウトから取得 */


		/* パズルオブジェクトを初期化 */


		/* シャッフルボタンにリスナーを追加 */


		/* サウンドエフェクト初期化 */

		
		
	}

	/**
	 * クリックリスナ
	 */
	public void onClick(View v) {
		/* クリックされたのがシャッフルボタンならば、puzzleをシャッフルする */

		
		
	}

	/**
	 * オプションメニューが作成されたときに実行される
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		/* リソースからメニューを読み込む */

		
		return true;
	}

	/**
	 * オプションメニューが選択されたとき
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;

		switch(id){
			/* モードセレクトがタッチされた時 */
			case R.id.mode_select:
				/* モードセレクト画面に遷移する */

				

				/* 効果音再生 */


				break;
			/* ビーマックスメニューがタッチされた時 */
			case R.id.bemax_menu:
				/* ビーマックス説明画面に遷移する */
				intent = new Intent(this, BemaxActivity.class);
				startActivity(intent);

				/* 効果音再生 */
				soundEffect.play(seMap.get(R.raw.menu), 0.5f, 0.5f, 0, 0, 1);

				break;
			default:
		}
		
		return true;
	}

	/**
	 * 呼び出したアクティビティから返信があった時の処理
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/* 呼び出したアクティビティで分ける */
		switch(requestCode){
			/* モードセレクトアクティビティからの返信 */
			case MODE_SELECT_CODE:
				/* 返信が成功したかどうかで分ける */
				if( ){
					/* インテントからデータを取得 */
					
					
					/* Puzzleのdimensionを更新 */
					
					
					/* puzzleの初期化 */
					
				}
				break;
		}
	}
}
