package bemax.puzzle;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
	Camera mCamera;
	SurfaceHolder holder;
	Bitmap image;
	Rect rect;
	
	public CameraView(Context context) {
		super(context);
		init();// TODO 自動生成されたコンストラクター・スタブ
	}

	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();// TODO 自動生成されたコンストラクター・スタブ
	}

	public CameraView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();// TODO 自動生成されたコンストラクター・スタブ
	}

	private void init(){
		holder = getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		rect = new Rect(0,0,width,height);
		if(mCamera != null){
			Parameters mParam = mCamera.getParameters();
		    List<Size> sizes = mParam.getSupportedPreviewSizes();
		    int previewWidth = width;
		    int previewHeight = width;
		    int tmpHeight = 0;
		    int tmpWidth = 0;
		    for (Size size : sizes) {
		        if ((size.width > previewWidth) || (size.height > previewHeight)) {
		            continue;
		        }
		        if (tmpHeight < size.height) {
		            tmpWidth = size.width;
		            tmpHeight = size.height;
		        }
		    }
		    previewWidth = tmpWidth;
		    previewHeight = tmpHeight;
		    Log.d("PreviewSize","w="+previewWidth+":h="+previewHeight);
		 
		    mParam.setPreviewSize(previewHeight, previewWidth);
		    
		    ViewGroup.LayoutParams lp = getLayoutParams();
		    lp.width = previewWidth;
		    lp.height = previewHeight;
		    setLayoutParams(lp);
		    Log.d("LayoutSize","w="+lp.width+":h="+lp.height);
		    
		    mCamera.setParameters(mParam);
		    
			mCamera.startPreview();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO 自動生成されたメソッド・スタブ
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e){
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO 自動生成されたメソッド・スタブ
		if(mCamera != null){
			mCamera.release();
			mCamera = null;
		}
	}
	
	public void shot(CameraActivity cb){
		mCamera.takePicture(null, null, cb);
	}
}
