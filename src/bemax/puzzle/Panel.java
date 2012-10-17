package bemax.puzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * パズルに組み込まれる一つひとつのパネルクラス
 * @author horikawa
 */
class Panel {
	private int num;			// パネル番号
	private Bitmap image;
	private final Rect rect;
	private boolean move;

	/**
	 * コンストラクタ
	 * @param number パネル番号
	 * @param img 画像データ
	 */
	public Panel(int number, Bitmap img){
		/* インスタンス変数初期化 */
		num = number;
		image = img;
		rect = new Rect(0, 0, img.getWidth(), img.getHeight());
		move = false;

		/* パネル画像に黒の枠線を描く */
		Canvas canvas = new Canvas(image);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rect.left, rect.top, rect.right-1, rect.bottom-1, paint);
	}

	/**
	 * インスタンスの画像データを返す
	 * @return 画像データ
	 */
	Bitmap getImage(){
		return image;
	}

	/**
	 * インスタンスの移動中情報をセットする
	 * @param b 移動状態ならtrue
	 */
	void setMove(boolean b){
		move= b;
	}

	/**
	 * インスタンスの移動状態を返す
	 * @return 移動状態ならtrue
	 */
	boolean getMove(){
		return move;
	}

	/**
	 * インスタンスのパネル番号を返す
	 * @return パネル番号
	 */
	int getNum(){
		return num;
	}
}
