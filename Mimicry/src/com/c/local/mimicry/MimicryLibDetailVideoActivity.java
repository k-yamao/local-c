package com.c.local.mimicry;

import java.io.IOException;

import com.c.local.mimicry.MimicryRankingDetailActivity.MimicryRankingAsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MimicryLibDetailVideoActivity extends Activity {


	private MediaController mc;
	private VideoView videoView;
	private MimicryRankingAsyncTask task;
	private int id;
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mimicry_detail_video);
		//setContentView(R.layout.mimicry_detail_web);
		Bundle extras = getIntent().getExtras();

		id = extras.getInt("id");
		Log.v("★",id+"");

		try {
			videoView = (VideoView) findViewById(R.id.videoView);
			//webView = (WebView) findViewById(R.id.webview);

			/*
			webView.setWebViewClient(new WebViewClient());
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadUrl(url);
			*/


			mc = new MediaController(this);

			//タスクの生成
	        task = new MimicryRankingAsyncTask(MimicryLibDetailVideoActivity.this);

	        task.execute(1.0);


		} catch (Exception e) {
		}
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
	        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
	        mProgress.setMax(5);


	        try {
	            //時間がかかる処理を想定して
	            //１秒待つのを１０回ループしてる。
	            //一回ループするごとに進捗を更新してる。


				videoView.setVideoURI(Uri.parse(Constant.URL[id]));
				videoView.requestFocus();
				videoView.setMediaController(mc);
				videoView.start();
				videoView.pause();

	            for(int i=0;i<5;i++)
	            {
	                //時間のかかる処理処理
	                Thread.sleep(1000);

	                //進捗表示を更新する。
	                onProgressUpdate(i+1);
	            }

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
	    	videoView.start();
	        mProgress.dismiss();
	    	// Facebookログイン処理
			// 録音ボタン取得

	    }

	}}
