### Status: Complete

Conway's Game of Life
=====================

Yet Another Conway's Game of Life Implementation  

Most rulesets were borrowed from http://www.mirekw.com/ca/rullex_life.html  
Rulesets are in born/survive format  
Save files list rows and columns then represent the board with - for dead and * for living  
Spaces are necessary

Example:
````
4 5
- - - - -
- - - - -
- * * - -
- * * - -
````
This is a conways game with 4 rows and 5 columns  
It has a 2x2 box at the bottom of living cells but the rest are dead  
See the saves folder for more examples

Controls
--------

space - Start/stop simulation  
+/= - Increase size of board  
-/_ - Decrease size of board  
p - Disable/enable wraparound  
c - Clear the board  
w - Write board to file  
r - Read board from file  
n - Generate new random board  
s - Resize board and regenerate  
b - Toggle cell borders  
./> - Advance one generation at a time  
,/< - Automatically advance generations  
e - Change ruleset  
a - Switch backend  
t - Toggle viewmode  
