package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int[][] WIN_COMBOS = { { 0, 1, 2 }, { 3, 4, 5 },
                                         { 6, 7, 8 }, { 0, 4, 8 },
                                         { 2, 4, 6 }, { 0, 3, 6 },
                                         { 1, 4, 7 }, { 2, 5, 8 } };
    private boolean isPlayerXTurn = true;
    private boolean isGameWon = false;
    private boolean isGameFilled = false;

    private TextView statusText;
    private Button[] buttonBoard;
    private Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* This technically allows the icon to appear,
           but it doesn't really look right.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setIcon(R.drawable.icon);
        actionBar.setDisplayShowHomeEnabled(true);
        */

        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.txt_status);
        buttonBoard = new Button[] { findViewById(R.id.btn_slot_topLeft),
                                     findViewById(R.id.btn_slot_topCenter),
                                     findViewById(R.id.btn_slot_topRight),
                                     findViewById(R.id.btn_slot_middleLeft),
                                     findViewById(R.id.btn_slot_middleCenter),
                                     findViewById(R.id.btn_slot_middleRight),
                                     findViewById(R.id.btn_slot_bottomLeft),
                                     findViewById(R.id.btn_slot_bottomCenter),
                                     findViewById(R.id.btn_slot_bottomRight) };
        newGameButton = findViewById(R.id.btn_newGame);

        newGameButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v){
                        newGame();
                    }
                }
        );
        for ( final Button button : buttonBoard ) {
            button.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View v){
                            changeButtonSymbol(button);
                        }
                    }
            );
        }
    }

    private void changeButtonSymbol(Button selectedBtn){
        if( selectedBtn.getText().equals("") ) {
            selectedBtn.setText( isPlayerXTurn ? "X" : "O" );
            updateGameOverValues();
            if(isGameWon || isGameFilled) {
                DisableButtons();
            }
            else{
                isPlayerXTurn = !isPlayerXTurn;
            }
            changeStatusText();
        }
    }

    private void DisableButtons() {
        for ( Button button : buttonBoard ) {
            button.setEnabled(false);
        }
    }

    private void updateGameOverValues() {
        String playerSymbol = isPlayerXTurn ? "X" : "O";
        byte numButtonsFilled = 1;
        for ( int i = 0; i < 8; i++ ) {
            if( buttonBoard[WIN_COMBOS[i][0]].getText() == playerSymbol &&
                buttonBoard[WIN_COMBOS[i][1]].getText() == playerSymbol &&
                buttonBoard[WIN_COMBOS[i][2]].getText() == playerSymbol ) {
                isGameWon = true;
                return;
            }
            if( buttonBoard[i].getText() != "" ){
                numButtonsFilled++;
            }
        }
        isGameFilled = numButtonsFilled == 9;
    }

    private void changeStatusText(){
        String StatusMessage;
        if(isGameFilled){
            StatusMessage = "It's a tie!";
        }
        else if(isGameWon){
            StatusMessage = isPlayerXTurn ? "X wins!" : "O wins!";
        }
        else {
            StatusMessage = isPlayerXTurn ? "Player X's turn" : "Player O's turn";
        }
        statusText.setText( StatusMessage );
    }

    private void newGame(){
        isPlayerXTurn = true;
        isGameWon = false;
        isGameFilled = false;
        for ( Button button : buttonBoard ) {
            button.setText("");
            button.setEnabled(true);
        }
        changeStatusText();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean( "isPlayerXTurn", isPlayerXTurn );
        outState.putBoolean( "isGameOver", isGameWon);
        outState.putBoolean( "isGameFilled", isGameFilled);

        CharSequence[] buttonBoardValues = new String[ buttonBoard.length ];
        for ( int i = 0; i < buttonBoard.length; i++ ) {
            buttonBoardValues[i] = buttonBoard[i].getText();
        };

        outState.putCharSequenceArray( "buttonBoardValues", buttonBoardValues );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isPlayerXTurn = savedInstanceState.getBoolean( "isPlayerXTurn" );
        isGameWon = savedInstanceState.getBoolean( "isGameOver" );
        isGameFilled = savedInstanceState.getBoolean( "isGameFilled" );

        changeStatusText();
        if(isGameWon || isGameFilled) {
            DisableButtons();
        }

        CharSequence[] buttonBoardValues =
                savedInstanceState.getCharSequenceArray( "buttonBoardValues" );

        for( int i = 0; i < buttonBoard.length; i++ ) {
            buttonBoard[i].setText(buttonBoardValues[i]);
        }
    }
}
