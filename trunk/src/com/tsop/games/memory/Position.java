package com.tsop.games.memory;

/// <summary>
/// structure to record the location (row, column) of a card
/// </summary>
public class Position {

	public int row;
	public int column;

	public Position(int Column, int Row)
	{
		row = Row;
		column = Column;
	}
	
	@Override
	public boolean equals(Object o) 
	{
		Position pos = (Position)o;
		return ((row == pos.row) && (column == pos.column));
	}
	
	@Override
	public String toString()
	{
		return "(Col = " + Integer.toString(column) + ", Row = " + Integer.toString(row) + ")";
	}


}
