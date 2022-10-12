package com.example.asm.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class appdatabase extends SQLiteOpenHelper {
    private static appdatabase instansce;
    private appdatabase(Context context){
        super(context, "my_database", null, 3);
    }


    public static synchronized  appdatabase getInstance(Context context){
        if(instansce == null) instansce = new appdatabase(context);
        return instansce;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlClazz = " CREATE TABLE IF NOT EXISTS CLAZZ (CLAZZ_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TIME TEXT , ROOM TEXT)";
        db.execSQL(sqlClazz);
        String sqlStudents = "CREATE TABLE IF NOT EXISTS STUDENTS( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USENAME TEXT, PASSWORD TEXT,NAME TEXT,CLAZZ_ID INTEGER, FOREIGN KEY (CLAZZ_ID) REFERENCES CLAZZ(CLAZZ_ID) )";
        db.execSQL(sqlStudents);

        db.execSQL("insert into CLAZZ (name, time, room) values ('Turkey,    common', '1:20 AM', 58)," +
                "('Iguana, marine', '6:23 AM', 61)," +
                "('Possum, western pygmy', '10:56 AM', 51)," +
                "('Verreaux''s sifaka', '8:27 PM', 27)," +
                "('Otter, canadian river', '3:12 AM', 60)," +
                "('Heron, gray', '1:49 PM', 55)," +
                "('Brolga crane', '3:26 PM', 10)," +
                "('Striped dolphin', '2:43 PM', 55)," +
                "('Miner''s cat', '5:24 AM', 66)," +
                "('Brown lemur', '8:53 PM', 39);");
        db.execSQL("insert into STUDENTS (USENAME, PASSWORD, NAME, CLAZZ_ID) values ('use1', '1', 'Bleucordon', 3)");
        db.execSQL("insert into STUDENTS (USENAME, PASSWORD, NAME, CLAZZ_ID) values ('use2', '2', 'Tasmanian devil', 4)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersoin, int newVersion) {
        if (oldVersoin != newVersion){
            db.execSQL("DROP TABLE IF EXISTS STUDENTS ");
            db.execSQL("DROP TABLE IF EXISTS CLAZZ ");
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

}


