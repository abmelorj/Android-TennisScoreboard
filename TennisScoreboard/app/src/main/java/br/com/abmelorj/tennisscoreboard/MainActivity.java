package br.com.abmelorj.tennisscoreboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private boolean thereIsWinner;

    private int normalGame = 1;
    private int extendedGame = 2;
    private int tieBreak = 3;

    private int playerA = 0;
    private int playerB = 1;

    private int gameScore = 0;
    private int matchScore = 6;

    private int game;
    // 1 = Normal
    // 2 = Extended
    // 3 = TieBreak

    private int set;
    // 1 .. 5 Sets in a match

    // These defaults maybe changed by menu option
    private int setMatch = 3;
    private int maxSets = 5;

    private int[][] score;
    // D1 - 0 = PlayerA
    //      1 = PlayerB
    //
    // D2 - 0 = GameScore
    //      1..5 = SetScore
    //      6 = Amount of sets (winner)

    // Score Objects
    private TextView player_a_score;
    private TextView player_b_score;
    private TextView player_a_set1;
    private TextView player_a_set2;
    private TextView player_a_set3;
    private TextView player_a_set4;
    private TextView player_a_set5;
    private TextView player_b_set1;
    private TextView player_b_set2;
    private TextView player_b_set3;
    private TextView player_b_set4;
    private TextView player_b_set5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Associate TextView Objects
        assingObjects();
        // Reset Game
        starMatch();
    }

    private void assingObjects(){
        // Score Objects
        player_a_score = (TextView) findViewById(R.id.player_a_score);
        player_b_score = (TextView) findViewById(R.id.player_b_score);
        player_a_set1 = (TextView) findViewById(R.id.player_a_set_1);
        player_a_set2 = (TextView) findViewById(R.id.player_a_set_2);
        player_a_set3 = (TextView) findViewById(R.id.player_a_set_3);
        player_a_set4 = (TextView) findViewById(R.id.player_a_set_4);
        player_a_set5 = (TextView) findViewById(R.id.player_a_set_5);
        player_b_set1 = (TextView) findViewById(R.id.player_b_set_1);
        player_b_set2 = (TextView) findViewById(R.id.player_b_set_2);
        player_b_set3 = (TextView) findViewById(R.id.player_b_set_3);
        player_b_set4 = (TextView) findViewById(R.id.player_b_set_4);
        player_b_set5 = (TextView) findViewById(R.id.player_b_set_5);
    }

    private void starMatch() {
        thereIsWinner = false; // New Match
        set  = 1; // First Set
        game = normalGame; // Normal Game
        // ResetScore
        score = new int[][] {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
        displayScore();
    }

    private void displayScore() {

        // Normal Game
        if (game == normalGame && !thereIsWinner) {
            // Format PlayerA Score
            switch (score[playerA][gameScore]) {
                case 0:
                    player_a_score.setText("00");
                    break;
                case 1:
                    player_a_score.setText("15");
                    break;
                case 2:
                    player_a_score.setText("30");
                    break;
                case 3:
                    player_a_score.setText("40");
                    break;
                default:
                    player_a_score.setText(String.valueOf(score[playerA][gameScore]));
                    break;
            }
            // Format PlayerB Score
            switch (score[playerB][gameScore]) {
                case 0:
                    player_b_score.setText("00");
                    break;
                case 1:
                    player_b_score.setText("15");
                    break;
                case 2:
                    player_b_score.setText("30");
                    break;
                case 3:
                    player_b_score.setText("40");
                    break;
                default:
                    player_b_score.setText(String.valueOf(score[playerB][gameScore]));
                    break;
            }
        }

        // Extended Game
        if (game == extendedGame) {
            // Equal score...
            if (score[playerA][gameScore] == score[playerB][gameScore]) {
                player_a_score.setText("40  ");
                player_b_score.setText("40  ");
            } else if (score[playerA][gameScore] > score[playerB][gameScore]) {
                // Player A Advantage
                player_a_score.setText("40*");
                player_b_score.setText("40  ");
            } else {
                // Player B Advantage
                player_a_score.setText("40  ");
                player_b_score.setText("40*");
            }
        }

        // TieBreak
        if (game == tieBreak) {
            player_a_score.setText(String.valueOf(score[playerA][gameScore]));
            player_b_score.setText(String.valueOf(score[playerB][gameScore]));
        }

        // Fill current Set score
        if (set == 1) {
            player_a_set2.setText("");
            player_b_set2.setText("");
            player_a_set3.setText("");
            player_b_set3.setText("");
            player_a_set4.setText("");
            player_b_set4.setText("");
            player_a_set5.setText("");
            player_b_set5.setText("");
        }
        if (set >= 1) {
            player_a_set1.setText(String.valueOf(score[playerA][1]));
            player_b_set1.setText(String.valueOf(score[playerB][1]));
        }
        if (set >= 2) {
            player_a_set2.setText(String.valueOf(score[playerA][2]));
            player_b_set2.setText(String.valueOf(score[playerB][2]));
        }
        if (set >= 3) {
            player_a_set3.setText(String.valueOf(score[playerA][3]));
            player_b_set3.setText(String.valueOf(score[playerB][3]));
        }
        if (set >= 4) {
            player_a_set4.setText(String.valueOf(score[playerA][4]));
            player_b_set4.setText(String.valueOf(score[playerB][4]));
        }
        if (set >= 5) {
            player_a_set5.setText(String.valueOf(score[playerA][5]));
            player_b_set5.setText(String.valueOf(score[playerB][5]));
        }
    }

    private void evaluateMatch() {
        //Player A won the Match?
        if (score[playerA][matchScore] == setMatch) {
            thereIsWinner = true;
            player_a_score.setText("winner");
            player_b_score.setText("");
        }
        //Player B won the Match?
        if (score[playerB][matchScore] == setMatch) {
            thereIsWinner = true;
            player_a_score.setText("");
            player_b_score.setText("winner");
        }
    }

    private void setWinner(int player) {
        // Player won a Set
        score[player][matchScore] += 1;
        evaluateMatch();
        // Next Set
        if (!thereIsWinner) {
            set += 1;
        }
    }

    private void evaluateSet(int player, int otherPlayer) {
        // Check if it became a TieBreak
        // Its the last Set and the Set score is 6 x 6?
        if (set == maxSets && score[player][set] == 6 && score[otherPlayer][set] == 6) {
            // Its TieBreak Time
            game = tieBreak;
        }
        // Player won a Set?
        if (score[player][set] == 7 ||
                (score[player][set] == 6 &&
                        (score[player][set] - score[otherPlayer][set]) > 1)) {
            setWinner(player);
        }
    }

    private void addSet(int player, int otherPlayer) {
        score[player][set] += 1;
        // Reset Game Score
        score[player][gameScore] = 0;
        score[otherPlayer][gameScore] = 0;
        // Reset to Normal Game
        game = normalGame;
        evaluateSet(player, otherPlayer);
    }

    private void evaluateGame(int player, int otherPlayer) {

        // Normal Game
        if (game == normalGame) {
            // Player won a game?
            if (score[player][gameScore] > 3 &&
                    (score[player][gameScore] - score[otherPlayer][gameScore]) > 1) {
                addSet(player, otherPlayer);
            }
            // Extend the game? 40 x 40
            if (score[player][gameScore] == 3 && score[otherPlayer][gameScore] == 3) {
                // Turn it in a Extended Game
                game = extendedGame;
            }
        }
        // Extended Game
        if (game == extendedGame) {
            // Player won a game?
            if ((score[player][gameScore] - score[otherPlayer][gameScore]) > 1) {
                addSet(player, otherPlayer);
            }
        }

        // TieBreak
        if (game == tieBreak) {
            // Player won the TieBreak?
            if (score[player][gameScore] >= 7 &&
                    (score[player][gameScore] - score[otherPlayer][gameScore]) > 1) {
                addSet(player, otherPlayer);
            }
        }
        displayScore();
    }

    public void addPointPlayerA(View v) {
        if (!thereIsWinner) {
            score[playerA][gameScore] += 1;
            evaluateGame(playerA, playerB);
        }
    }

    public void addPointPlayerB(View v) {
        if (!thereIsWinner) {
            score[playerB][gameScore] += 1;
            evaluateGame(playerB, playerA);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_reset) {
            starMatch();
        }
        return true;
    }
}
