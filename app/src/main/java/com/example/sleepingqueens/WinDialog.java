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
    private Button btnYes,btnNo;
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
        btnYes=findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo=findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
        et = findViewById(R.id.et);
        if(player==win)
            et.setText("ניצחת!!");
        else
            et.setText("הפסדת.. לא נורא תנסה שוב!");

    }

    @Override
    public void onClick(View view) {

        if (view==btnYes)
        {

            /*Intent i = new Intent(context, GameActivity.class);
            i.putExtra("player",player);
            context.startActivity(i);*/
            /*CustomDialog customDialog=new CustomDialog(context);
            customDialog.show();*/
            //להוסיף שהם מגיעים לBOARDGAME כמו בפעם הראשונה
            //אוליי לשיםב-PUTEXTRA ערך כמו
            // .3 שאומר שהם התחילו את המשחק שוב אבל הם שחקן 1 ואותו דבר ל2
            //לצייר מחדש את החבילות? למחוק מה שהיה לפני? לזמן לא משנה מי מהם את הזה של המשחק מחדש?
            //להשתמש ב- NEWGAME מה-GAMEMODULE
        }
        if (view==btnNo)
        {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);

        }

    }
}
