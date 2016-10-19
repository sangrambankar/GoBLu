package com.messengerr;

import com.actionbarsherlock.app.*;
import android.view.*;
import android.bluetooth.*;
import android.util.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import java.util.*;

public class DeviceListActivity extends SherlockActivity
{
    private static final boolean D = true;
    public static String EXTRA_DEVICE_ADDRESS;
    private static final String TAG = "DeviceListActivity";
    private BluetoothAdapter mBtAdapter;
    private AdapterView.OnItemClickListener mDeviceClickListener;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private final BroadcastReceiver mReceiver;
    ListView newDevicesListView;
    
    static {
        DeviceListActivity.EXTRA_DEVICE_ADDRESS = "device_address";
    }
    
    public DeviceListActivity() {
        super();
        this.mDeviceClickListener = (AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                DeviceListActivity.this.mBtAdapter.cancelDiscovery();
                final String string = ((TextView)view).getText().toString();
                final String substring = string.substring(string.length()-17);
                final Intent intent = new Intent();
                intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, substring);
                DeviceListActivity.this.setResult(-1, intent);
                DeviceListActivity.this.finish();
            }
        };
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(final Context context, final Intent intent) {
                final String action = intent.getAction();
                if ("android.bluetooth.device.action.FOUND".equals(action)) {
                    final BluetoothDevice bluetoothDevice = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice.getBondState() != 12) {
                        DeviceListActivity.this.mNewDevicesArrayAdapter.add((String.valueOf(bluetoothDevice.getName()) + "\n" + bluetoothDevice.getAddress()));
                    }
                }
                else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                    DeviceListActivity.this.setProgressBarIndeterminateVisibility(false);
                    DeviceListActivity.this.setTitle(2131296279);
                    if (DeviceListActivity.this.mNewDevicesArrayAdapter.getCount() == 0) {
                        DeviceListActivity.this.mNewDevicesArrayAdapter.add(DeviceListActivity.this.getString(R.string.none_found));
                    }
                }
            }
        };
    }
    
    private void doDiscovery() {
        Log.d("DeviceListActivity", "doDiscovery()");
        this.setProgressBarIndeterminateVisibility(true);
        this.setTitle(R.string.scanning);
        this.findViewById(R.id.title_new_devices).setVisibility(0);
        this.newDevicesListView.setVisibility(0);
        if (this.mBtAdapter.isDiscovering()) {
            this.mBtAdapter.cancelDiscovery();
        }
        this.mBtAdapter.startDiscovery();
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.device_list);
        this.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.actionbar_background));
        this.getSupportActionBar().setTitle(R.string.secure_connect);
        this.setResult(0);
        this.mPairedDevicesArrayAdapter = (ArrayAdapter<String>)new ArrayAdapter((Context)this, R.layout.device_name);
        this.mNewDevicesArrayAdapter = (ArrayAdapter<String>)new ArrayAdapter((Context)this, R.layout.device_name);
        final ListView listView = (ListView)this.findViewById(R.id.paired_devices);
        listView.setAdapter((ListAdapter)this.mPairedDevicesArrayAdapter);
        listView.setOnItemClickListener(this.mDeviceClickListener);
        (this.newDevicesListView = (ListView)this.findViewById(R.id.new_devices)).setAdapter((ListAdapter)this.mNewDevicesArrayAdapter);
        this.newDevicesListView.setOnItemClickListener(this.mDeviceClickListener);
        this.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        this.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        final Set<BluetoothDevice> bondedDevices = this.mBtAdapter.getBondedDevices();
        if (bondedDevices.size() > 0) {
            this.findViewById(2131034188).setVisibility(0);
            for (final BluetoothDevice bluetoothDevice : bondedDevices) {
                this.mPairedDevicesArrayAdapter.add((String.valueOf(bluetoothDevice.getName()) + "\n" + bluetoothDevice.getAddress()));
            }
            return;
        }
        this.mPairedDevicesArrayAdapter.add("No Paired Devices");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mBtAdapter != null) {
            this.mBtAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(this.mReceiver);
    }
    
    public void on_clk(final View view) {
        this.doDiscovery();
    }
}
