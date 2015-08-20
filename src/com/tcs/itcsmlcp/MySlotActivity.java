package com.tcs.itcsmlcp;

import static com.tcs.itcsmlcp.CommonUtilities.EXTRA_MESSAGE;
import static com.tcs.itcsmlcp.CommonUtilities.SENDER_ID;
import static com.tcs.itcsmlcp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; 

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcs.itcsmlcp.ServerUtilities;
import com.tcs.itcsmlcp.AlertDialogManager;
import com.tcs.itcsmlcp.ConnectionDetector;
import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MySlotActivity extends Activity {

	
	String serverURL_bookslot =AppConstant.URL;
	String serverURL_exitslot =AppConstant.URL+"exitslot_json.php";
	MyApp aController;
	private ProgressDialog progress;
	
	//****************
	// Asyntask
		AsyncTask<Void, Void, Void> mRegisterTask;
		
		// Alert dialog manager
		AlertDialogManager alert = new AlertDialogManager();
		
		// Connection detector
		ConnectionDetector cd;
	//****************
	 
//	 String vtype;
	 int empid;
	 
	 TextView lblFloor,lblSlot,lblTimein,lblTimehrs,lblHead;
	 ImageButton btnBack,btnRefresh;
     Button btnExit;
  /*   @Override
     public boolean dispatchTouchEvent(MotionEvent ev) {
         Rect dialogBounds = new Rect();
         getWindow().getDecorView().getHitRect(dialogBounds);

         if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
             return true;
         }
         return super.dispatchTouchEvent(ev);
     }
     @Override
	  public boolean onTouchEvent(MotionEvent event) {
	    // If we've received a touch notification that the user has touched
	    // outside the app, finish the activity.
	    if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
	      finish();
	      return true;
	    }

	    // Delegate everything else to Activity.
	    return super.onTouchEvent(event);
	  }*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // Make us non-modal, so that others can receive touch events.
	 /*   getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);

	    // ...but notify us that it happened.
	    getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);*/
		setContentView(R.layout.activity_myslot);
//		 this.setFinishOnTouchOutside(true);
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		
		lblFloor=(TextView)findViewById(R.id.lblFloor);
		lblSlot=(TextView)findViewById(R.id.lblSlot);
		lblTimein=(TextView)findViewById(R.id.lblTimein);
		lblTimehrs=(TextView)findViewById(R.id.lblTimehrs);
		
		lblHead=(TextView)findViewById(R.id.text_price);
		lblHead.setText("MY SLOT");
		
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
		
		//**************************************************
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MySlotActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
				//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			}/* else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, "ashishdemo", "demo@gmail.com", regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}*/
		
		}
		
		//**************************************************
		
		
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new LongOperation_bookslot(serverURL_bookslot,empid,MySlotActivity.this).execute("");
			}
		});
		
		
		//*************************************************
		//GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getBaseContext());
       /* try {
        	GCMRegistrar.unregister(MySlotActivity.this);
        } 
        catch (Exception e) {     
        System.out.println("Error Message: " + e.getMessage());
        }*/
		// Make sure the device has the proper dependencies.
				/*GCMRegistrar.checkDevice(this);

				// Make sure the manifest was properly set - comment out this line
				// while developing the app, then uncomment it when it's ready.
				GCMRegistrar.checkManifest(this);

				//lblMessage = (TextView) findViewById(R.id.lblMessage);
				
				registerReceiver(mHandleMessageReceiver, new IntentFilter(
						DISPLAY_MESSAGE_ACTION));
				
				// Get GCM registration id
				final String regId = GCMRegistrar.getRegistrationId(this);

				// Check if regid already presents
				if (regId.equals("")) {
					// Registration is not present, register now with GCM			
					GCMRegistrar.register(this, SENDER_ID);
				} else {
					// Device is already registered on GCM
					if (GCMRegistrar.isRegisteredOnServer(this)) {
						// Skips registration.				
						Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
					}  
		}*/
		//*************************************************
		
		
		
		btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// Creating alert Dialog with two Buttons

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(MySlotActivity.this);

				// Setting Dialog Title
				alertDialog.setTitle("Swipe Out...");

				// Setting Dialog Message
				alertDialog.setMessage("Are you sure you want to exit?");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.ic_launcher);

				
				// Setting Negative "NO" Button
				alertDialog.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int which) {
								// Write your code here to execute after dialog
//								Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
								dialog.cancel();
							}
						});
				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								// Write your code here to execute after dialog
//								Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								new LongOperation_exitslot(serverURL_exitslot, empid, MySlotActivity.this).execute("");
								
							}
						});
				// Showing Alert Message
				alertDialog.show();

				
				
				
//				new LongOperation_exitslot(serverURL_exitslot, empid, MySlotActivity.this).execute("");
//				
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		
		 aController = (MyApp) getApplicationContext();
//		aController = (MyApp) getApplicationContext();
			// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {
				// Internet Connection is not present
				aController.showAlertDialog(MySlotActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false,MySlotActivity.this);

						// stop executing code by return
//				finish();
			   return;
			   
			 
			 }
		
			/*else if(!GCMRegistrar.isRegisteredOnServer(this)) {
				
					Intent i=new Intent(this,RegisterActivity.class);
					startActivity(i);
					// Skips registration.				
					Toast.makeText(getApplicationContext(), "Please register yourself with TCS Park-IN Server", Toast.LENGTH_LONG).show();
				finish();
				} 		
	*/
			
			
			else{
		
//				Intent iget=getIntent();
//				vtype=iget.getStringExtra("vtype");
				final DatabaseHandler db = new DatabaseHandler(this);
				if(db.getContactsCount()>0)
				{
					
					List<Info> getInfo=db.getAllContacts();
					if(getInfo.size()>0){
					empid=getInfo.get(0).getID();
					}
				}
				
				new LongOperation_bookslot(serverURL_bookslot,empid,this).execute("");
			}
	}
	//****************************************************
	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
				
			Toast.makeText(MySlotActivity.this, "My Slot New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			new LongOperation_bookslot(serverURL_bookslot,empid,MySlotActivity.this).execute("");
			//lblTimehrs.setText(""+newMessage);
			// Releasing wake lock
			WakeLocker.release(); 
		}
	};
	
	
	
	//****************************************************
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
		 // Class with extends AsyncTask class
	  private class LongOperation_bookslot  extends AsyncTask<String, Void,String> {
	       String _url,_slotdate,_empimei;
	       Activity __context;
	       int _empid;
	       
	     
	//System.out.print(dateFormat.format(date)); //2014/08/06 15:59:48

	       
	  	// Required initialization
	  	public LongOperation_bookslot(String url,int id,Activity c)
	  	{
	  		_url=url;
	  		__context=c;
	  		_empid=id;
	  		
	  		
	  	}
	      private final HttpClient Client = new DefaultHttpClient();
	      private String Content;
	      private String Error = null;
//	      private ProgressDialog Dialog = new ProgressDialog(__context);
	      
	      String data =""; 
//	      TextView uiUpdate = (TextView) findViewById(R.id.output);
//	      TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
	      int sizeData = 0;  
//	      EditText serverText = (EditText) findViewById(R.id.serverText);
	      
	      
	      protected void onPreExecute() {
	          // NOTE: You can call UI Element here.
	           
	          //Start Progress Dialog (Message)
	         
//	          Dialog.setMessage("Please wait..");
//	          Dialog.show();
	          
//	          try{
//	          	// Set Request parameter
//	              data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
//		            	
//	          } catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
	    	  progress=ProgressDialog.show(MySlotActivity.this, "", "");
				 progress.setContentView(R.layout.progress);
			      progress.show();
	    	  
	    	  
	    	  
	    	  
//				} 
	          
		    	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		    	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    	  Date date = new Date();
		    	  _slotdate=dateFormat.format(date);
//		    	 	Toast.makeText(__context, _slotdate, Toast.LENGTH_LONG).show();
		    	  // GET IMEI NUMBER      
					 TelephonyManager tManager = (TelephonyManager) getBaseContext()
					    .getSystemService(Context.TELEPHONY_SERVICE);
					   _empimei = tManager.getDeviceId();
		    	  
	    	  
	    	  
	      }

	      // Call after onPreExecute method
	      protected String doInBackground(String... urls) {
	      	
	      	/************ Make Post Call To Web Server ***********/
	      	BufferedReader reader=null;
	 
		             // Send data 
		            try
		            { 
//		        		Thread.sleep(1000);
//		               // Defined URL  where to send data
		            	
//		            	_url=_url+"?empid="+_empid+"&empname="+_empname+"&empshift="+_empshift+"&empdate="+_empdate;
		            	
		            	 /* String data = URLEncoder.encode("slotdate", "UTF-8") + "=" + URLEncoder.encode(_slotdate, "UTF-8"); 
//		                  data += "&" + URLEncoder.encode("empimei", "UTF-8") + "=" + URLEncoder.encode(_empimei, "UTF-8"); 
		                  data += "&" + URLEncoder.encode("empid", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(_empid), "UTF-8");
//		                  data += "&" + URLEncoder.encode("empdate", "UTF-8") + "=" + URLEncoder.encode(_empdate, "UTF-8");
		            	*/
		            	
		            	//********************************
		            	String getBookingDetailsById = "GetBookingDetailsById";
						//String employeeId="836849";
						//String name="ashish kumar";
						String data = URLEncoder.encode("tag", "UTF-8") + "=" + URLEncoder.encode(getBookingDetailsById, "UTF-8");
									data += "&" + URLEncoder.encode("employeeId", "UTF-8")+ "=" + URLEncoder.encode(_empid+"", "UTF-8");
									//data += "&" + URLEncoder.encode("name", "UTF-8")+ "=" + URLEncoder.encode(_adminname, "UTF-8"); 
						
		            	//*********************************
		            	
		            	
		               URL url = new URL(_url);
		               
//		                 
//		              // Send POST data request
//		   
		              URLConnection conn = url.openConnection(); 
		              conn.setDoOutput(true); 
		              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
		              wr.write( data ); 
		              wr.flush(); 
//		          
		              // Get the server response 
		               
		              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		              StringBuilder sb = new StringBuilder();
		              String line = null;
		            
			            // Read Server Response
			            while((line = reader.readLine()) != null)
			                {
			                       // Append server response in string
			                       sb.append(line + "\n");
			                }
		                
		                // Append Server Response To Content String 
		               Content = sb.toString();
		            }
		            catch(Exception ex)
		            {
		            	Error = ex.getMessage();
		            }
		            finally
		            {
		                try
		                {
		     
		                    reader.close();
		                }
		   
		                catch(Exception ex) {}
		            }
	      	
	          /*****************************************************/
	          return "";
	      }
	       
	      @SuppressWarnings("unused")
		protected void onPostExecute(String unused) {
	          // NOTE: You can call UI Element here.
	           
//	           Close progress dialog
	    	  if(progress.isShowing())
	    		  progress.dismiss();
//	          Dialog.dismiss();
	           
	          if (Error != null) {
	               
//	              uiUpdate.setText("Output : "+Error);
	        		 Toast.makeText(__context, "Error due to some network problem! Please connect to internet. ", Toast.LENGTH_LONG).show();
	               
	          } else {
	            
	          	// Show Response Json On Screen (activity)
//	          	uiUpdate.setText( Content );
	          	//Toast.makeText(__context, Content, Toast.LENGTH_LONG).show();
	          	
	          	 JSONObject jsonResponse;
					try {
						if(Content!=null){
							/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
							//jsonResponse = new JSONObject(Content);
							JSONObject myJson = new JSONObject(Content);
							// use myJson as needed, for example 
							String tag = myJson.optString("tag");
							//Toast.makeText(__context, "tag:"+tag, Toast.LENGTH_LONG).show();
							
							
							String error = myJson.optString("error");
							if(error.equals("false")){
								//Toast.makeText(__context, "Valid User", Toast.LENGTH_LONG).show();
								JSONArray jArray = myJson.getJSONArray("values");
								for(int i=0;i<jArray.length();i++){
									
									JSONObject oneObject = jArray.getJSONObject(i);
									// Pulling items from the array
							        String slotid = oneObject.getString("slotid");
							        String floorid = oneObject.getString("floorid");
							        String book_timein = oneObject.getString("book_timein");
							        
							       // Toast.makeText(__context,"Slot Id:"+slotid+", Floor Id:"+floorid+", Time In:"+book_timein, Toast.LENGTH_LONG).show();
									
									lblSlot.setText(slotid);
									lblFloor.setText(floorid);
									lblTimein.setText(book_timein);
								}
								
								
								
								
							}else if(error.equals("true")){
								lblSlot.setText("");
								lblFloor.setText("");
								lblTimein.setText("");
								String errorMsg = myJson.optString("errorMsg");
								Toast.makeText(__context, errorMsg, Toast.LENGTH_LONG).show();
							}
							
						}
						
					}catch (Exception e) {
							// TODO: handle exception
						}
	          	
	          /* *//****************** Start Parse Response JSON Data *************//*
	        	  
	              
	              
	             String slot_result="",slot_no="",floor_no="",time_in="",time="";
	        	  
	        	  
	          	String OutputData = "";
	              JSONObject jsonResponse;
//	              Toast.makeText(_context, "Error"+Content, Toast.LENGTH_LONG).show();
	              try {
	            	  if(Content!=null){
	                   *//****** Creates a new JSONObject with name/value mappings from the JSON string. ********//*
	                   jsonResponse = new JSONObject(Content);
	                    Log.d("RESPONSE----", jsonResponse.toString());
	                   *//***** Returns the value mapped by name if it exists and is a JSONArray. ***//*
	                   *//*******  Returns null otherwise.  *******//*
	                   JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
	                    
	                   *//*********** Process each JSON Node ************//*

	                   int lengthJsonArr = jsonMainNode.length();  
//	                   String[] mStrings={};
//	                   String[] news_date_arr = new String[lengthJsonArr];
//	                   String[] news_time_arr = new String[lengthJsonArr];
//	                   String[] news_title_arr = new String[lengthJsonArr];
//	                   String[] news_desc_arr = new String[lengthJsonArr];
//	                   String[] mStrings = new String[lengthJsonArr];
//	                   String[] news_bigimg_arr = new String[lengthJsonArr];
	                   
	                   
//	                   news_date_arr = new String[lengthJsonArr];
//	                  news_time_arr = new String[lengthJsonArr];
//	                   news_title_arr = new String[lengthJsonArr];
//	                   news_desc_arr = new String[lengthJsonArr];
//	                   mStrings = new String[lengthJsonArr];
//	                   news_bigimg_arr = new String[lengthJsonArr];
	                   
	                   
//	                   ArrayList<Scores_Category> categoriesList=new ArrayList<Scores_Category>();
	                   
	                   for(int i=0; i < lengthJsonArr; i++) 
	                   {
	                       *//****** Get Object for each JSON node.***********//*
	                       JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
	                        
	                       *//******* Fetch node values **********//*
//	                       String news_date       = jsonChildNode.optString("news_date").toString();
//	                       String news_time     = jsonChildNode.optString("news_time").toString();
//	                       String news_title = jsonChildNode.optString("news_title").toString();
//	                       String news_desc = jsonChildNode.optString("news_content").toString();
//	                       String news_img = jsonChildNode.optString("nimg_image").toString();
//	                       String news_bigimg = jsonChildNode.optString("nimg_bigimg").toString();
//	                       String news_bigimg = jsonChildNode.optString("nimg_image").toString();
//	                        emp_id       = jsonChildNode.optInt("emp_id");
//	                        emp_date       = jsonChildNode.optString("emp_date").toString();
	                       
//	                        emp_name = jsonChildNode.optString("emp_name").toString();
//	                        emp_shift = jsonChildNode.optString("emp_shift").toString();
	                        slot_result = jsonChildNode.optString("myslot_result").toString();
	                        slot_no = jsonChildNode.optString("slotname").toString();
	                        floor_no = jsonChildNode.optString("floorname").toString();
	                        time_in = jsonChildNode.optString("timein").toString();
	                        time = jsonChildNode.optString("time").toString();
	                        
//	                       String news_content = jsonChildNode.optString("news_desc").toString();
//	                       news_img=AppConstant.URLimg+news_img;
//	                       news_bigimg=AppConstant.URLimg+news_bigimg;
//	                       mStrings[i]="http://10.0.2.2/fmard/"+news_qrimg;
//	                       news_bigimg_arr[i]="http://10.0.2.2/fmard/"+news_bigimg;
//	                       OutputData += " Name 		    : "+ news_title +" \n "
//	                                   + "Number 		: "+ news_date +" \n "
//	                                   + "Time 				: "+ news_time +" \n "
//	                                   + "DESC 				: "+ news_desc +" \n "
//	                                   + "Image 				: "+ news_qrimg +" \n "
//	                                   + "Image 				: "+ mStrings[i] +" \n "
//	                                   +"--------------------------------------------------\n";
	                      
	                       //Log.i("JSON parse", song_name);
	                       
	//#######################################################
	                       
//	                       JSONObject catObj = (JSONObject) jsonMainNode.get(i);
//								Scores_Category cat = new Scores_Category(catObj.getInt("news_id"),catObj.getString("news_title"));
//								categoriesList.add(cat);
//	                   	public NewsItem(int id, String date, String title, String image,String bigimage, String desc,String content,Boolean selected)
//	                            NewsItem itm=new NewsItem(news_id,news_date,news_title,news_img,news_bigimg,news_desc,news_content,false);
//	                            resultList.add(itm);
//	                       //######################################################
//	                       Log.d("Jeeeeet", String.valueOf(resultList.size()));
	                       
	                       
	                  }
	                   
	                   
	                   if(slot_result.equalsIgnoreCase("success"))
	                   {
//	                   formatTxt.setText("SHIFT: " +emp_shift +emp_name);
	                   aController.showAlertDialog(MySlotActivity.this,
								"Parking Status:",
							"Your slot is: \n"+"Floor :  "+	floor_no+"\n "+"Slot :   "+slot_no, true,MySlotActivity.this);
	                	   
	                	lblFloor.setText(floor_no);   
	                	lblSlot.setText(slot_no);   
	                	lblTimein.setText(time_in);   
	                	lblTimehrs.setText(timeConversion((int)Float.parseFloat(time)));   
	                	btnExit.setEnabled(true);   
	                	   
//	                   Toast.makeText(MySlotActivity.this, "Slot booked Successfully! ", Toast.LENGTH_LONG).show();
	                   }
	                   else if(slot_result.equalsIgnoreCase("unsuccess"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
									"Parking Status:",
									"Sorry! No slot is alloted for your vehicle.", false,MySlotActivity.this);
//		                   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("already"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
	                			   "Booking Status:",
	                			   "You already booked your slot!\n"+"Floor No:  "+	floor_no+"\n "+"Slot No.:   "+slot_no, true,MySlotActivity.this);
//	                	   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("unregister2W"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
	                			   "Booking Status:",
	                			   "You registered for Four Wheeler!" +
	                			   "\nPlease update registration for Two Wheeler!", false,MySlotActivity.this);
//	                	   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("unregister4W"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
	                			   "Booking Status:",
	                			   "You registered for Two Wheeler!" +
	                			   "\nPlease update registration for Four Wheeler!", false,MySlotActivity.this);
//	                	   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   
	                   else
	                   {
//	                	   aController.showAlertDialog(ParkingInfoActivity.this,
//									"Booking Status:",
//									"Sorry for inconvience! PARKING FULL", false);
		                   Toast.makeText(MySlotActivity.this, "Error in fetching parking info! ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	               *//****************** End Parse Response JSON Data *************//*     
	                   
	                   //Show Parsed Output on screen (activity)
//	                   jsonParsed.setText( OutputData );
	                   
	                   
	                   
	                // Create custom adapter for listview
//	           		adapter = new LazyImageLoadAdapter(News.this, mStrings,news_date_arr,news_time_arr,news_title_arr,news_desc_arr);

	           		// Set adapter to listview
//	           		list.setAdapter(adapter);

	                   
	                   
//	                 -------------------------Fmard-----------------Start
//	                 Intent i=new Intent(News.this,News.class);
//	                 i.putExtra("news_date", news_date_arr);
//	                 i.putExtra("news_time", news_time_arr);
//	                 i.putExtra("news_title", news_title_arr);
//	                 i.putExtra("news_desc", news_desc_arr);
//	                 i.putExtra("img_urls", mStrings);
//	                 i.putExtra("bigimg_urls",news_bigimg_arr);
//	                 
//	                 
//	                 i.putExtra("categories", categoriesList);
//	                 
//	                 startActivity(i);
//	                 -------------------------Fmard--------------End---
	                   
	              }else {
         			 Toast.makeText(__context, "Error due to some network problem! Please connect to internet. ", Toast.LENGTH_LONG).show();
				}        
	                    
	               } catch (JSONException e) {          
	                   e.printStackTrace();
//	                   Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();
	               }*/

	               
	           }
			
	      }
	       
	  }
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
	  private static String timeConversion(int totalSeconds) {
	        int hours = totalSeconds / 60 / 60;
	        int minutes = (totalSeconds - (hoursToSeconds(hours)))
	                / 60;
	        int seconds = totalSeconds
	                - ((hoursToSeconds(hours)) + (minutesToSeconds(minutes)));

	        return hours + " hrs " + minutes + " min " + seconds + " sec";
	    }

	    private static int hoursToSeconds(int hours) {
	        return hours * 60 * 60;
	    }

	    private static int minutesToSeconds(int minutes) {
	        return minutes * 60;
	    }
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
		
		 // Class with extends AsyncTask class
	  private class LongOperation_exitslot  extends AsyncTask<String, Void,String> {
	       String _url,_slotdate,_empimei;
	       Activity __context;
	       int _empid;
	       
	     
	//System.out.print(dateFormat.format(date)); //2014/08/06 15:59:48

	       
	  	// Required initialization
	  	public LongOperation_exitslot(String url,int id,Activity c)
	  	{
	  		_url=url;
	  		__context=c;
	  		_empid=id;
	  		
	  		
	  	}
	      private final HttpClient Client = new DefaultHttpClient();
	      private String Content;
	      private String Error = null;
//	      private ProgressDialog Dialog = new ProgressDialog(__context);
	      
	      String data =""; 
//	      TextView uiUpdate = (TextView) findViewById(R.id.output);
//	      TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
	      int sizeData = 0;  
//	      EditText serverText = (EditText) findViewById(R.id.serverText);
	      
	      
	      protected void onPreExecute() {
	          // NOTE: You can call UI Element here.
	           
	          //Start Progress Dialog (Message)
	         
//	          Dialog.setMessage("Please wait..");
//	          Dialog.show();
	          
//	          try{
//	          	// Set Request parameter
//	              data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
//		            	
//	          } catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
	    	  progress=ProgressDialog.show(MySlotActivity.this, "", "");
				 progress.setContentView(R.layout.progress);
			      progress.show();
	    	  
	    	  
	    	  
	    	  
//				} 
	          
		    	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	  
//		    	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    	  Date date = new Date();
		    	  _slotdate=dateFormat.format(date);
//		    	 	Toast.makeText(__context, _slotdate, Toast.LENGTH_LONG).show();
	//	    	 	Toast.makeText(__context, _empdate, Toast.LENGTH_LONG).show();
		    	  // GET IMEI NUMBER      
					 TelephonyManager tManager = (TelephonyManager) getBaseContext()
					    .getSystemService(Context.TELEPHONY_SERVICE);
					   _empimei = tManager.getDeviceId();
		    	  
	    	  
	    	  
	      }

	      // Call after onPreExecute method
	      protected String doInBackground(String... urls) {
	      	
	      	/************ Make Post Call To Web Server ***********/
	      	BufferedReader reader=null;
	 
		             // Send data 
		            try
		            { 
//		        		Thread.sleep(1000);
//		               // Defined URL  where to send data
		            	
//		            	_url=_url+"?empid="+_empid+"&empname="+_empname+"&empshift="+_empshift+"&empdate="+_empdate;
		            	
		            	  String data = URLEncoder.encode("slotdate", "UTF-8") + "=" + URLEncoder.encode(_slotdate, "UTF-8"); 
//		                  data += "&" + URLEncoder.encode("empimei", "UTF-8") + "=" + URLEncoder.encode(_empimei, "UTF-8"); 
		                  data += "&" + URLEncoder.encode("empid", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(_empid), "UTF-8");
//		                  data += "&" + URLEncoder.encode("empdate", "UTF-8") + "=" + URLEncoder.encode(_empdate, "UTF-8");
		            	
		            	
		            	
		               URL url = new URL(_url);
		               
//		                 
//		              // Send POST data request
//		   
		              URLConnection conn = url.openConnection(); 
		              conn.setDoOutput(true); 
		              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
		              wr.write( data ); 
		              wr.flush(); 
//		          
		              // Get the server response 
		               
		              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		              StringBuilder sb = new StringBuilder();
		              String line = null;
		            
			            // Read Server Response
			            while((line = reader.readLine()) != null)
			                {
			                       // Append server response in string
			                       sb.append(line + "\n");
			                }
		                
		                // Append Server Response To Content String 
		               Content = sb.toString();
		            }
		            catch(Exception ex)
		            {
		            	Error = ex.getMessage();
		            }
		            finally
		            {
		                try
		                {
		     
		                    reader.close();
		                }
		   
		                catch(Exception ex) {}
		            }
	      	
	          /*****************************************************/
	          return "";
	      }
	       
	      @SuppressWarnings("unused")
		protected void onPostExecute(String unused) {
	          // NOTE: You can call UI Element here.
	           
//	           Close progress dialog
	    	  if(progress.isShowing())
	    		  progress.dismiss();
//	          Dialog.dismiss();
	           
	          if (Error != null) {
	               
//	              uiUpdate.setText("Output : "+Error);
//	          	Toast.makeText(__context, "Error"+Error, Toast.LENGTH_LONG).show();
	          	 Toast.makeText(__context, "Error due to some network problem! Please connect to internet. ", Toast.LENGTH_LONG).show();       
	          } else {
	            
	          	// Show Response Json On Screen (activity)
//	          	uiUpdate.setText( Content );
	          	
	           /****************** Start Parse Response JSON Data *************/
	        	  
	              
	              
	             String slot_result="",slot_no="",floor_no="";
	        	  
	        	  
	          	String OutputData = "";
	              JSONObject jsonResponse;
//	              Toast.makeText(_context, "Error"+Content, Toast.LENGTH_LONG).show();
	              try {
	                    if(Content!=null){
	                   /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
	                   jsonResponse = new JSONObject(Content);
	                    Log.d("RESPONSE----", jsonResponse.toString());
	                   /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
	                   /*******  Returns null otherwise.  *******/
	                   JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
	                    
	                   /*********** Process each JSON Node ************/

	                   int lengthJsonArr = jsonMainNode.length();  
//	                   String[] mStrings={};
//	                   String[] news_date_arr = new String[lengthJsonArr];
//	                   String[] news_time_arr = new String[lengthJsonArr];
//	                   String[] news_title_arr = new String[lengthJsonArr];
//	                   String[] news_desc_arr = new String[lengthJsonArr];
//	                   String[] mStrings = new String[lengthJsonArr];
//	                   String[] news_bigimg_arr = new String[lengthJsonArr];
	                   
	                   
//	                   news_date_arr = new String[lengthJsonArr];
//	                  news_time_arr = new String[lengthJsonArr];
//	                   news_title_arr = new String[lengthJsonArr];
//	                   news_desc_arr = new String[lengthJsonArr];
//	                   mStrings = new String[lengthJsonArr];
//	                   news_bigimg_arr = new String[lengthJsonArr];
	                   
	                   
//	                   ArrayList<Scores_Category> categoriesList=new ArrayList<Scores_Category>();
	                   
	                   for(int i=0; i < lengthJsonArr; i++) 
	                   {
	                       /****** Get Object for each JSON node.***********/
	                       JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
	                        
	                       /******* Fetch node values **********/
//	                       String news_date       = jsonChildNode.optString("news_date").toString();
//	                       String news_time     = jsonChildNode.optString("news_time").toString();
//	                       String news_title = jsonChildNode.optString("news_title").toString();
//	                       String news_desc = jsonChildNode.optString("news_content").toString();
//	                       String news_img = jsonChildNode.optString("nimg_image").toString();
//	                       String news_bigimg = jsonChildNode.optString("nimg_bigimg").toString();
//	                       String news_bigimg = jsonChildNode.optString("nimg_image").toString();
//	                        emp_id       = jsonChildNode.optInt("emp_id");
//	                        emp_date       = jsonChildNode.optString("emp_date").toString();
	                       
//	                        emp_name = jsonChildNode.optString("emp_name").toString();
//	                        emp_shift = jsonChildNode.optString("emp_shift").toString();
	                        slot_result = jsonChildNode.optString("exitslot_result").toString();
//	                        slot_no = jsonChildNode.optString("slotname").toString();
//	                        floor_no = jsonChildNode.optString("floorname").toString();
	                        
//	                       String news_content = jsonChildNode.optString("news_desc").toString();
//	                       news_img=AppConstant.URLimg+news_img;
//	                       news_bigimg=AppConstant.URLimg+news_bigimg;
//	                       mStrings[i]="http://10.0.2.2/fmard/"+news_qrimg;
//	                       news_bigimg_arr[i]="http://10.0.2.2/fmard/"+news_bigimg;
//	                       OutputData += " Name 		    : "+ news_title +" \n "
//	                                   + "Number 		: "+ news_date +" \n "
//	                                   + "Time 				: "+ news_time +" \n "
//	                                   + "DESC 				: "+ news_desc +" \n "
//	                                   + "Image 				: "+ news_qrimg +" \n "
//	                                   + "Image 				: "+ mStrings[i] +" \n "
//	                                   +"--------------------------------------------------\n";
	                      
	                       //Log.i("JSON parse", song_name);
	                       
	//#######################################################
	                       
//	                       JSONObject catObj = (JSONObject) jsonMainNode.get(i);
//								Scores_Category cat = new Scores_Category(catObj.getInt("news_id"),catObj.getString("news_title"));
//								categoriesList.add(cat);
//	                   	public NewsItem(int id, String date, String title, String image,String bigimage, String desc,String content,Boolean selected)
//	                            NewsItem itm=new NewsItem(news_id,news_date,news_title,news_img,news_bigimg,news_desc,news_content,false);
//	                            resultList.add(itm);
//	                       //######################################################
//	                       Log.d("Jeeeeet", String.valueOf(resultList.size()));
	                       
	                       
	                  }
	                   
	                   
	                   if(slot_result.equalsIgnoreCase("success"))
	                   {
//	                   formatTxt.setText("SHIFT: " +emp_shift +emp_name);
	                   aController.showAlertDialog(MySlotActivity.this,
								"Parking Status:",
							"Thankyou for using the MLCP Parking.", true,MySlotActivity.this);
	                	   
	                	lblFloor.setText("");   
	                	lblSlot.setText("");   
	                	 btnExit.setEnabled(false);  
	                	   
//	                   Toast.makeText(MySlotActivity.this, "Slot booked Successfully! ", Toast.LENGTH_LONG).show();
	                   }
	                   else if(slot_result.equalsIgnoreCase("unsuccess"))
	                   {
	                	   /*aController.showAlertDialog(MySlotActivity.this,
									"Parking Status:",
									"Sorry! No slot is alloted for your vehicle.", false,MySlotActivity.this);*/
//		                   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                 /*  else if(slot_result.equalsIgnoreCase("already"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
	                			   "Booking Status:",
	                			   "You already booked your slot!\n"+"Floor No:  "+	floor_no+"\n "+"Slot No.:   "+slot_no, true,MySlotActivity.this);
//	                	   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("unregister2W"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
	                			   "Booking Status:",
	                			   "You registered for Four Wheeler!" +
	                			   "\nPlease update registration for Two Wheeler!", false,MySlotActivity.this);
//	                	   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("unregister4W"))
	                   {
	                	   aController.showAlertDialog(MySlotActivity.this,
	                			   "Booking Status:",
	                			   "You registered for Two Wheeler!" +
	                			   "\nPlease update registration for Four Wheeler!", false,MySlotActivity.this);
//	                	   Toast.makeText(MySlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }*/
	                   
	                   else
	                   {
//	                	   aController.showAlertDialog(ParkingInfoActivity.this,
//									"Booking Status:",
//									"Sorry for inconvience! PARKING FULL", false);
		                   Toast.makeText(MySlotActivity.this, "Error in fetching parking info! ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	               /****************** End Parse Response JSON Data *************/     
	                   
	                   //Show Parsed Output on screen (activity)
//	                   jsonParsed.setText( OutputData );
	                   
	                   
	                   
	                // Create custom adapter for listview
//	           		adapter = new LazyImageLoadAdapter(News.this, mStrings,news_date_arr,news_time_arr,news_title_arr,news_desc_arr);

	           		// Set adapter to listview
//	           		list.setAdapter(adapter);

	                   
	                   
//	                 -------------------------Fmard-----------------Start
//	                 Intent i=new Intent(News.this,News.class);
//	                 i.putExtra("news_date", news_date_arr);
//	                 i.putExtra("news_time", news_time_arr);
//	                 i.putExtra("news_title", news_title_arr);
//	                 i.putExtra("news_desc", news_desc_arr);
//	                 i.putExtra("img_urls", mStrings);
//	                 i.putExtra("bigimg_urls",news_bigimg_arr);
//	                 
//	                 
//	                 i.putExtra("categories", categoriesList);
//	                 
//	                 startActivity(i);
//	                 -------------------------Fmard--------------End---
	                    }else {
	                   	 Toast.makeText(__context, "Error due to some network problem! Please connect to internet. ", Toast.LENGTH_LONG).show();
						}
	                   
	                    
	               } catch (JSONException e) {          
	                   e.printStackTrace();
//	                   Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();
	               }

	               
	           }
			
	      }
	       
	  }
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
	  
	  
	  
	  
}
