package om.vtv;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.microedition.content.ContentHandler;
import javax.microedition.content.Invocation;
import javax.microedition.content.Registry;
import javax.microedition.io.HttpConnection;
import om.data.API;
import om.json.datamodel.NewsObject;
import om.json.datamodel.OmDataModel;
import om.json.datamodel.OmVideoDataModel;
import om.json.datamodel.VideoObject;
import om.ui.Layout;

import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.blackberry.api.browser.Browser;
import net.rim.device.api.content.BlackBerryContentHandler;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.image.ImageFactory;
import net.rim.device.api.ui.image.Image;


import cam.aedes.CMainScreen;
import cam.async.AsyncDownloader;



public class VTV extends CMainScreen{
	
 	protected NewsObject[] news;
 	protected VideoObject[] videos;
 	protected VideoObject[] videos_menu;
 	protected OmDataModel odm;
 	protected OmVideoDataModel ovm;
 	protected OmVideoDataModel menu_ovm;
 	
	Layout La;
	public Aplicacion theApp;
	

	public String maincontent;
	public String content_lang = "es";
	public String interface_lang = "es";
	public String this_video_slug = "";
	public String this_video_url = "";
	public String this_video_category;
	public String main_former_section = "";
	public String secondary_former_section = "";
	public String activity_sytle_kind = "";
	public String search_str = "";
	public String f_ilang = "";
	public String f_lang = "";
	public String f_master ="";
	public String f_categoria = "";
	public String f_tipo = "";
	public String f_default = "";
	boolean is_long_video = false;
	boolean on_mobile_network = false;
	public boolean im_secondary_list = false;
	public boolean error_collapse = false;
	int video_curr_paging = 1;
	public int video_page_increment = 6;

	int main_image_size = 110;
	int small_image_size = 80;
	Manager mastercontainer;
	Manager menucontainer;
	Manager filter_manager;
	public Hashtable Slugs = new Hashtable();
	public Hashtable Names = new Hashtable();
	public Hashtable SearchFilters = new Hashtable();
	
	
	public void getResource(String res, String[] content) {

		try {
			System.out.println("IS DOWNLODING res "+res);
			UiApplication.getUiApplication().invokeLater(new AsyncDownloader(this,res, content));
		} catch (Exception e){

		}
	}
	
	
	public void callback_error(int code) {
		
	}
	
	public void callback_downloaded(InputStream is) {
		
	}
	
	public void action_callback(String action, String specific, String param) {
		if (action.equalsIgnoreCase("share")) {
			invoke_sharing(specific, param);
		}
		
		if (action.equalsIgnoreCase("video_menu_click")) {
			//invoke_sharing(specific, param);
		}
		
		if (action.equalsIgnoreCase("menu_launch")) {
			launchMenu();	
		}
		
		if (action.equalsIgnoreCase("play_video")) {
			play_video();
		}
		
	}
	
	private MenuItem m_news=new MenuItem("Noticias",1,10)
    {
        public void run() {     
            UiApplication app = (UiApplication)Application.getApplication();
            if (app.getActiveScreen().getScreenBelow() != null) {
            	app.popScreen( app.getActiveScreen().getScreenBelow() );
            }
        	Screen screen = new Portada("categoria");    
        	UiApplication.getUiApplication().pushScreen(screen);                  
        }    
    };	
	private MenuItem m_prog=new MenuItem("Programas",2,10)
    {
        public void run() {
            UiApplication app = (UiApplication)Application.getApplication();
            if (app.getActiveScreen().getScreenBelow() != null) {
            	app.popScreen( app.getActiveScreen().getScreenBelow() );
            }
        	Screen screen = new Portada("", "programa", "", "programa", "");    
        	UiApplication.getUiApplication().pushScreen(screen);  
        }    
    };	
	private MenuItem m_inter=new MenuItem("Entrevistas",3,10)
    {
        public void run() { 
            UiApplication app = (UiApplication)Application.getApplication();
            if (app.getActiveScreen().getScreenBelow() != null) {
            	app.popScreen( app.getActiveScreen().getScreenBelow() );
            }
        	Screen screen = new Portada("", "entrevista", "", "entrevista", "");    
        	UiApplication.getUiApplication().pushScreen(screen);  
        }    
    };	
	private MenuItem m_search=new MenuItem("Búsqueda",4,10)
    {
        public void run() { 
            UiApplication app = (UiApplication)Application.getApplication();
            if (app.getActiveScreen().getScreenBelow() != null) {
            	app.popScreen( app.getActiveScreen().getScreenBelow() );
            }
        	Screen screen = new Busqueda();    
        	UiApplication.getUiApplication().pushScreen(screen);  
        }    
    };	
	private MenuItem m_more=new MenuItem("Más",5,10)
    {
        public void run() { 
            UiApplication app = (UiApplication)Application.getApplication();
            if (app.getActiveScreen().getScreenBelow() != null) {
            	app.popScreen( app.getActiveScreen().getScreenBelow() );
            }
        	Screen screen = new More();    
        	UiApplication.getUiApplication().pushScreen(screen);  
        }    
    };	
    
    private MenuItem m_live=new MenuItem("En Vivo",5,10)
    {
        public void run() { 
        	invoke_live();
        }    
    };	
    
    private MenuItem m_end=new MenuItem("Cerrar Aplicación",5,10)
    {
        public void run() { 
        	System.exit(0);
        }    
    };	
    
	public void collapse_error(VTV t, Manager parent, boolean show_error) {
		if (show_error) {
			error_collapse = true;
			//tsi.construct_error_box(t, parent);
		}
	}
	
	public void launchMenu() {
		this.getScreen().getMenu(0).show();		
	}
	
	public void makeMenu(Menu menu, int instance) {
		   //Image in = ImageFactory.createImage(Bitmap.getBitmapResource("img/noticias.png"));
		   Image ip = ImageFactory.createImage(Bitmap.getBitmapResource("img/programas.png"));
		   //Image ie = ImageFactory.createImage(Bitmap.getBitmapResource("img/especiales.png"));
		   //Image ib = ImageFactory.createImage(Bitmap.getBitmapResource("img/buscar.png"));
		   //Image im = ImageFactory.createImage(Bitmap.getBitmapResource("img/mas.png"));
		   Image iv = ImageFactory.createImage(Bitmap.getBitmapResource("img/live.png"));
		   Image ic = ImageFactory.createImage(Bitmap.getBitmapResource("img/btn_del.png"));
		   //m_news.setIcon(in);
		   m_prog.setIcon(ip);
		   //m_inter.setIcon(ie);
		   //m_search.setIcon(ib);
		   //m_more.setIcon(im);
		   m_live.setIcon(iv);
		   m_end.setIcon(ic);
		   super.makeMenu(menu, instance);
		   //add these items to the full menu 
		   if (instance == Menu.INSTANCE_DEFAULT) {
		      //menu.add(_newMenuItem);
		      //menu.add(_optionsMenuItem);
		   }
		   //menu.add(m_news);
		   menu.add(m_prog);
		   //menu.add(m_inter);
		   //menu.add(m_search);
		   menu.add(m_live);
		   //menu.add(m_more);
		   menu.add(m_end);
	}
	
	
    
    public void self_reload() {
    	invoke_video_list(f_lang, f_master, f_categoria, f_tipo, f_default);
    }
    
    public void load_more_vids(Manager parent) {
    	//construct_json_res(parent, f_lang, f_master, f_categoria, f_tipo, f_default);
    }
    	
	public void invoke_search_list(String maintxt) {
    	String superstring ="";
    	if (!(maintxt.equalsIgnoreCase(""))) {
    		superstring = "texto=" + maintxt + "&";
    	}
    	
    	String n_default = "";
    	Enumeration keys = SearchFilters.keys();
    	String key, value;
    	while(keys.hasMoreElements()) {
    	    key = (String) keys.nextElement();
    	    value = (String) SearchFilters.get(key);
    	    value = theApp.s.name_to_slug(value);
    	    n_default = key + "=" + value + "&";
    	    
    	}
    	if (n_default!="") {
    		superstring +=  n_default;
    	}
    	
    	if (superstring.length()>2) {
	    	if (superstring.substring(superstring.length()-1).equalsIgnoreCase("&")) {
	    		superstring = superstring.substring(0, superstring.length()-1);
	    	}
    	}
    	invoke_video_list(f_lang, "", f_categoria,"",superstring);
    }
	
	public Manager invoke_hold_flag(final Manager parent, String lang) { 
		//InterfaceBuilder tsi = new InterfaceBuilder(lang, "", "", "", "", false);
		//return tsi.construct_hold_manager(parent);
		return null;
	}
	
	public void destruct_filter_appendix(VTV t, String key, String val) {
			if (t.f_categoria.equalsIgnoreCase(val)) { t.f_categoria = ""; } else {  theApp.s.SearchFilters.remove(val); }
	}
		 
	public void invoke_video_list(String lang, String master, String categoria, String tipo, String sdefault) {
		Screen screen = new Portada(lang, master, categoria, tipo, sdefault);    
    	UiApplication.getUiApplication().pushScreen(screen);  
    }
	
	public void invoke_sharing(String vurl, String method) {
    	API tswa = new API(f_lang);
    	vurl = tswa.GetVidAddress(vurl);
		Screen screen = new Share(method,vurl);    
    	UiApplication.getUiApplication().pushScreen(screen); 
	    	
    }
    
    public void invoke_live() {
    	API twa = new API(f_lang);
    	String url = twa.GetStreaming();
    	Browser.getDefaultSession().displayPage(url);
    }
    	
    public void invoke_video_play_2(final Manager parent, String url, boolean play_first) {
    	try {
    	    Invocation invocation = new Invocation(url, "video/mp4", BlackBerryContentHandler.ID_MEDIA_CONTENT_HANDLER, true, ContentHandler.ACTION_OPEN);
    	    Registry _registry=Registry.getRegistry("telesur.Telesur");
    	    _registry.invoke(invocation);
    	} catch (IOException e) 
    	{     }
    }
    
    public void invoke_video_play_3(final Manager parent, String url, boolean playfirst) {
    	String filePathTemp = url;
    	Invocation invocation = new Invocation(); 
    	Registry _registry=Registry.getRegistry("net.rim.device.api.content.BlackBerryContentHandler"); 
    	HttpConnection connection = null;
	    InputStream inputStream = null;
				
    	try { 
	
    		ConnectionFactory cf = new ConnectionFactory();
			cf.setConnectionTimeout(4333);
			ConnectionDescriptor cd = cf.getConnection(url);
			HttpConnection httpConn;
            httpConn = (HttpConnection)cd.getConnection();
            inputStream = httpConn.openInputStream();    		
            
            StringBuffer rawResponse = new StringBuffer();
            byte[] responseData = rawResponse.toString().getBytes(); 
            int length = 0;
            while (-1 != (length = inputStream.read(responseData))) {
                rawResponse.append(new String(responseData, 0, length));
            }
            String result = rawResponse.toString();
            byte[] dataArray = result.getBytes(); 
            if (dataArray.length > 0) { invocation.setData(dataArray); } 
            httpConn.close();
		} catch(Exception e1){ 
			DebugLabel(e1.toString(), parent); 
		} 


    	try { 
    		_registry.invoke(invocation); 
    	} catch(Exception e){
    		DebugLabel(e.toString(), parent); 
		}
    }
    
    public void invoke_video_play_4(final Manager parent, String url, boolean playfirst) {
    	Browser.getDefaultSession().displayPage(url);
    }
    
    public void play_video() {
    	UiApplication.getUiApplication().invokeAndWait( 
				new Runnable()  {   
					public void run ()    { 
						invoke_video_play_4(mastercontainer,this_video_url, true);
					}        
			 } );
    }
	
	public void Initialize_Interface(){ 

	}
		
	public void InitializeFilters(Manager parent) {
		
		if (f_tipo.equalsIgnoreCase("programa")) { return ; }
    	    	
    	if (f_master.equalsIgnoreCase("entrevista")) {
    			
    	}
        	
    	if (!(f_categoria.equalsIgnoreCase(""))) {
    		//InterfaceBuilder tsi = new InterfaceBuilder(this, false);
    		//tsi.construct_filter_flag(parent,f_categoria, "categoria");
    	}
    	
    	if (!(f_tipo.equalsIgnoreCase(""))) {
    		//InterfaceBuilder tsi = new InterfaceBuilder(this, false);
    		//tsi.construct_filter_flag(parent,f_tipo, "tipo");
    	}
    	
    	if (!(f_default.equalsIgnoreCase(""))) {
    		//InterfaceBuilder tsi = new InterfaceBuilder(this, false);
    		//tsi.construct_filter_flag(parent,f_default, "busqueda");
    	}
    	
    }
	
	public String StreamToString(InputStream is) {
		
		if (is != null) {
			try {
				String str = new String(IOUtilities.streamToBytes(is), "UTF-8");
				return str;
			} catch (UnsupportedEncodingException e) {
				return "unsup encod";
			} catch (IOException e) {
				return "";
			}
		  
		}  else { 
			return "";
		}
	}

	public LabelField DebugLabel(String text, Manager gparent) {
		LabelField l_O = new LabelField(text, Field.FOCUSABLE);
		gparent.add(l_O);
		return l_O;
	}
	



	
}
