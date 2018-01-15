package com.example.admin.safetyapp;

/**
 * Created by admin on 11/18/2017.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class PrivateAdapter extends BaseAdapter{
    String [] result;
    Context context;
    String [] result2;
    SqliteDatabase create;
    SQLiteDatabase db;
    int len=0;

    private static LayoutInflater inflater=null;
    public PrivateAdapter(EmergencyContacts mainActivity, String[] prgmNameList, String[] prgmImages,int count) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        result2=prgmImages;
        len=count;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(TextView) rowView.findViewById(R.id.textView2);
        holder.tv.setText(result[position]);
        holder.img.setText(result2[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deletealertbox("Deleting!!", "Your GPS is: OFF",position);
                // TODO Auto-generated method stub




            }
        });
        return rowView;
    }
    protected void deletealertbox(String title, String mymessage, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        create=new SqliteDatabase(context);
        db = create.getWritableDatabase();
        builder.setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setTitle(title)
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                String table = "emergency";
                                String whereClause = "_id=?";
                                db.delete("emergency","username=? and emailid=?",new String[]{result[position],result2[position]});



                                int j=position;
                                for(j=position;j<len-1;j++)
                                {
                                    result[j]=result[j+1];
                                    result2[j]=result2[j+1];
                                }
                                len--;
                                Intent i = new Intent(context, EmergencyContacts.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(i);

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

}