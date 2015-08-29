package com.c.local.mimicry;

import java.io.FileOutputStream;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback {
	private Camera camera;

	public CameraView(Context context) {
		super(context);
		getHolder().addCallback(this);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(holder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Camera.Parameters parameters = camera.getParameters();
		// parameters.setPreviewSize(width, height);
		camera.setParameters(parameters);
		camera.startPreview();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.release();
		camera = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			camera.takePicture(null, null, this);
		}
		return true;
	}

	// 写真撮影完了時に呼ばれる
	public void onPictureTaken(byte[] data, Camera camera) {
		// ファイル保存とギャラリーへの登録
		try {
			String path = Environment.getExternalStorageDirectory()
					+ "/test.jpg";
			data2file(data, path);
		} catch (Exception e) {
		}
		// プレビュー再開
		camera.startPreview();
	}

	// バイトデータ→ファイル
	private void data2file(byte[] w, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(w);
			out.close();
		} catch (Exception e) {
			if (out != null)
				out.close();
			throw e;
		}
	}

}
