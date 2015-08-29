package com.c.local.mimicry.img;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SimpleCursorAdapterImg extends SimpleCursorAdapter {

	private int mTo[];
	private String[] mFrom;

	public SimpleCursorAdapterImg(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		mTo = to;
		mFrom = from;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		for (int i = 0; i < mTo.length; i++) {
			View v = view.findViewById(mTo[i]);
			if (v == null)
				continue;

			String text = cursor.getString(cursor.getColumnIndex(mFrom[i]));
			if (text == null)
				text = "";
			if (v instanceof TextView) {
				setViewText((TextView) v, text);
			} else if (v instanceof ImageView) {
				
				Log.v("★★★", text + Integer.toHexString(100));
				setViewImage((ImageView) v, text);
			}
		}
	}
	
	@Override
	public void setViewImage(ImageView iv, String text){
		if (text != "") {
		      iv.setImageResource(Integer.parseInt(text));
		}
	}

}
