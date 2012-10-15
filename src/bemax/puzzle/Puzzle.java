package bemax.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class Puzzle extends Thread implements SurfaceHolder.Callback, OnTouchListener{
	private SurfaceView puzView;
	private SurfaceHolder holder;
	private Rect puzRect, picRect;
	private boolean loop, touch;
	private Bitmap picture, puzpic;
	private Panel[] panels, map;
	private int startX, startY, num, dx, dy;
	private int quat, blank;

	public Puzzle(SurfaceView v){
		puzView = v;
		holder = puzView.getHolder();
		holder.addCallback(this);
		puzView.setOnTouchListener(this);

		picture = BitmapFactory.decodeResource(puzView.getResources(), R.drawable.picture);
		picRect = new Rect(0, 0, picture.getWidth(), picture.getHeight());
		
		panels = new Panel[16];
		map = new Panel[16];
	}

	public void run(){
		Paint frame_paint = new Paint();
		frame_paint.setColor(Color.BLACK);
		frame_paint.setStyle(Paint.Style.STROKE);

		for(int i=0; i<panels.length-1; i++){
			Bitmap bmp = Bitmap.createBitmap(puzpic, i%4*quat, i/4*quat, quat, quat);
			panels[i] = new Panel(i, bmp);
			map[i] = panels[i];
		}
		blank = 15;
		
		Rect r = new Rect(0, 0, quat, quat);

		while(loop){
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.WHITE);

			for(int i=0; i<map.length; i++){
				if(i==blank){
					continue;
				}
				if(map[i].getMove() && num==i){
					canvas.drawBitmap(map[i].getImage(), i%4*quat + dx, i/4*quat + dy, null);
				}else{
					canvas.drawBitmap(map[i].getImage(), i%4*quat, i/4*quat, null);
				}
			}

			canvas.drawRect(puzRect.left, puzRect.top, puzRect.right-1, puzRect.bottom-1, frame_paint);

			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO 変更されたviewの幅と高さに合わせる
		if(width<height){
			quat = width / 4;
		}else{
			quat = height / 4;
		}
		
		puzRect = new Rect(0, 0, quat*4, quat*4);
		puzpic = Bitmap.createBitmap(puzRect.width(), puzRect.height(), Config.ARGB_8888);
		Canvas c = new Canvas(puzpic);
		c.drawBitmap(picture, picRect, puzRect, null);
		
		this.start();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO スレッドを作成する
		loop = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO スレッドを終了させる
		loop = false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO 画面にタッチされた時の処理を書く
		int x = (int)event.getX();
		int y = (int)event.getY();

		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX = x;
			startY = y;
			int c = x / quat;
			int r = y / quat;
			num = r * 4 + c;
			if(num>=0 && num < 16 && num!=blank && (num-4==blank || num+1==blank || num+4==blank || num-1==blank)){
				map[num].setMove(true);
				dx = dy = 0;
				touch = true;	
			}else{
				touch = false;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(map[num].getMove()){
				if(num/4>0 && num-4==blank){
					if(y>startY){
						y = startY;
					}
					if(y<startY-quat){
						y = startY - quat;
					}
					dx = 0;
					dy = y - startY;
				}else	if(num%4<3  && num+1==blank){
					if(x<startX){
						x = startX;
					}
					if(x>startX + quat){
						x = startX + quat;
					}
					dx = x - startX;
					dy = 0;
				}else	if(num/4<3 && num+4==blank){
					if(y<startY){
						y = startY;
					}
					if(y>startY + quat){
						y = startY + quat;
					}
					dx = 0;
					dy = y - startY;
				}else if(num%4>0 && num-1==blank){
					if(x>startX){
						x = startX;
					}
					if(x<startX - quat){
						x = startX - quat;
					}
					dx = x - startX;
					dy = 0;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			int xx = startX + dx;
			int yy = startY + dy;
			int a = yy/quat*4+xx/quat;
			map[num].setMove(false);
			if(a==blank){
				swap(num, a);
				blank = num;
			}
			touch = false;
		}
		return touch;
	}
	
	void swap(int a, int b){
		Panel p;
		p = map[a];
		map[a] = map[b];
		map[b] = p;
	}
}
