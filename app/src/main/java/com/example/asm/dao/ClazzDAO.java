package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm.Database.appdatabase;
import com.example.asm.models.Clazz;

import java.util.ArrayList;

public class ClazzDAO {
    private appdatabase database;

    public ClazzDAO(Context context) {
    database=appdatabase.getInstance(context);
    }

    public Boolean insert(Clazz clazz) {
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try {
            ContentValues values = new ContentValues();
//            values.put("CLAZZ_ID", clazz.getId());
            values.put("TIME", clazz.getTime());
            values.put("NAME", clazz.getName());
            values.put("ROOM", clazz.getRoom());
            Long rows = db.insertOrThrow("CLAZZ", null, values);
            result = rows >= 1;
            db.setTransactionSuccessful();//xác nhận thành công


        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>tag", "insert: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
        return result;
    }

    public Boolean update(Clazz clazz) {
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try {
            ContentValues values = new ContentValues();
            values.put("CLAZZ_ID", clazz.getId());
            values.put("NAME", clazz.getName());
            values.put("TIME", clazz.getTime());
            values.put("ROOM", clazz.getRoom());
            Integer rows = db.update("CLAZZ", values, " CLAZZ_ID = ?",
                    new String[]{clazz.getId().toString()});
            result = rows >= 1;
            db.setTransactionSuccessful();//xác nhận thành công


        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>tag", "update: " + e.getMessage());
        } finally {
            db.endTransaction(); //đóng giao dịch
        }
        return result;
    }

    //delete from students where id=1
    public Boolean delete(Integer id) {
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try {
            Integer rows = db.delete("CLAZZ", " CLAZZ_ID = ?",
                    new String[]{id.toString()});
            result = rows >= 1;
            db.setTransactionSuccessful();//xác nhận thành công
        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>tag", "delete: " + e.getMessage());
        } finally {
            db.endTransaction(); //đóng giao dịch
        }
        return result;
    }

    // select
    public ArrayList<Clazz> getAll() {
        ArrayList<Clazz> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT CLAZZ_ID, NAME, TIME, ROOM FROM CLAZZ";
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Integer _id = cursor.getInt(0);
                    String _name = cursor.getString(1);
                    String _time = cursor.getString(2);
                    String _room = cursor.getString(3);
                    Clazz clazz = new Clazz(_id,_name, _time,_room);
                    list.add(clazz);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            {

            }
        }
        return list;
    }
}
