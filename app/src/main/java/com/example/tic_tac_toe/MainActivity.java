package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button singlePlayerBtn, multiPlayerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singlePlayerBtn = findViewById(R.id.single_player_btn);
        multiPlayerBtn = findViewById(R.id.multi_player_btn);

        singlePlayerBtn.setOnClickListener( view -> {
            Toast.makeText(this, "Single Player It Is..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SinglePlayer.class));
        } );

        multiPlayerBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Multi-Player It Is..", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MultiPlayer.class));
        });
    }
}