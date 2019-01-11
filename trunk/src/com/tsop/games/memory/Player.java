package com.tsop.games.memory;

import com.tsop.android.memory.library.Sounds;

/// <summary>
/// Base class for all player types
/// </summary>
public abstract class Player
{
	/// <summary>
	/// Default Constructor
	/// </summary>
	public Player()
	{
		m_nScore = 0;
		m_ID = 0;
		this.setName("Default");
	}

	/// <summary>
	/// Constructor that takes as a parameter the id number of the player
	/// </summary>
	/// <param name="number">
	/// the unique numeric id of the player</param>
	public Player(int number)
	{
		m_nScore = 0;
		m_ID = number;
		this.setName(null);
	}

	/// <summary>
	/// // field to keep track of the player's score
	/// </summary>
	private int m_nScore;

	/// <summary>
	/// Property to get the player's score
	/// </summary>
	public int getScore()
	{
		return m_nScore;
	}
	
	public void setScore(int score)
	{
			m_nScore = score;
	}
	
	public void addToScore(int add)
	{
		m_nScore += add;
	}

	/// <summary>
	/// ID for the player
	/// </summary>
	private int m_ID;

	/// <summary>
	/// Property ID for the player
	/// </summary>
	public int getID()
	{
		return m_ID;
	}
	public void	setID(int value)
	{
		if (value >= 0)
			m_ID = value;
		else
			m_ID = 0;
	}


	/// <summary>
	/// name of the player
	/// </summary>
	private String m_name;

	/// <summary>
	/// Property  Name of the player
	/// </summary>
	public String getName()
	{
		return m_name;
	}
	public void setName(String value)
	{
		if (value != "" && value != null)
			m_name = value;
		else
			m_name = "Player " + m_ID;
	}

	/// <summary>
	/// field that contains the name of the .wav file to play when the player wins
	/// </summary>
	protected Sounds winFileName;

	/// <summary>
	/// Property that contains the name of the .wav file to play when the player wins
	/// </summary>
	public Sounds getWinFileName()
	{
		return winFileName;
	}

	/// <summary>
	/// field that contains the name of the .wav file to play when the player matches
	/// </summary>
	protected Sounds matchFileName;

	/// <summary>
	/// Property that contains the name of the .wav file to play when the player matches
	/// </summary>
	public Sounds getMatchFileName()
	{
		return matchFileName;
	}

	/// <summary>
	/// Method to return whether or not the object accepts user inputs
	/// </summary>
	public boolean AcceptInput()
	{
		return false;
	}

	/// <summary>
	/// returns the move the player makes
	/// </summary>
	public abstract Position GetMove();

	/// <summary>
	/// Virtual method to remember a card's value, if that abiity exists in the derived class
	/// </summary>
	public void RememberCard(Card card, Position location, boolean isFlipped)
	{
		return;	// the derived class has the option of overriding this
	}

	/// <summary>
	/// the ability of the computer to remember cards (0 = none, 5 = perfect)
	/// </summary>
	protected int m_IQ;

	/// <summary>
	/// Integer describing how accurately the computer remembers cards, 5 = always, 0 = never
	/// </summary>
	public int getIQ()
	{
		return m_IQ;
	}
	public void setIQ(int value)
	{
		if (value >= 0 && value <= 5)
			m_IQ = value;
		else
			m_IQ = 3;
	}

	/// <summary>
	/// Virtual Function to display the player's memory as a string array
	/// To be overriden by the CComputer derived class
	/// </summary>
	public String ShowStringArray()
	{
		return "Only the computer's memory can be displayed";
	}


}

