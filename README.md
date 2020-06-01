# PathFinding-and-Maze-Generation
A Java project which uses multiple algorithms which can create or solve mazes. The user can also draw their own mazes / obstacles and the program will determine the shortest path (if applicable) from the start to the finish.  
  
The algorithms currently implemented are as follows (as of 01/06/2020):  
For solving mazes:  
-Dijkstra's shortest path algorithm / Breadth first traversal (in this instance both algorithms are visually and functionally identical, therefore I have decided to treat them as one).  
-Depth first traversal (doesn't guarantee the shortest path to the goal).  
-Dead End Backtracker / Tree Trimmer (an algorithm I came up with myself, however only works for perfect mazes (fully connected however no possible cycles)).  
  
For creating mazes:  
-Randomised Primm's algorithm  
  
I plan on coming back to this project in the future to improve certain aspects of the code and to implement more methods to solve and generate mazes (I plan on adding A* (maze solver) and Recursive Division (maze generator) algorithms).  
  
Controls:  
While editing:  
1 - Select wall tile  
2 - Select start tile  
3 - Select finish tile  
Left Click - Place currently selected tile  
Right Click - Remove tile  
  
M - Select Primm's algorithm  
D - Select Dijkstra's algorithm  
T - Select Dead End Backtracker / Tree Trimmer algorithm  
F - Select Depth first traversal algorithm  
  
Can be used anywhere:  
Q - Toggle grid  
Enter - Start or stop currently selected algorithm  
ESC - Exit  
