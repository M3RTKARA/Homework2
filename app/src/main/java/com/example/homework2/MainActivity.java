package com.example.homework2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "proactive";

    TextView tv2;

    String sksurl;
    String pcurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sksurl = "http://aybu.edu.tr/sks/";
        pcurl = "http://ybu.edu.tr/muhendislik/bilgisayar/";


        // Get ActionBar.
        ActionBar actionBar = getSupportActionBar();
        // Set tab navigation mode.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        actionBar.setTitle("Homework 2");

        // Listen to action bar tab select event.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                String tabText = (String) tab.getText();
                tabText = tabText.trim().toLowerCase();
                switch (tabText) {

                    // If select Announcement tab.
                    case "announcement":

                        announcement ann = new announcement();
                        ann.execute("Announcement");
                        break;

                    // If select SKS tab.
                    case "sks":

                        get_webdata gw = new get_webdata();
                        gw.execute("WebData");
                        break;

                    // If select news tab.
                    case "news":

                        news news = new news();
                        news.execute("News");
                        break;
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {


            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                if(tab.getText().equals("Announcement"))
                {
                    announcement ann = new announcement();
                    ann.execute("Announcement");
                }

                else
                {
                    news news = new news();
                    news.execute("News");
                }


            }

        };

        // Create and add tab1.
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("SKS");
        tab1.setTabListener(tabListener);
        actionBar.addTab(tab1, 0);

        // Create and add tab2.
        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Announcement");
        tab2.setTabListener(tabListener);
        actionBar.addTab(tab2, 1);

        // Create and add tab3.
        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("News");
        tab3.setTabListener(tabListener);
        actionBar.addTab(tab3, 2);


        //--------------------------------------------------------------


    }


    private class get_webdata extends AsyncTask<String, Integer, String> {
        String words = "";
        String date = "";


        @Override
        protected String doInBackground(String... strings) {

            try {
                Document doc = Jsoup.connect(sksurl).get();


                Elements eless = doc.select("div#main_wrapper > table > tbody > tr > td > table > tbody > tr > td > p");
                date = doc.select("div#main_wrapper > table > tbody > tr > td > table > tbody > tr > td > h3").text();

                for (Element eles : eless) {

                    words += eles.text() + "\n";

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            setContentView(R.layout.activity_main);

            TextView tv  = findViewById(R.id.tv);
            TextView tv2 = findViewById(R.id.tv2);
            TextView tv3 = findViewById(R.id.tv3);
            TextView tv4 = findViewById(R.id.tv4);


            tv.setText(words);
            tv2.setText(date);
            tv3.setText("Date:             ");
            tv4.setText("Food Menu: ");


        }

    }

    private class announcement extends AsyncTask<String, Integer, String> {

        List<String> annData = new ArrayList<String>();



        @Override
        protected String doInBackground(String... strings) {

            try {
                Document doc = Jsoup.connect(pcurl).get();


                Elements eless = doc.select("div[class=caContent]").select("div[class=cncItem]");





                for (Element eles : eless) {
                    annData.add(eles.text());


                }



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            setContentView(R.layout.activity_action_bar_tab_data_list_view);

            ListView listView = findViewById(R.id.listViewData);


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, annData);


            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.activity_main);

                    TextView tv = tv = findViewById(R.id.tv);
                    TextView tv2 = findViewById(R.id.tv2);



                    tv2.setText("");
                    tv.setText(annData.get(position));



                }
            });

        }


    }

    private class news extends AsyncTask<String, Integer, String> {

        List<String> newsData = new ArrayList<String>();

        @Override
        protected String doInBackground(String... strings) {

            try {
                Document doc = Jsoup.connect(pcurl).get();


                Elements eless = doc.select("div[class=cnContent]").select("div[class=cncItem]");

                for (Element eles : eless) {
                    newsData.add(eles.text());

                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            setContentView(R.layout.activity_action_bar_tab_data_list_view);

            ListView listView = findViewById(R.id.listViewData);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, newsData);

            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setContentView(R.layout.activity_main);

                    TextView tv = tv = findViewById(R.id.tv);
                    TextView tv2 = findViewById(R.id.tv2);


                    tv2.setText("");
                    tv.setText(newsData.get(position));



                }
            });

        }


    }

}



