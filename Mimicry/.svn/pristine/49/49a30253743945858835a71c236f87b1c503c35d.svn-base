package com.c.local.mimicry;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c.local.mimicry.dao.DatabaseOpenHelper;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.facebook.android.Facebook;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.mediation.admob.AdMobAdapter;

public class MimicryLibDetailActivity extends Activity {

	// ボタン
	private ImageView recButton;
	private ImageView playButton;
	private ImageView saveButton;
	private ImageView facebookButton;

	private MediaRecorder mRecorder;
	private boolean mRecordingFlag = false;

	private MimicryEffecter mimicryEffecter;
	private ProgressDialog dialog;

	// データベースヘルパー
	DatabaseOpenHelper databaseOpenHelper;

	// DropBox
	private String title;
	private String originaltitle;
	private String fileName;
	private String fileUrl;
	private String imgpath;


	// イメージ
	private long id;
	private Bitmap bm;

	private String deviceId;

	// Facebook
	private Session session;
	private Dispatcher dispatcher;
	private EditText originaltitleEditText;
	private long position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);





		Bundle extras = getIntent().getExtras();
		// 選択された人のリストID
		id = extras.getLong("id");



		// オリジナルモノマネレイアウトとモノマネリストのレイアウトか
		if (id == 0) {
			setContentView(R.layout.mimicry_original);
		} else {
			setContentView(R.layout.mimicry_detail);
		}



		// ディレクト作成
		Util.mimicryMidir();

		// 端末IDの取得
		// 保存ファイル名

		deviceId = Util.getDeviceID(this);

		// 録音ボタン取得
		recButton = (ImageView) findViewById(R.id.rec);
		playButton = ((ImageView) findViewById(R.id.play));
		saveButton = ((ImageView) findViewById(R.id.save));
		facebookButton = ((ImageView) findViewById(R.id.sns));

		playButton.setVisibility(View.INVISIBLE);
		saveButton.setVisibility(View.INVISIBLE);
		facebookButton.setVisibility(View.INVISIBLE);

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

			database = databaseOpenHelper.getReadableDatabase();
			cursor = database.query("MIMICRY", null, "_id=" + id, null, null,
					null, null);

			startManagingCursor(cursor);
			if (!cursor.moveToFirst()) {
				return;
			}

			title = cursor.getString(cursor.getColumnIndex("TITLE"));// DBからタイトル取得
			imgpath = cursor.getString(cursor.getColumnIndex("IMG_PATH"));// DBからイメージURL取得

			((TextView) findViewById(R.id.title)).setText(title);// タイトルを表示
			((ImageView) findViewById(R.id.imageView))
					.setImageResource(Constant.images[(int) id]);// イメージを表示

			// ボタンを取得する

			if (id != 0) {

				// 難易度を表示
				((TextView) findViewById(R.id.change_level)).setText(cursor
						.getString(cursor.getColumnIndex("CHALLENGE_LEVEL")));
				// 人気度を表示
				((TextView) findViewById(R.id.rating)).setText(cursor
						.getString(cursor.getColumnIndex("RATING")));
				// 定番ものまねを表示
				((TextView) findViewById(R.id.old_standby)).setText(cursor
						.getString(cursor.getColumnIndex("OLD_STANDBY")));
				// ものまねアドバイスを表示
//				((TextView) findViewById(R.id.advice)).setText(cursor
//						.getString(cursor.getColumnIndex("MIMICRY_POINT")));




			} else {
				// オリジナルものまねの表示
				originaltitleEditText = (EditText) findViewById(R.id.original_title);

				// Facebookのコメントにフォーカスが移動したらキーボードを開き、フォーカスが外れるとキーボードを閉じる
				originaltitleEditText.setOnFocusChangeListener(new EditFocus());

			}

		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
	}

	/**
	 * 録音ボタン
	 *
	 * @param v
	 */
	public void onClickRecord(View v) {

		// 録音時のダイアログ表示
		recButton.setImageResource(R.drawable.rec_btn_down);

		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("recording...　最長15秒");
		dialog.setCancelable(false);
		dialog.setButton("STOP", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ダイアログを閉じる
				dialog.dismiss();
				// 録音を止める
				mimicryEffecter.stopRecording();
				playButton.setVisibility(View.VISIBLE);
				saveButton.setVisibility(View.VISIBLE);
				facebookButton.setVisibility(View.VISIBLE);
				recButton.setImageResource(R.drawable.rec_btn);

			}
		});
		dialog.show();
		// 録音を開始する。
		mimicryEffecter.startRecording();

	}

	/**
	 * 再生ボタンをタップしたとき
	 *
	 * @param v
	 */
	public void onClickPlaying(View v) {

		playButton.setImageResource(R.drawable.play_btn_down);
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
				playButton.setImageResource(R.drawable.play_btn);
			}
		});
		dialog.show();
		// mimicryEffecter.setParameters(parameters);
		mimicryEffecter.convert();
		// 再生を開始する。
		mimicryEffecter.startPlaying();
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
	 * 保存ボタンをタップしたとき
	 *
	 * @param v
	 */
	public void onClickSave(View v) {


		if (Util.isNetConnect(this)) {

			saveButton.setImageResource(R.drawable.save_btn_down);
			saveAudio();
			saveButton.setImageResource(R.drawable.save_btn);

		} else {

			Util.isNetConnectDialog(MimicryLibDetailActivity.this);
		}

	}

	/**
	 * 音声ファイルの保存処理
	 */
	public void saveAudio() {

		// 保存するファイル名を生成
		fileName = Util.saveFileName(this, id);

		// ファイル名を生成
		mimicryEffecter.saveFile(fileName);

		// DropBoxへ保存
		uploadDropBox(fileName);

		// DropBoxパブリックURLを生成
		fileUrl = Constant.YAMAO_DROPBOXLIN + fileName;

		// 保存日時を取得
		String saveDate = Util.dateString2();

		if (id == 0) {
			// オリジナルものまねの場合
			SpannableStringBuilder sb = (SpannableStringBuilder) originaltitleEditText
					.getText();
			originaltitle = sb.toString();
			saveDB(originaltitle, imgpath, saveDate, fileName, fileUrl,
					originaltitle);
		} else {
			// ものまねライブラリのものまねの場合
			saveDB(title, imgpath, saveDate, fileName, fileUrl, title);
		}

	}

	/**
	 * DropBoxへ音声ファイルを保存する。
	 *
	 * @param fileName
	 */
	public void uploadDropBox(String fileName) {
		// DropBoxの認証
		DropboxAuth dba = new DropboxAuth();
		AndroidAuthSession dbsession = dba.buildSession();
		DropboxAPI<AndroidAuthSession> mApi = new DropboxAPI<AndroidAuthSession>(
				dbsession);

		// DropBoxのアップロードオブジェクトへファイルをセット
		UploadPicture upload = new UploadPicture(this, mApi,
				Constant.PUBLIC_DIR, Util.saveFileNameFile(fileName));
		// DropBoxへ保存
		upload.execute();

	}

	/**
	 * DBへものまね情報を保存する。
	 *
	 * @param title
	 * @param imgpath
	 * @param savedate
	 * @param fileName
	 * @param fileUrl
	 * @param charactor
	 * @return
	 */
	public boolean saveDB(String title, String imgpath, String savedate,
			String fileName, String fileUrl, String charactor) {

		DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(
				getApplicationContext());
		SQLiteDatabase database = null;
		ContentValues contentValues;
		try {

			if (database != null)
				database.close();

			database = databaseOpenHelper.getWritableDatabase();
			database.beginTransaction();
			contentValues = new ContentValues();

			contentValues.put("TITLE", title);
			contentValues.put("MIMICRY_ID", id);
			contentValues.put("IMG_PATH", imgpath);
			contentValues.put("SAVE_DATE", savedate);
			contentValues.put("FILE_NAME", fileName);
			contentValues.put("FILE_URL", fileUrl);
			contentValues.put("CHARACTER", charactor);

			database.insert("MY_MIMICRY", null, contentValues);
			database.setTransactionSuccessful();

		} catch (Exception exception) {
			Log.e(exception.getClass().getName(), exception.getMessage(),
					exception);

			return false;
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
		return true;
	}

	/**
	 * Facebookボタンを押下したとき
	 *
	 * @param v
	 */
	public void onFacebookShare(View v) {

		if (Util.isNetConnect(this)) {

			// Facebookセッションを取得
			session = Session.restore(this);

			if (session != null) {
				// 音声保存
				saveAudio();
				// 投稿ページへ遷移
				facebookShare();
			} else {
				dispatcher.runHandler("login");
			}
		} else {

			Util.isNetConnectDialog(MimicryLibDetailActivity.this);
		}

	}

	public void facebookShare() {

		Intent intent = new Intent(this, FacebookUploadActivity.class);
		if (id == 0) {
			intent.putExtra("title", originaltitle);
		} else {
			intent.putExtra("title", title);
		}
		intent.putExtra("id", id);
		// intent.putExtra("bm", bm);
		intent.putExtra("imgpath", imgpath);

		intent.putExtra("fileUrl", fileUrl);

		startActivity(intent);
	}

	/**
	 * 参考動画をみるボタンを押下したとき
	 *
	 * @param v
	 */
	public void onVideo(View v) {

		Intent intent = new Intent(this, MimicryLibDetailVideoActivity.class);

		intent.putExtra("id", (int)id);

		startActivity(intent);

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

	public void setRec() {
		// MediaRecorderのインスタンスを作成
		mRecorder = new MediaRecorder();

		// マイクからの入力とする
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

		// 記録フォーマットを3GPPに設定
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

		// 音声コーデックをAMR-NBに設定
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		// 出力ファイルパスを設定
		mRecorder.setOutputFile(Constant.SDCRD_PATH + "/" + Constant.REC_PATH
				+ "/" + Constant.REC_FILE);

		// レコーダーを準備
		try {
			mRecorder.prepare();
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 使わなくなった時点でレコーダーリソースを解放
		// mRecorder.release();
	}

	// FacebookコメントのEditTextにフォーカスがあたったとき、外れたときの処理
	private class EditFocus implements View.OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// フォーカスを受け取ったとき
			if (hasFocus) {
				// ソフトキーボードを表示する
				inputMethodManager.showSoftInput(v,
						InputMethodManager.SHOW_FORCED);
			}
			// フォーカスが外れたとき
			else {
				// ソフトキーボードを閉じる
				inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
						0);
			}
		}
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

	public void onCamera(View v) {


		Intent intent = new Intent(this, CameraActivity.class);
		startActivity(intent);

	}


}
