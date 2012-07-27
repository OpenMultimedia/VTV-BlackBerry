package om.vtv;

import om.ui.Layout;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.decor.BackgroundFactory;



public class Nav extends VTV {
	

	public Nav(String path) {
		La = new Layout(this);
		Manager fm_MainHolder = La.construct_main();
		fm_MainHolder.setBackground(BackgroundFactory.createSolidBackground(Color.BLACK));
		La.construct_micro_header(fm_MainHolder);
        La.construct_navigator(fm_MainHolder,path);		
	}
	




}
