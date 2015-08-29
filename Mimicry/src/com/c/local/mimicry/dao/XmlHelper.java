/*
 * Licensed under the Apache License, Version 2.0 (the "License").
 */
package com.c.local.mimicry.dao;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.c.local.mimicry.Entity.MinicryEntity;

import android.util.Log;

/**
 * XMLパースのヘルパークラス。
 */
public class XmlHelper {
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
    public List<MinicryEntity> parseRssFeeds(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException, DOMException, DateParseException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        NodeList nodes = document.getChildNodes();
        List<MinicryEntity> rssFeedEntities = new ArrayList<MinicryEntity>();
        for (int cnt = 0; cnt < nodes.getLength(); cnt++) {
            Node node = nodes.item(cnt);
            if (node == null) {
                continue;
            }
            if ("rss".equals(node.getNodeName())) {
                NodeList childNodes = node.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    if (childNode == null) {
                        continue;
                    }
                    if ("channel".equals(childNode.getNodeName())) {
                        NodeList channelNodes = childNode.getChildNodes();
                        String senderName = null;
                        String url = null;
                        for (int j = 0; j < channelNodes.getLength(); j++) {
                            Node channelNode = channelNodes.item(j);
                            if (channelNode == null) {
                                continue;
                            }
                            if ("title".equals(channelNode.getNodeName())) {
                                senderName = channelNode.getFirstChild().getNodeValue();
                            } else if ("link".equals(channelNode.getNodeName())) {
                                url = channelNode.getFirstChild().getNodeValue();
                            } else if ("item".equals(channelNode.getNodeName())) {
                            	MinicryEntity rssFeedEntity = new MinicryEntity();
//                                rssFeedEntity.setSenderName(senderName);
//                                rssFeedEntity.setUrl(url);
                                NodeList itemNodes = channelNode.getChildNodes();
                                for (int k = 0; k < itemNodes.getLength(); k++) {
                                    Node itemNode = itemNodes.item(k);
                                    if (itemNode == null) {
                                        continue;
                                    }
                                    if ("description".equals(itemNode.getNodeName())) {
                                        //rssFeedEntity.setDescription(itemNode.getFirstChild().getNodeValue());
                                    } else if ("content:encoded".equals(itemNode.getNodeName())) {
                                    	//rssFeedEntity.setContents(itemNode.getFirstChild().getNodeValue());
                                    } else if ("title".equals(itemNode.getNodeName())) {
                                        //rssFeedEntity.setTitle(itemNode.getFirstChild().getNodeValue());
                                    } else if ("link".equals(itemNode.getNodeName())) {
                                        NodeList linkChilds = itemNode.getChildNodes();
                                        StringBuilder link = new StringBuilder();
                                        for (int l = 0; l < linkChilds.getLength(); l++) {
                                            link.append(linkChilds.item(l).getNodeValue() == null ? "" : linkChilds.item(l).getNodeValue());
                                        }
                                        //rssFeedEntity.setLink(link.toString());
                                    } else if ("guid".equals(itemNode.getNodeName())) {
                                        NodeList guidChilds = itemNode.getChildNodes();
                                        StringBuilder guid = new StringBuilder();
                                        for (int l = 0; l < guidChilds.getLength(); l++) {
                                            guid.append(guidChilds.item(l).getNodeValue() == null ? "" : guidChilds.item(l).getNodeValue());
                                        }
                                        //rssFeedEntity.setGuid(guid.toString());
                                    } else if ("pubDate".equals(itemNode.getNodeName())) {
                                    	/** RSSフィードの日付フォーマットパターン */  
                                    	String pattern[] = {DateUtils.PATTERN_RFC1123};  
                                    	String formattedPubdate = "";
                                    	Date date = DateUtils.parseDate(itemNode.getFirstChild().getNodeValue(), pattern);  
                                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MMM.dd,'at' HH:mm");  
                                    	formattedPubdate = sdf.format(date);  
                                        //rssFeedEntity.setPublishDate(formattedPubdate);
                                    }
                                }
                                rssFeedEntities.add(rssFeedEntity);
                            }
                        }
                    }
                }
            }
            Log.v("XmlHelper", "Succeeded in retrieving the RssFeedEntity list.");
        }
        return rssFeedEntities;
    }
}
