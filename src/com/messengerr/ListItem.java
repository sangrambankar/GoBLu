package com.messengerr;

import com.actionbarsherlock.app.*;
import android.graphics.drawable.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.*;
import android.util.*;
import android.content.pm.*;
import java.util.*;
import android.view.*;
import android.widget.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;

public class ListItem extends SherlockActivity
{
    ArrayList<String> appName;
    ArrayList<Drawable> icons;
    String name;
    ArrayList<String> path;
    
    private boolean canRename(final File file, final File file2) {
        return file.getAbsolutePath().replaceAll("^(/mnt/|/)", "").replaceAll("\\/\\w+", "").equals(file2.getAbsolutePath().replaceAll("^(/mnt/|/)", "").replaceAll("\\/\\w+", ""));
    }
    
    private void copy(final File file, final File file2) throws IOException {
        file2.createNewFile();
        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        final RandomAccessFile randomAccessFile2 = new RandomAccessFile(file2, "rw");
        randomAccessFile2.getChannel().write(randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length()));
        randomAccessFile.close();
        randomAccessFile2.close();
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903065);
        this.getSupportActionBar().setTitle(2131296298);
        this.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(2130837618));
        final PackageManager packageManager = this.getPackageManager();
        this.appName = new ArrayList<String>();
        this.path = new ArrayList<String>();
        this.icons = new ArrayList<Drawable>();
        for (final ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0)) {
            if (!applicationInfo.sourceDir.startsWith("/system")) {
                Log.e("PackageList", "package: " + (Object)packageManager.getApplicationLabel(applicationInfo) + ", sourceDir: " + applicationInfo.sourceDir);
                this.appName.add((String)packageManager.getApplicationLabel(applicationInfo));
                this.path.add(applicationInfo.sourceDir);
                this.icons.add(packageManager.getApplicationIcon(applicationInfo));
            }
        }
        final ListView listView = (ListView)this.findViewById(2131034175);
        listView.setAdapter((ListAdapter)new AppAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                ListItem.this.name = ListItem.this.appName.get(n);
             //   new Rename().execute((Object[])new String[] { ListItem.this.path.get(n) });
            }
        });
    }
    
    class AppAdapter extends BaseAdapter
    {
        public int getCount() {
            return ListItem.this.appName.size();
        }
        
        public Object getItem(final int n) {
            return ListItem.this.appName.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            final View inflate = LayoutInflater.from((Context)ListItem.this).inflate(2130903069, viewGroup, false);
            final TextView textView = (TextView)inflate.findViewById(2131034130);
            final ImageView imageView = (ImageView)inflate.findViewById(2131034129);
            textView.setText((CharSequence)ListItem.this.appName.get(n));
            imageView.setImageDrawable((Drawable)ListItem.this.icons.get(n));
            return inflate;
        }
    }
    
 /*   class Rename extends AsyncTask<String, Void, File>
    {
        ProgressDialog dialog;
        
        protected File doInBackground(final String... array) {
            final File file = new File(array[0]);
            if (file.exists()) {
                Log.e("from", String.valueOf(ListItem.this.name) + "exist");
            }
            final File file2 = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Bt AppSharer");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            final File file3 = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Bt AppSharer", String.valueOf(ListItem.this.name) + ".apk");
            if (ListItem.this.canRename(file, file3)) {
                if (!file.renameTo(file3)) {
                    Log.e("copy", "Error to move new app: " + file + " > " + file3);
                }
            }
            else {
                try {
                    ListItem.this.copy(file, file3);
                }
                catch (Exception ex) {
                    Log.e("jm", "Error to move new app: " + file + " > " + file3);
                }
            }
            return null;
        }
        
        protected void onPostExecute(final File file) {
            super.onPostExecute(file);

        	this.dialog.dismiss();
            final File file = new File(Environment.getExternalStorageDirectory() + "/WhatsApp Bt AppSharer", String.valueOf(ListItem.this.name) + ".apk");
            if (file.exists()) {
                Log.e("source File:", file.getPath());
            }
            final Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("application/octet-stream");
            intent.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.fromFile(file));
            ListItem.this.startActivity(intent);
        }
        
        protected void onPreExecute() {
            (this.dialog = new ProgressDialog((Context)ListItem.this)).show();
            super.onPreExecute();
        }
    }*/
}
