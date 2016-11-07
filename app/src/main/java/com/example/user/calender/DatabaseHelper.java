package com.example.user.calender;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uchan on 24/10/2016.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "enkripsi_kalender.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_agenda = "Agenda";

    private static final String COL_ID = "ID";
    private static final String COL_TANGGAL = "TANGGAL";
    private static final String COL_JAM = "JAM";
    private static final String COL_JUDUL = "JUDUL";
    private static final String COL_ISI = "ISI";
    private static final String COL_LATITUDE = "LATITUDE";
    private static final String COL_LONGITUDE = "LONGITUDE";
    private static final String COL_PARTISIPAN = "PARTISIPAN";

    private static final String TABLE_pengaturan = "Pengaturan";
    private static final String COL_IDPENG = "IDPENGATURAN";
    private static final String COL_NAMA = "NAMA";
    private static final String COL_VALUE = "VALUE";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_TAG = "TAG";
    private static final String COL_REL = "REL";

    Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

   /* public List<Agenda> getAllAgenda(){ //listagenda
        List<Agenda> Agenda = new ArrayList<>() ;
        String query = "SELECT * FROM Agenda";
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if (c.moveToFirst()) {
            do{
               Agenda.add(new Agenda(
                       c.getString(0), c.getString(1), c.getString(2),
                       c.getString(3), c.getString(4), c.getString(5), c.getString(6)
               ));
            } while (c.moveToNext());
        }
        return Agenda;
    } */

    public List<Agenda> cari(String c) { //pencarian
        List<Agenda> nemu = new ArrayList<>();
        String query = "SELECT * FROM Agenda WHERE id LIKE '% " + c + " %' OR tanggal LIKE '% " + c + " %' OR jam LIKE '%" + c + "%' OR judul LIKE '%" + c + "%' OR isi LIKE '%" + c + "%' OR latitude LIKE '%" + c + "%' OR longitude LIKE '%" + c + "%' OR partisipan LIKE '%" + c + "%' ";
        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                nemu.add(new Agenda(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_TANGGAL)),
                        cursor.getString(cursor.getColumnIndex(COL_JAM)),
                        cursor.getString(cursor.getColumnIndex(COL_JUDUL)),
                        cursor.getString(cursor.getColumnIndex(COL_ISI)),
                        cursor.getDouble(cursor.getColumnIndex(COL_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(COL_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(COL_PARTISIPAN))
                ));
            } while (cursor.moveToNext());
        }
        return nemu;
    }

    //tambah
    public void addAgenda(Agenda a) { //listagenda
        String query = "INSERT INTO Agenda(" + COL_TANGGAL + "," + COL_JAM + "," + COL_JUDUL + "," + COL_ISI + "," + COL_LATITUDE + "," + COL_LONGITUDE +"," + COL_PARTISIPAN + ")" +
                " VALUES (" +
                " '"+a.tanggal+"',"+
                " '"+a.jam+"',"+
                " '"+a.judul+"',"+
                " '"+a.isi+"',"+
                " '"+a.latitude+"',"+
                " '"+a.longitude+"',"+
                " '"+a.partisipan+"' "+
                ") ";
        System.out.println(query);
        this.getReadableDatabase().execSQL(query);
    }
    //edit
    public void editAgenda(Agenda e) { //listagenda
        String query = "UPDATE Agenda SET "+COL_TANGGAL+" = '"+e.tanggal+"', "+COL_JAM+" = '"+e.jam+"', "+COL_JUDUL+" = '"+e.judul+"', "+COL_ISI+" = '"+e.isi+"', "+COL_LATITUDE+" = '"+e.latitude+"', "+COL_LONGITUDE+" = '"+e.longitude+"', "+COL_PARTISIPAN+" = '"+e.partisipan+"' " +
                " WHERE "+COL_ID+" = '"+e.id+"' ";
        System.out.println(query);
        this.getReadableDatabase().execSQL(query);

    }

    //delete
    public void deleteAgenda(Agenda d) { //listagenda
        String query = "DELETE  FROM Agenda WHERE "+COL_ID+" = '"+d.id+"' ";

        this.getReadableDatabase().execSQL(query);
    }


    //view agenda by tanggal
    public List<Agenda> getAgendabytanggal(String t) { //listagenda
        List<Agenda> Agenda = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_agenda+" WHERE "+COL_TANGGAL+" = '"+t+"' ";
        db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Agenda.add(new Agenda(
                        c.getInt(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getString(4), c.getDouble(5), c.getDouble(6), c.getString(7)
                ));
            } while (c.moveToNext());
        }
        return Agenda;
    }

    //get detail agenda
    public Agenda getDetail(String id) {
        Agenda agenda = null;
        String query = "SELECT * FROM "+TABLE_agenda+" WHERE "+COL_ID+" = '"+id+"' ";
        Cursor c = this.getReadableDatabase().rawQuery(query, null);

        if (c.moveToFirst()) {
            return new Agenda(
                    c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getDouble(5), c.getDouble(6), c.getString(7)
            );
        }
        return new Agenda();
    }

    //gettanggal
    public List<String> getalltanggal(){
        List<String> tanggal = new ArrayList<>();
        String query = "SELECT "+COL_TANGGAL+" FROM "+TABLE_agenda;
        Cursor c = this.getReadableDatabase().rawQuery(query,null);
        if (c.moveToFirst()) {
            do {
                tanggal.add(c.getString(0));
            } while (c.moveToNext());
        }
        return tanggal;
    }

    //addsetting
    public void addSetting(Setting s) { //listagenda
        String query = "INSERT INTO Pengaturan(" + COL_NAMA + "," + COL_VALUE + "," + COL_DESCRIPTION + "," + COL_TAG + ", "+COL_REL+")" +
                " VALUES (" +
                " '"+s.nama+"',"+
                " '"+s.value+"',"+
                " '"+s.description+"',"+
                " '"+s.tag+"',"+
                " '"+s.rel+"',"+
                ") ";
        System.out.println(query);
        this.getReadableDatabase().execSQL(query);
    }
    //editsetting
    public void editSetting(Setting t) { //listagenda
        String query = "UPDATE Pengaturan SET "+COL_NAMA+" = '"+t.nama+"', "+COL_VALUE+" = '"+t.value+"',"+COL_DESCRIPTION+" = '"+t.description+"', "+COL_TAG+" = '"+t.tag+"', "+COL_REL+" = '"+t.rel+"' " +
                " WHERE "+COL_IDPENG+" = '"+t.idpengaturan+"' ";
        System.out.println(query);
        this.getReadableDatabase().execSQL(query);
    }

    public Setting getSettingbyname (String nama){
        String query = "SELECT * FROM "+ TABLE_pengaturan+" WHERE "+COL_NAMA+" = '"+nama+"' ";
        Cursor c = this.getReadableDatabase().rawQuery(query,null);
        if (c.moveToFirst()){
            return new Setting(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getInt(5));
        } return new Setting();
    }

    public List<Setting> getSettingbytag (String tag){
        List<Setting> setting = new ArrayList<>() ;
        String query = "SELECT * FROM "+ TABLE_pengaturan +" WHERE "+COL_TAG+" = '"+tag+"' ";
        Cursor c = this.getReadableDatabase().rawQuery(query,null);

        if (c.moveToFirst()){
            do {
                setting.add(new Setting(
                        c.getInt(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getString(4), c.getInt(5)
                ) );
            } while (c.moveToNext());
        }return setting ;
    }
}