package com.example.tic_tac_toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class SinglePlayer extends AppCompatActivity {

    GridLayout gameBoard;
    ImageView one, two, three, four, five, six, seven, eight, nine;
    Button backBtn, drawBtn;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(SinglePlayer.this, view.getId() + "", Toast.LENGTH_SHORT).show();
            view.setOnClickListener(null);
            ((ImageView)view).setImageResource(R.color.dark_red);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        setViews();
    }

    private void setViews() {

        gameBoard = findViewById(R.id.game_multi_board);

        one = findViewById(R.id.one);
        one.setOnClickListener(listener);
        two = findViewById(R.id.two);
        two.setOnClickListener(listener);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        three.setOnClickListener(listener);
        four.setOnClickListener(listener);
        five.setOnClickListener(listener);
        six.setOnClickListener(listener);
        seven.setOnClickListener(listener);
        eight.setOnClickListener(listener);
        nine.setOnClickListener(listener);

        backBtn = findViewById(R.id.back_btn);
        drawBtn = findViewById(R.id.draw_btn);

        backBtn.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Quit Match");
            dialog.setMessage("Are you sure to quit the match?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            dialog.show();
        });
    }
}