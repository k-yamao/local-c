/*
 * Copyright 2010 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.c.local.mimicry;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

/**
 * A handler for the login page.
 * 
 * @author yariv
 */
public class LoginHandler extends Handler {

	// The permissions that the app should request from the user
	// when the user authorizes the app.
	private static String[] PERMISSIONS = new String[]{ "offline_access", "read_stream", "publish_stream" };

	public static final String FB_APP_ID = "235389116573658";

	/**
	 * Render the Login page.
	 */
	public void go() {
		// dispatcher.getWebView().addJavascriptInterface(
		// new JsHandler(), "app");
		// dispatcher.loadFile("login.html");
		// dispatcher.getWebView().
		new JsHandler().login();
	}

	/**
	 * Contains functions that are exported to the Javascript context in
	 * Login.html
	 * 
	 * @author yariv
	 */
	private class JsHandler {

		/**
		 * Opens the Facebook login dialog.
		 */
		public void login() {
			final Activity activity = LoginHandler.this.getActivity();
			activity.runOnUiThread(new Runnable() {
				public void run() {
					// We need to temporarily remove the app's WebView
					// instance because Android apparently doesn't support
					// multiple WebView instances in the same app.

					// dispatcher.hideWebView();
					final Facebook fb = new Facebook(FB_APP_ID);
					Session.waitForAuthCallback(fb);
					fb.authorize(getActivity(), PERMISSIONS,
							new AppLoginListener(fb));

				}
			});
		}

		private class AppLoginListener implements DialogListener {

			private Facebook fb;

			public AppLoginListener(Facebook fb) {
				this.fb = fb;
			}

			public void onCancel() {
				Log.d("app", "login canceled");
				ImageView fblogin = (ImageView) getActivity().findViewById(
						R.id.fblogin);
				TextView fbname = (TextView) getActivity().findViewById(
						R.id.fbname);
				if(fblogin != null){
					fblogin.setImageResource(R.drawable.login);
					fbname.setText("ログインしてください。");
					
				}
			}

			public void onComplete(Bundle values) {
				/**
				 * We request the user's info so we can cache it locally and use
				 * it to render the new html snippets when the user updates her
				 * status or comments on a post.
				 */
				
				
				new AsyncFacebookRunner(fb).request("/me", 
                        new AsyncRequestListener() {
                    public void onComplete(JSONObject obj, final Object state) {
                        // save the session data
                        String uid = obj.optString("id");
                        String name = obj.optString("name");
                        new Session(fb, uid, name).save(getActivity());
                        final String fbn = name;
                     // render the Stream page in the UI thread
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {


                				ImageView fblogin = (ImageView) getActivity().findViewById(
                						R.id.fblogin);
                				TextView fbname = (TextView) getActivity().findViewById(
                						R.id.fbname);
                				if(fblogin != null){
                					fblogin.setImageResource(R.drawable.logout);
                					fbname.setText(fbn + "さんでログインしています。");	
                				}
                				
                            	
                            	
                            	
                            }
                        });
                    }
                }, null);

//				ImageView fblogin = (ImageView) getActivity().findViewById(
//						R.id.fblogin);
//				TextView fbname = (TextView) getActivity().findViewById(
//						R.id.fbname);
//				JSONObject obj = null;
//				try {
//					String jo = fb.request("/me");
//					obj = new JSONObject(jo);
//					String uid = obj.optString("id");
//					String name = obj.optString("name");
//					new Session(fb, uid, name).save(getActivity());
//					fblogin.setImageResource(R.drawable.logout);
//					fbname.setText(name + "さんでログインしています。");
//
//				} catch (Exception e) {
//					fbname.setText("");
//					fblogin.setImageResource(R.drawable.login);
//					e.printStackTrace();
//				}

				
				// new AsyncFacebookRunner(fb).request("/me",
				// new AsyncRequestListener() {
				// public void onComplete(JSONObject obj, final Object state) {
				// // save the session data
				// String uid = obj.optString("id");
				// String name = obj.optString("name");
				// new Session(fb, uid, name).save(getActivity());
				//
				//
				//
				// }
				// }, null);

			}

			public void onError(DialogError e) {
				Log.d("app", "dialog error: " + e);
			}

			public void onFacebookError(FacebookError e) {
				Log.d("app", "facebook error: " + e);
			}
		}
	}

}
