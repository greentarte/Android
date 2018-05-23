package com.example.student.cluster;


import android.os.AsyncTask;
import android.os.Handler;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    TextView textView, textView2;
    TextView start_km, start_mm, start_whkm, charging_km, charging_kwh, charging_whkm, month_km, month_kwh, month_whkm, total_mileage;
    ViewPager viewPager;
    ImageView imageView;
    Server server;
    boolean flag = true;

    Handler handler = new Handler(); // start_mm 갱신
    int total_capacity = 90; //자동차의 배터리용량
    int lastCharge_capacity = 80; // 최근 충전종료시 배터리 용량
    int start_capacity = 80; //처음 시동시 배터리 용량
    int now_capacity = 80;  //현재 자동차의 배터리 용량
    double publicMileage = 3.9; //공인연비 3.9km/kWh;
    int startkm = 0;

    private int mm = 0;
    double startwhkm = 0;

    int chargekm = 50;
    double chargekwh = 12.2;
    double chargewhkm = 212;

    int monthkm = 2391;
    double monthkwh = 5260;
    double monthwhkm = 220;

    int total_distance = 23433;
    int battary_percent= now_capacity/total_capacity*100;
    private String time = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //상태바 제거
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // 값 불러오기
        start_km = findViewById(R.id.start_km);
        start_mm = findViewById(R.id.start_mm);
        start_whkm = findViewById(R.id.start_whkm);

        charging_km = findViewById(R.id.charging_km);
        charging_kwh = findViewById(R.id.charging_kwh);
        charging_whkm = findViewById(R.id.charging_whkm);

        month_km = findViewById(R.id.month_km);
        month_kwh = findViewById(R.id.month_kwh);
        month_whkm = findViewById(R.id.month_whkm);

        total_mileage = findViewById(R.id.total_mileage);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        viewPager = findViewById(R.id.pager);

        //배터리
        imageView=findViewById(R.id.imageView);
        //CAN통신으로 값을 받아와서 입력되는 값

        int v = 60; //속도
        textView.setText(""+v); //can값을 입력

        //배터리 퍼센테이지
        String percent= ""+battary_percent;


        //

        //시동 후 시간 카운트
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                time = String.valueOf(mm);
                                start_mm.setText(time);
                                start_km.setText(""+startkm);
                                start_whkm.setText("" + startwhkm);
                                charging_km.setText(""+chargekm);
                                charging_kwh.setText("" + chargekwh);
                                charging_whkm.setText("" + chargewhkm);
                                month_km.setText(""+monthkm);
                                month_kwh.setText("" + monthkwh);
                                month_whkm.setText("" + monthwhkm);
                                total_mileage.setText(""+total_distance);

                            }
                        });
                        Thread.sleep(10000); // 1 분마다60000
                        mm += 1;
                        startkm += 6;
                        chargekm += 6;
                        monthkm += 6;
                        total_distance += 6;
                        //연비를 구해야함(시동 후 , 충전 후, 월간 후)
                        //시동 후 연비  (시동시 배터리용량-현재용량)*1000
                        startwhkm = 256.4; //공인연비 적용값
                        now_capacity = start_capacity - ((int) (chargewhkm * startkm) / 1000);
                        chargekwh += ((int) (chargewhkm * startkm) / 1000);
                        monthkwh += ((int) (chargewhkm * startkm) / 1000);
//                        chargewhkm=212.4; //임의값
//                        monthwhkm=257.4; //임의값
                        battary_percent= now_capacity/total_capacity*100;



                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();


        //Server Start
        try {
            server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setSpeed(final String msg) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(msg);
                    }
                });
            }
        };
        new Thread(r).start();
    }

/*    public void setConnect(final String client,final String msg){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(msg.equals("t")){
                            textView2.setText(client+" Client Connected");
                        }else{
                            textView2.setText("Client DisConnected");
                        }
                    }
                });
            }
        };
        new Thread(r).start();
    }*/

    // HttpRequest Start ....
    // HttpRequest Start ....
    class SendHttp extends AsyncTask<Void, Void, Void> {

        String surl = "http://70.12.114.153/ws/main.do?speed=";
        URL url;
        HttpURLConnection urlConn;
        String speed;

        public SendHttp() {
        }

        public SendHttp(String speed) {
            this.speed = speed;
            surl += speed;
            try {
                url = new URL(surl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    // HttpRequest End ....

    // ServerSocket Start .....

    public class Server extends Thread {

        ServerSocket serverSocket;
        boolean flag = true;
        boolean rflag = true;
        HashMap<String, DataOutputStream> map =
                new HashMap<>();

        public Server() throws IOException {
            // Create ServerSocket ...
            serverSocket = new ServerSocket(8888);
            Log.d("[Server]", "Ready Server...");
        }

        @Override
        public void run() {
            // Accept Client Connection ...
            try {
                while (flag) {
                    Log.d("[Server]", "Waiting Server...");
                    Socket socket =
                            serverSocket.accept();
                    String client = socket.getInetAddress().getHostAddress();
                    //setConnect(client, "t");
                    new Receiver(socket).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        class Receiver extends Thread {

            InputStream in;
            DataInputStream din;
            OutputStream out;
            DataOutputStream dout;
            Socket socket;
            String ip;

            public Receiver(Socket socket) {
                try {
                    this.socket = socket;
                    in = socket.getInputStream();
                    din = new DataInputStream(in);
                    out = socket.getOutputStream();
                    dout = new DataOutputStream(out);
                    ip = socket.getInetAddress().getHostAddress();
                    map.put(ip, dout);
                    System.out.println("Connected Count:" + map.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // end Recevier


            @Override
            public void run() {
                try {
                    while (rflag) {

                        if (socket.isConnected() &&
                                din != null && din.available() > 0) {

                            String str = din.readUTF();
                            Log.d("[Server APP]", str);
                            setSpeed(str);

                            SendHttp sendHttp = new SendHttp(str);
                            sendHttp.execute();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //setConnect(null,"f");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    if (dout != null) {
                        try {
                            dout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (din != null) {
                        try {
                            din.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        public void sendAll(String msg) {
            System.out.println(msg);
            Sender sender = new Sender();
            sender.setMeg(msg);
            sender.start();
        }

        // Send Message All Clients
        class Sender extends Thread {

            String msg;

            public void setMeg(String msg) {
                this.msg = msg;
            }

            @Override
            public void run() {
                try {
                    Collection<DataOutputStream>
                            col = map.values();
                    Iterator<DataOutputStream>
                            it = col.iterator();
                    while (it.hasNext()) {
                        it.next().writeUTF(msg);
                    }

                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }

        public void stopServer() {
            rflag = false;
        }

    }

    // ServerSocket End .....


} // end MainActivity

