package com.dam.eva.listviewrec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ListViewRec extends AppCompatActivity {


    private static ListView listView;
    private static String[] NAMES=new String[] {"Tom","Jerry","Mary","George","Eva","Ana"};
    //adapter between data and the view

    private TextView textView1;
    private Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_list);

            //https://developer.android.com/guide/topics/ui/controls/spinner
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                    R.array.quevol, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            textView1=(TextView) findViewById(R.id.textView2);

            listView();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d( "test: ",e.getMessage());
        }
    }

    public void listView() {

        //carrega les dades a la listview
        listView=(ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.name_list,NAMES);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=(String) listView.getItemAtPosition(position);
                //Toast.makeText(ListViewRec.this, "Posició:" +position + " Valor: " + value, Toast.LENGTH_SHORT).show();
                textView1.setText("Posició:" +position + " Valor: " + value);
            }
        });


    }


    public void onClick(View view) {
        int position = spinner.getSelectedItemPosition();

        try {
            switch (position) {
                case 0:
                    Toast.makeText(this, "Primera opció del Spinner, control de selecció", Toast.LENGTH_SHORT).show();
                    break;
                case 1:

                    Toast.makeText(this, "ListView, control de selecció més usat", Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    Toast.makeText(this, "Recycler View, control de selecció estalvia recursos", Toast.LENGTH_SHORT).show();

                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("testing", e.getMessage());
        }
    }


}
