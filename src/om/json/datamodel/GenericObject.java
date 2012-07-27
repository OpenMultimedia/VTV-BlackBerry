package om.json.datamodel;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class GenericObject {
	
	String name;
	Hashtable abstractProperties;
	Vector nested;
	
	public GenericObject(Hashtable properties) {
		this.abstractProperties = properties;
		autoFillProperties();
	}
	
	public GenericObject() { }
	
	public String getSelfName() {
		return name;
	}
	
	public void setSelfName(String name) {
		this.name = name;
	}
	
	public GenericObject getNestedObject(String name) {
		Enumeration ne = nested.elements();
		do {
			GenericObject to = (GenericObject)ne.nextElement();
			if (name.equalsIgnoreCase(to.getSelfName())) {
				return to;
			}
		} while (ne.hasMoreElements());
		return null;
	}
	
	public void setNestedObject(GenericObject go) {
		nested.addElement(go);
	}
	
	public void fillObject(Hashtable properties) {
		this.abstractProperties = properties;
		autoFillProperties();
	}
	
	public void autoFillProperties() { }
	
	public String[] getPropNames() {
		return null;
	}
	
	public Hashtable getBundle() {
		return abstractProperties;
	}
	
	public String getKeysNames() {
		return "LALA";
	}
	
	public String getP(String name) {
		String r = "";
		if (abstractProperties.containsKey(name)){
			r = (String)(abstractProperties.get(name));
			if (r == null) { return ""; }
		} 
		return r;
	}
	
	public String getString(String name) {
		String r = "";
		if (abstractProperties.containsKey(name)){
			try {
				r = (String)(abstractProperties.get(name));
			} catch (Exception e) {
				r = "";
			}
			if (r == null) { return ""; }
		} 
		return r;
	}
	
	public int getInt(String name) {
		int r = -1;
		if (abstractProperties.containsKey(name)){
			r = Integer.parseInt((String) (abstractProperties.get(name)));
			if (r == -1) { return -1; }
		} 
		return r;
	}
	
	public String[] getStrArray(String name) {
		String[] r = {};
		if (abstractProperties.containsKey(name)){
			r = (String[])(abstractProperties.get(name));
			if (r == null) { String [] m = {}; return m; }
		} 
		return r;
	}
	
	public String getArrayAsString(String name) {
		String r = "";
		if (abstractProperties.containsKey(name)){
			String a[] = (String[])(abstractProperties.get(name));
			if (a!=null) {
				for (int i = 0; i < a.length; i++) {
					r += a[i]+" ";
				}
			}
		} 
		return r;
	}
	
	public boolean setP(String name, Object val) {
		if (abstractProperties.containsKey(name)){
			abstractProperties.put(name, val);
			return true;
		} else {
			return false;
		}
	}
	

}
