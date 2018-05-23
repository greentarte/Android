package brocolli.example.com.spring;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProgressDialog progressDialog;
    LoginTask loginTask;
    WebView webView;
    private SharedPreferences sf;
    String id = "";
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Intent initIntent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(initIntent);

        progressDialog = new ProgressDialog(MainActivity.this);
        textView = findViewById(R.id.textView1);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        textView.setText(name + "님 안녕하세요:)");


        sf = getSharedPreferences("loginData", MODE_PRIVATE);
        id = sf.getString("id", "");

        webView = findViewById(R.id.webView);
        webView.setVisibility(View.INVISIBLE);


    }


    public void clickButton(View v) {


        switch (v.getId()) {
            case R.id.button:
                loginTask = new LoginTask("http://70.12.114.136/mv/insertData9.do?id=" + id + "&time_nine=1");
                loginTask.execute();
                break;

            case R.id.button2:
                loginTask = new LoginTask("http://70.12.114.136/mv/updateData2.do?id=" + id + "&time_two=1");
                loginTask.execute();
                break;

            case R.id.button3:
                loginTask = new LoginTask("http://70.12.114.136/mv/updateData6.do?id=" + id + "&time_six=1");
                loginTask.execute();
                break;

            case R.id.button4:
                loginTask = new LoginTask("http://70.12.114.136/mv/extractData.do?id=" + id + "&month=12");
                loginTask.execute();
                break;

            case R.id.button5:
                webView.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl("http://70.12.114.136/mv/view/android.jsp?id=" + id);
                webView.invalidate();
                break;


            case R.id.button6:

                break;

            case R.id.button7:
                logout();
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;

        }
    }


    private void logout() {

        SharedPreferences.Editor editor = sf.edit();
        editor.putString("id", "");
        editor.commit();

    }


    class LoginTask extends AsyncTask<String, Void, String> {

        String url;

        LoginTask() {
        }

        LoginTask(String url) {
            this.url = url;
        }


        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            //http request
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {
                url = new URL(this.url);
                con = (HttpURLConnection) url.openConnection();

                if (con != null) {
                    con.setConnectTimeout(5000);   //connection 5초이상 길어지면 exepction
                    //con.setReadTimeout(10000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "*/*");
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;


                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }


                }


            } catch (Exception e) {
                progressDialog.dismiss();
                return e.getMessage();   //리턴하면 post로

            } finally {

                try {
                    if (reader != null) {
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

            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

        }


    }


}
