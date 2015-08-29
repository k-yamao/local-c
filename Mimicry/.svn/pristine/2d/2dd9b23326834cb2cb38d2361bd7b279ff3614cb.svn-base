package com.c.local.mimicry;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

public class DropboxActivity extends Activity {
    final static private String APP_KEY = "sq4lxd2m8zsvd6t";  // 取得した App Key に入れ替えます
    final static private String APP_SECRET = "idft1ephrrevgdh";  // 取得した App Secret に入れ替えます
    final static private AccessType ACCESS_TYPE = AccessType.DROPBOX; // DROPBOX ならフルアクセス、APP_FOLDER は独自のフォルダを作成 (APP KEY を作成するときに選択したものでないとうまくいかない）

    final static private String TOKEN_APP_KEY = "o89e562zt73e1wk";  // 取得した App Key に入れ替えます
    final static private String TOKEN_APP_SECRET = "3sgyjxpz0shtft9";  // 取得した App Secret に入れ替えます


    // Preference に保存するためのKEY
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    DropboxAPI<AndroidAuthSession> mApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropbox);

        AndroidAuthSession session = buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);

        if (!mApi.getSession().isLinked()) { // 認証済みでなかったら
            // 以下を実行することで、AuthActivityが呼び出されます
            mApi.getSession().startAuthentication(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();

        // session.startAuthentication() に対応した
        // session.finishAuthentication() を呼んでやる必要があります
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();

                // session key を保存
                TokenPair tokens = session.getAccessTokenPair();
                Log.v("★★★★★★★", tokens.key + "::" + tokens.secret);
                
                storeKeys(tokens.key, tokens.secret);
            } catch (IllegalStateException e) {
                // TODO: エラー処理
            }
        }
    }

    /**
     * 明示的にLogOutしないと、最初に使用したアカウントが使用され続けるので、
     * ユーザにlogOutさせたいときに、このメソッドを呼ぶ
     */
    private void logOut() {
        mApi.getSession().unlink();

        clearKeys();
    }

    /**
     * Preference に保存されているKeyがある場合は、それを返す
     * ない場合は null
     *
     * @return Array of [access_key, access_secret], or null if none stored
     */
    private String[] getKeys() {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(ACCESS_KEY_NAME, null);
        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
        if (key != null && secret != null) {
            String[] ret = new String[2];
            ret[0] = key;
            ret[1] = secret;
            return ret;
        } else {
            return null;
        }
    }

    /**
     * Preference に Key を保存する
     */
    private void storeKeys(String key, String secret) {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        Editor edit = prefs.edit();
        edit.putString(ACCESS_KEY_NAME, key);
        edit.putString(ACCESS_SECRET_NAME, secret);
        edit.commit();
    }

    /**
     * Preference の Key を削除する
     */
    private void clearKeys() {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }


    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session;


        String[] stored = getKeys();
        if (stored != null) {
            AccessTokenPair accessToken = new AccessTokenPair(stored[0], stored[1]);
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, accessToken);
            Log.v("★★★★★", accessToken.toString());
        } else {
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
        }

        return session;
    }
}