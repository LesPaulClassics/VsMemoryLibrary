package com.tsop.android.memory.library;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.tsop.android.memory.library.test.TestDialog;
import com.tsop.android.memory.library.test.TestDialog.OnTestSelectionListener;
import com.tsop.android.utils.Utilities;
import com.tsop.games.memory.Card;
import com.tsop.games.memory.GameBoard;
import com.tsop.games.memory.GameConstants;
import com.tsop.games.memory.GameType;
import com.tsop.games.memory.IGameBoardDisplay;
import com.tsop.games.memory.Match;
import com.tsop.games.memory.MemoryGame;
import com.tsop.games.memory.Player;
import com.tsop.games.memory.Position;
import com.tsop.games.memory.StatusMonitor;

public class ShowGameboard extends Activity implements IGameBoardDisplay
{
	private ImageButton[][] cards;
	private Menu mnuOptions;
	private MenuItem mnuSound;
	private android.widget.TextView player1Label, player2Label, player1Score, player2Score;
	private DisplayMetrics _metrics;
	private int labelHeight;
	private boolean displayingImage = false;
	private boolean imagesEnabled = false;
	
//	private GameBoard gameBoard;
	private MemoryGame theGame;
	private int m_moveTimerCount; // count to make sure at least 10 .1sec intervals have occurred since last timer action
//	private Timer moveTimer;
//	private Timer statusTimer;
	private StatusMonitor statMon;
	private int mDifficulty = 3;
	private long startTime;

	private Handler mHandler = new Handler();
	private long mStartTime;
	
    private SoundPool soundPool; 
    private HashMap<Sounds, Integer> soundPoolMap; 
    private boolean soundOn = true;
    
    private Animation anmFlipOver, anmFlipBack;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        theGame = new MemoryGame(this);
		statMon = new StatusMonitor(this, theGame);
		
        anmFlipOver = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_flip_over);
    	anmFlipOver.setFillEnabled(true);
    	anmFlipOver.setFillAfter(true);
        anmFlipBack = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.card_flip_back);
        anmFlipBack.setFillEnabled(true);
        anmFlipBack.setFillBefore(true);
        
        imagesEnabled = Boolean.parseBoolean(getString(R.string.IMAGES_ENABLED));

        LoadPreferences();

        createViews();
        InitSounds();
        InitGame();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {    
    	MenuInflater inflater = getMenuInflater();    
    	inflater.inflate(R.menu.options_menu, menu);    
    	mnuOptions = menu;
    	mnuSound = menu.findItem(R.id.Sound);

		
		SetCurrentDifficultyMenu();
		SetCurrentSoundMenu();
    	return true;
	}
    
    private void InitSounds() { 
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100); 
        soundPoolMap = new HashMap<Sounds, Integer>(); 
        soundPoolMap.put(Sounds.FlipAllCards, soundPool.load(getApplicationContext(), R.raw.deal, 1)); 
        soundPoolMap.put(Sounds.FlipCard, soundPool.load(getApplicationContext(), R.raw.flip, 1));
        soundPoolMap.put(Sounds.Player1Match, soundPool.load(getApplicationContext(), R.raw.playermatches, 1));
        soundPoolMap.put(Sounds.Player1NoMatch, soundPool.load(getApplicationContext(), R.raw.nomatch, 1));
        soundPoolMap.put(Sounds.Player1Win, soundPool.load(getApplicationContext(), R.raw.playerwins, 1));
        soundPoolMap.put(Sounds.Player2Match, soundPool.load(getApplicationContext(), R.raw.computermatches, 1));
        soundPoolMap.put(Sounds.Player2NoMatch, soundPool.load(getApplicationContext(), R.raw.nomatch, 1));
        soundPoolMap.put(Sounds.Player2Win, soundPool.load(getApplicationContext(), R.raw.computerwins, 1));
        soundPoolMap.put(Sounds.StartGame, soundPool.load(getApplicationContext(), R.raw.startgame, 1));
   }

    public void PlaySound(Sounds sound)
    {
    	PlaySound(sound, 0);
    }
    
    public void PlaySound(Sounds sound, int waitMs)
    {
    	if (!soundOn)
    		return;

    	try
    	{
    		// TODO only allow one sound to play at a time
    		synchronized(this)
    		{
		        AudioManager mgr = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);   
		        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
		        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
		        float volume = streamVolumeCurrent / streamVolumeMax;   
		        soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f); 
		        Thread.sleep(waitMs);
    		}
    	}
    	catch (Exception ex)
    	{
    		System.out.println("PlaySound() EXCEPTION: " + ex.getMessage());
    	}
    }



    protected void createViews()
    {
    	_metrics = new DisplayMetrics(); 
    	getWindowManager().getDefaultDisplay().getMetrics(_metrics);
    	int screenWidth = _metrics.widthPixels;
    	int screenHeight = _metrics.heightPixels - 18;	// todo this is a fudge to account for the title bar

    	int spacing = Utilities.scalePixels(_metrics, 2);
    	int leftSpacing = spacing;
 
    	// calculate label dimensions
    	labelHeight = Utilities.scalePixels(_metrics, 15);	
    	int x1 = Utilities.scalePixels(_metrics, 5), textspacing = Utilities.scalePixels(_metrics, 10);
    	int x2 = (int) (screenWidth * 0.30f);
    	int x3 = (int) (screenWidth * 0.50f);
    	int x4 = (int) (screenWidth * 0.8f);
    	int width = x2 - x1 - Utilities.scalePixels(_metrics, 5);
    	int yOffset = Utilities.scalePixels(_metrics, 3);

    	// create labels
    	player1Label = createLabel(getResources().getText(R.string.LABEL_PLAYER1).toString(), 
    			x1, yOffset, width);
    	width = x3 - x2 - Utilities.scalePixels(_metrics, 5);
    	player1Score = createLabel("0", x2, yOffset, width);
    	width = x4 - x3 - Utilities.scalePixels(_metrics, 5);
    	player2Label = createLabel(getResources().getText(R.string.LABEL_PLAYER2).toString(),
     			x3, yOffset, width);
     	width = screenWidth - x4 - Utilities.scalePixels(_metrics, 5);
     	player2Score = createLabel("0", x4, yOffset, width);
     	
     	// TODO: Enable this for debugging
//     	player1Label.setClickable(true);
//     	player1Label.setOnClickListener(LabelClickListener);
     	
     	// calculate card dimensions
       	int topMargin = labelHeight + yOffset + Utilities.scalePixels(_metrics, 2);	
    	int cardWidth = (int)((screenWidth - ((GameBoard.NUM_COLUMNS + 1) * spacing)) / GameBoard.NUM_COLUMNS);
    	int cardHeight = (int)((screenHeight - topMargin - ((GameBoard.NUM_ROWS + 1) * spacing)) / GameBoard.NUM_ROWS);
    	int cardSize = Math.min(cardHeight, cardWidth);
    	if (cardSize == cardHeight)
    		leftSpacing = (int)((GameBoard.NUM_COLUMNS * (cardWidth - cardHeight)) / 2.0);
    	if (cardSize == cardWidth)
    		topMargin += Math.min((int) ((GameBoard.NUM_ROWS * (cardHeight - cardWidth)) / 2.0), 15.0f);
    	cardWidth = cardHeight = cardSize;
    	
    	// create cards
    	cards = new ImageButton[GameBoard.NUM_COLUMNS][GameBoard.NUM_ROWS];
		for (int c = 0; c < GameBoard.NUM_COLUMNS; c++)
		{
	    	for (int r = 0; r < GameBoard.NUM_ROWS; r++)
	    	{
    			cards[c][r] = new ImageButton(getApplicationContext());
    			LayoutParams lp = new LayoutParams(cardSize,cardSize);
    			lp.gravity = Gravity.LEFT & Gravity.TOP;
    			lp.width = cardWidth;
    			lp.height = cardHeight;
    			
    			lp.topMargin = topMargin + ((cardWidth + spacing) * r);
    			lp.leftMargin = leftSpacing + ((cardSize + spacing) * c);
    			cards[c][r].setBackgroundDrawable(null);
    			cards[c][r].setLayoutParams(lp);
    			cards[c][r].setImageResource(R.drawable.back);
    			cards[c][r].setAdjustViewBounds(true);
    			cards[c][r].setScaleType(ScaleType.FIT_XY);
    			cards[c][r].setPadding(0,0,0,0);
    			cards[c][r].setClickable(true);
    			cards[c][r].setOnClickListener(CardClickListener);
    			cards[c][r].setTag(new Position(c,r));
    			if (c == 0 && r == 0)
    				System.out.println("card size = " + lp.width + " x " + lp.height + " spacing = " + spacing + " screenWidth = " + _metrics.widthPixels + " screenHeight = " + _metrics.heightPixels);

    	    	((FrameLayout)findViewById(R.id.gameFrameLayout)).addView(cards[c][r]);
    			
    		}
    	}

    }
    
    private TextView createLabel(String text, 
    		int posX, int posY, int width)
    {
    	TextView label = new TextView(getApplicationContext());
    	LayoutParams lp = new LayoutParams(labelHeight, width);	// todo scale
    	lp.gravity = Gravity.LEFT & Gravity.TOP;
    	lp.width = width;
    	lp.height = labelHeight;
    	lp.topMargin = posY;
    	lp.leftMargin = posX;
    	label.setLayoutParams(lp);
    	label.setText(text);
//    	label.setTypeface(Typeface.SANS_SERIF, R.style.LabelFont);
    	label.setTextAppearance(getApplicationContext(), R.style.LabelFont);
    	((FrameLayout)findViewById(R.id.gameFrameLayout)).addView(label);
    	float textAscent = label.getPaint().ascent();
    	float textDescent = label.getPaint().descent();
//    	label.getPaint().baselineShift = -2;
    	int textHeight = (int) (textDescent - textAscent);
    	System.out.println("createLabel(): textAscent = " + textAscent + ", textDescent = " + textDescent+ ", textHeight = " + textHeight + ", label Height = " + labelHeight);
    	int padding = Utilities.scalePixels(_metrics, 5);
    	if ((textHeight + padding) > labelHeight)
    	{
    		labelHeight = (int)textHeight + padding;   	
    		label = createLabel(text, posX, posY, width);
    	}
    	return label;
    }
    
	

    
    private void InitGame()
    {
//    	gameBoard = new GameBoard(this);
    	theGame.getGameBoard().initGameBoard(getCardSubset());
    	theGame.getGameBoard().Shuffle();
		m_moveTimerCount = 0;
		setCardDrawables(theGame.getGameBoard().getCardArray());
		UpdateScores();
    }
    
    private short[] getCardSubset()
    {
    	int highestCardNumber = getHighestCardNumber();
    	System.out.println("Highest Card Resource Number = " + highestCardNumber);
    	short[] valueArray = new short[highestCardNumber];
    	for (short i=1;i<=highestCardNumber;i++)
    	{
    		valueArray[i-1] = i;
    	}
    	//shuffle the array of available cards
    	Random randomNumber =new Random();
		short temporaryValue;
		for (int j = 0; j < highestCardNumber; j++)
		{
			// randomly choose new location
			int newJ = randomNumber.nextInt(highestCardNumber);

			// swap cards
			temporaryValue = valueArray[j];
			valueArray[j] = valueArray[newJ];
			valueArray[newJ] = temporaryValue;
		}
		// return only the first 24 of them
		short[] result = new short[GameBoard.TOTAL_CARDS / 2];
		for (int k=0; k < GameBoard.TOTAL_CARDS / 2; k++)
			result[k] = valueArray[k];
		
		return result;
    	
//    	Set<Integer> map = new HashSet<Integer>();
//    	Random rand = new Random();
//    	for (int i=1; i <= GameBoard.TOTAL_CARDS; i++)
//    	{
////    		int resNum = 1 + rand.nextInt(highestCardNumber);
//    		while (!map.add(1 + rand.nextInt(highestCardNumber)))
//    		{
//    			
//    		}
// 				
//    	}
//    	int[] numArray = new int[GameBoard.TOTAL_CARDS];
//    	int index = 0;
//    	while (map.iterator().hasNext())
//    	{
//    		numArray[index] = map.iterator().next();
//    	}
    	// TODO finish this

    }
    
    private int getHighestCardNumber()
    {
    	int highestNumber = 0;
        try
        {
        	String packageName = getApplicationContext().getPackageName();
        	String className = packageName + ".R";
	        Class res = Class.forName(className);        
	        Class[] subClasses = res.getDeclaredClasses();  
	        Class draw = null;  
	        for (Class subclass : subClasses) {  
	     	   if ((className + ".drawable").equals(subclass.getCanonicalName())) {  
	     		   draw = subclass;  
	     		   break;
	     	   }
	        }
	        while (highestNumber < 100) // arbitrary high number
	        {
	        	highestNumber++;
	        	try
	        	{
	        		String resString = "card_" + Integer.toString(highestNumber);
	        		int id = (int) draw.getField(resString).getInt(draw);
//	        		System.out.println(resString + " id = " + id);
	        	}
	        	catch (Exception e)
	        	{
	        		return highestNumber -1;
	        	}
	        }
	        
        }
        catch (Exception ex)
        {
     	   System.out.println("getHighestCardNumber(): " + ex.getMessage());
        }
        return highestNumber;
    }
    
    private void setCardDrawables(Card[][] cards)
    {
        try
        {
        	String packageName = getApplicationContext().getPackageName();
        	String className = packageName + ".R";
	        Class res = Class.forName(className);
	        Class[] subClasses = res.getDeclaredClasses();  
	        Class draw = null;  
	        for (Class subclass : subClasses) {  
	     	   if ((className + ".drawable").equals(subclass.getCanonicalName())) {  
	     		   draw = subclass;  
	     		   break;
	     	   }
	        }

	        for (int i=0; i < cards.length; i++)
	    		for (int j=0; j < cards[i].length; j++)
	    		{ //todo: find out why it's got a value of "card_26"
	    			Card card = cards[i][j];
	    			short value = card.getCardValue();
		            String resString = "card_" + Short.toString(value);
		            int id = (int) draw.getField(resString).getInt(draw);
//		            System.out.println(resString + " = " + id);
		            card.setPosition(i,j);
		            card.setDrawableId(id);

	    		}
	  
        }
        catch (Exception ex)
        {
     	   System.out.println("setCardDrawables(): " + ex.getMessage());
        }
    }
    
	@Override
	public void DrawGameBoard(Card[][] cardArray) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void UpdateGameBoard(int col, int row, boolean animate) {

		Card card= theGame.getGameBoard().getCardArray()[col][row];
		Drawable faceImage = getResources().getDrawable(card.getDrawableId());
		Drawable backImage = getResources().getDrawable(R.drawable.back);
		Drawable startingImage, endingImage;
		if (card.getFlipped())
		{
			System.out.println("UpdateGameBoard: card is flipped. End Image = " + Integer.toString(card.getDrawableId()));
			startingImage = backImage;
			endingImage = faceImage;
		}
		else
		{
			startingImage = faceImage;
			endingImage = backImage;
		}
		
		if (animate)
		{
//			cards[col][row].setImageDrawable(startingImage);
//			System.out.println("UpdateGameBoard: setImageDrawable ANIM on " + Integer.toString(col) + "," + Integer.toString(row));
			anmFlipOver.reset();
			anmFlipOver.setAnimationListener(new FinishFlip(col, row, endingImage));
			cards[col][row].startAnimation(anmFlipOver);
		}
		else
		{
			cards[col][row].setImageDrawable(endingImage);
			System.out.println("UpdateGameBoard: setImageDrawable NO ANIM on " + Integer.toString(col) + "," + Integer.toString(row));
		}
		
		UpdateScores();
	}
	
	public void UpdateScores()
	{
//		System.out.println("UpdateScores()");
		if (theGame.getPlayerArray()[0] != null)
			player1Score.setText(Integer.toString(theGame.getPlayerArray()[0].getScore()));
		else
			player1Score.setText("-");
		
		if (theGame.getPlayerArray()[1] != null)
			player2Score.setText(Integer.toString(theGame.getPlayerArray()[1].getScore()));
		else
			player2Score.setText("-");
		
		// indicate whose turn it is currently
		if (!theGame.getGameOver() && theGame.getGameType() != GameType.OnePlayer)
		{
			if (theGame.getWhoseTurn().getName().equals(GameConstants.PLAYER_1))
			{
				player1Label.setBackgroundColor(getResources().getColor(R.color.CurrentPlayerBack));
				player1Label.setTextColor(getResources().getColor(R.color.CurrentPlayerText));
//				player1Label.setTextColor(Color.WHITE);
				player2Label.setBackgroundColor(getResources().getColor(R.color.OtherPlayerBack));
				player2Label.setTextColor(getResources().getColor(R.color.OtherPlayerText));
//				player2Label.setTextColor(Color.BLUE);
			}
			else
			{
				player1Label.setBackgroundColor(getResources().getColor(R.color.OtherPlayerBack));
				player1Label.setTextColor(getResources().getColor(R.color.OtherPlayerText));
//				player1Label.setTextColor(Color.BLUE);
				player2Label.setBackgroundColor(getResources().getColor(R.color.CurrentPlayerBack));
				player2Label.setTextColor(getResources().getColor(R.color.CurrentPlayerText));
//				player2Label.setTextColor(Color.WHITE);
			}
		}
	}
	
   private void Exit()
   {
		SavePreferences();
	    soundPool.release();
		int pid = android.os.Process.myPid(); 
		android.os.Process.killProcess(pid);
   }

   /* Handles item selections */
   public boolean onOptionsItemSelected(MenuItem item) {    
	   switch (item.getItemId()) {    
   		case R.id.Exit:
   			Exit();
   			return true;
   		case R.id.StartVsComputer:
   			StartGame(GameType.OnePlayerVersusComputer, mDifficulty);
   			return true;
   		case R.id.StartTwoPlayer:
   			StartGame(GameType.TwoPlayer, mDifficulty);
   			return true;
   		case R.id.StartOnePlayer:
   			StartGame(GameType.OnePlayer, mDifficulty);
   			return true;
   		case R.id.Difficulty1:
   			SetDifficulty(1);
   			return true;
   		case R.id.Difficulty2:
   			SetDifficulty(2);
   			return true;
   		case R.id.Difficulty3:
   			SetDifficulty(3);
   			return true;
   		case R.id.Difficulty4:
   			SetDifficulty(4);
   			return true;
   		case R.id.Difficulty5:
   			SetDifficulty(5);
   			return true;
   		case R.id.Sound:
   			ToggleSound();
   			return true;
   		case R.id.Help:
   			openOptionsHelpDialog();
   			return true;
		}    
	   return false;
   }
	   
   private void SetDifficulty(int value)
   {
	   mDifficulty = value;
	   if (theGame.getPlayerArray() != null)
		   for (Player player : theGame.getPlayerArray())
			   if (player != null)
				   player.setIQ(value);
		  
	   SetCurrentDifficultyMenu();
   }
   
   protected void SetCurrentDifficultyMenu()
   {
		mnuOptions.findItem(R.id.Difficulty1).setChecked(false);
		mnuOptions.findItem(R.id.Difficulty2).setChecked(false);
		mnuOptions.findItem(R.id.Difficulty3).setChecked(false);
		mnuOptions.findItem(R.id.Difficulty4).setChecked(false);
		mnuOptions.findItem(R.id.Difficulty5).setChecked(false);
		switch (mDifficulty)
		{
		case 1:
			mnuOptions.findItem(R.id.Difficulty1).setChecked(true);
			break;
		case 2:
			mnuOptions.findItem(R.id.Difficulty2).setChecked(true);
			break;
		case 3:
			mnuOptions.findItem(R.id.Difficulty3).setChecked(true);
			break;
		case 4:
			mnuOptions.findItem(R.id.Difficulty4).setChecked(true);
			break;
		case 5:
			mnuOptions.findItem(R.id.Difficulty5).setChecked(true);
			break;
			
		}
   }

   protected void SetCurrentSoundMenu()
   {
      if (soundOn)
      {
    	  if (mnuSound != null) mnuSound.setTitle(R.string.MENU_ITEM_SOUND_ON);
      }
      else
      {
    	  if (mnuSound != null) mnuSound.setTitle(R.string.MENU_ITEM_SOUND_OFF);
      }
   }
   
   public void showMatchResult(int imageID)
   {
//	   MatchImageDialog md = new MatchImageDialog(this, imageID);
//	   md.show();
	   if (imagesEnabled)
	   {
		   displayingImage = true;
		   ShowMatchResult mr = new ShowMatchResult(this, imageID);
		   mr.show();
	   }
   }
   
   public void matchResultCancelled()
   {
	   displayingImage = false;
   }
   
   private void showTestDialog()
   {
	   TestDialog td = new TestDialog(this, testSelectionListener);
	   td.show();
   }
   
   private TestDialog.OnTestSelectionListener testSelectionListener = new OnTestSelectionListener() {
	
	@Override
	public void testSelection(int selectedIndex) {
		// TODO Auto-generated method stub
		   showMatchResult(selectedIndex);
	}
};
   
   private View.OnClickListener LabelClickListener = new View.OnClickListener() {
	   public void onClick(View arg0) {
//		   ShowMatchResult();
		   showTestDialog();
	   }
   };
   
   private View.OnClickListener CardClickListener = new View.OnClickListener() {
	   public void onClick(View arg0) {
		   if (theGame.getGameOver())
		   {
			   openOptionsMenu();
			   return;
		   }
		   
		   if ((new Date()).getTime() - startTime < 1000)
			   return;		// fixing a bug where app crashes if a card is selected too soon after starting game
		   
			if (theGame.getWhoseTurn() == null || theGame.getWhoseTurn().AcceptInput() == false // don't allow mouse clicking unless a user is the current player
					|| (theGame.getLastTwoMoves().location1.column != -1))							// or the cards haven't been flipped yet
					return;
					
			Position pos = (Position) arg0.getTag();
			int row = pos.row;
			int column = pos.column;
			
			if (column != -1 && row != -1)
			{
				// report the move to the game engine
//				System.out.println("OnClick- RecordMove()");
				theGame.RecordMove(pos);
				
				//flip the card
//				Card card = gameBoard.getCardArray()[column][row];
//				cards[column][row].setImageResource(card.getDrawableId());			
			}
			m_moveTimerCount = 0;
		}
	};
	
	/// Method that starts a new game
	private void StartGame(GameType type, int computerIQ)
	{
		// initialize a new set of game variables
		theGame.StartGame(type, computerIQ);
		startTime = (new Date()).getTime();

		PlaySound(Sounds.StartGame);
		// enable or disable the cheat button
		// todo: do I want this?
//		if (theGame.getPlayerArray()[1] != null && !theGame.getPlayerArray()[1].AcceptInput())
//			this.cheatButton.Enabled = true;
//		else
//			this.cheatButton.Enabled = false;
		
		// start the move and status timers ticking

		try
		{
			mStartTime = System.currentTimeMillis();
			mHandler.removeCallbacks(mTimerTick);
			mHandler.postDelayed(mTimerTick, 100);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		statMon.Reset();

	}
	  
   private void openOptionsHelpDialog()
   {  
	   new AlertDialog.Builder(this)
	   .setTitle(R.string.HELP_TITLE)
	   .setMessage(R.string.HELP_TEXT)
	   .setPositiveButton(R.string.HELP_OK_BUTTON, 
			   new DialogInterface.OnClickListener() 
	   			{        
		   			@Override    
		   			public void onClick(DialogInterface dialog, int which) 
		   			{     // nothing to do?         
		   			}
	   			}
	   		)  
			.show(); 
	}

   public void openGameOverDialog(String text)
   {  

	   
	   new AlertDialog.Builder(this)
	   .setTitle(R.string.GAME_OVER_TITLE)
	   .setMessage(text)
	   .setPositiveButton(R.string.GAME_OVER_OK, 
			   new DialogInterface.OnClickListener() 
	   			{        
		   			@Override    
		   			public void onClick(DialogInterface dialog, int which) 
		   			{     // nothing to do?         
		   			}
	   			}
	   		)  
			.show(); 
	}
   
   private void LoadPreferences()
   {
   	// TODO Add last game played to preferences
       SharedPreferences settings = getSharedPreferences(UIConstants.PREFS_NAME, 0);
       soundOn = settings.getBoolean(UIConstants.SOUND_ON_PREF, true);
       mDifficulty = settings.getInt(UIConstants.DIFFICULTY_PREF, 3);
       }
   
   private void SavePreferences()
   {      
   	SharedPreferences settings = getSharedPreferences(UIConstants.PREFS_NAME, 0);
   	SharedPreferences.Editor editor = settings.edit();
   	editor.putBoolean(UIConstants.SOUND_ON_PREF, soundOn);
   	editor.putInt(UIConstants.DIFFICULTY_PREF, mDifficulty);
   	editor.commit();
   }
   
   private void ToggleSound()
   {
      soundOn = !soundOn;
      SetCurrentSoundMenu();
//      SavePreferences();
   }

  
   /**
    * This class listens for the end of the first half of the card flip animation.
    * It then changes the card to the correct image and finishes the flip
    */
   private final class FinishFlip implements Animation.AnimationListener {
       private final int cardRow, cardCol;
       private final Drawable cardImage;

       private FinishFlip(int col, int row, Drawable newCard) {
           cardRow = row;
           cardCol = col;
           cardImage = newCard;
       }

       public void onAnimationStart(Animation animation) {
       }

       public void onAnimationEnd(Animation animation) {
       	cards[cardCol][cardRow].setImageDrawable(cardImage);
		System.out.println("onAnimationEnd: setImageDrawable on " + Integer.toString(cardCol) + "," + Integer.toString(cardRow));
       	anmFlipBack.reset();
		cards[cardCol][cardRow].startAnimation(anmFlipBack);
       }

       public void onAnimationRepeat(Animation animation) {
       }
   }
   
	
	private Runnable mTimerTick = new Runnable() {
		int m_delayTime = 8;
		ReentrantLock lockObject = new ReentrantLock();

		public void run() {
			final long start = mStartTime;
			long millis = SystemClock.uptimeMillis() - start;
//			System.out.println("Timer Tick");
			
			if (lockObject.isLocked())
				return;
			
			lockObject.lock();
			try
			{
				// stop the game when its over
				if (theGame.getGameBoard().getCardsTurnedOver() >= GameBoard.TOTAL_CARDS)
				{
					if (!displayingImage)	// keep looping while a match dialog is displayed
					{
						theGame.setGameOver(true);
						UpdateScores();
					}
					return;
				}
		
				// check if enough time has passed since last action
				if (m_moveTimerCount < m_delayTime)
				{
					m_moveTimerCount++;
					return;
				}
		
				// check if any cards need flipping
				if (theGame.getLastTwoMoves().location1.column != -1)
				{
					Match temp = theGame.getLastTwoMoves();
//					System.out.println("TimerTick- TurnOverCard()");
					theGame.getGameBoard().TurnOverCard(temp.location1.column, temp.location1.row, false, false);
					theGame.getGameBoard().TurnOverCard(temp.location2.column, temp.location2.row, false, false);
					theGame.setLastTwoMoves(new Match(new Position(-1, -1), new Position(-1, -1), false));
					m_moveTimerCount = 0;
					UpdateScores();
					return;
				}
		
				// if it is the user's turn, just exit
				if (theGame.getWhoseTurn() == null || theGame.getWhoseTurn().AcceptInput() == true)	
				{
					m_moveTimerCount = 0;
					UpdateScores();
					return;
				}
		
//				System.out.println("TimerTick - RecordMove()");
				theGame.RecordMove(theGame.getWhoseTurn().GetMove());
				UpdateScores();
				m_moveTimerCount = 0;

			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			finally
			{
				lockObject.unlock();
				if (!theGame.getGameOver())
					mHandler.postAtTime(this, start + millis + 100);
			}
			
		}
	};
	
}