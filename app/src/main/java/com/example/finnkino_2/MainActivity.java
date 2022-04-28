package com.example.finnkino_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Context context = null;
    TheaterStructure tStructure = TheaterStructure.getInstance();
    String name, ID;
    Spinner spinner;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> movies = new ArrayList<String>();
    ListView listView;
    EditText editTextDate, firstTime, lastTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.ListView);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        firstTime = findViewById(R.id.editTextTime);
        lastTime = findViewById(R.id.editTextTime2);

        context = MainActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        readXML();
    }

    public void readXML() {
        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                System.out.println();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    name = element.getElementsByTagName("Name").item(0).getTextContent();
                    ID = element.getElementsByTagName("ID").item(0).getTextContent();
                    if (name.contains(":")) {
                        tStructure.newTheater(name, ID);
                        names.add(name);
                    }
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

        public void readXMLMovies(View v) {
            try {

                movies.clear();
                editTextDate.getText().toString();
                String thisDate;

                if (editTextDate.getText().toString().trim().length() == 0) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                    Date date = new Date();
                    thisDate = formatter.format(date);
                } else {
                    thisDate = editTextDate.getText().toString();
                }



                String ID = spinner.getSelectedItem().toString();
                ID = tStructure.findTheater(ID);

                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + ID + "&dt=" + thisDate;
                Document doc = builder.parse(urlString);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");

                String[] startTime;
                String title;

                int time1 = 0;
                int time2 = 2359;

                if (firstTime.getText().toString().trim().length() != 0) {
                    time1 = Integer.parseInt(firstTime.getText().toString().replaceAll("[\\D]", ""));

                } if (lastTime.getText().toString().trim().length() != 0) {
                    time2 = Integer.parseInt(lastTime.getText().toString().replaceAll("[\\D]", ""));
                }

                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    System.out.println();

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        title = element.getElementsByTagName("Title").item(0).getTextContent();
                        startTime = element.getElementsByTagName("dttmShowStart").item(0).getTextContent().split("T");
                        int time3 = Integer.parseInt(startTime[1].replaceAll("[\\D]", "").substring(0,4)) ;
                        
                        if (time3 >= time1 & time3 <= time2) {
                            movies.add(title + "\npvm " + startTime[0] + "\nAlkaa klo " + startTime[1].substring(0,5));
                        }

                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movies);
                listView.setAdapter(arrayAdapter);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
    }
}