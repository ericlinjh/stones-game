/*
 * StonesGUI.java
 * Eric Lin
 * Stones Assignment
 * ICS3U1
 * 06/01/2020
 */

package stones;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The StonesGUI class will create, display, and modify a GUI to allow the user to play the game Stones.
 */

public class StonesGUI implements ActionListener {
	// Initializing Variables and Objects.
	JFrame frame;
	JPanel mainMenuPane, rulesPane, optionsPane, gamePane, backgroundPane, navFinalButtonsPane;
	JLabel logoLabel, rulesTitleLabel, howToPlayLabel, rule1Label, rule1ExplLabel, rule2Label, rule2ExplLabel, objectivesLabel, optionsLabel, numPlayersPromptLabel, numStartingStonesPromptLabel;
	JLabel gameBoardLabel, player1HomePitLabel, player2HomePitLabel, currentPlayerLabel, emptyLabel, specialEndTurnLabel, lastMoveLabel;
	JButton startButton, rulesButton, quitMMButton, returnRulesButton, confirmOptionsButton, returnOptionsButton, gameBoardButton, errorMessageButton, quitFinalButton, nextMoveButton;
	JComboBox numPlayersBox, numStartingStonesBox;
	int defaultStoneNum = 2, numPlayers = 1, playerNum = 1, winningPlayer;
	int[][] gameBoard;
	String defaultStoneString;
	JButton[][] gameButtons =  new JButton[2][7];
	StonesGame game;
	StonesAI bot = new StonesAI();
	
	/**
	 * The StonesGUI class will create the main menu for the stones game.
	 * pre: none
	 * post: A window with the main menu for the stones game is created.
	 */
	public StonesGUI() {
		// Creating and setting up the frame.
		frame = new JFrame("Stones");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creating a content pane.
		mainMenuPane = new JPanel();
		mainMenuPane.setLayout(new BoxLayout(mainMenuPane, BoxLayout.PAGE_AXIS));
		mainMenuPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Creating the main logo label.
		logoLabel = new JLabel(new ImageIcon("stonesLogo.png"));
		logoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		mainMenuPane.add(logoLabel);
		
		// Creating the buttons.
		startButton = new JButton("Start");
		startButton.setActionCommand("Start Main Menu");
		startButton.addActionListener(this);
		rulesButton = new JButton("Rules");
		rulesButton.setActionCommand("Rules");
		rulesButton.addActionListener(this);
		quitMMButton = new JButton("Quit");
		quitMMButton.setActionCommand("Quit");
		quitMMButton.addActionListener(this);
		
		// Positioning the buttons.
		startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		rulesButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		quitMMButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		quitMMButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		
		// Adding the buttons to the content pane.
		mainMenuPane.add(Box.createRigidArea(new Dimension(0, 10)));
		mainMenuPane.add(startButton);
		mainMenuPane.add(Box.createRigidArea(new Dimension(0, 10)));
		mainMenuPane.add(rulesButton);
		mainMenuPane.add(Box.createRigidArea(new Dimension(0, 10)));
		mainMenuPane.add(quitMMButton);
		
		// Adding content pane to the frame.
		frame.setContentPane(mainMenuPane);
		
		// Sizing and then displaying the frame.
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * The rulesScreen method will create a content pane with the rules on it.
	 * pre: none
	 * post: A content pane with the rules on it is created.
	 */
	public void rulesScreen() {	
		// Creating a content pane.
		rulesPane = new JPanel();
		rulesPane.setLayout(new BoxLayout(rulesPane, BoxLayout.PAGE_AXIS));
		rulesPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Creating, formatting, and adding required labels.
		rulesTitleLabel = new JLabel(new ImageIcon("rules.png"));
		rulesTitleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); 
		rulesTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		rulesPane.add(rulesTitleLabel);
		
		howToPlayLabel = new JLabel("<html>Stones is a two-player game. Players have the option to start the game with  2, 3, 4, or 5 stones. " 
				+ "The Stones board is made up of two rows of six <br/>pockets (also called “holes,” or “pits”) each. "
				+ "Players sit on opposite sides with the large bin to the player's right designated their home bin. "
				+ "On a <br/>turn, a player selects one of the six pits and remove all the stones by placing a stone counterclockwise around the board "
				+ "in each pit including the <br/>player's home bin (but excluding the opponent's home bin.)<html>");
		howToPlayLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		howToPlayLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesPane.add(howToPlayLabel);
		
		rule1Label = new JLabel("<html>RULE 1:<html>"); // Creating the label.
		rule1Label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		rule1Label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesPane.add(rule1Label);
		
		rule1ExplLabel = new JLabel("<html>If the last stone lands in the player's home bin, the player gets another turn.<html>"); 
		rule1ExplLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		rule1ExplLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesPane.add(rule1ExplLabel);
		
		rule2Label = new JLabel("<html>RULE 2:<html>");
		rule2Label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		rule2Label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesPane.add(rule2Label);
		
		rule2ExplLabel = new JLabel("<html>If the last stone lands in an empty pit on the player's side of the board, the player takes all stones in the corresponding " 
				+ "pit on the opponent's side <br/>and places them in the player's home bin.<html>");
		rule2ExplLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		rule2ExplLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesPane.add(rule2ExplLabel);
		
		objectivesLabel = new JLabel("<html>When a player cannot play (no more stones left in their 6 pits), the game is over and all stones remaining in the opponent's pit "
				+ " go to the player's <br/>home bin. The winner is the player with the most stones in the player's home bin at the end of the game.<html>");
		objectivesLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		objectivesLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		rulesPane.add(objectivesLabel);
		
		// Creating the button to return to the main menu.
		returnRulesButton = new JButton("Back");
		returnRulesButton.setActionCommand("Return to Main Menu");
		returnRulesButton.addActionListener(this);
		
		// Positioning the button.
		returnRulesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		returnRulesButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		// Adding the buttons to the content pane.);
		rulesPane.add(Box.createRigidArea(new Dimension(0, 5)));
		rulesPane.add(returnRulesButton);
		
		// Adding content pane to the frame.
		frame.setContentPane(rulesPane);
		
		// Sizing and then showing the frame.
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * The optionsScreen method will create a content pane that has the game options on it that can be interacted with by the user.
	 * pre: none
	 * post: An contents pane with the game options on it will be created and the user can interact with it.
	 */
	public void optionsScreen() {
		// Creating a content pane.
		optionsPane = new JPanel();
		optionsPane.setLayout(new BoxLayout(optionsPane, BoxLayout.PAGE_AXIS));
		optionsPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Creating, formatting, and placing the options title label.
		optionsLabel = new JLabel(new ImageIcon("options.png"));
		optionsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		optionsLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		optionsPane.add(optionsLabel);
		
		// Creating, formatting, and placing a label for prompting the user for number of players.
		numPlayersPromptLabel = new JLabel("Please select the number of players:");
		numPlayersPromptLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		optionsPane.add(numPlayersPromptLabel);
		
		// Creating, formatting, and placing a combo box to select the number of players.
		String[] numPlayersArray= {"One", "Two"};
		numPlayersBox = new JComboBox(numPlayersArray);
		numPlayersBox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		numPlayersBox.setSelectedIndex(0);
		numPlayersBox.addActionListener(this);
		optionsPane.add(numPlayersBox);
		
		// Creating, formatting, and placing a label for prompting the user for number of players.
		numStartingStonesPromptLabel = new JLabel("Please select the starting number of stones in each pit:");
		numStartingStonesPromptLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
		numStartingStonesPromptLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		optionsPane.add(numStartingStonesPromptLabel);
		
		// Creating, formatting, and placing a combo box to select the number of players.
		String[] numStartingStones = {"Two", "Three", "Four", "Five"};
		numStartingStonesBox = new JComboBox(numStartingStones);
		numStartingStonesBox.setAlignmentX(JComboBox.CENTER_ALIGNMENT);
		numStartingStonesBox.setSelectedIndex(0);
		numStartingStonesBox.addActionListener(this);
		optionsPane.add(numStartingStonesBox);
		
		// Creating the buttons.
		confirmOptionsButton = new JButton("Confirm");
		confirmOptionsButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		confirmOptionsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		confirmOptionsButton.setActionCommand("Confirm Options");
		confirmOptionsButton.addActionListener(this);
		returnOptionsButton = new JButton("Back");
		returnOptionsButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		returnOptionsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		returnOptionsButton.setActionCommand("Return to Main Menu");
		returnOptionsButton.addActionListener(this);
		
		// Adding the buttons and padding to the screen.
		optionsPane.add(Box.createRigidArea(new Dimension(0, 10)));
		optionsPane.add(confirmOptionsButton);
		optionsPane.add(Box.createRigidArea(new Dimension(0, 10)));
		optionsPane.add(returnOptionsButton);
		
		// Adding content pane to the frame.
		frame.setContentPane(optionsPane);
		
		// Sizing and then displaying the frame.
		frame.pack();
		frame.setVisible(true);		
	}

	/**
	 * The gameScreen method will create a content pane that has the actual Stones game on it, as well as information relating to the game.
	 * pre: none
	 * post: A content pane with the Stones game on it is created and can be interacted with.
	 */
	public void gameScreen() {
		// Setting up the game.
		game = new StonesGame(defaultStoneNum);
		defaultStoneString = Integer.toString(defaultStoneNum);
		
		// Creating a content pane.
		gamePane = new JPanel();
        gamePane.setLayout(new GridLayout(0, 8));
		
		// Creating and adding the top left corner of the gameBoard.
        gameBoardLabel = new JLabel(new ImageIcon("boardTopLeft.png"));
		gamePane.add(gameBoardLabel);
		
		// Creating, formatting, and adding the top 6 pits and buttons to interact with them.
		for (int col = 1; col < 7; col ++) {
	        gameBoardLabel = new JLabel(new ImageIcon("boardTop.png"));
	        gameBoardLabel.setLayout(new BorderLayout());
			gameBoardButton = new JButton(defaultStoneString);
			gameBoardButton.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
			
			// Simplifying error checking, if the user is playing single player they will always be on the bottom side, and these top buttons should never be pressed.
			if (numPlayers == 2) {
				gameBoardButton.setActionCommand("0 " + col);
				gameBoardButton.addActionListener(this);
			} else {
				gameBoardButton.setActionCommand("Wrong Side" + col);
				gameBoardButton.addActionListener(this);
			}
			gameBoardButton.setPreferredSize(new Dimension(gameBoardLabel.getHeight(), gameBoardLabel.getWidth()));
			gameBoardButton.setOpaque(false);
			gameBoardButton.setContentAreaFilled(false);
			gameBoardButton.setBorderPainted(false);
			gamePane.add(gameBoardLabel);
			gameBoardLabel.add(gameBoardButton);
			
			// Adding the button to an array of buttons.
			gameButtons[0][col] = gameBoardButton;
		}
		
		// Creating and adding the label that will show the top right corner of the board.
        gameBoardLabel = new JLabel(new ImageIcon("boardTopRight.png"));
		gamePane.add(gameBoardLabel);
		
		// Creating and adding the label and button that will display the home pit for player 2.
        gameBoardLabel = new JLabel(new ImageIcon("boardMidLeft.png"));
        gameBoardLabel.setLayout(new BorderLayout());
        
		gameBoardButton = new JButton("0"); // This button cannot be interacted with, but to maintain consistency with the other numbers it is made as a button.
		gameBoardButton.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		gameBoardButton.setPreferredSize(new Dimension(gameBoardLabel.getHeight(), gameBoardLabel.getWidth()));
		gameBoardButton.setOpaque(false);
		gameBoardButton.setContentAreaFilled(false);
		gameBoardButton.setBorderPainted(false);
		
		gamePane.add(gameBoardLabel);
		gameBoardLabel.add(gameBoardButton);
		
		// Adding the button to the array of buttons
		gameButtons[0][0] = gameBoardButton;
		
		// Creating and adding the labels that will separate the top and bottom pits.
		for (int i = 0; i < 6; i ++) {
			gameBoardLabel = new JLabel(new ImageIcon("boardMid.png"));
			gamePane.add(gameBoardLabel);
		}
		
		// Creating and adding the label and button that will display the home pit for player 1.
        gameBoardLabel = new JLabel(new ImageIcon("boardMidRight.png"));
        gameBoardLabel.setLayout(new BorderLayout());
        
		gameBoardButton = new JButton("0"); // This button cannot be interacted with, but to maintain consistency with the other numbers it is made as a button.
		gameBoardButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		gameBoardButton.setPreferredSize(new Dimension(gameBoardLabel.getHeight(), gameBoardLabel.getWidth()));
		gameBoardButton.setOpaque(false);
		gameBoardButton.setContentAreaFilled(false);
		gameBoardButton.setBorderPainted(false);
		
		gamePane.add(gameBoardLabel);
		gameBoardLabel.add(gameBoardButton);
		
		// Adding the button to the array of buttons.
		gameButtons[1][0] = gameBoardButton;
		
		// Creating and adding the label that shows the bottom left of the board.
        gameBoardLabel = new JLabel(new ImageIcon("boardBotLeft.png"));
		gamePane.add(gameBoardLabel);
		
		// Creating, formatting, and adding the bottom 6 pits and buttons to interact with them.
		for (int col = 1; col < 7; col ++) {
	        gameBoardLabel = new JLabel(new ImageIcon("boardBot.png"));
	        gameBoardLabel.setLayout(new BorderLayout());
	        
			gameBoardButton = new JButton(defaultStoneString);
			gameBoardButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 47, 0));
			gameBoardButton.setActionCommand("1 " + col);
			gameBoardButton.addActionListener(this);
			gameBoardButton.setPreferredSize(new Dimension(gameBoardLabel.getHeight(), gameBoardLabel.getWidth()));
			gameBoardButton.setOpaque(false);
			gameBoardButton.setContentAreaFilled(false);
			gameBoardButton.setBorderPainted(false);
			
			gamePane.add(gameBoardLabel);
			gameBoardLabel.add(gameBoardButton);
			
			// Adding the button to the array of buttons.
			gameButtons[1][col] = gameBoardButton;
		}
		
		// Creating and adding the label that will show the bottom right corner of the game board.
        gameBoardLabel = new JLabel(new ImageIcon("boardBotRight.png"));
		gamePane.add(gameBoardLabel);
		
		// Creating, formatting, and adding the button that will show any error messages.
		errorMessageButton = new JButton(""); // The actual error messages will be added in other methods.
		errorMessageButton.setActionCommand("Dismiss Error");
		errorMessageButton.addActionListener(this);
		errorMessageButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		errorMessageButton.setPreferredSize(new Dimension(gameBoardLabel.getHeight(), gameBoardLabel.getWidth()));
		errorMessageButton.setOpaque(false);
		errorMessageButton.setContentAreaFilled(false);
		errorMessageButton.setBorderPainted(false);
		
		gamePane.add(errorMessageButton);
		
		// Creating and adding an empty label to make spacing look better.
		emptyLabel = new JLabel("");
		gamePane.add(emptyLabel);
		
		// Creating and adding a label that will show the last move made by either side.
		lastMoveLabel = new JLabel(""); // The actual messages will be added in other methods.
		gamePane.add(lastMoveLabel);
		
		// Creating and adding an empty label to make spacing look better.
		emptyLabel = new JLabel("");		
		gamePane.add(emptyLabel);
		
		// Creating and adding a label that will show which player's turn it is.
		currentPlayerLabel = new JLabel("Player 1's Turn"); // The player number will be changed in another method.
		gamePane.add(currentPlayerLabel);
		
		// Creating and adding a label that will if any special ending pit was landed on.
		specialEndTurnLabel = new JLabel(""); // The actual messages will be added in other methods.
		gamePane.add(specialEndTurnLabel);
		
		// Creating and formatting a pane so that two buttons can be placed in one cell of the GridLayout pane.
		navFinalButtonsPane = new JPanel();
		navFinalButtonsPane.setLayout(new BoxLayout(navFinalButtonsPane, BoxLayout.PAGE_AXIS));
		navFinalButtonsPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Creating and formatting a button that will allow the user to see each move that the AI makes.
		if (numPlayers == 1) { // This button is only needed when there is only one player.
			nextMoveButton = new JButton("Next Move");
			nextMoveButton.setActionCommand("Next Move");
			nextMoveButton.addActionListener(this);
			nextMoveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
			nextMoveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		}
		
		// Creating and formatting a button that will allow the user to quit the game.
		quitFinalButton = new JButton("Quit");
		quitFinalButton.setActionCommand("Quit");
		quitFinalButton.addActionListener(this);
		quitFinalButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		quitFinalButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		// Adding the buttons if necessary, as well as padding to make it look nicer
		if (numPlayers == 1) {
			navFinalButtonsPane.add(Box.createRigidArea(new Dimension(0, 30)));
			navFinalButtonsPane.add(nextMoveButton);
			navFinalButtonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
			navFinalButtonsPane.add(quitFinalButton);
			nextMoveButton.setVisible(false);
		} else {
			navFinalButtonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
			navFinalButtonsPane.add(quitFinalButton);
		}
		
		// Adding the pane to the actual game pane.
		gamePane.add(navFinalButtonsPane);

		// Adding the pane to the screen.
		frame.setContentPane(gamePane);
		
		// Packing the frame and making it visible.
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * The changeErrorMessage method will change the error message label to the tell the user what they're doing wrong.
	 * pre: none
	 * post: The error message button text will be changed to the correct error message.
	 */
	public void changeErrorMessage(String error) {
		if (error.equals("Wrong Side")) {
			// If number of players is 2, based on the player num the user can select buttons on both the top and bottom side.
			if (numPlayers == 2) {
				if (playerNum == 0) {
					errorMessageButton.setText("<html>Please only select a tile <br/>on the top side of the board.<br/>Click to dismiss.<html>");
				} else {
					errorMessageButton.setText("<html>Please only select a tile <br/>on the bottom side of the board.<br/>Click to dismiss.<html>");
				}
			// If the number of players is 1, the only side the user can select from is the bottom.
			} else if (numPlayers == 1) {
				errorMessageButton.setText("<html>Please only select a tile <br/>on the bottom side of the board.<br/>Click to dismiss.<html>");
			}
		} else if (error.equals("Invalid Tile")) {
			errorMessageButton.setText("<html>Please only select a tile <br/> with stones in it.<br/>Click to dismiss.<html>");
		} else if (error.equals("Still AI Turn")) {
			errorMessageButton.setText("<html>Please finish seeing the<br/>AI's moves by clicking on<br/>the Next Move button.<br/>Click to dismiss.<html>");
		}
	}
	
	/**
	 * The changePlayerLabel method will change the label displaying the status of each round.
	 * pre: none
	 * post: The text in the current player label is changed.
	 */
	public void changePlayerLabel(String change) {
		if (change.equals("Turn Change")) {
			if (playerNum == 0) {
				currentPlayerLabel.setText("Player 2's Turn");
			} else {
				currentPlayerLabel.setText("Player 1's Turn");
			}
		} else if (change.equals("Winner")) {
			if (winningPlayer == 0) {
				currentPlayerLabel.setText("Player 2 Wins!");
			} else {
				currentPlayerLabel.setText("Player 1 Wins!");
			}
		}

	}
	
	/**
	 * The changeEndTurnLabel will change the label telling the user if there was any special end turn.
	 * pre: none
	 * post: The end turn label is changed to show what the proper change was.
	 * @param type
	 */
	public void changeEndTurnLabel(String type) {
		if (type.equals("Turn Reset")) {
			if (playerNum == 0) {
				specialEndTurnLabel.setText("<html>Since the last stone landed on <b/>player 2's home pit, player 2 gets another turn!<html>");
			} else {
				specialEndTurnLabel.setText("<html>Since the last stone landed on <b/>player 1's home pit, player 1 gets another turn!<html>");
			}
		} else if (type.equals("Opposite Taken")) {
			if (playerNum == 0) {
				specialEndTurnLabel.setText("<html>Since player 2 landed on a pit without any stones, <b/>player 2 steals all stones from the opposite tile!<html>");
			} else {
				specialEndTurnLabel.setText("<html>Since player 1 landed on a pit without any stones, <b/>player 1 steals all stones from the opposite tile!<html>");
			}
		}
	}
	
	/**
	 * The displayLastMove method will change the label telling the user what the last move was.
	 * pre: none
	 * post: The label telling the user what the last move was will update to show the latest move.
	 */
	public void displayLastMove(int col) {
		if (playerNum == 1) {
			lastMoveLabel.setText("<html>Last Move:<br/>Player 1 moved the <br/stones on column " + col + "<html>");
		} else if (playerNum == 0 && numPlayers == 1) {
			lastMoveLabel.setText("<html>Last Move:<br/>AI moved <br/>the stones on column " + col +"<html>");
		} else {
			lastMoveLabel.setText("<html>Last Move:<br/>Player 2 moved <br/>the stones on column " + col + "<html>");
		}	
	}
	
	/**
	 * The concludeGame method will finish the game after there are no more possible moves.
	 * pre: none
	 * post: The Stones game is complete and a winner is announced.
	 */
	public void concludeGame(int emptySide) {
		game.endGame(emptySide);
		gameBoard = game.returnGameBoard();
		
		for (int row = 0; row < 2; row ++) {
			for (int column = 0; column < 7; column ++) {
				gameButtons[row][column].setText(Integer.toString(gameBoard[row][column]));
			}
			
			if (gameBoard[0][0] > gameBoard[1][0]) {
				winningPlayer = 0;
			} else {
				winningPlayer = 1;
			}
		
		changePlayerLabel("Winner");
		}
	}
	
	/**
	 * The aiPlaysTurn method will let the AI play it's turn.
	 * pre: none
	 * post: The AI moves stones on the gameBoard.
	 */
	public void aiPlaysTurn(int col) {
		// Initializing Variables.
		int turnOver = 0;
		
		// Checking to see if the game is over.
		if (game.checkTotalRemaining() != 1) {
			System.out.println(col);
			// Distributing the stones and seeing what type of end turn it is.
			turnOver = game.distributeStones(col, 0);
			
			// Updating the gameboard shown on screen.
			gameBoard = game.returnGameBoard();
			
			for (int row = 0; row < 2; row ++) {
				for (int column = 0; column < 7; column ++) {
					gameButtons[row][column].setText(Integer.toString(gameBoard[row][column]));
				}
			}
			
			// Checking what type of end turn it was.
			if (turnOver == 1) { // AI turn is over.
				if (game.checkTotalRemaining() == 2) {
					concludeGame(2);
				} else {
					specialEndTurnLabel.setText("");
					displayLastMove(col);
					playerNum = 1;
					changePlayerLabel("Turn Change");
				}
			} else if (turnOver == 0) { // AI Turn continues.
				if (game.checkTotalRemaining() == 2) {
					concludeGame(2);
				} else {
					changeEndTurnLabel("Turn Reset");
					displayLastMove(col);
					nextMoveButton.setVisible(true);
				}
			} else if (turnOver == 2) { // AI Turn is over, but took opposite stones.
				changeEndTurnLabel("Opposite Taken");
				displayLastMove(col);
				playerNum = 1;
				changePlayerLabel("Turn Change");
			}
		} else {
			concludeGame(1);
		}	
	}
	
	/**
	 * The actionPerformed method will perform all the actions linked to any interactable component.
	 * pre: none
	 * post: An action is completed.
	 */
	public void actionPerformed(ActionEvent event) {
		// Initializing variables.
		String eventName = "";
		String numStartingStones = "";
		String numPlayersString = "";
		int turnOver = 0;
		
		// Checking what type of action was committed.
		if (event.getSource().equals(numStartingStonesBox)) {
			JComboBox numStartingStonesCB = (JComboBox)event.getSource();
			numStartingStones = (String)numStartingStonesBox.getSelectedItem();
		} else if (event.getSource().equals(numPlayersBox)) {
			JComboBox numPlayersCB = (JComboBox)event.getSource();
			numPlayersString = (String)numPlayersBox.getSelectedItem();
		} else {
			eventName = event.getActionCommand();
		}
		
		// Checking which event is triggered.
		if (eventName.equals("Quit")) { // Quits the window.
			System.exit(0);
		} else if (eventName.equals("Rules")) { // Hides the main menu and creates and shows the rules panel.
			mainMenuPane.setVisible(false);
			rulesScreen();
		} else if (eventName.equals("Return to Main Menu")) { // Removes the rule panel from the frame and brings the main menu pane back.
			frame.getContentPane().removeAll();
			mainMenuPane.setVisible(true);
			frame.add(mainMenuPane);
			frame.pack();
		} else if (eventName.equals("Start Main Menu")) { // Hides the main menu and creates and shows the game options pane.
			mainMenuPane.setVisible(false);
			optionsScreen();
		} else if (eventName.equals("Confirm Options")) { // Hides the options pane and creates and shows the game screen.
			optionsPane.setVisible(false);
			gameScreen();
		} else if (numStartingStones == "Two") { // Setting the default number of stones to 2.
			defaultStoneNum = 2;
		} else if (numStartingStones == "Three") { // Setting the default number of stones to 3.
			defaultStoneNum = 3;
		} else if (numStartingStones == "Four") { // Setting the default number of stones to 4.
			defaultStoneNum = 4;
		} else if (numStartingStones == "Five") { // Setting the default number of stones to 5.
			defaultStoneNum = 5;
		} else if (numPlayersString == "One") { // Setting the number of players to 1.
			numPlayers = 1;
		} else if (numPlayersString == "Two") { // Setting the number of players to 2.
			numPlayers = 2;
		} else if (eventName.equals("Dismiss Error")) { // Clearing the text in the error label.
			errorMessageButton.setText("");
		} else if (eventName.equals("Restart")) { // Bringing the user back to the game options screen to restart the game.
			frame.getContentPane().removeAll();
			optionsScreen();
		} else if (eventName.equals("Next Move")) {
			aiPlaysTurn(bot.chooseColumn(gameBoard));
			nextMoveButton.setVisible(false);
		}
		
		// Checking whether or not an AI is playing.
		if (numPlayers == 2) {
			// Checks for which button is pressed.
			for (int col = 1; col < 7; col ++) {
				if (eventName.equals("0 " + col)) {
					// Checks for which player is supposed to be going.
					if (playerNum == 0) {
						// Checks if any moves can be made
						if (game.checkTotalRemaining() != 1) {
							// Checks if the pit the user selected is valid
							if (game.checkPitValid(col, 0) == true) {
								// Moving the stones and checking what type of tile it landed on.
								turnOver = game.distributeStones(col, 0);
								
								// Updates the game board shown on the screen.
								gameBoard = game.returnGameBoard();
								
								for (int row = 0; row < 2; row ++) {
									for (int column = 0; column < 7; column ++) {
										gameButtons[row][column].setText(Integer.toString(gameBoard[row][column]));
									}
								}
								
								// Checks if the player's turn is over, and does the appropriate action
								if (turnOver == 1) {
									if (game.checkTotalRemaining() == 2) { // Checks if the other player can make any moves
										concludeGame(2);
									} else {
										specialEndTurnLabel.setText("");
										displayLastMove(col);
										playerNum = 1;
										changePlayerLabel("Turn Change");
									}
								} else if (turnOver == 0) {
									if (game.checkTotalRemaining() == 2) {
										concludeGame(2);
									} else {
										changeEndTurnLabel("Turn Reset");
										displayLastMove(col);
									}
								} else if (turnOver == 2) {
									displayLastMove(col);
									changeEndTurnLabel("Opposite Taken");
									playerNum = 1;
									changePlayerLabel("Turn Change");
								}
							} else {
								changeErrorMessage("Invalid Tile");
							}
						} else {
							concludeGame(1);
						}
					} else {
						changeErrorMessage("Wrong Side");
					}
				// Checks for which button is pressed.
				} else if (eventName.equals("1 " + col)) {
					// Checks for which player is supposed to be going.
					if (playerNum == 1) {
						// Checks if any moves can be made
						if (game.checkTotalRemaining() != 2) {
							// Checks if the pit the user selected is valid
							if (game.checkPitValid(col, 1) == true) {
								// Moving the stones and checking what type of tile it landed on.
								turnOver = game.distributeStones(col, 1);
								
								// Updates the game board shown on the screen.
								gameBoard = game.returnGameBoard();
								
								for (int row = 0; row < 2; row ++) {
									for (int column = 0; column < 7; column ++) {
										gameButtons[row][column].setText(Integer.toString(gameBoard[row][column]));
									}
								}
								
								// Checks if the player's turn is over, and does the appropriate action
								if (turnOver == 1) {
									if (game.checkTotalRemaining() == 1) {
										concludeGame(1);
									} else {
										specialEndTurnLabel.setText("");
										displayLastMove(col);
										playerNum = 0;
										changePlayerLabel("Turn Change");
									}
								} else if (turnOver == 0) {
									if (game.checkTotalRemaining() == 1) {
										concludeGame(1);
									} else {
										changeEndTurnLabel("Turn Reset");
										displayLastMove(col);
									}
								} else if (turnOver == 2) {
									changeEndTurnLabel("Opposite Taken");
									displayLastMove(col);
									playerNum = 0;
									changePlayerLabel("Turn Change");
								}
							} else {
								changeErrorMessage("Invalid Tile");
							}
						} else {
							concludeGame(2);
						}
		
					} else {
						changeErrorMessage("Wrong Side");
					}
					
				} else if (eventName.equals("Wrong Side" + col)) {
					changeErrorMessage("Wrong Side");
				}
			}
		} else if (numPlayers == 1) {
			for (int col = 1; col < 7; col ++) {
				// Checks to find what button was pressed.
				if (eventName.equals("1 " + col)) {
					if (playerNum == 1) {
						// Checks if any moves can be made
						if (game.checkTotalRemaining() != 2) {
							// Check if the pit the user selected is valid.
							if (game.checkPitValid(col, 1) == true) {
								/// Moving the stones and checking what type of tile it landed on.
								turnOver = game.distributeStones(col, 1);
								
								// Updates the game board shown on the screen.
								gameBoard = game.returnGameBoard();
								
								for (int row = 0; row < 2; row ++) {
									for (int column = 0; column < 7; column ++) {
										gameButtons[row][column].setText(Integer.toString(gameBoard[row][column]));
									}
								}
								
								// Checks if the player's turn is over, and does the appropriate action
								if (turnOver == 1) {
									if (game.checkTotalRemaining() == 1) {
										concludeGame(1);
									} else {
										specialEndTurnLabel.setText("");
										displayLastMove(col);
										playerNum = 0;
										aiPlaysTurn(bot.chooseColumn(gameBoard));
									}
								} else if (turnOver == 0) {
									if (game.checkTotalRemaining() == 1) {
										concludeGame(1);
									} else {
										changeEndTurnLabel("Turn Reset");
										displayLastMove(col);
									}
								} else if (turnOver == 2) {
									changeEndTurnLabel("Opposite Taken");
									displayLastMove(col);
									playerNum = 0;
									changePlayerLabel("Turn Change");
									aiPlaysTurn(bot.chooseColumn(gameBoard));
								}
							} else {
								changeErrorMessage("Invalid Tile");
							}
						} else {
							concludeGame(2);
						}
					} else {
						changeErrorMessage("Still AI Turn");
					}
				} else if (eventName.equals("Wrong Side" + col)) {
					changeErrorMessage("Wrong Side");
				}
			}
		}
					
	}
	
	/**
	 * The runGUI method will run the gui and make the frame look nice.
	 * pre: none
	 * post: The GUI is created.
	 */
	public static void runGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		StonesGUI gui = new StonesGUI();
	}
	
	public static void main(String[] args) {
		// 
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}

}
