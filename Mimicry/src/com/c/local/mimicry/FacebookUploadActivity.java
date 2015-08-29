package com.c.local.mimicry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

public class FacebookUploadActivity extends Activity {

	private static final String PUBLIC_DIR = "/Public/mimicry/";
	private static String YAMAO_DROPBOXLIN = "http://dl.dropbox.com/u/13468178/mimicry/";
	private long id;
	private Bitmap bm;
	private String title;
	private String nicname;
	private String saveFile;
	private String fileName;
	private String fileUrl;
	private String charactor;
	private String imgpath;
	private String facebook_data;
	private String twitter_data;

	private Button mRequestButton;
	private Button mPostButton;
	private Button mDeleteButton;
	private Button mUploadButton;

	private Facebook facebook;
	private AsyncFacebookRunner facebookRunner;

	// facebook
	private TextView facebookName;// Facebook　認証テキスト
	private LinearLayout viewContribution_layout;// 投稿レイアウト
	private TextView viewTitle;// タイトル
	private TextView viewNicname;// ニックネーム
	private TextView viewFileUrl;// イメージ
	private ImageView viewBitmap;// Dropbox 音声URL
	private TextView viewFaceBookCommentText;// Facebookのコメントテキスト
	private EditText viewFaceBookComment;// Facebookのコメント内容
	private Button viewCanselButton;// キャンセルボタン
	private Button viewContributionButton;// 投稿ボタン

	private AsyncFacebookRunner asyncFbRunner = null;

	// エラーダイアログ
	private ProgressDialog dialog;

	// エラーダイアログ
	private AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fbupload);
		Bundle extras = getIntent().getExtras();
		id = extras.getLong("id");
		// bm = (Bitmap) extras.get("bm");
		imgpath = extras.getString("imgpath");
		title = extras.getString("title");
		nicname = extras.getString("nicname");
		fileUrl = extras.getString("fileUrl");

		
		Log.v("★★★★★★★",imgpath);
		// Initialize the dispatcher
		Dispatcher dispatcher = new Dispatcher(this);
		dispatcher.addHandler("login", LoginHandler.class);

		// If a session already exists, render the stream page
		// immediately. Otherwise, render the login page.
		Session session = Session.restore(this);
		if (session != null) {
			facebook = session.getFb();
			facebookRunner = new AsyncFacebookRunner(facebook);
			facebookName = ((TextView) findViewById(R.id.facebookName));
			facebookName.setText(session.getName() + "さんでログインしています。");

		} else {
			dispatcher.runHandler("login");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Bundle extras = getIntent().getExtras();
		// id = extras.getLong("id");
		// bm = (Bitmap) extras.get("bm");
		// imgpath = extras.getString("imgpath");
		// title = extras.getString("title");
		// fileUrl = extras.getString("fileUrl");

		viewContribution_layout = (LinearLayout) findViewById(R.id.contribution_layout);
		viewTitle = (TextView) findViewById(R.id.title);

		viewNicname = (TextView) findViewById(R.id.nicname);

		viewBitmap = (ImageView) findViewById(R.id.facebookimg);// イメージ
		viewFileUrl = (TextView) findViewById(R.id.fileurl);// Dropbox 音声URL
		viewFaceBookCommentText = (TextView) findViewById(R.id.facebook_comment_text);
		viewFaceBookComment = (EditText) findViewById(R.id.fb_comment);
		viewCanselButton = (Button) findViewById(R.id.cansel_btn);
		viewContributionButton = (Button) findViewById(R.id.contribution_btn);

		// Facebookのコメントにフォーカスが移動したらキーボードを開き、フォーカスが外れるとキーボードを閉じる
		viewFaceBookComment.setOnFocusChangeListener(new EditFocus());
		viewTitle.setText(title);
		if (nicname != null) {
			viewNicname.setText(nicname);
		}
		viewBitmap.setImageResource(Constant.images[(int) id]);
		viewFileUrl.setText(fileUrl);

		// viewContribution_layout
		// .setVisibility(mFacebook.isSessionValid() ? View.VISIBLE
		// : View.INVISIBLE);

	}

	private final Handler handler = new Handler();

	/**
	 * 投稿ボタンがタップされたとき
	 * 
	 * @param v
	 */
	public void onClickContribution(View v) {

		if (Util.isNetConnect(this)) {

			if (facebook.isSessionValid() && fileUrl != null) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
				// アラートダイアログのタイトルを設定します
				alertDialogBuilder.setTitle("タイトル");
				// アラートダイアログのメッセージを設定します
				alertDialogBuilder.setMessage("メッセージ");
				// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
				alertDialogBuilder.setPositiveButton("肯定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});

				// アラートダイアログのキャンセルが可能かどうかを設定します
				alertDialogBuilder.setCancelable(true);
				final AlertDialog alertDialog = alertDialogBuilder.create();

				if (imgpath == null || imgpath.equals("")) {
					imgpath = "";// MIMICRYイメージURLを設定
				}
				SpannableStringBuilder sb = (SpannableStringBuilder) viewFaceBookComment
						.getText();
				Bundle params = new Bundle();
				params.putString("name", "Mimicry");
				params.putString("caption", title);
				params.putString("picture", imgpath);
				if (nicname != null) {
					params.putString("description",
							nicname + "さんのものまね　　 コメント：" + sb.toString());
				} else {
					params.putString("description", sb.toString());
				}

				params.putString("link", "http://mimicry.mobi/");
				params.putString("message", fileUrl);
				params.putString("access_token", facebook.getAccessToken());
				facebookRunner.request("me/feed", params, "POST",
						new RequestListener() {

							@Override
							public void onComplete(String response, Object state) {

								// 投稿完了ダイアログ
								handler.post(new Runnable() {
									public void run() {

										new AlertDialog.Builder(
												FacebookUploadActivity.this)
												.setMessage("Facebookに投稿しました。")
												.setCancelable(false)
												.setPositiveButton(
														"ＯＫ",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																dialog.dismiss();
																moveMimicryLibDetailActivity();

															}
														}).show();

									}

								});
							}

							@Override
							public void onIOException(IOException e,
									Object state) {
								// TODO 自動生成されたメソッド・スタブ

							}

							@Override
							public void onFileNotFoundException(
									FileNotFoundException e, Object state) {
								// TODO 自動生成されたメソッド・スタブ

							}

							@Override
							public void onMalformedURLException(
									MalformedURLException e, Object state) {
								// TODO 自動生成されたメソッド・スタブ

							}

							@Override
							public void onFacebookError(FacebookError e,
									Object state) {
								// TODO 自動生成されたメソッド・スタブ

							}
						}, null);
			} else {

				dialog = new ProgressDialog(this);
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setMessage("Facebookにログインしていないか、音声ファイルがありません。");
				dialog.setCancelable(false);
				dialog.setButton("閉じる", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				dialog.show();
			}
		} else {
			Util.isNetConnectDialog(FacebookUploadActivity.this);
		}

	}

	public void onCansel(View v) {
		finish();

	}

	public void moveMyLivActivity() {
		Intent intent = new Intent(this, MimicryMyLibActivity.class);
		startActivity(intent);
	}

	public void moveMimicryLibDetailActivity() {
		finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		facebook.authorizeCallback(requestCode, resultCode, data);
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

}
