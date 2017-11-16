# Game Of Life - Android
Android implementation of <b>Conway's Game of Life</b> for Program Development (DATS1600) at Oslo and Akershus University College (HiOA), spring 2017. The assignment recieved an A.

## Created by:
Henrik Finnerud Larsen <br>
Oscar Vladau-Husevold

## Features
* Converting a text to playable QR-Code.
* Cellsize automatic set to fit the whole pattern to the device screen.
* Manipulate the cellsize.
* Change the colors of the cells.
* Converting a photo to a playable game of life board, using Floyd SteinBerg dithering.

## Conway's Game of Life
The Game of Life is a cellular automaton created by John Horton Conway in 1970. It is effectively a zero-player game, meaning that it needs no further input after start.

## How to play
Either insert and convert a text into a QR-Code or take a picture with the camera and play with the pattern converted from it.
The speed slider-bar will allow you to manipulate the size of the cells.

## Rules
The initial rules of Conway's Game of Life are quite simple: If a live cell has two or three neighbours, it survives to the next generation. If a dead cell has exactly three neighbours, it is born. All other conditions result in dead cells. 

## Built With

* [Java](https://www.java.com/en/)

## License
This project is licenced under the MIT License. See file LICENSE.md file for details
