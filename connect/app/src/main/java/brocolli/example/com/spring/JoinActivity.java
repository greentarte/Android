package brocolli.example.com.spring;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinActivity extends AppCompatActivity {

    EditText editText1,editText2,editText3;
    ProgressDialog progressDialog;
    LoginTask loginTask;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        editText1 = findViewById(R.id.editText3);
        editText2 = findViewById(R.id.editText4);
        editText3 = findViewById(R.id.editText5);
        progressDialog = new ProgressDialog(JoinActivity.this);

    }


    public void clickBt(View v){

        String id = editText1.getText().toString().trim();
        String pwd = editText2.getText().toString().trim();
        String name = editText3.getText().toString().trim();
        if (id == null || pwd == null || name == null ||id.equals("") || pwd.equals("") || name.equals("")) {
            return;
        }
        loginTask = new LoginTask("http://70.12.114.136/mv/join.do?id="+id+"&pwd="+pwd+"&name="+name);
        loginTask.execute();

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
            progressDialog.setMessage("SIGN UP");
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
                    con.setConnectTimeout(10000);   //connection 5초이상 길어지면 exepction
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


            if(s.equals("1")) {
                Toast.makeText(JoinActivity.this, "회원가입 성공" , Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
            else{

                Toast.makeText(JoinActivity.this, "회원가입 실패" , Toast.LENGTH_SHORT).show();
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");

            }




        }

    } //LoginTask



}
