package com.tsystems.rssreader.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tsystems.rssreader.database.Feed;

public class RssParser {
	
	private String uri;
	
	public RssParser(String uri) {
		this.uri = uri;
	}

	/**
	 * It is better to use SAX parser but it also requires more time.
	 * */
	public List<Feed> parse(InputStream is) throws ParserConfigurationException,
												   SAXException, IOException {
		
		List<Feed> result = new ArrayList<Feed>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = factory.newDocumentBuilder();
	    Document doc = docBuilder.parse(is);
	
	    NodeList nodelist = doc.getElementsByTagName("item");
	    final int size = nodelist.getLength();
	    for (int i = 0; i < size; i++) {
	        Node node = nodelist.item(i);
	        if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element element = (Element) node;
	            NodeList guidList = element.getElementsByTagName("guid");
	            NodeList titleList = element.getElementsByTagName("title");
	            NodeList descList = element.getElementsByTagName("description");
	            NodeList pubDateList = element.getElementsByTagName("pubDate");
	
	            Feed feed = new Feed();
	            feed.setGuid(guidList.item(0).getChildNodes().item(0).getNodeValue());
	            feed.setTitle(titleList.item(0).getChildNodes().item(0).getNodeValue());
	            
	            String unformed = descList.item(0).getChildNodes().item(0).getNodeValue();
	            feed.setDesc(cutDivs(unformed));
	
	            String pubDate = pubDateList.getLength() == 0 ?
	                    "Wrong format of date" :
	                    pubDateList.item(0).getChildNodes().item(0).getNodeValue();
	            
	            feed.setPubDate(pubDate);
	            feed.setLink(uri);
	            feed.setViewed("false");
	            
	            result.add(feed);
	        }
	    }
	    
	    return result;
	}
	
	private String cutDivs(final String unformed) {
		final int idx = unformed.indexOf("<div");
		final String pretty = idx != -1 ? unformed.substring(0, idx) : unformed;
		return pretty;
	}
}