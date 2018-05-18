package brocolli.example.com.spring;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText1,editText2;
    ProgressDialog  progressDialog;
    LoginTask loginTask;
    Intent intent;
    private SharedPreferences sf;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(MainActivity.this);

        sf = getSharedPreferences("loginData",MODE_PRIVATE);


    }


    public void clickBt(View v){

        id = editText1.getText().toString().trim();
        String pwd = editText2.getText().toString().trim();
        if (id == null || pwd == null || id.equals("") || pwd.equals("")) {
            return;
        }
        loginTask = new LoginTask("http://70.12.114.136/mv/login.do?id="+id+"&pwd="+pwd);
        loginTask.execute();

    }


    public void clickBt2(View v){

        intent = new Intent(MainActivity.this,JoinActivity.class);
        startActivity(intent);

    }



    class LoginTask extends AsyncTask<String,Void,String> {

        String url;

        LoginTask() {
        }

        LoginTask(String url) {
            this.url = url;
        }


        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Login");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            //http request
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection con= null;
            BufferedReader reader = null;

            try{
                url = new URL(this.url);
                con = (HttpURLConnection) url.openConnection();

                if(con!=null){
                    con.setConnectTimeout(5000);   //connection 5초이상 길어지면 exepction
                    //con.setReadTimeout(10000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept","*/*");
                    if(con.getResponseCode()!=HttpURLConnection.HTTP_OK)
                        return null;



                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = null;
                    while(true){
                        line = reader.readLine();
                        if(line == null){
                            break;
                        }
                        sb.append(line);
                    }


                }



            }catch(Exception e){
                progressDialog.dismiss();
                return e.getMessage();   //리턴하면 post로

            }finally {

                try {
                    if(reader!= null) {
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


            if(s.charAt(0)=='1') {

                saveLoginData(); //아이디 값 sharedpreferences 저장함수
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                editText1.setText("");
                editText2.setText("");

                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("name",s.substring(2));
                startActivity(intent);

            }


            else{

                editText1.setText("");
                editText2.setText("");
                Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();

            }


        }

    }


    private void saveLoginData(){


        SharedPreferences.Editor editor = sf.edit();
        editor.putString("id",id);
        editor.commit();

    }





}




