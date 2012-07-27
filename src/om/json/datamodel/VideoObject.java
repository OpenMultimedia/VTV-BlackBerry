package om.json.datamodel;

import java.util.Hashtable;


public class VideoObject extends GenericObject {
	
	ImageObject[] imgs;
	
	static String[] propNames = {
			"titulo", "slug", "descripcion", "archivo_url", "fecha", "thumbnail_grande", "thumbnail_mediano", "url", "duracion", "pais", "ciudad", "programa",
			"nombre", "imagen_url", "@programa#nombre"
	};
	
	public String[] getPropNames() {
		return propNames;
	}
	
	public VideoObject(Hashtable properties) {
		super(properties);
	}	
	
	public VideoObject() {
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
