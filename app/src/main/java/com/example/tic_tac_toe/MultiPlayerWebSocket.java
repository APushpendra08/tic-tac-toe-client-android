package com.example.tic_tac_toe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MultiPlayerWebSocket extends AppCompatActivity {

    GridLayout gameBoard;
    ImageView one, two, three, four, five, six, seven, eight, nine;
    Button backBtn, rematchBtn;
    int[][] grid;
    int chance;
    OkHttpClient okHttpClient = null;
    WebSocket webSocket = null;
    private String SERVER_HOST = "ws://192.168.0.109";
    private String SERVER_PORT = "3000";
    private String GAME_ID;
    private int PlayerId = -1;
    private int BothOnline = 0;
    private int GameRunning = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        setViews();
        chance = 0;
        initiateSocketConnection();
        setButtons();
        grid = new int[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};

//        Button btn = findViewById(R.id.rematch_btn);
//        btn.setOnClickListener(v -> {
//            Toast.makeText(this, PlayerId + "" , Toast.LENGTH_SHORT).show();
//        });
    }

    private void setButtons() {
        backBtn = findViewById(R.id.back_btn);
        rematchBtn = findViewById(R.id.rematch_btn);
        rematchBtn.setText("Rematch");
        rematchBtn.setOnClickListener(v ->{
            webSocket.send("rematch");
        });

        backBtn.setOnClickListener(v -> {
            if(GameRunning == 1) {
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
            } else {
                finish();
                webSocket.close(1000, "Going Back");
            }
        });
    }

    private void initiateSocketConnection() {
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(SERVER_HOST + ":" + SERVER_PORT)
                .build();
        webSocket = okHttpClient.newWebSocket(request, new SocketListener());
    }

    private class SocketListener extends WebSocketListener{
        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            String[] textParts = text.split(",");
            if(textParts[0].equals("PlayerId")) {
                PlayerId = Integer.parseInt(textParts[1]);
                runOnUiThread(() -> {
                    Toast.makeText(MultiPlayerWebSocket.this, textParts[1], Toast.LENGTH_SHORT).show();
                });
            } else if(textParts[0].equals("BothReady")) {
                GameRunning = 1;
            } else if(textParts[0].equals("rematch")){
                Log.d("Rematch", "Rematch");
                runOnUiThread(()->{
                    setViews();
                    grid = new int[][]{{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};
                    GameRunning = 1;
                });
            } else {
                Log.d("Rematch", "Rematch");
                GameRunning = 1;
                String x = textParts[0], y = textParts[1], whoMadeMove = textParts[2], whosNext = textParts[3];

                runOnUiThread(() -> {
                    switch (x) {
                        case "0":
                            switch (y) {
                                case "0":
                                    setView(one);
                                    break;
                                case "1":
                                    setView(two);
//                                    two.setOnClickListener(null);
                                    break;
                                case "2":
                                    setView(three);
//                                    three.setOnClickListener(null);
                                    break;
                            }
                            break;
                        case "1":
                            switch (y) {
                                case "0":
                                    setView(four);
//                                    four.setOnClickListener(null);
                                    break;
                                case "1":
                                    setView(five);
//                                    five.setOnClickListener(null);
                                    break;
                                case "2":
                                    setView(six);
//                                    six.setOnClickListener(null);
                                    break;
                            }
                            break;
                        case "2":
                            switch (y) {
                                case "0":
                                    setView(seven);
//                                    seven.setOnClickListener(null);
                                    break;
                                case "1":
                                    setView(eight);
//                                    eight.setOnClickListener(null);
                                    break;
                                case "2":
                                    setView(nine);
//                                    nine.setOnClickListener(null);
                                    break;
                            }
                            break;
                    }
                    setValue(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(whoMadeMove), Integer.parseInt(whosNext));
                    Toast.makeText(MultiPlayerWebSocket.this, whoMadeMove + " : " + whosNext, Toast.LENGTH_SHORT).show();

                    int winner = MultiPlayer.winner(grid);

                    if(winner > 0) {
                        Toast.makeText(MultiPlayerWebSocket.this, "Winner is Player " + winner, Toast.LENGTH_SHORT).show();
                        Log.d("Winner", winner + "");
                        GameRunning = 0;
                        breakAllTouchs();
                    } else if( winner == -1){
                        Toast.makeText(MultiPlayerWebSocket.this, "Draw", Toast.LENGTH_SHORT).show();
                        GameRunning = 0;
                    }
                });
            }
        }



        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
//            super.onOpen(webSocket, response);
            webSocket.send("isSecondConnect");
            runOnUiThread(()->{
                Toast.makeText(MultiPlayerWebSocket.this, "Socket Connected", Toast.LENGTH_SHORT).show();
//                setViews();
            });
        }

        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            Log.d("ABC", response + "t" + t);
        }
    }

    private void setView(ImageView view) {
        if(chance % 2 == 0)
            ((ImageView)view).setImageResource(R.drawable.cross);
        else
            ((ImageView)view).setImageResource(R.drawable.circle);

        view.setOnClickListener(null);
    }

    private void setValue(int x, int y, int whoMade, int whosNext) {
        grid[x][y] = whoMade;
        chance = whosNext;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(chance != PlayerId || GameRunning == 0)
                return;

            switch (view.getId()){
                case R.id.one: webSocket.send("0,0," + chance + "," +(chance+1)%2);
                    break;
                case R.id.two: webSocket.send("0,1," + chance + "," +(chance+1)%2);
                    break;
                case R.id.three: webSocket.send("0,2," + chance + "," +(chance+1)%2);
                    break;
                case R.id.four: webSocket.send("1,0," + chance + "," +(chance+1)%2);
                    break;
                case R.id.five: webSocket.send("1,1," + chance + "," +(chance+1)%2);
                    break;
                case R.id.six: webSocket.send("1,2," + chance + "," +(chance+1)%2);
                    break;
                case R.id.seven: webSocket.send("2,0," + chance + "," +(chance+1)%2);
                    break;
                case R.id.eight: webSocket.send("2,1," + chance + "," +(chance+1)%2);
                    break;
                case R.id.nine: webSocket.send("2,2," + chance + "," +(chance+1)%2);
                    break;
            }
        }
    };

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webSocket.close(1000, "Closing Connection for Player " + PlayerId  );
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
}