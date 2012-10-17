package bemax.puzzle;

import java.util.Random;

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

/**
 * パズルクラス
 * @author horikawa
 */
public class Puzzle extends Thread implements SurfaceHolder.Callback, OnTouchListener{
	private SurfaceView puzView;
	private SurfaceHolder holder;
	private Rect puzRect, picRect, markRect;
	private boolean loop, touch;
	private Bitmap picture, puzpic, mark, cong;
	private Panel[] panels, map;
	private int startX, startY, num, dx, dy;
	private int quat, blank, mode;
	private boolean visible, comp;
	static final int INIT = 0, PLAY = 1;

	/**
	 * コンストラクタ
	 * @param v サーフェイスビュー
	 */
	public Puzzle(SurfaceView v){
		/* パズル用ビューを受け取る */
		puzView = v;

		/* パズル用ビューがタッチに反応できるようにする */
		holder = puzView.getHolder();
		holder.addCallback(this);
		puzView.setOnTouchListener(this);

		/* パズルの元になる画像をリソースから取り込む */
		picture = BitmapFactory.decodeResource(puzView.getResources(), R.drawable.picture);
		picRect = new Rect(0, 0, picture.getWidth(), picture.getHeight());

		/* パズルの背景となる画像をリソースから取り込む */
		mark = BitmapFactory.decodeResource(puzView.getResources(), R.drawable.mark);
		markRect = new Rect(0, 0, mark.getWidth(), mark.getHeight());

		/* パズル完成時の祝福メッセージ画像をリソースから取り込む */
		cong = BitmapFactory.decodeResource(puzView.getResources(), R.drawable.cong);

		/* パズル制御用の配列を初期化 */
		panels = new Panel[16];
		map = new Panel[16];
	}

	/**
	 * パズルプログラム本体
	 */
	public void run(){
		/* 枠線描画用のペイントを準備する */
		Paint frame_paint = new Paint();
		frame_paint.setColor(Color.BLACK);
		frame_paint.setStyle(Paint.Style.STROKE);

		/* 背景画像描画用のペイントを準備する */
		Paint back_paint = new Paint();
		back_paint.setAlpha(64);

		/* メイン処理ルーチン */
		Rect r = new Rect(0, 0, quat, quat);
		while(loop){
			/* サーフェイスビューのキャンバスをロック */
			Canvas canvas = holder.lockCanvas();

			/* キャンバスを白で塗りつぶす */
			canvas.drawColor(Color.WHITE);

			/* 背景画像を描画する */
			canvas.drawBitmap(mark, markRect, puzRect, back_paint);

			/* パネルを描画する */
			for(int i=0; visible &&  i<map.length; i++){
				if(i==blank){
					continue;
				}
				if(map[i].getMove() && num==i){
					canvas.drawBitmap(map[i].getImage(), i%4*quat + dx, i/4*quat + dy, null);
				}else{
					canvas.drawBitmap(map[i].getImage(), i%4*quat, i/4*quat, null);
				}
			}

			/* 周りの枠線を描画する */
			canvas.drawRect(puzRect.left, puzRect.top, puzRect.right-1, puzRect.bottom-1, frame_paint);

			/*
			 * パズルが完成しているかどうか調べる
			 * 完成していれば、compはtrueのまま抜ける
			 */
			comp = true;
			for(int i=0; i<map.length-1; i++){
				if(map[i]==null || map[i].getNum()!=i){
					comp = false;
					break;
				}
			}

			/* パズルが完成していれば、祝福メッセージを描画する */
			if(comp && mode == PLAY){
				canvas.drawBitmap(cong, puzRect.centerX()-cong.getWidth()/2, puzRect.centerY()-cong.getHeight()/2, null);
			}

			/* キャンバスのロックを解除 */
			holder.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * サーフェイスビューが変更されたとき
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		/* パネル1枚の辺の長さを決める */
		if(width<height){
			quat = width / 4;
		}else{
			quat = height / 4;
		}

		/* パズル画面の大きさを決定 */
		puzRect = new Rect(0, 0, quat*4, quat*4);

		/* パズル用画像を準備する */
		puzpic = Bitmap.createBitmap(puzRect.width(), puzRect.height(), Config.ARGB_8888);
		Canvas c = new Canvas(puzpic);
		c.drawBitmap(picture, picRect, puzRect, null);

		/* パズルの各パーツを初期化 */
		init();

		/* メインプログラムスタート */
		this.start();
	}

	/**
	 * サーフェイスビューが生成されたとき
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		loop = true;
	}

	/**
	 * サーフェイスビューが消去されたとき
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		loop = false;
	}

	/**
	 * サーフェイスビューがタッチされたとき
	 */
	public boolean onTouch(View v, MotionEvent event) {
		/* タッチされた座標を取得 */
		int x = (int)event.getX();
		int y = (int)event.getY();

		/* タッチの内容ごとに処理を分ける */
		switch(event.getAction()){
			/* タッチされた瞬間の処理 */
			case MotionEvent.ACTION_DOWN:
				/* 最初にタッチされた座標を記録 */
				startX = x;
				startY = y;

				/* タッチ座標から、どのパネルがタッチされたか求める */
				int c = x / quat;
				int r = y / quat;
				num = r * 4 + c;

				/* タッチされたパネルが、スライドできるパネルかどうかで分ける */
				if(num>=0 && num < 16 && num!=blank && (num-4==blank || num+1==blank || num+4==blank || num-1==blank)){
					/* パネルのステータスを動作中にする */
					map[num].setMove(true);

					/* パネルの移動量を初期化 */
					dx = dy = 0;

					/* 引き続きタッチイベントを追いかける */
					touch = true;
				}else{
					/* これ以上タッチイベントを追いかけない */
					touch = false;
				}
				break;
			/* タッチがドラッグ中である場合 */
			case MotionEvent.ACTION_MOVE:
				/* パネルが移動状態であるかどうかで分ける */
				if(map[num].getMove()){

					/* パネルが上方向に移動可能である場合 */
					if(num/4>0 && num-4==blank){
						/* 上方向への移動量を計算する */
						if(y>startY){
							y = startY;
						}
						if(y<startY-quat){
							y = startY - quat;
						}
						dx = 0;
						dy = y - startY;
					}
					/* パネルが右方向に移動可能である場合 */
					else	if(num%4<3  && num+1==blank){
						/* 右方向への移動量を計算する */
						if(x<startX){
							x = startX;
						}
						if(x>startX + quat){
							x = startX + quat;
						}
						dx = x - startX;
						dy = 0;
					}
					/* パネルが下方向に移動可能である場合 */
					else	if(num/4<3 && num+4==blank){
						/* 下方向への移動量を計算する */
						if(y<startY){
							y = startY;
						}
						if(y>startY + quat){
							y = startY + quat;
						}
						dx = 0;
						dy = y - startY;
					}
					/* パネルが左方向に移動可能である場合 */
					else if(num%4>0 && num-1==blank){
						/* 左方向への移動量を計算する */
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
			/* タッチが離れた瞬間 */
			case MotionEvent.ACTION_UP:
				/* タッチが離された位置から、パネルの移動先を計算する */
				int xx = startX + dx;
				int yy = startY + dy;
				int a = yy/quat*4+xx/quat;

				/* パネルの移動状態を解除する */
				map[num].setMove(false);

				/* パネルがスライドしているかどうかで分ける */
				if(a==blank){
					/* パネルとブランクを入れ替える */
					swap(num, a);
					blank = num;
				}

				/* タッチイベントをいったん終了 */
				touch = false;
		}
		return touch;
	}

	/**
	 * パネルを入れ替える
	 * @param a 入れ替えるパネルの位置
	 * @param b 入れ替えるパネルの位置
	 */
	void swap(int a, int b){
		/* 入れ替え */
		Panel p;
		p = map[a];
		map[a] = map[b];
		map[b] = p;
	}

	/**
	 * パズルの初期化
	 */
	void init(){
		/* パネル生成 */
		for(int i=0; i<panels.length-1; i++){
			Bitmap bmp = Bitmap.createBitmap(puzpic, i%4*quat, i/4*quat, quat, quat);
			panels[i] = new Panel(i, bmp);
			map[i] = panels[i];
		}
		/* ブランク位置の初期化 */
		blank = 15;

		/* パズルステータスの初期化 */
		visible = true;	//<=パズルを表示できるよ
		comp = false;	//<= パズルはそろってないよ
		mode = INIT;	//<= まだゲームプレイ前だよ
	}

	/**
	 * パネルをシャッフルする
	 */
	void shuffle(){
		/* いったん表示を消す */
		visible = false;	//<=パズルは表示できないよ

		/* ブランクと隣り合うパネルをランダムに入れ替える */
		Random rd = new Random();
		for(int i=0; true; i++){

			/* 200回以上入れ替え かつ ブランクが右下にあれば、シャッフルを終了 */
			if(i>200 && blank==15){
				break;
			}

			/* ブランクと入れ替えるパネルをランダムに決定 */
			int rn = rd.nextInt(4);

			/* 入れ替えるパネルの位置によって分ける */
			switch(rn){
				/* 上のパネルと入れ替える場合 */
				case 0:
					if(blank/4>0){
						swap(blank, blank-4);
						blank = blank - 4;
					}
					break;
				/* 右のパネルと入れ替える場合 */
				case 1:
					if(blank%4<3){
						swap(blank, blank+1);
						blank = blank + 1;
					}
					break;
				/* 下のパネルと入れ替える場合 */
				case 2:
					if(blank/4<3){
						swap(blank, blank+4);
						blank = blank + 4;
					}
					break;
				/* 左のパネルと入れ替える場合 */
				case 3:
					if(blank%4>0){
						swap(blank, blank-1);
						blank = blank - 1;
					}
					break;
			}
		}

		/* パズルのステータスを初期化 */
		visible = true;	//<=パズルは表示できるよ
		comp = false;	//<=パズルはそろってないよ
		mode = PLAY;	//<=パズルをプレイ中だよ
	}
}
