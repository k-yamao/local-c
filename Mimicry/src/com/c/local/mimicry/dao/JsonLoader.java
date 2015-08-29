package com.c.local.mimicry.dao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class JsonLoader extends AsyncTask<String, Integer, String> {

	private CallBackJson callbackjson;
	private String mode = "none";
	private List<NameValuePair> _nameValuePair;
	private String jsonStr;

	// コンストラクタ
	public JsonLoader() {
		mode = "none";
	}

	public JsonLoader(List<NameValuePair> nameValuePair) {
		mode = "post";
		_nameValuePair = nameValuePair;
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(String... params) {

		if (mode.equals("post")) {
			return getDataPost(params[0]);
		} else {
			return getData(params[0]);
		}
	}

	public void setOnCallBack(CallBackJson _cbj) {
		callbackjson = _cbj;
	}

	@Override
	protected void onPostExecute(String result) {
		callbackjson.CallBack(result);
	}

	public String getData(String _url) {
		DefaultHttpClient objHttp = new DefaultHttpClient();
		HttpParams params = objHttp.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpConnectionParams.setSoTimeout(params, 3000);
		String _return = "";
		try {
			HttpGet objGet = new HttpGet(_url);
			HttpResponse objResponse = objHttp.execute(objGet);
			if (objResponse.getStatusLine().getStatusCode() < 400) {
				InputStream objStream = objResponse.getEntity().getContent();
				InputStreamReader objReader = new InputStreamReader(objStream);
				BufferedReader objBuf = new BufferedReader(objReader);
				StringBuilder objJson = new StringBuilder();
				String sLine;
				while ((sLine = objBuf.readLine()) != null) {
					objJson.append(sLine);
				}
				_return = objJson.toString();
				objStream.close();
			}
		} catch (IOException e) {
			return "";
		}
		return _return;
	}

	public String getDataPost(String sUrl) {
		String sReturn = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(sUrl);
		try {
			if(jsonStr != null && !jsonStr.equals("")){
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-type", "application/json;charset=UTF-8");
				httppost.setHeader("Content-type", "application/json");
				httppost.setHeader("Accept-Charset", "utf-8");

				StringEntity se = new StringEntity(jsonStr);
				httppost.setEntity(se);
			} else {
				httppost.setEntity(new UrlEncodedFormEntity(_nameValuePair, "UTF-8"));
			}
			HttpResponse response = httpclient.execute(httppost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				sReturn = byteArrayOutputStream.toString();
			} else {
				return "";
			}
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
		return sReturn;
	}


	public void setJson(Map<String, String> item)  {

		JSONObject json = new JSONObject();
		try {

			//拡張for文（for-each)でループ
			for (Map.Entry<String, String> e : item.entrySet()) {

				if(e.getKey().equals("title") || e.getKey().equals("nicname")){
					json.put(e.getKey(), URLEncoder.encode(e.getValue(), "UTF-8"));
				}else{
					json.put(e.getKey(), e.getValue());
				}
			}

			jsonStr = json.toString();

		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

	}

}
