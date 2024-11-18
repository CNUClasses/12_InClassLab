package com.example.a12_inclasslab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String[]
            PERMISSIONS={Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS};

    private static final int PERMS_REQ_CODE = 200;     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyPermissions();
    }

    /**
     * Verify that the specific list of permisions requested have been granted, otherwise ask for
     * these permissions. Note this is coarse in that I assumme I need them all
     */
    private boolean verifyPermissions() {
        //loop through all permissions seeing if they are ALL granted
        //iff ALL granted then return true
        boolean allGranted = true;
        for (String permission:PERMISSIONS){
            //a single false causes allGranted to be false
            allGranted = allGranted && (ActivityCompat.checkSelfPermission(this, permission ) ==
                    PackageManager.PERMISSION_GRANTED);
        }
        if (!allGranted) {
            //OH NO!, missing some permissions, offer rationale if needed
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Snackbar.make(findViewById(android.R.id.content),
                            permission+" WE GOTTA HAVE IT!", Snackbar.LENGTH_LONG).show();
                }
            }
            //Okay now finally ask for them
            requestPermissions(PERMISSIONS, PERMS_REQ_CODE);
        }
        //return whether they are granted or not
        return allGranted;
    }

    /***
     * callback from requestPermissions
     * @param permsRequestCode user defined code passed to requestpermissions used to identify what
    callback is coming in
     * @param permissions list of permissions requested
     * @param grantResults //results of those requests
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults);
        boolean allGranted = true;
        switch (permsRequestCode) {
            case PERMS_REQ_CODE:
                for (int result: grantResults){
                    allGranted = allGranted&&(result== PackageManager.PERMISSION_GRANTED);
                }
                break;
        }
    }
}
