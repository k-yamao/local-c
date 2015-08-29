package com.c.local.mimicry;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Environment;

public class Constant {

	/***************************************************************************************
	 * HomeActivity　ホームタブ
	 *************************************************************************************/

	/** タブホストのタグ */
	public static final String TAG[] = { "tag1", "tag2", "tag3", "tag4" };
	/** タブ名前 */
	public static final String LABEL[] = { "ものまね", "MYものまね", "ランキング", "設定" };

	/** Mimicryの画像ID */
	public static int[] images = { R.drawable.mimicry000,
			R.drawable.mimicry001, R.drawable.mimicry002,
			R.drawable.mimicry003, R.drawable.mimicry004,
			R.drawable.mimicry005, R.drawable.mimicry006,
			R.drawable.mimicry007, R.drawable.mimicry008,
			R.drawable.mimicry009, R.drawable.mimicry010,
			R.drawable.mimicry011, R.drawable.mimicry012,
			R.drawable.mimicry013, R.drawable.mimicry014,
			R.drawable.mimicry015, R.drawable.mimicry016,
			R.drawable.mimicry017, R.drawable.mimicry018,
			R.drawable.mimicry019, R.drawable.mimicry020,
			R.drawable.mimicry021, R.drawable.mimicry022,
			R.drawable.mimicry023, R.drawable.mimicry024,
			R.drawable.mimicry025, R.drawable.mimicry026,
			R.drawable.mimicry027, R.drawable.mimicry028,
			R.drawable.mimicry029, R.drawable.mimicry030,
			R.drawable.mimicry031, R.drawable.mimicry032,
			R.drawable.mimicry033, R.drawable.mimicry034,
			R.drawable.mimicry035, R.drawable.mimicry036,
			R.drawable.mimicry037, R.drawable.mimicry038,
			R.drawable.mimicry039, R.drawable.mimicry040,
			R.drawable.mimicry041, R.drawable.mimicry042,
			R.drawable.mimicry043, R.drawable.mimicry044, 
			R.drawable.mimicry045, R.drawable.mimicry046 };

	public static final String LISTTITLE_MIMICRY = "ものまねリスト";
	public static final String LISTTITLE_MIMICRY_MY = "MYものまね";
	public static final String LISTTITLE_MIMICRY_RANKING = "ものまねランキング";
	public static final String LISTTITLE_MIMICRY_SETTING = "設定";

	/***************************************************************************************
	 * MimicryMyLibActivity　ホームタブ
	 *************************************************************************************/

	/** 仮想的に作成するDBのカラム名 */
	public static final String[] FROM = { "img", "title", "savedate" };

	/** AdapterでバインドするViewのID */
	public static final int[] TO = { R.id.mylib_image, R.id.rowtitle,
			R.id.save_date };

	public static final String DEDFAUT_NICNAME = "匿名";

	/***************************************************************************************
	 * MimicryLibDetailActivity　ホームタブ
	 *************************************************************************************/

	public static final int AUDIO_SAMPLE_FREQ = 8000;
	public static final int AUDIO_BUFFER_SIZE = Math.max(
			AUDIO_SAMPLE_FREQ * 2 * 10, AudioRecord.getMinBufferSize(
					AUDIO_SAMPLE_FREQ, AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT));

	public static final String SDCRD_PATH = Environment
			.getExternalStorageDirectory() + "/MIMICRY";
	public static final String PUBLIC_PATH = "public";
	public static final String LIB_PATH = "lib";
	public static final String REC_PATH = "rec";
	public static final String REC_FILE = "rec.mp3";

	public static final String str3 = null;
	public static final String str4 = null;

	/** 日付のフォーマットパターン */
	public static final String DATE_PATTERN = "yyyyMMdd-HHmmss";
	public static final String DATE_PATTERN2 = "yyyy.MM.dd-HH:mm:ss";

	/** SDカード保存ディレクト */
	public static final String PUBLIC_DIR = "/Public/mimicry/";
	/** DropBoxの保存先URL */
	public static String YAMAO_DROPBOXLIN = "http://dl.dropbox.com/u/13468178/mimicry/";

	public static final String SDCRD_SAVE_PATH = Environment
			.getExternalStorageDirectory() + "/MIMICRY/lib/";

	/***************************************************************************************
	 * MimicryLibDetailActivity　ホームタブ
	 *************************************************************************************/

	public static final String NICNAME_NULL = "ニックネームが入力されていません。";
	public static final String NICNAME_COMP = "ニックネームを登録しました。";
	public static final String NICNAME_FAIL = "ニックネームの登録できませんでした。";
	public static final String NICNAME_REGIST_BTN = "登録する";
	public static final String NICNAME_CHANGE_BTN = "変更する";
	
	/***************************************************************************************
	 * MimicryLibDetailVideiActivity　
	 *************************************************************************************/
	//public final static String[] URL ={"rtsp://v5.cache4.c.youtube.com/CiILENy73wIaGQlGVfZbF7k50xMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp"}; 
	public final static String[] URL = {
		null,
		"rtsp://v5.cache4.c.youtube.com/CiILENy73wIaGQlGVfZbF7k50xMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v4.cache7.c.youtube.com/CiILENy73wIaGQlegEnvv_LQthMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v6.cache6.c.youtube.com/CiILENy73wIaGQn91Fv4uzf2OhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v6.cache3.c.youtube.com/CiILENy73wIaGQmBR5f7DWmBEBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache7.c.youtube.com/CiILENy73wIaGQmHjpI51EW7mBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v5.cache4.c.youtube.com/CiILENy73wIaGQl1R3c-KCdNjhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v8.cache7.c.youtube.com/CiILENy73wIaGQl_2ASIwGBFeRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v1.cache6.c.youtube.com/CiILENy73wIaGQmo1dV9hew2MxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v8.cache6.c.youtube.com/CiILENy73wIaGQlXoQ7L-VkCzhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache4.c.youtube.com/CiILENy73wIaGQkyDpDmUdhKqhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache1.c.youtube.com/CiILENy73wIaGQlA6eXRVSICMhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v4.cache5.c.youtube.com/CiILENy73wIaGQk5JS7sKumK1BMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v8.cache3.c.youtube.com/CiILENy73wIaGQk0tUIMCgsHCBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v8.cache1.c.youtube.com/CiILENy73wIaGQlA1B708Ttb0RMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v2.cache4.c.youtube.com/CiILENy73wIaGQl_-L3OSOVEwRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache7.c.youtube.com/CiILENy73wIaGQltAEPzPAJ_DRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache1.c.youtube.com/CiILENy73wIaGQkileajeF0RThMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache6.c.youtube.com/CiILENy73wIaGQmuP-YQn1iUbhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache2.c.youtube.com/CiILENy73wIaGQmHpa27IdWc1BMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache3.c.youtube.com/CiILENy73wIaGQknv3Wkxa2NpxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v6.cache7.c.youtube.com/CiILENy73wIaGQmCF4Km4EBRpBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache1.c.youtube.com/CiILENy73wIaGQmlenrM0fl5vRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v1.cache2.c.youtube.com/CiILENy73wIaGQkSW3gYLT9FaBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache7.c.youtube.com/CiILENy73wIaGQlvHkFCgWnp6RMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v1.cache6.c.youtube.com/CiILENy73wIaGQlrQrdx6PA9vBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v2.cache1.c.youtube.com/CiILENy73wIaGQksr-6a2DZV6xMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache8.c.youtube.com/CiILENy73wIaGQlJd8cpu7lRRBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v2.cache5.c.youtube.com/CiILENy73wIaGQlG_7mZzTQMIRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v4.cache4.c.youtube.com/CiILENy73wIaGQmORdihlMdX4hMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache5.c.youtube.com/CiILENy73wIaGQn-XX_OvTCIVRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v6.cache6.c.youtube.com/CiILENy73wIaGQn8NbEZg2p-cRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache2.c.youtube.com/CiILENy73wIaGQlRXxBmOAfxgBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v5.cache5.c.youtube.com/CiILENy73wIaGQnRLmuiDJAoAxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v2.cache5.c.youtube.com/CiILENy73wIaGQniVYuoi7A3IhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache7.c.youtube.com/CiILENy73wIaGQnmbEHDh9XIQhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v8.cache6.c.youtube.com/CiILENy73wIaGQntwMunAfp8hRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v6.cache5.c.youtube.com/CiILENy73wIaGQnWlebdoCED0RMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v3.cache5.c.youtube.com/CiILENy73wIaGQl2urhKLT9xCBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v1.cache3.c.youtube.com/CiILENy73wIaGQntY8d-YBVudRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v7.cache2.c.youtube.com/CiILENy73wIaGQlQDTDN3EcrDhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v5.cache1.c.youtube.com/CiILENy73wIaGQn2E5q4WXtpxRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v4.cache6.c.youtube.com/CiILENy73wIaGQk5ndnMid_0lxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v6.cache3.c.youtube.com/CiILENy73wIaGQlJdmAYBF9fEBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v8.cache1.c.youtube.com/CiILENy73wIaGQkSfnpJm-TeYhMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v2.cache1.c.youtube.com/CiILENy73wIaGQml6xrwxQx3jxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		"rtsp://v5.cache7.c.youtube.com/CiILENy73wIaGQk8QIvC8tGBRxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp",
		};
	
	
	


}
