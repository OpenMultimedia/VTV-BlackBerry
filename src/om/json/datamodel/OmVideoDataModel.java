package om.json.datamodel;

import java.util.Hashtable;
import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

public class OmVideoDataModel extends JsonParser {
	
	Vector vov = new Vector();
	
	public OmVideoDataModel(JSONArray jArray) {
		super(jArray);
		do_parse(ja);
	}
	
	public OmVideoDataModel(String str) {
		super();
		try {
			JSONArray jArray = new JSONArray(str);
			do_parse(jArray);
		} catch (JSONException e) { }
	}
	
	 public void do_parse(JSONArray jArray) {
	    	try {
				for (int ii = 0; ii < jArray.length(); ii++) {
					Hashtable thistable = new Hashtable();
					VideoObject actualVids = new VideoObject();
					String[] aprops = actualVids.getPropNames();
					JSONObject joo = jArray.getJSONObject(ii);
					
					
					
					for(int p = 0; p < aprops.length; p++) {												
						String val = "";
			    		if (aprops[p].indexOf("@")==-1) {
			    			if (joo.has(aprops[p])) {
								Object getted = joo.get(aprops[p]);
								if (getted != null) {
									thistable.put(aprops[p], (joo.get(aprops[p])));
								}
							}	
			    		} else {
			    			String parent = aprops[p].substring(aprops[p].indexOf("@")+1, aprops[p].indexOf("#"));
			    			String child = aprops[p].substring(aprops[p].indexOf("#")+1);
			    			val = get_json_subitems_string(joo,parent,child);
			    			if (val!="") {
			    				String nn = parent + "_" + child;
			    				thistable.put(nn, val);
							}
			    		}
					}
					actualVids.fillObject(thistable);
					vov.addElement(actualVids);			
				}
			} catch (Exception e) { 
				System.out.println("__JSONE___"+e.toString());
			}
      }
	 
	  public VideoObject[] getItems() {
		  vov.trimToSize();
		  VideoObject[] vob = new VideoObject[vov.capacity()];
		  vov.copyInto(vob);
		  return vob;  
	  }
	 

}
