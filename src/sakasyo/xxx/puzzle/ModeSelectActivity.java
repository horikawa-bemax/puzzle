package sakasyo.xxx.puzzle;

import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * モードセレクトクラス
 * @author Masaaki Horikawa
 */
public class ModeSelectActivity extends Activity implements OnItemClickListener{
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private SoundPool soundEffect;
	private HashMap<Integer, Integer> seMap;

	/**
	 *  アクティビティ作成時
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* レイアウトを適用 */


		/* リストビューを取得 */


		/* リスト項目を追加 */

		
		

		/* イベントリスナを設定 */
		
	}

	/**
	 * アクティビティスタート時
	 */
	protected void onStart() {
		super.onStart();

		/* サウンドエフェクト初期化 */
		
		
		
	}

	/**
	 * メニューアイテムクリック時
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();

		/* クリック位置に従って戻り値をセット */
		switch(position){
			/* 8パネルモード */
			case 0:
				
				break;
			/* 15パネルモード */
			case 1:
				
				break;
			/* 24パネルモード */
			case 2:
				
				break;
		}
		setResult(RESULT_OK, intent);

		/* 効果音を鳴らす */


		/* オプションメニュー終了 */

	}
}
