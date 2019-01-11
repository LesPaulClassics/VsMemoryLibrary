package com.tsop.games.memory;
/// <summary>
/// enumeration that describes which type of game to start
/// </summary>
public enum GameType 
{
	OnePlayer(0),
	OnePlayerVersusComputer(1),
	TwoPlayer(2),
	All(10);

    /**
     * The ScopeType ID
     */
    public final int index;

    /**
     * the constructor of a enumeration which assigns it integer index
     * @param index
     */
    private GameType(int index)
    {
        this.index = index;
    }


}


