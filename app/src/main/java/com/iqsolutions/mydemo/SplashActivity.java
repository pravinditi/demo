package com.iqsolutions.mydemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iqsolutions.mydemo.Utils.CommonMethods;
import com.iqsolutions.mydemo.interfaces.BaseInterface;


public class SplashActivity extends AppCompatActivity implements BaseInterface {

    private int TIME_OUT_MILLIS = 5000;
    private long TIME_START_MILLIS = 0;
    private final int PERMISSIONS_REQUEST = 1234;
    private Context context;
    private String machineNo = "";
    private final int REQUEST = 112;
    private String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        loadDefaultData();
    }

    @Override
    public void init() {
        context = this;
    }


    @Override
    public void loadDefaultData() {
        try {
            //copy db
            if (CommonMethods.copyDatabase(this)) {
                Toast.makeText(this, "copy database success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "copy database error", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, "Database Sucessfully Copied", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);




    }

}
