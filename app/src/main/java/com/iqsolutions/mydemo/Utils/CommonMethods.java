package com.iqsolutions.mydemo.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.iqsolutions.mydemo.database.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CommonMethods {

    private static final String TAG = CommonMethods.class.getSimpleName();


    public static boolean copyDatabase(Context context) {

        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DATABASE_NAME);

            File f = new File(DatabaseHelper.DATABASE_PATH);

            if (f.exists()) {

                return true;
            } else {

                if (f.createNewFile()){
                    String outFileName = DatabaseHelper.DATABASE_PATH;
                    OutputStream outputStream = new FileOutputStream(outFileName);
                    if (f.getParentFile().exists() && !f.getParentFile().mkdir()) {
                        byte[] buffer = new byte[1024];
                        int length = 0;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                        outputStream.flush();
                        outputStream.close();
                        Log.w("MainActivity", "DB copy");
                        return true;
                    } else {
                        return false;
                    }
                }else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isConnected(Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiNetwork = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting()) {
                return true;
            }

            NetworkInfo mobileNetwork = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null
                    && mobileNetwork.isConnectedOrConnecting()) {
                return true;
            }

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }
}
