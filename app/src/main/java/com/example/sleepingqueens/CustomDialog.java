package com.example.sleepingqueens;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    private RadioGroup radioGroup;
    private RadioButton radioPlayer1,radioPlayer2;
    private Context context;
    public CustomDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.custom_dialog);
        this.context=context;
        radioGroup = findViewById(R.id.whichPlayer);
        radioPlayer1=findViewById(R.id.radioPlayer1);
        radioPlayer2=findViewById(R.id.radioPlayer2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                Intent i = new Intent(context, GameActivity.class);
                if (checkedId == radioPlayer1.getId()) {
                    i.putExtra("player",1);
                    context.startActivity(i);

                }
                if (checkedId == radioPlayer2.getId())  {
                    i.putExtra("player",2);
                    context.startActivity(i);

                }
            }
        });
    }
}