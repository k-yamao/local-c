package com.c.local.mimicry;

import java.io.File;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.google.ads.AdView;

public class HomeActivity extends TabActivity implements OnTabChangeListener {

	TabHost tabHost;
	TabHost.TabSpec spec1;
	TabHost.TabSpec spec2;
	TabHost.TabSpec spec3;
	TabHost.TabSpec spec4;
	TextView textView;
	 private AdView adView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		//アプリ用ディレクトを作成
		mkdir("Mimicry/Photo");
		mkdir("Mimicry/Movie");

		textView = new TextView(this);
		// TabHost の取得
		tabHost = getTabHost();
		// Tab が切り替わったときに呼ばれるコールバックを登録
		tabHost.setOnTabChangedListener(this);

		/********** Tab その1 **********/
		// TabSpec の作成
		spec1 = tabHost.newTabSpec(Constant.TAG[0]);
		// インジケーターの設定
		spec1.setIndicator(Constant.LABEL[0], getResources().getDrawable(R.drawable.micphone));      // タブに表示する文字列と画像
		//spec1.setIndicator(Constant.LABEL[0]);      // タブに表示する文字列と画像

		// Tab のコンテンツの設定
		spec1.setContent(new Intent(this, MimicryLibActivity.class));
		// TabHost に Tab を追加
		tabHost.addTab(spec1);

		/********** Tab その2 **********/

		spec2 = tabHost.newTabSpec(Constant.TAG[1]);
		// インジケーターの設定
		spec2.setIndicator(Constant.LABEL[1], getResources().getDrawable(R.drawable.save));      // タブに表示する文字列と画像
		//spec2.setIndicator(Constant.LABEL[1]);
		// Tab のコンテンツの設定
		spec2.setContent(new Intent(this, MimicryMyLibActivity.class));

		// TabHost に Tab を追加
		tabHost.addTab(spec2);

		/********** Tab その3 **********/

		spec3 = tabHost.newTabSpec(Constant.TAG[2]);
		// インジケーターの設定
		spec3.setIndicator(Constant.LABEL[2], getResources().getDrawable(R.drawable.chart_bar));      // タブに表示する文字列と画像
		//spec3.setIndicator(Constant.LABEL[2]);
		// Tab のコンテンツの設定
		spec3.setContent(new Intent(this, MimicryRanking2Activity.class));
		// TabHost に Tab を追加
		tabHost.addTab(spec3);


		/********** Tab その4 **********/

		spec4 = tabHost.newTabSpec(Constant.TAG[3]);
		// インジケーターの設定
		spec4.setIndicator(Constant.LABEL[3], getResources().getDrawable(R.drawable.gear));      // タブに表示する文字列と画像
		//spec4.setIndicator(Constant.LABEL[3]);
		// Tab のコンテンツの設定
		spec4.setContent(new Intent(this, SettingActivity.class));
		// TabHost に Tab を追加
		tabHost.addTab(spec4);


		tabHost.setCurrentTab(0);

		// AdView をリソースとしてルックアップしてリクエストを読み込む。
//	    AdView adView = (AdView)this.findViewById(R.id.adView);
//
//	    AdRequest adRequest = new AdRequest();
//	    //adRequest.addTestDevice(AdRequest.TEST_EMULATOR);               // エミュレータ
//	    adRequest.addTestDevice("46992c082d1ee9f2-3");                      // Android 端末をテスト
//
//	    // 一般的なリクエストを行って広告を読み込む
//	    adView.loadAd(adRequest);
//

	}


	@Override
	public void onTabChanged(String tabId) {
		Log.v("HomeActivity", "TabClicked" + tabId);
//		if (tabId == Constant.TAG[2]) {
//			textView.setBackgroundColor(Color.LTGRAY);
//		} else {
//			textView.setBackgroundColor(Color.DKGRAY);
//		}
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menulnfalter = getMenuInflater();
		menulnfalter.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.main_menu_add) {



			return true;
		}
		return false;
	}*/
	public boolean mkdir(String path){
		File file = new File(path);
		return file.mkdirs();
	}

}