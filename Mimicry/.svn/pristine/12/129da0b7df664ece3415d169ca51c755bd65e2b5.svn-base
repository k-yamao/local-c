package com.c.local.mimicry;

import android.util.Log;

import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

public class DropboxAuth {
	

    final static private String APP_KEY = "sq4lxd2m8zsvd6t";  // 取得した App Key に入れ替えます
    final static private String APP_SECRET = "idft1ephrrevgdh";  // 取得した App Secret に入れ替えます
    final static private AccessType ACCESS_TYPE = AccessType.DROPBOX; // DROPBOX ならフルアクセス、APP_FOLDER は独自のフォルダを作成 (APP KEY を作成するときに選択したものでないとうまくいかない）

    final static private String TOKEN_APP_KEY = "o89e562zt73e1wk";  // 取得した App Key に入れ替えます
    final static private String TOKEN_APP_SECRET = "3sgyjxpz0shtft9";  // 取得した App Secret に入れ替えます

    
    public AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session;
        AccessTokenPair accessToken = new AccessTokenPair(TOKEN_APP_KEY, TOKEN_APP_SECRET);
        session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, accessToken);
        return session;
    }
}
