# Game Of Life
Android implementation of <b>Conway's Game of Life</b> for Program Development (DATS1600) at Oslo and Akershus University College (HiOA), spring 2017. The assignment recieved an A.

## Created by:
Henrik Finnerud Larsen <br>
Oscar Vladau-Husevold

## Features
* Click-to-draw pattern editor
* User created rulesets
* Importing and exporting patterns using .rle files, with posibility to move imported patterns around the game board before commiting.
* Gif-exportation of animated simulation
* Dynamically expanding game board.
* Multithreaded calculations
* Statistics of likelihood of a recurring pattern, cell difference between generations and 

## Conway's Game of Life
The Game of Life is a cellular automaton created by John Horton Conway in 1970. It is effectively a zero-player game, meaning that it needs no further input after start.

## How to play
Either load a pattern into the game using a file, an URL or the built in pre-sets, or use the left mouse button to draw a pattern on the play board. Then you can press start to start the game, or reset board to go back to a blank slate. 

The center view button will take you back to the middle of the board (The center is subject to change whilst the game is running.)

The speed slider-bar will allow you to manipulate the speed of the animation, from 1 to 30 frames per second (FPS)

## Rules
The initial rules of Conway's Game of Life are quite simple: If a live cell has two or three neighbours, it survives to the next generation. If a dead cell has exactly three neighbours, it is born. All other conditions result in dead cells. 

There are several other rule variations. You can use the rule drop-down menu to choose from a list of already created rules, or you can experiment with your own combination by writing in the Set Rules box and pressing enter. 

## Controlls: 
* Scroll Wheel 			      – Zoom in and out. 
* Left Mouse Button 		  – Draw and erase cells from the board.
* Right Mouse Button 	    – Drag the board to look around.

## Built With

* [Java](https://www.java.com/en/)

## License
This project is licenced under the MIT License. See file LICENSE.md file for details
