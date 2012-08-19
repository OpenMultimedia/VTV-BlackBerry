package om.ui;


import javax.microedition.lcdui.Font;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldConfig;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.FocusChangeListener;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import om.vtv.Aplicacion;
import om.vtv.Portada;
import om.vtv.Video;
import cam.aedes.CInterface;
import cam.aedes.CMainScreen;
import cam.async.AsyncDownloadImage;

public class Layout extends UserInterface {
	
	Aplicacion theApp;
	
	FontFamily fontFamily[] = FontFamily.getFontFamilies();
	net.rim.device.api.ui.Font link1 = fontFamily[1].getFont(FontFamily.CBTF_FONT, 8, Ui.UNITS_pt);
	net.rim.device.api.ui.Font link2 = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 9, Ui.UNITS_pt);
	net.rim.device.api.ui.Font small = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 7, Ui.UNITS_pt).derive(Font.STYLE_PLAIN);
	net.rim.device.api.ui.Font small2 = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 7, Ui.UNITS_pt).derive(Font.STYLE_ITALIC);
	net.rim.device.api.ui.Font h4 = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 8, Ui.UNITS_pt).derive(Font.STYLE_PLAIN);
	net.rim.device.api.ui.Font h3 = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 9, Ui.UNITS_pt);
	net.rim.device.api.ui.Font h2 = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 10, Ui.UNITS_pt);
	net.rim.device.api.ui.Font h1 = fontFamily[1].getFont(FontFamily.SCALABLE_FONT, 11, Ui.UNITS_pt);
	XYEdges xye = new XYEdges(2,2,2,2);
	
	Background bplain = BackgroundFactory.createSolidTransparentBackground(Color.BLACK, 72);
	Background bfocus = BackgroundFactory.createSolidTransparentBackground(Color.BLACK, 95);
	
	Background mainbkg = BackgroundFactory.createBitmapBackground(Bitmap.getBitmapResource("img/fondo1.jpg"));
	Background b = BackgroundFactory.createSolidTransparentBackground(Color.GRAY, 24);
	Background b0 = BackgroundFactory.createSolidTransparentBackground(Color.GRAY, 36);
	Border bb = BorderFactory.createRoundedBorder(xye, Color.DARKGRAY, Border.STYLE_SOLID);
	
	Background ba = BackgroundFactory.createSolidTransparentBackground(Color.GRAY, 64);
	Background b0a = BackgroundFactory.createSolidTransparentBackground(Color.DARKRED, 80);
	Border bba = BorderFactory.createRoundedBorder(xye, Color.WHITE, Border.STYLE_SOLID);
	
	final int color_grayest = 2171169;
	final int color_vtv = 12400697;

	public Layout(CMainScreen c) {
		super(c);
		UiApplication ui = UiApplication.getUiApplication();
		theApp = (Aplicacion) ui;		
	}
	
	public Manager construct_main() {
		VerticalFieldManager fm_MainHolder = new VerticalFieldManager(Manager.USE_ALL_WIDTH | Manager.USE_ALL_HEIGHT | Manager.VERTICAL_SCROLL);
		Background back = mainbkg;
		fm_MainHolder.setBackground(back);
		main_image_resize();
		return fm_MainHolder;
	}
	
	public Manager construct_header(Manager parent, boolean reload) {
		CInterface ci = new CInterface();
		HorizontalFieldManager fm_Header = new HorizontalFieldManager(Manager.FIELD_VCENTER | Manager.FIELD_LEFT| Manager.FIELD_BOTTOM | Manager.USE_ALL_WIDTH);
		Background back_Header = BackgroundFactory.createSolidBackground(color_vtv);
		fm_Header.setBackground(back_Header);
		parent.add(fm_Header);
				
		final int columnWidth1 = (50);
		
		final int columnWidth0 = (Display.getWidth()-columnWidth1);
		
		String logo = "img/logohead.png";
		if (Display.getWidth()<360) {
			logo = "img/logohead_sm.png";
		}
			
		//MAIN LOGO
		HorizontalFieldManager lgo = new HorizontalFieldManager(Manager.NO_VERTICAL_SCROLL | Manager.NO_HORIZONTAL_SCROLL | Manager.FIELD_HCENTER) {
			public int getPreferredWidth() {       
			       return columnWidth0; // you should return your calculated max width
			   }
			   protected void sublayout(int width, int height) {
			       width = getPreferredWidth();
			       height = super.getPreferredHeight();
			       super.sublayout(width, height);
			       super.setExtent(width, height);
			   }
		};
			BitmapField img = ci.WriteSimpleImage(lgo, logo, BitmapField.FIELD_VCENTER | BitmapField.FIELD_HCENTER, 0, "");
			img.setMargin(2,2,2,2);
	        fm_Header.add(lgo);
	        
       //MORE
		VerticalFieldManager hlp = new VerticalFieldManager(Manager.FIELD_VCENTER | Manager.FIELD_HCENTER | Manager.USE_ALL_HEIGHT) {
			public int getPreferredWidth() {       
			       return columnWidth1; 
			   }
			   protected void sublayout(int width, int height) {
			       width = getPreferredWidth();
			       height = super.getPreferredHeight();
			       super.sublayout(width, height);
			       super.setExtent(width, height);
			   }
		};
		
			final Bitmap bLogo0 = Bitmap.getBitmapResource("img/btn_plus.png");
			final Bitmap bLogo1 = Bitmap.getBitmapResource("img/btn_on.png");
			BitmapField bmField1 = new BitmapField(bLogo0,BitmapField.FOCUSABLE)
				{
				    public void fieldChanged(Field field, int context) {
						c.action_callback("menu_launch", "", "");
					}	
				};
		    hlp.add(bmField1);
			fm_Header.add(hlp);
        return fm_Header;
	}
	
	public void construct_main_video(Manager parent, String video_path, String title, String description, String section, String duration, String vdate, final String link) {
		final VerticalFieldManager fm_Body = new VerticalFieldManager(Manager.USE_ALL_WIDTH | Field.FOCUSABLE){


			protected boolean navigationClick(int status,int time)
            {
        		Screen screen = new Video(link, true);    
        	    UiApplication.getUiApplication().pushScreen(screen);
                return true;
            }	
		};
		fm_Body.setFocusListener(new FocusChangeListener() 
		{
			public void focusChanged(Field field, int context) 
			{
				if (context==FocusChangeListener.FOCUS_GAINED) { field.setBackground(b0a); }			
				if (context==FocusChangeListener.FOCUS_LOST) { field.setBackground(b0); }	
			}

		}); 
		fm_Body.setBorder(bb);
		fm_Body.setBackground(b0);
		fm_Body.setMargin(2,2,2,2);
		parent.add(fm_Body);
		video_path += ";deviceside=true";
		VerticalFieldManager fm_Img = new VerticalFieldManager(){
		};
		int fotow = Display.getWidth()-10;
		BitmapField b_VidImage = WriteWebImage(fm_Img, video_path,fotow);
        
        fm_Body.add(fm_Img);
        
        FormatLabel(fm_Body, h2, Color.BLACK, title); 
        
		if (vdate != "") {
			FormatLabel(fm_Body, small2, color_grayest, vdate); 
		}
		if (duration != "") {
			//FormatLabel(fm_Body, small, Color.RED, duration); 
		}
        
		FormatLabel(fm_Body, small, Color.RED, section); 
        
        parent.invalidate();
	}
		
	public void construct_listed_video(Manager parent, String video_path, String title, String description, String section, String duration, String vdate, final String link) {
		final HorizontalFieldManager fm_Body = new HorizontalFieldManager(Manager.USE_ALL_WIDTH | Field.FOCUSABLE){
			protected boolean navigationClick(int status,int time)
            {
        		Screen screen = new Video(link, true);    
        	    UiApplication.getUiApplication().pushScreen(screen);
                return true;
            }	
		};
		fm_Body.setFocusListener(new FocusChangeListener() 
		{
			public void focusChanged(Field field, int context) 
			{
				if (context==FocusChangeListener.FOCUS_GAINED) { field.setBackground(b0a); }			
				if (context==FocusChangeListener.FOCUS_LOST) { field.setBackground(b0); }	
			}

		}); 
		fm_Body.setBorder(bb);
		fm_Body.setBackground(b);
		fm_Body.setMargin(2,1,2,1);
		parent.add(fm_Body);
		int maxw = 128;
		
		video_path += ";deviceside=true";
		
		VerticalFieldManager fm_Img = new VerticalFieldManager(){
			protected boolean navigationClick(int status,int time)
            {
        		Screen screen = new Video(link, true);    
        	    UiApplication.getUiApplication().pushScreen(screen);
                return true;
            }	
		};
		fm_Body.add(fm_Img);
		
		BitmapField b_VidImage = WriteWebImage(fm_Img, video_path, maxw);
        b_VidImage.setMargin(0,4,0,0);
   
		VerticalFieldManager fm_Right = new VerticalFieldManager(){
			protected boolean navigationClick(int status,int time)
            {
        		Screen screen = new Video(link, true);    
        	    UiApplication.getUiApplication().pushScreen(screen);
                return true;
            }	
		};
		fm_Body.add(fm_Right);
        
        FormatLabel(fm_Right, h4, Color.BLACK, title);     
        
		if (vdate != "") {
			 FormatLabel(fm_Right, small2, color_grayest, vdate);
		}
		if (duration != "") {
			//FormatLabel(fm_Right, small2, Color.GRAY, duration);
		}
		
		FormatLabel(fm_Right, small2, Color.RED, section);
        
        parent.invalidate();
	}
	
	public void construct_makescrollable_attache(Manager parent){
		parent.add(new NullField(NullField.FOCUSABLE));
	}
	
	public Manager construct_video_details(final Manager parent, final String video_path, final String video_img, String title, String description, String section, String lenght, String vdate) {
		//HOLDER DECLARATION
		VerticalFieldManager fm_Main = new VerticalFieldManager(Manager.USE_ALL_WIDTH);

		fm_Main.setBackground(b);
		fm_Main.setBorder(bb);
		fm_Main.setMargin(3,3,3,3);
		
    	//IMAGE HOLDER
		HorizontalFieldManager fm_Image = new HorizontalFieldManager(Manager.FOCUSABLE){
			protected boolean navigationClick(int status,int time)
            {
				c.action_callback("play_video", "", "");
                return true;
            }
		};
		fm_Image.setFocusListener(new FocusChangeListener() 
		{
			public void focusChanged(Field field, int context) 
			{
				if (context==FocusChangeListener.FOCUS_GAINED) { field.setBackground(bfocus); }			
				if (context==FocusChangeListener.FOCUS_LOST) { field.setBackground(null); }	
			}

		}); 

		int fotow = Display.getWidth()-10;
		fm_Image.setMargin(3,3,3,3);
		WriteWebImage(fm_Image, video_img, fotow); 
		
    	//CONTROL HOLDER
		HorizontalFieldManager fm_Ctl = new HorizontalFieldManager(Manager.USE_ALL_WIDTH | Manager.FIELD_VCENTER | Manager.FOCUSABLE) {
			protected boolean navigationClick(int status,int time)
            {
				c.action_callback("play_video", "selected", "");
                return true;
            }
			
			
		};
		fm_Ctl.setFocusListener(new FocusChangeListener() 
		{
			public void focusChanged(Field field, int context) 
			{
				if (context==FocusChangeListener.FOCUS_GAINED) { field.setBackground(bfocus); }			
				if (context==FocusChangeListener.FOCUS_LOST) { field.setBackground(bplain); }	
			}

		}); 
		fm_Ctl.setBackground(bplain);
		fm_Ctl.setPadding(4,4,4,4);
		BitmapField bf1 = WriteSimpleImage(fm_Ctl, "img/btn_play.png", BitmapField.FOCUSABLE, 0, "");
		bf1.setFocusListener(new FocusChangeListener() 
		{
			public void focusChanged(Field field, int context) 
			{
				
			}

		}); 
		FormatLabel(fm_Ctl, h3, Color.WHITE, "Reproducir");		
		
    	//TEXT HOLDER
		VerticalFieldManager fm_TH = new VerticalFieldManager(Manager.USE_ALL_WIDTH);
		fm_TH.setMargin(4,4,4,4);
				
			FormatLabel(fm_TH,  small, Color.BLACK, vdate);
			FormatLabel(fm_TH,  h2, Color.BLACK, title);
			FormatLabel(fm_TH,  small2, Color.RED, lenght);
			FormatLabel(fm_TH,  small, Color.BLACK, description);

	
		fm_Main.add(fm_Image);
		fm_Main.add(fm_Ctl);
		fm_Main.add(fm_TH);
		parent.add(fm_Main);
    	return fm_Main;
	}
	
	
	public void construct_video_extended_info(
			Manager parent, final String share_path, 
			final String content_name, final String content_slug,
			final String filter_name, final String filter_slug) {
		
		//TEXT HOLDER 3
		VerticalFieldManager fm_Holder3 = new VerticalFieldManager(Manager.USE_ALL_WIDTH);
		fm_Holder3.setBackground(b);
		fm_Holder3.setBorder(bb);
		fm_Holder3.setMargin(3,3,3,3);
		FormatLabel(fm_Holder3, small, Color.DARKGRAY, "Compartir");
		parent.add(fm_Holder3);
    	
    	//SHARE HOLDER
		HorizontalFieldManager fm_Holder4 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		fm_Holder4.setMargin(3,3,3,3);
		
		Bitmap bH1 = Bitmap.getBitmapResource("img/facebtn.png");
		BitmapField fH1 = new BitmapField(bH1, BitmapField.FOCUSABLE){
			protected boolean navigationClick(int status, int time) {
				c.action_callback("share", "facebook", share_path);
				return true;
			}
		};
        fH1.setPadding(0,0,0,0);
        fH1.setMargin(1,1,1,1);
        //fm_Holder4.add(fH1);
		
        Bitmap bH2 = Bitmap.getBitmapResource("img/tweetbtn.png");
		BitmapField fH2 = new BitmapField(bH2, BitmapField.FOCUSABLE){
			protected boolean navigationClick(int status, int time) {
				c.action_callback("share", "twitter", share_path);
				return true;
			}
		};
        fH2.setPadding(0,0,0,0);
        fH2.setMargin(1,1,1,1);
        fm_Holder4.add(fH2);
        
        Bitmap bH3 = Bitmap.getBitmapResource("img/mailbtn.png");
		BitmapField fH3 = new BitmapField(bH3, BitmapField.FOCUSABLE){
			protected boolean navigationClick(int status, int time) {
				c.action_callback("share", "mail", share_path);
				return true;
			}
		};
        fH3.setPadding(0,0,0,0);
        fH3.setMargin(1,1,1,1);
        fm_Holder4.add(fH3);


		fm_Holder3.add(fm_Holder4);
		
		//HOLDER DECLARATION
		VerticalFieldManager fm_Main = new VerticalFieldManager(Manager.USE_ALL_WIDTH);
		fm_Main.setBackground(b);
		fm_Main.setBorder(bb);
		fm_Main.setMargin(13,3,3,3);
		parent.add(fm_Main);
		
		FormatLabel(fm_Main, small, Color.DARKGRAY, "Relacionados");
		
		HorizontalFieldManager fm_HField = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		
			ButtonField bf_rel1 = new ButtonField(content_name,ButtonField.CONSUME_CLICK);
			fm_HField.add(bf_rel1);
			bf_rel1.setChangeListener(new FieldChangeListener() {
				public void fieldChanged(Field field, int context) {
		        	Screen screen = new Portada(content_slug);    
		        	UiApplication.getUiApplication().pushScreen(screen);  
				}
			});
			
			
			ButtonField bf_rel2 = new ButtonField(filter_name,ButtonField.CONSUME_CLICK);
			fm_HField.add(bf_rel2);
			bf_rel2.setChangeListener(new FieldChangeListener() {
				public void fieldChanged(Field field, int context) {
		        	Screen screen = new Portada(content_slug, filter_slug);    
		        	UiApplication.getUiApplication().pushScreen(screen);  
				}
			});
			
			
		fm_Main.add(fm_HField); 	
		
		
		
	}
		
	public BitmapField WriteWebImage(final Manager parent, final String url, final int size) {
		final BitmapField fImg = new BitmapField(Bitmap.getBitmapResource("img/cargandosm.png"), BitmapField.FOCUSABLE){
			protected void layout(int width, int height) {
	            setExtent(getBitmapWidth()+3, getBitmapHeight()+3);
	        }
		};
	    parent.add(fImg);
	    try {
	    	UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					new AsyncDownloadImage(url,fImg,size).run();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fImg;	
	}
	
	public Manager construct_hmenu_holder(Manager parent) {     		
		HorizontalFieldManager fm_Body = new HorizontalFieldManager(Manager.FIELD_RIGHT | Manager.HORIZONTAL_SCROLL){};
    	fm_Body.setBackground(BackgroundFactory.createSolidBackground(Color.MAROON));
    	parent.add(fm_Body);
    	return fm_Body;
	}
	
	public Manager construct_image_menu_item(Manager parent, String name, String description, String image_path, boolean setfocus, final String listener, final String link) {     		
		VerticalFieldManager fm_Body = new VerticalFieldManager(){
    		protected boolean navigationClick(int status, int time)
    		  {
    			c.action_callback("video_menu_click", listener, link);
    		    return true;
    		  }
    	};
    	fm_Body.setPadding(4,4,4,4);
    	WriteWebImage(fm_Body, image_path, 180);
    	parent.add(fm_Body);
    	if (setfocus) { fm_Body.setFocus(); }
    	return fm_Body;
	}
	
	public Manager construct_text_menu_item(Manager parent, String name, String description, String image_path, boolean setfocus, final String listener, final String link) {     		
		final Background b1 = BackgroundFactory.createSolidTransparentBackground(Color.BLACK, 86);
		VerticalFieldManager fm_Body = new VerticalFieldManager(Field.FOCUSABLE){
    		protected boolean navigationClick(int status, int time)
    		  {
    			c.action_callback("video_menu_click", listener, link);
    		    return true;
    		  }
    		
    	};

    	fm_Body.setPadding(4,4,4,4);
    	LabelField lf = FocusableFormatLabel(fm_Body, h3, Color.WHITE, name);
    	lf.setFocusListener(new FocusChangeListener() 
		{
			public void focusChanged(Field field, int context) 
			{
				if (context==FocusChangeListener.FOCUS_GAINED) { field.setBackground(b1); }			
				if (context==FocusChangeListener.FOCUS_LOST) { field.setBackground(null); }	
			}

		}); 
    	lf.setChangeListener(new FieldChangeListener(){

			public void fieldChanged(Field field, int context) {
    			c.action_callback("video_menu_click", listener, link);
			}});
    	parent.add(fm_Body);
    	if (setfocus) { 
    		fm_Body.setFocus(); 
    		lf.setBackground(BackgroundFactory.createSolidBackground(color_grayest)); 
		}
    	return fm_Body;
	}
	
	public Manager share_content(Manager parent, final String content, final String link) {
		final VerticalFieldManager vfm = new VerticalFieldManager(Field.USE_ALL_WIDTH);
		Background b = BackgroundFactory.createSolidTransparentBackground(Color.GRAY, 48);
		Border bb = BorderFactory.createRoundedBorder(xye, Color.GRAY, Border.STYLE_SOLID);
		vfm.setBackground(b);
		vfm.setBorder(bb);
		vfm.setMargin(13,3,3,3);
		parent.add(vfm);
		XYEdges cxye = new XYEdges(Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK);
		final Border sb =  BorderFactory.createRoundedBorder(xye);
		if (content.equalsIgnoreCase("facebook")) {
			WriteParragraph(vfm, "Escriba su mensaje personal", h3, 0);
			final EditField efMess = new EditField(Field.USE_ALL_WIDTH);
			efMess.setBorder(sb);
			vfm.add(efMess);
			//msg = efMess;
			// ButtonField
			ButtonField btn = new ButtonField("Compartir"){
				protected boolean navigationClick(int status, int time) {
					c.action_callback("share", "face", efMess.getText());
					return true;
				   }
			};
			vfm.add(btn);
			FormatLabel(vfm, small, Color.BLACK, "Vínculo a la nota");
			FormatLabel(vfm, h3, Color.BLACK, link);
			FormatLabel(vfm, small, Color.BLACK, "Se incluirá automáticamente");
		}
		
		if (content.equalsIgnoreCase("twitter")) {
			WriteParragraph(vfm, "Escriba su mensaje personal", h3, 0);
			final EditField efMess = new EditField(Field.USE_ALL_WIDTH);
			efMess.setBorder(sb);
			vfm.add(efMess);
			//msg = efMess;
			// ButtonField
			ButtonField btn = new ButtonField("Compartir"){
				protected boolean navigationClick(int status, int time) {
					c.action_callback("share", "tweet", efMess.getText());
					return true;
				   }
			};
			vfm.add(btn);
			FormatLabel(vfm, small, Color.BLACK, "Vínculo a la nota");
			FormatLabel(vfm, h3, Color.BLACK, link);
			FormatLabel(vfm, small, Color.BLACK, "Se incluirá automáticamente");
		}
	
		if (content.equalsIgnoreCase("mail")) {
			//MAIL
			WriteParragraph(vfm, "Escriba el correo del destinatario", h3, 0);
			final EditField efMail = new EditField(Field.USE_ALL_WIDTH | EditField.FILTER_EMAIL);
			vfm.add(efMail);
			efMail.setBorder(sb);
			//mail = efMail;
			//SUJECT
			WriteParragraph(vfm, "Escriba asunto", h3, 0);
			final EditField efSubj = new EditField(Field.USE_ALL_WIDTH);
			vfm.add(efSubj);
			efSubj.setBorder(sb);
			//sub = efSubj;
			//Title
			WriteParragraph(vfm, "Escriba su mensaje personal", h3, 0);
			EditField efMess = new EditField(Field.USE_ALL_WIDTH);
			vfm.add(efMess);
			efMess.setBorder(sb);
			//msg = efMess;
			ButtonField btn = new ButtonField("Compartir"){
				protected boolean navigationClick(int status, int time) {
					if (content.equalsIgnoreCase("facebook") || content.equalsIgnoreCase("twitter")) {
						WriteParragraph(vfm, "Escriba su mensaje personal", h3, 0);
						final EditField efMess = new EditField(Field.USE_ALL_WIDTH);
						efMess.setBorder(sb);
						vfm.add(efMess);
						//msg = efMess;
						// ButtonField
						ButtonField btn = new ButtonField("Compartir"){
							protected boolean navigationClick(int status, int time) {
								String params[] = {"mail",efMail.getText(),efSubj.getText(),efMess.getText()};
								c.action_callback("share", params);
								return true;
							   }
						};
						vfm.add(btn);
						FormatLabel(vfm, small, Color.BLACK, "Vínculo a la nota");
						FormatLabel(vfm, h3, Color.BLACK, link);
						FormatLabel(vfm, small, Color.BLACK, "Se incluirá automáticamente");
					}
					return true;
				   }
			};
			vfm.add(btn);
			FormatLabel(vfm, small, Color.BLACK, "Vínculo a la nota");
			FormatLabel(vfm, h3, Color.BLACK, link);
			FormatLabel(vfm, small, Color.BLACK, "Se incluirá automáticamente");
		}
		return vfm;	
	}
	
	public Manager construct_main_vid() {
		VerticalFieldManager fm_MainHolder = new VerticalFieldManager(Manager.USE_ALL_WIDTH | Manager.USE_ALL_HEIGHT | Manager.FIELD_VCENTER){
			protected boolean navigationClick(int status, int time) {
				//
				return true;
			}
		};
		Background back = BackgroundFactory.createSolidBackground(Color.ANTIQUEWHITE);
		fm_MainHolder.setBackground(back);
		return fm_MainHolder;
	}
	
	
	public Manager construct_micro_header(Manager parent) {
		CInterface ci = new CInterface();
		HorizontalFieldManager fm_Header = new HorizontalFieldManager(Manager.FIELD_VCENTER | Manager.FIELD_HCENTER| Manager.FIELD_BOTTOM | Manager.USE_ALL_WIDTH);
		Background back_Header = BackgroundFactory.createSolidBackground(Color.BLACK);
		fm_Header.setBackground(back_Header);
		parent.add(fm_Header);
				
		int NUM_COLUMNS = 3;
		final int columnWidth0 = (main_image_size);
		final int columnWidth1 = (Display.getWidth()-columnWidth0)/2;

		//HELP LOGO
		VerticalFieldManager hlp = new VerticalFieldManager(Manager.FIELD_VCENTER | Manager.FIELD_HCENTER) {
				public int getPreferredWidth() {       
			       return columnWidth1; 
			   }
			   protected void sublayout(int width, int height) {
			       width = getPreferredWidth();
			       height = super.getPreferredHeight();
			       super.sublayout(width, height);
			       super.setExtent(width, height);
			   }
		};
		
			final Bitmap bLogo0 = Bitmap.getBitmapResource("img/btn_del.png");
			final Bitmap bLogo1 = Bitmap.getBitmapResource("img/btn_del.png");
			BitmapField bmField1 = new BitmapField(bLogo0,BitmapField.FOCUSABLE)
				{
				    public void onFocus(int direction)
				    {
				        setBitmap(bLogo1);
				    }
	
				    public void onUnfocus()
				    {
				        //super.onUnfocus();
				        setBitmap(bLogo0);
				    }
				    protected boolean navigationClick(int status, int time) {
				    	UiApplication app = (UiApplication)Application.getApplication();
				    	app.popScreen( app.getActiveScreen());
						return true;
					}	
				};
		        
			hlp.add(bmField1);
			fm_Header.add(hlp);
			

        return fm_Header;
	}
	
	public void construct_navigator(Manager parent,String path) {
		BrowserFieldConfig bfc = new BrowserFieldConfig();
		BrowserField bf = new BrowserField();
		parent.add(bf);
		bf.requestContent(path);
		
	}
	
	public LabelField FormatLabel(Manager parent, net.rim.device.api.ui.Font font, final int color, String content) {	
		content = content.trim();

	    LabelField l_Label = new LabelField(content, Field.NON_FOCUSABLE) {
	        public void paint(Graphics g){
	            g.setColor(color);  
	            super.paint(g);
	        }  
	    };
	    l_Label.setMargin(1,1,1,1);
	    l_Label.setPadding(0,0,0,0);
	    l_Label.setFont(font);
	    parent.add(l_Label);
		return l_Label;
	}
	
	public LabelField FocusableFormatLabel(Manager parent, net.rim.device.api.ui.Font font, final int color, String content) {	
		content = content.trim();

	    LabelField l_Label = new LabelField(content, Field.FOCUSABLE) {
	        public void paint(Graphics g){
	            g.setColor(color);  
	            super.paint(g);
	        }  
	    };
	    l_Label.setMargin(1,1,1,1);
	    l_Label.setPadding(0,0,0,0);
	    l_Label.setFont(font);
	    parent.add(l_Label);
		return l_Label;
	}
	
	public BitmapField WriteSimpleImage(Manager parent, String resource, long style, int size, String listener) {
		EncodedImage ei = EncodedImage.getEncodedImageResource(resource);
		EncodedImage ei2 = getScaledImage(ei, size);
		BitmapField fImg = new BitmapField(ei2.getBitmap(), style);	
		parent.add(fImg);
		return fImg;
	}
	
	public TextField WriteParragraph(Manager parent, String text, net.rim.device.api.ui.Font font, long style) {
		String alignedtxt  = text;
		TextField tf = new TextField(style);
		tf.setEditable(false);
		tf.setFont(font);
		alignedtxt = alignedtxt.trim();
		tf.setText(alignedtxt);
		tf.setMargin(10,5,5,5);
		parent.add(tf);  
		return tf;	
	}
	
	
	

}
