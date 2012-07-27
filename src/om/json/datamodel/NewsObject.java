package om.json.datamodel;

import java.util.Hashtable;


public class NewsObject extends GenericObject {
	
	ImageObject[] imgs;
	
	static String[] propNames = {
			"id", "family", "section", "mtype", "noteXmlUrl", "title", "edTitle", "summary", "abstr", "series", "author", "date", "audio"
	};
	
	public String[] getPropNames() {
		return propNames;
	}
	
	public NewsObject(Hashtable properties) {
		super(properties);
	}	
	
	public NewsObject() {
		super();
	}
	
	public ImageObject getImageObject(int i) {
		if (i<imgs.length) {
			return imgs[i];
		}
		return null;
	}
	
	public void setImageArray(ImageObject[] images) {
		for (int i = 0; i < images.length; i++){
			System.out.println("ARRAY SET->");
		}
		imgs = images;
	}
	
	public String getStringFromImg(int i, String name) {
		if (i<imgs.length) {
			return imgs[i].getString(name);
		}
		return "";
	}

}
