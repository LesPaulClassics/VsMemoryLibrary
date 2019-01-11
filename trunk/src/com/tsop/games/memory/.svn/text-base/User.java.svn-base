package com.tsop.games.memory;

import com.tsop.android.memory.library.Sounds;

/// <summary>
/// Derived Class that defines a human player
/// </summary>
public class User extends Player
{
	/// <summary>
	/// Default Constructor
	/// </summary>
	public User()
	{
		winFileName = Sounds.Player1Win;
		matchFileName = Sounds.Player1Match;
	}

	/// <summary>
	/// Method that indicates that this object accepts user input
	/// </summary>
	/// <returns>
	/// returns true
	/// </returns>
	@Override
	public boolean AcceptInput()
	{
		return true;
	}

	/// <summary>
	/// returns move
	/// </summary>
	@Override
	public Position GetMove()
	{
		return new Position(-1, -1);
	}
}

