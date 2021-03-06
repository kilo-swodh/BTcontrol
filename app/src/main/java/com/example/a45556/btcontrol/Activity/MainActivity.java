package com.example.a45556.btcontrol.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a45556.btcontrol.R;
import com.example.a45556.btcontrol.bluetooth.BluetoothChatService;
import com.example.a45556.btcontrol.bluetooth.DeviceListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.a45556.btcontrol.R.id.tv_info_led1;
import static com.example.a45556.btcontrol.R.id.tv_info_led2;
import static com.example.a45556.btcontrol.R.id.tv_info_led3;
import static com.example.a45556.btcontrol.R.id.tv_info_led4;
import static com.example.a45556.btcontrol.R.id.tv_info_led5;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.DEVICE_NAME;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.MESSAGE_DEVICE_NAME;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.MESSAGE_DIALOG;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.MESSAGE_READ;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.MESSAGE_STATE_CHANGE;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.MESSAGE_TOAST;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.MESSAGE_WRITE;
import static com.example.a45556.btcontrol.bluetooth.BluetoothChatService.TOAST;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnSettings,btnCustom,btnInfo;
    private SeekBar skLed1,skLed2,skLed3,skLed4,skLed5;
    private TextView tvLED1,tvLED2,tvLED3,tvLED4,tvLED5,tvTip;
    private CheckBox cb1,cb2,cb3,cb4,cb5;

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CMD = 3;

    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothChatService mChatService = null;
    private String mConnectedDeviceName = null;

    private static StringBuilder deLost = null;
    private List<String> stringArrayList;
    private StringBuilder cmdBuild;
    private boolean isReadyNext;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        btnSettings =(ImageButton)findViewById(R.id.settings);
        btnCustom = (ImageButton)findViewById(R.id.custom);
        btnInfo = (ImageButton)findViewById(R.id.btn_info);
        skLed1 = (SeekBar)findViewById(R.id.sk_live_led1);
        tvLED1 = (TextView)findViewById(tv_info_led1);
        skLed2 = (SeekBar)findViewById(R.id.sk_live_led2);
        tvLED2 = (TextView)findViewById(tv_info_led2);
        skLed3 = (SeekBar)findViewById(R.id.sk_live_led3);
        tvLED3 = (TextView)findViewById(tv_info_led3);
        skLed4 = (SeekBar)findViewById(R.id.sk_live_led4);
        tvLED4 = (TextView)findViewById(tv_info_led4);
        skLed5 = (SeekBar)findViewById(R.id.sk_live_led5);
        tvLED5 = (TextView)findViewById(tv_info_led5);
        tvTip = (TextView)findViewById(R.id.tv_tip);
        cb1 = (CheckBox)findViewById(R.id.cb_1);
        cb2 = (CheckBox)findViewById(R.id.cb_2);
        cb3 = (CheckBox)findViewById(R.id.cb_3);
        cb4 = (CheckBox)findViewById(R.id.cb_4);
        cb5 = (CheckBox)findViewById(R.id.cb_5);
        initListener();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "手机无蓝牙设备", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }else{
            if(!mBluetoothAdapter.isEnabled()){
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
            }else{
                // Initialize the BluetoothChatService to perform bluetooth connections
                mChatService = new BluetoothChatService(this, mHandler);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChatService != null && mBluetoothAdapter.isEnabled()) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                mChatService.start();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mChatService != null) mChatService.stop();
        if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    if(mChatService == null){
                        mChatService = new BluetoothChatService(this, mHandler);
                        mChatService.start();
                    }
                    mChatService.connect(device);
                }
                break;

            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, R.string.bt_enabled_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case REQUEST_CMD:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (mChatService == null ||(mChatService.getState() != BluetoothChatService.STATE_CONNECTED)) {
                        Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    stringArrayList = bundle.getStringArrayList("cmd");
                    dialog = ProgressDialog.show(this,"正在预约","操作进行中,请勿退出App",true,true);
                    dialog.setCancelable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendCustomCmd((ArrayList<String>) stringArrayList);
                        }
                    }).start();
                }else {
                    Toast.makeText(this,"未预约",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void sendCustomCmd(final ArrayList<String> cmd){
        long currentTime = 0;
        long taskTime = 0;
        int count = 0;
        isReadyNext = true;
        cmdBuild = new StringBuilder();
        for ( String mess : cmd){
            if (mess.startsWith("$$") || mess.startsWith("%%")){
                cmdBuild.append(mess);
                cmdBuild.append("-");
            }else {
                if(!"".equals(mess)){
                    taskTime = Long.parseLong(mess);
                }
                else
                    taskTime = 1;
            }
            if (count >= 5){
                isReadyNext = false;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String[] cmdArry = cmdBuild.toString().split("-");         //间隔符
                        for (String cmd : cmdArry){
                            Log.d("发送了命令 ", cmd);
                            sendMessage(cmd);
                        }
                        isReadyNext = true;
                    }
                },currentTime*1000);
                if(!isReadyNext){
                    while (true){
                        if (isReadyNext){
                            break;
                        }
                    }
                }
                cmdBuild.delete(0,cmdBuild.length());
                count = 0;
                currentTime = taskTime;
                continue;
            }
            count++;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendMessage("~~");
                mHandler.obtainMessage(MESSAGE_DIALOG).sendToTarget();
            }
        },currentTime*1000);

    }

    private void resetLights(){
        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);
        cb5.setChecked(false);
        skLed1.setProgress(70);
        skLed2.setProgress(70);
        skLed3.setProgress(70);
        skLed4.setProgress(70);
        skLed5.setProgress(70);
        tvLED1.setText("100%亮度");
        tvLED2.setText("100%亮度");
        tvLED3.setText("100%亮度");
        tvLED4.setText("100%亮度");
        tvLED5.setText("100%亮度");
    }

    /**
     * @Decription 初始化各个按钮效果
     **/
    private void initListener(){

        /*渐变SeekBar部分*/
        skLed1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvLED1.setText((i+30)+"%亮度");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                deLost = new StringBuilder();
                deLost.append("$$1#"+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                deLost.append("&"+seekBar.getProgress()+"*");
                sendMessage(deLost.toString());
            }
        });

        skLed2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvLED2.setText((i+30)+"%亮度");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                deLost = new StringBuilder();
                deLost.append("$$2#"+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                deLost.append("&"+seekBar.getProgress()+"*");
                sendMessage(deLost.toString());
            }
        });

        skLed3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvLED3.setText((i+30)+"%亮度");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                deLost = new StringBuilder();
                deLost.append("$$3#"+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                deLost.append("&"+seekBar.getProgress()+"*");
                sendMessage(deLost.toString());
            }
        });

        skLed4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvLED4.setText((i+30)+"%亮度");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                deLost = new StringBuilder();
                deLost.append("$$4#"+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                deLost.append("&"+seekBar.getProgress()+"*");
                sendMessage(deLost.toString());
            }
        });

        skLed5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvLED5.setText((i+30)+"%亮度");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                deLost = new StringBuilder();
                deLost.append("$$5#"+seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                deLost.append("&"+seekBar.getProgress()+"*");
                sendMessage(deLost.toString());
            }
        });

        /*闪烁CheckBox部分*/
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sendMessage("%%1b*");
                    skLed1.setEnabled(false);
                }else {
                    sendMessage("%%1o*");
                    skLed1.setEnabled(true);
                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sendMessage("%%2b*");
                    skLed2.setEnabled(false);
                }else {
                    sendMessage("%%2o*");
                    skLed2.setEnabled(true);
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sendMessage("%%3b*");
                    skLed3.setEnabled(false);
                }else {
                    sendMessage("%%3o*");
                    skLed3.setEnabled(true);
                }
            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sendMessage("%%4b*");
                    skLed4.setEnabled(false);
                }else {
                    sendMessage("%%4o*");
                    skLed4.setEnabled(true);
                }
            }
        });

        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sendMessage("%%5b*");
                    skLed5.setEnabled(false);
                }else {
                    sendMessage("%%5o*");
                    skLed5.setEnabled(true);
                }
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        /*功能键部分*/
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            }
        });

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, LightSettingActivity.class);
                startActivityForResult(serverIntent, REQUEST_CMD);
            }
        });
    }

    /**
     * Sends a message.
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService == null ||(mChatService.getState() != BluetoothChatService.STATE_CONNECTED)) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            Toast.makeText(getApplicationContext(), "正在连接该蓝牙设备", Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d("BlueTooth","readMessage:"+readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "连接上 "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    tvTip.setVisibility(View.GONE);
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_DIALOG:
                    if (dialog != null){
                        dialog.dismiss();
                        resetLights();
                    }
                    break;
            }
        }
    };
}
