package com.example.henriklarsen.gameofqr;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameOfLifeTest {
    private StaticBoard board;
    private GameOfLife gol;

    @Test
    public void nextGenerationTest1() {
        board = new StaticBoard(8,8);
        gol = new GameOfLife(board);
        byte[][] testBoard = {
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}};

        board.setBoard(testBoard);
        gol.nextGeneration();
        String expectedOutput = "0000000000000000101000000110000001000000000000000000000000000000";
        org.junit.Assert.assertEquals(expectedOutput, gol.getBoard().toString());
        org.junit.Assert.assertEquals(8, board.getWidth());
        org.junit.Assert.assertEquals(8, board.getHeight());
    }
}