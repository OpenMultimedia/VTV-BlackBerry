package om.json.datamodel;

import java.util.Hashtable;


public class ImageObject extends GenericObject {
	
	static String[] propNames = {
			"id", "url", "snap", "alt", "caption", "author", "header"
	};
	
	public String[] getPropNames() {
		return propNames;
	}
	
	public ImageObject(Hashtable properties) {
		super(properties);
	}	
	
	public ImageObject() {
		super();
	}

}
