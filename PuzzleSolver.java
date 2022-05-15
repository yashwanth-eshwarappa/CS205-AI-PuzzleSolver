import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class PuzzleSolver {
	// Puzzle Solver main class
	public static void main(String[] args){
		PuzzleSolver ps = new PuzzleSolver();
		ps.createPuzzle();
	}
	// Initial point of the project to create a puzzle or use a default puzzle.
	public void createPuzzle(){
		System.out.println("Enter 1 for default puzzle or 2 for entering custom puzzle");
		try (
			Scanner scan = new Scanner(System.in)) {
			String enteredValue = scan.nextLine();
			while (!enteredValue.equals("1") && !enteredValue.equals("2")){
				System.out.println("Please enter correct option!");
				enteredValue = scan.nextLine();
				if(enteredValue.equals("1") && !enteredValue.equals("2")){
					break;
				}
			}
			// Parent node with main puzzle to solve
			node puzzleMainNode = null;
			
			if(enteredValue.equals("1")) {
			// Default puzzle values
				puzzleMainNode = new node(new int[][]{{0, 7, 2},{4, 6, 1},{3, 5, 8}});
			}
			else { // Scan for custom puzzle values
				System.out.println("Please enter the first row. Provide space/tab after each value");
				String row1 = scan.nextLine();
				System.out.println("Please enter the second row. Provide space/tab after each value");
				String row2 = scan.nextLine();
				System.out.println("Please enter the third row. Provide space/tab after each value");
				String row3 = scan.nextLine();
				
				String[] r1 = row1.split(" ");
				String[] r2 = row2.split(" ");
				String[] r3 = row3.split(" ");
				
				int[][] puzzleMatrix = new int[3][3];
				for(int i=0;i<3;i++) {
					puzzleMatrix[0][i] = Integer.parseInt(r1[i]);
				}
				for(int i=0;i<3;i++) {
					puzzleMatrix[1][i] = Integer.parseInt(r2[i]);
				}
				for(int i=0;i<3;i++) {
					puzzleMatrix[2][i] = Integer.parseInt(r3[i]);
				}
				puzzleMainNode = new node(puzzleMatrix);
			}
			
			// Options to select an Algorithm
			System.out.println("Select the algorithm to solve the puzzle-");
			System.out.println("1. Uniform Cost Search");
			System.out.println("2. A* - Misplaced Tile heuristic");
			System.out.println("3. A* - Manhattan Distance heuristic");
			
			enteredValue = scan.nextLine();
			while(!(enteredValue.equals("1")|| enteredValue.equals("2") || enteredValue.equals("3"))) {
				System.out.println("Please select valid option from the above options");
				enteredValue = scan.nextLine();
				if(enteredValue.equals("1")|| enteredValue.equals("2") || enteredValue.equals("3")){
					break;
				}
			}
			
			// Passing algorithm selection to the generic search function
			puzzleMainNode.generic_search(Integer.parseInt(enteredValue));

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
	}
}

class node {
	static int totalExpanded = 0; // Total count of all expanded nodes
	int [][] puzzle = new int[3][3];
	int f;	//estimated cost of cheapest solution
	int g; //cost to get to node 
	int h; //estimated distance
	
	node(int[][] puzzle){
		this.puzzle = puzzle;
		g = 0;
		h = 0;
		f = 0;
	}
	
	//Generic Search algorithm
	void generic_search(int algoSelected){
		// min heap using priority queue
		PriorityQueue<node> pq = new PriorityQueue<node>(10, (node a, node b)-> a.f - b.f);
		Set<node> explored = new HashSet<>();
		
		// Utility class instance reference
		PuzzleSolverUtils pzUtil = new PuzzleSolverUtils();
		
		pq.add(this);
		
		node currentNode = this;
		int maxQueue = 0;
		
		while(pq.size() != 0){
			
			maxQueue = Math.max(pq.size(), maxQueue);
			currentNode = pq.poll();
			
			// Flag to check final state
			boolean isFinalState = true;
			
			// Set final state values
			final int[][] finalState = new int[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}};
			
			// Check if final state is reached
			for(int i = 0; i < 3; ++i){
				for(int j = 0; j < 3; ++j){
					if(!(currentNode.puzzle[i][j] == finalState[i][j])){
						isFinalState = false;
					}
				}
			}
			// Final state is reached. Printing the depth, totalExpanded and maximum nodes in queue at any given time.
			if(isFinalState){
				System.out.println("Goal Reached!");
				for(int i = 0; i < 3; ++i){
					for(int j = 0; j < 3; ++j){
						System.out.print(currentNode.puzzle[i][j] + " ");
					}
					System.out.print("\n");
				}
				
				System.out.println("---------------------------------------------------------");
				System.out.println("The depth of the goal state = " + currentNode.g);
				
				// If algorithm is Misplaced or Manhattan
				if(algoSelected != 1) {
					System.out.println("Algorithm expanded total nodes = "+ totalExpanded);
					System.out.println("The maximum nodes in the queue at any time = " + maxQueue);
					System.out.println("---------------------------------------------------------");
					return;
				}
				else if(algoSelected == 1) { // Handling special cases for Uniform cost search
					explored.add(currentNode);
					while(!pq.isEmpty()) {
						node temp = pq.poll();
						if(temp.g != currentNode.g) {
							System.out.println("Algorithm expanded total nodes = "+ totalExpanded);
							System.out.println("The maximum nodes in the queue at any time = " + maxQueue);
							System.out.println("---------------------------------------------------------");
							return;
						}
						
						// Get all neighbor nodes
						node[] neighborNodes = PuzzleSolverUtils.getNodeNeighbors(temp.puzzle);
						int count = 0;
						for(node each : neighborNodes){
							if(each != null) {
								count++;
								if(!pzUtil.isExplored(explored, each.puzzle)) {
									maxQueue++;
								}
							}
						}
						if(count > 1) {
							totalExpanded++;
						}
						maxQueue--;
					}
				}
			}
			
			System.out.println("Optimal state to expand when g(n) = " + currentNode.g  + ", h(n) = " + currentNode.h + " is: ");
			
			for(int i = 0; i < 3; ++i){
				for(int j = 0; j < 3; ++j){
					System.out.print(currentNode.puzzle[i][j] + " ");
				}
				System.out.print("\n");
			}
			// Adding node to explored set to avoid duplicates to Queue
			explored.add(currentNode);
			
			// Get all the neighbor nodes for currentNode
			node[] neighborNodes = PuzzleSolverUtils.getNodeNeighbors(currentNode.puzzle);
			totalExpanded++;
			
			for(node each : neighborNodes){
				if(each != null){
					each.g = currentNode.g + 1;
					// Based on algorithm selection perform cost function
					switch(algoSelected) {
						case 1: 
							each.f = each.g + 0; //since h = 0 for uniform cost
							break;
						case 2:
							each.h = pzUtil.getMisplacedCount(each.puzzle); // get the heuristic value for all the misplaced values
							each.f = each.g + each.h;
							break;
						case 3:
							each.h = pzUtil.getManhattanDist(each.puzzle); // get the heuristic distance
							each.f = each.g + each.h;
							break;
						default:
							break;
					}
					
					// If the node puzzle is not already explored, add it to queue
					if(!pzUtil.isExplored(explored, each.puzzle)) {
						pq.add(each);
						explored.add(each);
					}
					
				}
			}
		}
		
	}
}
