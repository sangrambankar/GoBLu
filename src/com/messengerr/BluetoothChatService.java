package com.messengerr;

import android.annotation.*;
import java.util.*;
import android.net.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.bluetooth.*;
import java.io.*;

@SuppressLint({ "NewApi" })
public class BluetoothChatService
{
    private static final boolean D = true;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200fffff");
    private static final String NAME_INSECURE = "BluetoothChatInsecure";
    private static final String NAME_SECURE = "BluetoothChatSecure";
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_NONE = 0;
    private static final String TAG = "BluetoothChatService";
    Boolean img;
    Uri imguri;
    private final BluetoothAdapter mAdapter;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private final Handler mHandler;
    private AcceptThread mInsecureAcceptThread;
    private AcceptThread mSecureAcceptThread;
    private int mState;
    
    public BluetoothChatService(final Context context, final Handler mHandler) {
        super();
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mState = 0;
        this.mHandler = mHandler;
    }
    
    private void connectionFailed() {
        final Message obtainMessage = this.mHandler.obtainMessage(5);
        final Bundle data = new Bundle();
        data.putString("toast", "Unable to connect device");
        obtainMessage.setData(data);
        this.mHandler.sendMessage(obtainMessage);
        this.start();
    }
    
    private void connectionLost() {
        final Message obtainMessage = this.mHandler.obtainMessage(5);
        final Bundle data = new Bundle();
        data.putString("toast", "Device connection was lost");
        obtainMessage.setData(data);
        this.mHandler.sendMessage(obtainMessage);
        this.start();
    }
    
    private void setState(final int mState) {
        synchronized (this) {
            Log.d("BluetoothChatService", "setState() " + this.mState + " -> " + mState);
            this.mState = mState;
            this.mHandler.obtainMessage(1, mState, -1).sendToTarget();
        }
    }
    
    public void connect(final BluetoothDevice bluetoothDevice, final boolean b) {
        synchronized (this) {
            Log.d("BluetoothChatService", "connect to: " + bluetoothDevice);
            if (this.mState == 2 && this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            (this.mConnectThread = new ConnectThread(bluetoothDevice, b)).start();
            this.setState(2);
        }
    }
    
    public void connected(final BluetoothSocket bluetoothSocket, final BluetoothDevice bluetoothDevice, final String s) {
        synchronized (this) {
            Log.d("BluetoothChatService", "connected, Socket Type:" + s);
            if (this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            if (this.mSecureAcceptThread != null) {
                this.mSecureAcceptThread.cancel();
                this.mSecureAcceptThread = null;
            }
            if (this.mInsecureAcceptThread != null) {
                this.mInsecureAcceptThread.cancel();
                this.mInsecureAcceptThread = null;
            }
            (this.mConnectedThread = new ConnectedThread(bluetoothSocket, s)).start();
            final Message obtainMessage = this.mHandler.obtainMessage(4);
            final Bundle data = new Bundle();
            data.putString("device_name", bluetoothDevice.getName());
            obtainMessage.setData(data);
            this.mHandler.sendMessage(obtainMessage);
            this.setState(3);
        }
    }
    
    public int getState() {
        synchronized (this) {
            return this.mState;
        }
    }
    
    public void start() {
        synchronized (this) {
            Log.d("BluetoothChatService", "start");
            if (this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            this.setState(1);
            if (this.mSecureAcceptThread == null) {
                (this.mSecureAcceptThread = new AcceptThread(true)).start();
            }
            if (this.mInsecureAcceptThread == null) {
                (this.mInsecureAcceptThread = new AcceptThread(false)).start();
            }
        }
    }
    
    public void stop() {
        synchronized (this) {
            Log.d("BluetoothChatService", "stop");
            if (this.mConnectThread != null) {
                this.mConnectThread.cancel();
                this.mConnectThread = null;
            }
            if (this.mConnectedThread != null) {
                this.mConnectedThread.cancel();
                this.mConnectedThread = null;
            }
            if (this.mSecureAcceptThread != null) {
                this.mSecureAcceptThread.cancel();
                this.mSecureAcceptThread = null;
            }
            if (this.mInsecureAcceptThread != null) {
                this.mInsecureAcceptThread.cancel();
                this.mInsecureAcceptThread = null;
            }
            this.setState(0);
        }
    }
    
    public void tellRecipient(final String s) {
        synchronized (this) {
            if (this.mState != 3) {
                return;
            }
            final ConnectedThread mConnectedThread = this.mConnectedThread;
        }
    }
    
    public void write(final byte[] array) {
        synchronized (this) {
            if (this.mState != 3) {
                return;
            }
            final ConnectedThread mConnectedThread = this.mConnectedThread;
            // monitorexit(this)
            mConnectedThread.write(array);
        }
    }
    
    private class AcceptThread extends Thread
    {
        private String mSocketType;
        private BluetoothServerSocket mmServerSocket;
        
        public AcceptThread(final boolean b) {
        	BluetoothServerSocket tmp = null;
                            mSocketType = b ? "Secure":"Insecure";
                                    try {
                                    	if(b){
                                        BluetoothServerSocket mmServerSocket = BluetoothChatService.this.mAdapter.listenUsingRfcommWithServiceRecord("BluetoothChatSecure", BluetoothChatService.MY_UUID_SECURE);
                                        this.mmServerSocket = mmServerSocket;
                                    	}else{
                                        mmServerSocket = BluetoothChatService.this.mAdapter.listenUsingInsecureRfcommWithServiceRecord("BluetoothChatInsecure", BluetoothChatService.MY_UUID_INSECURE);
                                        this.mmServerSocket = mmServerSocket;
                                    	}
                                    }
                                    catch (IOException ex) {
                                        Log.e("BluetoothChatService", "Socket Type: " + this.mSocketType + "listen() failed", (Throwable)ex);
                                    }
                }
        
        public void cancel() {
            Log.d("BluetoothChatService", "Socket Type" + this.mSocketType + "cancel " + this);
            try {
                this.mmServerSocket.close();
            }
            catch (IOException ex) {
                Log.e("BluetoothChatService", "Socket Type" + this.mSocketType + "close() of server failed", (Throwable)ex);
            }
        }
        
        @Override
        public void run() {
            Log.d("BluetoothChatService", "Socket Type: " + this.mSocketType + "BEGIN mAcceptThread" + this);
            this.setName("AcceptThread" + this.mSocketType);

            BluetoothSocket socket;
                while (BluetoothChatService.this.mState != 3) {
                                BluetoothSocket accept = null;
                                try {
                                    accept = this.mmServerSocket.accept();
                                }
                                catch (IOException ex) {
                                    Log.e("BluetoothChatService", "Socket Type: " + this.mSocketType + "accept() failed", (Throwable)ex);
                                    break;
                                }
                                
                                
                                if (accept != null){
                                	synchronized (BluetoothChatService.this) {
                                		switch (mState) {
                                        case STATE_LISTEN:
                                        case STATE_CONNECTING:
                                            // Situation normal. Start the connected thread.
                                            connected(accept, accept.getRemoteDevice(),
                                                    mSocketType);
                                            break;
                                        case STATE_NONE:
                                        case STATE_CONNECTED:
                                            // Either not ready or already connected. Terminate new socket.
                                            try {
                                                accept.close();
                                            } catch (IOException e) {
                                                Log.e(TAG, "Could not close unwanted socket", e);
                                            }
                                            break;
                                        }
									}
                                }
                                
                }
            Log.i("BluetoothChatService", "END mAcceptThread, socket Type: " + this.mSocketType);
        }
    }
    
    private class ConnectThread extends Thread
    {
        private String mSocketType;
        private final BluetoothDevice Device;
        private final BluetoothSocket mmSocket;
        
        public ConnectThread(BluetoothDevice mmDevice, final boolean b) {
        	Device = mmDevice;
             BluetoothSocket tmp = null;
             mSocketType = b ? "Secure" : "Insecure";

             // Get a BluetoothSocket for a connection with the
             // given BluetoothDevice
             try {
                 if (b) {
                     tmp = mmDevice.createRfcommSocketToServiceRecord(
                             MY_UUID_SECURE);
                 } else {
                     tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(
                             MY_UUID_INSECURE);
                 }
             } catch (IOException e) {
                 Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
             }
             mmSocket = tmp;
        }
        
        public void cancel() {
            try {
                this.mmSocket.close();
            }
            catch (IOException ex) {
                Log.e("BluetoothChatService", "close() of connect " + this.mSocketType + " socket failed", (Throwable)ex);
            }
        }
        
        @Override
        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, Device, mSocketType);

        }
            
    }
    
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;
        
        public ConnectedThread(final BluetoothSocket socket, final String socketType) {
        	 Log.d(TAG, "create ConnectedThread: " + socketType);
             mmSocket = socket;
             InputStream tmpIn = null;
             OutputStream tmpOut = null;

             // Get the BluetoothSocket input and output streams
             try {
                 tmpIn = socket.getInputStream();
                 tmpOut = socket.getOutputStream();
             } catch (IOException e) {
                 Log.e(TAG, "temp sockets not created", e);
             }

             mmInStream = tmpIn;
             mmOutStream = tmpOut;
        }
        
        public void cancel() {
            try {
                this.mmSocket.close();
            }
            catch (IOException ex) {
                Log.e("BluetoothChatService", "close() of connect socket failed", (Throwable)ex);
            }
        }
        
        @Override
        public void run() {
        	 Log.i(TAG, "BEGIN mConnectedThread");
             byte[] buffer = new byte[1024];
             int bytes;

             // Keep listening to the InputStream while connected
             while (true) {
                 try {
                     // Read from the InputStream
                     bytes = mmInStream.read(buffer);

                     // Send the obtained bytes to the UI Activity
                     mHandler.obtainMessage(2, bytes, -1, buffer)
                             .sendToTarget();
                 } catch (IOException e) {
                     Log.e(TAG, "disconnected", e);
                     connectionLost();
                     break;
                 }
             }
        }
        
        public void write(final byte[] array) {
            try {
                this.mmOutStream.write(array);
                BluetoothChatService.this.mHandler.obtainMessage(3, -1, -1, array).sendToTarget();
            }
            catch (IOException ex) {
                Log.e("BluetoothChatService", "Exception during write", (Throwable)ex);
            }
        }
    }
}
