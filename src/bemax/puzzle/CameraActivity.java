package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class CameraActivity extends Activity implements OnClickListener, PictureCallback{
	CameraView mCameraView;
	Button shuttorButton;
	Bitmap image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		mCameraView  = (CameraView)findViewById(R.id.cameraView);
		
		shuttorButton = (Button)findViewById(R.id.shuttor_button);
		shuttorButton.setOnClickListener(this);
		
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
		Log.d("PicturSize","w="+b.getWidth()+":h="+b.getHeight());
		Matrix m = new Matrix();
		m.setScale(800.0f/b.getWidth(), 800.0f/b.getWidth());
		m.postRotate(90, 250, 250);
		image = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false);
		Intent intent = new Intent();
		Log.d("ImageSize","w="+image.getWidth()+":h="+image.getHeight());
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
