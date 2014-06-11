package mooklabs.rss;

import java.util.ArrayList;


public class ReadXMLFromURl {
	public static ArrayList<FeedMessage> getModpackData(String url) {
		RSSFeedParser parser = new RSSFeedParser(url);
		Feed feed = parser.readFeed();
		//System.out.println(feed);
		ArrayList<FeedMessage> s = new ArrayList();
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);
			s.add(message);
		}
		return s;

	}
}