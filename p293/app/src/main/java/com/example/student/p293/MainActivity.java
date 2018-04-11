package com.example.student.p293;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("pref", Activity.MODE_PRIVATE);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        resetState();
    }

    @Override
    protected  void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        saveState();
    }
    @Override
    protected void onStop() {
        super.onStop();
        saveState();
    }


    public void resetState() {
        if ( sp != null) {
            if( sp.contains("cnt"))  {
                int rcnt = sp.getInt("cnt",0);
                Toast.makeText(this, rcnt, Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void saveState() {
        SharedPreferences.Editor editor = sp.edit();
        if(sp != null) {
            if(sp.contains("cnt") ) {
                int rcnt = sp.getInt("cnt",0);
                editor.putInt("cnt", ++rcnt);

            } else {
                int cnt = 0;
                editor.putInt("cnt",++cnt);
                editor.commit();
            }
            editor.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Message ||");
        builder.setMessage("Are you want to exit & clear");
        builder.setNegativeButton("NO", DialogInterface.OnClickListener);
}
