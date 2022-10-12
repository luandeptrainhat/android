package com.example.asm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.dao.StudentDAO;
import com.example.asm.models.Student;

public class LoginAtivity extends AppCompatActivity {
private EditText edtUsename,edtPassword;
private StudentDAO studentDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        readLogin();
        edtUsename=findViewById(R.id.editUsename);
        edtPassword=findViewById(R.id.editPassword);
        studentDAO = new StudentDAO(LoginAtivity.this);
    }
    public void onLoginClick(View view){
        String usename = edtUsename.getText().toString();
        String password = edtPassword.getText().toString();
        Student result = studentDAO.login(usename,password);
        if(result== null){
            Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show();
        }else {
//            lưu trạng thái đăng nhập
            saveLogin(result);
            Toast.makeText(this,"Login success",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public  void onRegisterclick(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    private void saveLogin(Student student){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn",true);
        editor.putInt("id",student.getId());
        editor.commit();
    }
    private void readLogin(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        Boolean isLoggedIn = preferences.getBoolean("isLoggedIn",false);
        if(isLoggedIn){
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}