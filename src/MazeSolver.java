/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        ArrayList<MazeCell> solution = new ArrayList<>();
        MazeCell currentCell = maze.getEndCell();

        while (currentCell != null) {
            // Add the cell to the beginning of the list
            solution.add(0, currentCell);
            currentCell = currentCell.getParent();
        }

        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Initialize a stack to perform DFS
        Stack<MazeCell> stack = new Stack<>();
        // Initialize an ArrayList to store the solution
        ArrayList<MazeCell> sol = new ArrayList<>();

        stack.push(maze.getStartCell());

        while (!stack.isEmpty()) {
            // Pop the top cell from the stack
            MazeCell currentCell = stack.pop();

            // Mark the current cell as explored
            currentCell.setExplored(true);

            // If the current cell is the end cell, break the loop
            if (currentCell == maze.getEndCell()) {
                break;
            }
            // Get the neighbors of the current cell
            ArrayList<MazeCell> neighbors = getUnexploredNeighbors(currentCell);

            // Explore each neighbor
            for (MazeCell neighbor : neighbors) {
                // Set the parent of the neighbor
                neighbor.setParent(currentCell);
                // Add the neighbor to the stack
                stack.push(neighbor);
            }
        }
        sol = getSolution();

        return sol;

    }
    private ArrayList<MazeCell> getUnexploredNeighbors(MazeCell cell) {
        ArrayList<MazeCell> neighbors = new ArrayList<>();

        int row = cell.getRow();
        int col = cell.getCol();

        // Explore neighboring cells in all directions
        exploreNeighbor(row - 1, col, neighbors); // NORTH
        exploreNeighbor(row, col + 1, neighbors); // EAST
        exploreNeighbor(row + 1, col, neighbors); // SOUTH
        exploreNeighbor(row, col - 1, neighbors); // WEST

        return neighbors;
    }

    // Check if the neighbor is unexplored and add it to the list
    private void exploreNeighbor(int row, int col, ArrayList<MazeCell> neighbors) {
        //Checks if the cell is valid or not and if the cell has already been explored
        if (maze.isValidCell(row, col) && !maze.getCell(row, col).isExplored()) {
            //if not explored or other condition: then it is added to neighbors.
            neighbors.add(maze.getCell(row, col));
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> queue = new LinkedList<>();
        // Initialize an ArrayList to store the solution
        ArrayList<MazeCell> sol = new ArrayList<>();
        // Add the start cell to the queue
        queue.add(maze.getStartCell());

        // Continue until the queue is empty
        while (!queue.isEmpty()) {
            // Remove the front cell from the queue
            MazeCell currentCell = queue.remove();

            // Mark the current cell as explored
            currentCell.setExplored(true);

            // If the current cell is the end cell, break the loop
            if (currentCell == maze.getEndCell()) {
                break;
            }

            // Get the neighbors of the current cell
            ArrayList<MazeCell> neighbors = getUnexploredNeighbors(currentCell);

            // Explore each neighbor
            for (MazeCell neighbor : neighbors) {
                // Set the parent of the neighbor
                neighbor.setParent(currentCell);
                // Add the neighbor to the queue
                queue.add(neighbor);
            }
        }

        // Get the solution path
        sol = getSolution();

        return sol;
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
