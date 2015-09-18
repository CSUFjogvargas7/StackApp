package com.mugwumps.stackapp;

import android.app.*;
import android.content.*;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.*;
import android.text.TextUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import com.mugwumps.stackapp.*;

import java.util.*;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class StackApp extends Activity {

	int item;
	private EditText digitField;
	public int digit;
	private String stackString;
	TextView stackDisplay;
	Button Pushbutton;
	Button PopButton;
	Button ClearButton;
	Button QuitButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stack_app);
		
		stackDisplay = (TextView)findViewById(R.id.StackContents);
		Pushbutton=(Button)findViewById(R.id.button1);
		PopButton=(Button)findViewById(R.id.buttonPop);
		ClearButton=(Button)findViewById(R.id.buttonClear);
		QuitButton=(Button)findViewById(R.id.buttonQuit);
		stackDisplay = (TextView)findViewById(R.id.StackContents);
				
		Pushbutton.setOnClickListener(pushStack);
		PopButton.setOnClickListener(popStack);
		ClearButton.setOnClickListener(clearStack);
		QuitButton.setOnClickListener(quitApplication);
	}

	private OnClickListener pushStack = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			digitField = (EditText)findViewById(R.id.editText1);
			String digitString = digitField.getText().toString();
			if(TextUtils.isEmpty(digitString)){
				InfoMessage = "Nothing to push";
				digitField.setError(InfoMessage);
				return;
			} else {
				digit = Integer.parseInt(digitString);
				push(digit);
				stackString = view(); 
				stackDisplay.setText(stackString);
			}
			Toast toast = new Toast (getApplicationContext());
			toast.setGravity(Gravity.TOP, 0, 0);
			toast.makeText(StackApp.this, InfoMessage, toast.LENGTH_SHORT).show();
			digitField.setText("");
		}
	};
	
	private OnClickListener quitApplication = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
			toneG.startTone(ToneGenerator.TONE_PROP_BEEP); 
			finish();
			//System.exit(0);
		}
	};
	
	private OnClickListener clearStack = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			
					
		}
	};
	
	private OnClickListener popStack = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stack_app, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	//SECTION WHERE ALL THE METHODS ARE IMPLEMENTED
	private int maxSize = 3;
	public Object [] stack = new Object[maxSize]; 
	public int top = 0;
	public String InfoMessage;
	//haven't tested this
	public void push(Object item) {
		if (top == stack.length){
			InfoMessage = "Stack is full";
			return;
		} else {
			stack[top++] = item;
			InfoMessage = String.valueOf(item) + " is pushed to the stack";
			return;
		}
	}

	public String view() {
		StringBuffer SB = new StringBuffer();
		SB.append(" [ ");
		for (int i = 0; i < maxSize; i++){
			if(stack[i] == null)
				SB.append("_ ");
			else
				SB.append(stack[i] + " ");
		}
		SB.append("] ");
		return SB.toString();
	}
	
	public boolean isEmpty() {
		return top == 0;
	}
	
}
