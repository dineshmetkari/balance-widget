package com.baranski.balancewidget;

import com.baranski.balacnewidget.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class ConfigurationActivity extends PreferenceActivity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
	}
	
	

}
