package com.example.admin.safetyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Launcher extends AppCompatActivity {
    SqliteDatabase create;
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_EMAILID = "emailid";
    MyPreferences a;
    String username = "";
    String password = "";
    String emailid = "";

    EditText mName;
    EditText mPassword;
    EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        create = new SqliteDatabase(getBaseContext());
        super.onCreate(savedInstanceState);


        Cursor cursor = create.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", "personal"});



        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if ((isFirstRun)) {
            //show start activity










                setContentView(R.layout.activity_launcher);
            }else {
                Intent i = new Intent(this, MainActivity.class);
                String a[] = new String[]{TAG_USERNAME, TAG_PASSWORD, TAG_EMAILID};
                SQLiteDatabase db = create.getReadableDatabase();

                Cursor c = db.query("personal", a, null, null, null, null, null, null);
                while (c.moveToNext()) {
                    username = c.getString(c.getColumnIndexOrThrow(TAG_USERNAME));
                    password = c.getString(c.getColumnIndexOrThrow(TAG_PASSWORD));
                    emailid = c.getString(c.getColumnIndexOrThrow(TAG_EMAILID));
                }
                i.putExtra("username", username);
                i.putExtra("password", password);
                i.putExtra("emailid", emailid);
                String msg = "" + username + " " + password + " " + emailid;
                Log.d("info", msg);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();



                startActivity(i);
            }

        }











    public void otpgeneration(android.view.View v) {
        Log.d("info", "in otpgeneration function");


        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String values = Capital_chars + Small_chars +
                numbers;

        // Using random method
        Random rndm_method = new Random();

        char[] password = new char[5];
        String otp = "";

        for (int i = 0; i < 5; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            password[i] =
                    values.charAt(rndm_method.nextInt(values.length()));
            otp = otp + password[i];

        }
        mName = (EditText) findViewById(R.id.signup_nameField);
        mPassword = (EditText) findViewById(R.id.signup_IDField);
        mEmail = (EditText) findViewById(R.id.signup_emailField);

        boolean flag = false;
        if (mName.getText().toString() == null) {
            mName.setError("Name field is empty");
            flag = true;
        }
        if (mPassword.getText().toString() == null) {
            mPassword.setError("Password field is required");
            flag = true;
        }
        if (mEmail.getText().toString() == null) {
            mEmail.setError("Email field is required");
            flag = true;
        }
            if (flag == false) {
                Intent i = new Intent(this, OtpVerification.class);
                i.putExtra("username", mName.getText().toString());
                i.putExtra("password", mPassword.getText().toString());
                i.putExtra("emailid", mEmail.getText().toString());
                i.putExtra("otp", otp);
                emailid=mEmail.getText().toString();
                Log.d("mail",emailid);
                Log.d("otp is=",otp);


                Log.d("mail",emailid);
                try {
                    GMailSender sender = new GMailSender("manikjaiswal007@gmail.com", "myriad_hues");
                    sender.sendMail("EMAIL Verification",
                            "This email has been sent by safety app to verify your email id. Your otp is "+otp+"",
                            mEmail.getText().toString(),
                            mEmail.getText().toString());
                    Log.d("mail",emailid);
                } catch (Exception e) {
                    String k="";
                    k=k+e;
                    Log.d("SendMail",k );
                }



                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();

                startActivity(i);
            }



    }
}


class MyPreferences {

    private static final String MY_PREFERENCES = "my_preferences";

    public static boolean isFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if(first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }

}
