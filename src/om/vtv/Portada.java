package om.vtv;

import java.io.InputStream;

import om.json.datamodel.NewsObject;
import om.json.datamodel.OmDataModel;
import om.json.datamodel.OmVideoDataModel;
import om.json.datamodel.OmWebApi;
import om.json.datamodel.VideoObject;
import om.ui.Layout;
import net.rim.device.api.i18n.Locale;
import net.rim.device.api.system.Application;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;

public class Portada extends VTV {
	
	boolean first = true;
	String content = "noticia";
	String filter = "";
	
	public Portada(final String content) {
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;
		La = new Layout(this);
		Locale.setDefault(Locale.get(Locale.LOCALE_es_MX, null));  
		
		this.content = content;
		
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
	
	public Portada(final String content, final String filter) {
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;
		
		La = new Layout(this);
		
		this.content = content;
		this.filter = filter;
		
		System.out.println("THIS_CONTENT___>"+content+"___FILTER___"+filter);
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
	        	startJsonContent(content, filter); 
			}
		});
        
        
	}
	
	public void startJsonContent() {
		OmWebApi owa = new OmWebApi();
		String resurl = owa.getDefault();
		String[] params = {"videos"};
		getResource(resurl, params);
	}
	
	public void startJsonContent(String kind, String filter) {
		OmWebApi owa = new OmWebApi();
		String resurl = owa.getFiltered(kind, filter);
		String[] params = {"videos"};
		getResource(resurl, params);
	}
	
	public void startJsonMenu() {
		menucontainer = La.construct_hmenu_holder(mastercontainer);
		OmWebApi owa = new OmWebApi();
		
		if (this.content.equalsIgnoreCase("noticia")) {
			String resurl = owa.getMenu("categoria");
			String[] params = {"menu_programa"};
			getResource(resurl, params);
		}
		if (this.content.equalsIgnoreCase("programa")) {
			String resurl = owa.getMenu("programa");
			String[] params = {"menu_programa"};
			getResource(resurl, params);
		}

	}
	
	public void callback_error(int errorcode) {
		System.out.println("DOWNLOAD ERROR "+errorcode);
	}
	
	public void callback_downloaded(InputStream is, String[] content) {
		//System.out.println("_____DOWNLOADED CALLBACK ID "+content[0]);
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
				inflateNews(news, filter);
			}
		});	
		

	}
	
	public void all_videos(final String str) {
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				ovm = new OmVideoDataModel(str);
				videos = ovm.getItems();
				theApp.videos = videos;
				inflateVideos(videos,filter);
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
				inflateVideosMenu(videos_menu,filter);
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
			inflateVideoItem(vob, filter);	
		}
	}
	
	public void inflateVideoItem(final VideoObject vo, String filter) {
		String vsfilter = "tipo_slug";
		String vsslug = "programa_slug";
		String vsnombre = "programa_nombre";
		
		if (this.content.equalsIgnoreCase("noticia")) {
			vsfilter = "categoria_slug";
			vsslug = "categoria_slug";
			vsnombre = "categoria_nombre";
		} 
		
		if (this.content.equalsIgnoreCase("programa")) {
			vsfilter = "programa_slug";
			vsslug = "programa_slug";
			vsnombre = "programa_nombre";
		}
		
		System.out.println("THIS_FILTER___>"+filter+"___VS___"+vsfilter);
        
		 
		if (vo.getString("tipo_slug").equalsIgnoreCase(this.content)) {
			String datet = DateParseTransform(vo.getString("fecha"));
			String durt = TimeParseTransform(vo.getString("duracion"));
			if (first) {
				La.construct_main_video(
						mastercontainer, 
						vo.getString("thumbnail_grande"), 
						vo.getString("titulo"), 
						vo.getString("descripcion"), 
						vo.getString(vsnombre), 
						durt, 
						datet, 
						vo.getString("slug")
					);	
			} else {
				La.construct_listed_video(
						mastercontainer, 
						vo.getString("thumbnail_mediano"), 
						vo.getString("titulo"), 
						vo.getString("descripcion"), 
						vo.getString(vsnombre), 
						durt, 
						datet, 
						vo.getString("slug")
					);
			}
			first=false;
		}
		//System.out.println(vo.getString("titulo"));
	}
	
	
	public void inflateVideosMenu(VideoObject[] mvideos, String filter) {
		for (int i = 0; i < mvideos.length; i++) {
			VideoObject vob = mvideos[i];
			System.out.println("EACH MENU ITEM"+i);
			inflateVideosMenuItem(vob, filter);	
		}
	}
	
	public void inflateVideosMenuItem(final VideoObject vo, String filter) {
		boolean focus = false;
		if (this.filter.equalsIgnoreCase(vo.getString("slug"))){focus = true;}
		if (this.content.equalsIgnoreCase("noticia")) {
			La.construct_text_menu_item(menucontainer, 
					vo.getString("nombre"), 
					vo.getString("descripcion"), 
					vo.getString("imagen_url"), 
					focus, 
					this.content,
					vo.getString("slug")
					
				);	
		} else {
			La.construct_image_menu_item(menucontainer, 
					vo.getString("nombre"), 
					vo.getString("descripcion"), 
					vo.getString("imagen_url"), 
					focus, 
					this.content,
					vo.getString("slug")
				);	
		}
	
		System.out.println("MENU"+vo.getString("slug"));	
	}

	
}
