package brocolli.example.com.spring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {
    TextView back,air_control,indoor_temp,temp_down,set_temp,temp_up;
    LinearLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        background=findViewById(R.id.background);
        back=findViewById(R.id.back);
        air_control=findViewById(R.id.air_control);
        indoor_temp=findViewById(R.id.indoor_temp);
        temp_down=findViewById(R.id.temp_down);
        set_temp=findViewById(R.id.set_temp);
        temp_up=findViewById(R.id.temp_down);
    }

    //background image change
    public void change_cool(View v) {
        background.setBackgroundResource(R.drawable.cool_phone);
    }
    public void change_heat(View v) {
        background.setBackgroundResource(R.drawable.heat_phone);
    }
    public void change_default(View v) {
        background.setBackgroundResource(R.drawable.off_phone);
    }
}
