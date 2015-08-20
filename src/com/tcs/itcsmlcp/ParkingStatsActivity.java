package com.tcs.itcsmlcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ParkingStatsActivity extends Activity {

	
	String serverURL_bookslot =AppConstant.URL+"parkingstats_json.php";
	 MyApp aController;
	 private ProgressDialog progress;
	 String[] values,values1;
	 Integer[]  images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher};
	 ListView listView;
	 
	 ImageButton btnBack,btnRefresh;
//	 TextView cars,slots_cars,bikes,slots_bikes;
	 TextView lblHead;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_info_new);
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		
		lblHead=(TextView)findViewById(R.id.text_price);
		lblHead.setText("PARKING STATS");
		
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new LongOperation_bookslot(serverURL_bookslot,ParkingStatsActivity.this).execute("");
			}
		});
		 aController = (MyApp) getApplicationContext();
		 
		 /*cars=(TextView)findViewById(R.id.cars);
		 slots_cars=(TextView)findViewById(R.id.slots_cars);
		 bikes=(TextView)findViewById(R.id.bikes);
		 slots_bikes=(TextView)findViewById(R.id.slots_bikes);*/
		 
		 
		  listView = (ListView) findViewById(R.id.list_colors);
//			String[] values = new String[] { "Green", "Blue", "Lilac", "Red","Orange", "Purple", "Yellow" };
//			String[] values = new String[] { "News", "Newspaper Review", "Events", "HM Events","Content", "Speeches", "Photos","Videos","Media Release","Contact Us","Subscribe" };
//			String[] values = new String[] { "The Games", "Kerala 2015", "Schedule", "General Info","Accreditation", "Visit Kerala", "News","Gallery","Tenders","About Us" };
//			String[] values = new String[] { "The Games","Latest News",  "Schedule", "Stadiums","Medal Tally","Predict Your Team", "Visit Kerala" };
			 values = new String[] { "Yesterday Busy Traffic Time:",  "Slots almost full by:"};//, "No. of Two Wheeler Parked:","Available slots for Two Wheeler Parking:"};
//			Integer[] images = { R.drawable.lst_green, R.drawable.lst_blue,
//					R.drawable.lst_lilac, R.drawable.lst_red,
//					R.drawable.lst_orange, R.drawable.lst_purple,
//					R.drawable.lst_yellow };
//			 images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
//					R.drawable.ic_launcher, R.drawable.ic_launcher};
//					R.drawable.lst_medal, 
//					R.drawable.lst_photos};
//					R.drawable.lst_videos,
//					R.drawable.lst_media_release,R.drawable.lst_contact_us};//,R.drawable.lst_subscribe };
			// Create Color Adapter
			/*MyColorArrayAdapter adapter = new MyColorArrayAdapter(this, values,
					images);

			// Assign adapter to ListView
			listView.setAdapter(adapter);
*/
			// Add Listener to handle click on list row
//			listView.setOnItemClickListener(this);

		 
		 
		 
		 
//		aController = (MyApp) getApplicationContext();
			// Check if Internet Connection present
			if (!aController.isConnectingToInternet()) {
				// Internet Connection is not present
				aController.showAlertDialog(ParkingStatsActivity.this,
						"Internet Connection Error",
						"Please connect to working Internet connection", false,ParkingStatsActivity.this);

						// stop executing code by return
				
			   return;
			 }
		
			else{
		
new LongOperation_bookslot(serverURL_bookslot,this).execute("");
			}
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
		 // Class with extends AsyncTask class
	  private class LongOperation_bookslot  extends AsyncTask<String, Void,String> {
	       String _url,_slotdate,_empimei;
	       Activity __context;
	       
	       HashMap<String,Integer> _al;
	       
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
	    	  progress=ProgressDialog.show(ParkingStatsActivity.this, "", "");
				 progress.setContentView(R.layout.progress);
			      progress.show();
	    	  
	    	  _al=new HashMap<String,Integer>();
	    	  
	    	  
//				} 
	          
//	    	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	    	  Date date = new Date();
//	    	  _slotdate=dateFormat.format(date);
	    	  Calendar cal = Calendar.getInstance();
	          cal.add(Calendar.DATE, -1);    
	          _slotdate= dateFormat.format(cal.getTime());
	    	  
	    	  
	    	  
//	    	 	Toast.makeText(__context, _slotdate, Toast.LENGTH_LONG).show();
	    	  // GET IMEI NUMBER      
				/* TelephonyManager tManager = (TelephonyManager) getBaseContext()
				    .getSystemService(Context.TELEPHONY_SERVICE);
				   _empimei = tManager.getDeviceId();*/
	    	  
	    	  
	    	  
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
//		                  data += "&" + URLEncoder.encode("empshift", "UTF-8") + "=" + URLEncoder.encode(_empshift, "UTF-8");
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
	        		 Toast.makeText(__context, "Error due to some network problem! Please connect to internet. ", Toast.LENGTH_LONG).show();
	               
	          } else {
	            
	          	// Show Response Json On Screen (activity)
//	          	uiUpdate.setText( Content );
	          	
	           /****************** Start Parse Response JSON Data *************/
	        	  
	              
	              
	             String slot_result="";int slot_830=0,slot_930=0,slot_1030=0;//,slot_no_bikes=0,bike_no=0;
	        	  
	        	  
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
	                        slot_result = jsonChildNode.optString("space_result").toString();
	                        slot_830 = jsonChildNode.optInt("tot_1");
	                        slot_930 = jsonChildNode.optInt("tot_2");
	                        slot_1030 = jsonChildNode.optInt("tot_3");
	                        /*_al.put("08:30-09:30",slot_830);
	                        _al.put("09:30-10:30",slot_930);
	                        _al.put("10:30-11:30",slot_1030);*/
	                        
//	                        bike_no = jsonChildNode.optInt("tot_bikes");
	                        
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
//	                   aController.showAlertDialog(ParkingInfoActivity.this,
//								"Booking Status:",
//							"Floor No:  "+	floor_no+"\n "+"Slot No.:   "+slot_no, true);
	                	   
	                	  /* cars.setText(String.valueOf(car_no));
	                	   slots_cars.setText(String.valueOf(slot_no_cars-car_no));
	                	   bikes.setText(String.valueOf(bike_no));
	                	   slots_bikes.setText(String.valueOf(slot_no_bikes-bike_no));*/
//	                	   int x=Collections.max(_al.)
	               	 
	           /*    	 Entry<String,Integer> maxEntry = null;

	               	for(Entry<String,Integer> entry : _al.entrySet()) {
	               	    if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
	               	        maxEntry = entry;
	               	    }
	               	}
	            	Toast.makeText(__context, maxEntry.getKey(), Toast.LENGTH_LONG).show();  */
	                	   values1=new String[2];
	                	   if ( slot_830 > slot_930 && slot_830 > slot_1030 )
	                	         {
	                		   values1[0] = "08:30-09:30";
//		                        values1[1] = "08:30-09:30";
	                	         }
	                	      else if ( slot_930 > slot_830 && slot_930 > slot_1030 )
	                	      {
		                		   values1[0] = "09:30-10:30";
//			                        values1[1] = "09:30-10:30";
		                	         }
	                	      else if ( slot_1030 > slot_830 && slot_1030 > slot_930 )
	                	      {
		                		   values1[0] = "10:30-11:30";
//			                        values1[1] = "10:30-11:30";
		                	         }
	                	      else   
	                	         {
	                	    	  {
	   	                		   values1[0] = "NA";
//	   		                        values1[1] = "NA";
	   	                	         }
	                	         }
	                	   int smallest = slot_830;
	                	   values1[1] = "08:30-09:30";
	                	   if (smallest > slot_930) 
	                		   {smallest = slot_930;
	                		   values1[1] = "09:30-10:30";
	                		   }
	                	   if (smallest > slot_1030) 
	                		   {smallest = slot_1030;
	                		   values1[1] = "10:30-11:30";
	                		   }
//	                	   values1[0] = String.valueOf(car_no);
//	                        values1[1] = String.valueOf(slot_no_cars-car_no);
//	                        values1[2] = String.valueOf(bike_no);
//	                        values1[3] = String.valueOf(slot_no_bikes-bike_no);
	                	   
	                	   
	                	   
	                	   ParkingInfoColorArrayAdapter adapter = new ParkingInfoColorArrayAdapter(ParkingStatsActivity.this, values,values1,images);

	           			// Assign adapter to ListView
	           			listView.setAdapter(adapter);

	                	   
	                	   
	                	   
	                	   
//	                   Toast.makeText(ParkingInfoActivity.this, "Slot booked Successfully! ", Toast.LENGTH_LONG).show();
	                   }
	                   else
	                   {
//	                	   aController.showAlertDialog(ParkingInfoActivity.this,
//									"Booking Status:",
//									"Sorry for inconvience! PARKING FULL", false);
		                   Toast.makeText(ParkingStatsActivity.this, "Error in fetching parking info! ", Toast.LENGTH_LONG).show();
	                	   
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
