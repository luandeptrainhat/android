package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.asm.Database.appdatabase;
import com.example.asm.models.Student;
import java.util.ArrayList;

/*
 * viết các hàm thao tác với DB
 * insert,update, delete,select
 * */
public class StudentDAO {
    private appdatabase database;

    public StudentDAO(Context context) {
            database=appdatabase.getInstance(context);
    }

    public Boolean insert(Student student) {
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try {
            ContentValues values = new ContentValues();
            values.put("USENAME", student.getUsername());
            values.put("PASSWORD", student.getPassword());
            values.put("CLAZZ_ID", student.getClazz_id());
            values.put("NAME", student.getName());
            Long rows = db.insertOrThrow("STUDENTS", null, values);
            result = rows >= 1;
            db.setTransactionSuccessful();//xác nhận thành công


        } catch (Exception e) {
            Log.d(">>>>>>>>>>>>>>>tag", "insert: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
        return result;
    }

    public Boolean update(Student student) {
        Boolean result = true;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); // bắt đầu tương tác db
        try {
            ContentValues values = new ContentValues();
            values.put("CLAZZ_ID", student.getClazz_id());
            values.put("NAME", student.getName());
            Integer rows = db.update("STUDENTS", values, " ID = ?",
                    new String[]{student.getId().toString()});
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
            Integer rows = db.delete("STUDENTS", " ID = ?",
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
    public ArrayList<Student> getAll() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, USENAME, PASSWORD, NAME,CLAZZ_ID FROM STUDENTS";
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Integer _id = cursor.getInt(0);
                    String _username = cursor.getString(1);
                    String _password = cursor.getString(2);
                    String _name = cursor.getString(3);
                    Integer _clazz_id = cursor.getInt(4);
                    Student student = new Student(_id, _clazz_id, _username,_password,_name);
                    list.add(student);
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
    public Student login(String usename, String password){
        Student student=null;
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, USENAME, PASSWORD, NAME,CLAZZ_ID FROM STUDENTS WHERE USENAME=?";
        Cursor cursor = db.rawQuery(sql, new String[]{usename});
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Integer _id = cursor.getInt(0);
                    String _username = cursor.getString(1);
                    String _password = cursor.getString(2);
                    String _name = cursor.getString(3);
                    Integer _clazz_id = cursor.getInt(4);
                    if(!password.equals(_password))break;

                    student = new Student(_id,_clazz_id,_username,null,_name);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
            {

            }
        }
        return  student;
    }
}
