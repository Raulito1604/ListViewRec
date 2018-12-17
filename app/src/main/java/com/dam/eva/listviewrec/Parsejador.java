package com.dam.eva.listviewrec;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parsejador extends AppCompatActivity {
    public List<Bloc> parseja(String xml) throws XmlPullParserException {
        String time = null;
        String temperatura = null;
        String fredCalor=null;
        Bloc bloc;

        List<Bloc> llista = new ArrayList<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

//        new JSONObject()new JSONArray()

        xpp.setInput(new StringReader(xml));
        int eventType=xpp.getEventType();
        while (eventType!=XmlPullParser.END_DOCUMENT){
            if (eventType==XmlPullParser.START_TAG){
                if(xpp.getName().equals("time")){
                    time=xpp.getAttributeValue(null, "from");
                    if(xpp.getName().equals("temperature")){
                        temperatura=xpp.getAttributeValue(null, "value");
                        if(temperatura!=null){
                            if (Double.parseDouble(temperatura)>=20.){
                                fredCalor="hot";
                            }else {
                                fredCalor="cold";
                            }
                        }else {
                            fredCalor="hot";
                        }
                    }
                }

                bloc = new Bloc(time, temperatura, fredCalor);
                llista.add(bloc);
            }
            try {
                eventType = xpp.next();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return llista;
    }
}
