package com.c.local.mimicry.img;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class UrlImageSave {
	private final Context context;

	private Request request;
	private String url;
	private final Handler handler = new Handler();
	public  static String SAVE_DIRECTORY = "/LOCALCOLOR/PHOTO/";
	private String imgdirectory;
	private String imgFileName;

	private OnImageLoadListener listener = new OnImageLoadListener() {
		public void onStart(String url) {
		}

		public void onComplete(String url) {
		}
	};

	public static interface OnImageLoadListener {
		public void onStart(String url);

		public void onComplete(String url);
	}

	private final Runnable threadRunnable = new Runnable() {
		public void run() {
			handler.post(imageLoadRunnable);
		}
	};

	private final Runnable imageLoadRunnable = new Runnable() {
		public void run() {
			setImageLocalCache();
		}
	};

	private boolean setImageLocalCache() {
		SoftReference<Bitmap> image = ImageCache.getImage(
				context.getCacheDir(), url);
		File root = Environment.getExternalStorageDirectory();
		// 保存処理開始
		FileOutputStream fos = null;
		try {
			if (image != null && image.get() != null) {
				
				// 日付でファイル名を作成　
				Date mDate = new Date();
				SimpleDateFormat imgFileName = new SimpleDateFormat(
						"yyyyMMdd_HHmmss");

				listener.onComplete(url);
				fos = new FileOutputStream(new File(root + SAVE_DIRECTORY + imgdirectory, this.imgFileName + ".jpg"));

				// jpegで保存
				image.get().compress(CompressFormat.JPEG, 100, fos);
				// 保存処理終了
				fos.close();
				return true;
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return false;
	}

	public UrlImageSave(Context context) {
		this.context = context;
	}

	public UrlImageSave(Context context, AttributeSet attrs) {
		this.context = context;
	}

	public UrlImageSave(Context context, AttributeSet attrs, int defStyle) {
		this.context = context;
	}

	public void setOnImageLoadListener(OnImageLoadListener listener) {
		this.listener = listener;
	}

	public void setImageUrl(String url, OnImageLoadListener listener) {
		setOnImageLoadListener(listener);
		setImageUrl(url);
	}

	public void setImageUrl(String url) {
		this.url = url;
		request = new Request(url, context.getCacheDir(), threadRunnable);
		if (setImageLocalCache()) {
			return;
		}

		listener.onStart(url);
		Channel.getInstance().putRequest(request);
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String getImgdirectory() {
		return imgdirectory;
	}

	public void setImgdirectory(String imgdirectory) {
		this.imgdirectory = imgdirectory;
	}
	
}