package com.messengerr;

import android.text.*;

public class Messages
{
    boolean isMine;
    boolean isSticker;
    Spannable message;
    
    public Messages(final Spannable message, final boolean isMine, final boolean isSticker) {
        super();
        this.message = message;
        this.isMine = isMine;
        this.isSticker = isSticker;
    }
    
    public Spannable getMessage() {
        return this.message;
    }
    
    public boolean isMine() {
        return this.isMine;
    }
    
    public boolean isSticker() {
        return this.isSticker;
    }
    
    public void setMessage(final Spannable message) {
        this.message = message;
    }
    
    public void setMine(final boolean isMine) {
        this.isMine = isMine;
    }
}
