package com.example.asm.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.asm.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public  void goClazz(View view){
        Intent intent = new Intent(HomeActivity.this, ClazzActivity.class);
        startActivity(intent);
    }
    public  void goStudent(View view){
        Intent intent = new Intent(HomeActivity.this, StudentActivity.class);
        startActivity(intent);
    }
    public  void logOut(View view){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("isLoggedIn");
        editor.remove("id");
        editor.apply();
        Intent intent = new Intent(this,LoginAtivity.class);
        startActivity(intent);
        finish();
    }
}