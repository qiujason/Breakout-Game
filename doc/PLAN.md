# Game Plan
## NAMEs

breaking generates more balls (or other ways of adding balls)
moving blocks
variations of blocks (change how much is needed to break)
preset images

### Breakout Variation Ideas

### Interesting Existing Game Variations

 * Game 1 - Bricks n Balls: this was interesting because it had more of a puzzle/strategy feel towards the game. The game
 had a grid of blocks that had numbers on them. Whenever a ball was aimed and shot at a block, the number on the block dropped as 
 more balls hit it. The ball richochets off the wall and other blocks and drops the number on more blocks until finally
 it drops off the screen. This variation was really interesting because we liked the way users were able to aim and 
 use angles to strategize rather than repeatedly trying to save the ball from falling off the screen. Also, we liked the
 idea that the blocks don't break immediately, adding a more intense feel.

 * Game 2 - Centipong: this was interesting because the game felt very dynamic because the blocks themselves moved
 and more were added exponentially as blocks break. This game had "centipede-like" moving blocks that went back and forth horizontally and slowly
 crawled down the screen. Whenever a user broke a block, a new ball was generated from the broken block. This quickly
 cascaded into a large chain reaction that was cool and chaotic to watch and is also very satisfying.


#### Block Ideas

 * Block 1 - single-hit: takes one hit for the user to destroy. This will be the most standard block.

 * Block 2 - multi-hit: takes more than one hit for the user to destroy. This can start off as a low number of hits needed
   and progresses into larger numbers as the level count goes up.

 * Block 3 - unbreakable: creates obstacles for the user to get around to hit the other blocks. This can be used in higher levels
   to add challenge to the game.
   
 * Block 4 - bomb block: does damage to all blocks in a surrounding radius when broken.


#### Power Up Ideas

 * Power Up 1 - +1 life. When you hit this powerup with a ball, it adds an extra life (in the form of another ball to use
 in the future).

 * Power Up 2 - slowdown. When you hit this powerup with a ball, it slows down your ball for a certain amount of time,
 which can give the user more time to react.

 * Power Up 3 - decrease size of paddle. This powerup is bad for the user as it will require the user to be more accurate
 in paddle placement when the ball falls down.


#### Cheat Key Ideas

 * Cheat Key 1 - 'I' - add 1 life

 * Cheat Key 2 - 'Q' - clear all blocks

 * Cheat Key 3 - space key: pause or unpause the game

 * Cheat Key 4 - 'R' - reset game


#### Level Descriptions

Key
(---- represents 1 single-hit block)
(==== represents multi-hit blocks)

 * Level 1
   * Block Configuration (5x5 grid of single-hit blocks) - basic implementation
   ---- ---- ---- ---- ----
   ---- ---- ---- ---- ----
   ---- ---- ---- ---- ----
   ---- ---- ---- ---- ----
   ---- ---- ---- ---- ----

   * Variation features
   Every N number of times the ball hits the paddle, the blocks progress downwards.

 * Level 2 - more advanced level with variation in blocks
   * Block Configuration
  ---- ---- ---- ---- ----
  ---- ====      ==== ----
  ----      ----      ----
  ---- ====      ==== ----
  ---- ---- ---- ---- ----

   * Variation features
   Different types of blocks are used (multi-hit blocks and 1 hit blocks).

 * Level 3 - preset art
   * Block Configuration 

    ----- -----
    -----	-----
    -----	  -----
    -----	  -----
    -----	  -----
    -----	-----
    ----- -----

    (miniature, un-detailed version, but it's the Duke logo!)
   * Variation features
    As opposed to the regular breakout, our game will have presets in which the blocks will be generated in some sort of art.

### Possible Classes

 * Class 1 - Block Class (Abstract)
   * Purpose
   
   The purpose of this class is to create an extendable class for all the different
   types of blocks to be implemented in the program. This class will likely be abstract.

   * Method
   
        * Constructor that sets the number of hits it takes for the block to break,sets
   the position of the block, and sets the color of the block
   
        * Getter for the position of the block

 * Class 2 - Ball class
   * Purpose
    
    The purpose of this class is to implement the ball used to break the blocks in
    the game.  
    
   * Method
   
        * Constructor that sets the size, color, and position of the block
        * Method to change ball position
        * Method to check if ball has collided with block
        * Method to check if ball has collided with the wall
        * Method to check if the ball has collided with the paddle
        * Method to check if the ball is still in play (i.e not below the paddle)

 * Class 3 - Paddle Class
   * Purpose
   
   To implement the user-controlled paddle for the program

   * Method
   
        * Constructor to set the size and initial position of the paddle.

 * Class 4 - Power-Up Class
   * Purpose
    
    To create a class for all types of powerups that will be implemented in the game.
    
   * Method
        * Method to check if ball collided with it

 * Class 5 Game Class
   * Purpose
   
   The class that starts and runs the game using JavaFX.

   * Method
        * Method to update level once all blocks are cleared