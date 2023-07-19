package com.example.dartscoreboard;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {


    private TextView gameTitle;
    private TextView playerName;
    private TextView playerNameTwo;
    private TextView playerCurrentScore;
    private TextView playerCurrentScoreTwo;
    private EditText inputScoreEditText;
    private Player testPlayer = new Player("test 1", 0, true);
    private Player testPlayer2 = new Player("test 2",0,false);

    private String playerNameKey;


    private HomeActivity.GameType gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("dom test", "GameActivityonCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        Log.d("dom test", "gameType\n-------\nname " + getGameType().name + "\nstartingScore " + getGameType().startingScore);
        setupUI();

    }

    private void setupUI() {
        gameTitle = findViewById(R.id.gameActivityTitle);
        playerName = findViewById(R.id.gameActivityPlayerOneName);
        playerCurrentScore = findViewById(R.id.gameActivityPlayerCurrentScore);
        playerNameTwo = findViewById(R.id.gameActivityPlayerTwoName);
        playerCurrentScoreTwo = findViewById(R.id.gameActivityPlayerTwoCurrentScore);
        inputScoreEditText = findViewById(R.id.inputScoreEditText);

        playerName.setText(testPlayer.name);
        playerNameTwo.setText(testPlayer2.name);


        gameTitle.setText(getGameType().name);
      //  playerName.setText(setPlayerOneName()); todo move this so that the information for player names is added on the home activity.

        testPlayer.currentScore = getGameType().startingScore;// todo this may be called again
        testPlayer2.currentScore = getGameType().startingScore;
        playerCurrentScore.setText(valueOf(testPlayer.currentScore));
        playerCurrentScoreTwo.setText(valueOf(testPlayer2.currentScore));

            if (testPlayer.playerTurn) {
                testPlayer.turn();
            }
            else {
                testPlayer2.turn();
            }
    }
    private HomeActivity.GameType getGameType() {
        if (gameType != null) {
            return gameType;
        }

        Bundle arguments = getIntent().getExtras();
        gameType = (HomeActivity.GameType) arguments.getSerializable(HomeActivity.GAME_TYPE_KEY);
        return gameType;
    }

//    private void onScoreEntered(String scoreString) {
//        try {
//           int scoreInt = Integer.parseInt(scoreString);
//            Log.d("dom test", Integer.toString(scoreInt));
//          //  scoreInt = currentTypedScore;
//            testPlayer.currentScore = subtract(testPlayer.currentScore, scoreInt);
//            playerCurrentScore.setText(String.valueOf(testPlayer.currentScore));
//            Log.d("dom test","Current Score: " + testPlayer.currentScore);
//
//        } catch (NumberFormatException e) {
//            Log.d("dom test", e.getMessage());
//        }
//    }

    private void onScoreEntered(String scoreString) {
        try {
            if (testPlayer.playerTurn) {

                int scoreInt = Integer.parseInt(scoreString);
                Log.d("dom test", Integer.toString(scoreInt));
                testPlayer.currentScore = subtract(testPlayer.currentScore, scoreInt);
                Log.d("dom test","score subtract run");
                playerCurrentScore.setText(String.valueOf(testPlayer.currentScore));
                Log.d("dom test","Current Score: " + testPlayer.currentScore);
            }

            else {
                int scoreInt = Integer.parseInt(scoreString);
                Log.d("dom test", Integer.toString(scoreInt));
                testPlayer2.currentScore = subtract(testPlayer2.currentScore, scoreInt);
                playerCurrentScoreTwo.setText(String.valueOf(testPlayer2.currentScore));
                Log.d("dom test","Current Score: " + testPlayer2.currentScore);
            }


        } catch (NumberFormatException e) {
            Log.d("dom test", e.getMessage());
        }
    }



    public int subtract(int playerScore, int currentTypedScore) {
        int newScore = playerScore - currentTypedScore;
        if (( ((playerScore <= 180) && (playerScore >= 171)) || (playerScore == 169) || (playerScore == 168) || (playerScore == 166) || (playerScore == 165) || (playerScore == 163) || (playerScore == 162) || (playerScore == 159)) && (currentTypedScore == playerScore)){
            if (testPlayer.playerTurn) {
                playerName.setBackgroundColor(Color.GREEN);
                playerNameTwo.setBackgroundColor(Color.WHITE);
                testPlayer.playerTurn = true;
                testPlayer2.playerTurn = false;

            }
            else {
                playerNameTwo.setBackgroundColor(Color.GREEN);
                playerName.setBackgroundColor(Color.WHITE);
                testPlayer.playerTurn = false;
                testPlayer2.playerTurn = true;

            }
            Toast.makeText(GameActivity.this, "Invalid Score", Toast.LENGTH_SHORT).show();
            return playerScore;
        }

        if (currentTypedScore > 180) {
            if (testPlayer.playerTurn) {
                playerName.setBackgroundColor(Color.GREEN);
                playerNameTwo.setBackgroundColor(Color.WHITE);
                testPlayer.playerTurn = true;
                testPlayer2.playerTurn = false;
            }
            else {
                playerNameTwo.setBackgroundColor(Color.GREEN);
                playerName.setBackgroundColor(Color.WHITE);
                testPlayer.playerTurn = false;
                testPlayer2.playerTurn = true;
            }
            Toast.makeText(GameActivity.this, "Invalid Score", Toast.LENGTH_SHORT).show();
            return playerScore;
        }

        if (newScore > 1) {
            if (testPlayer.playerTurn) {
                playerNameTwo.setBackgroundColor(Color.GREEN);
                playerName.setBackgroundColor(Color.WHITE);
                testPlayer.playerTurn = false;
                testPlayer2.playerTurn = true;
            } else {
                playerName.setBackgroundColor(Color.GREEN);
                playerNameTwo.setBackgroundColor(Color.WHITE);
                testPlayer2.playerTurn = false;
                testPlayer.playerTurn = true;
            }
            return newScore;
        }
        if (newScore == 0) {
            if (testPlayer.playerTurn) {
                playerName.setBackgroundColor(Color.GREEN);
                playerNameTwo.setBackgroundColor(Color.WHITE);
                Toast.makeText(GameActivity.this, testPlayer.name + "wins!", Toast.LENGTH_LONG).show();
            } else {
                playerNameTwo.setBackgroundColor(Color.GREEN);
                playerName.setBackgroundColor(Color.WHITE);
                Toast.makeText(GameActivity.this, testPlayer2.name + "wins!", Toast.LENGTH_LONG).show();
            }
            return newScore;
        }
        else {
            if (testPlayer.playerTurn) {
                testPlayer.playerTurn = false;
                testPlayer2.playerTurn = true;
            }
            else {
                testPlayer.playerTurn = true;
                testPlayer2.playerTurn = false;
            }
            Toast.makeText(GameActivity.this, "BUST", Toast.LENGTH_SHORT).show(); // toast

        return playerScore;
        }

    }

//    private String setPlayerOneName(){
//        Intent intent = getIntent();
//        playerNameKey = intent.getStringExtra("send_name_one");
//        return playerNameKey;
//
//    }


    class Player {
        String name;
        int currentScore;
        boolean playerTurn;


        Player(String name, int currentScore, boolean playerTurn) {
            this.name = name;
            this.currentScore = currentScore;
            this.playerTurn = playerTurn;
        }

        private void turn(){


            //determines score for processing
                inputScoreEditText.setOnEditorActionListener((v, actionId, event) -> {
                    if (testPlayer.playerTurn) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Log.d("dom test", "IME_ACTION_DONE");
                            onScoreEntered(inputScoreEditText.getText().toString());

//                            testPlayer.playerTurn = false; //todo create UI to indicate player turn, if score is invalid, don't do this line
//                            testPlayer2.playerTurn = true;
                            ((EditText) findViewById(R.id.inputScoreEditText)).getText().clear();
                            return true;
                        }

                    } else {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Log.d("dom test", "IME_ACTION_DONE");
                            onScoreEntered(inputScoreEditText.getText().toString());
//                            testPlayer2.playerTurn = false;
//                            testPlayer.playerTurn = true;
                            ((EditText) findViewById(R.id.inputScoreEditText)).getText().clear();
                            return true;
                        }

                    }
                    return false;
                });

        }


        private void printOutDetails() {
            Log.d("dom test", "Name: " + name);
        }

    }

}

