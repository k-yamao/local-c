package com.c.local.mimicry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;

public class PostRequestListener implements AsyncFacebookRunner.RequestListener{

	@Override
	public void onComplete(String response, Object state) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onIOException(IOException e, Object state) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onFileNotFoundException(FileNotFoundException e, Object state) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onMalformedURLException(MalformedURLException e, Object state) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onFacebookError(FacebookError e, Object state) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	
	
}