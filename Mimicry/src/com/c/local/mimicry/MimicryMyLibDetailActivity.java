package com.c.local.mimicry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.local.mimicry.dao.CallBackJson;
import com.c.local.mimicry.dao.DatabaseOpenHelper;
import com.c.local.mimicry.dao.JsonLoader;
import com.facebook.android.Facebook;

public class MimicryMyLibDetailActivity extends Activity {

	private static final int AUDIO_SAMPLE_FREQ = 8000;
	private static final int AUDIO_BUFFER_SIZE = Math.max(
			AUDIO_SAMPLE_FREQ * 2 * 10, AudioRecord.getMinBufferSize(
					AUDIO_SAMPLE_FREQ, AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT));

	private static final String SDCRD_PATH = Environment
			.getExternalStorageDirectory() + "/MIMICRY";
	private static final String REC_PATH = "rec";
	private static final String LIB_PATH = "lib";
	private static final String REC_FILE = "rec.mp3";

	// Facebook
	private int mAuthAttempts = 0;
	private String mFacebookToken;

	// ボタン
	private ImageView recButton;
	private ImageView playButton;
	private ImageView saveButton;
	private ImageView facebookButton;

	private MediaRecorder mRecorder;
	private boolean mRecordingFlag = false;

	private MimicryEffecter mimicryEffecter;
	private MimicryRecorder mimicryRecorder;
	private MimicryPlayer mimicryPlayer;
	private ProgressDialog dialog;

	private MediaPlayer mediaPlayer;

	// データベースヘルパー
	DatabaseOpenHelper databaseOpenHelper;
	// 保存ファイル名
	private String saveFileId;
	private String saveFiledateTime;

	// 端末ID
	private String deviceId;

	// DropBox
	private static final String PUBLIC_DIR = "/Public/mimicry/";
	private static String YAMAO_DROPBOXLIN = "http://dl.dropbox.com/u/13468178/mimicry/";
	private String title;
	private String imgpath;
	private String saveDate;
	private String fileUrl;
	private String saveFile;
	private String mimicryId;

	private String fileName;
	private String charactor;
	private String facebook_data;
	private String twitter_data;

	// イメージ
	private long id;
	private Bitmap bm;

	// Facebook
	private Session session;
	private Dispatcher dispatcher;
	private EditText originaltitleEditText;
	private String nicname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		// 選択された人のリストID
		id = extras.getLong("id");

		setContentView(R.layout.mimicry_detail_my);

		// 録音ボタン取得
		playButton = ((ImageView) findViewById(R.id.play));
		facebookButton = ((ImageView) findViewById(R.id.sns));

		try {
			// 録音とプレイヤーの生成
			mimicryEffecter = new MimicryEffecter();

		} catch (Exception e) {

		}

		// Facebookログイン処理
		dispatcher = new Dispatcher(this);
		dispatcher.addHandler("login", LoginHandler.class);

	}

	@Override
	protected void onResume() {
		super.onResume();

		databaseOpenHelper = new DatabaseOpenHelper(this);
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {

			// 保存ファイル名
			// saveFileId = deviceId + "-" + String.valueOf(id);

			database = databaseOpenHelper.getReadableDatabase();
			cursor = database.query("MY_MIMICRY", null, "_id=" + id, null,
					null, null, null);

			startManagingCursor(cursor);
			if (!cursor.moveToFirst()) {
				return;
			}

			mimicryId = cursor.getString(cursor.getColumnIndex("MIMICRY_ID"));// DBからセットID取得
			title = cursor.getString(cursor.getColumnIndex("TITLE"));// DBからタイトル取得
			imgpath = cursor.getString(cursor.getColumnIndex("IMG_PATH"));// DBからイメージURL取得
			saveDate = cursor.getString(cursor.getColumnIndex("SAVE_DATE"));
			fileUrl = cursor.getString(cursor.getColumnIndex("FILE_URL"));
			fileName = cursor.getString(cursor.getColumnIndex("FILE_NAME"));

			((TextView) findViewById(R.id.title)).setText(title);// タイトルを表示
			((ImageView) findViewById(R.id.imageView))
					.setImageResource(Util.imgBind(cursor.getInt(cursor
							.getColumnIndex("MIMICRY_ID"))));// イメージを表示
			((TextView) findViewById(R.id.save_date)).setText(saveDate);// 保存日時表示

		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
	}

	/**
	 * 再生ボタンをタップしたとき
	 *
	 * @param v
	 * @throws IOException
	 */
	public void onClickPlaying(View v) throws IOException {

		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMax(100);
		dialog.incrementProgressBy(0);
		dialog.setCancelable(false);
		dialog.setMessage("playing...");
		dialog.setCancelable(false);
		dialog.setButton("STOP", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ダイアログを閉じる
				dialog.dismiss();
				// 再生を止める
				mimicryEffecter.stopPlaying();
			}
		});
		dialog.show();
		// mimicryEffecter.setParameters(parameters);
		// mimicryEffecter.convert();
		// 再生を開始する。

		mimicryEffecter.startSaveDataPlaying(fileName);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (dialog.isShowing()) {
					dialog.setProgress((int) (mimicryEffecter
							.getCurrentProgress() * 100));
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * Facebookボタンを押下したとき
	 *
	 * @param v
	 */
	public void onFacebookShare(View v) {

		if (Util.isNetConnect(this)) {

			session = Session.restore(this);

			if (session != null) {
				// 投稿ページへ遷移
				facebookShare();
			} else {
				dispatcher.runHandler("login");
			}
		} else {
			Util.isNetConnectDialog(MimicryMyLibDetailActivity.this);
		}
	}

	public void facebookShare() {

		Intent intent = new Intent(this, FacebookUploadActivity.class);

		intent.putExtra("title", title);
		intent.putExtra("id", Long.parseLong(mimicryId));
		// intent.putExtra("bm", bm);
		intent.putExtra("imgpath", imgpath);
		intent.putExtra("fileUrl", fileUrl);

		startActivity(intent);
	}

	/**
	 * Mimicryボタンを押したとき
	 *
	 * @param v
	 */
	public void onMimicry(View v) {

		if (Util.isNetConnect(this)) {

			databaseOpenHelper = new DatabaseOpenHelper(this);
			SQLiteDatabase database = null;
			Cursor cursor = null;
			try {

				// 保存ファイル名
				// saveFileId = deviceId + "-" + String.valueOf(id);

				database = databaseOpenHelper.getReadableDatabase();
				cursor = database.query("SETTING", null, "_id = 1", null,
						null, null, null);

				startManagingCursor(cursor);
				if (cursor.moveToFirst()) {
					nicname = (String) cursor.getString(cursor.getColumnIndex("NICNAME"));// DBからニックネームをセット

				} else {
					//nicname = "";
				}

				final EditText editView = new EditText(
						MimicryMyLibDetailActivity.this);
				if (nicname != null) {
					editView.setText(nicname);
				} else {
					editView.setHint(R.string.nicname_input);

				}

				new AlertDialog.Builder(MimicryMyLibDetailActivity.this)
						.setMessage("Mimicryランキングに投稿する。")
						.setCancelable(false)
						.setView(editView)
						.setNegativeButton("キャンセル",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.dismiss();

									}
								})
						.setPositiveButton("投稿",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.dismiss();
										String nicName = editView.getText()
												.toString();
										if (nicName.equals("")) {
											nicName = Constant.DEDFAUT_NICNAME;
										}
										uploadMimicry(nicName, title, imgpath,
												saveDate, fileName, fileUrl,
												charactor);

									}
								}).show();

			} catch (Exception e) {

			} finally {
				if (database != null) {
					database.close();
				}
			}

		} else {
			Util.isNetConnectDialog(MimicryMyLibDetailActivity.this);
		}
	}

	/**
	 * みみ
	 *
	 * @param title
	 * @param imgpath
	 * @param savedate
	 * @param fileName
	 * @param fileUrl
	 * @param charactor
	 */
	public void uploadMimicry(String nicName, String title, String imgpath,
			String savedate, String fileName, String fileUrl, String charactor) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);

		nameValuePair.add(new BasicNameValuePair("mimicry_id", mimicryId));
		nameValuePair.add(new BasicNameValuePair("nicname", nicName));
		nameValuePair.add(new BasicNameValuePair("title", title));
		nameValuePair.add(new BasicNameValuePair("imgpath", imgpath));
		nameValuePair.add(new BasicNameValuePair("filename", fileName));
		nameValuePair.add(new BasicNameValuePair("fileurl", fileUrl));

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("mimicry_id", mimicryId);
		jsonMap.put("nicname", nicName);
		jsonMap.put("title", title);
		jsonMap.put("imgpath", imgpath);
		jsonMap.put("filename", fileName);
		jsonMap.put("fileurl", fileUrl);

		JsonLoader task = new JsonLoader(nameValuePair);
		//task.setJson(jsonMap);
		task.setOnCallBack(new CallBackJson() {
			@Override
			public void CallBack(String Result) {

				String r = Result;
				Toast.makeText(MimicryMyLibDetailActivity.this, "投稿しました。", 800)
						.show();

			};
		});
		task.execute("http://api.local-c.com/mimicry/ranking/post");
	}

	public static final void clean(File root) {
		if (root == null || !root.exists()) {
			return;
		}
		if (root.isFile()) {
			// ファイル削除
			if (root.exists() && !root.delete()) {
				root.deleteOnExit();
			}
		} else {
			// ディレクトリの場合、再帰する
			File[] list = root.listFiles();
			for (int i = 0; i < list.length; i++) {
				clean(list[i]);
			}
			if (root.exists() && !root.delete()) {
				root.deleteOnExit();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 使わなくなった時点でレコーダーリソースを解放
		// mRecorder.release();
	}

	/**
	 * 削除するボタンを押したとき
	 *
	 * @param v
	 */
	public void onDelete(View v) {

		SQLiteDatabase database = null;
		try {
			database = databaseOpenHelper.getReadableDatabase();
			database.delete("MY_MIMICRY", "_id = " + id, null);
			finish();

		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}

	}

	/**
	 * シェアボタンをタップしたとき
	 *
	 * @param v
	 */
	public void onShare(View v) {

		if (Util.isNetConnect(this)) {

			Intent it = new Intent(Intent.ACTION_SEND);
			it.putExtra(Intent.EXTRA_SUBJECT, "Mimicryシェア " + title);

			Uri url = Uri.parse(Constant.SDCRD_SAVE_PATH + saveFile);

			it.putExtra(Intent.EXTRA_TEXT, fileUrl);
			it.setType("text/*");
			startActivity(Intent.createChooser(it, "シェアするアプリを選択"));

		} else {
			Util.isNetConnectDialog(MimicryMyLibDetailActivity.this);
		}
	}

	/**
	 * 戻るボタンを押したとき
	 *
	 * @param v
	 */
	public void onBack(View v) {
		finish();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Facebook fb = Session.wakeupForAuthCallback();
		fb.authorizeCallback(requestCode, resultCode, data);
	}

	public void onCansel(View v) {
		finish();

	}
}
