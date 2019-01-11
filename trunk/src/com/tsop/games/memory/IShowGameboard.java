package com.tsop.games.memory;
/// <summary>
/// Interface class to draw the gameboard to the given graphics object
/// </summary>
public interface IShowGameboard
{

	/// <summary>
	/// Method to draw the game board to the given graphics object at the given location
	/// </summary>
	void DrawGameBoard(Card[][] cardArray);

	/// <summary>
	/// Method to redraw a single card on the game board
	/// </summary>
	void UpdateGameBoard(Card card);

}

