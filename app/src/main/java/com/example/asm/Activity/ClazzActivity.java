package com.example.asm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapter.ClazzAdapter;
import com.example.asm.adapter.StudentAdapter;
import com.example.asm.dao.ClazzDAO;
import com.example.asm.dao.StudentDAO;
import com.example.asm.models.Clazz;
import com.example.asm.models.Student;

import java.util.ArrayList;

public class ClazzActivity extends AppCompatActivity {
    private ListView lvClazz;
    private ArrayList<Clazz> list;
    private ClazzDAO dao;
    private ClazzAdapter adapter;
    private  AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz);
        lvClazz = findViewById(R.id.lvClazz);
        dao = new ClazzDAO(ClazzActivity.this);
        list=dao.getAll();
        adapter = new ClazzAdapter(list);
        lvClazz.setAdapter(adapter);
    }
    //luân đẹp trai
    public void onDelete(Clazz clazz) {
        new AlertDialog.Builder(ClazzActivity.this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa sẽ không hồi phục được")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Boolean result = dao.delete(clazz.getId());
                        if (result == true) {
                            Toast.makeText(ClazzActivity.this, "Đã xóa", Toast.LENGTH_LONG).show();
                            fillData();
                        } else {
                            Toast.makeText(ClazzActivity.this, "Không xoá được",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();
    }
    public void fillData() {
        list = dao.getAll();
        adapter = new ClazzAdapter(list);
        lvClazz.setAdapter(adapter);
    }
    public  void onAddClick(View _view){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_new_clazz,null);
        EditText editName =view.findViewById(R.id.edtName);
        EditText edtTime = view.findViewById(R.id.edtTime);
        EditText edtRoom =view.findViewById(R.id.edtRoom);
        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnHuy= view.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setText(null);
                edtTime.setText(null);
                edtRoom.setText(null);
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String time = edtTime.getText().toString();
                String room=edtRoom.getText().toString();
                Clazz clazz1 = new Clazz(1,name,time,room);
                Boolean result =dao.insert(clazz1);
                if (result == true) {
//                    Toast.makeText(ClazzActivity.this, "Đã thêm mới", Toast.LENGTH_LONG).show();
//                    fillData();
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View _view = layoutInflater.inflate(R.layout.layout_them,null);
                    Toast toast = new Toast(ClazzActivity.this);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(_view);
                    toast.show();
                    fillData();
                } else {
                    Toast.makeText(ClazzActivity.this, "Thêm mới thất bại",
                            Toast.LENGTH_LONG).show();

                }
                dialog.dismiss();

            }
        });
//        hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ClazzActivity.this);
        builder.setTitle("Thêm mới thông tin sinh viên");
        builder.setView(view);
        builder.setCancelable(false);
        dialog= builder.create();
        dialog.show();
    }
    public  void  onClickEdit(Clazz clazz){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_clazz_info,null);
        EditText edtName =view.findViewById(R.id.edtName);
        EditText edtTime = view.findViewById(R.id.edtTime);
        EditText edtRoom = view.findViewById(R.id.edtRoom);
        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnHuy= view.findViewById(R.id.btnHuy);

        edtName.setText(clazz.getName());
        edtTime.setText(clazz.getTime());
        edtRoom.setText(clazz.getRoom());
//        gán sự kiện cho 2 button
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText(null);
                edtTime.setText(null);
                edtRoom.setText(null);
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String time= edtTime.getText().toString();
                String room=edtRoom.getText().toString();
                clazz.setName(name);
                clazz.setTime(time);
                clazz.setRoom(room);
                Boolean result =dao.update(clazz);
                if (result == true) {
//                    Toast.makeText(ClazzActivity.this, "Đã thêm lớp", Toast.LENGTH_LONG).show();
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View _view = layoutInflater.inflate(R.layout.layout_toast,null);
                    Toast toast = new Toast(ClazzActivity.this);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(_view);
                    toast.show();
                    fillData();
                } else {
                    Toast.makeText(ClazzActivity.this, "Sửa thông tin thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

//        hiển thị dialog mot hai ba
        AlertDialog.Builder builder = new AlertDialog.Builder(ClazzActivity.this);
        builder.setTitle("Cập nhật thông tin sinh viên");
        builder.setView(view);
        builder.setCancelable(false);
        dialog= builder.create();
        dialog.show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }
    public void onBack(View view){
        finish();
    }
}