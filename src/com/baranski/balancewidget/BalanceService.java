package com.baranski.balancewidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BalanceService extends RemoteViewsService {
	  @Override
	  public RemoteViewsFactory onGetViewFactory(Intent intent) {
	    return(new BalanceViewFactory(this.getApplicationContext(),intent));
	  }

}
