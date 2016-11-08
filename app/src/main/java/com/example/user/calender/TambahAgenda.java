package com.example.user.calender;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 26/10/2016.
 */

public class TambahAgenda extends AppCompatActivity implements View.OnClickListener {

    EditText tanggal;
    EditText jam;
    EditText judul;
    EditText isi;
    EditText lokasi;
    EditText partisipan;
    Switch on;

    Agenda agenda = new Agenda();
    Geocoder geocoder;
    Button btntgl, btnjam, btnlokasi;
    private int mYear, mMonth, mDay, mHour, mMinute;

    String alamat = null;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_agenda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Agenda");
        tanggal = (EditText) findViewById(R.id.idTanggal);
        jam = (EditText) findViewById(R.id.idJam);
        judul = (EditText) findViewById(R.id.idJudul);
        isi = (EditText) findViewById(R.id.idIsi);
        lokasi = (EditText) findViewById(R.id.idLokasi);
        partisipan = (EditText) findViewById(R.id.idPartisipan);
        on = (Switch) findViewById(R.id.idOn);


        btntgl = (Button) findViewById(R.id.btntgl);
        btnjam = (Button) findViewById(R.id.btnjam);
        btnlokasi = (Button) findViewById(R.id.btnlokasi);

        if (getIntent().hasExtra("agenda")) {
            agenda = (Agenda) getIntent().getSerializableExtra("agenda");
            loadlocation();
            tanggal.setText(agenda.getTanggal());
            jam.setText(agenda.getJam());
            judul.setText(agenda.getJudul());
            isi.setText(agenda.getIsi());
            lokasi.setText(alamat);
            partisipan.setText(agenda.getPartisipan());
            //on.setChecked();
        }
        ;

        btntgl.setOnClickListener(this);
        btnjam.setOnClickListener(this);
        btnlokasi.setOnClickListener(this);
    }

    public void loadlocation(){
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(agenda.getLatitude(), agenda.getLongitude(), 1);
            alamat = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        if (v == btntgl) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                String bulan = String.valueOf(monthOfYear + 1);
                String hari = String.valueOf(dayOfMonth);

                tanggal.setText(year + "-" + (bulan.length() > 1 ? bulan : "0" + bulan) + "-" + (hari.length() > 1 ? hari : "0" + hari));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnjam) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> jam.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        }


        if (v == btnlokasi) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), 1);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                LatLng loc = place.getLatLng();
                agenda.setLatitude(loc.latitude);
                agenda.setLongitude(loc.longitude);
                lokasi.setText(place.getAddress());

            }
        }
    }

    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tambah, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.enkrip) {
            String hasil = "";
            String isiText;
            EditText judul;
            //EditText isi;

            judul = (EditText) findViewById(R.id.idJudul);
            //isi = (EditText) findViewById(R.id.idIsi);

            hasil = "";
            //isiText = isi.getText().toString();
            isiText = judul.getText().toString();
            //enkrip
            for (int i = 0; i < isiText.length(); i++) {
                int index = isiText.charAt(i);
                char s = (char) (index + 1);
                hasil = hasil + String.valueOf(s);
            }
            judul.setText(hasil);
            //isi.setText(null);


        } else if (id == R.id.save) {
            agenda.setJudul(judul.getText().toString());
            agenda.setTanggal(tanggal.getText().toString());
            agenda.setJam(jam.getText().toString());
            agenda.setIsi(isi.getText().toString());
           // agenda.setLokasi(lokasi.getText().toString());
            agenda.setPartisipan(partisipan.getText().toString());

            DatabaseHelper db = new DatabaseHelper(TambahAgenda.this);

            if (agenda.getId() != null) {
                db.editAgenda(agenda);
            } else {
                db.addAgenda(agenda);
            }

            finish();
        }
        return true;
    }


}
