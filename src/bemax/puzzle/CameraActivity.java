package bemax.puzzle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
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
		FileOutputStream out = null;
		try{
			File dir = new File(Environment.getExternalStorageDirectory()+File.separator+"puzzle");
			if(!dir.exists()){
				dir.mkdir();
			}
			out = new FileOutputStream(dir.getPath()+File.separator+"puz_bitmap.jpg");
			out.write(data);
			out.close();
			Intent intent = new Intent();
			setResult(RESULT_OK);
		}catch(Exception e){
			if(out != null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			setResult(2);
		}

		finish();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.shuttor_button:
			mCameraView.shot(this);
			break;
		}
	}
	
	private boolean savePicture(){
		
		
		return false;
	}
}
