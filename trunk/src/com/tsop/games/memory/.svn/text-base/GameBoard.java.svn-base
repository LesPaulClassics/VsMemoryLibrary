package com.tsop.games.memory;

import java.util.Random;

import com.tsop.android.memory.library.Sounds;

/// <summary>
/// Summary description for CGameBoard.
/// </summary>
public class GameBoard
{
	/// <summary>
	/// Constants that define the number of cards on the game board
	/// </summary>
	public static final int NUM_ROWS = 8;
	public static final int NUM_COLUMNS = 6;
	public static final int TOTAL_CARDS = NUM_ROWS * NUM_COLUMNS;

	/// <summary>
	/// number of cards that have been turned over so far
	/// </summary>
	private int m_cardsTurnedOver;

	/// <summary>
	/// reference to the interface class
	/// </summary>
	private IGameBoardDisplay m_IObject;


	// default constuctor (initializes cards)
	public GameBoard(IGameBoardDisplay display)
	{
		for (short i = 0; i < NUM_COLUMNS; i++)
		{
			for (short j = 0; j < NUM_ROWS; j++)
			{
				short temp = (short) ((j + (i * NUM_ROWS)) % (TOTAL_CARDS / 2) + 1);
				m_cardArray[i][j] = new Card(temp);
			}
		}

		m_IObject = display;
	}
	
	// constuctor (initializes cards with random subset of resources)
	public GameBoard(IGameBoardDisplay display, short[] valueArray)
	{
		initGameBoard(valueArray);
		m_IObject = display;
	}
	
	public void initGameBoard(short[] valueArray)
	{
		for (short i = 0; i < NUM_COLUMNS; i++)
		{
			for (short j = 0; j < NUM_ROWS; j++)
			{
				short temp = (short) ((j + (i * NUM_ROWS)) % (TOTAL_CARDS / 2));
				m_cardArray[i][j] = new Card(valueArray[temp]);
			}
		}		
	}
	
	public void PlaySound(Sounds sound)
	{
		m_IObject.PlaySound(sound);
	}

	public void PlaySound(Sounds sound, int waitMs)
	{
		m_IObject.PlaySound(sound, waitMs);
	}

	/// <summary>
	/// // game grid array of CCard objects
	/// </summary>
	private Card[][] m_cardArray = new Card[NUM_COLUMNS][NUM_ROWS];

	/// <summary>
	/// // diagnostic tool to show the current card values
	/// </summary>
	public String ShowStringArray()
	{
		String output = "";
		for (int i = 0; i < NUM_COLUMNS; i++)
		{
			for (int j = 0; j < NUM_ROWS; j++)
			{
				output += m_cardArray[i][j].getCardValue() + "\t";
			}
			output += "\n";
		}

		return output;
		
	}

	/// <summary>
	/// // method to shuffle all cards
	/// </summary>
	public void Shuffle()
	{
		Random randomNumber =new Random();
		Card temporaryValue;

		// swap each card with a random card
		for (int i = 0; i < NUM_COLUMNS; i++)
			for (int j = 0; j < NUM_ROWS; j++)
			{
				// randomly choose new location
				int newI = randomNumber.nextInt(NUM_COLUMNS);
				int newJ = randomNumber.nextInt(NUM_ROWS);

				// swap cards
				temporaryValue = m_cardArray[i][j];
				m_cardArray[i][j] = m_cardArray[newI][newJ];
				m_cardArray[i][j].setPosition(i, j);
				m_cardArray[newI][newJ] = temporaryValue;
				m_cardArray[newI][newJ].setPosition(newI, newJ);
			}
	}

	/// <summary>
	/// Property that contains the number of cards currently turned over
	/// </summary>
	public int getCardsTurnedOver()
	{
		return m_cardsTurnedOver;
	}

	/// <summary>
	/// method to turn over the card at the specified location
	/// </summary>
	public void TurnOverCard(int column, int row, boolean isFlipped, boolean animate)
	{
		if (row < 0 || row >= NUM_ROWS || column < 0 || column >= NUM_COLUMNS)
			return;

		if (isFlipped)
			m_cardsTurnedOver++;
		else if (m_cardsTurnedOver > 0)
			m_cardsTurnedOver--;

		m_cardArray[column][row].setFlipped(isFlipped);
		
		getIObject().UpdateGameBoard(column, row, animate);
	}

	/// <summary>
	/// Contains the interface object that CMemoryForm implements to draw the game board
	/// </summary>
	public IGameBoardDisplay getIObject()
	{
		return m_IObject;
	}
	public void setIObject(IGameBoardDisplay value)
	{
		if (value != null)
			m_IObject = value;
	}

	/// <summary>
	/// Draw the Game Board via the interface
	/// </summary>
	public void Draw()
	{
		if (m_IObject == null)
			return;
		m_IObject.DrawGameBoard(m_cardArray);
	}

	/// <summary>
	/// method to flip all cards over or back
	/// </summary>
	public void FlipAllCards(boolean showFace)
	{
//		System.out.println("FlipAllCards- TurnOverCard()");
		for (int i = 0; i < NUM_COLUMNS; i++)
			for (int j = 0; j < NUM_ROWS; j++)
				TurnOverCard(i, j, showFace, false);

		m_cardsTurnedOver = (showFace) ? TOTAL_CARDS : 0;
	}

	/// <summary>
	/// Contains the array of cards that represent the game board
	/// </summary>
	public Card[][] getCardArray()
	{
		return m_cardArray;
	}

	
}

