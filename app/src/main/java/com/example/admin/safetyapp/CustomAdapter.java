package com.example.admin.safetyapp;

/**
 * Created by admin on 11/18/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    String [] result2;
    private static LayoutInflater inflater=null;
    public CustomAdapter(EmergencyContacts mainActivity, String[] prgmNameList, String[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        result2=prgmImages;
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
                // TODO Auto-generated method stub
            }
        });
        return rowView;
    }

}