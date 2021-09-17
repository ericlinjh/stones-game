/*
 * StonesGame.java
 * Eric Lin
 * Stones Assignment
 * ICS3U1
 * 06/01/2020
 */

package stones;

/**
 * The StonesGame class will keep track of the Stones game board as well as interact with it.
 */

public class StonesGame {
	// Initializing variables
	private int[][] gameBoard;
	
	/**
	 * Default Constructor.
	 * pre: none
	 * post: Game board array created with the number of default stones being 2.
	 */
	public StonesGame() {
		// Creating the 2D array
		gameBoard = new int[2][7];
		
		// Assigning the values to the array
		for (int row = 0; row < 2; row ++) {
			for (int col = 1; col < 7; col ++) {
				gameBoard[row][col] = 2;
			}
		
		// Assigning the base values for the two home pits.
		gameBoard[0][0] = 0;
		gameBoard[1][0] = 0;
		}
	}
	
	/**
	 * Constructor.
	 * pre: none
	 * post: Game board array created with the number of default stones being the value the user selected.
	 */
	public StonesGame(int numStones) {
		// Creating the 2D array
		gameBoard = new int[2][7];
		
		// Assigning the values to the array
		for (int row = 0; row < 2; row ++) {
			for (int col = 1; col < 7; col ++) {
				gameBoard[row][col] = numStones;
			}
		
		// Assigning the base values for the two home pits.
		gameBoard[0][0] = 0;
		gameBoard[1][0] = 0;
		}
	}
	
	/**
	 * The distributeStones method will move one pit of stones across the board
	 * pre: none
	 * post: gameBoard array will be changed based on the pit the user initially selected. Depending on what ending pit the last stone
	 * landed on, a different integer value will be returned.
	 */
	public int distributeStones(int column, int playerNum) {
		// Initializing Variables
		int boardSide = playerNum;
		int numStones = gameBoard[boardSide][column];
		int newNumStones = numStones;
		int newColumn;

		// Taking away all stones from the pit the user wants.
		gameBoard[boardSide][column] = 0;
		
		// Alternating the side that the stones are being added to until there are no more stones to move.
		while(newNumStones != 0) {
			if (boardSide == 0) {
				newColumn = column - newNumStones; // This column value will only be used if the number of stones is at 0, meaning that newColumn will always be valid in the gameBoard array.
				column -= 1; // Moving to the column that the stone will start being added to
				
				// Moving the stones from pit to pit on the top side of the board.
				newNumStones = moveTop(column, newNumStones);
				
				// Checking what ending pit the last stone will land on
				if (newNumStones == 1 && playerNum == 0) {
					column = 0;
					gameBoard[0][0] += 1;
					newNumStones -= 1;
				} else if (newNumStones > 0) { 
					if (playerNum == 0) { // Checking if any stones are passing the player's home pit and should be added.
						gameBoard[0][0] += 1;
						newNumStones -= 1;
					}
					
					// Setting up variables for the bottom side of the board, and changing the boardSide for the loop.
					column = 0;
					boardSide = 1;
				} else {
					// Checking if the last pit didn't have any stones on it before and that there are stones eligible to steal.
					if (playerNum == 0 && gameBoard[0][newColumn] == 1 && gameBoard[1][newColumn] != 0) {
						takeCorresponding(newColumn, 0); // Taking the stones from the opposite tile.
						return 2; // Telling the application file what happened so it can display it to the user.
					}
				}
			} else {
				newColumn = column + newNumStones; // This column value will only be used if the number of stones is at 0, meaning that newColumn will always be valid in the gameBoard array.
				column += 1; // Moving to the column that the stone will start being added to
				
				// Moving the stones from pit to pit on the top side of the board.
				newNumStones = moveBottom(column, newNumStones);
				
				// Checking what ending pit the last stone will land on
				if (newNumStones == 1 && playerNum == 1) {
					column = 0;
					gameBoard[1][0] += 1;
					newNumStones -= 1;
				} else if (newNumStones > 0) {
					if (playerNum == 1) { // Checking if any stones are passing the player's home pit and should be added.
						gameBoard[1][0] += 1;
						newNumStones -= 1;
					}
					
					// Setting up variables for the bottom side of the board, and changing the boardSide for the loop.
					column = 7;
					boardSide = 0;
				} else {
					// Checking if the last pit didn't have any stones on it before and that there are stones eligible to steal.
					if (playerNum == 1 && gameBoard[1][newColumn] == 1 && gameBoard[0][newColumn] != 0) {
						takeCorresponding(newColumn, 1); // Taking the stones from the opposite tile.
						return 2; // Telling the application file what happened so it can display it to the user.
					}
				}
			}
		}
		
		// Telling the application file what happened so it can display it to the user
		if (column == 0) {
			return 0; // The last stone landed on a home pit.
		} else {
			return 1; // The last stone landed on a regular pit.
		}

	}
	
	/**
	 * The moveTop method will move stones on the top side of the board.
	 * pre: none
	 * post: the gameBoard array will be changed, and the remaining number of stones is returned
	 */
	public int moveTop(int column, int numStones) {
		while (column > 0 && numStones > 0) {
			gameBoard[0][column] += 1;
			numStones -= 1;
			
			if (column != 1) {
				column -= 1;
			} else {
				break;
			}
		}
		return numStones;
	}
	
	/**
	 * The moveBottom method will move stones on the bottom side of the board.
	 * pre: none
	 * post: the gameBoard array will be changed, and the remaining number of stones is returned
	 */
	public int moveBottom(int column, int numStones) {
		while (column < 7 && numStones != 0) {
			gameBoard[1][column] += 1;
			numStones -= 1;
			
			if (column != 6) {
				column += 1;
			} else {
				break;
			}
		}
		return numStones;
	}
	
	/**
	 * The takeCorresponding method will take stones from the opposite side of the board and add it to the proper user's home pit.
	 * pre: none
	 * post: The gameBoard array is changed to reflect the stealing of stones.
	 */
	public void takeCorresponding(int col, int playerNum) {
		if (playerNum == 1) {
			gameBoard[1][0] += gameBoard[0][col];
			gameBoard[0][col] = 0;
		} else {
			gameBoard[0][0] += gameBoard[1][col];
			gameBoard[1][col] = 0;
		}
	}
	
	/**
	 * The checkPitValid method will check if the pit the user is trying to select has stones in it or not.
	 * pre: none
	 * post: Returns either true if the pit is valid or false if the pit is invalid.
	 */
	public boolean checkPitValid(int column, int playerNum) {
		if (gameBoard[playerNum][column] == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * The checkTotalRemaining method will check if either the top or bottom row has no more stones.
	 * pre: none
	 * post: Returns an integer value based on which rows are empty or not.
	 */
	public int checkTotalRemaining() {
		// Initializing Variables
		boolean emptyRow = true;
		
		// Checking if the top row is completely empty.
		for (int col = 1; col < 7; col ++) {
			if (gameBoard[0][col] != 0) {
				emptyRow = false;
				break; // Since at least one tile isn't empty, there isn't a need to continue checking the row.
			}
		}
		
		// Returning 1 if the top row is empty, allows the emptyRow variable to be used again.
		if (emptyRow == true) {
			return 1;
		} 
		
		// Resetting the emptyRow variable for use in the next check.
		emptyRow = true;
		
		// Checking if the bottom row is completely empty
		for (int col = 1; col < 7; col ++) {
			if (gameBoard[1][col] != 0) {
				emptyRow = false;
				break; // Since at least one tile isn't empty, there isn't a need to continue checking the row.
			}
		}
		
		// Returning 2 if the bottom row is empty, or 0 if no rows are empty
		if (emptyRow == true) {
			return 2;
		} else {
			return 0;
		}
	}
	
	/**
	 * The endGame method will take all stones from the unempty side and add it to the home bin of the empty side. This ends the Stones game.
	 * pre: none
	 * post: The gameBoard array is changed to reflect the moving of stones.
	 */
	public void endGame(int emptySide) {
		// Initializing Variables
		int stonesAdded = 0;
		
		// Checking which side is empty to figure out which side to take from and which pit to add to
		if (emptySide == 2) {
			// Removing all stones from each pit on the top side and adding it to the home pit on the bottom.
			for (int col = 1; col < 7; col ++) { 
				gameBoard[1][0] += gameBoard[0][col];
				stonesAdded += gameBoard[0][col];
				gameBoard[0][col] = 0;
			}	
		} else {
			// Removing all stones from each pit on the bottom side and adding it to the home pit on the top.
			for (int col = 1; col < 7; col ++) {
				gameBoard[0][0] += gameBoard[1][col];
				stonesAdded += gameBoard[1][col];
				gameBoard[1][col] = 0;
			}
		}
	}
	
	/**
	 * The returnGameBoard method will return the gameBoard array to the calling method.
	 * pre: none
	 * post: Returns the gameBoard array.
	 */
	public int[][] returnGameBoard() {
		return gameBoard;
	}
}
