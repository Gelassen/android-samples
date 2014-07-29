package com.home.calltruck;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class CreateOrderCommand implements Serializable {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private String name;
	
	private String phone;
	
	private String latFrom;
	
	private String lonFrom;
	
	private String addressFrom;
	
	private String latWhere;
	
	private String lonWhere;
	
	private String addressWhere;
	
	private String description;
	
	public CreateOrderCommand(String name, String phone, double latFrom,
			double lonFrom, String addressFrom, double latWhere,
			double lonWhere, String addressWhere, String description) {
		this.name = name;
		this.phone = phone;
		this.latFrom = latFrom != 0d ? String.valueOf(latFrom) : null;
		this.lonFrom = lonFrom != 0d ? String.valueOf(lonFrom) : null;
		this.addressFrom = addressFrom;
		this.latWhere = latWhere != 0d ? String.valueOf(latWhere) : null;
		this.lonWhere = lonWhere != 0d ? String.valueOf(lonWhere) : null;
		this.addressWhere = addressWhere;
		this.description = description;
	}
	
	public HttpPost createFakeRequest() throws UnsupportedEncodingException {
		HttpPost post = new HttpPost("http://all-evak.ru/market/api/1.4/");
		String fakeReq = getXmlFile();
		StringEntity entity = new StringEntity(fakeReq, UTF_8.name());
		post.setEntity(entity);
		return post;
	}
	
	public String getXmlFile() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument(UTF_8.name(), true);
			serializer.startTag("", "reguest");
			createMeta(serializer);
			createData(serializer);
			serializer.endTag("", "reguest");
			serializer.endDocument();
		} catch (Exception e) {
			
		}
		return writer.toString();
	}
	
	private void createMeta(XmlSerializer serializer) throws Exception, IllegalStateException, IOException {
		serializer.startTag("", "meta");
		serializer.startTag("", "type");
		serializer.text("CreateOrder");
		serializer.endTag("", "type");
		serializer.startTag("", "date");
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formedDate = format.format(date);
		serializer.text(formedDate);
		serializer.endTag("", "date");
		serializer.startTag("", "uid");
		serializer.text("evacm-50b5389895b13");
		serializer.endTag("", "uid");
		serializer.startTag("", "devise_name");
		serializer.text(android.os.Build.MODEL);
		serializer.endTag("", "devise_name");
		serializer.startTag("", "devise_version");
		serializer.text(android.os.Build.VERSION.RELEASE);
		serializer.endTag("", "devise_version");
		serializer.endTag("", "meta");
	}
	
	private void createData(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "data");
		serializer.startTag("", "client");
		serializer.startTag("", "phone_number");
		serializer.text(phone);
		serializer.endTag("", "phone_number");
		if (null != name) {
			serializer.startTag("", "name");
			serializer.text(name);
			serializer.endTag("", "name");
		}
		serializer.endTag("", "client");
		// write pick up location
		serializer.startTag("", "location");
		if (null != latFrom && null != lonFrom) {
			serializer.startTag("", "lat");
			serializer.text(latFrom);
			serializer.endTag("", "lat");
			serializer.startTag("", "lon");
			serializer.text(lonFrom);
			serializer.endTag("", "lon");
		}
		if (null != addressFrom) {
			serializer.startTag("", "address");
			serializer.text(addressFrom);
			serializer.endTag("", "address");
		}
		serializer.endTag("", "location");
		// write destination
		serializer.startTag("", "dest");
		if (null != latWhere && null != lonWhere) {
			serializer.startTag("", "lat");
			serializer.text(latWhere);
			serializer.endTag("", "lat");
			serializer.startTag("", "lon");
			serializer.text(lonWhere);
			serializer.endTag("", "lon");
		}
		if (null != addressWhere) {
			serializer.startTag("", "address");
			serializer.text(addressWhere);
			serializer.endTag("", "address");
		}
		serializer.endTag("", "dest");
		serializer.startTag("", "description");
		serializer.text(description);
		serializer.endTag("", "description");
		serializer.endTag("", "data");
	}
	
}
