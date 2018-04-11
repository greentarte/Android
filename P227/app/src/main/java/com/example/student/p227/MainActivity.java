package com.example.student.p227;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    WebView wb_vw;
    LinearLayout lnr_lyt;
    ImageView img_vw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    public void initUI() {
        wb_vw = findViewById(R.id.wb_vw);
        lnr_lyt = findViewById(R.id.lnr_lyt);
        img_vw = findViewById(R.id.img_vw);

        wb_vw.setWebViewClient(new WebViewClient());
        wb_vw.getSettings().setJavaScriptEnabled(true);

        wb_vw.setVisibility(View.INVISIBLE);
        lnr_lyt.setVisibility(View.INVISIBLE);
        img_vw.setVisibility(View.INVISIBLE);
    }

    public void onClickBnt(View v) {
        switch (v.getId()) {
            case R.id.btn1 :
                wb_vw.setVisibility(View.VISIBLE);
                wb_vw.loadUrl("http://www.naver.com");
                lnr_lyt.setVisibility(View.INVISIBLE);
                img_vw.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn2 :
                wb_vw.setVisibility(View.INVISIBLE);
                lnr_lyt.setVisibility(View.VISIBLE);
                img_vw.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn3 :
                wb_vw.setVisibility(View.INVISIBLE);
                lnr_lyt.setVisibility(View.INVISIBLE);
                img_vw.setVisibility(View.VISIBLE);
                break;
        }
    }
}
