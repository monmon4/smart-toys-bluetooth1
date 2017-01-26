package com.example.gottago;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            thread2.write(send);
            timerHandler.postDelayed(this, 250);
        }
    };


    Button connection;
    private ImageButton forward,backward,left,right,forward_left,forward_right,backward_left,backward_right,stop;

    private static final String TAG = "MainActivity";
    private ConnectedThread thread2;
    public static final byte[] send = new byte[1];


    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;
    }


    private Handler mHandler; // handler that gets info from Bluetooth service


    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        connection = (Button) findViewById(R.id.connect);


        forward = (ImageButton) findViewById(R.id.forward_button);
        backward = (ImageButton) findViewById(R.id.backward_button);
        left = (ImageButton) findViewById(R.id.left_button);
        right = (ImageButton) findViewById(R.id.right_button);
        forward_left = (ImageButton) findViewById(R.id.forward_left_button);
        forward_right = (ImageButton) findViewById(R.id.forward_right_button);
        backward_left = (ImageButton) findViewById(R.id.backward_left_button);
        backward_right = (ImageButton) findViewById(R.id.backward_right_button);
        stop = (ImageButton) findViewById(R.id.stop_button);

        forward.setEnabled(false);
        backward.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);
        forward_left.setEnabled(false);
        forward_right.setEnabled(false);
        backward_left.setEnabled(false);
        backward_right.setEnabled(false);
        stop.setEnabled(false);


        connection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                forward.setEnabled(true);
                backward.setEnabled(true);
                left.setEnabled(true);
                right.setEnabled(true);
                forward_left.setEnabled(true);
                forward_right.setEnabled(true);
                backward_left.setEnabled(true);
                backward_right.setEnabled(true);
                stop.setEnabled(true);

                Intent serverIntent = new Intent(MainActivity.this , DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);


            }
        });


        /*forward .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA0;
                thread2.write(send);

            }
        });

        backward .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA1;
                thread2.write(send);

            }
        });

        left .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA2;
                thread2.write(send);

            }
        });

        right .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA3;
                thread2.write(send);

            }
        });

        forward_left .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA4;
                thread2.write(send);

            }
        });

        forward_right .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA5;
                thread2.write(send);

            }
        });

        backward_left .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA6;
                thread2.write(send);

            }
        });

        backward_right .setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                byte[] send = new byte[1];
                send[0] = (byte) 0xA7;
                thread2.write(send);

            }
        });*/


        forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        left.setEnabled(false);
                        right.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward_right.setEnabled(false);
                        backward_left.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA0;
                        timerHandler.postDelayed(timerRunnable, 0);


                       return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward_right.setEnabled(true);
                        backward_left.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);


                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        forward.setEnabled(false);
                        left.setEnabled(false);
                        right.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward_right.setEnabled(false);
                        backward_left.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);


                        send[0] = (byte) 0xA1;
                        timerHandler.postDelayed(timerRunnable, 0);

                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        forward.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward_right.setEnabled(true);
                        backward_left.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        forward.setEnabled(false);
                        right.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward_right.setEnabled(false);
                        backward_left.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA2;
                        timerHandler.postDelayed(timerRunnable, 0);

                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        forward.setEnabled(true);
                        right.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward_right.setEnabled(true);
                        backward_left.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        left.setEnabled(false);
                        forward.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward_right.setEnabled(false);
                        backward_left.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA3;
                        timerHandler.postDelayed(timerRunnable, 0);

                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        left.setEnabled(true);
                        forward.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward_right.setEnabled(true);
                        backward_left.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        forward_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        left.setEnabled(false);
                        right.setEnabled(false);
                        forward.setEnabled(false);
                        forward_right.setEnabled(false);
                        backward_left.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA4;
                        timerHandler.postDelayed(timerRunnable, 0);
                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        forward.setEnabled(true);
                        forward_right.setEnabled(true);
                        backward_left.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        forward_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        left.setEnabled(false);
                        right.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward.setEnabled(false);
                        backward_left.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA5;
                        timerHandler.postDelayed(timerRunnable, 0);

                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward.setEnabled(true);
                        backward_left.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        backward_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        left.setEnabled(false);
                        right.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward_right.setEnabled(false);
                        forward.setEnabled(false);
                        backward_right.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA6;
                        timerHandler.postDelayed(timerRunnable, 0);

                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward_right.setEnabled(true);
                        forward.setEnabled(true);
                        backward_right.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

        backward_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //disabling the other buttons
                        backward.setEnabled(false);
                        left.setEnabled(false);
                        right.setEnabled(false);
                        forward_left.setEnabled(false);
                        forward_right.setEnabled(false);
                        backward_left.setEnabled(false);
                        forward.setEnabled(false);
                        stop.setEnabled(false);

                        send[0] = (byte) 0xA7;
                        timerHandler.postDelayed(timerRunnable, 0);

                        return true;

                    case MotionEvent.ACTION_UP:
                        //enabling the buttons
                        backward.setEnabled(true);
                        left.setEnabled(true);
                        right.setEnabled(true);
                        forward_left.setEnabled(true);
                        forward_right.setEnabled(true);
                        backward_left.setEnabled(true);
                        forward.setEnabled(true);
                        stop.setEnabled(true);

                        timerHandler.removeCallbacks(timerRunnable);
                        return true;
                }

                return false;
            }

        });

    }






    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        }
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    //setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        ConnectThread thread1 = new ConnectThread(device);
        thread1.run();

    }


    public class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            thread2 = new ConnectedThread(mmSocket);
            thread2.run();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }


        private class ConnectedThread extends Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;
            private byte[] mmBuffer; // mmBuffer store for the stream

            public ConnectedThread(BluetoothSocket socket) {
                mmSocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                // Get the input and output streams; using temp objects because
                // member streams are final.
                try {
                    tmpIn = socket.getInputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating input stream", e);
                }
                try {
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating output stream", e);
                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }

            public void run() {
                mmBuffer = new byte[1024];
                int numBytes; // bytes returned from read()

                // Keep listening to the InputStream until an exception occurs.
                /*while (true) {
                    try {
                        // Read from the InputStream.
                        numBytes = mmInStream.read(mmBuffer);
                        // Send the obtained bytes to the UI activity.
                        Message readMsg = mHandler.obtainMessage(
                                MessageConstants.MESSAGE_READ, numBytes, -1,
                                mmBuffer);
                        readMsg.sendToTarget();
                    } catch (IOException e) {
                        Log.d(TAG, "Input stream was disconnected", e);
                        break;
                    }
                }*/
            }

            // Call this from the main activity to send data to the remote device.
            public void write(byte[] bytes) {
                try {
                        mmOutStream.write(bytes);



                    // Share the sent message with the UI activity.
                    /*Message writtenMsg = mHandler.obtainMessage(
                            MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                    writtenMsg.sendToTarget();*/
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when sending data", e);

                    // Send a failure message back to the activity.
                    Message writeErrorMsg =
                            mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                    Bundle bundle = new Bundle();
                    bundle.putString("toast",
                            "Couldn't send data to the other device");
                    writeErrorMsg.setData(bundle);
                    mHandler.sendMessage(writeErrorMsg);
                }
            }

            // Call this method from the main activity to shut down the connection.
            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "Could not close the connect socket", e);
                }
            }
        }
    }

