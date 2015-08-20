package com.tcs.itcsmlcp;






import com.google.android.gcm.GCMRegistrar;

//import com.slidingmenu.lib.SlidingMenu;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
public class MainActivity extends Activity implements OnClickListener,OnItemClickListener {

	// 
	ImageButton btnSettings,btnSearch;
	

private ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		
		
	final DatabaseHandler db = new DatabaseHandler(this);
		
//	Toast.makeText(getApplicationContext(), ""+db.getContactsCount(), Toast.LENGTH_LONG).show();
		if(db.getContactsCount()<=0)
		{
			db.close();
			finish();
			Intent i=new Intent(this,InfoActivity.class);
			startActivity(i);
			
			
		}
		
		
		 progress = new ProgressDialog(this);
		
//		btnSearch = (ImageButton) findViewById(R.id.btnImg_Search);
		btnSettings = (ImageButton) findViewById(R.id.btnImg_Settings);
		
		
		
//		btnSearch.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		
		ListView listView = (ListView) findViewById(R.id.list_colors);
//		String[] values = new String[] { "Green", "Blue", "Lilac", "Red","Orange", "Purple", "Yellow" };
//		String[] values = new String[] { "News", "Newspaper Review", "Events", "HM Events","Content", "Speeches", "Photos","Videos","Media Release","Info Us","Subscribe" };
//		String[] values = new String[] { "The Games", "Kerala 2015", "Schedule", "General Info","Accreditation", "Visit Kerala", "News","Gallery","Tenders","About Us" };
//		String[] values = new String[] { "The Games","Latest News",  "Schedule", "Stadiums","Medal Tally","Predict Your Team", "Visit Kerala" };
		String[] values = new String[] { "MY SLOT",  "MLCP PARKING INFO", "PARKING STATS" };
//		Integer[] images = { R.drawable.lst_green, R.drawable.lst_blue,
//				R.drawable.lst_lilac, R.drawable.lst_red,
//				R.drawable.lst_orange, R.drawable.lst_purple,
//				R.drawable.lst_yellow };
		Integer[] images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher 
//				R.drawable.ico_menu, 
//				R.drawable.ico_menu
				};
//				R.drawable.lst_videos,
//				R.drawable.lst_media_release,R.drawable.lst_contact_us};//,R.drawable.lst_subscribe };
		// Create Color Adapter
		MyColorArrayAdapter adapter = new MyColorArrayAdapter(this, values,
				images);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// Add Listener to handle click on list row
		listView.setOnItemClickListener(this);
		
		
		
		
		
		
	}
	
//	 public void launchRingDialog(View view) {
//		 
//		         final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...", "Downloading Image ...", true);
//		 
//		       ringProgressDialog.setCancelable(true);
//		 
//		         new Thread(new Runnable() {
//		 
//		             @Override
//		 
//		             public void run() {
//		 
//		                try {
//		 
//		                     // Here you should write your time consuming task...
//		 
//		                     // Let the progress ring for 10 seconds...
//		 		                     Thread.sleep(10000);
//		 
//		                 } catch (Exception e) {
//		 
//		  
//		 
//		                 }
//		 
//		                 ringProgressDialog.dismiss();
//		 
//		             }
//		 
//		         }).start();
//		 
//		     }

	
	 public void open(View view){
//	      progress.setMessage("Loading...");
//	      progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//	      progress.setIndeterminate(true);
//	      progress.show();
	      progress=ProgressDialog.show(MainActivity.this, "", "");
			 progress.setContentView(R.layout.progress);
		      progress.show();
	   final int totalProgressTime = 100;

	   final Thread t = new Thread(){

	   @Override
	   public void run(){
	 
	      int jumpTime = 0;
	      while(jumpTime < totalProgressTime){
	         try {
	            sleep(200);
	            jumpTime += 5;
	            progress.setProgress(jumpTime);
	         } catch (InterruptedException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	         }
finally{
	
	progress.dismiss();
}
	      }

	   }
	   };
	   t.start();

	   }
	@Override
	public void onClick(View v) {
//		if (v.getId() == R.id.btnImg_Search){
//			Intent intent = new Intent(this, SearchActivity.class);
//			startActivity(intent);
//		}
		if (v.getId() == R.id.btnImg_Settings){
//			Intent intent = new Intent(this, SettingActivity.class);
//			startActivity(intent);
		}

	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//		p.setVisibility(View.VISIBLE);
//		r.setVisibility(View.GONE);
		open(v);
//		launchRingDialog(v);
	/*	Intent intent = new Intent(this, ParkingInfoActivity_New.class);
		
		
//		Intent intent = new Intent(this,NewsActivity_old.class);
		intent.putExtra("id", v.getId());
		startActivity(intent);*/
//		progress.dismiss();	
		
		switch (position) {
		case 0:
			Intent intent = new Intent(this, MySlotActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent.putExtra("id", v.getId());
			startActivity(intent);
//			progress.dismiss();	
			break;
		case 1:
Intent intent1 = new Intent(this, ParkingInfoActivity_New.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent1.putExtra("id", v.getId());
			startActivity(intent1);
//			String serverURL_newspaper ="http://192.168.1.3/fmard/json_new/newspaper_json.php";
//			String serverURL_newspaper =AppConstant.URL+"newspaper_json.php";
//			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_newspaper().execute(serverURL_newspaper);
//			  Intent i1= new Intent(getApplicationContext(),Newspaper_review.class);
//                // passing array index
////                i.putExtra("id", position);
//                startActivity(i1);
			break;

		case 2:
Intent intent2 = new Intent(this, ParkingStatsActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent2.putExtra("id", v.getId());
			startActivity(intent2);
//			String serverURL_event ="http://192.168.1.7/fmard/json_new/event_json.php";
//			String serverURL_event ="http://www.fmard.gov.ng/json_new/event_json.php";
			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_event().execute(serverURL_event);
//			  Intent i2 = new Intent(getApplicationContext(),Events.class);
//                 passing array index
//                i.putExtra("id", position);
//                startActivity(i2);
			break;
		/*case 3:
			
Intent intent3 = new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent3.putExtra("id", v.getId());
			startActivity(intent3);
//			String serverURL_hmevent ="http://10.0.2.2/fmard/json_new/hmevent_json.php";
//			String serverURL_hmevent =AppConstant.URL+"hmevent_json.php";
			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_hmevent().execute(serverURL_hmevent);
//			  Intent i3 = new Intent(getApplicationContext(),Hm_events.class);
//                // passing array index
////                i.putExtra("id", position);
//                startActivity(i3);
			break;
		case 4:
Intent intent4 = new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent4.putExtra("id", v.getId());
			startActivity(intent4);
//			String serverURL_content =AppConstant.URL+"content_json.php";
//			String serverURL_content ="http://10.0.2.2/fmard/json_new/content_json.php";
			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_content().execute(serverURL_content);
//			  Intent i4 = new Intent(getApplicationContext(),Content.class);
                // passing array index
//                i.putExtra("id", position);
//                startActivity(i4);
			break;
		case 5:
Intent intent5 = new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent5.putExtra("id", v.getId());
			startActivity(intent5);
//			String serverURL_speeches ="http://10.0.2.2/fmard/json_new/speeches_json.php";
////			String serverURL_speeches ="http://www.fmard.gov.ng/json_new/news_json.php";
//			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_speeches().execute(serverURL_speeches);
//			  Intent i5 = new Intent(getApplicationContext(),Speeches.class);
                // passing array index
//                i.putExtra("id", position);
//                startActivity(i5);
			break;
		case 6:
Intent intent6= new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent6.putExtra("id", v.getId());
			startActivity(intent6);
//			String serverURL_photos ="http://10.0.2.2/fmard/json/photo_gallery_json.php";
//			String serverURL_photos ="http://10.0.2.2/fmard/json_new/photo_gallery_json.php";
//			String serverURL_photos =AppConstant.URL+"photo_gallery_json.php";
			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_photos().execute(serverURL_photos);
//			  Intent i6 = new Intent(getApplicationContext(),Photos.class);
                // passing array index
//                i.putExtra("id", position);
//                startActivity(i6);
			break;
		case 7:
Intent intent7 = new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent7.putExtra("id", v.getId());
			startActivity(intent7);
//			String serverURL_videos ="http://10.0.2.2/fmard/json/video_gallery_json.php";
//			String serverURL_videos =AppConstant.URL+"video_gallery_json.php";
			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_videos().execute(serverURL_videos);
//			  Intent i7 = new Intent(getApplicationContext(),Videos.class);
                // passing array index
//                i.putExtra("id", position);
//                startActivity(i7);
			break;
		case 8:
Intent intent8 = new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent8.putExtra("id", v.getId());
			startActivity(intent8);
//			String serverURL_media ="http://10.0.2.2/fmard/json_new/media_json.php";
//			String serverURL_media =AppConstant.URL+"media_json.php";
//			// Use AsyncTask execute Method To Prevent ANR Problem
//	        new LongOperation_media().execute(serverURL_media);
//			  Intent i8 = new Intent(getApplicationContext(),Media_release.class);
//                // passing array index
////                i.putExtra("id", position);
//                startActivity(i8);
			break;
		case 9:
Intent intent9 = new Intent(this, SubActivity.class);
			
			
//			Intent intent = new Intent(this,NewsActivity_old.class);
			intent9.putExtra("id", v.getId());
			startActivity(intent9);
//			  Intent i9 = new Intent(getApplicationContext(),Contact_us.class);
                // passing array index
//                i.putExtra("id", position);
//                startActivity(i9);
			break;		
		case 10:
			  Intent i10 = new Intent(getApplicationContext(),RegisterActivity.class);
                // passing array index
//                i.putExtra("id", position);
                startActivity(i10);
			break;		*/
		default:
			break;
		}
		
	}
	
	
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
	        return true;
	    }
	     
	    /**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	        case R.id.signout:
	            // Single menu item is selected do something
	            // Ex: launching new activity/screen or show alert message
	            //Toast.makeText(AndroidMenusActivity.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
	        	 try {
	             	//GCMRegistrar.unregister(MainActivity.this);
	             	DatabaseHandler db = new DatabaseHandler(MainActivity.this);
	             	db.dropTable();
	             	finish();
	             	Toast.makeText(getApplicationContext(), "signout", Toast.LENGTH_LONG).show();
	             } 
	             catch (Exception e) {     
	             System.out.println("Error Message: " + e.getMessage());
	             }
	        	return true;
	 
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	

}
