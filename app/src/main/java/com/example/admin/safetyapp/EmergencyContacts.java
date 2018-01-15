package com.example.admin.safetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class EmergencyContacts extends AppCompatActivity {
    private PopupWindow pwindo,deletewindow;
    Button btnClosePopup, cancelButton,deletButton;
    Button btnAddPopup;
    Button btnCreatePopup;
    ArrayList prgmNames;
    ListView lv,lv2;
    Context context;
    SqliteDatabase create,create2;
    SQLiteDatabase db;
    String[] emergencynames=new String[10];
    int len;
    String[] emergencyemailid=new String[10];
    EditText mName;
    EditText mPassword;
    View layout,deletelayout;
    EditText mEmail;
    String emailid="";
    String name="";
    int i=0;
    int no=0;
    String contactname="",contactemailid="";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_EMAILID = "emailid";
    private static final String TAG_IUSERNAME="username";
    private static final String TAG_IPASSWORD= "password";
    private static final String TAG_IEMAILID = "emailid";

    public String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    public  String [] prgmName={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        create2 = new SqliteDatabase(getBaseContext());
        i=0;
        no=0;



        String a[] = new String[]{TAG_USERNAME, TAG_EMAILID};
        SQLiteDatabase db2 = create2.getReadableDatabase();

        Cursor c = db2.query("emergency", a, null, null, null, null, null, null);
        while (c.moveToNext()) {
            contactname = c.getString(c.getColumnIndexOrThrow(TAG_USERNAME));
            contactemailid = c.getString(c.getColumnIndexOrThrow(TAG_EMAILID));
            emergencynames[i]=contactname;
            emergencyemailid[i]=contactemailid;

            i++;
            if(emergencyemailid[no]!=null)
            {
                String l=""+emergencyemailid[no]+ " "+no;
                Log.d("display",l);
                no++;
            }
        }






        Log.d("count in emrgency",""+len);
        setContentView(R.layout.activity_emergency_contacts);



        context=this;

        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, emergencynames,emergencyemailid));











    }
    public void initiateContactPopup(android.view.View v) throws Exception {
        try {
            if(len<=5)
            {


// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) EmergencyContacts.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.contact_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 600, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnAddPopup = (Button) layout.findViewById(R.id.btn_add_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);
            btnAddPopup.setOnClickListener(add_button_click_listener);

        }
        else
            {
                Toast.makeText(this,"Cant add more than 5 emergency contacts", LENGTH_LONG).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };

    private View.OnClickListener add_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {



            mName = (EditText)layout. findViewById(R.id.add_nameField);
            mEmail = (EditText)layout. findViewById(R.id.add_emailField);
            Log.d("reaching","in on click listener");
            String check=mEmail.getText().toString();
            boolean flag = false;
            if (mName.getText().toString().matches("")) {
                mName.setError("Name field is empty");
                flag = true;
            }
            if (check.matches("")) {
                Log.d("inside if","no change");
                mEmail.setError("Email field is required");
                flag = true;
            }
            if (!flag) {

                Log.d(check+" fuck","off "+check+"sdvf");
                Log.d("reaching","after once gettext");
                emailid=mEmail.getText().toString();
                name=mName.getText().toString();

                create=new SqliteDatabase(getBaseContext());
                db = create.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(TAG_IUSERNAME, name);
                values.put(TAG_IEMAILID, emailid);
                db.insert("emergency", null, values);

                emergencynames[len]=name;
                emergencyemailid[len]=emailid;
                len++;
                Intent i = new Intent(EmergencyContacts.this, EmergencyContacts.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);





                pwindo.dismiss();
            }





        }
    };

    public void delete(android.view.View v)throws Exception
    {
        try {

            Log.d("was here","wtf");
            LayoutInflater inflater = (LayoutInflater) EmergencyContacts.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            deletelayout = inflater.inflate(R.layout.delete_popup,
                    (ViewGroup) findViewById(R.id.delete_element));
            deletewindow = new PopupWindow(EmergencyContacts.this);
            deletewindow.setContentView(deletelayout);
            deletewindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            deletewindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            deletewindow.showAtLocation(deletelayout, Gravity.CENTER, 0, 0);
            lv2 = (ListView)deletelayout. findViewById(R.id.listView2);
            lv2.setAdapter(new PrivateAdapter(this, emergencynames, emergencyemailid, len));

            deletButton = (Button) deletelayout.findViewById(R.id.emergency_fordelete);
            cancelButton = (Button) deletelayout.findViewById(R.id.emergency_forcancel);
            deletButton.setOnClickListener(delete_button_click_listener);
            cancelButton.setOnClickListener(close_button_click_listener);
            Log.d("no error","wtf");
        }
        catch (Exception e) {
            String msg=""+e;
            Log.d("exception",msg);
        }

    }
    private View.OnClickListener close_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            deletewindow.dismiss();

        }
    };
    private View.OnClickListener delete_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {


        }
    };
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();


        create2 = new SqliteDatabase(getBaseContext());
        i=0;
        no=0;



        String a[] = new String[]{TAG_USERNAME, TAG_EMAILID};
        SQLiteDatabase db2 = create2.getReadableDatabase();

        Cursor c = db2.query("emergency", a, null, null, null, null, null, null);
        while (c.moveToNext()) {
            contactname = c.getString(c.getColumnIndexOrThrow(TAG_USERNAME));
            contactemailid = c.getString(c.getColumnIndexOrThrow(TAG_EMAILID));
            emergencynames[i]=contactname;
            emergencyemailid[i]=contactemailid;

            i++;
            if(emergencyemailid[no]!=null)
            {
                String l=""+emergencyemailid[no]+ " "+no;
                Log.d("display",l);
                no++;
            }
        }
        for(int j=i;j<10;j++){
            emergencyemailid[j]="";
            emergencynames[j] ="";
        }

        //Refresh your stuff here
    }

}
