package com.c.local.mimicry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.local.mimicry.dao.CallBackJson;
import com.c.local.mimicry.dao.DatabaseOpenHelper;
import com.c.local.mimicry.dao.FileLoader;
import com.c.local.mimicry.dao.JsonLoader;
import com.facebook.android.Facebook;

public class MimicryRankingDetailActivity extends Activity {

	private static final long MAX_RECORD_DIALOG_TIME = 1000 * 5;
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

	// intentで渡ってくるやつ
	private int mimicry_id;
	private int img;
	private String nicname;
	private String title;
	private String contributionDate;
	private String fileName;
	private String fileUrl;
	private String imgpath;
	private String docId;

	// イメージ
	private long id;
	private Bitmap bm;

	private String deviceId;

	// Facebook
	private Session session;
	private Dispatcher dispatcher;
	private EditText originaltitleEditText;
	private MimicryRankingAsyncTask task;
	private LinearLayout llayout;
	private String eene;
	private String eeneflg;
	private Button eeneBtn;
	private String deviceid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// タイトルバーにプログレスアイコンを表示可能にする
		// requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.mimicry_detail_ranking);

		llayout =   (LinearLayout) findViewById(R.id.Layout_ranking);

		eeneBtn =   (Button) findViewById(R.id.like_btn);

		llayout.setVisibility(View.INVISIBLE);
		dispatcher = new Dispatcher(this);
		dispatcher.addHandler("login", LoginHandler.class);
		Bundle extras = getIntent().getExtras();

		mimicry_id = extras.getInt("mimicry_id");
		img = extras.getInt("img");
		imgpath = extras.getString("imgpath");
		contributionDate = extras.getString("contributionDate");
		nicname = extras.getString("nicname");
		title = extras.getString("title");
		fileName = extras.getString("fileName");
		fileUrl = extras.getString("fileUrl");
		docId = extras.getString("docId");
		eene = extras.getString("eene");
		eeneflg = extras.getString("eeneflg");
		deviceid = extras.getString("deviceid");


		if (eeneflg.equals("1")) {
			eeneBtn.setEnabled(false);
		}

		//タスクの生成
        task = new MimicryRankingAsyncTask(MimicryRankingDetailActivity.this);

        task.execute(5.0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		/*
		 * dialog = new ProgressDialog(this);
		 * dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 * dialog.setCancelable(false); dialog.setMessage("Loading...");
		 * dialog.setCancelable(true); dialog.setButton("キャンセル", new
		 * DialogInterface.OnClickListener() {
		 *
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * ダイアログを閉じる dialog.dismiss();
		 *
		 * } }); dialog.show();
		 *
		 * runOnUiThread(new Runnable() { public void run() { try {
		 *
		 * setContentView(R.layout.mimicry_detail_ranking); // ディレクト作成
		 * //Util.mimicryMidir();
		 *
		 *
		 *
		 *
		 * // 選択された人のリストID
		 *
		 *
		 *
		 *
		 *
		 *
		 * dialog.dismiss();
		 *
		 * } catch (Exception e) {
		 *
		 * } } });
		 */

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

		mimicryEffecter.startWebSaveDataPlaying();
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

		session = Session.restore(this);

		if (session != null) {
			// 投稿ページへ遷移
			facebookShare();
		} else {
			dispatcher.runHandler("login");
		}

	}

	public void facebookShare() {

		Intent intent = new Intent(this, FacebookUploadActivity.class);

		intent.putExtra("title", title);
		intent.putExtra("nicname", nicname);
		intent.putExtra("id", (long) mimicry_id);
		// intent.putExtra("bm", bm);
		intent.putExtra("imgpath", imgpath);
		intent.putExtra("fileUrl", fileUrl);

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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 使わなくなった時点でレコーダーリソースを解放
		// mRecorder.release();
	}

	/**
	 * シェアボタンをタップしたとき
	 *
	 * @param v
	 */
	public void onShare(View v) {
		Intent it = new Intent(Intent.ACTION_SEND);
		it.putExtra(Intent.EXTRA_SUBJECT, "Mimicryシェア " + nicname);

		it.putExtra(Intent.EXTRA_TEXT, fileUrl);
		it.setType("text/*");
		startActivity(Intent.createChooser(it, "シェアするアプリを選択"));
	}

	/**
	 * 戻るボタンを押したとき
	 *
	 * @param v
	 */
	public void onBack(View v) {
		finish();
	}

	/**
	 * ええねボタンが押されたとき
	 *
	 * @param v
	 */

	public void onLike(View v) {

		new AlertDialog.Builder(MimicryRankingDetailActivity.this)
				.setMessage("このものまねに「ええね。」する。")
				.setCancelable(false)
				.setNegativeButton("キャンセル",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();

							}
						})
				.setPositiveButton("ええね。",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();

								postEene();

							}
						}).show();
		// Jsonengineに保存
		// uploadMimicry(title, imgpath, saveDate, fileName, fileUrl,
		// charactor);
	}

	/**
	 * ええね。のカウントする更新
	 *
	 * @param title
	 * @param imgpath
	 * @param savedate
	 * @param fileName
	 * @param fileUrl
	 * @param charactor
	 */
	public void uploadMimicry(int count) {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
		nameValuePair.add(new BasicNameValuePair("_docId", docId));
		nameValuePair
				.add(new BasicNameValuePair("eene", String.valueOf(count)));

		JsonLoader task = new JsonLoader(nameValuePair);

		task.setOnCallBack(new CallBackJson() {
			@Override
			public void CallBack(String Result) {
				Toast.makeText(MimicryRankingDetailActivity.this,
						"「ええね。」しました。", 800).show();

			};
		});
		task.execute("http://local-color-json.appspot.com/_je/mimicry");
	}


	public void postEene(){
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("ranking_id", docId);
		jsonMap.put("device_id", deviceid);
		JsonLoader task = new JsonLoader(nameValuePair);
		task.setJson(jsonMap);
		task.setOnCallBack(new CallBackJson() {
			@Override
			public void CallBack(String Result) {

				String r = Result;
				Toast.makeText(MimicryRankingDetailActivity.this,
						"「ええね。」しました。", 800).show();
				eeneBtn.setEnabled(false);
			};
		});
		task.execute("http://api.local-c.com/mimicry/ranking/eene");

	}

	public void eeneMimicry() {

		FileLoader task = new FileLoader();
		task.setOnCallBack(new FileLoader.CallBackFile() {
			@Override
			public void CallBack(String Result) {

				try {
					final JSONObject jsonObject = new JSONObject(Result);

					int eene;
					try {
						eene = jsonObject.getInt("eene");
						uploadMimicry(eene + 1);
						// uploadMimicryDevice();

					} catch (JSONException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}

				} catch (JSONException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			};
		});
		task.execute("http://local-color-json.appspot.com/_je/mimicry/" + docId);

	}

	/**
	 * ええね。した人を特定するため
	 *
	 * @param title
	 * @param imgpath
	 * @param savedate
	 * @param fileName
	 * @param fileUrl
	 * @param charactor
	 */
	public void uploadMimicryDevice() {

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
		nameValuePair.add(new BasicNameValuePair("ranking_id", docId));
		nameValuePair.add(new BasicNameValuePair("device_id", Util
				.getDeviceID(this)));
		JsonLoader task = new JsonLoader(nameValuePair);
		task.setOnCallBack(new CallBackJson() {
			@Override
			public void CallBack(String Result) {

			};
		});
		task.execute("http://local-color-json.appspot.com/_je/mimicrydevice");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Facebook fb = Session.wakeupForAuthCallback();
		fb.authorizeCallback(requestCode, resultCode, data);
	}





	class MimicryRankingAsyncTask extends AsyncTask<Double,Integer,String>
	{
	    Activity  activity = null;
	    ProgressDialog mProgress=null;


	    //コンストラクタ
	    public MimicryRankingAsyncTask(Activity act) {
	        //Activityを取得すればメインスレッドのインターフェースにアクセスできるようになる。
	        //ButtonとかTextViewをだけ使うんだったらButton型とかで渡しても良いんだけど…
	        //いろいろ使い勝手名良さそうなActivity全体を渡したほうがいい？
	        //プログレスダイアログにActivityを渡す必要があるのでActivityをとってる。

	        activity = act;
	    }

	    @Override
	    protected void onPreExecute()
	    {
	        //スレッド開始直後に呼び出される。
	        //何か初期化したいことがあればここでする。
	        //ここではプログレスダイアログを初期化して表示させている。
	        mProgress = new ProgressDialog(activity);
	        mProgress.setMessage("Now Loading...");
	        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	        mProgress.setIndeterminate(false);
	        mProgress.setCancelable(true);
	        mProgress.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					finish();

				}
			});
	        mProgress.show();

	    }


	    //バックグラウンドで動作させたい処理を記述する。
	    @Override
	    protected String doInBackground(Double... params)
	    {
	        String str = "";
	        mProgress.setMax(10);


	        try {
	            //時間がかかる処理を想定して
	            //１秒待つのを１０回ループしてる。
	            //一回ループするごとに進捗を更新してる。
	        	mimicryEffecter = new MimicryEffecter();
	        	mimicryEffecter.setWebSaveData(fileUrl);
	        	
	        	


	            for(int i=0;i<10;i++)
	            {
	                //時間のかかる処理処理
	                Thread.sleep(1000);

	                //進捗表示を更新する。
	                onProgressUpdate(i+1);
	            }
	        } catch (IOException e) {
	        } catch (InterruptedException e) {}

	        str = params[0] + "：";

	        //この返り値が
	        //onPostExecuteの引数として渡される。
	        return str;

	    }

	    //プログレスバーとか操作したい場合はここに書く
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	        mProgress.incrementProgressBy(values[0]);

	    }

	    @Override
	    protected void onPostExecute(String result) {
	        //スレッド内の処理が完了した後に、画面に何か表示させたかったり
	        //したい場合(メインスレッド上で動作する。)
	        //ここでは、テキストビューに完了メッセージを表示させている。

	        //プログレスダイアログの消去
	        mProgress.dismiss();
	    	// Facebookログイン処理
			// 録音ボタン取得
			playButton = ((ImageView) findViewById(R.id.play));
			facebookButton = ((ImageView) findViewById(R.id.sns));

			dispatcher.addHandler("login", LoginHandler.class);

			((TextView) findViewById(R.id.nicname)).setText(nicname);// ニックネームを表示
			((TextView) findViewById(R.id.title)).setText(title);// タイトルを表示
			((ImageView) findViewById(R.id.imageView)).setImageResource(img);// イメージを表示
			((TextView) findViewById(R.id.save_date)).setText(contributionDate);// 保存日時表示
			// 録音とプレイヤーの生成

			llayout.setVisibility(View.VISIBLE);

	    }
	}
}
