package bemax.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private Rect viewRect, picRect;
	private boolean loop, touch;
	private Bitmap picture;
	private Panel[] panels;
	private int startX, startY, oldX, oldY, column, row, num;
	
	public Puzzle(SurfaceView v){
		puzView = v;
		holder = puzView.getHolder();
		holder.addCallback(this);
		puzView.setOnTouchListener(this);
		
		picture = BitmapFactory.decodeResource(puzView.getResources(), R.drawable.picture);
		picRect = new Rect(0, 0, picture.getWidth(), picture.getHeight());
	}
	
	public void run(){
		Paint frame_paint = new Paint();
		frame_paint.setColor(Color.BLACK);
		frame_paint.setStyle(Paint.Style.STROKE);
		
		panels = new Panel[16];
		for(int i=0; i<16; i++){
			panels[i] = new Panel(i, picture);
		}
		
		Rect r = new Rect(0, 0, 120, 120);
		
		while(loop){
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.WHITE);
			
			for(int i=0; i<panels.length; i++){
				if(panels[i].getNum()==15) continue;
				canvas.drawBitmap(panels[i].getImage(), r, panels[i].getRect(), null);
			}
			
			canvas.drawRect(picRect, frame_paint);
			
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
		int dx, dy;
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(!picRect.contains(x, y)){
			return false;
		}
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX = oldX = x;
			startY = oldY = y;
			column = startX / 120;
			row = startY / 120;
			num = row*4+column;
			if(panels[num].getNum()==15){
				touch = false;
			}else{
				touch = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(row>0 && panels[num-4].getNum()==15){
				if(y>startY){
					y = startY;
				}
				if(y<startY-120){
					y = startY - 120;
				}
				dy = y - oldY;
				panels[num].getRect().offset(0, dy);
			}
			if(column<3 && panels[num+1].getNum()==15){
				if(x<startX){
					x = startX;
				}
				if(x>startX + 120){
					x = startX + 120;
				}
				dx = x - oldX;
				panels[num].getRect().offset(dx, 0);
			}
			if(row<3 && panels[num+4].getNum()==15){
				if(y<startY){
					y = startY;
				}
				if(y>startY + 120){
					y = startY + 120;
				}
				dy = y - oldY;
				panels[num].getRect().offset(0, dy);
			}
			if(column>0 && panels[num-1].getNum()==15){
				if(x>startX){
					x = startX;
				}
				if(x<startX - 120){
					x = startX - 120;
				}
				dx = x - oldX;
				panels[num].getRect().offset(dx, 0);
			}
			oldX = x;
			oldY = y;
			break;
		case MotionEvent.ACTION_UP:
			int cx = panels[num].getRect().centerX();
			int cy = panels[num].getRect().centerY();
			if(column == cx / 120 && row == cy / 120){
				panels[num].resetRect(column, row);
			}
			if(row > cy / 120){
				Panel p = panels[num];
				p.moveUp();
				panels[num-4].moveDown();
				panels[num] = panels[num-4];
				panels[num-4] = p;
			}
			if(column < cx / 120){
				Panel p = panels[num];
				p.moveRight();
				panels[num+1].moveLeft();
				panels[num] = panels[num+1];
				panels[num+1] = p;
			}
			if(row < cy / 120){
				Panel p = panels[num];
				p.moveDown();
				panels[num+4].moveUp();
				panels[num] = panels[num+4];
				panels[num+4] = p;
			}
			if(column > cx / 120){
				Panel p = panels[num];
				p.moveLeft();
				panels[num-1].moveRight();
				panels[num] = panels[num-1];
				panels[num-1] = p;
			}
			touch = false;
		}
		return touch;
	}
}
