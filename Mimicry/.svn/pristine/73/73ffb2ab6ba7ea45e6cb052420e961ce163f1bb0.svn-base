package com.c.local.mimicry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

public class Util {

	private static final String SDCRD_PATH = Environment
			.getExternalStorageDirectory() + "/MIMICRY";
	private static final String REC_PATH = "rec";
	private static final String LIB_PATH = "lib";
	private static final String REC_FILE = "rec.mp3";

	/**
	 * インターネット接続確認
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			return cm.getActiveNetworkInfo().isConnected();
		}
		return false;
	}

	private static final Handler handler = new Handler();

	public static void isNetConnectDialog(final Activity activity) {

		// 投稿完了ダイアログ
		handler.post(new Runnable() {
			public void run() {

				new AlertDialog.Builder(activity)
						.setMessage("インターネットに接続できませんでした。")
						.setCancelable(false)
						.setPositiveButton("ＯＫ",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.dismiss();

									}
								}).show();

			}

		});
	}

	public static void isRequired(final Activity activity, final String message) {

		handler.post(new Runnable() {
			public void run() {

				new AlertDialog.Builder(activity)
						.setMessage(message)
						.setCancelable(false)
						.setPositiveButton("ＯＫ",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.dismiss();

									}
								}).show();

			}

		});
	}

	/**
	 * ディレクトリを再帰的に削除
	 *
	 * @param root
	 */
	public static final void clean(File root) {
		if (root == null || !root.exists()) {
			return;
		}
		if (root.isFile()) {
			// ファイル削除
			if (root.exists() && !root.delete()) {
				root.deleteOnExit();
			}
		} else {
			// ディレクトリの場合、再帰する
			File[] list = root.listFiles();
			for (int i = 0; i < list.length; i++) {
				clean(list[i]);
			}
			if (root.exists() && !root.delete()) {
				root.deleteOnExit();
			}
		}
	}

	/**
	 * Mimicryディレクトリを作成
	 */
	public static void mimicryMidir() {

		// SD カード/パッケージ名 ディレクトリ生成
		File outDir = new File(Constant.SDCRD_PATH);
		// パッケージ名のディレクトリが SD カードになければ作成します。
		if (outDir.exists() == false) {
			outDir.mkdir();
		}

		// "rec" のディレクトリがパッケージ名のディレクトリになければ作成します。
		File recdir = new File(outDir, Constant.PUBLIC_DIR);
		if (recdir.exists() == false) {
			recdir.mkdir();
		}

		// "rec" のディレクトリがパッケージ名のディレクトリになければ作成します。
		File libdir = new File(outDir, Constant.LIB_PATH);
		if (libdir.exists() == false) {
			libdir.mkdir();
		}

	}

	/**
	 * 端末IDの取得
	 *
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context) {
		return Settings.Secure.getString(context.getContentResolver(),
				Settings.System.ANDROID_ID);

	}

	/**
	 * 保存するファイル名を取得String型
	 *
	 * @param context
	 * @param id
	 * @return
	 */
	public static String saveFileName(Context context, long id) {

		return saveFileParts(context, String.valueOf(id)) + "_" + dateString()
				+ ".wav";
	}

	public static String getDropBoxURL(String saveFileName) {

		return null;

	}

	/**
	 * ファイル名の文字列をFile型する。
	 *
	 * @param context
	 * @param id
	 * @return
	 */
	public static File saveFileNameFile(String fileName) {

		return new File(Constant.SDCRD_PATH + "/" + Constant.LIB_PATH + "/"
				+ fileName);
	}

	/**
	 * デバイスIDとものまねIDの結合
	 *
	 * @param context
	 * @param id
	 * @return
	 */
	public static String saveFileParts(Context context, String id) {
		return getDeviceID(context) + "-" + String.valueOf(id);
	}

	/**
	 * 日付取得
	 *
	 * @return Date
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 日付取得
	 *
	 * @return Date
	 */
	public static String getDateLongString(long longdate) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_PATTERN2);
		return sdf.format(new Date(longdate));

	}

	/**
	 * 日付をフォーマット
	 */
	public static String getDateFormat(long longdate) {
		String datestr = String.valueOf(longdate);
		String year = datestr.substring(0, 4);
		String month = datestr.substring(4, 6);
		String day = datestr.substring(6, 8);
		String date = year + "." + month + "." + day;
		return date;
	}

	/**
	 * 時間をフォーマット
	 */
	public static String getTimeFormat(long longdate) {
		String datestr = String.valueOf(longdate);
		String hour = datestr.substring(8, 10);
		String minutes = datestr.substring(10, 12);
		String seconds = datestr.substring(12, 14);
		String time = hour + ":" + minutes + ":" + seconds;
		return time;
	}

	/**
	 * 日付・時間フォーマット
	 * @param datetime
	 * @return
	 */
	public static String getDateTime(long datetime) {

		return getDateFormat(datetime) + " " + getTimeFormat(datetime);
	}

	/**
	 * Date日付型をString文字列型へ変換
	 *
	 * @param date
	 * @return
	 */
	public static String dateString() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_PATTERN);
		return sdf.format(getDate());
	}

	/**
	 * Date日付型をString文字列型へ変換
	 *
	 * @param date
	 * @return
	 */
	public static String dateString2() {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_PATTERN2);
		return sdf.format(getDate());
	}

	/**
	 * ものめね画像のリソースIDを返す。
	 *
	 * @param mimicryId
	 * @return
	 */
	public static int imgBind(int mimicryId) {

		return Constant.images[mimicryId];
	}

	/**
	 * 画像のURLからBitmapに変換して返す。
	 *
	 * @param url
	 * @return
	 */
	public static Bitmap getImgUrlBitmap(String url) {
		// イメージをBitmapへ変換
		URL imageUrl;
		InputStream imageIs = null;
		try {
			imageUrl = new URL(url);
			imageIs = imageUrl.openStream();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return BitmapFactory.decodeStream(imageIs);
	}

	public static ArrayList<HashMap<String, Object>> getRankingList(List<HashMap<String, Object>> outputArray,
			String jsonStr, int offset)
			throws JSONException {

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;

		JSONObject jsonobj = new JSONObject(jsonStr);
		JSONArray jsonArray = new JSONArray(jsonobj.getString("result"));

		for (int i = 0; i < jsonArray.length(); i++) {
			map = new HashMap<String, Object>();
			JSONObject jsonObj = jsonArray.getJSONObject(i);

			map.put("ranking", String.valueOf(i + 1 + offset));
			map.put("nicname", jsonObj.getString("nicname"));
			map.put("title", jsonObj.getString("title"));
			map.put("mimicry_id",
					Integer.parseInt(jsonObj.getString("mimictyId")));
			map.put("img", Util.imgBind(Integer.parseInt(jsonObj
					.getString("mimictyId"))));
			map.put("imgpath", jsonObj.getString("imgpath"));
			map.put("contributionDate",
					Util.getDateTime(jsonObj.getLong("savedate")));

			map.put("fileUrl", jsonObj.getString("fileurl"));
			map.put("fileName", jsonObj.getString("filename"));
			map.put("docId", jsonObj.getString("rankingId"));
			map.put("charactor", jsonObj.getString("title"));
			map.put("eene", jsonObj.getString("eene"));
			map.put("eeneflg", jsonObj.getString("eeneflg"));
			list.add(map);
		}
		outputArray.addAll(list);
		return (ArrayList<HashMap<String, Object>>) outputArray;

	}

	public static boolean isResultCheck(String jsonStr) {
		boolean check = false;

		if (jsonStr == null || jsonStr.equals("")) {
			return false;
		}

		try {
			JSONObject jsonobj = new JSONObject(jsonStr);
			String status = jsonobj.getString("status");
			if (status.equals("200")) {
				check = true;
			}

		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			Log.v("★", e.toString());
		}
		return check;

	}

	public static int resultCnt(String jsonStr) {

		int cnt = 0;
		try {
			JSONObject jsonobj = new JSONObject(jsonStr);
			JSONArray jsonArray = new JSONArray(jsonobj.getString("result"));
			cnt = jsonArray.length();
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			Log.v("★", e.toString());
		}

		return cnt;
	}

}
