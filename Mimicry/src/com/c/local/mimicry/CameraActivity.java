package com.c.local.mimicry;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		CameraView view = new CameraView(this);
		setContentView(view);

	}

	protected void onResume() {
		super.onResume();
	}

	protected void onStop() {
		super.onStop();
	}

	public void onDestroy() {
		super.onDestroy();
	}
}
