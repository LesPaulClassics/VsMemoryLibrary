package com.tsop.android.memory.library;

import android.app.Dialog;
import android.content.Context;

/// <summary>
/// Summary description for HighScoreEntry.
/// </summary>
public class HighScoreEntryForm extends Dialog
{

	public HighScoreEntryForm(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
//	private System.Windows.Forms.TextBox textBoxName;
//	private System.Windows.Forms.Label label1;
//	private System.Windows.Forms.Label label2;
//	/// <summary>
//	/// Required designer variable.
//	/// </summary>
//	private System.ComponentModel.Container components = null;
//
//	/// <summary>
//	/// Default Constructor
//	/// </summary>
//	public HighScoreEntryForm()
//	{
//		//
//		// Required for Windows Form Designer support
//		//
//		InitializeComponent();
//
//	}
//
//	/// <summary>
//	/// Constructor that accepts the dialog boxes title as a parameter
//	/// </summary>
//	/// <param name="title">
//	/// Title for the dialog box</param>
//	public HighScoreEntryForm(string title)
//	{
//		InitializeComponent();
//
//		this.Text = title;
//
//	}
//
//	/// <summary>
//	/// Clean up any resources being used.
//	/// </summary>
//	protected override void Dispose( bool disposing )
//	{
//		if( disposing )
//		{
//			if(components != null)
//			{
//				components.Dispose();
//			}
//		}
//		base.Dispose( disposing );
//	}
//
//	#region Windows Form Designer generated code
//	/// <summary>
//	/// Required method for Designer support - do not modify
//	/// the contents of this method with the code editor.
//	/// </summary>
//	private void InitializeComponent()
//	{
//		System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(HighScoreEntryForm));
//		this.textBoxName = new System.Windows.Forms.TextBox();
//		this.label1 = new System.Windows.Forms.Label();
//		this.label2 = new System.Windows.Forms.Label();
//		this.SuspendLayout();
//		// 
//		// textBoxName
//		// 
//		this.textBoxName.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(255)), ((System.Byte)(255)), ((System.Byte)(200)));
//		this.textBoxName.ForeColor = System.Drawing.Color.MidnightBlue;
//		this.textBoxName.Location = new System.Drawing.Point(176, 72);
//		this.textBoxName.MaxLength = 16;
//		this.textBoxName.Name = "textBoxName";
//		this.textBoxName.Size = new System.Drawing.Size(152, 20);
//		this.textBoxName.TabIndex = 0;
//		this.textBoxName.Text = "";
//		this.textBoxName.KeyUp += new System.Windows.Forms.KeyEventHandler(this.textBoxName_KeyUp);
//		// 
//		// label1
//		// 
//		this.label1.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
//		this.label1.Location = new System.Drawing.Point(16, 16);
//		this.label1.Name = "label1";
//		this.label1.Size = new System.Drawing.Size(312, 48);
//		this.label1.TabIndex = 1;
//		this.label1.Text = "Your high score from this game will be saved in the Hall Of Fame!";
//		// 
//		// label2
//		// 
//		this.label2.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
//		this.label2.Location = new System.Drawing.Point(16, 72);
//		this.label2.Name = "label2";
//		this.label2.Size = new System.Drawing.Size(152, 32);
//		this.label2.TabIndex = 2;
//		this.label2.Text = "Enter your name:";
//		// 
//		// HighScoreEntryForm
//		// 
//		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
//		this.BackColor = System.Drawing.Color.CornflowerBlue;
//		this.ClientSize = new System.Drawing.Size(344, 118);
//		this.ControlBox = false;
//		this.Controls.AddRange(new System.Windows.Forms.Control[] {
//																	  this.label2,
//																	  this.label1,
//																	  this.textBoxName});
//		this.ForeColor = System.Drawing.Color.FromArgb(((System.Byte)(255)), ((System.Byte)(255)), ((System.Byte)(200)));
//		this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
//		this.MaximizeBox = false;
//		this.MinimizeBox = false;
//		this.Name = "HighScoreEntryForm";
//		this.ShowInTaskbar = false;
//		this.Text = "Congratulations!";
//		this.ResumeLayout(false);
//
//	}
//	#endregion
//
//	/// <summary>
//	/// Event Handler that closes the dialog box when ENTER is pressed
//	/// </summary>
//	private void textBoxName_KeyUp(object sender, System.Windows.Forms.KeyEventArgs e)
//	{
//		if (e.KeyCode == System.Windows.Forms.Keys.Enter)
//		{
//			this.Close();
//		}
//	}
//
//	/// <summary>
//	/// Field that contains the name entered in the dialog box
//	/// </summary>
//	private string playerName = "<none>";
//
//	/// <summary>
//	/// Property that returns the name entered in the dialog box
//	/// </summary>
//	public string PlayerName
//	{
//		get
//		{
//			return playerName;
//		}
//	}
//
//
//	/// <summary>
//	/// Event Handler that saves the text entered in the textbox
//	/// </summary>
//	protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
//	{
//		playerName = textBoxName.Text;
//
//		base.OnClosing(e);
//	}

	


	
}
