## Lab Discussion
### game_team28
### Franklin Wu (fyw4), Jason Qiu (jq39)


### Issues in Current Code

 * Game class is too long, should only contain methods that are necessary for
 running the Game class. It currently contains a lot of helper methods that should be 
 re-allocated to other classes
 * Nested for loops and if statements make code very hard to read
 * A lot of extensive if/else statement complexes, which violate the open-closed 
 principle
 * Methods are still long, and method/variable naming could use some work

#### Handle Key Input Method
 * Design issue
    This is the longest method within our code. The length makes it very hard to read
    and it looks messy overall.
 
 * Design issue
    The method is almost solely reliant on a bunch of nested if/else statements. 
    Not only does it contribute a lot to the length of the method, but it also violates
    the open-closed principle. If someone wanted to add another key input, it would once
    again lengthen the method by 6-7 lines, which is highly inefficient.

#### Game Class
 * Design issues
    The Game class is very long, which makes it hard to read. It also violates
    the Single Responsibility Principle, since it contains a plethora of methods
    that do not specifically relate to the running of the game.

 * Design issue
    There is an excess of public and private constants declared at the top of the class.
    Many of these constants should be allocated to other classes, since they don't deal
    with the functioning of the Game, but rather to other class implementations.


### Refactoring Plan

 * What are the code's biggest issues?
 
 Methods are too long, and methods employ too many if/else statmeents
 that violate the open-closed principle.

 * Which issues are easy to fix and which are hard?
 
 Refactoring the methods to be shorter is easy, since we can just extract helper methods.
 Trying to get around using if/else statements will be tricky, since it's difficult to 
 implement an alternate solution that won't use if/else statements if the implementation
 is based on a case-by-case handling (i.e dealing with key inputs).

 * What are good ways to implement the changes "in place"?
   
   A good way to implement the changes in place would be to utilize IntelliJ's 
   method extraction functions, which will make creating helper methods much easier.

### Refactoring Work

 * Issue chosen: Fix and Alternatives
 
    HandleKeyInput: We will fix it by first extracting helper methods to make the code
    more readable. Then, we will also implement switch statements instead of if/else. Although
    they are functionally the same, we think that switch statements look more clean. 
    In the future, we'd like to change the final implementation to a Map, or something
    else that does not require if/else statements.


 * Issue chosen: Fix and Alternatives
 
    Cleaning up Game class: we went through and shorten a lot of the methods, and we will
    also re-allocate certain methods and constants to their appropriate class, rather than
    just having everything collect in the Game class.