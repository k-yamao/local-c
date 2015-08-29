/*
 * Licensed under the Apache License, Version 2.0 (the "License").
 */
package com.c.local.mimicry.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.impl.cookie.DateParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.c.local.mimicry.Entity.MinicryEntity;

/**
 * XMLパースのヘルパークラス。
 */
public class JsonHelper {
    /**
     * RSSを表すXMLが取得可能かどうか判断する。
     * 
     * @param inputStream
     * @return boolean
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public boolean isRssFeed(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        NodeList nodes = document.getChildNodes();
        for (int cnt = 0; cnt < nodes.getLength(); cnt++) {
            Node node = nodes.item(cnt);
            if (node == null) {
                continue;
            }
            if ("rss".equals(node.getNodeName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * XMLのInputStreamからRSSフィードをパースする。
     * 
     * @param inputStream
     * @return List<RssFeedEntity>
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws DateParseException 
     * @throws DOMException 
     */
    public List<MinicryEntity> parcejson(InputStream is){
    	
    	BufferedReader reader;
    	StringBuffer buf = new StringBuffer();
    	MinicryEntity photoSetEntity = null;
    	 List<MinicryEntity> photoSetEntityList = new ArrayList<MinicryEntity>();
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    	    String str;
    	    while ((str = reader.readLine()) != null) {
    	            buf.append(str);
    	            
    	    }
    	    JSONArray jsonArray = new JSONArray(buf.toString());
    	
    	    int count = jsonArray.length();
    	    
    	    JSONObject[] photoSetObject = new JSONObject[count];
    	    
    	    for (int i = 0; i < count; i++) {
    	    	photoSetObject[i] = jsonArray.getJSONObject(i);
				
			}    	    
    	    for (int i=0; i<photoSetObject.length; i++){
//    	    	photoSetEntity = new PhotoSetEntity();
//    	    	photoSetEntity.setSetName(photoSetObject[i].getString("setname"));
//    	    	photoSetEntity.setArea(photoSetObject[i].getInt("area"));
//    	    	photoSetEntity.setSetUrl(photoSetObject[i].getString("seturl"));
//    	    	photoSetEntity.setSetNo(photoSetObject[i].getString("setno"));
//    	    	photoSetEntity.setSetNsid(photoSetObject[i].getString("nsid"));
//    	    	photoSetEntity.setFormat(photoSetObject[i].getString("format"));
//    	    	photoSetEntity.setDocId(photoSetObject[i].getString("_docId"));
//    	    	photoSetEntity.setUpdatedAt(photoSetObject[i].getLong("_updatedAt"));
//    	    	photoSetEntity.setCreatedAt(photoSetObject[i].getLong("_createdAt"));

    	    	photoSetEntityList.add(photoSetEntity);
    	    
    	    }
    	    
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	    
    	    
    	    
    	    return photoSetEntityList;
    	
    }
}
