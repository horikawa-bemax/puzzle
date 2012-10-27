package bemax.puzzle;

import java.util.HashMap;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener, OnMenuItemClickListener{
	private SurfaceView puzView;
	private Button button;
	private Puzzle puzzle;
	private SoundPool soundEffect;
	private MediaPlayer bgmPlayer;
	private HashMap<String, Integer> seMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* パズル用ビューをレイアウトから取得 */
		puzView = (SurfaceView)findViewById(R.id.puzzle_view);

		/* シャッフルボタンをレイアウトから取得 */
		button = (Button)findViewById(R.id.reset_button);

		/* パズルオブジェクトを初期化 */
		puzzle = new Puzzle(puzView);

		/* シャッフルボタンにリスナーを追加 */
		button.setOnClickListener(this);
		
		/* サウンドエフェクト初期化 */
		soundEffect = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		seMap = new HashMap<String, Integer>();
		seMap.put("歓声", soundEffect.load(this, R.raw.muci_fan_10, 1));
		seMap.put("ファンファーレ", soundEffect.load(this, R.raw.hito_ge_kansei02, 1));
		seMap.put("bemax_menu", soundEffect.load(this, R.raw.botan_b07, 1));
	}

	public void onClick(View v) {
		puzzle.shuffle();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id){
			/* ギャラリーメニューがタッチされた時 */	
			case R.id.gallery_menu:
				
				
				break;
				
			/* ビーマックスメニューがタッチされた時 */
			case R.id.bemax_menu:
				/* ビーマックスActivityを呼び出す */
				Intent intent = new Intent(this, BemaxActivity.class);
				startActivity(intent);
				
				/* 効果音再生 */
				soundEffect.play(seMap.get("bemax_menu"), 0.5f, 0.5f, 0, 0, 1);
				
				break;
			default:
		}
		return true;
	}

	public boolean onMenuItemClick(MenuItem item) {
		return false;
	}
}
