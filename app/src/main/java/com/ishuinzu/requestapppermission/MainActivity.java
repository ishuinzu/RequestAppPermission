package com.ishuinzu.requestapppermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    private Button btnAskPermission, btnCheckPermission;
    private static final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAskPermission = findViewById(R.id.btnAskPermission);
        btnCheckPermission = findViewById(R.id.btnCheckPermission);

        btnAskPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ask Permission From User
                askPermission();
            }
        });
        btnCheckPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check Permission
                checkPermission();
            }
        });
    }

    private void checkPermission() {
        //Check Device Version [For Marshmallow Devices]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check Permission
            if (ActivityCompat.checkSelfPermission(
                    MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Already Granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Is Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void askPermission() {
        //Check Device Version [For Marshmallow Devices]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check Permission
            if (ActivityCompat.checkSelfPermission(
                    MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Already Granted.", Toast.LENGTH_SHORT).show();
            } else {
                //Ask For Permission
                Toast.makeText(this, "Grant Permission First.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE
                );
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            //Check If Permission Granted Or Denied
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted.", Toast.LENGTH_SHORT).show();
            } else {
                //If Permission Denied
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //Show A Dialog Why You Really Needed The Permission
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
                    builder.setTitle("Permission Request");
                    builder.setMessage("Read External Storage Permission Is Required To Perform The Intended Operation.");
                    builder.setCancelable(false);
                    builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            askPermission();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE);
                }
            }
        }
    }
}