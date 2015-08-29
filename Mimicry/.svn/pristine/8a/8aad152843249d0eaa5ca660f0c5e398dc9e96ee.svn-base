package com.c.local.mimicry;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.c.local.mimicry.dao.DatabaseOpenHelper;

public class MimicryMyLibActivity extends ListActivity {
	
	private Map<String, String> idmap;
	private int position;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mimicry);
		TextView mtitle = ((TextView) findViewById(R.id.listtitle));
		mtitle.setText(Constant.LISTTITLE_MIMICRY_MY);

	}

	@Override
	protected void onResume() {
		super.onResume();
	
		//DatabaseOpenHelperを取得
		DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);
		Log.v("RssListActivity", "Succeeded in open the database.");
		SQLiteDatabase database = null;
		Cursor cursor = null;
		
		try {
			//読み取り専用でSQLiteDatabaseを取得 
			database = databaseOpenHelper.getWritableDatabase();

			ArrayList<HashMap<String, Object>> outputArray = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> item = null;
			
			
			//クエリーを投げて、検索して取得結果のカーソルを取得
			cursor = database.query("MY_MIMICRY", null, null, null, null, null,
					"_id DESC");
			startManagingCursor(cursor);


			idmap = new HashMap<String, String>();
			int i = 0;
			
			while (cursor.moveToNext()) {

				item = new HashMap<String, Object>();
				
				item.put("img", Util.imgBind(cursor.getInt(cursor.getColumnIndex("MIMICRY_ID"))));
				
				item.put("title",
						cursor.getString(cursor.getColumnIndex("TITLE")));
				item.put("savedate",
						cursor.getString(cursor.getColumnIndex("SAVE_DATE")));
				
				outputArray.add(item);
				
				//リストクリックしたときどのMyものまねがクリックされたか判断するためIDをポジション順に格納
				String myMimicryid = cursor.getString(cursor.getColumnIndex("_id"));
				idmap.put(String.valueOf(i), myMimicryid);
				
				i++;
				
			}
			SimpleAdapter myAdapter = new SimpleAdapter(this, outputArray,
					R.layout.mimicry_row_my, 
					Constant.FROM, //マップのキー
					Constant.TO //レイアウトのID
			);

			setListAdapter(myAdapter);
			myAdapter.notifyDataSetChanged();
			setSelection(position);
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
		
		this.position = position;
		Intent intent = new Intent(this, MimicryMyLibDetailActivity.class);
		intent.putExtra("id", Long.parseLong(idmap.get(String.valueOf(id))));
		
		startActivity(intent);
	}




	private class MyViewBinder implements SimpleCursorAdapter.ViewBinder{

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			try {
				switch(columnIndex)
				{
					case 1:
						((TextView)view).setText(cursor.getString(columnIndex));
						break;
						
					case 4:
						URL imageUrl = new URL(cursor.getString(columnIndex));
						InputStream imageIs = imageUrl.openStream();
						Bitmap bm = BitmapFactory.decodeStream(imageIs);
						((ImageView)view).setImageBitmap(bm);
						break;
					default:
						((TextView)view).setText(cursor.getString(columnIndex));
						break;
				}
				return true;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
}
