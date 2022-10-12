package com.example.asm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapter.StudentAdapter;
import com.example.asm.dao.StudentDAO;
import com.example.asm.models.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private ListView lvStudents;
    private  AlertDialog dialog;
    private ArrayList list;
    private StudentDAO dao;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        lvStudents = findViewById(R.id.lvStudents);
        //fill data
        dao = new StudentDAO(StudentActivity.this);

    }

    public void fillData() {
        dao=new StudentDAO(this);
        list = dao.getAll();
        adapter = new StudentAdapter(list);
        lvStudents.setAdapter(adapter);
    }


    public void onDelete(Student student) {
        new AlertDialog.Builder(StudentActivity.this)
                .setTitle("Xác nhận xóa")
                .setMessage("Xóa sẽ không hồi phục được")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Boolean result = dao.delete(student.getId());
                        if (result == true) {
                            Toast.makeText(StudentActivity.this, "Đã xóa", Toast.LENGTH_LONG).show();
                            fillData();
                        } else {
                            Toast.makeText(StudentActivity.this, "Không xoá được",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();
    }
    public  void  onClickEdit(Student student){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_studen_info,null);
        EditText edtName =view.findViewById(R.id.edtName);
        EditText edtClazz = view.findViewById(R.id.edtClazz);
        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnHuy= view.findViewById(R.id.btnHuy);

        edtName.setText(student.getName());
        edtClazz.setText(student.getClazz_id().toString());

//        gán sự kiện cho 2 button
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText(null);
                edtClazz.setText(null);
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String clazz= edtClazz.getText().toString();
                student.setName(name);
                student.setClazz_id(Integer.parseInt(clazz));
                Boolean result =dao.update(student);
                if (result == true) {
                    Toast.makeText(StudentActivity.this, "Đã cập nhật", Toast.LENGTH_LONG).show();
                    fillData();
                } else {
                    Toast.makeText(StudentActivity.this, "Cập nhật thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });

//        hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setTitle("Cập nhật thông tin sinh viên");
        builder.setView(view);
        builder.setCancelable(false);
        dialog= builder.create();
        dialog.show();

    }
    public  void onAddClick(View _view){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_new_student,null);
        EditText editUsename =view.findViewById(R.id.editUsename);
        EditText editPassword = view.findViewById(R.id.editPassword);
        EditText edtName =view.findViewById(R.id.edtName);
        EditText edtClazz = view.findViewById(R.id.edtClazz);
        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnHuy= view.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText(null);
                edtClazz.setText(null);
                editPassword.setText(null);
                editUsename.setText(null);
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String usename = editUsename.getText().toString();
            String pass = editPassword.getText().toString();
            String clazz=edtClazz.getText().toString();
            String name = edtName.getText().toString();
            Student student = new Student(1 ,Integer.parseInt(clazz),usename,pass,name);
                Boolean result =dao.insert(student);
                if (result == true) {
                    Toast.makeText(StudentActivity.this, "Đã thêm mới", Toast.LENGTH_LONG).show();
                    fillData();
                } else {
                    Toast.makeText(StudentActivity.this, "Thêm mới thất bại",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();

            }
        });
//        hiển thị dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setTitle("Thêm mới thông tin sinh viên");
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
public  void onBack(View view){
        finish();
}
}