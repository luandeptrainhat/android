package com.example.asm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.dao.StudentDAO;
import com.example.asm.models.Student;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsename,edtPassword;
    private StudentDAO studentDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsename=findViewById(R.id.editUsename);
        edtPassword=findViewById(R.id.editPassword);
        studentDAO = new StudentDAO(RegisterActivity.this);
    }
    public void onRegisterclick(View view){
        String usename = edtUsename.getText().toString();
        String password = edtPassword.getText().toString();
        Student student= new Student(-1,11,usename,password,null);
        Boolean result = studentDAO.insert(student);
        if(result== null){
            Toast.makeText(this,"Register failed",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Register success",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    public void onLoginClick(View view){
        finish();
    }
}