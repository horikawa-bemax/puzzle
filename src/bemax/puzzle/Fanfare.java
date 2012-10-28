package bemax.puzzle;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Fanfare extends Thread {
	private Context context;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> seMap;
	
	public Fanfare(Context con){
		context = con;
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		seMap = new HashMap<Integer, Integer>();
		seMap.put(R.raw.fanfare, soundPool.load(context, R.raw.fanfare, 1));
		seMap.put(R.raw.kansei, soundPool.load(context, R.raw.kansei, 1));
	}
	
	public void run(){
		soundPool.play(seMap.get(R.raw.fanfare), 0.5f, 0.5f, 0, 0, 1.0f);
		soundPool.play(seMap.get(R.raw.kansei), 0.5f, 0.5f, 0, 0, 1.0f);
		
		/* 2秒間停止 */
		try {
			this.wait(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
