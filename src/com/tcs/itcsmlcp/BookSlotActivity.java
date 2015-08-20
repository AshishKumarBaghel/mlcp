package com.tcs.itcsmlcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

public class BookSlotActivity extends Activity {

	
	String serverURL_bookslot =AppConstant.URL+"bookslot_json.php";
	 MyApp aController;
	 private ProgressDialog progress;
	 
	 String vtype;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_slot);
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		 aController = (MyApp) getApplicationContext();
//		aController = (MyApp) getApplicationContext();
			// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {
				// Internet Connection is not present
				aController.showAlertDialog(BookSlotActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false,BookSlotActivity.this);

						// stop executing code by return
				finish();
//			   return;
			   
			 
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
		
				Intent iget=getIntent();
				vtype=iget.getStringExtra("vtype");
				
new LongOperation_bookslot(serverURL_bookslot,this).execute("");
			}
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
		 // Class with extends AsyncTask class
	  private class LongOperation_bookslot  extends AsyncTask<String, Void,String> {
	       String _url,_slotdate,_empimei;
	       Activity __context;
	       
	       
	     
	//System.out.print(dateFormat.format(date)); //2014/08/06 15:59:48

	       
	  	// Required initialization
	  	public LongOperation_bookslot(String url,Activity c)
	  	{
	  		_url=url;
	  		__context=c;
	  		
	  		
	  		
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
	    	  progress=ProgressDialog.show(BookSlotActivity.this, "", "");
				 progress.setContentView(R.layout.progress);
			      progress.show();
	    	  
	    	  
	    	  
	    	  
//				} 
	          
	    	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    	  Date date = new Date();
	    	  _slotdate=dateFormat.format(date);
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
		                  data += "&" + URLEncoder.encode("empimei", "UTF-8") + "=" + URLEncoder.encode(_empimei, "UTF-8"); 
		                  data += "&" + URLEncoder.encode("vtype", "UTF-8") + "=" + URLEncoder.encode(vtype, "UTF-8");
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
	          	Toast.makeText(__context, "Error"+Error, Toast.LENGTH_LONG).show();
	               
	          } else {
	            
	          	// Show Response Json On Screen (activity)
//	          	uiUpdate.setText( Content );
	          	
	           /****************** Start Parse Response JSON Data *************/
	        	  
	              
	              
	             String slot_result="",slot_no="",floor_no="";
	        	  
	        	  
	          	String OutputData = "";
	              JSONObject jsonResponse;
//	              Toast.makeText(_context, "Error"+Content, Toast.LENGTH_LONG).show();
	              try {
	                    
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
	                        slot_result = jsonChildNode.optString("slot_result").toString();
	                        slot_no = jsonChildNode.optString("slot_no").toString();
	                        floor_no = jsonChildNode.optString("floor_no").toString();
	                        
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
	                   aController.showAlertDialog(BookSlotActivity.this,
								"Booking Status:",
							"Slot Booked Successfully!\n"+"Floor No:  "+	floor_no+"\n "+"Slot No.:   "+slot_no, true,BookSlotActivity.this);
	                   Toast.makeText(BookSlotActivity.this, "Slot booked Successfully! ", Toast.LENGTH_LONG).show();
	                   }
	                   else if(slot_result.equalsIgnoreCase("unsuccess"))
	                   {
	                	   aController.showAlertDialog(BookSlotActivity.this,
									"Booking Status:",
									"Sorry for inconvience! \n PARKING FULL", false,BookSlotActivity.this);
		                   Toast.makeText(BookSlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("already"))
	                   {
	                	   aController.showAlertDialog(BookSlotActivity.this,
	                			   "Booking Status:",
	                			   "You already booked your slot!\n"+"Floor No:  "+	floor_no+"\n "+"Slot No.:   "+slot_no, true,BookSlotActivity.this);
//	                	   Toast.makeText(BookSlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("unregister2W"))
	                   {
	                	   aController.showAlertDialog(BookSlotActivity.this,
	                			   "Booking Status:",
	                			   "You registered for Four Wheeler!" +
	                			   "\nPlease update registration for Two Wheeler!", false,BookSlotActivity.this);
//	                	   Toast.makeText(BookSlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
	                   }
	                   else if(slot_result.equalsIgnoreCase("unregister4W"))
	                   {
	                	   aController.showAlertDialog(BookSlotActivity.this,
	                			   "Booking Status:",
	                			   "You registered for Two Wheeler!" +
	                			   "\nPlease update registration for Four Wheeler!", false,BookSlotActivity.this);
//	                	   Toast.makeText(BookSlotActivity.this, "PARKING FULL ", Toast.LENGTH_LONG).show();
	                	   
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
	                   
	                   
	                    
	               } catch (JSONException e) {          
	                   e.printStackTrace();
//	                   Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();
	               }

	               
	           }
			
	      }
	       
	  }
		//+++++++++++++++++++++++++++++++++++++++++++++++++++

	
}
