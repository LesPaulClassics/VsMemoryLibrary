package com.tsop.games.memory;
import android.app.Activity;
/// <summary>
/// Class to monitor the status of the game and update the GUI when necessary
/// </summary>
public class StatusMonitor
{
	/// <summary>
	/// Flags that enable the controls
	/// </summary>
	public final int MENU_DIFFICULTY = 1;

	/// <summary>
	/// field that monitors when first move has been made
	/// </summary>
	private boolean gameStarted = false;

	/// <summary>
	/// Field that contains a pointer to the CMemoryForm object
	/// </summary>
	private Activity memoryForm;

	/// <summary>
	/// Field that contains a pointer to the CMemoryGame object
	/// </summary>
	private MemoryGame memoryGame;

	/// <summary>
	/// Constructor
	/// </summary>
	/// <param name="passedForm">
	/// The CMemoryForm object that this object monitors
	/// </param>
	/// <param name="passedGame">
	/// The CMemoryGame object that this object monitors</param>
	public StatusMonitor(Activity passedForm, MemoryGame passedGame)
	{
		memoryForm = passedForm;
		memoryGame = passedGame;
	}

	/// <summary>
	/// called to update the status
	/// </summary>
	public void Update()
	{
		// end the game if all cards have been turned over
		if (memoryGame.getGameBoard().getCardsTurnedOver() >= GameBoard.TOTAL_CARDS)
		{		
			// TODO
//			if (memoryGame.getWinner() == 0)
//				memoryForm.UpdateStatus(-1, "Game Over: Player 1 Wins!");
//			else
//				if (memoryGame.getWinner() == 1)
//					memoryForm.UpdateStatus(-1, "Game Over: Player 2 Wins!");
//				else
//					if (memoryGame.getWinner() == -1)
//						memoryForm.UpdateStatus(-1, "Game Over: Tie!");
			return;
		}
		
		// don't do anything if the game hasn't started
		if (memoryGame.getWhoseTurn() == null)
			return;

		// Update the controls
		int flags = 0;

		if (!gameStarted)	// if start flag has not been set to true yet
			// check if a card has been turned over
			if (memoryGame.getGameBoard().getCardsTurnedOver() > 0)
				// if so, toggle the flag
				gameStarted = true;
		
		// if first move has not yet been made, but the game is in play mode
		// and player 2 is the computer
		if (!gameStarted && !memoryGame.getGameOver() && (memoryGame.getPlayerArray()[1] != null) && !memoryGame.getPlayerArray()[1].AcceptInput())	
			flags |= MENU_DIFFICULTY;	// enable difficulty menu
		else
			flags &= ~MENU_DIFFICULTY;	// disable difficulty menu

		// TODO
//		memoryForm.UpdateControls(flags);

		// Update the player's turn indicators
		if (memoryGame.getWhoseTurn() == memoryGame.getPlayerArray()[0])
		{
			// TODO
			//			if ((flags & MENU_DIFFICULTY) != 0)	
//				memoryForm.UpdateStatus(1, "Player One's Turn - Select Difficulty Level");
//			else
//				memoryForm.UpdateStatus(1,"Player One's turn");
		}
//		else
//			memoryForm.UpdateStatus(2,"Player Two's turn");

	}

	/// <summary>
	/// Resets the Status Monitor when a new game begins
	/// </summary>
	public void Reset()
	{
		gameStarted = false;	// reset for next game
	}


}

