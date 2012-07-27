package om.vtv;

import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;
import om.ui.Layout;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;

public class VPlayer extends VTV {
	

	Player player;
	VideoControl vc;
	
	VPlayer(final String url, final boolean play) {
		La = new Layout(this);
		final Manager fm_MainHolder = La.construct_main_vid();
		super.add(fm_MainHolder);
		UiApplication.getUiApplication().invokeLater( 
				new Runnable()  {   
					public void run ()    { 
						invoke_video_play_4(fm_MainHolder,url, play);
					}        
			 } );	
		final Player player;
        VideoControl videoControl;
        Field videoField;
   	 
	}
	
	
	

	
}
