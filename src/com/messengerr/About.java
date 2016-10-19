package com.messengerr;

import com.actionbarsherlock.app.*;
import android.view.*;
import android.content.*;
import android.net.*;
import android.os.*;

public class About extends SherlockActivity
{
    public void fbClick(final View view) {
        final Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://www.facebook.com/BLAKKY7799"));
        this.startActivity(intent);
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.aboutus);
        this.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.actionbar_background));
    }
    
   /* public void rateClick(final View view) {
        final Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.getPackageName()));
        intent.addFlags(1208483840);
        this.startActivity(intent);
    }*/
}
