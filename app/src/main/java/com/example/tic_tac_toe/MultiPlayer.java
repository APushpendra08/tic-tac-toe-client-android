package com.example.tic_tac_toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MultiPlayer extends AppCompatActivity {

    GridLayout gameBoard;
    ImageView one, two, three, four, five, six, seven, eight, nine;
    Button backBtn, rematchBtn;
    int[][] grid;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Toast.makeText(MultiPlayer.this, view.getId() + "", Toast.LENGTH_SHORT).show();
            view.setOnClickListener(null);
            if(chance % 2 == 0)
                ((ImageView)view).setImageResource(R.drawable.cross);
            else
                ((ImageView)view).setImageResource(R.drawable.circle);

            switch (view.getId()){
                case R.id.one: grid[0][0] = chance % 2;
                            break;
                case R.id.two: grid[0][1] = chance % 2;
                    break;
                case R.id.three: grid[0][2] = chance % 2;
                    break;
                case R.id.four: grid[1][0] = chance % 2;
                    break;
                case R.id.five: grid[1][1] = chance % 2;
                    break;
                case R.id.six: grid[1][2] = chance % 2;
                    break;
                case R.id.seven: grid[2][0] = chance % 2;
                    break;
                case R.id.eight: grid[2][1] = chance % 2;
                    break;
                case R.id.nine: grid[2][2] = chance % 2;
                    break;
            }

            int winner = winner();

            if(winner > 0) {
                Toast.makeText(MultiPlayer.this, "Winner is Player " + winner, Toast.LENGTH_SHORT).show();
                Log.d("Winner", winner + "");

                breakAllTouchs();
            } else if( winner == -1){
                Toast.makeText(MultiPlayer.this, "Draw", Toast.LENGTH_SHORT).show();

            }


            chance++;

            for(int i=0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j)
                    System.out.print(grid[i][j] + " ");
                System.out.println();
            }
        }
    };
    int chance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);
        backBtn = findViewById(R.id.back_btn);
        rematchBtn = findViewById(R.id.rematch_btn);

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

        rematchBtn.setOnClickListener(v ->{
            gameInit();
        });
        gameInit();
    }

    public  void gameInit(){
        grid = new int[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
        chance = 0;
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
        one.setImageResource(R.color.bg_gray);
        two.setImageResource(R.color.bg_gray);
        three.setImageResource(R.color.bg_gray);
        four.setImageResource(R.color.bg_gray);
        five.setImageResource(R.color.bg_gray);
        six.setImageResource(R.color.bg_gray);
        seven.setImageResource(R.color.bg_gray);
        eight.setImageResource(R.color.bg_gray);
        nine.setImageResource(R.color.bg_gray);

    }

    public int winner(){
        for(int i=0; i <3; ++i)
            if(grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][2] != -1)
                return grid[i][0] + 1;
            else if(grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[2][i] != -1)
                return grid[0][i] + 1;

            if(grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[2][2] != -1)
                return grid[0][0] + 1;

            if(grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2] && grid[1][1] != -1)
                return grid[1][1] + 1;

        for(int i=0; i<3; ++i)
            for(int j=0; j < 3; ++j)
                if(grid[i][j] == -1)
                    return 0;

        return -1;
    }

    private void breakAllTouchs() {
        one.setOnClickListener(null);
        two.setOnClickListener(null);
        three.setOnClickListener(null);
        four.setOnClickListener(null);
        five.setOnClickListener(null);
        six.setOnClickListener(null);
        seven.setOnClickListener(null);
        eight.setOnClickListener(null);
        nine.setOnClickListener(null);
    }

}