package com.tsop.games.memory;

import java.util.Date;
import java.io.Serializable;

/// <summary>
/// structure that contains the information about a single high score
/// </summary>
public class HighScore implements Serializable
{
	public GameType Type;
	public int WinnerScore;
	public int LoserScore;
	public Date Date; 
	public String Name;
	public int Diff;
	
	public HighScore()
	{
		Date = new Date();
		Name = "";
		Type = GameType.All;
	}

	/// <summary>
	/// Constructor that takes all structure members as parameters (for 1P vs Computer)
	/// </summary>
	/// <param name="type">
	/// the type of game that was played
	/// </param>
	/// <param name="winnerScore">
	/// the score of the user whose name has just been entered
	/// </param>
	/// <param name="loserScore">
	/// the opponent's score
	/// </param>
	/// <param name="name">
	/// the name of the high scorer
	/// </param>
	/// <param name="difficulty">
	/// the difficulty level (computer's IQ)
	/// </param>
	public HighScore(GameType type, int winnerScore, int loserScore, String name, int difficulty)
	{
		Type = type;
		WinnerScore = winnerScore;
		LoserScore = loserScore;
		Date = new Date(); //TODO is this right?
		Name = name;
		Diff = difficulty;
	}

	/// <summary>
	/// Constructor that takes limited parameters (as in 2P game)
	/// </summary>
	/// <param name="type">
	/// the type of game that was played
	/// </param>
	/// <param name="winnerScore">
	/// the score of the user whose name has just been entered
	/// </param>
	/// <param name="loserScore">
	/// the opponent's score
	/// </param>
	/// <param name="name">
	/// the name of the high scorer
	/// </param>
	public HighScore(GameType type, int winnerScore, int loserScore, String name)
	{
		Type = type;
		WinnerScore = winnerScore;
		LoserScore = loserScore;
		Date = new Date(); //TODO is this right?
		Name = name;
		Diff = -1;
	}

	/// <summary>
	/// Constructor that takes limited parameters (as in 1P game)
	/// </summary>
	/// <param name="type">
	/// the type of game that was played
	/// </param>
	/// <param name="winnerScore">
	/// the score of the user whose name has just been entered
	/// </param>
	/// <param name="name">
	/// the name of the high scorer
	/// </param>
	public HighScore(GameType type, int winnerScore, String name)
	{
		Type = type;
		WinnerScore = winnerScore;
		LoserScore = Integer.MIN_VALUE;
		Date = new Date(); // TODO is this right?
		Name = name;
		Diff = -1;
	}

	/// <summary>
	/// Override that returns the High Score structure as a string
	/// </summary>
	/// <returns>
	/// string representation of structure
	/// </returns>
	@Override
	public String toString()
	{
		// TODO how do I really want to do this?
//		String output = Date.toString("d");
//		output += String.Format("\t\t{0:S}\t{1:D}\t", 
//								Name.PadRight(20, ' '), WinnerScore);
//		if (Type != gameType.OnePlayer)
//			output += String.Format("\t{0:D}", LoserScore);
//		if (Type == gameType.OnePlayerVersusComputer)
//			output += String.Format("\t\t{0:D}", Diff);
//		return output;
		return "";
	}

};

