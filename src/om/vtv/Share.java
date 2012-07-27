package om.vtv;

import om.ui.Layout;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.PasswordEditField;


public class Share extends VTV {
	
		String BITLY_KEY = "R_8925c37a76786f9a5620d8c3f0f9e65c";
		String BITLY_USER = "telesur";
		String FACEBOOK_ID ="178675485518802";
		String PREFS_NAME = "Persistent_Sharing_teleSUR";
		String TWITTER_KEY = "qrnWqNWf4T0KofJ0CRkQ";
		String TWITTER_CONSUMER_SECRET_KEY = "cdg32ba6xKcjx6H92kiAs0hpfBTwbUW5RCYJUCThBQ";
		String FACEBOOK_APP_ID = "178675485518802";
		String FACEBOOK_APP_SECRET = "0ee272004c24e551ac546092920e05d4";
		
		PasswordEditField psw = null;
		EditField usr = null;
		EditField mail = null;
		EditField sub = null;
		EditField msg = null;
		String this_link ="";
		String this_method = "";

		Share(String smethod, String ref) {
			La = new Layout(this);
			this_link = ref;
			this_method = smethod;
			Manager fm_MainHolder = La.construct_main();
			super.add(fm_MainHolder);
		    mastercontainer = fm_MainHolder;
	        La.construct_header(fm_MainHolder, false);
	        String bquery = BitlyQuery(ref);
	        construct_bitly_res(fm_MainHolder, bquery);
	        
		}
		public void InitializeShareContent(Manager parent, String subk, String tag, String title, String data) {
			La.share_content(parent, subk, this_link);
				
		}
		
		
		
		 public void Launch_Sharing(String content) {
			 if (content.equalsIgnoreCase("mail")) {
				 InvokeMail(msg.getText(), sub.getText(), mail.getText(), this_link);
			 }
			 if (content.equalsIgnoreCase("facebook")) {
				 InvokeFace(msg.getText(), this_link);
			 }
			 if (content.equalsIgnoreCase("twitter")) {
				 InvokeTwitter(msg.getText(), this_link);
			 }
		 }
		
		 
		 
		 public void construct_bitly_res(final Manager parent, final String url) {
			 final Manager hold = invoke_hold_flag(parent, content_lang);
			 final VTV t = this;
			 UiApplication.getUiApplication().invokeLater( 
				new Runnable()  {   
					public void run ()    { 
						//ContentManager tcn = new ContentManager(t, parent, content_lang, "","","","");
				        //InputStream is = tcn.download_parse_resource(url, true);
						//String str = StreamToString(is);
						//this_link = parse_json_bitly(str);
						this_link = url;
						La.share_content(parent, this_method, this_link);
						//parent.delete(hold);
					}        
			 } );  
			 
		 }
		 
		public String parse_json_bitly(String str) {
			String result ="";
			try {
				JSONObject jObject = new JSONObject(str);
				JSONObject innerObj = jObject.getJSONObject("data");
				result = innerObj.getString("url").toString();

			} catch (JSONException e) {		}
			return result;
		}
		    
		    public String BitlyQuery(String myurl) {
		    	myurl =  URLencode(myurl);
		    	String bitly_web="";
		    	bitly_web = "http://api.bit.ly/v3/shorten?login="+ BITLY_USER + "" + "&apiKey="  + BITLY_KEY + "&longUrl=" + myurl + "&format=json";
				return bitly_web;
				
				
		    }
		    
		    public static String URLencode(String s)
		    {
		        if (s!=null) {
		            StringBuffer tmp = new StringBuffer();
		            int i=0;
		            try {
		                while (true) {
		                    int b = (int)s.charAt(i++);
		                    if ((b>=0x30 && b<=0x39) || (b>=0x41 && b<=0x5A) || (b>=0x61 && b<=0x7A)) {
		                        tmp.append((char)b);
		                    }
		                    else {
		                        tmp.append("%");
		                        if (b <= 0xf) tmp.append("0");
		                        tmp.append(Integer.toHexString(b));
		                    }
		                }
		            }
		            catch (Exception e) {}
		            return tmp.toString();
		        }
		        return null;
		    }

		    public void DoMail() {
		    	
		    }
		
		    public void InvokeMail(String text, String subject, String address, String link) {
		    	Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES , 
		    	new MessageArguments(MessageArguments.ARG_NEW, address, subject, text+"  "+link));
		    }
		    
		    public void InvokeFace(String text, String link) {
		          BrowserSession bSession = Browser.getDefaultSession();
		          String shareUrl = "http://m.facebook.com/dialog/feed?app_id="+FACEBOOK_ID+"&link="+link+"&caption="+text+"&display=touch";
		          System.out.println(shareUrl);
		          bSession.displayPage( shareUrl );
		          //Screen screen = new Nav(shareUrl);    
		          //UiApplication.getUiApplication().pushScreen(screen);  

		    }
		    
		    public void InvokeTwitter(String text, String link) {
		          BrowserSession bSession = Browser.getDefaultSession();
		          String shareUrl = "https://twitter.com/intent/tweet?text="+text+"&url=" + link;
		          System.out.println(shareUrl);
		          bSession.displayPage( shareUrl );
		          //Screen screen = new Nav(shareUrl);    
		          //UiApplication.getUiApplication().pushScreen(screen);  
		    }
		    
		    
		    
		    ///
		    
		    
		    

	}
