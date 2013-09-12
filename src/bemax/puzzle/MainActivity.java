package bemax.puzzle;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		/* mediaplayerの音量を本体ボタンで可能にする */
		setVolumeControlStream(AudioManager.STREAM_MUSIC);  

		/* パズル用ビューをレイアウトから取得 */
		puzView = (SurfaceView)findViewById(R.id.puzzle_view);

		/* シャッフルボタンをレイアウトから取得 */
		button = (Button)findViewById(R.id.reset_button);

		/* パズルオブジェクトを初期化 */
		puzzle = new Puzzle(puzView);

		/* シャッフルボタンにリスナーを追加 */
		button.setOnClickListener(this);

		/* サウンドエフェクト初期化 */
		soundEffect = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		seMap = new HashMap<Integer, Integer>();
		seMap.put(R.raw.menu, soundEffect.load(this, R.raw.menu, 1));
		
	}
	
	/**
	 * クリックリスナ
	 */
	public void onClick(View v) {
		/* クリックされたのがシャッフルボタンならば、puzzleをシャッフルする */
		if(v.getId() == button.getId()){
			puzzle.shuffle();
		}
	}

	/**
	 * オプションメニューが作成されたときに実行される
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		/* リソースからメニューを読み込む */
		getMenuInflater().inflate(R.menu.main_menu, menu);
		
		return true;
	}

	/**
	 * オプションメニューが選択されたとき
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;

		switch(id){
			/* オプションメニューがタッチされた時 */
			case R.id.mode_select:
				/* モードセレクト画面に遷移する */
				intent = new Intent(this, ModeSelectActivity.class);
				startActivityForResult(intent, 1001);

				/* 効果音再生 */
				soundEffect.play(seMap.get(R.raw.menu), 0.5f, 0.5f, 0, 0, 1);

				break;
			/* ビーマックスメニューがタッチされた時 */
			case R.id.bemax_menu:
				/* ビーマックス説明画面に遷移する */
				intent = new Intent(this, BemaxActivity.class);
				startActivity(intent);

				/* 効果音再生 */
				soundEffect.play(seMap.get(R.raw.menu), 0.5f, 0.5f, 0, 0, 1);

				break;
			case R.id.camera:
				intent = new Intent(this, CameraActivity.class);
				Log.d("puzViewSize","w="+puzView.getWidth()+":h="+puzView.getHeight());
				int[] size = new int[2];
				size[0] = puzView.getWidth();
				size[1] = puzView.getHeight();
				intent.putExtra("aaa", size);
				startActivityForResult(intent, 2002);
				break;
			default:
		}
		
		return true;
	}

	/**
	 * 呼び出したアクティビティから返信があった時の処理
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("ResultCode",""+resultCode);
		/* 呼び出したアクティビティで分ける */
		switch(requestCode){
			/* モードセレクトアクティビティからの返信 */
			case 1001:
				/* 返信が成功したかどうかで分ける */
				if(resultCode == RESULT_OK){
					/* インテントからデータを取得 */
					int dim = data.getIntExtra("mode", 1);
					
					/* Puzzleのdimensionを更新 */
					puzzle.setDimension(dim);
					
					/* puzzleの初期化 */
					puzzle.init();
				}
				break;
			case 2002:
				if(resultCode == RESULT_OK){
					File f = new File(Environment.getExternalStorageDirectory()+File.separator+"puzzle"+File.separator+"puz_bitmap.jpg");
					Bitmap image = BitmapFactory.decodeFile(f.getPath());
					Matrix m = new Matrix();
					m.setScale((float)puzzle.width/image.getWidth(), (float)puzzle.height/image.getWidth());
					m.postRotate(90);
					image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, false);
					image = Bitmap.createBitmap(image, 0, 0, puzzle.width, puzzle.height);
					if(image != null){
						puzzle.init2(image);
						puzzle.init();
					}
				}
		}
	}
}
