package com.example.henriklarsen.gameofqr;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StaticBoardTest {
    private StaticBoard board;

    @Test
    public void countNeighboursTest1() {
        board = new StaticBoard(6,6);

        byte[][] testBoard = {
                {0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0}};
        board.setBoard(testBoard);
        byte[][]neighbourCount = board.countNeighbours();

        String expectedNeighbours = "112231113222244432123320123121001110";
        String actualNeighbours = array2DToString(neighbourCount);

        org.junit.Assert.assertEquals(expectedNeighbours, actualNeighbours);
    }

    @Test
    public void countNeighboursTest2() {
        board = new StaticBoard(8,8);
        byte[][] testBoard = {
                {0, 0, 0, 0, 0, 1, 0 ,0},
                {1, 1, 0, 0, 0, 1, 0 ,0},
                {0, 0, 1, 0, 0, 1, 0 ,0},
                {1, 0, 0, 0, 0, 0, 0 ,0},
                {0, 0, 0, 0, 0, 1, 1 ,0},
                {0, 0, 1, 0, 0, 0, 0 ,0},
                {0, 0, 1, 0, 1, 1, 0 ,0},
                {0, 1, 1, 0, 1, 0, 0 ,1}};
        board.setBoard(testBoard);
        byte[][]neighbourCount = board.countNeighbours();

        String expectedNeighbours = "2130101122422242121111320111135423221322121314232323132200011110";
        String actualNeighbours = array2DToString(neighbourCount);

        org.junit.Assert.assertEquals(expectedNeighbours, actualNeighbours);
    }

    @Test
    public void countNeighboursTest3() {
        board = new StaticBoard(10,10);
        byte[][] testBoard = {
                {1, 0, 0, 0, 1, 1, 0 ,0, 0, 1},
                {0, 1, 0, 0, 0, 1, 0 ,0, 0, 1},
                {0, 1, 1, 0, 0, 1, 0 ,0, 0, 1},
                {0, 0, 0, 0, 0, 1, 0 ,0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0 ,1, 1, 0},
                {0, 1, 0, 1, 0, 1, 0 ,0, 1, 0},
                {0, 0, 1, 0, 0, 1, 0 ,0, 0, 1},
                {1, 0, 0, 1, 0, 1, 0 ,0, 0, 1},
                {1, 1, 0, 1, 0, 1, 0 ,0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1 ,0, 0, 0}};
        board.setBoard(testBoard);
        byte[][] neighbourCount = board.countNeighbours();

        String expectedNeighbours = "1322122232232433343313232434321212223211243233554" +
                "223212122222333333331000223101123232332101212232110";
        String actualNeighbours = array2DToString(neighbourCount);

        org.junit.Assert.assertEquals(expectedNeighbours, actualNeighbours);
    }

    private String array2DToString(byte[][] neighbour) {
        String str = "";
        for (int y = 0; y < neighbour[0].length; y++) {
            for (int x = 0; x < neighbour.length; x++) {
                str = str + neighbour[x][y];
            }
        }
        return str;
    }
}