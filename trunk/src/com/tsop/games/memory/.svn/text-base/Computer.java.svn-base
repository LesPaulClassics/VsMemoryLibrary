package com.tsop.games.memory;

import java.util.Random;

import com.tsop.android.memory.library.Sounds;

/// <summary>
/// Player that is controlled by the computer
/// </summary>
public class Computer extends Player
{
	public Computer(int columns, int rows)
	{
		m_IQ = 3;	// default (average) intelligence of computer

		m_memoryArray = new int[columns][rows];

		// initialize the memory to unknown values
		for (short i = 0; i < columns; i++)
			for (short j = 0; j < rows; j++)
				m_memoryArray[i][j] = -1;

		m_rows = rows;
		m_columns = columns;
		m_lastMatch = new Match(new Position(-1, -1), new Position(-1, -1), true);
		m_lastMove = new Position(-1, -1);

		winFileName = Sounds.Player2Win;
		matchFileName = Sounds.Player2Match;
	}

	/// <summary>
	/// Overridden method that indicates that this inherited object does not accept user input
	/// </summary>
	/// <returns>
	/// false
	/// </returns>
	@Override
	public boolean AcceptInput()
	{
		return false;
	}

	/// <summary>
	/// the number of columns on the board
	/// </summary>
	private int m_columns;

	/// <summary>
	/// the number of rows on the board
	/// </summary>
	private int m_rows;

	/// <summary>
	/// remember the results of the LookForMatch method
	/// </summary>
	private Match m_lastMatch;
	
	/*
	 * Keep track of the last move selected
	 */
	private Position m_lastMove;

	/// <summary>
	/// Performs the logic of the computer when selecting which pair of cards to turn over
	/// </summary>
	@Override
	public Position GetMove()
	{			
		int c, r;	// column and row iterators

		// if no matches were previously found, look for a match now
		if (m_lastMatch.location1.column == -1)
			m_lastMatch = LookForMatches();

		// if no match has been found, select a card at pseudo-random
		if (m_lastMatch.location2.column == -1)
		{
			Random random = new Random();
			c = random.nextInt(m_columns);
			r = random.nextInt(m_rows);
			Position randomMove = new Position(c,r);
//			System.out.println("Random Move = " + Integer.toString(c) + ", " + Integer.toString(r));
//			System.out.println("m_memoryArray[" + Integer.toString(c) + "," + Integer.toString(r) + "] = " + Integer.toString(m_memoryArray[randomMove.column][randomMove.row]));
			if (m_memoryArray[randomMove.column][randomMove.row] == 0)	// if card has already been turned over
			{
//				System.out.println("Looking for non-random move");
				for (int col = 0; col < m_columns; col++)				// select the next card that is not
					for (int row = 0; row < m_rows; row++)
					{
//						System.out.println("m_memoryArray[" + Integer.toString(col) + "," + Integer.toString(row) + "] = " + Integer.toString(m_memoryArray[col][row]));
						if (m_memoryArray[col][row] != 0 && !m_lastMove.equals(new Position(col, row)))
						{
//							System.out.println("Non-Random Move = " + Integer.toString(col) + ", " + Integer.toString(row));
							m_lastMove = new Position(col, row);
							return m_lastMove;
						}
					}
			}
			m_lastMove = randomMove;
			return randomMove;		// if this point is reached, all the cards are already turned over. Game Over.
		}
		else
		{
			// a match was found and this is the first move in the turn
			if (m_lastMatch.firstMove == true)
			{
				m_lastMatch.firstMove = false;
				m_lastMove = m_lastMatch.location1;
				return m_lastMatch.location1;
			}
			else
			// a match was found and this is the second move in the turn
			{
				// record the second move
				Position secondMove = new Position(m_lastMatch.location2.column, m_lastMatch.location2.row);
				// clear out the lastMatch field 
				m_lastMatch = new Match(new Position(-1, -1), new Position(-1, -1), true);
				m_lastMove = secondMove;
				return secondMove;
			}
		}

	}

	/// <summary>
	/// Remember the value of the card just shown, adjust for intelligence level (m_IQ)
	/// </summary>
	/// <param name="card">
	/// A card object containing the card that was just flipped
	/// </param>
	/// <param name="isFlipped">
	/// a boolean indicating whether or not the card is currently flipped (matched)
	/// and therefore out of play
	/// </param>
	/// <param name="location">
	/// A Position object containing the row and column of the card
	/// </param>
	@Override
	public void RememberCard(Card card, Position location, boolean isFlipped)
	{
		Random random = new Random();

		// calculate the number of unique cards
		int numberOfCards = m_columns * m_rows / 2;

		// generate a random number, 
		if ((random.nextInt(5) + 1) <= m_IQ)	// if  the random number is less than the IQ,
		{								// correctly remember the card
			m_memoryArray[location.column][location.row] = (isFlipped) ? 0 : (int) card.getCardValue();
			System.out.println("Correctly Remembering Card Value " + Integer.toString(m_memoryArray[location.column][location.row]) + " at Position " + location.toString());
		}
		else							// otherwise incorrectly remember the card
		{
			m_memoryArray[location.column][location.row] = (isFlipped) ? 0 : random.nextInt(numberOfCards) + 1;
			System.out.println("Incorrectly Remembering Card Value " + Integer.toString(m_memoryArray[location.column][location.row]) + " at Position " + location.toString());
		}
	}

	/// <summary>
	/// an array of integer values that the computer memorizes the location of each card (-1 if card value unknown)
	/// </summary>
	private int[][] m_memoryArray;


	/// <summary>
	/// // diagnostic tool to show the current memory values
	/// </summary>
	@Override
	public String ShowStringArray()
	{
		String output = "";
		for (int i = 0; i < m_columns; i++)
		{
			for (int j = 0; j < m_rows; j++)
			{
				output += m_memoryArray[i][j] + "\t";
			}
			output += "\n";
		}

		return output;
	}

	/// <summary>
	/// Function that scans memory to see if any matches are known
	/// </summary>
	private Match LookForMatches()
	{
		// create a default Match object containing all -1's
		Match match = new Match(new Position(-1, -1), new Position(-1, -1), true);

		// step through every pair of cards in memory comparing values
		for (int c1 = 0; c1 < m_columns; c1++)
			for (int r1 = 0; r1 < m_rows; r1++)
				for (int c2 = 0; c2 < m_columns; c2++)
					for (int r2 = 0; r2 < m_rows; r2++)
					{
						int first = m_memoryArray[c1][r1];
						int second = m_memoryArray[c2][r2];
						if (m_memoryArray[c1][r1] == m_memoryArray[c2][r2]	// the two card values are equal
							&& m_memoryArray[c1][r1] != 0					// they are not flipped
							&& m_memoryArray[c1][r1] != -1					// they are not unknown
							&& !(c1 == c2 && r1 == r2))						// they are not the same card
						{
							// a match was found (or so the computer thinks)
							match = new Match(new Position(c1, r1), new Position(c2, r2), true);
							return match;
						}
					}

		return match;
	}


	

}

