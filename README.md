# PathFinding-and-Maze-Generation
A Java project which uses multiple algorithms which can create or solve mazes. The user can also draw their own mazes / obstacles and the program will determine the shortest path (if applicable) from the start to the finish.  
  
The algorithms currently implemented are as follows (as of 11/06/2020):  
For solving mazes:  
-Dijkstra's shortest path algorithm / Breadth first traversal (in this instance both algorithms are visually and functionally identical, therefore I have decided to treat them as one).  
-Depth first traversal (doesn't guarantee the shortest path to the goal).  
-Dead End Backtracker / Tree Trimmer (an algorithm I came up with myself, however only works for perfect mazes (fully connected however no possible cycles)).  
-A Star algorithm (Dijkstra's algorithm but has a sense of direction).  
-Depth Star algorithm (Depth first traversal however it prioritises tiles which are closer to the goal (however still doesn't guarantee the shortest path to the goal)).  

For creating mazes:  
-Randomised Primm's algorithm  
-Recursive Backtracker  
-Recursive Division (supports horizontal lines only, vertical lines only, and a mixture of both horizontal and vertical lnes).  
-Mazectric (uses a modified version of Conway's Game of Life (with rule string B4|S1234) to produce a maze-like structure (however cannot be classified as an actual maze as most generated mazes have no solution without further modification)).  
  
Controls:  
While editing:  
M - Access the menu  
CTRL - Access the controls screen  
1 - Select wall tile  
2 - Select start tile  
3 - Select finish tile  
Left Click - Place currently selected tile / press menu button  
Right Click - Remove tile  
C - Clear all tiles  
  
Can be used anywhere:  
Q - Toggle grid  
Enter - Start or stop currently selected algorithm  
ESC - Exit  
