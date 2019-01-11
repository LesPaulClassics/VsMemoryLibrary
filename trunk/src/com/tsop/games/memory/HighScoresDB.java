package com.tsop.games.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/// <summary>
/// Class that manages the High Scores Database
/// </summary>
public class HighScoresDB implements Comparator<HighScore>	// TODO Comparable??? Why?
{
	public HighScoresDB()
	{
		collection = new ArrayList<HighScore>();
	}

	/// <summary>
	/// Field that contains the array list of high score structs
	/// </summary>
	private ArrayList<HighScore> collection;

	/// <summary>
	/// Field that contains the file name for the high scores file
	/// </summary>
	private final String FILENAME = "hs.bin";

	private final int NUMBER_OF_SCORES_PER_TYPE = 5;

	/// <summary>
	/// Method to add a new High Score to the database
	/// </summary>
	public boolean Add(HighScore hs)
	{
		// test the following conditions:
		// 1) The score must be higher than the lowest high score (unless fewer than NUMBER_OF_SCORES_PER_TYPE)
		// 2) The lowest score must be successfully removed; OR
		//		there is room for the new score without removal
		if (IsHighScore(hs) && (Remove(GetLowestScore(hs.Type)) || GetCount(hs.Type) < NUMBER_OF_SCORES_PER_TYPE))
		{
			collection.add(hs);
			return true;
		}

		return false;
	}

	/// <summary>
	/// Method to display all the high scores as a string
	/// </summary>
	@Override
	public String toString()
	{
		String output = "";
		// define the heading string
		String heading = "\tDate\t\t\t";
		String NameTitle = "Name";
		heading += Utils.padRight(NameTitle, 20) + "\tScore\t\tOpponent\tDifficulty\n";

		// create 3 subarrays for each game type
		ArrayList[] typeList = new ArrayList[3];
		typeList[0] = new ArrayList<HighScore>(5);
		typeList[1] = new ArrayList<HighScore>(5);
		typeList[2] = new ArrayList<HighScore>(5);

		for (HighScore hs : collection)
		{
			// Assign the current object to the subarray indexed by the current object's game type
			typeList[hs.Type.ordinal()].add(hs);				
		}

		String[] gameTitles = {"One Player Games", "One Player Versus Computer Games", "Two Player Games"};

		// output the heading
		output += heading;

		// create the big output string
		for (int i = 0; i < 3; i++)
		{
			// output the game type title
			output += "\n" + gameTitles[i] + "\n";

			// output each score within that heading
			Collections.sort(collection, this);
			for (HighScore hs : (ArrayList<HighScore>)typeList[i])
			{
				output += "\t" + hs.toString() + "\n";
			}
		
		}
		return output;
	}

	/// <summary>
	/// Public method that writes the high scores database to disk
	/// </summary>
	public boolean SaveScores()
	{
		// TODO
//		try
//		{
//			// create the output file
//			FileStream outputFile =  new FileStream(FILENAME, FileMode.Create, FileAccess.Write);
//
//			// write the data to the file
//			BinaryFormatter formatter = new BinaryFormatter();
//			formatter.Serialize(outputFile, collection);
//
//			// close the file & delete the records from memory
//			outputFile.Close();
//		}
//		catch (Exception e)
//		{
//			MessageBox.Show(e.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
//			return false;
//		}

		return true;
	}

	/// <summary>
	/// Public method that loads the high scores database from the disk
	/// </summary>
	public boolean LoadScores()
	{
		// TODO
//		try
//		{
//			// open the file for reading
//			FileStream inputFile = new FileStream(FILENAME, FileMode.OpenOrCreate, FileAccess.Read);
//
//			// build the high scores database
//			collection.Clear();
//
//			BinaryFormatter formatter = new BinaryFormatter();
//
//			HighScore hs = new HighScore();
//
//			collection = formatter.Deserialize(inputFile) as ArrayList;
//
//			inputFile.Close();
//		}
//		catch (Exception e)
//		{
//			// don't display the exception if this is simply an empty file
//			if (e.Message != "Attempting to deserialize an empty stream.")
//				MessageBox.Show(e.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
//			return false;
//		}

		return true;
	}

	/// <summary>
	/// Method that determines what the lowest high score of the specified game type is
	/// </summary>
	/// <remarks>
	/// The "Lowest High Score" is defined as the score that contains the lowest
	/// winners high score and the highest losers high score.  i.e., if two scores
	/// had the same winner's score, the one with the higher loser's score would
	/// be considered a lower overall score
	/// </remarks>
	/// <param name="type">
	/// the type of game that was played</param>
	public HighScore GetLowestScore(GameType type)
	{
		// sort the collection so that the scores are in descending order
		Collections.sort(collection, this);

		HighScore lowest = new HighScore();
		lowest.WinnerScore = Integer.MAX_VALUE;

		// step through each score of the game type to see if its lower
		for (HighScore current : collection)
		{
			if (current.Type == type && current.WinnerScore <= lowest.WinnerScore)
				lowest = current;
		}

		// if lowest is still int.MaxValue, then no scores were found
		// or if the high score list is not full yet
		if (lowest.WinnerScore == Integer.MAX_VALUE || GetCount(type) < NUMBER_OF_SCORES_PER_TYPE)
			// return an arbitrarily low value
			lowest = new HighScore(type, Integer.MIN_VALUE, Integer.MAX_VALUE, "<none>", 0);

		return lowest;
	}

	/// <summary>
	/// Method that decides if the queried score is worthy of entering the hall of fame
	/// </summary>
	/// <param name="highScore">
	/// The high score struct to evaluate
	/// </param>
	public boolean IsHighScore(HighScore query)
	{
		HighScore lowest = GetLowestScore(query.Type);
		// if the score is lower, cannot add it to the high score list
		if (query.WinnerScore < lowest.WinnerScore)
			return false;

		// if the score is higher, this is a simple decision
		if (query.WinnerScore > lowest.WinnerScore)
			return true;

		// otherwise the scores are equal, check the loser score and difficulty
		// a lower loser score is considered a higher overall high score
		if (query.LoserScore < lowest.LoserScore)
			return true;
		
		// if the loser's score is higher than the lowest, this score is not a high score
		if (query.LoserScore > lowest.LoserScore)
			return false;

		// if we get to this point both the winner and loser scores are equal
		// one last chace: compare difficulties
		if (query.Diff > lowest.Diff)
			return true;

		// if we get this far, it doesn't deserve the hall of fame
		return false;


	}

	/// <summary>
	/// Method that counts the number of High Scores of a specified game type, or All
	/// </summary>
	public int GetCount(GameType type)
	{
		if (type == GameType.All)
			return collection.size();

		int count = 0;

		for (HighScore hs : collection)
		{
			if (hs.Type == type)
				count++;
		}

		return count;
	}

	/// <summary>
	/// Method to remove a high score from the database
	/// </summary>
	public boolean Remove(HighScore hs)
	{
		int countBefore = collection.size();

		collection.remove(hs);

		if (countBefore == collection.size())	// if nothing changed
			return false;						// the remove wasn't successful

		return true;
	}

	@Override
	public int compare(HighScore arg0, HighScore arg1) 
	{
		// sort by game type first
		if (arg0.Type.ordinal() < arg1.Type.ordinal())
			return -1;
		if (arg0.Type.ordinal() > arg1.Type.ordinal())
			return 1;	

		// else they are equal, so sort by score
		if (arg0.WinnerScore > arg1.WinnerScore)
			return -1;
		if (arg0.WinnerScore < arg1.WinnerScore)
			return 1;

		// else they are still equal, sort by loser score
		if (arg0.LoserScore < arg1.LoserScore)
			return -1;
		if (arg0.LoserScore > arg1.LoserScore)
			return 1;
	
		// else they are still equal, sort by difficulty
		if (arg0.Diff > arg1.Diff)
			return -1;
		if (arg0.Diff < arg1.Diff)
			return 1;

		return 0;
	}

}

