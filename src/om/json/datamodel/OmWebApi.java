package om.json.datamodel;

public class OmWebApi {
	
	String baseurl = "http://api-vtv.openmultimedia.biz";
	String clipurl = "";
	String defurl = "http://api-vtv.openmultimedia.biz/clip/?detalle=completo";
	String noteurl = "http://api-vtv.openmultimedia.biz/clip/?detalle=completo";
	
	public String getDefault() {
		return defurl;
	}
	
	public String getFiltered(String kind, String filter) {
		if (kind.equalsIgnoreCase("noticia")) { kind = "categoria"; }
		return defurl + "&" + kind + "=" + filter;
	}
	
	public String getMenu(String kind) {
		return baseurl+"/"+kind+"/";
	}
	
	
	public String getNote(String id) {
		return noteurl+id;
	}
	

}
