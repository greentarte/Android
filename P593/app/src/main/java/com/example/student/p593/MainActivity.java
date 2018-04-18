package com.example.student.p593;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText, editText2;
    LoginTask loginTask;
    ProgressDialog progressDialog;
    LocationTask locationTask;
    boolean flag = true;
    AlertDialog.Builder alBuilder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(MainActivity.this);
        alBuilder = new AlertDialog.Builder(MainActivity.this);
        new Thread(r).start();

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            while (flag) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //좌표를 가지고 온다.
                locationTask = new LocationTask("http://70.12.114.148/android/location.jsp");
                locationTask.execute(37.12, 127.123);
            }
        }
    };


    public void clickBt(View v) {
        String id = editText.getText().toString();
        String pwd = editText2.getText().toString();
        if (id == null || pwd == null || id.equals("") || pwd.equals("")) {
            return;
        }
        loginTask = new LoginTask("http://70.12.114.148/android/login.jsp");
        loginTask.execute(id.trim(), pwd.trim());
    }

    class LoginTask extends AsyncTask<String, String, String> {

        String url;

        public LoginTask() {
        }

        public LoginTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Login...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String id = strings[0];
            String pwd = strings[1];

            url += "?id=" + id + "&pwd=" + pwd;

            //HTTP request
            StringBuilder sb = new StringBuilder();
            URL url = null;
            HttpURLConnection con = null;
            BufferedReader reader =null;
            try {
                url = new URL(this.url);
                con = (HttpURLConnection) url.openConnection();
                if (con != null) {
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "*/*");
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;
                }
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = null;
                while (true){
                    line = reader.readLine();
                    if(line==null){
                        break;
                    }
                    sb.append(line);

                }

            } catch (Exception e) {
                progressDialog.dismiss();
                return e.getMessage();

            } finally {
                try {
                    if(reader!=null){
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                con.disconnect();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s.trim().equals("1")){
                Toast.makeText(MainActivity.this, "Login ok", Toast.LENGTH_SHORT).show();
            }else if(s.trim().equals("0")){
                Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();

        }
    }

    class LocationTask extends AsyncTask<Double, Void, String> {
        String url;

        public LocationTask() {
        }

        ;

        public LocationTask(String url) {
            this.url = url;
        }


        @Override
        protected String doInBackground(Double... doubles) {
            double lat = doubles[0];
            double log = doubles[1];

            url += "?lat=" + lat + "&log=" + log;

            //HTTP request
            StringBuilder sb = new StringBuilder();
            URL url = null;
            HttpURLConnection con = null;
            try {
                url = new URL(this.url);
                con = (HttpURLConnection) url.openConnection();
                if (con != null) {
                    con.setConnectTimeout(30000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "*/*");
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;
                }
            } catch (Exception e) {
                return e.getMessage();
            } finally {
                con.disconnect();
            }
            return "";
        }
    } //end LocationTask

    @Override
    public void onBackPressed() {
        alBuilder.setTitle("Alert");
        alBuilder.setMessage("Are you Finish App?");
        alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                flag = false;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        alBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}