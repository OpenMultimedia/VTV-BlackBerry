package om.data;


public class API {
	
	String tlkey = "Md1a49pW82";
	String f_lang;
	String f_master ="categorias";
	String f_categoria ;
	String f_tipo;
	String lod = "normal";
	int init = 0;
	int fin = 0 ;
	
	public API(String lang) {
		f_lang = lang;
	}
	
	public API(String lang, String master) {
		f_lang = lang;
		f_master = master;
	}
	
	public API(String lang, String master, String detalle, int vinit, int vfin) {
		f_lang = lang;
		f_master = master;
		init = vinit;
		fin = vfin;
		lod = "completo";
	}
	
	public String GetStreaming() {
		return "rtmp://edg.ord.movipbox2.streamguys.net/vtvdsl/vtvdsl.sdp";
	}
	
	public String GetVidAddress(String url) {
		return (url);
	}
	
	public String GetMenuAddress(String menu) {
		String xurl ="";
		String base_url= "http://multimedia.tlsur.net/";
		String base_api= "api/";
		String base_lang = "";
		String apikey = "key=" + tlkey;
		if (!(f_lang.equalsIgnoreCase("es")) && !(f_lang.equalsIgnoreCase(""))) { base_lang = f_lang + "/"; }
		if (menu.equalsIgnoreCase("")) { menu = "categoria"; }
		if (menu.equalsIgnoreCase("entrevista")) { menu = "categoria"; }
		xurl = base_url+ base_lang + base_api + menu + "/?" + apikey;
		return xurl;
	}
	
	
	public String GetFileAddress(String categoria, String tipo, String sdefault) {

		String xurl ="";
		String base_url= "http://multimedia.tlsur.net/";
		String base_api= "api/";
		String apikey = "&key=" + tlkey;
		String base_lang = "";
		String filter ="?";
		String vinit = "";
		String vfin = "";
		String acategoria ="";
		String atipo ="";
		String adefault ="";
		
		if (!(f_lang.equalsIgnoreCase("es")) && !(f_lang.equalsIgnoreCase(""))) { base_lang = f_lang + "/"; }
		if (!(categoria.equalsIgnoreCase(""))) { acategoria = "categoria=" + categoria + "&"; }
		if (!(tipo.equalsIgnoreCase(""))) {  atipo = "tipo=" + tipo + "&"; } else { atipo = "tipo=noticia&"; }
		if (!(sdefault.equalsIgnoreCase(""))) {  adefault = sdefault + "&"; }
		filter += acategoria + atipo + adefault;
		

		if (init != 0) {
			vinit = "primero=" + init;
		}
		
		if (fin != 0) {
			vfin = "&ultimo=" + fin;
		}

		String detalle = "&detalle=" + lod; 
		
		xurl= base_url + base_lang + base_api + "clip/";
		xurl = xurl + filter + vinit+ vfin + detalle + apikey;
		
		if (categoria.equalsIgnoreCase("populares")) {
			xurl = "http://multimedia.tlsur.net/api/clip/?tiempo=semana&orden=popularidad";
		}
		return xurl;

	}
	
	
	
	public String GetImageAddress(String tipo, String src) {
	    
		String xurl ="";
		String base_url= "http://multimedia.tlsur.net/";
		String base_img= "clips/";
		
		xurl = base_url + base_img + src;
		
		return xurl;
	}

}