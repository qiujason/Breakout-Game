game
====

This project implements the game of Breakout.

Name: Franklin Wu, Jason Qiu

### Timeline

Start Date: Sept 12 2020

Finish Date: Sept 29 2020

Hours Spent: 30 each

### Resources Used
StackOverflow, JavaFX API


### Running the Program

Main class: GameLauncher

Data files needed: 

Files to read in blocks, which can be found under data/blockfiles.
Also need file to store high score, which can be found under data/highscore.

Key/Mouse inputs:

Mouse: Launches ball. To do so, click anywhere in the window. The position in which
you click the mouse will approximately direct the direction of the ball. This is not 
meant to be accurate because we did not wish for users to be able to pinpoint
send the ball into any potential gaps formed by missing blocks, which would
make the game much easier. Randomness is fun! 

Left Arrow Key: Moves paddle to the left.

Right Arrow Key: Moves paddle to the right.

Cheat keys:

Known Bugs:

1) Game Physics of the Ball + Block Collisions. 
The physics of our game is slightly off. Occasionally, when the ball approaches
a block in a particular way, it might pass right through the block without bouncing
off, leading to some weird pathing. We tried very hard to debug this, but ultimately
to no avail. We think that there are two issues that could be contributing to this. The
first potential issue is that 

One solution that we looked into and ruled out was the  

2) Game Physics of the Ball + Paddle Collision

Extra credit:


### Notes/Assumptions


### Impressions

