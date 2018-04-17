package com.example.student.p553;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView, imageView2;
    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.im1);
        imageView2 = findViewById(R.id.im2);
        myHandler = new MyHandler();
    }

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            //받는 쓰레드
            for (int i=1; i<=7; i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Image Switch ...
            }
        }
    });
    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
    //보내는 쓰레드
            for (int i=1; i<=7; i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Image Switch ...
                //handler필요
                Message msg =new Message();
            }
        }
    });
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

        }
    }
    public void clickBt(View v) {
    t1.start();
    t2.start();
    }
}
