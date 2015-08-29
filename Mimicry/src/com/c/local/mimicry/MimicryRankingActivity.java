package com.c.local.mimicry;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.c.local.mimicry.dao.FileLoader;

public class MimicryRankingActivity extends ListActivity {

	private ProgressDialog dialog;
	ArrayList<HashMap<String, Object>> outputArray = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> item = null;
	private int position;
	private final Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mimicry_ranking);
		TextView mtitle = ((TextView) findViewById(R.id.listtitle));
		mtitle.setText(Constant.LISTTITLE_MIMICRY_RANKING);

		if (Util.isNetConnect(this)) {
			rankingList();
		} else {

			Util.isNetConnectDialog(MimicryRankingActivity.this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setSelection(position);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		this.position = position;
		Intent intent = new Intent(this, MimicryRankingDetailActivity.class);
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

	public void rankingList() {
		FileLoader task = new FileLoader();

		task.setOnCallBack(new FileLoader.CallBackFile() {
			@Override
			public void CallBack(String Result) {

				try {
					final JSONArray jsonArray = new JSONArray(Result);

					runOnUiThread(new Runnable() {
						public void run() {


							try {

								for (int i = 0; i < jsonArray.length(); i++) {

									item = new HashMap<String, Object>();
									JSONObject jsonObj = jsonArray
											.getJSONObject(i);

									item.put("ranking", String.valueOf(i + 1));
									item.put("nicname",
											jsonObj.getString("nicname"));
									item.put("title",
											jsonObj.getString("title"));
									item.put("mimicry_id", Integer
											.parseInt(jsonObj
													.getString("mimicry_id")));
									item.put("img", Util.imgBind(Integer
											.parseInt(jsonObj
													.getString("mimicry_id"))));
									item.put("imgpath", jsonObj
													.getString("imgpath"));
									item.put("contributionDate", Util
											.getDateLongString(jsonObj
													.getLong("_createdAt")));

									item.put("fileUrl",
											jsonObj.getString("fileUrl"));
									item.put("fileName",
											jsonObj.getString("fileName"));
									item.put("docId",
											jsonObj.getString("_docId"));
									item.put("charactor",
											jsonObj.getString("title"));

									outputArray.add(item);

								}

								SimpleAdapter myAdapter = new SimpleAdapter(
										MimicryRankingActivity.this,
										outputArray,
										R.layout.mimicry_row_ranking,
										new String[] { "ranking", "nicname",
												"title", "img",
												"contributionDate" },
										new int[] { R.id.ranking, R.id.nicname,
												R.id.title, R.id.ranking_image,
												R.id.contribution_date });
								// lv.setAdapter(myAdapter);
								setListAdapter(myAdapter);
								myAdapter.notifyDataSetChanged();
								if (dialog != null) {
									dialog.cancel();
									dialog.dismiss();
								}
							} catch (JSONException e) {
								// TODO 自動生成された catch ブロック
								e.printStackTrace();
							}

						}
					});

				} catch (JSONException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			};
		});
		task.execute("http://local-color-json.appspot.com/_je/mimicry?sort=eene.desc&limit=20");

	}

	public void onUpdate(View view) {


		if (Util.isNetConnect(this)) {

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
			outputArray.clear();

			rankingList();

		} else {
			Util.isNetConnectDialog(MimicryRankingActivity.this);
		}
	}

}
