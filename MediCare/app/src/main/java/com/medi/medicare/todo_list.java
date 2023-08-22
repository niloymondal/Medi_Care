package com.medi.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class todo_list extends AppCompatActivity {
    FloatingActionButton bt_float,removeall;
    ListView listView;
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        bt_float = findViewById(R.id.bt_float);
        removeall = findViewById(R.id.removeall);
        listView = findViewById(R.id.listView);
        final TextAdapter adapter = new TextAdapter();

        readInfo();
        adapter.setData(list);
        listView.setAdapter(adapter);

        TextView emptytextview = new TextView(todo_list.this);
        emptytextview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        emptytextview.setText("Wohooooo!! You have no due medicine to take");
        emptytextview.setGravity(Gravity.CENTER);
        emptytextview.setVisibility(View.GONE);
        ((ViewGroup)listView.getParent()).addView(emptytextview);
        listView.setEmptyView(emptytextview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                TextView customtxt3 =new TextView(todo_list.this);
                customtxt3.setTextColor(Color.WHITE);
                customtxt3.setText("Have You Taken This Medicine?");
                customtxt3.setTextSize(20F);
                customtxt3.setPadding(60,10,0,0);


                final AlertDialog alertDialog = new AlertDialog.Builder(todo_list.this)
                        .setCustomTitle(customtxt3)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                adapter.setData(list);
                                saveInfo();
                            }
                        }).setNegativeButton("No", null)
                        .create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    }
                });

                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary1);
            }
        });

        bt_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskinput = new EditText(todo_list.this);
                taskinput.setSingleLine();
                TextView customtxt =new TextView(todo_list.this);
                customtxt.setTextColor(Color.WHITE);
                customtxt.setText("Write your medicine name here");
                customtxt.setTextSize(20F);
                customtxt.setPadding(60,10,0,0);
                taskinput.setTextColor(Color.WHITE);


                final AlertDialog dialog = new AlertDialog.Builder(todo_list.this)
                        .setCustomTitle(customtxt)
                        .setView(taskinput)
                        .setPositiveButton("Add Medicine", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(taskinput.getText().toString());
                                adapter.setData(list);
                                saveInfo();
                            }
                        }).setNegativeButton("Cancel",null).create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    }
                });
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary1);

            }
        });

        removeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView customtxt3 =new TextView(todo_list.this);
                customtxt3.setTextColor(Color.WHITE);
                customtxt3.setText("Have You Taken all Medicines?");
                customtxt3.setTextSize(20F);
                customtxt3.setPadding(60,10,0,0);

                final AlertDialog alertDialog = new AlertDialog.Builder(todo_list.this)
                        .setCustomTitle(customtxt3)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.clear();
                                adapter.setData(list);
                                saveInfo();
                            }
                        }).setNegativeButton("No", null).create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary1);
            }
        });
    }
    @Override
    protected  void onPause() {
        super.onPause();
        saveInfo();
    }
    private void saveInfo(){
        try{

            File file = new File(this.getFilesDir(), "saved");
            FileOutputStream fout = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));

            for(int i=0; i<list.size();i++)
            {
                bw.write(list.get(i));
                bw.newLine();
            }
            bw.close();
            fout.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void readInfo(){
        File file = new File(this.getFilesDir(),"saved");
        if(!file.exists())
        {
            return;
        }

        try{

            FileInputStream is = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while(line!=null)
            {
                list.add(line);
                line = reader.readLine();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
     class TextAdapter extends BaseAdapter {

        List<String> list = new ArrayList<>();

        void setData(List<String> mlist)
        {
            list.clear();
            list.addAll(mlist);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) todo_list.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowview = inflater.inflate(R.layout.todo_sample ,parent,false);
            TextView textView = rowview.findViewById(R.id.task);
            textView.setBackgroundColor(Color.GRAY);
            textView.setTextColor(Color.WHITE);
            textView.setText(list.get(position));
            return rowview;
        }
    }
}
