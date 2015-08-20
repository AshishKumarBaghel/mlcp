package com.tcs.itcsmlcp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressLint("NewApi")
public class MyApp extends Application {

	private static MyApp instance;

	public MyApp() {
		super();
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Utils.loadFonts();
	}

	public static MyApp getApp() {
		return instance;
	}

	public static Context getContext() {
		return instance;
	}

	@Override
	public void onTerminate() {

		super.onTerminate();
	}

	
	// Checking for all possible internet providers
    public boolean isConnectingToInternet(){
    	
        ConnectivityManager connectivity = 
        	                 (ConnectivityManager) getSystemService(
        	                  Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }

  //Function to display simple Alert Dialog
    public void showAlertDialog(Context context, String title, String message,
 			Boolean status,final Activity a) {
 		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
alertDialog.setCanceledOnTouchOutside(false);
//a.setFinishOnTouchOutside(true);
 		// Set Dialog Title
 		alertDialog.setTitle(title);

 		// Set Dialog Message
 		alertDialog.setMessage(message);

 		if(status != null)
 			// Set alert dialog icon
 			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

 		// Set OK Button
 		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
 			public void onClick(DialogInterface dialog, int which) {
//             a.recreate(); 	
 				a.finish();
 			}
 		});

 		// Show Alert Message
 		alertDialog.show();
 	}

	
}
