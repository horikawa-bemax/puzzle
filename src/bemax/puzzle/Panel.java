package bemax.puzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Panel {
	private int num;			// パネル番号
	private int column;		// 列番号
	private int row;			// 行番号
	private Bitmap image;
	private Rect rect;
	
	public Panel(int n, Bitmap img){
		num = n;
		column = num % 4;
		row = num / 4;
		rect = new Rect();
		resetRect(column, row);
		
		if(num < 15){
			image = Bitmap.createBitmap(img, column * 120, row * 120, 120, 120);
		}else{
			image = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(image);
			c.drawColor(Color.GRAY);
		}
		Canvas canvas = new Canvas(image);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(0, 0, 120, 120, paint);
	}
	
	void moveUp(){
		row--;
		resetRect(column, row);
	}
	
	void moveRight(){
		column++;
		resetRect(column, row);
	}
	
	void moveDown(){
		row++;
		resetRect(column, row);
	}
	
	void moveLeft(){
		column--;
		resetRect(column, row);
	}
	
	void resetRect(int col, int row){
		rect.set(col*120, row*120, (col+1)*120, (row+1)*120);
	}
	
	Rect getRect(){
		return rect;
	}
	
	Bitmap getImage(){
		return image;
	}
	
	int getNum(){
		return num;
	}
}
