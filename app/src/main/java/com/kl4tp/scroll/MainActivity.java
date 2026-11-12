

package com.kl4tp.scroll;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LinearLayout startScreenLayout, gameOverLayout;
    private androidx.constraintlayout.widget.ConstraintLayout gameplayLayout;
    private TextView tvScore, tvCombo, tvTimer, tvStartHighScore, tvFinalScore, tvNewRecord;
    private Button btnStartGame, btnPlayAgain;
    
    private final Button[] cells = new Button[9];
    
    private int score = 0;
    private int combo = 1;
    private int highScore = 0;
    private int activeCellIndex = -1;
    
    private CountDownTimer gameTimer;
    private android.os.Handler targetHandler;
    private Runnable targetRunnable;
    private boolean isPlaying = false;
    private Random random;
    
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "CyberTapPrefs";
    private static final String KEY_HIGH_SCORE = "high_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        loadHighScore();
        setupListeners();
    }

    private void initViews() {
        startScreenLayout = findViewById(R.id.startScreenLayout);
        gameplayLayout = findViewById(R.id.gameplayLayout);
        gameOverLayout = findViewById(R.id.gameOverLayout);

        tvScore = findViewById(R.id.tvScore);
        tvCombo = findViewById(R.id.tvCombo);
        tvTimer = findViewById(R.id.tvTimer);
        tvStartHighScore = findViewById(R.id.tvStartHighScore);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvNewRecord = findViewById(R.id.tvNewRecord);

        btnStartGame = findViewById(R.id.btnStartGame);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        int[] cellIds = {
                R.id.cell_0, R.id.cell_1, R.id.cell_2,
                R.id.cell_3, R.id.cell_4, R.id.cell_5,
                R.id.cell_6, R.id.cell_7, R.id.cell_8
        };

        for (int i = 0; i < 9; i++) {
            final int index = i;
            cells[i] = findViewById(cellIds[i]);
            cells[i].setOnClickListener(v -> onCellClicked(index));
        }

        random = new Random();
        targetHandler = new android.os.Handler();
    }

    private void loadHighScore() {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt(KEY_HIGH_SCORE, 0);
        tvStartHighScore.setText("Rekord: " + highScore);
    }

    private void saveHighScore(int newScore) {
        if (newScore > highScore) {
            highScore = newScore;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_HIGH_SCORE, highScore);
            editor.apply();
            tvNewRecord.setVisibility(View.VISIBLE);
        } else {
            tvNewRecord.setVisibility(View.GONE);
        }
    }

    private void setupListeners() {
        btnStartGame.setOnClickListener(v -> startGame());
        btnPlayAgain.setOnClickListener(v -> startGame());
    }

    private void startGame() {
        startScreenLayout.setVisibility(View.GONE);
        gameOverLayout.setVisibility(View.GONE);
        gameplayLayout.setVisibility(View.VISIBLE);

        score = 0;
        combo = 1;
        isPlaying = true;

        updateHud();
        resetAllCells();

        // 30 seconds game timer
        gameTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Czas: " + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                endGame();
            }
        }.start();

        startTargetSpawner();
    }

    private void startTargetSpawner() {
        targetRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPlaying) return;

                resetAllCells();
                activeCellIndex = random.nextInt(9);
                
                // Light up active cell with neon pink/cyan
                cells[activeCellIndex].setBackgroundTintList(
                        ContextCompat.getColorStateList(MainActivity.this, R.color.cyber_pink)
                );
                cells[activeCellIndex].setText("TAP!");
                cells[activeCellIndex].setTextColor(getResources().getColor(R.color.white));

                // Speed up target rotation as score increases
                long delay = Math.max(400, 850 - (score * 15));
                targetHandler.postDelayed(this, delay);
            }
        };
        targetHandler.post(targetRunnable);
    }

    private void onCellClicked(int index) {
        if (!isPlaying) return;

        if (index == activeCellIndex) {
            // Correct tap
            score += 10 * combo;
            combo = Math.min(10, combo + 1);
            resetAllCells();
            activeCellIndex = -1;
        } else {
            // Wrong tap / miss penalty
            combo = 1;
            score = Math.max(0, score - 5);
        }
        updateHud();
    }

    private void resetAllCells() {
        for (Button cell : cells) {
            cell.setBackgroundTintList(
                    ContextCompat.getColorStateList(MainActivity.this, R.color.cyber_surface)
            );
            cell.setText("");
        }
    }

    private void updateHud() {
        tvScore.setText("Wynik: " + score);
        tvCombo.setText("COMBO x" + combo);
    }

    private void endGame() {
        isPlaying = false;
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        targetHandler.removeCallbacks(targetRunnable);
        resetAllCells();

        saveHighScore(score);

        gameplayLayout.setVisibility(View.GONE);
        gameOverLayout.setVisibility(View.VISIBLE);
        tvFinalScore.setText("Twój wynik: " + score);
        tvStartHighScore.setText("Rekord: " + highScore);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        if (targetHandler != null && targetRunnable != null) {
            targetHandler.removeCallbacks(targetRunnable);
        }
    }
}