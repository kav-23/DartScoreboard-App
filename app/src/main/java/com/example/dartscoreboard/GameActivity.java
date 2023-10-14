package com.example.dartscoreboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private TextView gameTitle;
    private ArrayList<User> playersList;
    private RecyclerView recyclerView;
    private EditText inputScoreEditText;

    private SelectGameActivity.GameType gameType;
    private int playerStartingScore;
    private RecyclerAdapterGamePlayers adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("dom test", "GameActivityOnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        Log.d("dom test", "gameType\n-------\nname " + getGameType().name + "\nstartingScore " + getGameType().startingScore);
        setupUI();
        onScoreEntered();
    }

    private void setupUI() {
        recyclerView = findViewById(R.id.player_info_recycler_view);
        gameTitle = findViewById(R.id.gameActivityTitle);
        inputScoreEditText = findViewById(R.id.inputUserNameEditText);
        playersList = PrefConfig.readUsersForGameSP(this);
        gameTitle.setText(getGameType().name);
        setPlayerStartingScores();
        setPlayerTurns();
        setAdapter();
    }

    private SelectGameActivity.GameType getGameType() {
        if (gameType != null) {
            return gameType;
        }
        Bundle arguments = getIntent().getExtras();
        gameType = (SelectGameActivity.GameType) arguments.getSerializable(SelectGameActivity.GAME_TYPE_KEY);
        return gameType;
    }

    private void setAdapter(){
        adapter = new RecyclerAdapterGamePlayers(playersList,playerStartingScore);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setPlayerStartingScores(){
        playerStartingScore = getGameType().startingScore;
        for (int i = 0; i < playersList.size(); i++){
            playersList.get(i).setPlayerScore(playerStartingScore);
        }
    }

    private void setPlayerTurns(){
        playersList.get(0).setTurn(true);
        for (int i = 1; i < playersList.size(); i++){
            playersList.get(i).setTurn(false);
        }
    }

    private void onScoreEntered() {
        inputScoreEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("dom test", "IME_ACTION_DONE");
                playerVisit(inputScoreEditText.getText().toString());
                ((EditText) findViewById(R.id.inputUserNameEditText)).getText().clear();
                return true;
            }
            return false;
        });
    }

    private void playerVisit(String scoreString){
        int scoreInt = Integer.parseInt(scoreString);
        if (scoreInt <= 180) { // checks for valid score input
            for (int i = 0; i < playersList.size(); i++) {
                int currentScore = playersList.get(i).getPlayerScore();
                if (playersList.get(i).isTurn()) {
                    playersList.get(i).setPlayerScore(subtract(currentScore, scoreInt));
                    playersList.get(i).setTurn(false);
                    adapter.notifyItemChanged(i);
                    Log.d("dom test", playersList.get(i).getUsername() + " " + scoreString);
                    Log.d("dom test", playersList.get(i).getUsername() + " " + playersList.get(i).getPlayerScore());
                    if (i + 1 < playersList.size()) {
                        playersList.get(i + 1).setTurn(true);
                        adapter.notifyItemChanged(i+1);
                        break;
                    } else if (i + 1 == playersList.size()) {
                        i = 0;
                        playersList.get(i).setTurn(true);
                        adapter.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    }


    public int subtract(int playerScore, int currentTypedScore) {
        int newScore = playerScore - currentTypedScore;
        if (( ((playerScore <= 180) && (playerScore >= 171)) || (playerScore == 169) || (playerScore == 168) || (playerScore == 166) || (playerScore == 165) || (playerScore == 163) || (playerScore == 162) || (playerScore == 159)) && (currentTypedScore == playerScore)){
            Toast.makeText(GameActivity.this, "Invalid Score", Toast.LENGTH_SHORT).show();
            return playerScore;
        }

        if (currentTypedScore > 180) {
            Toast.makeText(GameActivity.this, "Invalid Score", Toast.LENGTH_SHORT).show();
            return playerScore;
        }

        if (newScore > 1) {
            return newScore;
        }
        if (newScore == 0) {
            for (int i = 0; i < playersList.size(); i++) {
                if (playersList.get(i).turn) {
                Toast.makeText(GameActivity.this, playersList.get(i).getUsername() + " wins!", Toast.LENGTH_LONG).show();
                }
            }
            return newScore;
        }
        else Toast.makeText(GameActivity.this, "BUST", Toast.LENGTH_SHORT).show();
        return playerScore;
        }
}
