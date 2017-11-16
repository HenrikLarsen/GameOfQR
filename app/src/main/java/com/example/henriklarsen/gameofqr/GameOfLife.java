package com.example.henriklarsen.gameofqr;

/**
 * Created by henriklarsen on 10.03.2017.
 */

public class GameOfLife{
    private StaticBoard playBoard;
    private byte[][] neighbourCount;
    private byte[][] newGenerationCells;
    private boolean isAnimating = false;

    /**
     * Sole constructor, sets the parameter board as the current board.
     * @param board StaticBoard - The board to be played.
     */
    public GameOfLife(StaticBoard board) {
        this.playBoard = board;
    }

    /**
     * Sets the next generation of cells as the current play board.
     * Calls on Boards countNeighbours() and sets it as a nested byte array.
     * Calls on enforceRules() and finally sets the new generation as the current play board.
     * @see #enforceRules()
     * @see StaticBoard#setBoard(byte[][])
     * @see StaticBoard#countNeighbours()
     */
    public void nextGeneration() {
        neighbourCount = playBoard.countNeighbours();
        enforceRules();
        playBoard.setBoard(newGenerationCells);
    }

    /**
     * Compares the current board with the neighbour count and enforces the
     * rules of the game, creating a new nested array to be the next generation's board.
     * @see #newGenerationCells
     * @see #neighbourCount
     * @see StaticBoard#boardGrid
     */
    private void enforceRules() {

        //Creates a new byte[][] with the same dimensions as the current board.
        newGenerationCells = new byte[playBoard.getBoard().length][playBoard.getBoard()[0].length];

        //Compares the current play board with the neighbour count.
        //Sets the values in newGenerationCells based on the rules of the Game of Life.
        for (int x = 0; x < playBoard.getBoard().length; x++) {
            for (int y = 0; y < playBoard.getBoard()[0].length; y++) {

                //Checks if the current cell is alive
                if (playBoard.getBoard()[x][y] == 1) {

                    //Checks if the live cell has less than two or more than three living neighbours.
                    //If yes, the cell dies.
                    if (neighbourCount[x][y] < 2 || neighbourCount[x][y] > 3) {
                        newGenerationCells[x][y] = 0;
                    }

                    //Checks if the live cell has exactly two or three living neighbours.
                    //If yes, the cell survives to the next generation.
                    else if (neighbourCount[x][y] == 2 || neighbourCount[x][y] == 3) {
                        newGenerationCells[x][y] = 1;
                    }
                }

                //If the current cell is dead and has exactly three living neighbours, it comes alive.
                else if (playBoard.getBoard()[x][y] == 0) {
                    if (neighbourCount[x][y] == 3) {
                        newGenerationCells[x][y] = 1;
                    }
                }
            }
        }
    }

    /**
     * Method that takes a boolean as a parameter and sets it to isAnimating
     * @param ani - Sets if animating or not.
     */
    public void setIsAnimating(boolean ani){
        this.isAnimating = ani;
    }

    /**
     * Method that returns the boolean of if animating.
     */
    public boolean getIsAnimating(){
        return this.isAnimating;
    }

    /**
     * Method that returns the boolean of if animating.
     */
    public StaticBoard getBoard(){
        return this.playBoard;
    }
}
