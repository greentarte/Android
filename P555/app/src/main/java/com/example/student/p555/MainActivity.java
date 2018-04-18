package com.example.student.p555;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText editText, editText2;
    TextView textView;
    LoginTask loginTask;
    ProgressDialog progressDialog;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Progress ...");
        progressDialog.setCancelable(false); //프로그레스 진행중 다른 곳을 터치해도 사라지지 않음

    }

    public void clickLogin(View v) throws ExecutionException, InterruptedException {
        loginTask = new LoginTask(); //버튼을 누를때 마다 task가 실행되게 삽입
        String id = editText.getText().toString(); //id 받기
        String pwd = editText2.getText().toString(); //pwd받기
        String result = "";
        //loginTask.onPreExecute();
        loginTask.execute(id, pwd); //main쓰레드에 id,pwd를 넘겨준다
        //get을 쓰면 해당 라인이 전부 실행이 끝나야 넘어감(결과를 끝까지 받아야하는 경우 사용
        //get을 사용하지 않으면 그냥 진행됨, 동시진행할 때 사용 (기본 쓰레드)
        //loginTask.onPostExecute("");

    }

    class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //커넥트
            progressDialog.show(); //프로그래스 돌아가는것 get과 같이 동작하지 않음
            button.setEnabled(false);


        }


        @Override
        protected String doInBackground(String... strings) {
            //제네릭으로 메소드 생성
            String id = strings[0];
            String pwd = strings[1];
            String result = "";
            try {
                Thread.sleep(30000);
                //2초 딜레이
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (id.equals("qq") && pwd.equals("11")) {
                result = "1";
            } else {
                result = "0";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            button.setEnabled(true);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            // 클로즈
            if (s.equals("1")) {
                textView.setText("Login OK");
                dialog.setTitle("Alert");
                dialog.setMessage("Login OK !!!");
                dialog.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText.setText("");
                        editText2.setText("");
                        return;
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            } else {
                textView.setText("Login Fail!");
                dialog.setTitle("Alert");
                dialog.setMessage("Login Fail !!!");
                dialog.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText.setText("");
                        editText2.setText("");
                        return;
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();

            }
        }
    }
}
