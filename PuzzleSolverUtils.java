import java.util.Set;

public class PuzzleSolverUtils {
	
	private static int[] getZeroPosition(int[][] currPuzzle){
		for (int i = 0; i < 3; ++i){
			for (int j = 0; j < 3; ++j){
				if(currPuzzle[i][j] == 0){
					return new int[] {i,j};
				}
			}
		}
		return null;
	}
	
	public static node[] getNodeNeighbors(int[][] puzzle) {

		int[] positions = getZeroPosition(puzzle);
		int rowPosition =  positions[0];
		int colPosition =  positions[1];
		
		node[] neighbors = new node[4];
		
		for(int i=0;i<4;i++) {
			int temp;
			int [][] tempPuzzle = new int[3][3];
			for(int m = 0; m< 3; m++){
				for(int n = 0; n< 3; n++){
					tempPuzzle[m][n] = puzzle[m][n];
				}
			}
			switch(i) {
			case 0://Move down
				if(rowPosition == 2){
					tempPuzzle = null;
					break;
				}
				temp = tempPuzzle[rowPosition + 1][colPosition];
				tempPuzzle[rowPosition + 1][colPosition] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			case 1://Move up
				if(rowPosition == 0){
					tempPuzzle = null;
					break;
				}
				temp = tempPuzzle[rowPosition - 1][colPosition];
				tempPuzzle[rowPosition - 1][colPosition] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			case 2://Move left
				if(colPosition == 0){
					tempPuzzle = null;
					break;
				}
				temp = tempPuzzle[rowPosition][colPosition - 1];
				tempPuzzle[rowPosition][colPosition - 1] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			case 3://Move right
				if(colPosition == 2){
					tempPuzzle = null;
					break;
				}
				temp = tempPuzzle[rowPosition][colPosition + 1];
				tempPuzzle[rowPosition][colPosition + 1] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			default: 
				break;
			}
			
			if(tempPuzzle == null) {
				neighbors[i] = null;
			}
			else {
				node newPuzzleNode = new node(tempPuzzle); 
				neighbors[i] = newPuzzleNode;
			}
		}

		return neighbors;
	}
	
	int getMisplacedCount(int[][] puzzle){
		int valueInEachPosition = 1;
		int misplacedCount = 0;
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				//If the goal state value does not match the value in puzzle -> add 1 to misplacedCount;
				if(puzzle[i][j] != valueInEachPosition){
					++misplacedCount;
				}
				++valueInEachPosition;
			}
		}
		return misplacedCount;
	}
	
	private int getDistance(int num, int x, int y){
		int[][] goalState = new int[][]{{1, 2, 3},{4, 5, 6},{7, 8, 0}};
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				if(num == goalState[i][j]){
					return Math.abs(x - i) + Math.abs(y - j);
				}
			}
		}
		return 0;
	}
	
	public int getManhattanDist(int[][] puzzle){
		int dist = 0;
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				if(puzzle[i][j] != 0){
					dist += getDistance(puzzle[i][j], i, j);
				}
			}
		}
		return dist;
	}

	
	public boolean isExplored(Set<node> explored, int[][] testPuzzle) {
		for(node each:explored) {
			int counter=0;
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					if(each.puzzle[i][j] == testPuzzle[i][j]) {
						counter++;
					}
				}
			}
			if(counter==9)
				return true;
		}
		return false;
	}
	
}
