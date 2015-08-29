package com.c.local.mimicry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.c.local.mimicry.dao.FileLoader;
import com.c.local.mimicry.dao.HttpHelper;

public class MimicryRanking2Activity extends Activity implements MListView.Callback, Runnable, OnScrollListener {

	private static ProgressDialog waitDialog;
	private Thread thread;
	private int offset = 0;
	private int page = 1;
	private ProgressDialog dialog;
	private Button updateBtn = null;
	ArrayList<HashMap<String, Object>> outputArray = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> item = null;
	private int position;
	private final Handler handler = new Handler();
	private String deviceid;
	private ArrayAdapter<String> adapter;
	private View mFooter;
	private ListView listView;
	private boolean readStatus;
	private int maxlist = 150;
	private int maxpage = 20;
	private View mFooterBtn;
	private MimicryRanking2AsyncTask<Object, Object, Object> task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mimicry_ranking3);
		listView = (ListView) findViewById(R.id.lists);
		updateBtn = (Button) findViewById(R.id.update_btn);
		TextView mtitle = ((TextView) findViewById(R.id.listtitle));
		mtitle.setText(Constant.LISTTITLE_MIMICRY_RANKING);

		listView.setSelection(position);
		//listView.setCallback(this);
		//listView.setOverDrawable(getResources().getDrawable(R.drawable.icon_update));
		//listView.setUpdateDrawable(getResources().getDrawable(R.drawable.icon_update));
		listView.addFooterView(getFooter());
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MimicryRankingDetailActivity.class);
				HashMap<String, Object> item = outputArray.get(position);

				intent.putExtra("mimicry_id", (Integer) item.get("mimicry_id"));
				intent.putExtra("img", (Integer) item.get("img"));
				intent.putExtra("nicname", (String) item.get("nicname"));
				intent.putExtra("title", (String) item.get("title"));
				intent.putExtra("contributionDate", (String) item.get("contributionDate"));
				intent.putExtra("fileUrl", (String) item.get("fileUrl"));
				intent.putExtra("fileName", (String) item.get("fileName"));
				intent.putExtra("docId", (String) item.get("docId"));
				intent.putExtra("imgpath", (String) item.get("imgpath"));
				intent.putExtra("eene", (String) item.get("eene"));
				intent.putExtra("eeneflg", (String) item.get("eeneflg"));
				intent.putExtra("deviceid", deviceid);
				startActivity(intent);
			}

		});

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		Log.v("★Scroll", "" + firstVisibleItem + ":" + visibleItemCount + ":" + totalItemCount);
		if (totalItemCount == firstVisibleItem + visibleItemCount && readStatus) {

			if (maxlist < totalItemCount) {

				listView.removeFooterView(getFooter());
			} else {

				if (Util.isNetConnect(this)) {
					readStatus = false;
					rankingList(offset);
					offset += 20;
				} else {
					Util.isNetConnectDialog(MimicryRanking2Activity.this);
				}
			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private View getFooter() {

		if (mFooter == null) {
			mFooter = getLayoutInflater().inflate(R.layout.listview_footer, null);
		}
		return mFooter;
	}

	private View getFooterBtn() {

		if (mFooterBtn == null) {
			mFooterBtn = getLayoutInflater().inflate(R.layout.listview_footer_btn, null);
		}
		return mFooterBtn;
	}

	@Override
	protected void onResume() {
		final int os = 0;
		super.onResume();
		FileLoader task = new FileLoader();
		task.setOnCallBack(new FileLoader.CallBackFile() {
			@Override
			public void CallBack(String Result) {
				try {

					boolean check = Util.isResultCheck(Result);

					if (check) {

						int resultcnt = Util.resultCnt(Result);

						if (resultcnt > 0) {

							outputArray = Util.getRankingList(outputArray, Result, os);

							SimpleAdapter myAdapter = new SimpleAdapter(
									MimicryRanking2Activity.this,
									outputArray,
									R.layout.mimicry_row_ranking,
									new String[] { "ranking", "nicname",
											"title", "img",
											"contributionDate" },
									new int[] { R.id.ranking, R.id.nicname,
											R.id.title, R.id.ranking_image,
											R.id.contribution_date });
							// lv.setAdapter(myAdapter);
							listView.setAdapter(myAdapter);
							myAdapter.notifyDataSetChanged();
							/**
							 					if (dialog != null) {
													dialog.cancel();
													dialog.dismiss();
												}
							*/
							listView.invalidateViews();
							readStatus = true;
							listView.setSelection(os - 1);

							/*
							if (os == 100) {
								listView.removeFooterView(getFooter());
								listView.addFooterView(getFooterBtn());
							}
							 */

							if(resultcnt < 20){
								listView.removeFooterView(getFooter());
								listView.invalidate();
								listView.setSelection(os);

							}

						} else {
							listView.removeFooterView(getFooter());
							listView.invalidate();
							listView.setSelection(os);
						}

					}
					//itemClick();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		});
		task.execute("http://api.local-c.com/mimicry/ranking/list?limit=20&offset=" + offset + "&deviceid=" + deviceid);
		updateBtn.setVisibility(View.GONE);
		//タスクの生成
//        task = new MimicryRanking2AsyncTask<Object, Object, Object>(MimicryRanking2Activity.this);

//        task.execute(5.0);
//        task.execute();
        
//		if (outputArray.size() == 0) {
//			//offset = 0;
//			if (Util.isNetConnect(this)) {
//				HttpHelper http = new HttpHelper();
//				deviceid = Util.getDeviceID(this);
//				InputStream is = http
//						.getResponseContent("http://api.local-c.com/mimicry/ranking/list?limit=20&offset=0&deviceid="
//								+ deviceid);
//
//				BufferedReader br = new BufferedReader(new InputStreamReader(is));
//				StringBuilder sb = new StringBuilder();
//				String line;
//				try {
//					while ((line = br.readLine()) != null) {
//						sb.append(line);
//					}
//
//					String result = sb.toString();
//
//					boolean check = Util.isResultCheck(result);
//
//					if (check) {
//						outputArray = new ArrayList<HashMap<String, Object>>();
//						outputArray = Util.getRankingList(outputArray, result, offset);
//						SimpleAdapter myAdapter = new SimpleAdapter(
//								MimicryRanking2Activity.this,
//								outputArray,
//								R.layout.mimicry_row_ranking,
//								new String[] { "ranking", "nicname",
//										"title", "img",
//										"contributionDate" },
//								new int[] { R.id.ranking, R.id.nicname,
//										R.id.title, R.id.ranking_image,
//										R.id.contribution_date });
//
//						listView.setAdapter(myAdapter);
//						myAdapter.notifyDataSetChanged();
//						updateBtn.setVisibility(View.GONE);
//						offset += 20;
//						readStatus = true;
//
//					}
//				} catch (Exception e) {
//					Log.v("★", e.toString());
//				}
//
//			} else {
//
//				Util.isNetConnectDialog(MimicryRanking2Activity.this);
//			}
//
//        }

	}

	public void rankingList(int offset) {

		/*
		if (dialog != null) {
			dialog.cancel();
			dialog.dismiss();
		}

		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.setMessage("Loading...");
		dialog.setCancelable(true);
		dialog.setButton("キャンセル", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ダイアログを閉じる
				dialog.dismiss();
			}
		});
		dialog.show();
		//outputArray.clear();
		 */
		final int os = offset;

		FileLoader task = new FileLoader();
		task.setOnCallBack(new FileLoader.CallBackFile() {
			@Override
			public void CallBack(String Result) {
				try {

					boolean check = Util.isResultCheck(Result);

					if (check) {

						int resultcnt = Util.resultCnt(Result);

						if (resultcnt > 0) {

							outputArray = Util.getRankingList(outputArray, Result, os);

							SimpleAdapter myAdapter = new SimpleAdapter(
									MimicryRanking2Activity.this,
									outputArray,
									R.layout.mimicry_row_ranking,
									new String[] { "ranking", "nicname",
											"title", "img",
											"contributionDate" },
									new int[] { R.id.ranking, R.id.nicname,
											R.id.title, R.id.ranking_image,
											R.id.contribution_date });
							// lv.setAdapter(myAdapter);
							listView.setAdapter(myAdapter);
							myAdapter.notifyDataSetChanged();
							/**
							 					if (dialog != null) {
													dialog.cancel();
													dialog.dismiss();
												}
							*/
							listView.invalidateViews();
							readStatus = true;
							listView.setSelection(os - 1);

							/*
							if (os == 100) {
								listView.removeFooterView(getFooter());
								listView.addFooterView(getFooterBtn());
							}
							 */

							if(resultcnt < 20){
								listView.removeFooterView(getFooter());
								listView.invalidate();
								listView.setSelection(os);

							}

						} else {
							listView.removeFooterView(getFooter());
							listView.invalidate();
							listView.setSelection(os);
						}

					}
					//itemClick();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		});
		task.execute("http://api.local-c.com/mimicry/ranking/list?limit=20&offset=" + offset + "&deviceid=" + deviceid);

	}

	public void itemClick() {

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				listView = (MListView) parent;
				listView.setSelection(position);
				Intent intent = new Intent();
				HashMap<String, Object> item = outputArray.get(position);

				intent.putExtra("mimicry_id", (Integer) item.get("mimicry_id"));

				intent.putExtra("img", (Integer) item.get("img"));
				intent.putExtra("nicname", (String) item.get("nicname"));
				intent.putExtra("title", (String) item.get("title"));
				intent.putExtra("contributionDate",
						(String) item.get("contributionDate"));
				intent.putExtra("fileUrl", (String) item.get("fileUrl"));
				intent.putExtra("fileName", (String) item.get("fileName"));
				intent.putExtra("docId", (String) item.get("docId"));
				intent.putExtra("imgpath", (String) item.get("imgpath"));
				startActivity(intent);
			}
		});
	}

	public void onUpdate(View view) {
		rankingList(offset);
	}

	@Override
	public void reload() {

		Toast.makeText(MimicryRanking2Activity.this, "更新します", Toast.LENGTH_LONG).show();

		//rankingList(2);

	}

	private void setWait() {
		// プログレスダイアログの設定
		waitDialog = new ProgressDialog(this);
		// プログレスダイアログのメッセージを設定します
		waitDialog.setMessage("ネットワーク接続中...");
		// 円スタイル（くるくる回るタイプ）に設定します
		waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// プログレスダイアログを表示
		waitDialog.show();

		thread = new Thread(this);
		thread.start();

	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ

	}
	class MimicryRanking2AsyncTask<Params, Result, Progress> extends AsyncTask<Params, Progress, Result>
	{
	    Activity  activity = null;
	    ProgressDialog mProgress=null;
	    InputStream is = null;

	    //コンストラクタ
	    public MimicryRanking2AsyncTask(Activity act) {
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
	    protected Object doInBackground(Object[] objects) {
	    
	        String str = "";
	        mProgress.setMax(10);


	        try {
	        	HttpHelper http = new HttpHelper();
				deviceid = Util.getDeviceID(activity);
				is = http
						.getResponseContent("http://api.local-c.com/mimicry/ranking/list?limit=20&offset=0&deviceid="
								+ deviceid);

	        	
	        	
	        	


	            for(int i=0;i<10;i++)
	            {
	                //時間のかかる処理処理
	                Thread.sleep(1000);

	                //進捗表示を更新する。
	               // onProgressUpdate(i+1);
	            }
	        
	        } catch (InterruptedException e) {}

	        //str = params[0] + "：";

	        //この返り値が
	        //onPostExecuteの引数として渡される。
	    
	        return str;

	    }

	    //プログレスバーとか操作したい場合はここに書く
//	    @Override
//	    protected void onProgressUpdate(Progress...) {
//	        mProgress.incrementProgressBy(values[0]);
//
//	    }

	    @Override
        protected void onPostExecute(Object o) {
	        //スレッド内の処理が完了した後に、画面に何か表示させたかったり
	        //したい場合(メインスレッド上で動作する。)
	        //ここでは、テキストビューに完了メッセージを表示させている。

	        //プログレスダイアログの消去
	        mProgress.dismiss();
	        

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				String res = sb.toString();

				boolean check = Util.isResultCheck(res);

				if (check) {
					outputArray = new ArrayList<HashMap<String, Object>>();
					outputArray = Util.getRankingList(outputArray, res, offset);
					SimpleAdapter myAdapter = new SimpleAdapter(
							MimicryRanking2Activity.this,
							outputArray,
							R.layout.mimicry_row_ranking,
							new String[] { "ranking", "nicname",
									"title", "img",
									"contributionDate" },
							new int[] { R.id.ranking, R.id.nicname,
									R.id.title, R.id.ranking_image,
									R.id.contribution_date });

					listView.setAdapter(myAdapter);
					myAdapter.notifyDataSetChanged();
					updateBtn.setVisibility(View.GONE);
					offset += 20;
					readStatus = true;

				}
			
				
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
        	
	    	// Facebookログイン処理
			// 録音ボタン取得
//			playButton = ((ImageView) findViewById(R.id.play));
//			facebookButton = ((ImageView) findViewById(R.id.sns));
//
//			dispatcher.addHandler("login", LoginHandler.class);
//
//			((TextView) findViewById(R.id.nicname)).setText(nicname);// ニックネームを表示
//			((TextView) findViewById(R.id.title)).setText(title);// タイトルを表示
//			((ImageView) findViewById(R.id.imageView)).setImageResource(img);// イメージを表示
//			((TextView) findViewById(R.id.save_date)).setText(contributionDate);// 保存日時表示
//			// 録音とプレイヤーの生成
//
//			llayout.setVisibility(View.VISIBLE);

	    }

		
	}

}
