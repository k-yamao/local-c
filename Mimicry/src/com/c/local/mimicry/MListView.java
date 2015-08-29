package com.c.local.mimicry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MListView extends ListView implements View.OnTouchListener {

	public interface Callback {
		public void reload();
	}

	private int mOverscrollDistance = 150;
	private int nowScrollY = 0; //現在のスクロール位置
	private Callback callback = null; //Activityへ通知用のコールバック
	private Drawable overDrawable = null; //オーバースクロールで表示する画像
	private Drawable updateDrawable = null;//更新位置までスクロールしたときに表示する画像
	private int maxOverScroll = 70; //オーバースクロールの最大位置
	private int borderPosition = 50; //この値以上スクロールすると更新

	public void setCallback(Callback callback) {
		this.callback = callback;

	}

	public MListView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public MListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOverScrollMode(OVER_SCROLL_ALWAYS);
		setOnTouchListener(this);
	}

	public MListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOverDrawable(Drawable overDrawable) {
		this.overDrawable = overDrawable;
	}

	public void setUpdateDrawable(Drawable updateDrawable) {
		this.updateDrawable = getResources().getDrawable(R.drawable.icon);
	}

	public void setMaxOverScroll(int maxOverScroll) {
		this.maxOverScroll = maxOverScroll;
	}

	public void setBorderPosition(int borderPosition) {
		this.borderPosition = borderPosition;
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
			int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		super.overScrollBy(0, deltaY, 0, scrollY, 0, scrollRangeY, 0, maxOverScroll, isTouchEvent);
		nowScrollY = scrollY;
		Log.v("●OrverScroll", "scrollX:" + scrollX + " scrollY:" + scrollY);
		//スクロール位置で画像の差し替え
		if (scrollY <= -borderPosition) {
			setOverscrollHeader(updateDrawable);

		} else {
			setOverscrollHeader(overDrawable);
		}
		return true;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		super.onTouchEvent(motionEvent);
		//ACTION_UPしたときにスnowScrollY置以上なら更新
		Log.v("★", ":" + nowScrollY + "アクション：" + motionEvent.getAction());
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			if (nowScrollY <= -borderPosition) {
				callback.reload();
				
			}
		}
		return false;
	}
	 // オーバースクロール実行後
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                    boolean clampedY) {

    	nowScrollY = 0;
            Log.v("■OrverScrolled", "scrollX:" + scrollX + " scrollY:" + scrollY);

            super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
