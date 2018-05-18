package com.example.student.androidtooracle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        webView = findViewById(R.id.webView);
        webView.loadUrl("http://m.naver.com");

        String sendmsg = "vision_write";

        String result = "값"; //자신이 보내고싶은 값을 보내시면됩니다

        try {

            String rst = new Task(sendmsg).execute(result, "vision_write").get();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


}
