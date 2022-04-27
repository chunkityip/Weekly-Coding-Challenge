import java.util.HashMap;

/**
 * Robot in a Grid: 
 * Imagine a robot sitting on the upper left corner of grid with r rows and c columns (r * c). 
 * The robot can only move in two directions, right and down, but certain cells are "off limits" such that the robot cannot step on them. 
 * Design an algorithm to find a path for the robot from the top left to the bottom right.
 * 
 * Since the question didn't tell us what will be off limits cells look like , I will setup 1 as off limits , 0 as normal cells 
 * 
 * Example 1 :
 * 
 * Robot starting point--> 0 , 0 , 0
 *                         0 , 1 , 0<--1 as Off limits cells
 *                         0 , 0 , 0<--Robot end point
 * 
 * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * Output: 2
 * Explanation: There is one obstacle in the middle of the 3 x 3 grid above.
 * There are two ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down -> Down
 * 2. Down -> Down -> Right -> Right
 * 
 * Example 2 :
 * 
 * Robot starting point--> 0 , 1<--Off limits cells
 *                         0 , 0<--Robot end point
 * 
 * Input: obstacleGrid =[[0 , 1],[0 , 0]]
 * Output: 1
 * 
 * 
 * My idea of Constraints:
 * r == obstacleGrid.length
 * c == obstacleGrid[i].length
 * obstacleGrid[i][j] is 0 or 1



Soluation 1 : Recursion
x    y
(0 , 0) as start position to (r - 1 , c - 1) as ending position

There can have two way as first step of moving :
1 . (x , y) go right as (0 , y + 1) to (r - 1 , c - 1)
or
2. (x , y) go down as (x + 1, y) to (r - 1 , c - 1)

Because it is recursion, to avoid any repeated movement, set "@" to the grid for those already been visited
Ex: 
                 v
obstacleGrid = [[0,0,0],
                [0,1,0],
                [0,0,0]]
(x + 1 , y) going down

obstacleGrid = [[@,0,0],
               >[0,1,0],
                [0,0,0]]
(1x + 1 , y) going down again

obstacleGrid = [[@,0,0],
                [@,1,0],
               >[0,0,0]]

                etc...........

How to avoid the off limits cells 
off limits cells are 1 and normal cells is 0 . It means normal cells are alaways small then off limits cells.
and
x and y always are 0 .If there have one cells is bigger than 0 which is 1 , 2......... We can know there is  off limits cells 

*/
class RobotInAGrid {
   

public int rebotInAGrid(int[][] obstacleGrid) {
    //r as rows
    int r = obstacleGrid.length;
    //c as columns
    int c = obstacleGrid[0].length;

    HashMap<String , Integer> visited = new HashMap<>();

    //base case
    // If the starting cell has an obstacle, then simply return as there would be
    // no paths to the destination.
    if (obstacleGrid[0][0] == 1) {
        return 0;
    }
    // first 0 as doing down , second 0 as going right , third 0 as the path for the robot from the top left to the bottom right
    return rebotAlgorithm( 0 , 0 , r - 1 , c - 1 , 0 , visited, obstacleGrid);
}

public int rebotAlgorithm(int x , int y , int r , int c , int path , HashMap<String , Integer> visited , int [][] obstacleGrid) {
     //base case
     if (x == r && y == c) {
         return 1;
     }
     //To count how many down movement or right movement
     int down = 0;
     int right = 0;

     // Set "@" to the x grid for those already been visited
     String key = (x + 1) + "@" + y;

     //Determine whether the current point has been visited
     if (!visited.containsKey(key)) {
         //Determine whether the current point is off limits cells or not 
         if (x + 1 <= r && obstacleGrid[x + 1][y] == 0) {
            down = rebotAlgorithm(x+ 1 , y  , r  , c  , path , visited , obstacleGrid);
         } 
        } else {
            down = visited.get(key);
        }
    // Set "@" to the y grid for those already been visited
    key = x + "@" + (y + 1);
    if (!visited.containsKey(key)) {
        //Determine whether the current point is off limits cells or not 
        if (y + 1 <= c && obstacleGrid[x][y + 1] == 0) {
            right = rebotAlgorithm(x+ 1 , y  , r  , c  , path , visited , obstacleGrid);
        } 
    } else {
        right = visited.get(key);
    }

    //Putting the current path into visited
    key = x + "@" + y;
    visited.put(key , down + right);
    return down + right;
    }
}

/**
 * Soluation 2 : Dynamic programming
 * Every Recursion can do it in Dynamic Porgramming , basically just going to search x(i) and y(j) 
 * It means filling the valuews for every column and every row at one time. It save lot of time complexity compare with Recursion at multiple time
 * If there is off limits cells as 1 , break it 
 * Basically , I will put the visited path to off limits cells (0 to 1) so those path can't visited again to avoid any repeat visited 
 * And the funcation will be very simple : dp[i][j] = dp[i - 1][j] + dp[i][j - 1] to sum the path 
 */
class RobotInAGrid {
    public int rebotInAGrid(int[][] obstacleGrid) {

        int r = obstacleGrid.length;
        int c = obstacleGrid[0].length;

        int[][] dp = new int[r][c];

        //for row
        for (int i = 0; i < r; i++) {
            if (obstacleGrid[i][0] == 1) break;
            //the path is visited so change to 1
            dp[i][0] = 1;
        }

        //for column
        for (int j = 0; j < c; j++) {
            if (obstacleGrid[0][j] == 1) break;
            //the path is visited so change to 1
            dp[0][j] = 1;
        }

        //fucntion to add the path
        for (int i = 1; i < r; i++) {
            for (int j = 1; j < c; j++) {
                //Because I am counting the possiable visted path , so igrone the off limits cells
                if (obstacleGrid[i][j] == 1) continue;
                dp[i][j] = dp[i - 1][j] + dp[i][j -1];
            }
        }

        return dp[r - 1][c - 1];
    }
}


/**
 * Soluation 3 : Dynamic programming (Leetcode soluation) 
 * https://leetcode.com/problems/unique-paths-ii/solution/
 */
class RobotInAGrid {
    public int rebotInAGrid(int[][] obstacleGrid) {

        int r = obstacleGrid.length;
        int c = obstacleGrid[0].length;

        // If the starting cell has an obstacle, then simply return as there would be
        // no paths to the destination.
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }

        int[] dp = new int[c];
        int i = 0;

        // Filling the values for the first column
        for (int i = 1; i < r; i++) {
            obstacleGrid[i][0] = (obstacleGrid[i][0] == 0 && obstacleGrid[i - 1][0] == 1) ? 1 : 0;
        }

        // Filling the values for the first row
        for (int i = 1; i < c; i++) {
            obstacleGrid[0][i] = (obstacleGrid[0][i] == 0 && obstacleGrid[0][i - 1] == 1) ? 1 : 0;
        }

        // Starting from cell(1,1) fill up the values
        // No. of ways of reaching cell[i][j] = cell[i - 1][j] + cell[i][j - 1]
        // i.e. From above and left.
        for (int i = 1; i < r; i++) {
            for (int j = 1; j < c; j++) {
                if (obstacleGrid[i][j] == 0) {
                    obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
                } else {
                    obstacleGrid[i][j] = 0;
                }
            }
        }

        // Return value stored in rightmost bottommost cell. That is the destination.
        return obstacleGrid[r - 1][c - 1];
    }
}
/*
Time Complexity: O(r x c). The rectangular grid given to us is of size r x c and we process each cell just once.
Space Complexity: O(1). We are utilizing the obstacleGrid as the DP array. Hence, no extra space.

*/