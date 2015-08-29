package com.c.local.mimicry;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.c.local.mimicry.dao.DatabaseOpenHelper;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MimicryLibActivity extends ListActivity {
	private TextView mtitle;
	private int ps;
	private final String AD_UNIT_ID = "a15003b6995266d";
	private ListView lv;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.mimicry);
		mtitle = ((TextView) findViewById(R.id.listtitle));
		mtitle.setText(Constant.LISTTITLE_MIMICRY);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// DatabaseOpenHelperを取得
		DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);

		SQLiteDatabase database = null;
		Cursor cursor = null;

		try {
			// 読み取り専用でSQLiteDatabaseを取得
			database = databaseOpenHelper.getWritableDatabase();
			// databaseOpenHelper.onCreate(database);
			// databaseOpenHelper.onInsert(database);
			ArrayList<HashMap<String, Object>> outputArray = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> item = null;
			// クエリーを投げて、検索して取得結果のカーソルを取得
			cursor = database.query("MIMICRY", null, null, null, null, null,
					null);

			startManagingCursor(cursor);

			int i = 0;
			while (cursor.moveToNext()) {

				item = new HashMap<String, Object>();
				item.put("img", Constant.images[i]);
				item.put("title",
						cursor.getString(cursor.getColumnIndex("TITLE")));
				item.put("challenge", cursor.getString(cursor
						.getColumnIndex("CHALLENGE_LEVEL")));
				item.put("rating",
						cursor.getString(cursor.getColumnIndex("RATING")));
				outputArray.add(item);
				i++;
			}
			SimpleAdapter myAdapter = new SimpleAdapter(this, outputArray,
					R.layout.mimicry_row, // ここがポイント２
					new String[] { "img", "title", "challenge", "rating" }, // ここがポイント３－１
					new int[] { R.id.imageView, R.id.rowtitle,
							R.id.change_level, R.id.rating } // ここがポイント３－２
			);

			setListAdapter(myAdapter);
			myAdapter.notifyDataSetChanged();

			setSelection(ps);

		} finally {
			if (database != null) {
				database.close();
				Log.v("RssListActivity", "Succeeded in close the database.");
			}
		}

	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		ps = position;
		Intent intent = new Intent(this, MimicryLibDetailActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("position", position);
		startActivity(intent);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (lv != null) {
			// ListViewの位置
			outState.putInt("lvFirstVisiblePosition",
					lv.getFirstVisiblePosition());
			outState.putInt("lvGetTop", lv.getChildAt(0).getTop());
		}
	}
}