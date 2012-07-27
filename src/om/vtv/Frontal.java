package om.vtv;



import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import java.lang.String;

import om.vtv.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import net.rim.device.api.ui.component.Dialog;



public final class Frontal extends MainScreen {
	
	
	boolean downloadsuccessful = false;
	private static String _dataFileName = "/docs/all.json";
	private static long StoreTelesur = 0x2e897b0bbd4fbd6fL; //TELESURP
	
	public Frontal() {
		super(Manager.NO_VERTICAL_SCROLL | Manager.NO_HORIZONTAL_SCROLL);
		
		
		//MAIN HOLDER
		VerticalFieldManager vMainHolder = new VerticalFieldManager(Manager.USE_ALL_WIDTH | Manager.USE_ALL_HEIGHT | Manager.FIELD_VCENTER);
		Background back = BackgroundFactory.createBitmapBackground(Bitmap.getBitmapResource("img/fondo1.jpg"));
		vMainHolder.setBackground(back);
		add(vMainHolder);
		
		//MAIN LOGO
		Bitmap bLogo = Bitmap.getBitmapResource("img/logo.png");
        BitmapField fLogo = new BitmapField(bLogo, Manager.FIELD_VCENTER | Manager.FIELD_HCENTER);
        fLogo.setPadding(0,0,0,0);
        fLogo.setMargin(25,25,25,25);
        vMainHolder.add(fLogo);
        
               
        HorizontalFieldManager h_Menu = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        vMainHolder.add(h_Menu);
		
	        
	        LabelField l_Entrar = new LabelField("Cargando", LabelField.FIELD_RIGHT | Field.FOCUSABLE) {
	            protected boolean navigationClick(int status,int time)
	            {
	            	//if (downloadsuccessful) {
	            	Screen screen = new Portada("categoria");    
	            	UiApplication.getUiApplication().pushScreen(screen);
	            	//}
	            	return true;
	            }
	            public void paint(Graphics g){      
	                g.setColor(Color.BLACK);
	                g.clear();
	                super.paint(g);
	           }   
	   
	        };
	        h_Menu.add(l_Entrar);
	        
        
        
                
        //DATA MANAGER
        
        //DataManager dm = new DataManager(StoreTelesur);
        //if (dm.DownloadnStore(_dataFileName)) {
       // 	l_Entrar.setText("Información descargada");
        	downloadsuccessful = true;
        //} else {
        //	l_Entrar.setText("Error al descargar la infromación");
        	downloadsuccessful = true;
        //}
        
	}
	
	

	

	
	
	
}

