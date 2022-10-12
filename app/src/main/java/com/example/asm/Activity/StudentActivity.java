package com.example.asm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapter.StudentAdapter;
import com.example.asm.dao.ClazzDAO;
import com.example.asm.dao.StudentDAO;
import com.example.asm.models.Clazz;
import com.example.asm.models.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private ListView lvStudents;
    private  AlertDialog dialog;
    private ArrayList list;
    private StudentDAO dao;
    private StudentAdapter adapter;
    private  ArrayList<Clazz> classes = new ArrayList<>();
    private ClazzDAO clazzDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        lvStudents = findViewById(R.id.lvStudents);
        //fill data
        dao = new StudentDAO(StudentActivity.this);
        clazzDAO = new ClazzDAO(StudentActivity.this);
        classes = clazzDAO.getAll();

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
        final Integer[] clazzID = {1};
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_new_student,null);
        EditText editUsename =view.findViewById(R.id.editUsename);
        EditText editPassword = view.findViewById(R.id.editPassword);
        EditText edtName =view.findViewById(R.id.edtName);
        Spinner edtClazz = view.findViewById(R.id.edtClazz);
        Button btnSave=view.findViewById(R.id.btnSave);
        Button btnHuy= view.findViewById(R.id.btnHuy);
//hiện spinner (danh sách lớp để chọn)
        ArrayAdapter<Clazz> clazzArrayAdapter = new ArrayAdapter<>(StudentActivity.this,
                android.R.layout.simple_spinner_dropdown_item, classes.toArray(new Clazz[0]));
            clazzArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edtClazz.setAdapter(clazzArrayAdapter);
            edtClazz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    Adapter adapter = adapterView.getAdapter();
                    Clazz clazz = (Clazz) adapter.getItem(position);
                    clazzID[0] = clazz.getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText(null);
//                edtClazz.setText(null);
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
//            String clazz=edtClazz.getText().toString();
            String name = edtName.getText().toString();
            Student student = new Student(1 ,clazzID[0],usename,pass,name);
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