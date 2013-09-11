package bemax.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CameraActivity extends Activity implements OnClickListener, PictureCallback{
	CameraView mCameraView;
	Button shuttorButton;
	Bitmap image;
	private int[] puzViewSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		mCameraView  = (CameraView)findViewById(R.id.cameraView);
		
		shuttorButton = (Button)findViewById(R.id.shuttor_button);
		shuttorButton.setOnClickListener(this);
		
		Intent intent = getIntent();
		puzViewSize = intent.getIntArrayExtra("aaa");
		
		image = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		try{
		Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, null);
		int w  = mCameraView.rect.width();
		int h = mCameraView.rect.height();
		image = Bitmap.createScaledBitmap(b, w, h, false);
		Matrix m = new Matrix();
		m.setRotate(90);
		image = Bitmap.createBitmap(image, 0, 0, w, h, m, false);
		image = Bitmap.createBitmap(image, 0, 0, puzViewSize[0], puzViewSize[0]*w/h);
		Log.d("image","w="+image.getWidth()+":h="+image.getHeight());
		Intent intent = new Intent();
		intent.putExtra("image", image);
		setResult(RESULT_OK, intent);
		finish();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.shuttor_button:
			mCameraView.shot(this);
			break;
		}
	}
}
