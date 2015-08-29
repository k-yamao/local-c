package com.c.local.mimicry;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

import android.app.Activity;
import android.os.Bundle;

public class DropboxTestActivity extends Activity {
    final static private String APP_KEY = "sq4lxd2m8zsvd6t";  // 取得した App Key に入れ替えます
    final static private String APP_SECRET = "idft1ephrrevgdh";  // 取得した App Secret に入れ替えます
    final static private AccessType ACCESS_TYPE = AccessType.DROPBOX; // DROPBOX ならフルアクセス、APP_FOLDER は独自のフォルダを作成 (APP KEY を作成するときに選択したものでないとうまくいかない）

    DropboxAPI<AndroidAuthSession> mApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropbox);

        AndroidAuthSession session = buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);

        // 以下を実行することで、AuthActivityが呼び出されます
        mApi.getSession().startAuthentication(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();

        // session.startAuthentication() に対応して
        // session.finishAuthentication() を呼んでやる必要があります
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();
            } catch (IllegalStateException e) {
                // TODO: エラー処理
            }
        }
    }

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session;

        session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);

        return session;
    }
}