package com.medi.medicare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomAdapter extends BaseAdapter {
    int listviewController;
    String [] names;
    String [] dgree;
    String [] specialized;
    String [] time_table ;
    String [] id ;
    String [] pic ;
    Context context;
    LayoutInflater inflater; //xml k view te replace korbe

   /* public CustomAdapter(Context context,String[] names, int[] img) {
        this.names = names;
        this.img = img;
        this.context = context;
    }*/

    public CustomAdapter(int listviewController,String[] id,String[] names,
                         String[] dgree, String[] specialized, String[] time_table,
                         String[] pic, Context context) {
        this.listviewController=listviewController;
        this.id=id;
        this.names = names;
        this.dgree = dgree;
        this.specialized = specialized;
        this.time_table = time_table;
        this.pic = pic;
        this.context = context;
    }

    @Override
    public int getCount() { //jotogula item dekhate chachi oitar number
        return listviewController;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //sample view k view hisebe return korbe
        if(convertView ==null) //jdi ekhn o view  e convert na hoy tahole
        {
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.sample_for_custom_view,parent,false);
        }
        TextView textView1 =  convertView.findViewById(R.id.textview1Id);
        TextView textView2 =  convertView.findViewById(R.id.textview2Id);
        TextView textView3 =  convertView.findViewById(R.id.textview3Id);
        TextView textView4 =  convertView.findViewById(R.id.textview4Id);
        TextView textView5 =  convertView.findViewById(R.id.textview5Id);
        ImageView imageView = convertView.findViewById(R.id.docImageID);

        textView1.setText(names[position]);
        textView2.setText(dgree[position]);
        textView3.setText(specialized[position]);
        textView4.setText(time_table[position]);
        textView5.setText(id[position]);
        Picasso.get().load(pic[position]).placeholder(R.drawable.doctor).into(imageView);
        return convertView;
    }
}

