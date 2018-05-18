package com.example.student.pdh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtv;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtv = findViewById(R.id.txtv); //textview의 id값 받아오기
        webView.getSettings().setJavaScriptEnabled(true); //불러오는 url의 java스크립트를 허용하는지
        webView.loadUrl("http://70.12.114.147/pdh");







    }

}
