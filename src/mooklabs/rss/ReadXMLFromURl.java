package mooklabs.rss;

import java.util.ArrayList;


public class ReadXMLFromURl {
	public static ArrayList<ModFeedMessage> getModpackData(String url) {
		ModPackFeedParser parser = new ModPackFeedParser(url);
		ModFeed feed = parser.readFeed();
		//System.out.println(feed);
		ArrayList<ModFeedMessage> s = new ArrayList();
		for (ModFeedMessage message : feed.getMessages()) {
			System.out.println(message);
			s.add(message);
		}
		return s;

	}
	public static ArrayList<FeedMessage> getRssData(String url) {
		RSSFeedParser parser = new RSSFeedParser(url);
		Feed feed = parser.readFeed();
		System.out.println(feed);
		ArrayList<FeedMessage> s = new ArrayList();
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);
			s.add(message);
		}
		return s;

	}
}