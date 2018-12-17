package com.dam.eva.listviewrec;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity  {

    Button boto, botoDesc;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter, tAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;
    private boolean sdAvailable=false;
    private boolean sdWriteAccess=false;
    final static String API_KEY="a909425ac95dc9c09141139e0e4bae0d";//0e47309874c00c364e2ddceb8832e7d2
    //crea compte a openweathermap.org i posa l api id que et donen aquí dalt
    private String city;
    private EditText editTextCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView= (TextView) findViewById(R.id.textView);


        String estat=Environment.getExternalStorageState();
        // comprova si hi ha SD i si puc escriure en ella
        if (estat.equals(Environment.MEDIA_MOUNTED))
        {
            sdAvailable=true;
            sdWriteAccess=true;
        }
        else if (estat.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            sdAvailable=true;
            sdWriteAccess=false;
        }
        else {
            sdAvailable=false;
            sdWriteAccess=false;
            //manifest --> uses-permission
        }
        editTextCity=(EditText) findViewById(R.id.editTextCity);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new MyAdapter(input);
        recyclerView.setAdapter(mAdapter);

    }


    public void openWeather() {
        String nomCiutat;
        city= editTextCity.getText().toString();
        if (!city.isEmpty()) nomCiutat=city;
        else nomCiutat="Petropavlovsk";

        //Irkutsk
        //Proveu aquesta de dalt, també és tropical     :-)

        String result;
        String myUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" +
                nomCiutat + "&units=metric&mode=xml&appid=" + API_KEY;

        //String url="http://www.elpais.com/rss/feed.html?feedId=1022";
        //exercici extra: crida  a aquesta news feed de elpais.com

        Descarregador descarregador = new Descarregador(this);
        try {

            result = descarregador.execute(myUrl).get();

//            Toast.makeText(this, "Result és:" + result, Toast.LENGTH_SHORT).show();


            Parsejador parsejador = new Parsejador();
            List<Bloc> blocs = parsejador.parseja(result);

            if(result!=null) {
                tAdapter = new CiutatAdapter(blocs);
                recyclerView.setAdapter(tAdapter);
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("test", "onClickDesc: "+e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("test", "onClickDesc: "+e.getMessage());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    class Descarregador extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        public Descarregador(Context context) {
        }
        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result = "";
            String inputLine;
            try {
                URL myURL=new URL(stringUrl);
                Log.d("test","comprobarURL: "+myURL);
                HttpURLConnection connection=(HttpURLConnection) myURL.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();
                InputStreamReader streamReader=new InputStreamReader(connection.getInputStream());
                BufferedReader reader=new BufferedReader(streamReader);
                StringBuilder stringBuilder=new StringBuilder();
                while ((inputLine=reader.readLine())!=null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                connection.disconnect();
                result=stringBuilder.toString();
                Log.d("test","result és "+result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            int id = item.getItemId();
            if (id == R.id.readMemInt) {
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput("memiterna.txt")));
                String line=bufferedReader.readLine();
                textView.setText(line);
                bufferedReader.close();
                return true;

            }
            else if (id == R.id.readProgram) {
                InputStream fraw= getResources().openRawResource(R.raw.fraw2);
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fraw));
                String line= null;
                line = bufferedReader.readLine();
                textView.setText(line);
                fraw.close();
                //IOException
                return true;
            }

            else if (id == R.id.writeMemInt) {
                OutputStreamWriter fout=new OutputStreamWriter(openFileOutput("memiterna.txt",Context.MODE_PRIVATE));
                fout.write("Contigut del fitxer de mem. interna");
                fout.close();



                return true;


            } else if (id == R.id.exListView) {
                Intent intent=new Intent(this, ListViewRec.class);
                startActivity(intent);
                return true;
            }

            else if (id == R.id.readSD) {
                if (sdAvailable){
                    File rutaSD=Environment.getExternalStorageDirectory();
                    File f=new File(rutaSD.getAbsolutePath(),"files.txt");
                    BufferedReader fout=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    textView.setText(fout.readLine());
                    fout.close();
                    return true;
                }
                else Toast.makeText(this, "no és sdAvailable", Toast.LENGTH_SHORT).show();
            }

            else if (id == R.id.writeSD) {
                if (sdAvailable && sdWriteAccess) {
                    try {
                        File rutaSD=Environment.getExternalStorageDirectory();
                        File f=new File(rutaSD.getAbsolutePath(),"files.txt");
                        OutputStreamWriter fout=new OutputStreamWriter(new FileOutputStream(f));
                        fout.write("Contingut del fitxer de la SD");
                        fout.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.d("testSD", e.getMessage()+ e.getCause());
                        //Toast.makeText(this, String.valueOf(sdAvailable)+"," + String.valueOf(sdWriteAccess), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("testSD",e.getMessage()+ e.getCause());
                    }
                    return true;
                }
            }

            else if (id == R.id.openWeather) {
                //exemple HttpURLConnection
                //ha de ser crida AsyncTask desde versió 4 Android
                //exercici extra: prova de fer-ho síncron....
                openWeather();
                return true;

            }

            return super.onOptionsItemSelected(item);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onOptionsItemSelected: ",e.getMessage());
        }
        return true;
    }



}