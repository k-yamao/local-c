package com.c.local.mimicry;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.c.local.mimicry.dao.DatabaseOpenHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

public class SettingActivity extends Activity {

	private ImageView fblogin;
	private Dispatcher dispatcher;
	private Facebook facebook;
	private AsyncFacebookRunner facebookRunner;
	private TextView facebookName;
	private Session session;
	private EditText nicnameEditText;
	private String nicname;
	private DatabaseOpenHelper databaseOpenHelper;
	private Button nicnamebtn;
	private Boolean nicnameFlag = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		TextView mtitle = ((TextView) findViewById(R.id.listtitle));
		mtitle.setText(Constant.LISTTITLE_MIMICRY_SETTING);
		
		fblogin = (ImageView) findViewById(R.id.fblogin);

		session = Session.restore(this);
		if (session != null) {
			facebook = session.getFb();
			facebookRunner = new AsyncFacebookRunner(facebook);
		}
		facebookName = ((TextView) findViewById(R.id.fbname));
		
		

		// Facebookログイン処理
		dispatcher = new Dispatcher(this);
		dispatcher.addHandler("login", LoginHandler.class);
		dispatcher.addHandler("logout", LogoutHandler.class);

		// ニックネームEditText
		nicnameEditText = (EditText) findViewById(R.id.nicname);

		// Facebookのコメントにフォーカスが移動したらキーボードを開き、フォーカスが外れるとキーボードを閉じる
		//nicnameEditText.setOnFocusChangeListener(new EditFocus());
		
		
		nicnamebtn =  (Button) findViewById(R.id.nicname_regist_btn);
		
		databaseOpenHelper = new DatabaseOpenHelper(this);
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {

			// 保存ファイル名
			// saveFileId = deviceId + "-" + String.valueOf(id);

			database = databaseOpenHelper.getReadableDatabase();
			cursor = database.query("SETTING", null, "_id = 1", null,
					null, null, null);

			startManagingCursor(cursor);
			if (cursor.moveToFirst()) {
				nicnameFlag = true;
				nicnameEditText.setText(cursor.getString(cursor.getColumnIndex("NICNAME")));// DBからニックネームをセット
				nicnameEditText.setEnabled(false);
				nicnamebtn.setText(Constant.NICNAME_CHANGE_BTN);
				
			}else{
				nicnameFlag = false;
				nicnameEditText.setEnabled(true);
				nicnamebtn.setText(Constant.NICNAME_REGIST_BTN);
			}

			
			
		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		fblogin.setImageResource(session != null ? R.drawable.logout
				: R.drawable.login);

		facebookName.setText(session != null ? session.getName()
				+ "さんでログインしています。" : "ログインしてください。");
	}

	/**
	 * facebookログインボタンを押したとき
	 * 
	 * @param view
	 */
	public void onLogin(View view) {

		if (Util.isNetConnect(this)) {

			Session session = Session.restore(this);

			if (session != null) {
				fblogin.setImageResource(R.drawable.logout_down);
				dispatcher.runHandler("logout");
			} else {
				fblogin.setImageResource(R.drawable.login_down);
				dispatcher.runHandler("login");
			}
		}else{
			Util.isNetConnectDialog(SettingActivity.this);
		}
	}

	public void onNicnameRegist(View view) {

		if(nicnameFlag){
			nicnameEditText.setEnabled(true);
			nicnamebtn.setText(Constant.NICNAME_REGIST_BTN);
			nicnameFlag = false;
		}else{
			nicname = nicnameEditText.getText().toString();
			if (nicname != null && !nicname.equals("")) {
				saveNicnameRegist(nicname);
				nicnameEditText.setEnabled(false);
				nicnamebtn.setText(Constant.NICNAME_CHANGE_BTN);
			} else {
				
				Util.isRequired(SettingActivity.this, Constant.NICNAME_NULL);
				
			}

		}
		
	}

	/**
	 * ニックネーム保存
	 */
	public void saveNicnameRegist(String nicname) {

		DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(
				getApplicationContext());
		SQLiteDatabase database = null;
		ContentValues contentValues;
		try {

			if (database != null) database.close();

			database = databaseOpenHelper.getWritableDatabase();
			database.beginTransaction();
			
			databaseOpenHelper.onSettingUpgrade(database);
			
			contentValues = new ContentValues();
			
			
			contentValues.put("NICNAME", nicname);

			database.insert("SETTING", null, contentValues);
			database.setTransactionSuccessful();
			Util.isRequired(SettingActivity.this, Constant.NICNAME_COMP);

		} catch (Exception exception) {
			Log.e(exception.getClass().getName(), exception.getMessage(),
					exception);
			Util.isRequired(SettingActivity.this, Constant.NICNAME_FAIL);
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Facebook fb = Session.wakeupForAuthCallback();
		fb.authorizeCallback(requestCode, resultCode, data);
	}

	private class EditFocus implements View.OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// フォーカスを受け取ったとき
			if (hasFocus) {
				// ソフトキーボードを表示する
				inputMethodManager.showSoftInput(v,
						InputMethodManager.SHOW_FORCED);
			}
			// フォーカスが外れたとき
			else {
				// ソフトキーボードを閉じる
				inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
						0);
			}
		}
	}

}
