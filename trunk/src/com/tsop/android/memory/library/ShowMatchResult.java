package com.tsop.android.memory.library;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.tsop.android.utils.TouchImageView;
import com.tsop.android.utils.Utilities;

public class ShowMatchResult extends Dialog {
	
	DisplayMetrics _metrics;
	ShowGameboard _gameBoard;
	
	private int _imageID;

	private int _dialogWidth;
	private int _dialogHeight;
	private int _layoutHeight;
	private int _layoutWidth;
	private int _titleHeight;
	private int _systemTitleBarHeight;
	private int _descriptionHeight;
	private int _padding;
	private int _imageViewWidth;
	private int _imageViewHeight;
	private float _imageScale;
	private Drawable _imageDrawable;
	
	private FrameLayout _frameLayout;
	ImageView _imageView;
	TouchImageView _touchView;
	
	protected ShowMatchResult(ShowGameboard context, int imageID){
		super(context);
		this.setContentView(R.layout.match_result);
		_gameBoard = context;
		this._imageID = imageID;
	}
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) 
	{
		this.setCanceledOnTouchOutside(true);
		this.setOnCancelListener(new CancelListener());
		
    	setTitle();
    	
		calculateDialogSize();
		
		_frameLayout = (FrameLayout) findViewById(R.id.matchFrameLayout);
		_frameLayout.setMinimumHeight(_layoutHeight);
		_frameLayout.setMinimumWidth(_layoutWidth);
	
	    int descHeight = createDescription();

        calculateImageDimensions(descHeight);
	    
        createImageView();
	    	
	}
	
	public void setTitle()
	{
		// set the Dialog title
        String[] stringArray = _gameBoard.getResources().getStringArray(R.array.MATCH_TITLE_ARRAY);
        this.setTitle(stringArray[_imageID]);
	}
	
	private void calculateDialogSize()
	{
        // calculate the size of the Dialog
    	_metrics = new DisplayMetrics(); 
    	_gameBoard.getWindowManager().getDefaultDisplay().getMetrics(_metrics);
    	_padding = Utilities.scalePixels2(_metrics, 20);//Utilities.scalePixels(_metrics, 20);
    	_systemTitleBarHeight = Utilities.scalePixels2(_metrics, 18);//Utilities.scalePixels(_metrics, 18);
    	_titleHeight = Utilities.scalePixels2(_metrics, 67);//Utilities.scalePixels(_metrics, 100);
    	_descriptionHeight = Utilities.scalePixels2(_metrics, 67);//Utilities.scalePixels(_metrics, 100);
    	_dialogWidth = _metrics.widthPixels - _padding;
    	_dialogHeight = _metrics.heightPixels - _padding - _systemTitleBarHeight;	// TODO apply scaling to this
    	_layoutWidth = _dialogWidth;
    	_layoutHeight = _dialogHeight - _titleHeight;
	}
	
	public int createDescription()
	{
    	// create the Description Label
    	// TODO apply scaling to this
        String[] descArray = _gameBoard.getResources().getStringArray(R.array.MATCH_DESCRIPTION_ARRAY);
        
        // if no description, return a zero height
        if (descArray == null || descArray.length == 0 || descArray[_imageID] == "")
        	return 0;
        
        TextView descView = new TextView(_gameBoard);
        descView.setSingleLine(false);
        descView.setTextColor(R.color.CurrentPlayerText);
        LayoutParams descLP = new LayoutParams(_layoutWidth - (int) (_padding / 2.0f), _descriptionHeight);
        descLP.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        descView.setLayoutParams(descLP);
        descView.setText(descArray[_imageID]);
    	float textAscent = descView.getPaint().ascent();
    	float textDescent = descView.getPaint().descent();
    	Rect bounds = new Rect();
    	descView.getPaint().getTextBounds(descArray[_imageID], 0, descArray[_imageID].length(), bounds);
    	System.out.println("bounds = " + bounds.width() + ", " + bounds.height());
    	int textHeight = (int) (textDescent - textAscent); // TODO this is working since it is a multi line text view
    	System.out.println("createDescription(): textAscent = " + textAscent + ", textDescent = " + textDescent+ ", textHeight = " + textHeight);
    	float numLines = (float) bounds.width() / (float) (_layoutWidth - _padding);	
    	numLines += 1.0f; // throw in an extra line because the above calculation is not very precise
    	_descriptionHeight = textHeight * (int) Math.ceil(numLines);
    	descLP.height = _descriptionHeight + (int) ((float) _padding / 2f);	// divide by 4 instead of 2 because the extra line above should usually provide extra padding
    	descView.setLayoutParams(descLP);
      _frameLayout.addView(descView);
        
        return _descriptionHeight;
	}
	
	private void calculateImageDimensions(int descHeight)
	{
		_imageViewWidth = _layoutWidth;
		_imageViewHeight = _layoutHeight - descHeight - _padding;  

    	// calculate imageView dimensions
    	// TODO apply scaling to this
    	_imageDrawable = getDrawable(_imageID);
		int imageWidth = _imageDrawable.getIntrinsicWidth();
		int imageHeight = _imageDrawable.getIntrinsicHeight();

		float widthScale = (float) _imageViewWidth / (float) imageWidth;
		float heightScale = (float) _imageViewHeight / (float) imageHeight;
		_imageScale = Math.min(widthScale, heightScale);
//		_imageScale *= 1.67f;	// "zoom in" to the image part of the photo
	}
	
	private void createImageView()
	{
    	// create the image view
		LayoutParams lp = new LayoutParams(_imageViewWidth, _imageViewHeight);
		lp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
		
		if (_frameLayout != null)
		{


		    if (VERSION.SDK_INT > 4)
	    	{
				_touchView = new TouchImageView(_gameBoard);
				_touchView.setImage(_imageDrawable, _imageViewWidth, _imageViewHeight, _imageScale);
//				_touchView.setImageDrawable(_imageDrawable);
//				_touchView.setScaleType(ScaleType.MATRIX);
//				_touchView.setBackgroundColor(R.color.CurrentPlayerBack);
//				Matrix m = new Matrix();
//				m.setScale(_imageScale, _imageScale);
//				_touchView.setImageMatrix(m);
				_touchView.setAdjustViewBounds(false);
				_touchView.setLayoutParams(lp);
				_frameLayout.addView(_touchView);
	    	}
	    	else
	    	{
	    		_imageView = new ImageView(_gameBoard);
//	    		_imageView.setBackgroundColor(R.color.CurrentPlayerBack);
	    		_imageView.setScaleType(ScaleType.MATRIX);
	    		Matrix m = new Matrix();
	    		m.setScale(_imageScale, _imageScale);
	    		_imageView.setImageMatrix(m);
	    		_imageView.setImageDrawable(_imageDrawable);
	    		_imageView.setAdjustViewBounds(true);
	    		_imageView.setLayoutParams(lp);
	    		_frameLayout.addView(_imageView);
	    		
	    	}
		}
	}
	
	private Drawable getDrawable(int index)
	{
		try
		{
	    	String packageName = _gameBoard.getPackageName();
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
	        
	        if (draw != null)
	        {
	            String resString = "image_" + Integer.toString(index);
	            int id = (int) draw.getField(resString).getInt(draw);
	            return _gameBoard.getResources().getDrawable(id);
	        }
	        else
	        	return _gameBoard.getResources().getDrawable(R.drawable.icon);

        }
        catch (Exception ex)
        {
        	System.out.println("FAILED to get get drawable for index= " + index);
        	System.out.println(ex.toString());
        	return _gameBoard.getResources().getDrawable(R.drawable.icon);
        }

	}
	
    private class CancelListener implements android.content.DialogInterface.OnCancelListener {

		@Override
		public void onCancel(DialogInterface arg0) {
			ShowMatchResult.this.dismiss();
			_gameBoard.matchResultCancelled();
			System.out.println("ShowMatchResult.onCancel called: Destructing ImageView Object.");
			if (_imageView != null)
				((BitmapDrawable)_imageView.getDrawable()).getBitmap().recycle(); 
			System.gc();
		}
    }

}