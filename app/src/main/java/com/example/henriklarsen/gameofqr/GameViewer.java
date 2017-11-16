package com.example.henriklarsen.gameofqr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;



/**
 * Created by henriklarsen on 13.03.2017.
 */

public class GameViewer extends View{
    private StaticBoard staticBoard;
    private GameOfLife gameOfLife;

    private int cellSize;
    private boolean canDraw = false;

    private Paint deadCells;
    private Paint aliveCells;

    // Constructor of the GameViever
    public GameViewer(Context context, StaticBoard s, GameOfLife g) {
        super(context);
        staticBoard = s;
        gameOfLife = g;
        init();
    }

    // Makes the paints and sets the colors.
    private void init(){
        aliveCells = new Paint();
        deadCells = new Paint();
        aliveCells.setColor(Color.BLACK);
        deadCells.setColor(Color.WHITE);
        cellSize = staticBoard.getCellSize();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Gets the cellsize to be drawn and checks if it is going to animate.
        cellSize = staticBoard.getCellSize();
        canDraw = gameOfLife.getIsAnimating();

        // Itherates through the board and draws alive and dead cells.
        for (int x = 0; x < staticBoard.getBoard().length; x++){
            for (int y = 0; y <staticBoard.getBoard()[0].length; y++){
                if(staticBoard.getBoard()[x][y] == 1){
                    canvas.drawRect(x*cellSize, y*cellSize, (x*cellSize + cellSize), (y*cellSize + cellSize), aliveCells);
                }else if(staticBoard.getBoard()[x][y] == 0){
                    canvas.drawRect(x*cellSize, y*cellSize, (x*cellSize + cellSize), (y*cellSize + cellSize), deadCells);
                }
            }
        }
        // onDraw method will loop with invalidate if canDraw is true.
        if(canDraw){
            gameOfLife.nextGeneration();
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            invalidate();
        }
    }

    public void setDrawColor(Object paintColor){
        if(paintColor.equals("RED")){
            aliveCells.setColor(Color.RED);
        }else if(paintColor.equals("GREEN")){
            aliveCells.setColor(Color.GREEN);
        }else if(paintColor.equals("BLUE")){
            aliveCells.setColor(Color.BLUE);
        }else if(paintColor.equals("YELLOW")){
            aliveCells.setColor(Color.YELLOW);
        }else if(paintColor.equals("BLACK")){
            aliveCells.setColor(Color.BLACK);
        }else if(paintColor.equals("BROWN")){
            aliveCells.setColor(Color.rgb(165,42,42));
        }else if(paintColor.equals("ORANGE")){
            aliveCells.setColor(Color.rgb(255,165,0));
        }else if(paintColor.equals("GRAY")){
            aliveCells.setColor(Color.GRAY);
        }else if(paintColor.equals("PURPLE")){
            aliveCells.setColor(Color.rgb(128,0,128));
        }else if(paintColor.equals("PINK")){
            aliveCells.setColor(Color.rgb(255,105,180));
        }else{
            aliveCells.setColor(Color.BLACK);
        }
    }

}


