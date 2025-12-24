package com.example.sleepingqueens;

import android.app.Dialog;
import android.content.Context;
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
                if (checkedId == radioPlayer1.getId()) {
                    ((MainActivity) context).whichpPlayer(1);
                    dismiss();
                }
                if (checkedId == radioPlayer2.getId())  {
                    ((MainActivity) context).whichpPlayer(2);
                    dismiss();
                }
            }
        });
    }
}