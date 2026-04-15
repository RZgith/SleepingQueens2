package com.example.sleepingqueens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class WinDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int player,win;
    private TextView et;
    private Button btnOK;

    public WinDialog(@NonNull Context context,int player,int win) {
        super(context);
        this.context=context;
        this.player=player;
        this.win=win;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //חיבור הXML לCLASS
        setContentView(R.layout.win_dialog_game);
        btnOK=findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        et = findViewById(R.id.et);
        if(player==win)
            et.setText("ניצחת!! ");
        else
            et.setText("הפסדת.. לא נורא תנסה שוב!");

    }

    @Override
    public void onClick(View view) {
        if (view==btnOK)
        {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);

        }

    }
}
