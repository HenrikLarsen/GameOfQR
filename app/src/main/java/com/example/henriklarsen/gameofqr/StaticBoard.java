package com.example.henriklarsen.gameofqr;

/**
 * Created by henriklarsen on 13.03.2017.
 */

public class StaticBoard {
    private  int width = 500, height = 500;
    private byte[][] boardGrid;
    private int cellSize = 10;

    /**
     * Constructor of the class
     * sets the parameter board as the current board.
     */
    public StaticBoard() {
        //this.boardGrid = new byte[WIDTH][HEIGHT];
    }

    /**
     * Constructor of the class
     * sets the parameter board as the current board.
     * @param i - width of the board
     * @param j - height of the board
     */
    public StaticBoard(int i, int j) {
        width = i;
        height = j;
        this.boardGrid = new byte[width][height];
    }

    /**
     * Method that counts neighbours to every cell.
     * Returns a byte[][] array with the count number to each cell.
     */
    public byte[][] countNeighbours() {
        int xMax = boardGrid.length;
        int yMax = boardGrid[0].length;

        //new byte[][] that sets the number of neighbours to each cell.
        byte[][] neighbourCount = new byte[xMax][yMax];

        //Iterates through the board and counts neighbours to each cell.
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {

                if (boardGrid[x][y] == 1) {

                    if (x - 1 >= 0 && y - 1 >= 0) {
                        neighbourCount[x - 1][y - 1]++;
                    }

                    if (x - 1 >= 0) {
                        neighbourCount[x - 1][y]++;
                    }

                    if (x - 1 >= 0 && y + 1 < yMax) {
                        neighbourCount[x - 1][y + 1]++;
                    }

                    if (y - 1 >= 0) {
                        neighbourCount[x][y - 1]++;
                    }

                    if (y + 1 < yMax) {
                        neighbourCount[x][y + 1]++;
                    }

                    if (x + 1 < xMax && y - 1 >= 0) {
                        neighbourCount[x + 1][y - 1]++;
                    }

                    if (x + 1 < xMax) {
                        neighbourCount[x + 1][y]++;
                    }

                    if (x + 1 < xMax && y + 1 < yMax) {
                        neighbourCount[x + 1][y + 1]++;
                    }
                }
            }
        }
        return neighbourCount;
    }

    @Override
    public String toString(){
        String str = "";
        for (int y = 0; y < boardGrid[0].length; y++) {
            for (int x = 0; x < boardGrid.length; x++) {
                if (boardGrid[x][y] == 1) {
                    str = str + "1";
                } else {
                    str = str + "0";
                }
            }
        }
        return str;
    }

    /**
     * Method that takes a board as a parameter and sets it as the current board.
     * @param newGrid - the new board to be set.
     */
    public void setBoard(byte[][] newGrid) {

        this.boardGrid = newGrid;
    }

    /**
     * Method that returns the board
     */
    public byte[][] getBoard(){
        return this.boardGrid;
    }


    /**
     * Method that returns the width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Method that returns the height of the board
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method that returns the size of the cells
     */
    public int getCellSize(){
        return this.cellSize;
    }

    /**
     * Method that takes the cellSize as a parameter and sets it.
     * @param cellSize - cellSize to be set
     */
    public void setCellSize(int cellSize){
        this.cellSize = cellSize;
    }

}
