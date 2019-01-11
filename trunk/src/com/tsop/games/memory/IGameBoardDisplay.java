package com.tsop.games.memory;

import com.tsop.android.memory.library.Sounds;

/// <summary>
/// Interface class to draw the gameboard to the given graphics object
/// </summary>
public interface IGameBoardDisplay
{

	/// <summary>
	/// Method to draw the game board to the given graphics object at the given location
	/// </summary>
	void DrawGameBoard(Card[][] cardArray);

	/// <summary>
	/// Method to redraw a single card on the game board
	/// </summary>
	void UpdateGameBoard(int col, int row, boolean animate);
	
	/*
	 * Play the specified sound
	 */
	void PlaySound(Sounds sound);
	void PlaySound(Sounds sound, int waitMs);
	
	/*
	 * Show the GameOverPopup
	 */
	void openGameOverDialog(String text);
	
	/*
	 * Show the Match Image
	 */
	void showMatchResult(int imageID);
}

