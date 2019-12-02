package hr.fer.zemris.java.jsdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
 * Represents a database of {@link Photo} read from the file,
 * used to store photos for our gallery.
 * 
 * @author Patrik Okanovic
 *
 */
public class PhotoDB {

	/**
	 * List of {@link Photo}
	 */
	public static List<Photo> photos = new ArrayList<>();
	
	/**
	 * all the tags
	 */
	public static Set<String> tags = new TreeSet<>();
	
	/**
	 * Returns a list of photos which have the given tag.
	 * 
	 * @param tag
	 * @return
	 */
	public static List<Photo> getPhotosWithTag(String tag) {
		List<Photo> list = new ArrayList<>();
		if(tag == null) {
			return list;
		}
		tag = tag.toLowerCase();
		for(Photo photo : photos) {
			String[] tags = photo.getTags();
			for(int j = 0; j < tags.length; j++) {
				if(tags[j].equals(tag)) {
					list.add(photo);
					break;
				}
			}
		}
		return list;
	}

	/**
	 * Returns tags as a list
	 * @return
	 */
	public static List<String> getTags() {
		return new ArrayList<String>(tags);
	}

	/**
	 * Used to return a photo by fileName from the database
	 * 
	 * @param fileName
	 * @return
	 */
	public static Photo getPhoto(String fileName) {
		for(Photo photo : photos) {
			if(photo.getFileName().equals(fileName)) {
				return photo;
			}
		}
		return null;
	}
}
