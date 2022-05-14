import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class PuzzleSolver {
	
	public static void main(String[] args){
		PuzzleSolver ps = new PuzzleSolver();
		ps.createPuzzle();
	}
	
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
			node puzzleMainNode = null;
			if(enteredValue.equals("1")) {
			//	120 453 786 - 2 6 8
				puzzleMainNode = new node(new int[][]{{7, 1, 2},{4, 8, 5},{6, 3, 0}});
			}
			else {
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
			if (enteredValue.equals("1")){
				puzzleMainNode.generic_search(1);
			}
			else if (enteredValue.equals("2")){
				puzzleMainNode.generic_search(2);
			}
			else if (enteredValue.equals("3")){
				puzzleMainNode.generic_search(3);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
	}
}

class node {
	static int totalExpanded = 0; // Total count of all expanded nodes
	int [][] puzzle = new int[3][3];
	int f;
	int g;
	int h;
	
	node(int[][] puzzle){
		this.puzzle = puzzle;
		g = 0;
		h = 0;
		f = 0;
	}
	
	void generic_search(int algoSelected){
		
		PriorityQueue<node> pq = new PriorityQueue<node>(10, (node a, node b)-> a.f - b.f);
		
		Set<node> explored = new HashSet<>();
		
		PuzzleSolverUtils pzUtil = new PuzzleSolverUtils();
		
		pq.add(this);
		
		node currentNode = this;
		int maxQueue = 0;
		
		while(pq.size() != 0){

			maxQueue = Math.max(pq.size(), maxQueue);
			currentNode = pq.poll();
			
			boolean isFinalState = true;
			
			final int[][] finalState = new int[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}};
			for(int i = 0; i < 3; ++i){
				for(int j = 0; j < 3; ++j){
					if(!(currentNode.puzzle[i][j] == finalState[i][j])){
						isFinalState = false;
					}
				}
			}
			
			System.out.println(isFinalState);
			
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
				if(algoSelected != 1) {
					System.out.println("Algorithm expanded total nodes = "+ totalExpanded);
					System.out.println("The maximum nodes in the queue at any time = " + maxQueue);
					System.out.println("---------------------------------------------------------");
					return;
				}
				else if(algoSelected == 1) {
					explored.add(currentNode);
					while(!pq.isEmpty()) {
						node temp = pq.poll();
						if(temp.g != currentNode.g) {
							System.out.println("Algorithm expanded total nodes = "+ totalExpanded);
							System.out.println("The maximum nodes in the queue at any time = " + maxQueue);
							System.out.println("---------------------------------------------------------");
							return;
						}
						
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
			
			explored.add(currentNode);
			
			node[] neighborNodes = PuzzleSolverUtils.getNodeNeighbors(currentNode.puzzle);
			totalExpanded++;
			
			for(node each : neighborNodes){
				if(each != null){
					each.g = currentNode.g + 1;
					
					switch(algoSelected) {
						case 1: 
							each.f = each.g + 0; //since h = 0 for uniform cost
							break;
						case 2:
							each.h = pzUtil.getMisplacedCount(each.puzzle);
							each.f = each.g + each.h;
							break;
						case 3:
							each.h = pzUtil.getManhattanDist(each.puzzle);
							each.f = each.g + each.h;
							break;
						default:
							break;
						
					}
					
					if(!pzUtil.isExplored(explored, each.puzzle)) {
						pq.add(each);
						explored.add(each);
					}
					
				}
			}
		}
		
	}
}
