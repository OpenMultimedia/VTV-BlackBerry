package om.json.datamodel;

import java.util.Hashtable;
import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

public class OmDataModel extends JsonParser {
	
	Vector vov = new Vector();
	
	public OmDataModel(JSONArray jArray) {
		super(jArray);
		do_parse(ja);
	}
	
	public OmDataModel(String str) {
		super();
		try {
			JSONArray jArray = new JSONArray(str);
			do_parse(jArray);
		} catch (JSONException e) { }
	}
	
	 public void do_parse(JSONArray jArray) {
	    	try {
				JSONObject j0 = jArray.getJSONObject(0);
				JSONArray ja = j0.getJSONArray("content");
				for (int ii = 0; ii < ja.length(); ii++) {
					Hashtable thistable = new Hashtable();
					NewsObject actualNews = new NewsObject();
					String[] aprops = actualNews.getPropNames();
					JSONObject joo = ja.getJSONObject(ii);
					for(int p = 0; p < aprops.length; p++) {
						if (joo.has(aprops[p])) {
							thistable.put(aprops[p], (joo.get(aprops[p])));		
						}	
					}
					if (joo.has("images")) {
						Vector imgs = new Vector();
						try {
							JSONArray jia = joo.getJSONArray("images");
							for(int ia = 0; ia < jia.length(); ia++) {
								ImageObject io = new ImageObject();
								System.out.print("IMAGE ADDED->");
								String[] iprops = io.getPropNames();
								Hashtable itable = new Hashtable();
								JSONObject jim = jia.getJSONObject(ia);
								for(int ic = 0; ic < iprops.length; ic++) {
									if (jim.has(iprops[ic])) {
										itable.put(iprops[ic], (jim.get(iprops[ic])));		
									}	
								}
								System.out.print(" size("+itable.size()+") ");
								io.fillObject(itable);
								imgs.addElement(io);
							}
						}catch(Exception e) { System.out.println("NOIMAGESINARTICLE->");}
						imgs.trimToSize();
						ImageObject[] images = new ImageObject[imgs.capacity()];
						imgs.copyInto(images);
						System.out.print("img bundle size "+ imgs.capacity()+ " array size a"+images.length);
						System.out.println("");
						actualNews.setImageArray(images);
						
					}
					actualNews.fillObject(thistable);
					vov.addElement(actualNews);			
				}
			} catch (Exception e) { 
				System.out.println("__JSONE___"+e.toString());
			}
      }
	 
	  public NewsObject[] getItems() {
		  vov.trimToSize();
		  NewsObject[] nob = new NewsObject[vov.capacity()];
		  vov.copyInto(nob);
		  return nob;  
	  }
}
