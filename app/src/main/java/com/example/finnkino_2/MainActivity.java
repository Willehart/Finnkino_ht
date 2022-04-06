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
    EditText editTextDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.ListView);
        editTextDate = (EditText) findViewById(R.id.editTextDate);

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

                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                Date date = new Date();
                String thisDate = formatter.format(date);

                String ID = spinner.getSelectedItem().toString();
                ID = tStructure.findTheater(ID);

                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + ID + "&dt=" + thisDate;
                Document doc = builder.parse(urlString);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");

                movies.clear();

                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    System.out.println();

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        movies.add(element.getElementsByTagName("Title").item(0).getTextContent());
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

    public void testi(View v) {
        setContentView(R.layout.testi);
    }
}