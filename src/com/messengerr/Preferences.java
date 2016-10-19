package com.messengerr;

import android.content.*;

public class Preferences
{
    SharedPreferences.Editor edit;
    SharedPreferences pref;
    
    public Preferences(final Context context) {
        super();
        this.pref = context.getSharedPreferences("test", 0);
        this.edit = this.pref.edit();
    }
    
    public int getBubble() {
        return this.pref.getInt("bubble", 0);
    }
    
    public int getFont() {
        return this.pref.getInt("Font", 0);
    }
    
    public String getPreference() {
        return this.pref.getString("bg", "0");
    }
    
    public boolean getRate() {
        return this.pref.getBoolean("dontshowagain", false);
    }
    
    public void setBubble(final int n) {
        this.edit.putInt("bubble", n);
        this.edit.commit();
    }
    
    public void setFont(final int n) {
        this.edit.putInt("Font", n);
        this.edit.commit();
    }
    
    public void setPreference(final String s) {
        this.edit.putString("bg", s);
        this.edit.commit();
    }
    
    public void setRate(final boolean b) {
        this.edit.putBoolean("dontshowagain", b);
        this.edit.commit();
    }
}
