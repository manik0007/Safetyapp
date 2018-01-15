package com.example.admin.safetyapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class OtpVerification extends AppCompatActivity {
    String username="";
    String password="";
    String emailid="";
    String otp="";
    EditText meditText;
    SqliteDatabase create;
    SQLiteDatabase db;
    private static final String TAG_IUSERNAME="username";
    private static final String TAG_IPASSWORD= "password";
    private static final String TAG_IEMAILID = "emailid";
    public static String createQ="CREATE TABLE if not exists personal"+ "("+ BaseColumns._ID+ " integer primary key autoincrement, "+TAG_IUSERNAME+" text, "+TAG_IPASSWORD+" text, "+TAG_IEMAILID+" text"+")";
    public static String createQ2="CREATE TABLE if not exists emergency"+ "("+ BaseColumns._ID+ " integer primary key autoincrement, "+TAG_IUSERNAME+" text, "+TAG_IEMAILID+" text"+")";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Bundle b=getIntent().getExtras();
        username=b.getString("username");
        password=b.getString("password");
        emailid=b.getString("emailid");
        otp=b.getString("otp");
    }
    public void verifying(android.view.View v)
    {
        meditText = (EditText) findViewById(R.id.otpverify_enteredotp);
        String value = meditText.getText().toString();
        if(value.equals(otp))
        {Intent i = new Intent(this, MainActivity.class);
            i.putExtra("username", username);
            i.putExtra("password", password);
            i.putExtra("emailid", emailid);
            String msg = "" + username + " " + password + " " + emailid;
            Log.d("info", msg);



            create=new SqliteDatabase(getBaseContext());
            Toast.makeText(this,"creating table",Toast.LENGTH_LONG).show();
            Log.d("creating","in function create table");
            db = create.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS personal" );
            db.execSQL("DROP TABLE IF EXISTS emergency" );
            db.execSQL(createQ);
            db.execSQL(createQ2);
                ContentValues values = new ContentValues();
                values.put(TAG_IUSERNAME, username);
                values.put(TAG_IPASSWORD,password);
                values.put(TAG_IEMAILID, emailid);
            db.insert("personal", null, values);

            Log.d("otp verified","table created");

            Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getBoolean("isFirstRun", true);

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();




            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();

            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"Otp doesnt match",Toast.LENGTH_LONG).show();
        }
    }
}
