package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isPlayerXTurn;
    //private boolean isResetState;

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
        if( selectedBtn.getText() == "" ) {
            selectedBtn.setText( isPlayerXTurn ? "X" : "O" );
            isPlayerXTurn = !isPlayerXTurn;
            changeStatusText();
        }
    }

    private void changeStatusText(){
        String StatusMessage;
        StatusMessage = isPlayerXTurn ? "Player X's Turn" : "Player O's Turn";
        statusText.setText( StatusMessage );
    }

    private void newGame(){
        isPlayerXTurn = false;
        for ( Button button : buttonBoard ) {
            button.setText("");
        }
        changeStatusText();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean( "isPlayerXTurn", isPlayerXTurn );

        CharSequence[] buttonBoardValues = new String[ buttonBoard.length ];
        for( int i = 0; i < buttonBoard.length; i++ ) {
            buttonBoardValues[i] = buttonBoard[i].getText();
        }
        outState.putCharSequenceArray( "buttonBoardValues", buttonBoardValues );
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isPlayerXTurn = savedInstanceState.getBoolean( "isPlayerXTurn" );
        changeStatusText();

        CharSequence[] buttonBoardValues =
                savedInstanceState.getCharSequenceArray( "buttonBoardValues" );

        for( int i = 0; i < buttonBoard.length; i++ ) {
            buttonBoard[i].setText(buttonBoardValues[i]);
        }
    }

}
