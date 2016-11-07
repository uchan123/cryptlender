package com.example.user.calender;

import android.content.Context;

/**
 * Created by user on 03/11/2016.
 */
public class Set {
    private static Set ourInstance;
    private static Context c;

    public static Set i(Context c) {
        if (null == ourInstance) {
            ourInstance = new Set(c);
        }

        return ourInstance;
    }

    private Set(Context c) {
        this.c = c;
    }

    public boolean isFirst (){
        DatabaseHelper db = new DatabaseHelper(c);
        return db.getSettingbyname("first").getValue().equals("1") ? true : false;
    }

    public Setting get(String nama){
        DatabaseHelper db = new DatabaseHelper(c);
        Setting s = db.getSettingbyname(nama);
        s.setContext(c);
        return s;
    }
}
