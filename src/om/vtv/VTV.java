package om.vtv;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.microedition.content.ContentHandler;
import javax.microedition.content.Invocation;
import javax.microedition.content.Registry;
import javax.microedition.io.HttpConnection;
import om.json.datamodel.NewsObject;
import om.json.datamodel.OmDataModel;
import om.json.datamodel.OmVideoDataModel;
import om.json.datamodel.OmWebApi;
import om.json.datamodel.VideoObject;
import om.ui.Layout;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.blackberry.api.browser.Browser;
import net.rim.device.api.content.BlackBerryContentHandler;
import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.FieldPosition;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.http.HttpDateParser;
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
			invoke_sharing(param, specific);
		}
		
		if (action.equalsIgnoreCase("video_menu_click")) {
        	Screen screen = new Portada(specific, param);    
        	UiApplication.getUiApplication().pushScreen(screen);
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
        	Screen screen = new Portada("noticia");    
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
        	Screen screen = new Portada("programa");    
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
		   Image in = ImageFactory.createImage(Bitmap.getBitmapResource("img/noticias.png"));
		   Image ip = ImageFactory.createImage(Bitmap.getBitmapResource("img/programas.png"));
		   Image iv = ImageFactory.createImage(Bitmap.getBitmapResource("img/live.png"));
		   Image ic = ImageFactory.createImage(Bitmap.getBitmapResource("img/btn_del.png"));
		   m_news.setIcon(in);
		   m_prog.setIcon(ip);
		   m_live.setIcon(iv);
		   m_end.setIcon(ic);
		   super.makeMenu(menu, instance);
		   menu.add(m_news);
		   menu.add(m_prog);
		   menu.add(m_live);
		   menu.add(m_end);
	}
	
	
    
   
    public void load_more_vids(Manager parent) {
    	
    }
    	
		 
	public void invoke_video_list(String lang, String master, String categoria, String tipo, String sdefault) {
		Screen screen = new Portada(tipo);    
    	UiApplication.getUiApplication().pushScreen(screen);  
    }
	
	public void invoke_sharing(String vurl, String method) {
		Screen screen = new Share(method,vurl);    
    	UiApplication.getUiApplication().pushScreen(screen); 
	    	
    }
    
    public void invoke_live() {
    	OmWebApi owa = new OmWebApi();
    	String url = owa.getStreaming();
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
			
	public String StreamToString(InputStream is) {
		
		if (is != null) {
			try {
				String str = new String(IOUtilities.streamToBytes(is), "UTF-8");
				return str;
			} catch (Exception e) {
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
	
	public String TimeParseTransform(String time) {
		String r = time;
		if (r.length()>5){
			//String hh = r.substring(0,2);
			//String mm = r.substring(1,2);
			//int h = Integer.parseInt(hh);
			//int m = Integer.parseInt(mm);
			//r = "";
			//if (m>55) { h++; m = 0; }
			//if (h>1) { r+= h + " horas "; } else if (h>0) { r+= "Una hora "; }
			//if (m>5) { r+= m + " minutos"; }
		}
        return "";   
	}
	
	
	public String DateParseTransform(String date) {
		String r = "";
		Date currentDate = new Date();
        Date sentdate = new Date(HttpDateParser.parse(date));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy");
        //return sdf.formatLocal();
        return sdf.format(sentdate);       
	}
	



	
}
