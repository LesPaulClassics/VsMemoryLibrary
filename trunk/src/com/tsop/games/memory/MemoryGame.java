package com.tsop.games.memory;

import com.tsop.android.memory.library.Sounds;

/// <summary>
/// Summary description for CMemoryGame.
/// </summary>
public class MemoryGame
{
	public MemoryGame(IGameBoardDisplay display)
	{
		m_gameBoard = new GameBoard(display);
		m_lastMove = new Position(-1, -1);
		m_lastTwoMoves = new Match(new Position(-1, -1), m_lastMove, false);
		m_gameOver = true;
		scoresDB = new HighScoresDB();
	}

	/// <summary>
	/// the game board on which the game is played
	/// </summary>
	private GameBoard m_gameBoard;

	/// <summary>
	/// The Game Board Property
	/// </summary>
	public GameBoard getGameBoard()
	{
		return m_gameBoard;
	}

	/// <summary>
	/// if 1, user has another move, if 0, his turn is over
	/// </summary>
	private int m_goAgain = 1;
	
	/// <summary>
	/// remember the card picked during the first move
	/// </summary>
	private Position m_lastMove;

	/// <summary>
	/// remember the last two cards picked (to be flipped back over)
	/// </summary>
	private Match m_lastTwoMoves;
	
	/// <summary>
	/// field which points to the CPlayer object whose turn it currently is
	/// </summary>
	private Player m_whoseTurn = null;

	/// <summary>
	/// number of points awarded for matching a card
	/// </summary>
	private final int POINTS_MATCH = 4;

	/// <summary>
	/// the number of points awarded for failing to match cards
	/// </summary>
	private final int POINTS_MISSED = -1;

	/// <summary>
	/// Property to Get or Set the CPlayer object whose turn it is
	/// </summary>
	public Player getWhoseTurn()
	{

			return m_whoseTurn;
	}
	public void setWhoseTurn(Player value)
	{
		m_whoseTurn = value;
	}


	/// <summary>
	/// Array of CPlayer objects
	/// </summary>
	private Player[] m_playerArray = new Player[2];

	/// <summary>
	/// Property to access the array of players in the game
	/// </summary>
	public Player[] getPlayerArray()
	{
		return m_playerArray;
	}
	public void setPlayerArray(Player[] value)
	{
		m_playerArray = value;
	}

	/// <summary>
	/// field that contains the winner of the game (tie = 2)
	/// </summary>
	private int m_winner;

	/// <summary>
	/// Property that contains the winner of the game (-1 = game not over, 2 = tie)
	/// </summary>
	public int getWinner()
	{
		return m_winner;
	}

	/// <summary>
	/// Property to get/set the last two moves made (for card flipping)
	/// </summary>
	public Match getLastTwoMoves()
	{

		return m_lastTwoMoves;
	}
	public void setLastTwoMoves(Match value)
	{
		m_lastTwoMoves = value;
	}


	/// <summary>
	/// bool to indicate when the game is over
	/// </summary>
	private boolean m_gameOver;

	/// <summary>
	/// Property to return game over state
	/// </summary>
	public boolean getGameOver()
	{
		return m_gameOver;
	}
	public void setGameOver(boolean value)
	{
		m_gameOver = value;
		if (value)
		{
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AnnounceWinner();
		}
	}
	
	/// <summary>
	/// Field that contains the HighScoresDB object
	/// </summary>
	private HighScoresDB scoresDB;

	/// <summary>
	/// Field that records type of game being played
	/// </summary>
	private GameType m_gameType;
	
	public GameType getGameType()
	{
		return m_gameType;
	}

	/// <summary>
	/// Property to access the high scores database
	/// </summary>
	public HighScoresDB getScoresDB()
	{
		scoresDB.LoadScores();
		return scoresDB;
	}


	/// <summary>
	/// Method to start a new game
	/// </summary>
	/// <param name="type">
	/// the type of game to start
	/// </param>
	public void StartGame(GameType type, int ComputerIQ)
	{
		// turn all the cards over
		m_gameBoard.FlipAllCards(false);

		// shuffle them thrice
		m_gameBoard.Shuffle();
		m_gameBoard.Shuffle();
		m_gameBoard.Shuffle();
		// clear all players
		m_playerArray = new Player[2];

		//add the appropriate types of players
		m_playerArray[0] = new User();
		m_playerArray[0].setName(GameConstants.PLAYER_1);

		switch (type)
		{
			case OnePlayer:
				m_playerArray[1] = null;
				break;
			case TwoPlayer:
				m_playerArray[1] = new User();
				m_playerArray[1].setName(GameConstants.PLAYER_2);
				break;
			case OnePlayerVersusComputer:
				m_playerArray[1] = new Computer(GameBoard.NUM_COLUMNS, GameBoard.NUM_ROWS);
				m_playerArray[1].setName(GameConstants.PLAYER_2);
				m_playerArray[1].setIQ(ComputerIQ);
				break;
		}

		// initialize game variables
		m_gameType = type;
		m_lastMove = new Position(-1, -1);
		m_lastTwoMoves = new Match(new Position(-1, -1), m_lastMove, false);
		m_gameOver = false;
		m_whoseTurn = m_playerArray[0];
	}

	/// <summary>
	/// tells the game what move was just made 
	/// </summary>
	public void RecordMove(Position move)
	{
		// if move is -1, -1 wait for user input
		if (move.column == -1 && move.row == -1)
			return;
		
		// validate range of array bounds
		if ((move.column < 0 || move.column >= m_gameBoard.NUM_COLUMNS) ||
				(move.row < 0 || move.row >= m_gameBoard.NUM_ROWS))
			return;

		// if card is already flipped, this isn't a move
		if (m_gameBoard.getCardArray()[move.column][move.row].getFlipped())
		{
			//System.out.println("RecordMove- RecordMove()");
			//RecordMove(getWhoseTurn().GetMove());	// TODO is this a bug?
			return;
		}

		m_gameBoard.PlaySound(Sounds.FlipCard);
				
//		System.out.println("RecordMove- TurnOverCard()");
		m_gameBoard.TurnOverCard(move.column, move.row, true, true);
//		m_gameBoard.getIObject().UpdateGameBoard(m_gameBoard.getCardArray()[move.column][move.row], move.column, move.row);

		// let the players remember the card
		for (Player player : m_playerArray)
			if (player != null)
				player.RememberCard(m_gameBoard.getCardArray()[move.column][move.row], move, false);

		// Perform the logic whether this is the first or second move
		switch (m_goAgain)
		{
			case 0:	// this was the second move
				if (m_gameBoard.getCardArray()[move.column][move.row].getCardValue() == m_gameBoard.getCardArray()[m_lastMove.column][m_lastMove.row].getCardValue())
				// a match is found: score points
				{
					getWhoseTurn().addToScore(POINTS_MATCH);
					m_goAgain = 1;

					// tell the players that the cards are now turned over
					for (Player player : m_playerArray)
						if (player != null)
						{
							player.RememberCard(m_gameBoard.getCardArray()[move.column][move.row], move, true);
							player.RememberCard(m_gameBoard.getCardArray()[m_lastMove.column][m_lastMove.row], m_lastMove, true);
						}
					
					m_gameBoard.PlaySound(Sounds.FlipCard);
					m_gameBoard.PlaySound(getWhoseTurn().getMatchFileName());
					// if this was a human, show the match image
					if (getWhoseTurn().AcceptInput())
						m_gameBoard.getIObject().showMatchResult(m_gameBoard.getCardArray()[move.column][move.row].getCardValue());
				}
				else
				// no match is found: deduct points
				{
					getWhoseTurn().addToScore(POINTS_MISSED);
					m_goAgain =1;
					TogglePlayer(); 
					m_lastTwoMoves = new Match(new Position(move.column, move.row), m_lastMove, false);

					m_gameBoard.PlaySound(Sounds.FlipCard);
					m_gameBoard.PlaySound(Sounds.Player1NoMatch);
				}
				break;

			case 1:	// this was the first move
				m_goAgain = 0;
				m_lastMove = move;
				m_gameBoard.PlaySound(Sounds.FlipCard);
				break;
		}
		return;
	}

	/// <summary>
	/// Alternates the Player in 2-Player games
	/// </summary>
	public void TogglePlayer()
	{
		if (getWhoseTurn() == m_playerArray[0] && m_playerArray[1] != null)
			setWhoseTurn(m_playerArray[1]);
		else
			setWhoseTurn(m_playerArray[0]);
	}


	/// <summary>
	/// Method to compare scores, and announce the winner of the game
	/// </summary>
	private void AnnounceWinner()
	{
		
		// if this is a one-player game, player one is the winner
		if (m_playerArray[1] == null)
		{
			m_winner = 0;
			m_gameBoard.PlaySound(getWhoseTurn().getWinFileName());
		}
		else
		{
			// determine the winner (tie = 2)
			int winner = 2;
			if (m_playerArray[0].getScore() > m_playerArray[1].getScore())
				winner = 0;
			if (m_playerArray[1].getScore() > m_playerArray[0].getScore())
				winner = 1;
			if (!getGameOver())
				winner = -1;
		
			// play the winner's win sound
			if (winner == 0 || winner == 1)
				m_gameBoard.PlaySound(m_playerArray[winner].getWinFileName());

			m_winner = winner;
		}
		
		ProcessHighScore();
		
	   StringBuilder textSbldr = new StringBuilder();
	   int score1 = Integer.MIN_VALUE, score2 = Integer.MIN_VALUE;
	   score1 = m_playerArray[0].getScore();
	   if (m_playerArray[1] != null)
		   score2 = m_playerArray[1].getScore();
	   switch (getGameType())
	   {
		   case OnePlayerVersusComputer:
			   if (score1 > score2)
				   textSbldr.append("Player 1 Wins!");
			   else if (score1 < score2)
				   textSbldr.append("Computer Wins!");
			   else if (score1 == score2)
				   textSbldr.append("Tie Game!");
			   textSbldr.append("\n\n");
			   textSbldr.append("Player 1 Score: ");
			   textSbldr.append(Integer.toString(score1));
			   textSbldr.append("\n\n");
			   textSbldr.append("Computer Score: ");
			   textSbldr.append(Integer.toString(score2));
			   textSbldr.append("\n");
			   break;
		   case TwoPlayer:
			   if (score1 > score2)
				   textSbldr.append("Player 1 Wins!");
			   else if (score1 < score2)
				   textSbldr.append("Player 2 Wins!");
			   else if (score1 == score2)
				   textSbldr.append("Tie Game!");
			   textSbldr.append("\n\n");
			   textSbldr.append("Player 1 Score: ");
			   textSbldr.append(Integer.toString(score1));
			   textSbldr.append("\n\n");
			   textSbldr.append("Player 2 Score: ");
			   textSbldr.append(Integer.toString(score2));
			   textSbldr.append("\n");
			   break;
		   case OnePlayer:
			   if (score1 > 0)
				   textSbldr.append("Good Game!");
			   else
				   textSbldr.append("Better luck next time!");
			   textSbldr.append("\n\n");
			   textSbldr.append("Player 1 Score: ");
			   textSbldr.append(Integer.toString(score1));
			   textSbldr.append("\n");
			   break;
	   }
		m_gameBoard.getIObject().openGameOverDialog(textSbldr.toString());

	}

	/// <summary>
	/// Method to process recording of High Scores
	/// </summary>
	/// <param name="type">
	/// The type of game that was just played</param>
	public void ProcessHighScore()
	{
		// load the high scores database
		scoresDB.LoadScores();

		// a temporary high score structure to test
		HighScore hs = new HighScore(), hs2 = new HighScore();

		switch (m_gameType)
		{
			case OnePlayer:
				hs = new HighScore(m_gameType, m_playerArray[0].getScore(), "<none>");
				break;
			case OnePlayerVersusComputer:
				hs = new HighScore(m_gameType, m_playerArray[0].getScore(),
									m_playerArray[1].getScore(), "<none>", m_playerArray[1].getIQ());
				break;
			case TwoPlayer:
				hs = new HighScore(m_gameType, m_playerArray[0].getScore(), m_playerArray[1].getScore(), "<none>", -1);
				hs2 = new HighScore(m_gameType, m_playerArray[1].getScore(), m_playerArray[0].getScore(), "<none>", -1);
				break;
		}

		// test the score to see if its a high score
		IShowHighScores hsForm;
		if (scoresDB.IsHighScore(hs))
		{
			// TODO
//			hsForm = new HighScoreEntryForm("Congratulations Player 1!");
//			hsForm.ShowDialog();
//			hs.Name = hsForm.PlayerName;
//			scoresDB.Add(hs);
		}
		// if there was a human player 2, check his high score
		if (m_gameType == GameType.TwoPlayer && scoresDB.IsHighScore(hs2))
		{
			// TODO
//			hsForm = new HighScoreEntryForm("Congratulations Player 2!");
//			hsForm.ShowDialog();
//			hs2.Name = hsForm.PlayerName;
//			scoresDB.Add(hs2);
		}

		// show the high score list
		// TODO
//		MessageBox.Show(scoresDB.ToString(), "High Scores");

		// save the database
		scoresDB.SaveScores();
	}

}
