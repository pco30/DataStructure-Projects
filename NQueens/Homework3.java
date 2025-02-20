public class Homework3 {
    int N; // Size of the board
    int[][] board; // Board to store queen positions

    public Homework3(int N) {
        this.N = N;
        this.board = new int[N][N]; // Initialize the board 
    }

    public static void main(String[] args) {
        int N = 4; // 4 by 4 board size
        Homework3 solver = new Homework3(N);
        if (!solver.solver(0)) {
            System.out.println("No solution, try another N value");
        }
    }

    // Method to check if a queen can be safely placed at board[row][column]
    private boolean isValidPlacement(int row, int column) {
        // Check this column on the previous rows
        for (int i = 0; i < row; i++) {
            if (board[i][column] == 1) {
                return false;
            }
        }
        
        // Check the rows to ensure no interferring queen placements
        for (int i = 0; i < column; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check the 2 diagonals for the same reason
        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        for (int i = row, j = column; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    //Recursive method to solve for N-queens 
    public boolean solver(int row) {
        // Base case so If all queens are placed, print the solution and stop the recursion
        if (row >= N) {
            printSolution();
            return true;
        }

        // Try placing a queen in each column of the current row
        for (int column = 0; column < N; column++) {
            if (isValidPlacement(row, column)) {
                // Place the queen
                board[row][column] = 1;

                // Recur to look for queen placement in the next row
                if (solver(row + 1)) {
                    return true;
                }

                // If placing queen here leads to no solution, backtrack
                board[row][column] = 0; // Remove the queen
            }
        }

        return false; 
    }

    // Method to print the positions of the queens on the board
    private void printSolution() {
    	// Loop through the rows and columns and print where i,j == 1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) { // Queen found
                    System.out.println("Queen at row " + (i + 1) + ", column " + (j + 1)); //(i+1) and (j+1) because of indexing
                }
            }
        }
    }
}
