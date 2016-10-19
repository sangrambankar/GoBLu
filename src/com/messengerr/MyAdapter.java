package com.messengerr;

import android.content.*;
import java.util.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;

public class MyAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Messages> messages;
    
    MyAdapter(final Context context, final ArrayList<Messages> messages) {
        super();
        this.context = context;
        this.messages = messages;
    }
    
    public int getCount() {
        return this.messages.size();
    }
    
    public Object getItem(final int n) {
        return this.messages.get(n);
    }
    
    public long getItemId(final int n) {
        return 0L;
    }
    
    public View getView(final int n, final View view, final ViewGroup viewGroup) {
        final Preferences preferences = new Preferences(this.context);
        final int bubble = preferences.getBubble();
        final int font = preferences.getFont();
        final Messages message = (Messages)this.getItem(n);
        View inflate = view;
        ViewHolder tag;
        if (inflate == null) {
            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
            inflate = LayoutInflater.from(this.context).inflate(R.layout.message, viewGroup, false);
            tag = new ViewHolder();
            tag.t = (TextView)inflate.findViewById(R.id.text1);
            inflate.setTag((Object)tag);
        }
        else {
            tag = (ViewHolder)inflate.getTag();
        }
        final LinearLayout.LayoutParams linearLayout$LayoutParams = (LinearLayout.LayoutParams)tag.t.getLayoutParams();
        if (!message.isMine()) {
            linearLayout$LayoutParams.gravity = 3;
            if (font == 0) {
                tag.t.setTypeface((Typeface)null);
            }
            else if (font == 1) {
                tag.t.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "fonts/42938.ttf"));
                tag.t.setTextSize(23.0f);
            }
            else if (font == 2) {
                tag.t.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "fonts/AnnabelScript.ttf"));
                tag.t.setTextSize(23.0f);
            }
            else if (font == 3) {
                tag.t.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "fonts/BaroqueScript.ttf"));
            }
            else if (font == 4) {
                tag.t.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "fonts/BatFont.ttf"));
                tag.t.setTextSize(22.0f);
            }
            if (bubble == 0) {
                tag.t.setBackgroundResource(R.drawable.bubbleu);
            }
            else if (bubble == 1) {
                tag.t.setBackgroundResource(R.drawable.green_bubble);
            }
            else if (bubble == 2) {
                tag.t.setBackgroundResource(R.drawable.me);
            }
            if (message.isSticker) {
                tag.t.setBackgroundResource(0);
            }
            tag.t.setText((CharSequence)message.getMessage());
            return inflate;
        }
        linearLayout$LayoutParams.gravity = 5;
        if (bubble == 0) {
            tag.t.setBackgroundResource(R.drawable.bubbleme);
        }
        else if (bubble == 1) {
            tag.t.setBackgroundResource(R.drawable.gray_bubble);
        }
        else if (bubble == 2) {
            tag.t.setBackgroundResource(R.drawable.you);
        }
        if (message.isSticker) {
            tag.t.setBackgroundResource(0);
        }
        tag.t.setText(message.getMessage());
        tag.t.setTextColor(Color.parseColor("#000000"));
        return inflate;
    }
}
