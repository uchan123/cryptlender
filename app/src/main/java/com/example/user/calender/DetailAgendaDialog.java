package com.example.user.calender;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 02/11/2016.
 */

public class DetailAgendaDialog extends DialogFragment {
    TextView txtTgl;
    TextView txtJam;
    //TextView txtJudul;
    TextView txtIsi;
    TextView txtLokasi;
    TextView txtPartisipan;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflater = getActivity().getLayoutInflater().inflate(R.layout.detail_agenda,null);
        txtTgl = (TextView) inflater.findViewById(R.id.idtgl);
        txtJam = (TextView) inflater.findViewById(R.id.idjam);
        //txtJudul = (TextView) inflater.findViewById(R.id.idjudul);
        txtIsi = (TextView) inflater.findViewById(R.id.idisi);
        txtLokasi = (TextView) inflater.findViewById(R.id.idlokasi);
        txtPartisipan = (TextView) inflater.findViewById(R.id.idpartisipan);

        final Agenda agenda = (Agenda) getArguments().getSerializable("agenda");
        txtTgl.setText(agenda.getTanggal());
        txtJam.setText(agenda.getJam());
        //txtJudul.setText(agenda.getJudul());
        txtIsi.setText(agenda.getIsi());
        //txtLokasi.setText(agenda.getLokasi());
        txtPartisipan.setText(agenda.getPartisipan());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(agenda.getJudul())
                .setView(inflater)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getActivity(), TambahAgenda.class);
                        i.putExtra("agenda", agenda);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       DatabaseHelper  db = new DatabaseHelper(getActivity());
                        db.deleteAgenda(agenda);
                        ((CalenderView)getActivity()).reload();
                        dialog.cancel();
                    }
                });

        return builder.create();

    }

}
