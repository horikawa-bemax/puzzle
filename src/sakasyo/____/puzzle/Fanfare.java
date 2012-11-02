package sakasyo.____.puzzle;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * ファンファーレクラス
 * @author Masaaki Horikawa
 */
public class Fanfare extends Thread {
	private Context context;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> seMap;
	
	/* コンストラクタ */
	public Fanfare(Context con){
		context = con;
		
		/* サウンド関連初期化 */
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		seMap = new HashMap<Integer, Integer>();
		seMap.put(R.raw.fanfare, soundPool.load(context, R.raw.fanfare, 1));
		seMap.put(R.raw.kansei, soundPool.load(context, R.raw.kansei, 1));
	}
	
	/**
	 * スレッド本体
	 */
	public void run(){
		/* SE再生 */
		soundPool.play(seMap.get(R.raw.fanfare), 0.5f, 0.5f, 0, 0, 1.0f);
		soundPool.play(seMap.get(R.raw.kansei), 0.5f, 0.5f, 0, 0, 1.0f);
		
		/* 5秒間停止 */
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/* SE停止 */
		soundPool.stop(seMap.get(R.raw.fanfare));
		soundPool.stop(seMap.get(R.raw.kansei));
	}
}
