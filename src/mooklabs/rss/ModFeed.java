package mooklabs.rss;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class ModFeed {

	final String title;
	final String link;
	final String description;
	final String pubDate;

	final List<ModFeedMessage> entries = new ArrayList<ModFeedMessage>();

	public ModFeed(String title, String link, String description, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
	}

	public List<ModFeedMessage> getMessages() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getPubDate() {
		return pubDate;
	}

	@Override
	public String toString() {
		return "Feed [description=" + description + ", link=" + link + ", pubDate="
				+ pubDate + ", title=" + title + "]";
	}

}