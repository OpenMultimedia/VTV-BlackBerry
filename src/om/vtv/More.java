package om.vtv;

import om.ui.Layout;

import net.rim.device.api.ui.Manager;

public class More extends VTV {
	
	More() {
		La = new Layout(this);
		Manager fm_MainHolder = La.construct_main();
		super.add(fm_MainHolder);
	    mastercontainer = fm_MainHolder;
        La.construct_header(fm_MainHolder, false);
        //InterfaceBuilder ti = new InterfaceBuilder(this, false);
        //ti.construct_more(fm_MainHolder);
        
	}
	

	
}
