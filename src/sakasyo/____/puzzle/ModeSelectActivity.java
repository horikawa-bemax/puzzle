package sakasyo.____.puzzle;

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

public class ModeSelectActivity extends Activity implements OnItemClickListener{
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private Puzzle puzzle;
	private SoundPool soundEffect;
	private HashMap<Integer, Integer> seMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* レイアウトを適用 */
		setContentView(R.layout.option_menu);

		/* リストビューを取得 */
		listView = (ListView)findViewById(R.id.mode_list);

		/* リスト項目を追加 */
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapter.add(getString(R.string.mode_3x3));
		adapter.add(getString(R.string.mode_4x4));
		adapter.add(getString(R.string.mode_5x5));
		listView.setAdapter(adapter);

		/* イベントリスナを設定 */
		listView.setOnItemClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		/* サウンドエフェクト初期化 */
		soundEffect = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		seMap = new HashMap<Integer, Integer>();
		seMap.put(R.raw.menu, soundEffect.load(this, R.raw.menu, 1));

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();

		/* クリック位置に従って戻り値をセット */
		switch(position){
		case 0:
			intent.putExtra("mode", 3);
			break;
		case 1:
			intent.putExtra("mode", 4);
			break;
		case 2:
			intent.putExtra("mode", 5);
			break;
		}
		setResult(RESULT_OK, intent);

		/* 効果音を鳴らす */
		soundEffect.play(seMap.get(R.raw.menu), 0.3f, 0.3f, 0, 0, 1.0f);

		/* オプションメニュー終了 */
		finish();
	}

}
