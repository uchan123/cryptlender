package com.example.user.calender;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalenderView extends AppCompatActivity {
    public GregorianCalendar month, itemmonth;  //calender instances
    public CalenderAdapter adapter;             //adapter instance public handler
    public Handler handler;                                    //for grabbing some event values for showing to dot // marker
    public ArrayList<String> items;             //container to store calender items
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    ListView lisagen;
    ArrayAdapter<String> listadapater;
    List<String> agenda = new ArrayList<>();
    List<Agenda> currentAgenda = new ArrayList<>();
    //Button btnSet;

    private String currentDate;

    //needs showing the event marker
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Agenda");


        Locale.setDefault(Locale.US);
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();
        items = new ArrayList<String>();
        adapter = new CalenderAdapter(this, month);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        handler = new Handler();
        handler.post(calenderUpdeter);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        lisagen = (ListView) findViewById(R.id.listAgenda);
        listadapater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, agenda);
        lisagen.setAdapter(listadapater);

        lisagen.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DetailAgendaDialog d = new DetailAgendaDialog();
                Bundle b = new Bundle();
                b.putSerializable("agenda",currentAgenda.get(position));
                d.setArguments(b);
                d.show(getFragmentManager(),null);
            }
        });

        RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalender();
            }
        });
        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalender();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                ((CalenderAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = CalenderAdapter.dayString.get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");   //taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);      //navigate to next or prefoius month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalender();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalender();
                }
                ((CalenderAdapter) parent.getAdapter()).setSelected(v);
                showToast(selectedGridDate);
                currentDate = selectedGridDate;
                updateAgenda();

                Agenda agenda = new Agenda();
                agenda.setTanggal(selectedGridDate);
                Intent i = new Intent(CalenderView.this,TambahAgenda.class);
                i.putExtra("agenda",agenda);
                startActivity(i);

                return true;
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ((CalenderAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = CalenderAdapter.dayString.get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");   //taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);      //navigate to next or prefoius month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalender();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalender();
                }
                ((CalenderAdapter) parent.getAdapter()).setSelected(v);
                showToast(selectedGridDate);
                currentDate = selectedGridDate;
                updateAgenda();
            }
        });
        /*btnSet = (Button) findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalenderView.this,DetailSetting.class);
                startActivity(i);

            }
        }); */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void setNextMonth() {
        //noinspection WrongConstant
        if (month.getActualMaximum(GregorianCalendar.MONTH) == month.get(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1), month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        //noinspection WrongConstant
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public void refreshCalender() {
        TextView title = (TextView) findViewById(R.id.title);
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calenderUpdeter);      //generate some calender items
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    public Runnable calenderUpdeter = new Runnable() {
        @Override
        public void run() {
            items.clear();      //print dates of the current week
            agenda.clear();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String itemvalue = df.format(itemmonth.getTime());
            itemmonth.add(GregorianCalendar.DATE, 1);

//            for (int i = 0; i < 7; i++) {
//                itemvalue = df.format(itemmonth.getTime());
//                itemmonth.add(GregorianCalendar.DATE, 1);
//                items.add("2012-09-12");
//                items.add("2012-10-07");
//                items.add("2012-10-15");
//                items.add("2012-10-20");
//                items.add("2012-11-30");
//                items.add("2016-10-19");
//            }

            DatabaseHelper db = new DatabaseHelper(CalenderView.this);

            System.out.println("item: " + itemvalue);

            items.addAll(db.getalltanggal());

            currentDate = itemvalue;

            updateAgenda();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CalenderView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.calender/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CalenderView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.calender/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void updateAgenda() {
        agenda.clear();
        agenda.addAll(getAgendaTitle(currentDate));

        listadapater.notifyDataSetChanged();
    }

    private List<String> getAgendaTitle(String date) {
        List<String> agendas = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper(this);

        currentAgenda = db.getAgendabytanggal(date);

        for (Agenda a : currentAgenda) {
            agendas.add(a.getJudul());
        }

        System.out.println(agendas.size());

        return agendas;
    }


    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    public void reload(){
        updateAgenda();
        refreshCalender();
    }

    //menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.search) {
            //DatabaseHelper db = new DatabaseHelper(this);
            //db.cari();
        //}else
        if (id == R.id.add){
            Intent i = new Intent(CalenderView.this, TambahAgenda.class);
            i.putExtra("agenda",new Agenda());
            startActivity(i);
        }else if (id == R.id.about) {
            Intent i = new Intent(CalenderView.this, About.class);
            startActivity(i);
        }else if (id == R.id.help) {
            Intent i = new Intent(CalenderView.this, Help.class);
            startActivity(i);
        }
            return true;

       // return super.onOptionsItemSelected(item);
    }

}


