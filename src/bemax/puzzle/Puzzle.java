package bemax.puzzle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class Puzzle extends Thread implements SurfaceHolder.Callback, OnTouchListener{
	private SurfaceView puzView;
	private SurfaceHolder holder;
	private Rect viewRect;
	private boolean loop;
	
	public Puzzle(SurfaceView v){
		puzView = v;
		holder = puzView.getHolder();
		holder.addCallback(this);
	}
	
	public void run(){
		Paint white_paint = new Paint();
		white_paint.setColor(Color.WHITE);
		
		while(loop){
			Canvas canvas = holder.lockCanvas();
			canvas.drawRect(viewRect, white_paint);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO 変更されたviewの幅と高さに合わせる
		viewRect = new Rect(0, 0, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO スレッドを作成する
		loop = true;
		this.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO スレッドを終了させる
		loop = false;
		this.stop();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 画面にタッチされた時の処理を書く
		return true;
	}
}
