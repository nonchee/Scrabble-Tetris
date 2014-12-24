TetraSpell 

Word.java

TetraSpell is like Tetris, but to get points you must spell a word. You can 
hard drop, rotate, wall kick and shift words around on the board as they fall,
but once they hit the bottom they're stuck forever.   

Use it to get really good at Scrabble, expand your four-letter vocabulary, or 
write random poems that only use four-letter words. 

Word.java
A Word is constructed by providing a String that is broken down into a 
set of characters that knows its own set of points, which are stored in a 2D 
array. It has methods for rotating, not intersecting with fellow words, and
staying in the bounds of the screen relative to the character array it is a 
member of. 

Dictionary.java
This one is directly taken from Homework 9. I simply created a dictionary out 
of Scrabble's four-letter dictionary. 

WordStatus.java
WordStatus keeps track of your score and the words you've written. It is 
constructed when a Board is constructed and responds to changes in the state of 
board.  

Instructions.java 
A simple JPanel that shows up when the User clicks the Instructions button. 


Board.java
The Board contains a 2D array characters, essentially keeping track of all the 
Words that have been played. The i, j indices of any character in the array
correspond to its x, y position on the drawing screen.     


Game.java 