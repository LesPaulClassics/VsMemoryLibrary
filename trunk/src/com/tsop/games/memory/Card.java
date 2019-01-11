package com.tsop.games.memory;

import android.graphics.drawable.Drawable;

/// <summary>
/// Summary description for Card.
/// </summary>
public class Card
{
	/// <summary>
	/// the state of the card, flipped = card's value is showing
	/// </summary>
	private boolean m_flipped;

	/// <summary>
	/// integer representing the value of the card
	/// </summary>
	private short m_value;
	
	/**
	 * The row the card is currently in
	 */
	private int m_row;
	
	/**
	 * The column the card is currently in
	 */
	private int m_column;
	
	/// <summary>
	/// location to draw card at
	/// </summary>
//	private Point m_location;
	
	/// <summary>
	/// location to draw card at
	/// </summary>
//	private Point m_size;

	/// <summary>
	/// the name of the image file for this card
	/// </summary>
	private String m_fileName;

	/// <summary>
	/// the image object that contains the card image
	/// </summary>
	private int m_imageId;

	/// <summary>
	/// the image object that contains the back image
	/// </summary>
	private Drawable m_back;

	/// <summary>
	/// Constructor for a card object
	/// </summary>
	/// <param name="cardValue">
	/// the numerical value for the card (must be between 0 and NUM_CARDS)
	/// </param>
	public Card(short cardValue)
	{
//		if (cardValue <= (GameBoard.TOTAL_CARDS / 2) && cardValue > 0) // TODO validate if it is OK to use highestCardValue instead of TOTAL_CARDS/2
		if (cardValue > 0) 
			m_value = cardValue;
		else
			m_value = 0;

		m_flipped = false;
		// TODO load the proper image
//		m_fileName = "image" + m_value.ToString("D2") + ".gif";
//		m_image = Image.FromFile(IMAGE_DIR + m_fileName);
//		m_back = Image.FromFile(IMAGE_DIR + BACK_FILENAME);
	}

	/// <summary>
	/// integer represeting the value of the card (ie, picture number)
	/// </summary>
	public short getCardValue()
	{
		return m_value;
	}

	/// <summary>
	/// returns whether or not the card is flipped over to show its image
	/// </summary>
	public boolean getFlipped()
	{
		return m_flipped;
	}
	public void setFlipped(boolean value)
	{
		m_flipped = value;
	}

	/// <summary>
	/// location to draw card at
	/// </summary>
//	public void setDrawLocation(Point value)
//	{
//		if (value.x >= 0 && value.y >= 0)
//			m_location = value;
//	}
	
	public int getDrawableId()
	{
		return m_imageId;
	}
	
	public void setDrawableId(int imageId)
	{
		m_imageId = imageId;
	}

	/// <summary>
	/// draw card at pre defined location and size
	/// </summary>
	// TODO figure out how to do this with android
//	public void Draw(Graphics g)
//	{
//		if (m_flipped)
//			g.DrawImage(m_image, m_location.X, m_location.Y, m_size.Width, m_size.Height);
//		else
//			g.DrawImage(m_back, m_location.X, m_location.Y, m_size.Width, m_size.Height);
//	}

	/// <summary>
	/// draw card at specified location and size
	/// </summary>
	/// <param name="loc">
	/// Point object containing the x,y coords of the upper-left corner to draw at
	/// </param>
	// TODO figure out how to do this with android
//	public void Draw(Graphics g, Point loc)
//	{
//		DrawLocation = loc;
//		Draw(g);
//	}

	/// <summary>
	/// draw card at specified location and size
	/// </summary>
	// TODO figure out how to do this with android
//	public void Draw(Graphics g, Point loc, Size size)
//	{
//		CardSize = size;
//		DrawLocation = loc;
//		Draw(g);
//	}

	/// <summary>
	/// size structure defining the draw dimensions
	/// </summary>
//	protected Point getCardSize()
//	{
//		return m_size;
//	}
//	protected void setCardSize(Point value)
//	{
//		if (value.x > 0 && value.y > 0)
//			m_size = value;
//	}

	/// <summary>
	/// returns the rectangle which defines the card's bounds
	/// </summary>
//	public Rect getBounds()
//	{
//		Rect r = new Rect();
//		r.left = m_location.x;
//		r.top = m_location.y;
//		r.bottom = r.top + m_size.y;
//		r.right = r.left + m_size.x;
//		return r;
//	}

	public void setPosition(int col, int row)
	{
		this.m_column = col;
		this.m_row = row;
	}
	
	public int getRow()
	{
		return m_row;
	}
	
	public int getColumn()
	{
		return m_column;
	}

	



}
