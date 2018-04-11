package com.example.student.p211;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txt_id, txt_pwd;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    public void initUI() {
        txt_id = findViewById(R.id.txt_id);
        txt_pwd = findViewById(R.id.txt_pwd);
    }
    public void onClickLogin(View v) {
        String id = txt_id.getText().toString();
        String pwd = txt_pwd.getText().toString();
        Intent myIntent;
        txt_id.setText("");
        txt_pwd.setText("");
        Toast.makeText(this, id+""+pwd, Toast.LENGTH_LONG).show();
        if(id.equals("qq") && pwd.equals("11")) {
            myIntent = new Intent(getApplicationContext(), loginOK.class);
            myIntent.putExtra("loginID", id);
        } else {
            myIntent = new Intent(getApplicationContext(), loginFail.class);
        }
        startActivity(myIntent);
    }

}
