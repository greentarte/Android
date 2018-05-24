package com.example.student.evproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CtrlFragment extends Fragment {
    ImageView wind;
    TextView temp_set;
    ImageButton temp_up, temp_down, w_up, w_down;
    int windP = 0;
    int temp = 23;

    public CtrlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.fragment_ctrl, container, false);

        temp_set = ll.findViewById(R.id.temp_set);
        temp_up = ll.findViewById(R.id.bt_temp_up);
        temp_down = ll.findViewById(R.id.bt_temp_down);

        wind = ll.findViewById(R.id.wind);
        w_up = ll.findViewById(R.id.bt_wind_up);
        w_down = ll.findViewById(R.id.bt_wind_down);

        //설정온도 up 클릭이벤트

        temp_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp >= 17 && temp <32){
                    temp++;
                }
                temp_set.setText(String.valueOf(temp));
            }
        });

        //설정온도 down 클릭이벤트
        temp_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp > 17 && temp <= 32){
                    temp --;
                }
                temp_set.setText(String.valueOf(temp));
            }
        });


        //바람세기 up 버튼 클릭이벤트
        w_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(windP >= 0 && windP <4){
                    windP++;
                }
                switch (windP){
                    case 0: wind.setImageResource(R.drawable.fan);
                        break;
                    case 1: wind.setImageResource(R.drawable.fan_1);
                        break;
                    case 2: wind.setImageResource(R.drawable.fan_2);
                        break;
                    case 3: wind.setImageResource(R.drawable.fan_3);
                        break;
                    case 4: wind.setImageResource(R.drawable.fan_4);
                        break;
                }
            }
        });

        //바람세기 down 버튼 클릭이벤트
        w_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(windP > 0 && windP <=4){
                    windP--;
                }
                switch (windP){
                    case 0: wind.setImageResource(R.drawable.fan);
                        break;
                    case 1: wind.setImageResource(R.drawable.fan_1);
                        break;
                    case 2: wind.setImageResource(R.drawable.fan_2);
                        break;
                    case 3: wind.setImageResource(R.drawable.fan_3);
                        break;
                    case 4: wind.setImageResource(R.drawable.fan_4);
                        break;
                }
            }
        });



        // Inflate the layout for this fragment
        return ll;
    }
}
