package com.example.vbd.gold_feelyounger;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SudokuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        GameEngine.getInstance().createGrid(this);

    }
    private void printSudoku(int Sudoku[][]) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                System.out.print(Sudoku[x][y] + "|");
            }
            System.out.println();
        }
    }

}


