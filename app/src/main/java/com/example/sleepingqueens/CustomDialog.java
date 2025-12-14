package com.example.sleepingqueens;

import android.app.Dialog;
import android.content.Context;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    private RadioGroup radioGroup;
    private int id;
    private Context context;
    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.custom_dialog);
        radioGroup = findViewById(R.id.whichPlayer);
        radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);
    }
    private void OnCheckedChangeListener(){
        id=radioGroup.getCheckedRadioButtonId();
        if (id==R.id.radioPlayer1){
            dismiss();
            ((MainActivity)context).player1();
        }
        else {
            dismiss();
            ((MainActivity)context).player2();
        }
    }

}
