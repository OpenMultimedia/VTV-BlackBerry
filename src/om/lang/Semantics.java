package om.lang;

import java.util.Hashtable;


public class Semantics {
	
	public Hashtable Slugs = new Hashtable();
	public Hashtable Names = new Hashtable();
	public Hashtable SearchFilters = new Hashtable();
	
	
	public Semantics() { }
	
	public void slug_emparenting(String slug, String title) {
    	if (Slugs.get(slug) == null) {
    		Slugs.put(slug, title);
    		Names.put(title, slug);
    	}
    }
    
    public String name_to_slug(String name) {
    	String rstr = name;
    	if (Names.get(name) != null) {
    		rstr = (String) Names.get(name);
    	}
    	rstr = edit_to_machine(rstr);
    	return rstr;
    }
    
    public String slug_to_name(String slug) {
    	String rstr = slug;
    	if (Slugs.get(slug) != null) {
    		rstr= (String) Slugs.get(slug);
    	}
    	rstr = edit_to_human(rstr);
    	return rstr;
    }
    
    
    public String edit_to_human(String slug) {
    	String rstr = slug;
    	if (!(rstr.equalsIgnoreCase(""))) {
	    	rstr = replaceAll(slug, "&", "; ");
	    	rstr = replaceAll(rstr, "=", ": ");
	    	rstr = replaceAll(rstr, "-", " ");
	    	String f = (rstr.substring(0,1)).toUpperCase();
	    	String e = rstr.substring(1);
	    	rstr = f+e;
    	}
    	return rstr;
    }
    
    public String edit_to_machine(String name) {
    	String rstr = name;
    	rstr = replaceAll(name, " ", "-");
    	rstr = replaceAll(rstr, "á", "a");
    	rstr = replaceAll(rstr, "é", "e");
    	rstr = replaceAll(rstr, "í", "i");
    	rstr = replaceAll(rstr, "ó", "o");
    	rstr = replaceAll(rstr, "ú", "u");
    	return rstr;
    }
    
    public String date_to_human(String date) {
    	String rstr = date;
    	rstr = replaceAll(rstr, "-", "/");
    	rstr = rstr.substring(0, rstr.length()-3);
    	rstr = rstr.substring(0, 10);
    	rstr = rstr.substring(8) + "/" + rstr.substring(5,7) + "/" + rstr.substring(0,4);
    	return rstr;
    }
    
    public String lenght_to_human(String lenght) {
    	String rstr = lenght;
    	rstr = rstr.substring(0, rstr.length()-3);
    	String hour = rstr.substring(0,2);
    	int ihour = Integer.parseInt(hour);
    	String min = rstr.substring(3);
    	int imin = Integer.parseInt(min);
    	if (imin>57) { min = ""; ihour+=1; hour = ihour + "";  imin = 0;}
    	if (imin<1) { min = ""; imin = 0;}
    	rstr = "";
    	if (ihour >0) { 
    		if (hour.equalsIgnoreCase("01")) { hour = "1"; }
    		rstr += hour + " hora";
    		if (imin>0) { rstr += " con " + min + " minutos"; }
		} else {
			if (imin>0) { rstr += min + " minutos"; }
		}
    	if (rstr=="") { rstr = "Menos de un minuto"; }
    	return rstr;
    }
    
    
    public String replaceAll(String source, String pattern, String replacement)	{    
	    if (source == null) {
	        return "";
	    }

	    StringBuffer sb = new StringBuffer();
	    int idx = -1;
	    String workingSource = source;
	     
	    while ((idx = workingSource.indexOf(pattern)) != -1) {
	        sb.append(workingSource.substring(0, idx));
	        sb.append(replacement);
	        sb.append(workingSource.substring(idx + pattern.length()));
	        workingSource = sb.toString();
	        sb.delete(0, sb.length());
	    }
	    return workingSource;
	}

}
