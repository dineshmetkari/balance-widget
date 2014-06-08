package com.baranski.balancewidget;

import com.baranski.balacnewidget.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity{
	
	private static final String AD_UNIT_ID = "ca-app-pub-7481407618707880/2701235257";
	private AdView adView;
	
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_activity);
	    
	    adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);
		 
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		layout.addView(adView);
		
		 TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		 String deviceid = tm.getDeviceId();

		 AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(deviceid)
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .build();
	    
	    adView.loadAd(adRequest);
	    

	    Intent intent = new Intent(this, ConfigurationActivity.class);
	    startActivity(intent);
	   
	    
	    
	 }

}
