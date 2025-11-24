package com.example.sleepingqueens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button gamebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gamebtn=findViewById(R.id.gamebtn);
        gamebtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==gamebtn)
        {
            Intent i=new Intent(this, GameActivity.class);
            startActivity(i);
        }
    }
}