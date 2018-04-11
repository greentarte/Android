package com.example.student.p230;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView img_vw;
    WebView wb_vw;
    LinearLayout btn_lyt;
    RelativeLayout rgst_lyt;
    RelativeLayout login_lyt;
    TextView txt_time;
    Date day;
    Calendar cal;
    SimpleDateFormat sdf;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread timeText = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timeText.start();
    }

    public void initUI() {
        img_vw = findViewById(R.id.img_vw);
        wb_vw = findViewById(R.id.wb_vw);
        btn_lyt = findViewById(R.id.btn_lyt);
        rgst_lyt = findViewById(R.id.rgst_lyt);
        login_lyt = findViewById(R.id.login_lyt);
        txt_time = findViewById(R.id.txt_time);
        sdf = new SimpleDateFormat("hh:mm:ss a");
        wb_vw.setWebViewClient(new WebViewClient());
        wb_vw.getSettings().setJavaScriptEnabled(true);

        wb_vw.setVisibility(View.INVISIBLE);
        img_vw.setVisibility(View.INVISIBLE);
        rgst_lyt.setVisibility(View.INVISIBLE);
        login_lyt.setVisibility(View.INVISIBLE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateTime();
            }
        };

    }

    public void onClickBnt(View v) {
        switch (v.getId()) {
            case R.id.btn_home :
                wb_vw.setVisibility(View.INVISIBLE);
                btn_lyt.setVisibility(View.VISIBLE);
                img_vw.setVisibility(View.INVISIBLE);
                rgst_lyt.setVisibility(View.INVISIBLE);
                login_lyt.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_register :
                wb_vw.setVisibility(View.INVISIBLE);
                btn_lyt.setVisibility(View.INVISIBLE);
                img_vw.setVisibility(View.INVISIBLE);
                rgst_lyt.setVisibility(View.VISIBLE);
                login_lyt.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_login :
                wb_vw.setVisibility(View.INVISIBLE);
                btn_lyt.setVisibility(View.INVISIBLE);
                img_vw.setVisibility(View.INVISIBLE);
                rgst_lyt.setVisibility(View.INVISIBLE);
                login_lyt.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_analysis :
                wb_vw.setVisibility(View.INVISIBLE);
                btn_lyt.setVisibility(View.INVISIBLE);
                img_vw.setVisibility(View.VISIBLE);
                rgst_lyt.setVisibility(View.INVISIBLE);
                login_lyt.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_naver :
                wb_vw.setVisibility(View.VISIBLE);
                wb_vw.loadUrl("http://m.naver.com");
                btn_lyt.setVisibility(View.INVISIBLE);
                img_vw.setVisibility(View.INVISIBLE);
                rgst_lyt.setVisibility(View.INVISIBLE);
                login_lyt.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void onClickWebGoBack(View v) {
        if(wb_vw.canGoBack() && wb_vw.getVisibility() == View.VISIBLE) {
            wb_vw.goBack();
        }
    }

    private void updateTime() {
        cal = Calendar.getInstance();
        day = cal.getTime();
        txt_time.setText(sdf.format(day));
    }
}
