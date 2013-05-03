package com.c.local.umpire;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	// カウント
	private int bc;
	private int sc;
	private int oc;
	private int sc1;
	private int sc2;

	// View
	private ImageView ball1;
	private ImageView ball2;
	private ImageView ball3;
	private ImageView strike1;
	private ImageView strike2;
	private ImageView out1;
	private ImageView out2;

	private SoundPool soundPool;
	private int[] soundIds;
	private Object soundResouces;
	private TextView score1;
	private TextView score2;
	private int ball_sound;
	private int strike_sound;
	private int out_sound;
	private int strikeout_sound;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ball1 = (ImageView) findViewById(R.id.ball1);
		ball2 = (ImageView) findViewById(R.id.ball2);
		ball3 = (ImageView) findViewById(R.id.ball3);

		strike1 = (ImageView) findViewById(R.id.strike1);
		strike2 = (ImageView) findViewById(R.id.strike2);

		out1 = (ImageView) findViewById(R.id.out1);
		out2 = (ImageView) findViewById(R.id.out2);

		score1 = (TextView) findViewById(R.id.attack_before);
		score2 = (TextView) findViewById(R.id.attack_after);

		Context context = this.getApplicationContext();

//		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//
//
//		ball_sound = soundPool.load(context, R.raw.ball, 1);
//		strike_sound = soundPool.load(context, R.raw.strike, 1);
//		strikeout_sound = soundPool.load(context, R.raw.strikeout, 1);
//		out_sound = soundPool.load(context, R.raw.out, 1);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ボールイメージボタンをタップ
	 *
	 * @param v
	 */
	public void onBallAdd(View v) {

		//soundPool.play(ball_sound, 100, 100, 1, 0, 1);

		if (bc == 0) {
			ball1.setImageResource(R.drawable.ball);
			bc++;
		} else if (bc == 1) {

			ball2.setImageResource(R.drawable.ball);
			bc++;

		} else if (bc == 2) {
			ball3.setImageResource(R.drawable.ball);
			bc++;
		} else {
			ball1.setImageResource(R.drawable.none);
			ball2.setImageResource(R.drawable.none);
			ball3.setImageResource(R.drawable.none);
			strike1.setImageResource(R.drawable.none);
			strike2.setImageResource(R.drawable.none);
			bc = 0;
			sc = 0;
		}

	}

	/**
	 * ストライクイメージボタンをタップ
	 *
	 * @param v
	 */
	public void onStrikeAdd(View v) {


		if (sc == 0) {
			//soundPool.play(strike_sound, 100, 100, 1, 0, 1);
			strike1.setImageResource(R.drawable.strike);
			sc++;
		} else if (sc == 1) {
			//soundPool.play(strike_sound, 100, 100, 1, 0, 1);
			strike2.setImageResource(R.drawable.strike);
			sc++;
		} else {
			//soundPool.play(strikeout_sound, 100, 100, 1, 0, 1);
			ball1.setImageResource(R.drawable.none);
			ball2.setImageResource(R.drawable.none);
			ball3.setImageResource(R.drawable.none);
			strike1.setImageResource(R.drawable.none);
			strike2.setImageResource(R.drawable.none);
			sc = 0;
			bc = 0;

			if (oc == 0) {
				out1.setImageResource(R.drawable.out);
				oc++;
			} else if (oc == 1) {
				out2.setImageResource(R.drawable.out);
				oc++;
			} else {
				ball1.setImageResource(R.drawable.none);
				ball2.setImageResource(R.drawable.none);
				ball3.setImageResource(R.drawable.none);
				strike1.setImageResource(R.drawable.none);
				strike2.setImageResource(R.drawable.none);
				out1.setImageResource(R.drawable.none);
				out2.setImageResource(R.drawable.none);
				bc=0;
				sc=0;
				oc = 0;
			}
		}
	}

	/**
	 * アウトイメージボタンをタップ
	 *
	 * @param v
	 */
	public void onOutAdd(View v) {
		//soundPool.play(out_sound, 100, 100, 1, 0, 1);
		if (oc == 0) {
			out1.setImageResource(R.drawable.out);
			ball1.setImageResource(R.drawable.none);
			ball2.setImageResource(R.drawable.none);
			ball3.setImageResource(R.drawable.none);
			strike1.setImageResource(R.drawable.none);
			strike2.setImageResource(R.drawable.none);
			oc++;
			sc = 0;
			bc = 0;
		} else if (oc == 1) {
			out2.setImageResource(R.drawable.out);
			ball1.setImageResource(R.drawable.none);
			ball2.setImageResource(R.drawable.none);
			ball3.setImageResource(R.drawable.none);
			strike1.setImageResource(R.drawable.none);
			strike2.setImageResource(R.drawable.none);
			oc++;
			sc = 0;
			bc = 0;
		} else {
			ball1.setImageResource(R.drawable.none);
			ball2.setImageResource(R.drawable.none);
			ball3.setImageResource(R.drawable.none);
			strike1.setImageResource(R.drawable.none);
			strike2.setImageResource(R.drawable.none);
			out1.setImageResource(R.drawable.none);
			out2.setImageResource(R.drawable.none);
			oc = 0;
			sc = 0;
			bc = 0;

		}

	}

	public void onSuound(View v) {


	}
	/**
	 * 全てクリアボタン
	 * @param v
	 */
	public void onAllClear(View v) {
		bc = 0;
		sc = 0;
		oc = 0;
		ball1.setImageResource(R.drawable.none);
		ball2.setImageResource(R.drawable.none);
		ball3.setImageResource(R.drawable.none);
		strike1.setImageResource(R.drawable.none);
		strike2.setImageResource(R.drawable.none);
		out1.setImageResource(R.drawable.none);
		out2.setImageResource(R.drawable.none);

	}

	/**
	 * カウントクリアボタン
	 * @param v
	 */
	public void onCountClear(View v) {
		bc = 0;
		sc = 0;
		ball1.setImageResource(R.drawable.none);
		ball2.setImageResource(R.drawable.none);
		ball3.setImageResource(R.drawable.none);
		strike1.setImageResource(R.drawable.none);
		strike2.setImageResource(R.drawable.none);

	}

	/**
	 * 左のプラスボタン
	 * @param v
	 */
	public void onLeftPlus(View v) {

		sc1++;
		if(sc1 > 99){
			sc1=0;
		}
		score1.setText(String.valueOf(sc1));


	}
	/**
	 * 左のマイナスボタン
	 * @param v
	 */
	public void onLeftMinus(View v) {
		sc1--;
		if(sc1 < 0){
			sc1=0;
		}
		score1.setText(String.valueOf(sc1));
	}

	/**
	 * 右のプラスボタン
	 * @param v
	 */
	public void onRightPlus(View v) {
		sc2++;
		if(sc2 > 99){
			sc2=0;
		}
		score2.setText(String.valueOf(sc2));
	}

	/**
	 * 右のマイナスボタン
	 * @param v
	 */
	public void onRightMinus(View v) {

		sc2--;
		if(sc2 < 0){
			sc2=0;
		}
		score2.setText(String.valueOf(sc2));


	}

}
