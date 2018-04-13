package com.example.student.myfragment;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add().commit();
            R.id.container,
            new MainFragment()).commit();
        }
    }
    public static class MainFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable View v){
            View v =inflater.inflate(R.layout.mainlayout,container,attachTo);
            return v;
        }
    }
}
