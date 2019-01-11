package com.tsop.android.memory.library.test;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.tsop.android.memory.library.*;

public class TestDialog extends Dialog {
	
	private int selectedIndex;
	private OnTestSelectionListener testSelectionListener;
	
	public TestDialog(Context context, OnTestSelectionListener selectionListener) {
		super(context);
		this.testSelectionListener = selectionListener;
	}
	
	public interface OnTestSelectionListener {
	     public void testSelection(int selectedIndex);
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.tsop.android.memory.library.R.layout.test_dialog);
		
		Spinner matchSpinner = (Spinner) this.findViewById(com.tsop.android.memory.library.R.id.matchSpinner);
		
		matchSpinner.setOnItemSelectedListener(new SpinnerClickListener());
	}

	private class SpinnerClickListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			System.out.println("Selected Item index = " + arg2);
			if (arg2 > 0)
			{
				selectedIndex = arg2;
				TestDialog.this.testSelectionListener.testSelection(arg2);
				TestDialog.this.dismiss();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
