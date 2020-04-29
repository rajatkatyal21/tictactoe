package com.fastretailing.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //cross : 1, zero: 0, empty : 2
    int activePlayer = 0;
    boolean gameActive = true;
    int boardSpaces[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int winningPositions[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    String winner;
    MediaPlayer mediaPlayer= null;

    public void dropIn(View view) {
        mediaPlayer = MediaPlayer.create(this, R.raw.applause);
        ImageView imageView = (ImageView) view;
        int tappedCounter = Integer.parseInt(imageView.getTag().toString());
        if (boardSpaces[tappedCounter] != 2 || !gameActive) {
            return;
        }
        Log.i("Info", "tappedCounter : " + tappedCounter);

        if (boardSpaces[tappedCounter] != 2) {
            return;
        } else {
            boardSpaces[tappedCounter] = activePlayer;
        }

        imageView.setTranslationY(-1500);

        if (activePlayer == 0) {
            activePlayer = 1;
            imageView.setImageResource(R.drawable.zero1);
            imageView.animate().translationYBy(1500).setDuration(300);
        } else {
            activePlayer = 0;
            imageView.setImageResource(R.drawable.cross);
            imageView.animate().translationYBy(1500).setDuration(300);
        }

        for (int[] winningPosition : winningPositions) {
            if (boardSpaces[winningPosition[0]] == boardSpaces[winningPosition[1]] && boardSpaces[winningPosition[1]] == boardSpaces[winningPosition[2]] && boardSpaces[winningPosition[0]] != 2) {
                if (activePlayer == 1) {
                    winner = "Zero";
                } else {
                    winner = "Cross";
                }

                TextView textView = (TextView) findViewById(R.id.winnerText);
                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                textView.setText(winner + " won!! Congratulations");
                textView.setVisibility(view.VISIBLE);
                playAgainButton.setVisibility(view.VISIBLE);
                gameActive = false;
                mediaPlayer.start();
            }
        }

        int count = 0;
        for (int i = 0; i < boardSpaces.length; i++) {
            if(gameActive && boardSpaces[i] != 2) {
                count++;
            }
        }
        if (count == 9) {
            winner = "Game Drawn!! Better luck next time";
            TextView textView = (TextView) findViewById(R.id.winnerText);
            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
            textView.setText(winner);
            textView.setVisibility(view.VISIBLE);
            playAgainButton.setVisibility(view.VISIBLE);
            gameActive = false;
        }


    }

    public void playAgain(View view) {
        TextView textView = (TextView) findViewById(R.id.winnerText);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        textView.setVisibility(view.INVISIBLE);
        playAgainButton.setVisibility(view.INVISIBLE);
        gameActive = true;
        mediaPlayer.stop();
        winner = "";
        for (int i = 0; i < boardSpaces.length; i++) {
            boardSpaces[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}


