/*
 * StonesAI.java
 * Eric Lin
 * Stones Assignment
 * ICS3U1
 * 06/01/2020
 */

package stones;

/**
 * The StonesAI class is the AI that will play against the user in the game Stones when the option "One Player" is selected.
 */
		
public class StonesAI {
	private int currentLargest;
	
	/**
	 * Constuctor.
	 * pre: none
	 * post: Sets the variable currentLargest to 0
	 */
	
	public StonesAI() {
		currentLargest = 0;
	}

	/**
	 * The chooseColumn method will find the best tile to take stones from on the top side of the board.
	 * pre: 2D Array with a width of atleast 7.
	 * post: Returns the value of the column that is the best choice for the game Stones.
	 */
	public int chooseColumn(int[][] gameBoard) {
		// Initializing Variables.
		int finalCol = 1;
		int numStones;
		int currentLargest = 0;
		
		// Checking if any number of stones will leave the last stone at home pit.
		for (int col = 1; col < 7; col ++) {
			numStones = gameBoard[0][col];
			finalCol = (numStones - col) % 13;
			
			if (numStones != 0) {
				if (finalCol == 0 && col > 0) {
					return col;
				} else if (col == numStones && col > 0) {
					return col;
				}
			}
		}
		
		// Checking if any number of stones in each tile can end up on a tile with 0 stones.
				for (int col = 1; col < 6; col ++) {
					numStones = gameBoard[0][col];
					finalCol = col - numStones;
					
					if (numStones != 0 && finalCol > 0) {
						if (gameBoard[0][finalCol] == 0 && finalCol != 7 && col > 0) {
							return col;
						} else if ((col + numStones) > 13){
							finalCol = (col + numStones) % 13;
							if (gameBoard[0][finalCol] == 0 && finalCol != 0 && col > 0) {
								return col;
							}
						}
					}
				}
		
		// Find the column with the highest number of stones.	
		for (int col = 1; col < 7; col ++) {
			if (gameBoard[0][col] >= currentLargest && col > 0) {
				finalCol = col;
				currentLargest = gameBoard[0][col];
			}
		}

		return (finalCol);
	}
	
}
