package com.example.admin.safetyapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;
    private LocationManager locationManager;

    int no=0;

    String emergencyname[]=new String[10];
    String emergencyemailid[]=new String[10];
    String contactname;
    String contactemailid;
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_EMAILID = "emailid";
    private static final String TAG = "Debug";
    private Boolean flag = false;
    private int check=0;
    private  int allowed=0;
    int i=1;
    LocationManager mLocationManager;
    String username="";
    String password="";
    String emailid="";
    SqliteDatabase create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create = new SqliteDatabase(getBaseContext());
        i=0;
        no=0;


        String a[] = new String[]{TAG_USERNAME, TAG_EMAILID};
        SQLiteDatabase db = create.getReadableDatabase();

        Cursor c = db.query("emergency", a, null, null, null, null, null, null);
        while (c.moveToNext()) {
            contactname = c.getString(c.getColumnIndexOrThrow(TAG_USERNAME));
            contactemailid = c.getString(c.getColumnIndexOrThrow(TAG_EMAILID));
            emergencyname[i]=contactname;
            emergencyemailid[i]=contactemailid;

            i++;
            if(emergencyemailid[no]!=null)
            {
                String l=""+emergencyemailid[no]+ " "+no;
                Log.d("display",l);
                no++;
            }
        }





        setContentView(R.layout.activity_main);
        Bundle b=getIntent().getExtras();
        username=b.getString("username");
        password=b.getString("password");
        emailid=b.getString("emailid");
        String l=""+username+" "+password+" "+emailid+" ";
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);







    }

    public void sendmail(android.view.View v) throws Exception {
        Log.d("reached", "sendmail");


        flag = displayGpsStatus();
        if (flag) {

            Log.d(TAG, "onClick");






            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Log.d("check", "found permission");
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Log.d("check","inside else for gethhing location");
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


            Toast.makeText(this, "Mails set to send", Toast.LENGTH_LONG).show();
            Log.d("check","ahead of timer");

        } else {
            Log.d("gps","switched off");
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }








    }
    public void logout(android.view.View v)
    {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", true).commit();



        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(i);
    }

    public void emergency(android.view.View v) throws Exception {
        Intent i = new Intent(this, EmergencyContacts.class);
        Log.d("count in main",""+no);
        startActivity(i);

    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }



    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("verify","in location changed");
        String latitude=""+location.getLatitude();
        String longitude=""+location.getLongitude();
        String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();


        Log.d("msg",msg);
        check++;
        if(check==1)
        {
            Intent serviceIntent = new Intent(this, EmailSending.class);
            serviceIntent.putExtra("latitude", latitude);
            serviceIntent.putExtra("longitude", longitude);
            serviceIntent.putExtra("username",username);
            serviceIntent.putExtra("id",emailid);
            serviceIntent.putExtra("password",password);
            serviceIntent.putExtra("count", no);
            serviceIntent.putExtra("names", emergencyname);
            serviceIntent.putExtra("emailid", emergencyemailid);
            this.startService(serviceIntent);
        }check++;

    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }





}