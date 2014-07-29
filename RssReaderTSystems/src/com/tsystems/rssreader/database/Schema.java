package com.tsystems.rssreader.database;

public class Schema {
	
	public final static String TAG = "RssReader";

	public static class Links {

		public static final String TABLE_NAME = "links";

		public static final String _ID = "_id";

		public static final String ID = "id";

		public static final String LINK = "link";
		
		public static final String TITLE = "title";

	}
	
	public static class Feeds {

		public static final String TABLE_NAME = "feeds";

		public static final String _ID = "_id";

		public static final String ID = "id";

		public static final String LINK = "link";
		
		public static final String GUID = "guid";
		
		public static final String TITLE = "title";
		
		public static final String DESC = "desc";
		
		public static final String PUB_DATE = "pub_date";
		
		public static final String VIEWED = "viewed";

	}

}
