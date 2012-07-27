package om.vtv;

import om.json.datamodel.NewsObject;
import om.json.datamodel.VideoObject;
import om.lang.Semantics;
import net.rim.device.api.ui.UiApplication;


public class Aplicacion extends UiApplication
{

	public  NewsObject[] news;
	public  VideoObject[] videos;
	public  VideoObject[] videos_menu;
	public Semantics s;
	
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        Aplicacion theApp = new Aplicacion();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new Aplicacion object
     */
    public Aplicacion()
    {        
    	s = new Semantics();
        pushScreen(new Portada("categoria"));
    }    
}
