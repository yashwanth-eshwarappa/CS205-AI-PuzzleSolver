

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
		
//		for(int i=0;i<4;i++) {
//			switch(i) {
//			case 0://Move down
//				if(rowPosition == 2){
//					return null;
//				}
//				totalExpanded++;
//				break;
//			case 1://Move up
//				if(rowPosition == 0){
//					return null;
//				}
//				totalExpanded++;
//				break;
//			case 2://Move left
//				if(colPosition == 0){
//					return null;
//				}
//				totalExpanded++;
//				break;
//			case 3://Move right
//				if(colPosition == 2){
//					return null;
//				}
//				totalExpanded++;
//				break;
//			default: 
//				break;
//			}
//		}
		
		
		
//		System.out.println("0->Down");
		

		
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
				System.out.println("Inside Move down");
				node.totalExpanded++;
				temp = tempPuzzle[rowPosition + 1][colPosition];
				tempPuzzle[rowPosition + 1][colPosition] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			case 1://Move up
				if(rowPosition == 0){
					tempPuzzle = null;
					break;
				}
				System.out.println("Inside Move Up");
				node.totalExpanded++;
				temp = tempPuzzle[rowPosition - 1][colPosition];
				tempPuzzle[rowPosition - 1][colPosition] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			case 2://Move left
				if(colPosition == 0){
					tempPuzzle = null;
					break;
				}
				System.out.println("Inside Move left");
				node.totalExpanded++;
				temp = tempPuzzle[rowPosition][colPosition - 1];
				tempPuzzle[rowPosition][colPosition - 1] = 0;
				tempPuzzle[rowPosition][colPosition] = temp;
				break;
			case 3://Move right
				if(colPosition == 2){
					tempPuzzle = null;
					break;
				}
				System.out.println("Inside Move right");
				node.totalExpanded++;
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
}
