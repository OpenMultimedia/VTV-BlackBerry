package om.vtv;

import java.io.InputStream;

import om.json.datamodel.NewsObject;
import om.json.datamodel.OmDataModel;
import om.json.datamodel.OmVideoDataModel;
import om.json.datamodel.OmWebApi;
import om.json.datamodel.VideoObject;
import om.ui.Layout;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;

public class Portada extends VTV {
	
	boolean first = true;
	
	Portada(String content) {
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;
	
		La = new Layout(this);
		
		f_master = "categoria";
		Manager fm_MainHolder = La.construct_main();
		super.add(fm_MainHolder);
	    mastercontainer = fm_MainHolder;
	    La.construct_header(fm_MainHolder,true);
                
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonMenu(); 
			}
		});
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonContent(); 
			}
		});
        
	}
	
	Portada(String content, String filter) {
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;
		
		La = new Layout(this);
		
		f_master = "categoria";
        f_categoria = filter;
        Manager fm_MainHolder = La.construct_main();
		super.add(fm_MainHolder);
	    mastercontainer = fm_MainHolder;
	    La.construct_header(fm_MainHolder,true);

        UiApplication app = (UiApplication)Application.getApplication();
        if (app.getActiveScreen().getScreenBelow() != null) {
        	app.popScreen( app.getActiveScreen().getScreenBelow() );
        }
        
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonMenu(); 
			}
		});
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonContent(); 
			}
		});
        
        
	}
	
	Portada(String lang, String master, String categoria, String tipo, String sdefault) {
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;
		
		La = new Layout(this);
		
        content_lang = lang;
		f_master = master;
		f_categoria = categoria;
		f_tipo = tipo;
		f_default = sdefault;
		Manager fm_MainHolder = La.construct_main();
		super.add(fm_MainHolder);
	    mastercontainer = fm_MainHolder;
	    La.construct_header(fm_MainHolder,true);
        if (master.equalsIgnoreCase("programa")) {
        	
        } 
        
		UiApplication app = (UiApplication)Application.getApplication();
		if (app.getActiveScreen().getScreenBelow() != null) {
        	app.popScreen( app.getActiveScreen().getScreenBelow() );
        }
		
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonMenu(); 
			}
		});
		
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonContent(); 
			}
		});	
        
        
	}
	
	public void startJsonContent() {
		if (theApp.videos!=null) {
			videos = theApp.videos;
			inflateVideos(videos,f_master);
		} else {
			OmWebApi owa = new OmWebApi();
			String resurl = owa.getDefault();
			String[] params = {"videos"};
			getResource(resurl, params);
		}

	}
	
	public void startJsonMenu() {
		if (theApp.videos_menu!=null) {
			videos_menu = theApp.videos_menu;
			inflateVideosMenu(videos_menu,f_master);
		} else {
			OmWebApi owa = new OmWebApi();
			String resurl = owa.getMenu("programa");
			String[] params = {"menu_programa"};
			getResource(resurl, params);
		}

	}
	
	public void callback_error(int errorcode) {
		System.out.println("DOWNLOAD ERROR "+errorcode);
	}
	
	public void callback_downloaded(InputStream is, String[] content) {
		System.out.println("_____DOWNLOADED CALLBACK ID "+content[0]);
		if (content[0]=="videos") { all_videos(StreamToString(is)); }
		if (content[0]=="menu_programa") { menu_videos(StreamToString(is)); }
	}
	
	public void all_news(final String str) {
		System.out.println("DOWNLOADED "+str);
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				odm = new OmDataModel(str);
				news = odm.getItems();
				theApp.news = news;
				inflateNews(news, f_master);
			}
		});	
		

	}
	
	public void all_videos(final String str) {
		System.out.println("DOWNLOADED "+str);
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				ovm = new OmVideoDataModel(str);
				videos = ovm.getItems();
				theApp.videos = videos;
				inflateVideos(videos,f_master);
			}
		});	
		

	}
	
	public void menu_videos(final String str) {
		System.out.println("_____________DOWNLOADED MENU "+str);
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				menu_ovm = new OmVideoDataModel(str);
				videos_menu = menu_ovm.getItems();
				theApp.videos_menu = videos_menu;
				inflateVideosMenu(videos_menu,f_master);
			}
		});	
	}
	
	public void inflateNews(NewsObject[] mnews, String filter) {
		for (int i = 0; i < mnews.length; i++) {
			NewsObject nob = mnews[i];
			boolean first = (i==1);
			inflateNewsItem(nob, filter, first);	
		}
	}
	
	public void inflateNewsItem(final NewsObject no, String filter, boolean isfirst) {
		try {
			if(no.getString("family").equalsIgnoreCase(filter)&&no.getString("type").equalsIgnoreCase("noticia")){
				if (!first) {
					
				} else {

				}
				first=true;
			}
			System.out.print(no.getString("title"));
		} catch(Exception e) {  }
	}
	
	public void inflateVideos(VideoObject[] mvideos, String filter) {
		for (int i = 0; i < mvideos.length; i++) {
			VideoObject vob = mvideos[i];
			System.out.println("EACH VIDEO"+i);
			inflateVideoItem(vob, filter);	
		}
	}
	
	public void inflateVideoItem(final VideoObject vo, String filter) {
		if (first) {
			La.construct_main_video(
					mastercontainer, 
					vo.getString("thumbnail_grande"), 
					vo.getString("titulo"), 
					vo.getString("descripcion"), 
					vo.getString("programa_nombre"), 
					vo.getString("duración"), 
					vo.getString("fecha"), 
					vo.getString("slug")
				);	
		} else {
			La.construct_listed_video(
					mastercontainer, 
					vo.getString("thumbnail_mediano"), 
					vo.getString("titulo"), 
					vo.getString("descripcion"), 
					vo.getString("programa_nombre"), 
					vo.getString("duración"), 
					vo.getString("fecha"), 
					vo.getString("slug")
				);
		}
		//La.construct_makescrollable_attache(mastercontainer);
		first=false;
		System.out.println(vo.getString("titulo"));
	}
	
	
	public void inflateVideosMenu(VideoObject[] mvideos, String filter) {
		menucontainer = La.construct_hmenu_holder(mastercontainer);
		for (int i = 0; i < mvideos.length; i++) {
			VideoObject vob = mvideos[i];
			System.out.println("EACH MENU ITEM"+i);
			inflateVideosMenuItem(vob, filter);	
		}
	}
	
	public void inflateVideosMenuItem(final VideoObject vo, String filter) {
		La.construct_menu_item(menucontainer, 
				vo.getString("nombre"), 
				vo.getString("descripcion"), 
				vo.getString("imagen_url"), 
				vo.getString("slug"),
				false, 
				vo.getString("slug")
			);		
		System.out.println("MENU"+vo.getString("slug"));	
	}

	
}
