package com.example.a3rdeye.enabler;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a3rdeye.R;
import com.example.a3rdeye.declaration.Declaration_Page1_Enabler;

public class PhoneDetails_Enabler extends AppCompatActivity {

    TextView textWifiStatus, textManufacture, textModel, textDeviceName, textMobileNetworkStatus, textKernelVersion;
    TextView textSerial, textBasebandVersion, textOSVersion, textBuildNumber, textModel1, textDeviceName1, textFINGERPRINT;
    TextView textScreenResolution, textBluetoothStatus, textSerialNumber, textScreenResolution1, textSdkVersion, textHost;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details__enabler);


        textDeviceName = findViewById(R.id.device_name);
        textModel = findViewById(R.id.model);
        textManufacture = findViewById(R.id.manufacture);
        textSerial = findViewById(R.id.serial);
        textBasebandVersion = findViewById(R.id.baseband);
        textOSVersion = findViewById(R.id.version_os);
        textKernelVersion = findViewById(R.id.kernel);
        textWifiStatus = findViewById(R.id.wifi_status);
        textMobileNetworkStatus = findViewById(R.id.mobile_network_status);
        textBluetoothStatus = findViewById(R.id.bluetooth_status);
        textScreenResolution = findViewById(R.id.screen_res);
        textBuildNumber = findViewById(R.id.build_num);

        textDeviceName1 = findViewById(R.id.device_name1);
        textModel1 = findViewById(R.id.model1);
        textScreenResolution1 = findViewById(R.id.screen_res1);
        textSdkVersion = findViewById(R.id.sdk);
        textFINGERPRINT = findViewById(R.id.fingerprint);
        textHost = findViewById(R.id.host);

        button = findViewById(R.id.next);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PhoneDetails_Enabler.this, Declaration_Page1_Enabler.class);
                startActivity(intent);
            }
        });

        getDeviceInformation();

    }


    public String getDeviceScreenResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x; //device width
        int height = size.y; //device height

        return "" + width + " x " + height; //example "480 * 800"
    }

    private void getDeviceInformation() {

        textDeviceName.setText(Build.PRODUCT);
        textDeviceName1.setText(Build.ID);
        textModel1.setText(Build.BRAND); // get model name
        textModel.setText(Build.MODEL); // get model name
        textManufacture.setText(Build.MANUFACTURER); // get device manufacture
        textSerial.setText(Build.VERSION.INCREMENTAL); // get device Serial
        textBasebandVersion.setText(Build.getRadioVersion());
        textOSVersion.setText(Build.VERSION.RELEASE); // get OS version
        textKernelVersion.setText(System.getProperty("os.version")); // get kernel version
        textWifiStatus.setText(isWifiNetworkAvailable(PhoneDetails_Enabler.this)); // check wifi connection
        textMobileNetworkStatus.setText(isMobileNetworkAvailable(PhoneDetails_Enabler.this)); //check 3G connection
        textBluetoothStatus.setText(checkBluetoothConnection()); //check bluetooth status
        textScreenResolution.setText(getDeviceScreenResolution()); //get device resolution
        textBuildNumber.setText(Build.FINGERPRINT); //get Device's Build Number
        textScreenResolution1.setText(Build.DISPLAY); //get device resolution
        textSdkVersion.setText(Build.VERSION.SDK);
        textFINGERPRINT.setText(Build.FINGERPRINT);
        textHost.setText(Build.HOST);

    }

    /**
     * Checks if the device has Wifi connection.
     */
    public String isWifiNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return "Connected";
        } else return "Disconnected";
    }

    /**
     * Checks if the device has 3G or other mobile data connection.
     */

    public String isMobileNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return "Connected";
        } else {
            return "Disconnected";
        }

    }

    private String checkBluetoothConnection() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return "Not supported";
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return "Disabled";
            } else {
                return "Enabled";
            }
        }
    }
}