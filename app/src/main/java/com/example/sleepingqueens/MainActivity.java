package com.example.sleepingqueens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button VS1btn,VSComputerbtn;
    private int player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        VS1btn=findViewById(R.id.VS1btn);
        VS1btn.setOnClickListener(this);
        VSComputerbtn=findViewById(R.id.VSComputerbtn);
        VSComputerbtn.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        //Intent i=new Intent(this, GameActivity.class);
        if(v==VS1btn)
        {
            CustomDialog customDialog=new CustomDialog(this);
            customDialog.show();
        //    i.putExtra("player",player);
        //    startActivity(i);
        }
        if(v==VSComputerbtn)
        {

        }


    }
}