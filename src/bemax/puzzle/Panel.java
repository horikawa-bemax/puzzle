package bemax.puzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Panel {
	private int num;			// パネル番号
	private Bitmap image;
	private final Rect rect;
	private boolean move;
	
	public Panel(int n, Bitmap img){
		num = n;
		image = img;
		rect = new Rect(0, 0, img.getWidth(), img.getHeight());
		move = false;

		Canvas canvas = new Canvas(image);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rect.left, rect.top, rect.right-1, rect.bottom-1, paint);
	}
	
	Bitmap getImage(){
		return image;
	}
	
	void setMove(boolean b){
		move= b;
	}
	
	boolean getMove(){
		return move;
	}
}
