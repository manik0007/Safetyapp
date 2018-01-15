package com.example.admin.safetyapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 10/26/2017.
 */

public class EmailSending extends Service implements ServerResponse {
    IBinder mBinder;
    int mStartMode;
    SendEmail f;
    int j=0;
    String latitude="",longitude="";
    String username="",emailid="",password="";
    String[] emergencyemailid=new String[10];
    String[] emergencynames=new String[10];
    int len=0;
    public EmailSending() {

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        emergencynames=intent.getStringArrayExtra("names");
        emergencyemailid=intent.getStringArrayExtra("emailid");
        len=intent.getIntExtra("count",5);
        username=intent.getStringExtra("username");
        emailid=intent.getStringExtra("id");
        password=intent.getStringExtra("password");
        Log.d("Service","in service");
        /*f=new SendEmail(latitude,longitude);
        f.delegate=this;
        f.execute("");
        */
        try {
            for(j=0;j<len;j++)
            {


                GMailSender sender = new GMailSender(emailid, password);
                sender.sendMail("Help Required",
                        "This email has been sent by safety app on behalf of "+username+". He requires your help and his latitude is " + latitude + " and longitude is " + longitude,
                        emergencyemailid[j],
                        emergencyemailid[j]);
            }
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }



        return mStartMode;
    }


    public void ServerResponds(String result) {


            Toast.makeText(this,"msg sent",Toast.LENGTH_LONG).show();


    }


}


