package com.messengerr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.messengerr.R;

public class BluetoothChat extends SherlockActivity {
	private static final boolean D = true;
    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_WRITE = 3;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;
    private static final int SELECT_PICTURE = 6;
    private static final int SEND_AUDIO = 9;
    private static final int SEND_PICTURE = 7;
    private static final int SEND_VIDEO = 8;
    private static final String TAG = "BluetoothChat";
    public static final String TOAST = "toast";
    int actionBarHeight;
    private ContactPagerAdapter adapter;
    AlertDialog alert;
    private ArrayList<String> arrayList;
    ImageButton b;
    ImageButton b1;
    ImageButton b2;
    String bg;
    int c;
    int c1;
    int count;
    int e;
    private HashMap<String, Integer> emoticons;
    private String filemanagerstring;
    GridView gridView1;
    GridView gridView2;
    int heightDifferenc;
    int heightDifference;
    InputMethodManager imm;
    boolean isKbd;
    boolean isKbdActive;
    int keyBoardSize;
    LinearLayout lay;
    View layout;
    boolean layvisible;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothChatService mChatService;
    private String mConnectedDeviceName;
    public Context mContext;
    private MyAdapter mConversationArrayAdapter;
    private ListView mConversationView;
    private Handler mHandler;
    private EditText mOutEditText;
    private StringBuffer mOutStringBuffer;
    ImageButton mSendButton;
    private TextWatcher mWatcher;
    private TextView.OnEditorActionListener mWriteListener;
    ArrayList<Messages> messages;
    RelativeLayout myLayout;
    Intent nintent;
    NotificationManager nm;
    NotificationManager notificationManager;
    BroadcastReceiver notify;
    boolean once;
    boolean onpause;
    private ArrayList<String> oshastickerList;
    private ArrayList<String> oshasticker_keyList;
    HashMap<String, Integer> oshastickerkey;
    private HashMap<String, Integer> oshastickers;
    private ViewPager pager;
    private ArrayList<String> pikachustickerList;
    private ArrayList<String> pikachusticker_keyList;
    HashMap<String, Integer> pikachustickerkey;
    private HashMap<String, Integer> pikachustickers;
    PendingIntent pintent;
    HashMap<String, Integer> pokestickerkey;
    private HashMap<String, Integer> pokestickers;
    PopupWindow pop;
    PopupWindow popattach;
    int pos;
    Preferences pref;
    String read;
    String readMessage;
    int screenHeight;
    private String selectedImagePath;
    private String sendingImagePath;
    int statusBarSize;
    private ArrayList<String> stickerList;
    ArrayList<String> sticker_keyList;
    private PagerSlidingTabStrip tabs;
    TextView title;
    TextView toast;
    int typ;
    boolean typing;
    BroadcastReceiver typingReciever;
    View v;
    WebView w;
    int x;
    int y;
    
    public BluetoothChat() {
        super();
        this.alert = null;
        this.messages = new ArrayList<Messages>();
        this.mConnectedDeviceName = null;
        this.mBluetoothAdapter = null;
        this.mChatService = null;
        this.emoticons = new HashMap<String, Integer>();
        this.arrayList = new ArrayList<String>();
        this.pokestickers = new HashMap<String, Integer>();
        this.stickerList = new ArrayList<String>();
        this.pikachustickers = new HashMap<String, Integer>();
        this.pikachustickerList = new ArrayList<String>();
        this.oshastickers = new HashMap<String, Integer>();
        this.oshastickerList = new ArrayList<String>();
        this.pokestickerkey = new HashMap<String, Integer>();
        this.sticker_keyList = new ArrayList<String>();
        this.pikachusticker_keyList = new ArrayList<String>();
        this.pikachustickerkey = new HashMap<String, Integer>();
        this.oshastickerkey = new HashMap<String, Integer>();
        this.oshasticker_keyList = new ArrayList<String>();
        this.c = 0;
        this.c1 = 0;
        this.typing = false;
        this.mWatcher = (TextWatcher)new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
                if (editable.length() > 0) {
                    mSendButton.setEnabled(true);
                    mSendButton.setImageResource(R.drawable.ic_send_holo_light);
                    return;
                }
                mSendButton.setEnabled(false);
                mSendButton.setImageResource(R.drawable.ic_send_disabled_holo_light);
                sendBroadcast(new Intent().setAction("TYPING"));
            }
            
            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                if (charSequence.length() > 0) {
                    mSendButton.setEnabled(true);
                    mSendButton.setImageResource(R.drawable.ic_send_holo_light);
                    return;
                }
                mSendButton.setEnabled(false);
                mSendButton.setImageResource(R.drawable.ic_send_disabled_holo_light);
            }
            
            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                if (charSequence.length() > 0) {
                    sendBroadcast(new Intent().setAction("TYPING"));
                    mSendButton.setEnabled(true);
                    mSendButton.setImageResource(R.drawable.ic_send_holo_light);
                    return;
                }
                sendBroadcast(new Intent().setAction("TYPING"));
                mSendButton.setEnabled(false);
                mSendButton.setImageResource(R.drawable.ic_send_disabled_holo_light);
            }
        };
        this.once = true;
        this.count = 0;
        this.statusBarSize = 0;
        this.keyBoardSize = 0;
        this.isKbdActive = false;
        this.layvisible = false;
        this.e = 0;
        this.isKbd = true;
        this.onpause = false;
        this.mWriteListener = (TextView.OnEditorActionListener)new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				 if (actionId == 0 && event.getAction() == 1) {
	                    sendMessage(v.getText().toString());
	                }
	                Log.i("BluetoothChat", "END onEditorAction");
	                return true;
			}
           
        };
        
     // The Handler that gets information back from the BluetoothChatService
          mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                    case BluetoothChatService.STATE_CONNECTED:
                    	  setStatus(String.valueOf(mConnectedDeviceName) + ":" + getString(R.string.title_connected_to));
                          pop.dismiss();
                        break;
                    case BluetoothChatService.STATE_CONNECTING:
                        setStatus(String.valueOf(getString(R.string.title_connecting)));
                        break;
                    case BluetoothChatService.STATE_LISTEN:
                    case BluetoothChatService.STATE_NONE:
                        setStatus(R.string.title_not_connected);
                        break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                   // String s = new String(writeBuf);
                    final String s = new String(writeBuf, 0,writeBuf.length-2);
                    
                    if (s.contains("sticker_")) {
                        messages.add(new Messages(getPokeStickerText(BluetoothChat.this, s), true, true));
                    }
                    else if (s.contains("sticker1_")) {
                        messages.add(new Messages(getPikachuStickerText(BluetoothChat.this, s), true, true));
                    }
                    else if (s.contains("sticker2_")) {
                        messages.add(new Messages(getOshaStickerText(BluetoothChat.this, s), true, true));
                    }
                    else if (!(s.equals("is_typing") || s.equals("is_typed"))) {
                        messages.add(new Messages(getSmiledText(BluetoothChat.this, s), true, false));
                    }
                    
                    
                    mConversationArrayAdapter.notifyDataSetChanged();
                    
                    
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1-2);
                    Log.i("MSG READ",""+readMessage);
                    if (readMessage.contains("sticker_")) {
                        messages.add(new Messages(getPokeStickerText(BluetoothChat.this, readMessage), false, true));
                        read = "sent a sticker";
                        if (onpause) {
                            sendBroadcast(new Intent("Message"));
                        }
                    }
                    else if (readMessage.contains("sticker1_")) {
                        messages.add(new Messages(getPikachuStickerText(BluetoothChat.this, readMessage), false, true));
                        read = "sent a sticker";
                        if (onpause) {
                            sendBroadcast(new Intent("Message"));
                        }
                    }
                    else if (readMessage.contains("sticker2_")) {
                        messages.add(new Messages(getOshaStickerText(BluetoothChat.this, readMessage), false, true));
                        read = "sent a sticker";
                        if (onpause) {
                            sendBroadcast(new Intent("Message"));
                        }
                    }
                    else if (readMessage.equals("is_typing")) {
                        setStatus(getString(R.string.typing));
                    }
                    else if (readMessage.equals("is_typed")) {
                        setStatus(String.valueOf(mConnectedDeviceName) + ":" + getString(R.string.title_connected_to));
                    }
                    else {
                        messages.add(new Messages(getSmiledText(BluetoothChat.this, readMessage), false, false));
                        read = readMessage;
                        if (onpause) {
                            sendBroadcast(new Intent("Message"));
                        }
                    }
                    mConversationArrayAdapter.notifyDataSetChanged();
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                	  BluetoothChat.access$5(BluetoothChat.this, msg.getData().getString("device_name"));
                      setStatus(String.valueOf(mConnectedDeviceName) + ":" + getString(R.string.title_connected_to));
                	  Toast.makeText(getApplicationContext(), ("Connected to " + mConnectedDeviceName), 0).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                                   Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        };

       /* mHandler = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {}
                    case 1: {
                        Log.i("BluetoothChat", "MESSAGE_STATE_CHANGE: " + message.arg1);
                        switch (message.arg1) {
                            default: {
                                break;
                            }
                            case 0:
                            case 1: {
                                setStatus(R.string.title_not_connected);
                                break;
                            }
                            case 3: {
                                setStatus(String.valueOf(mConnectedDeviceName) + ":" + getString(R.string.title_connected_to));
                                pop.dismiss();
                                break;
                            }
                            case 2: {
                                setStatus(String.valueOf(getString(R.string.title_connecting)));
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        byte[] array = (byte[])message.obj;
                        String s = new String(array);
                        if (s.contains("sticker_")) {
                            messages.add(new Messages(getPokeStickerText(BluetoothChat.this, s), true, true));
                        }
                        else if (s.contains("sticker1_")) {
                            messages.add(new Messages(getPikachuStickerText(BluetoothChat.this, s), true, true));
                        }
                        else if (s.contains("sticker2_")) {
                            messages.add(new Messages(getOshaStickerText(BluetoothChat.this, s), true, true));
                        }
                        else if (!s.equals("is_typing") && !s.equals("is_typed")) {
                            messages.add(new Messages(getSmiledText(BluetoothChat.this, s), true, false));
                        }
                        mConversationArrayAdapter.notifyDataSetChanged();
                    }
                    case 2: {
                    	byte[] array = (byte[])message.obj;
                        String readMessage = new String(array, 0,message.arg1);
                        if (readMessage.contains("sticker_")) {
                            messages.add(new Messages(getPokeStickerText(BluetoothChat.this, readMessage), false, true));
                            read = "sent a sticker";
                            if (onpause) {
                                sendBroadcast(new Intent("Message"));
                            }
                        }
                        else if (readMessage.contains("sticker1_")) {
                            messages.add(new Messages(getPikachuStickerText(BluetoothChat.this, readMessage), false, true));
                            read = "sent a sticker";
                            if (onpause) {
                                sendBroadcast(new Intent("Message"));
                            }
                        }
                        else if (readMessage.contains("sticker2_")) {
                            messages.add(new Messages(getOshaStickerText(BluetoothChat.this, readMessage), false, true));
                            read = "sent a sticker";
                            if (onpause) {
                                sendBroadcast(new Intent("Message"));
                            }
                        }
                        else if (readMessage.equals("is_typing")) {
                            setStatus(getString(R.string.typing));
                        }
                        else if (readMessage.equals("is_typed")) {
                            setStatus(String.valueOf(mConnectedDeviceName) + ":" + getString(R.string.title_connected_to));
                        }
                        else {
                            messages.add(new Messages(getSmiledText(BluetoothChat.this, readMessage), false, false));
                            read = readMessage;
                            if (onpause) {
                                sendBroadcast(new Intent("Message"));
                            }
                        }
                        mConversationArrayAdapter.notifyDataSetChanged();
                    }
                    case 4: {
                        BluetoothChat.access$5(BluetoothChat.this, message.getData().getString("device_name"));
                        Toast.makeText(getApplicationContext(), ("Connected to " + mConnectedDeviceName), 0).show();
                    }
                    case 5: {
                        Toast.makeText(getApplicationContext(), message.getData().getString("toast"), 0).show();
                    }
                }
            }
        };*/
        this.typ = 0;
        this.typingReciever = new BroadcastReceiver() {
            public void onReceive(final Context context, final Intent intent) {
                if (mSendButton.isEnabled() && typ != 1) {
                    typ = 1;
                    sendMessage("is_typing");
                }
                else if (!mSendButton.isEnabled() && typ != 0) {
                    typ = 0;
                    sendMessage("is_typed");
                }
            }
        };
        this.notify = new BroadcastReceiver() {
            public void onReceive(final Context context, final Intent intent) {
                final Uri defaultUri = RingtoneManager.getDefaultUri(2);
                (nintent = new Intent()).setClass(BluetoothChat.this, (Class)BluetoothChat.class);
                pintent = PendingIntent.getActivity(context, 0, nintent, 134217728);
                final Notification build = new NotificationCompat.Builder(context).setContentTitle(mConnectedDeviceName).setContentText(read).setSmallIcon(R.drawable.notii).setContentIntent(pintent).setTicker(String.valueOf(mConnectedDeviceName) + ":" + read).setSound(defaultUri).build();
                build.flags |= 0x10;
                notificationManager.notify(0, build);
            }
        };
    }
    
    static  void access$5(final BluetoothChat bluetoothChat, final String mConnectedDeviceName) {
        bluetoothChat.mConnectedDeviceName = mConnectedDeviceName;
    }
    
    private void connectDevice(final Intent intent, final boolean b) {
        this.mChatService.connect(this.mBluetoothAdapter.getRemoteDevice(intent.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS)), b);
    }
    
    private void ensureDiscoverable() {
        Log.d("BluetoothChat", "ensure discoverable");
        if (this.mBluetoothAdapter.getScanMode() != 23) {
            final Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
            intent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 300);
            this.startActivity(intent);
        }
    }
    
    private void fillArrayList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.emoticons.entrySet().iterator();
        while (iterator.hasNext()) {
            this.arrayList.add(iterator.next().getKey());
        }
    }
    
    private void fillOshaStickerList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.oshastickers.entrySet().iterator();
        while (iterator.hasNext()) {
            this.oshasticker_keyList.add(iterator.next().getKey());
        }
    }
    
    private void fillOshaStickerkeyList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.oshastickerkey.entrySet().iterator();
        while (iterator.hasNext()) {
            this.oshastickerList.add(iterator.next().getKey());
        }
    }
    
    private void fillPikachuStickerList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.pikachustickers.entrySet().iterator();
        while (iterator.hasNext()) {
            this.pikachusticker_keyList.add(iterator.next().getKey());
        }
    }
    
    private void fillPikachuStickerkeyList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.pikachustickerkey.entrySet().iterator();
        while (iterator.hasNext()) {
            this.pikachustickerList.add(iterator.next().getKey());
        }
    }
    
    private void fillPokeStickerList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.pokestickers.entrySet().iterator();
        while (iterator.hasNext()) {
            this.sticker_keyList.add(iterator.next().getKey());
        }
    }
    
    private void fillPokeStickerkeyList() {
        final Iterator<Map.Entry<String, Integer>> iterator = this.pokestickerkey.entrySet().iterator();
        while (iterator.hasNext()) {
            this.stickerList.add(iterator.next().getKey());
        }
    }
    
    private void sendMessage(final String s) {
        if (this.mChatService.getState() != 3) {
            Toast.makeText(this, R.string.not_connected, 0).show();
        }
        else {
            final String string = String.valueOf(s) + "/k";
            if (string.length() > 0) {
                this.mChatService.write(string.getBytes());
                this.mOutStringBuffer.setLength(0);
                if (!s.equals("is_typing") && !s.equals("is_typed")) {
                    this.mOutEditText.setText(this.mOutStringBuffer);
                }
            }
        }
    }
    
    private final void setStatus(final int subtitle) {
        this.getSupportActionBar().setSubtitle(subtitle);
    }
    
    private final void setStatus(final String subtitle) {
        this.getSupportActionBar().setSubtitle(subtitle);
    }
    
    private void setupChat() {
        Log.d("BluetoothChat", "setupChat()");
        this.mConversationArrayAdapter = new MyAdapter(this, this.messages);
        (this.mConversationView = (ListView)this.findViewById(R.id.listView1)).setOnCreateContextMenuListener((View.OnCreateContextMenuListener)new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(final ContextMenu contextMenu, final View view, final ContextMenu.ContextMenuInfo contextMenu$ContextMenuInfo) {
                contextMenu.add(0, view.getId(), 0, "copy");
            }
        });
        this.mConversationView.setAdapter((ListAdapter)this.mConversationArrayAdapter);
        (this.mOutEditText = (EditText)this.findViewById(R.id.deditText1)).setOnEditorActionListener(this.mWriteListener);
        this.mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                sendMessage(((TextView)findViewById(R.id.deditText1)).getText().toString());
            }
        });
        this.mChatService = new BluetoothChatService(this, mHandler);
        this.mOutStringBuffer = new StringBuffer("");
    }
    
    public void appClick(final View view) {
       /* final Intent intent = new Intent();
        intent.setClass(this,ListItem.class);
        this.startActivity(intent);
        this.popattach.dismiss();*/
    }
    
    public void audioClick(final View view) {
        final Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(Intent.createChooser(intent, "Select Audio"), 9);
        this.popattach.dismiss();
    }
    
    public void delete(final View view) {
        this.mOutEditText.dispatchKeyEvent(new KeyEvent(2, 67));
    }
    
    public void galleryClick(final View view) {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        this.startActivityForResult(Intent.createChooser(intent, "Send Picture"), 7);
        this.popattach.dismiss();
    }
    
    public Spannable getOshaStickerText(final Context context, final String s) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s);
        spannableStringBuilder.setSpan(new ImageSpan(context, this.oshastickers.get(s)), 0, spannableStringBuilder.length(), 33);
        return (Spannable)spannableStringBuilder;
    }
    
    public String getPath(final Uri uri) {
        final Cursor managedQuery = this.managedQuery(uri, new String[] { "_data" }, (String)null, (String[])null, (String)null);
        String string = null;
        if (managedQuery != null) {
            final int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
            managedQuery.moveToFirst();
            string = managedQuery.getString(columnIndexOrThrow);
        }
        return string;
    }
    
    public Spannable getPikachuStickerText(final Context context, final String s) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s);
        spannableStringBuilder.setSpan((Object)new ImageSpan(context, this.pikachustickers.get(s)), 0, spannableStringBuilder.length(), 33);
        return (Spannable)spannableStringBuilder;
    }
    
    public Spannable getPokeStickerText(final Context context, final String s) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s);
        spannableStringBuilder.setSpan((Object)new ImageSpan(context, this.pokestickers.get(s)), 0, spannableStringBuilder.length(), 33);
        return (Spannable)spannableStringBuilder;
    }
    
    public Spannable getSmiledText(final Context context, final String s) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s);
        for (int i = 0; i < spannableStringBuilder.length(); ++i) {
            for (final Map.Entry<String, Integer> entry : this.emoticons.entrySet()) {
                final int length = entry.getKey().length();
                if (i + length <= spannableStringBuilder.length() && spannableStringBuilder.subSequence(i, i + length).toString().equals(entry.getKey())) {
                    spannableStringBuilder.setSpan((Object)new ImageSpan(context, entry.getValue()), i, i + length, 33);
                    i += length - 1;
                    break;
                }
            }
        }
        return (Spannable)spannableStringBuilder;
    }
    
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        Log.d("BluetoothChat", "onActivityResult " + n2);
            switch (n) {
                case 1: {
                    if (n2 == -1) {
                        this.connectDevice(intent, true);
                        return;
                    }
                    break;
                }
                case 2: {
                    if (n2 == -1) {
                        this.connectDevice(intent, false);
                        return;
                    }
                    break;
                }
                case 3: {
                    if (n2 == -1) {
                        this.setupChat();
                        break;
                    }
                    Log.d("BluetoothChat", "BT not enabled");
                    Toast.makeText(this, 2131296274, 0).show();
                    this.finish();
                    break;
                }
                case 6: {
                    if (n2 == -1) {
                        if (n == 6) {
                            final Uri data = intent.getData();
                            this.filemanagerstring = data.getPath();
                            this.selectedImagePath = this.getPath(data);
                            if (this.selectedImagePath != null) {
                                System.out.println(this.selectedImagePath);
                            }
                            else {
                                System.out.println("selectedImagePath is null");
                            }
                            if (this.filemanagerstring != null) {
                                System.out.println(this.filemanagerstring);
                            }
                            else {
                                System.out.println("filemanagerstring is null");
                            }
                            if (this.selectedImagePath != null) {
                                System.out.println("selectedImagePath is the right one for you!");
                            }
                            else {
                                System.out.println("filemanagerstring is the right one for you!");
                            }
                        }
                        this.setImage(this.selectedImagePath);
                        return;
                    }
                    break;
                }
                case 7: {
                    if (n2 != -1) {
                        break;
                    }
                    if (n == 7) {
                        this.sendingImagePath = this.getPath(intent.getData());
                    }
                    if (this.sendingImagePath != "") {
                        final File file = new File(this.sendingImagePath);
                        final Intent intent2 = new Intent();
                        intent2.setAction("android.intent.action.SEND");
                        intent2.setType("image/*");
                        intent2.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.fromFile(file));
                        this.startActivity(intent2);
                        return;
                    }
                    break;
                }
                case 9: {
                    if (n2 != -1) {
                        break;
                    }
                    if (n == 9) {
                        this.sendingImagePath = this.getPath(intent.getData());
                    }
                    if (this.sendingImagePath != "") {
                        final File file2 = new File(this.sendingImagePath);
                        final Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.SEND");
                        intent3.setType("image/*");
                        intent3.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.fromFile(file2));
                        this.startActivity(intent3);
                        return;
                    }
                    break;
                }
                case 8: {
                    if (n2 != -1) {
                        break;
                    }
                    if (n == 8) {
                        this.sendingImagePath = this.getPath(intent.getData());
                    }
                    if (this.sendingImagePath != "") {
                        final File file3 = new File(this.sendingImagePath);
                        final Intent intent4 = new Intent();
                        intent4.setAction("android.intent.action.SEND");
                        intent4.setType("image/*");
                        intent4.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.fromFile(file3));
                        this.startActivity(intent4);
                        return;
                    }
                    break;
                }
            }
    }
    
    public void onBackPressed() {
        if (this.popattach != null && this.popattach.isShowing()) {
            this.once = true;
            this.popattach.dismiss();
            return;
        }
        if (this.layvisible) {
            this.lay.setVisibility(8);
            this.layvisible = false;
            this.b.setImageResource(R.drawable.emoji_btn_normal);
            this.count = 0;
            return;
        }
        if (this.once) {
            Toast.makeText(this, R.string.pressback, 1).show();
            this.once = false;
            return;
        }
        super.onBackPressed();
        
        
    }
    
    public boolean onContextItemSelected(final MenuItem menuItem) {
        if (menuItem.getTitle() == "copy") {
            final String string = ((Messages)this.mConversationArrayAdapter.getItem(menuItem.getItemId())).getMessage().toString();
            if (Build.VERSION.SDK_INT >= 11) {
                ((ClipboardManager)this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("simple text", string));
            }
            else {
                ((android.text.ClipboardManager)this.getSystemService("clipboard")).setText(string);
            }
            Toast.makeText(this.getApplicationContext(), "Copied", 1).show();
            return true;
        }
        return false;
    }
    
    
    
    
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        Log.e("BluetoothChat", "+++ ON CREATE +++");
        this.pref = new Preferences(this.getApplicationContext());
        this.registerReceiver(this.typingReciever, new IntentFilter("TYPING"));
        this.registerReceiver(this.notify, new IntentFilter("Message"));
        this.setContentView(R.layout.activity_bluetooth_chat);
        (this.mOutEditText = (EditText)this.findViewById(R.id.deditText1)).setOnEditorActionListener(this.mWriteListener);
        this.tabs = (PagerSlidingTabStrip)this.findViewById(R.id.tabs);
        this.pager = (ViewPager)this.findViewById(R.id.pager);
        this.adapter = new ContactPagerAdapter();
        this.pager.setAdapter(adapter);
        this.tabs.setViewPager(this.pager);
        final ImageButton imageButton = (ImageButton)this.findViewById(R.id.delButton);
        
        
        final Runnable mAction = new Runnable() {
            @Override
            public void run() {
                mOutEditText.dispatchKeyEvent(new KeyEvent(2, 67));
                System.out.println("Performing action...");
               mHandler.postDelayed((Runnable)this, 200L);
            }
        };
        
        
        imageButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				 boolean b = true;
	                switch (event.getAction()) {
	                
	                case 0: {
                        if (mHandler == null) {
                            (mHandler = new Handler()).postDelayed(mAction, 500L);
                            break;
                        }
                        return b;
                    }
                    case 1: {
                        if (mHandler != null) {
                            mHandler.removeCallbacks(mAction);
                            mHandler = null;
                            break;
                        }
                        return b;
                    }
                }
                b = false;
				return b;
			}
		});
		
    
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                mOutEditText.dispatchKeyEvent(new KeyEvent(2, 67));
            }
        });
        this.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.actionbar_background));
        this.lay = (LinearLayout)this.findViewById(R.id.slider);
        this.b = (ImageButton)this.findViewById(R.id.button1);
        this.lay.setVisibility(8);
        this.imm = (InputMethodManager)this.getSystemService("input_method");
        this.myLayout = (RelativeLayout)this.findViewById(R.id.main2);
        final Rect rect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        final int height = this.getWindowManager().getDefaultDisplay().getHeight();
        this.heightDifferenc = rect.bottom;
        Log.e("Keyboard Size", "Size: " + this.heightDifferenc);
        Log.e("Screen", "Size: " + height);
        this.myLayout.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                final Rect rect = new Rect();
                myLayout.getWindowVisibleDisplayFrame(rect);
                final int height = myLayout.getRootView().getHeight();
                screenHeight = height;
                heightDifference = height - (rect.bottom - rect.top);
                if (rect.bottom < heightDifferenc && isKbd) {
                    isKbdActive = true;
                    isKbd = false;
                    count = 0;
                    b.setImageResource(R.drawable.emoji_btn_normal);
                    lay.setVisibility(8);
                    layvisible = false;
                }
                else {
                    if (e == 1) {
                        isKbdActive = false;
                    }
                    isKbd = true;
                }
                Log.e("Keyboard Size", "Size: " + heightDifference);
                Log.e("Screen height", "Size: " + screenHeight);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                if (count == 0) {
                    imm.hideSoftInputFromWindow(mOutEditText.getWindowToken(), 0);
                    lay.setVisibility(0);
                    layvisible = true;
                    lay.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, height / 2));
                    Log.e("Screen", "Size: " + height);
                    if (!isKbdActive) {
                        Log.e("KBD Active?", "false");
                        b.setImageResource(R.drawable.emoji_down_icon);
                        count = 2;
                        return;
                    }
                    e = 1;
                    Log.e("KBD Active?", "true");
                    b.setImageResource(R.drawable.emoji_kbd);
                    lay.setVisibility(0);
                    count = 1;
                }
                else {
                    if (count == 1) {
                        e = 0;
                        lay.setVisibility(8);
                        layvisible = false;
                        b.setImageResource(R.drawable.emoji_btn_normal);
                        imm.showSoftInput((View)mOutEditText, 0);
                        count = 0;
                        return;
                    }
                    if (count == 2) {
                        e = 1;
                        lay.setVisibility(8);
                        layvisible = false;
                        b.setImageResource(R.drawable.emoji_btn_normal);
                        count = 0;
                    }
                }
            }
        });
        final TypedValue typedValue = new TypedValue();
        if (Build.VERSION.SDK_INT >= 11) {
            if (this.getTheme().resolveAttribute(R.style.Sherlock___Theme, typedValue, true)) {
                this.actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, this.getResources().getDisplayMetrics());
            }
        }
        else if (this.getTheme().resolveAttribute(R.style.AppBaseTheme, typedValue, true)) {
            this.actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, this.getResources().getDisplayMetrics());
        }
        final LayoutInflater layoutInflater = this.getLayoutInflater();
        this.layout = layoutInflater.inflate(R.layout.toast, (ViewGroup)this.findViewById(R.id.l));
        this.toast = (TextView)this.layout.findViewById(R.id.textView1);
        this.pop = new PopupWindow(this.layout, -2, -2, false);
        final float density = this.getResources().getDisplayMetrics().density;
        final float ydpi = this.getResources().getDisplayMetrics().ydpi;
        this.x = (int)(96.0f * (160.0f * density / 240.0f));
        this.y = (int)(90.0f * (ydpi / 240.0f));
        this.v = layoutInflater.inflate(R.layout.attach, (ViewGroup)this.findViewById(R.id.latt));
        (this.popattach = new PopupWindow(this.v, -2, -2, false)).setOutsideTouchable(true);
        this.popattach.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.emoji_popup_group_horizontal));
        (this.notificationManager = (NotificationManager)this.getSystemService("notification")).cancel(0);
        (this.mSendButton = (ImageButton)this.findViewById(R.id.dcancel)).setEnabled(false);
        this.mSendButton.setImageResource(R.drawable.ic_send_disabled_holo_light);
        this.mOutEditText.addTextChangedListener(this.mWatcher);
        Log.i("bg", this.pref.getPreference());
        this.bg = this.pref.getPreference();
        this.emoticons.put("(amazing)", R.drawable.blue_2);
        this.emoticons.put("(smoking)", R.drawable.blue_3);
        this.emoticons.put("(speechless)", R.drawable.blue_4);
        this.emoticons.put("(wondering)", R.drawable.blue_5);
        this.emoticons.put(";)", R.drawable.blue_6);
        this.emoticons.put("(yes)", R.drawable.blue_7);
        this.emoticons.put("(wtf)", R.drawable.blue_8);
        this.emoticons.put("(yawning)", R.drawable.blue_9);
        this.emoticons.put("(whew)", R.drawable.blue_10);
        this.emoticons.put("(wfh)", R.drawable.blue_11);
        this.emoticons.put("(waiting)", R.drawable.blue_12);
        this.emoticons.put("(wait)", R.drawable.blue_13);
        this.emoticons.put(":p", R.drawable.blue_14);
        this.emoticons.put("(toivo)", R.drawable.blue_15);
        this.emoticons.put("(tmi)", R.drawable.blue_16);
        this.emoticons.put("(time)", R.drawable.blue_17);
        this.emoticons.put("(talking)", R.drawable.blue_18);
        this.emoticons.put("(thinking)", R.drawable.blue_19);
        this.emoticons.put("(sweating)", R.drawable.blue_20);
        this.emoticons.put("(swear)",  R.drawable.blue_21);
        this.emoticons.put(":o",  R.drawable.blue_22);
        this.emoticons.put("(sunshine)",  R.drawable.blue_23);
        this.emoticons.put("(star)",  R.drawable.blue_24);
        this.emoticons.put("(nerd)",  R.drawable.blue_25);
        this.emoticons.put("(muscle)",  R.drawable.blue_26);
        this.emoticons.put("(movie)",  R.drawable.blue_27);
        this.emoticons.put("(worried)",  R.drawable.blue_28);
        this.emoticons.put(":)",  R.drawable.blue_29);
        this.emoticons.put("(sleepy)",  R.drawable.blue_30);
        this.emoticons.put("(shake)", R.drawable.blue_31);
        this.emoticons.put(":(", R.drawable.blue_32);
        this.emoticons.put("(rofl)", R.drawable.blue_33);
        this.emoticons.put("(rock)", R.drawable.blue_34);
        this.emoticons.put("(rain)", R.drawable.blue_35);
        this.emoticons.put("(punch)", R.drawable.blue_36);
        this.emoticons.put("(puking)", R.drawable.blue_37);
        this.emoticons.put("(priidu)", R.drawable.blue_38);
        this.emoticons.put("(oliver)", R.drawable.blue_39);
        this.emoticons.put("(nerd)", R.drawable.amazing);
        this.emoticons.put("(ninja)", R.drawable.anger);
        this.emoticons.put("(no)", R.drawable.bad_egg);
        this.emoticons.put("(mail)", R.drawable.bad_smile);
        this.emoticons.put("(makeup)", R.drawable.beaten);
        this.emoticons.put("(malthe)", R.drawable.big_smile);
        this.emoticons.put("(mooning)", R.drawable.black_heart);
        this.emoticons.put("(lalala)", R.drawable.cry);
        this.emoticons.put("(lipssealed)", R.drawable.electric_shock);
        this.emoticons.put(":*", R.drawable.exciting);
        this.emoticons.put("(facepalm)", R.drawable.eyes_droped);
        this.emoticons.put("(finger)", R.drawable.girl);
        this.emoticons.put("(fingercrossed)", R.drawable.greedy);
        this.emoticons.put("(fubar)", R.drawable.grimace);
        this.emoticons.put("(envy)", R.drawable.haha);
        this.emoticons.put("(evilgrin)", R.drawable.happy);
        this.emoticons.put("(emo)", R.drawable.horror);
        this.emoticons.put("(dancing)", R.drawable.money);
        this.emoticons.put("(devil)", R.drawable.nothing);
        this.emoticons.put("(doh)", R.drawable.nothing_to_say);
        this.emoticons.put("(drink)", R.drawable.scorn);
        this.emoticons.put("(drunk)", R.drawable.red_heart);
        this.emoticons.put("(dull)", R.drawable.secret_smile);
        this.emoticons.put("(dull_tuari)", R.drawable.shame);
        this.emoticons.put("(cake)", R.drawable.super_man);
        this.emoticons.put("(call)", R.drawable.shocked);
        this.emoticons.put("(cash)", R.drawable.the_iron_man);
        this.emoticons.put("(clapping)", R.drawable.unhappy);
        this.emoticons.put("(coffee)", R.drawable.victory);
        this.emoticons.put("(cool)", R.drawable.what);
        this.fillArrayList();
        this.pokestickers.put("sticker_1", R.drawable.alakazam);
        this.pokestickers.put("sticker_2", R.drawable.birdy);
        this.pokestickers.put("sticker_3", R.drawable.blastoise);
        this.pokestickers.put("sticker_4", R.drawable.blissey);
        this.pokestickers.put("sticker_5", R.drawable.bulb);
        this.pokestickers.put("sticker_6", R.drawable.caterpie);
        this.pokestickers.put("sticker_7", R.drawable.chardragon);
        this.pokestickers.put("sticker_8", R.drawable.charmainder);
        this.pokestickers.put("sticker_9", R.drawable.dewott);
        this.pokestickers.put("sticker_10", R.drawable.electroman);
        this.pokestickers.put("sticker_11", R.drawable.fireitup);
        this.pokestickers.put("sticker_12", R.drawable.gyarados);
        this.pokestickers.put("sticker_13", R.drawable.heracross);
        this.pokestickers.put("sticker_14", R.drawable.jiglepie);
        this.pokestickers.put("sticker_15", R.drawable.kingdra);
        this.pokestickers.put("sticker_16", R.drawable.leavess);
        this.pokestickers.put("sticker_17", R.drawable.leavessss);
        this.pokestickers.put("sticker_21", R.drawable.marilla);
        this.pokestickers.put("sticker_100", R.drawable.rocket_meowth);
        this.pokestickers.put("sticker_101", R.drawable.sceptile);
        this.pokestickers.put("sticker_102", R.drawable.scizor);
        this.pokestickers.put("sticker_103", R.drawable.servine);
        this.pokestickers.put("sticker_104", R.drawable.snivy);
        this.pokestickers.put("sticker_105", R.drawable.steelix);
        this.pokestickers.put("sticker_106", R.drawable.trickster);
        this.pokestickers.put("sticker_107", R.drawable.typhlosion);
        this.pokestickers.put("sticker_108", R.drawable.venusaur);
        this.pokestickers.put("sticker_109", R.drawable.wailord);
        this.pokestickers.put("sticker_110", R.drawable.squirtle);
        this.pokestickerkey.put("sticker_1", R.drawable.alakazam_key);
        this.pokestickerkey.put("sticker_2", R.drawable.birdy_key);
        this.pokestickerkey.put("sticker_3", R.drawable.blastoise_key);
        this.pokestickerkey.put("sticker_4", R.drawable.blissey_key);
        this.pokestickerkey.put("sticker_5", R.drawable.bulb_key);
        this.pokestickerkey.put("sticker_6", R.drawable.caterpie_key);
        this.pokestickerkey.put("sticker_7", R.drawable.chardragon_key);
        this.pokestickerkey.put("sticker_8", R.drawable.charmainder_key);
        this.pokestickerkey.put("sticker_9", R.drawable.dewott_key);
        this.pokestickerkey.put("sticker_10", R.drawable.electroman_key);
        this.pokestickerkey.put("sticker_11", R.drawable.fireitup_key);
        this.pokestickerkey.put("sticker_12", R.drawable.gyarados_key);
        this.pokestickerkey.put("sticker_13", R.drawable.heracross_key);
        this.pokestickerkey.put("sticker_14", R.drawable.jiglepie_key);
        this.pokestickerkey.put("sticker_15", R.drawable.kingdra_key);
        this.pokestickerkey.put("sticker_16", R.drawable.leavess_key);
        this.pokestickerkey.put("sticker_17", R.drawable.leavessss_key);
        this.pokestickerkey.put("sticker_21", R.drawable.marilla_key);
        this.pokestickerkey.put("sticker_100", R.drawable.rocket_meowth_key);
        this.pokestickerkey.put("sticker_101", R.drawable.sceptile_key);
        this.pokestickerkey.put("sticker_102", R.drawable.scizor_key);
        this.pokestickerkey.put("sticker_103", R.drawable.servine_key);
        this.pokestickerkey.put("sticker_104", R.drawable.snivy_key);
        this.pokestickerkey.put("sticker_105", R.drawable.steelix_key);
        this.pokestickerkey.put("sticker_106", R.drawable.trickster_key);
        this.pokestickerkey.put("sticker_107", R.drawable.typhlosion_key);
        this.pokestickerkey.put("sticker_108", R.drawable.venusaur_key);
        this.pokestickerkey.put("sticker_109", R.drawable.wailord_key);
        this.pokestickerkey.put("sticker_110", R.drawable.squirtle_key);
        this.pikachustickers.put("sticker1_1", R.drawable.angerpikachu);
        this.pikachustickers.put("sticker1_2", R.drawable.angrypikachu2);
        this.pikachustickers.put("sticker1_3", R.drawable.blushingpikachu2);
        this.pikachustickers.put("sticker1_4", R.drawable.cryingpikachu2);
        this.pikachustickers.put("sticker1_5", R.drawable.cutepikachu);
        this.pikachustickers.put("sticker1_6", R.drawable.cutepikachucap);
        this.pikachustickers.put("sticker1_7", R.drawable.evilpikachu2);
        this.pikachustickers.put("sticker1_8", R.drawable.moustachepikachu);
        this.pikachustickers.put("sticker1_9", R.drawable.pikachu2);
        this.pikachustickers.put("sticker1_10", R.drawable.pikachuwithstick);
        this.pikachustickers.put("sticker1_11", R.drawable.rockstarpikachu);
        this.pikachustickers.put("sticker1_12", R.drawable.samuraipikachu);
        this.pikachustickers.put("sticker1_13", R.drawable.sleepingpikachu2);
        this.pikachustickers.put("sticker1_14", R.drawable.stylishpikachu);
        this.pikachustickers.put("sticker1_15", R.drawable.surprisedpikachu2);
        this.pikachustickers.put("sticker1_16", R.drawable.winkingpikachu);
        this.pikachustickers.put("sticker1_17", R.drawable.winterpikachu);
        this.pikachustickers.put("sticker1_21", R.drawable.winterpikachu2);
        this.pikachustickerkey.put("sticker1_1", R.drawable.angerpikachu_key);
        this.pikachustickerkey.put("sticker1_2", R.drawable.angrypikachu2_key);
        this.pikachustickerkey.put("sticker1_3", R.drawable.blushingpikachu2_key);
        this.pikachustickerkey.put("sticker1_4", R.drawable.cryingpikachu2_key);
        this.pikachustickerkey.put("sticker1_5", R.drawable.cutepikachu_key);
        this.pikachustickerkey.put("sticker1_6", R.drawable.cutepikachu_key);
        this.pikachustickerkey.put("sticker1_7", R.drawable.evilpikachu2_key);
        this.pikachustickerkey.put("sticker1_8", R.drawable.moustachepikachu_key);
        this.pikachustickerkey.put("sticker1_9", R.drawable.pikachu2_key);
        this.pikachustickerkey.put("sticker1_10", R.drawable.pikachuwithstick_key);
        this.pikachustickerkey.put("sticker1_11", R.drawable.pikachu2_key);
        this.pikachustickerkey.put("sticker1_12", R.drawable.samuraipikachu_key);
        this.pikachustickerkey.put("sticker1_13", R.drawable.sleepingpikachu2_key);
        this.pikachustickerkey.put("sticker1_14", R.drawable.stylishpikachu_key);
        this.pikachustickerkey.put("sticker1_15", R.drawable.surprisedpikachu2_key);
        this.pikachustickerkey.put("sticker1_16", R.drawable.winkingpikachu_key);
        this.pikachustickerkey.put("sticker1_17", R.drawable.winterpikachu_key);
        this.pikachustickerkey.put("sticker1_21", R.drawable.winterpikachu2_key);
        this.oshastickers.put("sticker2_1", R.drawable.addressig_oshawot);
        this.oshastickers.put("sticker2_2", R.drawable.angel_oshawot);
        this.oshastickers.put("sticker2_3", R.drawable.cool_oshawot);
        this.oshastickers.put("sticker2_4", R.drawable.cute_oshawot);
        this.oshastickers.put("sticker2_5", R.drawable.cuty_oshawot);
        this.oshastickers.put("sticker2_6", R.drawable.loving_oshawot);
        this.oshastickers.put("sticker2_7", R.drawable.oshawot_confused);
        this.oshastickers.put("sticker2_8", R.drawable.pissed_off_oshawot);
        this.oshastickers.put("sticker2_9", R.drawable.pissed_off_oshawot_2);
        this.oshastickers.put("sticker2_10", R.drawable.samurai_oshawot);
        this.oshastickers.put("sticker2_11", R.drawable.smiling_oshawot);
        this.oshastickers.put("sticker2_12", R.drawable.very_happy_oshawot);
        this.oshastickers.put("sticker2_12", R.drawable.angel_oshawot);
        this.oshastickerkey.put("sticker2_1", R.drawable.addressig_oshawot_key);
        this.oshastickerkey.put("sticker2_2", R.drawable.angel_oshawot_key);
        this.oshastickerkey.put("sticker2_3", R.drawable.cool_oshawot_key);
        this.oshastickerkey.put("sticker2_4", R.drawable.cute_oshawot_key);
        this.oshastickerkey.put("sticker2_5", R.drawable.cuty_oshawot_key);
        this.oshastickerkey.put("sticker2_6", R.drawable.loving_oshawot_key);
        this.oshastickerkey.put("sticker2_7", R.drawable.oshawot_confused_key);
        this.oshastickerkey.put("sticker2_8", R.drawable.pissed_off_oshawot_key);
        this.oshastickerkey.put("sticker2_9", R.drawable.pissed_off_oshawot_2_key);
        this.oshastickerkey.put("sticker2_10", R.drawable.samurai_oshawot_key);
        this.oshastickerkey.put("sticker2_11", R.drawable.smiling_oshawot_key);
        this.oshastickerkey.put("sticker2_12", R.drawable.very_happy_oshawot_key);
        this.oshastickerkey.put("sticker2_12", R.drawable.angry_oshawot_key);
        this.fillPokeStickerList();
        this.fillPikachuStickerList();
        this.fillOshaStickerList();
        this.fillPokeStickerkeyList();
        this.fillPikachuStickerkeyList();
        this.fillOshaStickerkeyList();
        Collections.sort(this.sticker_keyList);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", 1).show();
            this.finish();
        }
    }
    
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getSupportMenuInflater().inflate(R.menu.activity_bluetooth_chat, menu);
        return true;
    }
    
    public void onDestroy() {
        if (this.pop != null) {
            this.pop.dismiss();
        }
        if (this.popattach != null) {
            this.popattach.dismiss();
        }
        if (this.mChatService != null) {
            this.mChatService.stop();
        }
        Log.e("BluetoothChat", "--- ON DESTROY ---");
        this.unregisterReceiver(this.typingReciever);
        this.unregisterReceiver(this.notify);
        super.onDestroy();
        this.pop.dismiss();
    }
    
    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.secure_connect_scan: {
                this.startActivityForResult(new Intent(this,DeviceListActivity.class), 1);
                return true;
            }
            case R.id.discoverable: {
                this.ensureDiscoverable();
                return true;
            }
            case R.id.background: {
                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 6);
                break;
            }
            case R.id.send_image: {
                this.popattach.showAtLocation(this.v, 53, 0, 10 + this.y);
                break;
            }
            case R.id.bubbles: {
                final AlertDialog.Builder alertDialog$Builder = new AlertDialog.Builder(this);
                alertDialog$Builder.setTitle("Choose Bubbles Type");
                alertDialog$Builder.setSingleChoiceItems(new CharSequence[] { "Bubbles Type 1", "Bubbles Type 2", "Bubbles Type 3" }, this.pref.getBubble(), (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int bubble) {
                        pref.setBubble(bubble);
                        mConversationArrayAdapter.notifyDataSetChanged();
                        alert.dismiss();
                    }
                });
                (this.alert = alertDialog$Builder.create()).show();
                break;
            }
            case R.id.fonts: {
                final AlertDialog.Builder alertDialog$Builder2 = new AlertDialog.Builder(this);
                alertDialog$Builder2.setTitle("Choose Font Type");
                alertDialog$Builder2.setSingleChoiceItems(new CharSequence[] { "Default", "Font Type 1", "Font Type 2", "Font Type 3", "Font Type 4" }, this.pref.getFont(), (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int font) {
                        pref.setFont(font);
                        mConversationArrayAdapter.notifyDataSetChanged();
                        alert.dismiss();
                    }
                });
                (this.alert = alertDialog$Builder2.create()).show();
                break;
            }
            case R.id.about: {
                final Intent intent2 = new Intent();
                intent2.setClass(this,About.class);
                this.startActivity(intent2);
                break;
            }
        }
        return false;
    }
    
    public void onPause() {
        synchronized (this) {
            super.onPause();
            this.onpause = true;
            this.popattach.dismiss();
            Log.e("BluetoothChat", "- ON PAUSE -");
        }
    }
    
    public void onResume() {
        synchronized (this) {
            super.onResume();
            if (this.mBluetoothAdapter.isEnabled() && this.mChatService.getState() == 0) {
                new Handler().postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (getWindow().getWindowManager().getDefaultDisplay().getOrientation() == 1) {
                            pop.showAtLocation(layout, 53, x, y);
                        }
                        else if (getWindow().getWindowManager().getDefaultDisplay().getOrientation() == 0) {
                            pop.showAtLocation(layout, 53, x, 10 + y);
                        }
                        toast.setText((String.valueOf(getResources().getString(R.string.title_not_connected)) + " : " + getResources().getString(R.string.secure_connect)));
                    }
                }, 1000L);
            }
            this.onpause = false;
            this.notificationManager.cancel(0);
            this.bg = this.pref.getPreference();
            Log.e("BluetoothChat", "+ ON RESUME +");
            if (this.mChatService != null && this.mChatService.getState() == 0) {
                this.mChatService.start();
            }
            this.setImage(this.bg);
        }
    }
    
    public void onStart() {
        super.onStart();
        Log.e("BluetoothChat", "++ ON START ++");
        if (!this.mBluetoothAdapter.isEnabled()) {
            this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 3);
        }
        else if (this.mChatService == null) {
            this.setupChat();
        }
    }
    
    public void onStop() {
        super.onStop();
        Log.e("BluetoothChat", "-- ON STOP --");
    }
    
    public void setImage(final String preference) {
        if (preference != null) {
            if (preference != "0") {
                this.getWindow().setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeFile(new File(preference).getAbsolutePath())));
                this.pref.setPreference(preference);
                return;
            }
            this.getWindow().setBackgroundDrawableResource(2130837640);
        }
    }
    
    public void videoClick(final View view) {
        final Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction("android.intent.action.PICK");
        this.startActivityForResult(Intent.createChooser(intent,"Select Video"), 8);
        this.popattach.dismiss();
    }
    
    public class ContactPagerAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider
    {
        private final int[] ICONS;
        
        public ContactPagerAdapter() {
            super();
            this.ICONS = new int[] { R.drawable.blue_251, R.drawable.pokeball, R.drawable.pikachu, R.drawable.oshawott };
        }
        
        @Override
        public void destroyItem(final ViewGroup viewGroup, final int n, final Object o) {
            viewGroup.removeView((View)o);
        }
        
        @Override
        public int getCount() {
            return this.ICONS.length;
        }
        
        @Override
        public int getPageIconResId(final int n) {
            return this.ICONS[n];
        }
        
        @Override
        public Object instantiateItem(final ViewGroup viewGroup,final int pos) {
        	BluetoothChat.this.pos = pos;
            final GridView gridView = new GridView(BluetoothChat.this);
            gridView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1));
            gridView.setAdapter((ListAdapter)new IconAdapter(pos));
            if (pos == 0) {
                gridView.setColumnWidth(60);
            }
            else {
                gridView.setColumnWidth(100);
            }
            gridView.setStretchMode(2);
            gridView.setNumColumns(4);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                    if (pos == 0) {
                        mOutEditText.setText(getSmiledText(BluetoothChat.this, String.valueOf(mOutEditText.getText().toString()) + gridView.getAdapter().getItem(n).toString()));
                        mOutEditText.setSelection(mOutEditText.getText().length());
                        return;
                    }
                    final String string = gridView.getAdapter().getItem(n).toString();
                    Log.i("sticker", string);
                    sendMessage(string);
                }
            });
            viewGroup.addView(gridView, 0);
            return gridView;
        }
        
        @Override
        public boolean isViewFromObject(final View view, final Object o) {
            return view == o;
        }
    }
    
    class IconAdapter extends BaseAdapter
    {
        int position;
        
        public IconAdapter(final int position) {
            super();
            this.position = position;
        }
        
        public int getCount() {
            if (this.position == 0) {
                return arrayList.size();
            }
            if (this.position == 1) {
                return sticker_keyList.size();
            }
            if (this.position == 2) {
                return pikachusticker_keyList.size();
            }
            return oshasticker_keyList.size();
        }
        
        public Object getItem(final int n) {
            if (this.position == 0) {
                return arrayList.get(n);
            }
            if (this.position == 1) {
                return stickerList.get(n);
            }
            if (this.position == 2) {
                return pikachustickerList.get(n);
            }
            return oshastickerList.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            final View inflate = LayoutInflater.from(BluetoothChat.this).inflate(R.layout.row,null);
            final ImageView imageView = (ImageView)inflate.findViewById(R.id.imageView1);
            if (this.position == 0) {
                imageView.setBackgroundResource(emoticons.get(arrayList.get(n)));
                return inflate;
            }
            if (this.position == 1) {
                imageView.setBackgroundResource(pokestickerkey.get(stickerList.get(n)));
                return inflate;
            }
            if (this.position == 2) {
                imageView.setBackgroundResource(pikachustickerkey.get(pikachustickerList.get(n)));
                return inflate;
            }
            imageView.setBackgroundResource(oshastickerkey.get(oshastickerList.get(n)));
            return inflate;
        }
    }
    
    class ImageAdapter extends BaseAdapter
    {
        public int getCount() {
            return arrayList.size();
        }
        
        public Object getItem(final int n) {
            return arrayList.get(n);
        }
        
        public long getItemId(final int n) {
            return n;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            final View inflate = LayoutInflater.from(BluetoothChat.this).inflate(R.layout.row, null);
            ((ImageView)inflate.findViewById(R.id.imageView1)).setBackgroundResource(emoticons.get(arrayList.get(n)));
            return inflate;
        }
    }
    
    class StickerAdapter extends BaseAdapter
    {
        public int getCount() {
            return sticker_keyList.size();
        }
        
        public Object getItem(final int n) {
            return sticker_keyList.get(n);
        }
        
        public long getItemId(final int n) {
            return n;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            final View inflate = LayoutInflater.from(BluetoothChat.this).inflate(R.layout.row,null);
            (inflate.findViewById(R.id.imageView1)).setBackgroundResource(pokestickerkey.get(sticker_keyList.get(n)));
            Log.i("sticker", getApplicationContext().getResources().getResourceEntryName(pokestickers.get(stickerList.get(n))));
            return inflate;
        }
    }}
