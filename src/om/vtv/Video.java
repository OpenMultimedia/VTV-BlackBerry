package om.vtv;

import om.json.datamodel.VideoObject;
import om.ui.Layout;

import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;

public class Video extends VTV {
	
	private boolean startplaying = false;
	private String slug = "";
	
	public Video(String slug, boolean play) {
		this.slug=slug;
		this.startplaying = play;
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;
	
		La = new Layout(this);
		
		Manager fm_MainHolder = La.construct_main();
		super.add(fm_MainHolder);
	    mastercontainer = fm_MainHolder;
        La.construct_header(fm_MainHolder, false);
        
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
	        	startJsonContent(); 
			}
		});	
	}
	
	public void startJsonContent() {
		if (theApp.videos!=null) {
			videos = theApp.videos;
			inflateSelVideo(videos, this.slug, this.startplaying);
		} else {
			this.close();
		}

	}
	
	public void inflateSelVideo(VideoObject[] mvideos, String slug, boolean startplaying) {
		for (int i = 0; i < mvideos.length; i++) {
			VideoObject vob = mvideos[i];
			if (vob.getString("slug").equalsIgnoreCase(slug)) {
				inflateVideoItem(vob, startplaying);
				return;
			}
		}
	}
	
	public void inflateVideoItem(final VideoObject vo, boolean startplaying) {
		this_video_url = vo.getString("archivo_url");
		this_video_slug = vo.getString("slug");
		System.out.println("THIS VIDEO______>"+this_video_slug+ ": "+ this_video_url);
		La.construct_video_details(mastercontainer, 
				vo.getString("archivo_url"), 
				vo.getString("thumbnail_grande"), 
				vo.getString("titulo"), 
				vo.getString("descripcion"),  
				"seccion", 
				vo.getString("duraci�n"), 
				vo.getString("fecha")
			);
		La.construct_video_extended_info(mastercontainer, 
				vo.getString("archivo_url"), 
				vo.getString("tema_slug"), 
				vo.getString("categoria"), 
				vo.getString("slug"), 
				vo.getString("pais"), 
				vo.getString("tags")
			);
		System.out.println(vo.getString("titulo"));
	}
	
	
	

	
}