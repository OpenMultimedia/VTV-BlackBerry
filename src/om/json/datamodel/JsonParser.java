package om.json.datamodel;

import java.util.Hashtable;

import org.json.me.*;


public class JsonParser {
	
	JSONArray ja;
	Hashtable properties;
	String[] propNames;
	
	public JsonParser(JSONArray jArray) {
		this.ja = jArray;
	}
	
	public JsonParser() {	}
	
	public String get_json_item_string(JSONObject jo, String item) {
	    	String rstr = "";
	    	
	    	try {
	    		if (jo.has(item)) {
	    			rstr = jo.getString(item).toString();
	    		} else {
	    			rstr= "lala";
	    		}
	    		return rstr;
	    	} catch (Exception e) {
	    		return rstr;
	    	}
	    }
	    
	    public String get_json_subitems_string(JSONObject jo, String parent, String item) {
	    	String rstr = "";
	    	
	    	try {
	    		if (jo.has(parent)) {
	    			JSONObject subjo = (JSONObject) jo.get(parent);
	            	rstr = get_json_item_string(subjo, item);
	    		} else {
	    			rstr= "";
	    		}
	    		return rstr;
	    	} catch (Exception e) {
	    		return rstr;
	    	}
	    }
	    
	    public void autoFillPropertieBundle(JSONObject jo) {
	    	for (int i = 0; i < propNames.length; i++) {
	    		String val = "";
	    		if (propNames[i].indexOf("@")==-1) {
	    			val = get_json_item_string(jo, propNames[i]);
	    			if (val!="") {
						properties.put(propNames[i], val);
					}
	    		} else {
	    			String parent = propNames[i].substring(propNames[i].indexOf("@")+1, propNames[i].indexOf("#"));
	    			String child = propNames[i].substring(propNames[i].indexOf("#")+1);
	    			val = get_json_subitems_string(jo,parent,child);
	    			if (val!="") {
	    				String nn = parent + "_" + child;
						properties.put(nn, val);
					}
	    		}
				
			}
	    	
	    }
	    
	    public Hashtable PropertiesToBundle(JSONObject jo) {
	    	Hashtable b = new Hashtable();
	    	for (int i = 0; i < propNames.length; i++) {
	    		String val = "";
	    		if (propNames[i].indexOf("@")==-1) {
	    			val = get_json_item_string(jo, propNames[i]);
	    			if (val!=""&&val!=null) {
						b.put(propNames[i], val);
					}
	    		} else {
	    			String parent = propNames[i].substring(propNames[i].indexOf("@")+1, propNames[i].indexOf("#"));
	    			String child = propNames[i].substring(propNames[i].indexOf("#")+1);
	    			val = get_json_subitems_string(jo,parent,child);
	    			if (val!=""&&val!=null) {
	    				String nn = parent + "_" + child;
						b.put(nn, val);
					}
	    		}
				
			}
	    	return b;    	
	    }

}
