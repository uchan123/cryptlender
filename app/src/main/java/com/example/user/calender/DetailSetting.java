package com.example.user.calender;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 03/11/2016.
 */

public class DetailSetting extends AppCompatActivity implements View.OnClickListener {
    EditText idKey;
    EditText idKey1;
    Spinner idPer;
    EditText idJawaban;

    Button btnSimpan;

    Setting setting = new Setting();
    List<Setting> pertanyaan = new ArrayList<>();
    Setting selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.setting);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.tosca)));
        getSupportActionBar().setTitle("Setting");

        // actionBar
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //mode dapat d custom
        actionBar.setCustomView(R.layout.splash);

        ColorDrawable colorDrawable = new ColorDrawable(this.getResources().getColor(R.color.tosca));
        actionBar.setBackgroundDrawable(colorDrawable);*/

        idKey = (EditText) findViewById(R.id.idKey);
        idKey1 = (EditText) findViewById(R.id.idKey1);
        idPer = (Spinner) findViewById(R.id.idPer);
        DatabaseHelper db = new DatabaseHelper(DetailSetting.this);
        pertanyaan = db.getSettingbytag("pertanyaan");
        // idPer.setAdapter(new ArrayAdapter<String>());
        idJawaban = (EditText) findViewById(R.id.idJawaban);

        List<String> p = Stream.of(pertanyaan)
                .map(s -> s.getValue())
                .collect(Collectors.toList());

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, p);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        idPer.setAdapter(a);

        idPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = pertanyaan.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = null;
            }
        });

        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(this);
        //Toast.makeText(this, "lala", Toast.LENGTH_SHORT).show();

    }

    public void onClick(View v) {
        if (v == btnSimpan) {
            try {
                validate();

                Set.i(this).get("kunci").setValue(idKey.getText().toString()).save();
                Set.i(this).get("jawaban").setValue(idJawaban.getText().toString()).setRel(selected.getIdpengaturan()).save();
                Set.i(this).get("first").setValue("0").save();

                Intent i = new Intent(DetailSetting.this, CalenderView.class);
                startActivity(i);

                finish();

            }catch (ValidationException e ){
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void validate () throws ValidationException {
        if (idKey.getText().toString().equals("")) {
            throw new ValidationException("Kunci harus diisi");
        }

        if (!idKey1.getText().toString().equals(idKey.getText().toString())){
            throw new ValidationException("Kunci tidak sesuai");
        }

        if (idJawaban.getText().toString().equals("")){
            throw new ValidationException("Jawaban harus diisi");
        }
    }

}
