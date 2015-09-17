package com.mugwumps.stackapp.test;

import junit.framework.TestCase;
import android.widget.*;

import java.util.Arrays;

import com.mugwumps.stackapp.*;
import com.mugwumps.stackapp.R;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class stackappTest extends ActivityInstrumentationTestCase2 {
	
	TextView stackLabel, stackDisplay;
	EditText digitField;
	Button pushButton;
	StackApp activity;
	
	
	public stackappTest(String name){
		super("com.mugwumps", StackApp.class);
		setName(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		//Find Views
		activity = (StackApp) getActivity();
		stackLabel = (TextView)activity.findViewById(R.id.textView1);
		stackDisplay = (TextView)activity.findViewById(R.id.StackContents);
		pushButton = (Button)activity.findViewById(R.id.button1);
		digitField = (EditText)activity.findViewById(R.id.editText1);
	}
	
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	@SmallTest
	//checks if launching app creates the right views
	public void testStackAppViewsCreated(){
		assertNotNull(R.layout.activity_stack_app);
		assertNotNull(getActivity());
		assertNotNull(stackLabel);
		assertNotNull(stackDisplay);
		assertNotNull(pushButton);
		assertNotNull(digitField);
	}
	
	@SmallTest
	//checks if views are visible
	public void testStackAppViewsVisible(){
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),stackDisplay);
		ViewAsserts.assertOnScreen(stackDisplay.getRootView(),stackLabel);
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),pushButton);
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),digitField);
	}
	
	@SmallTest
	//checks if field is being initialized to empty
	public void testStackStartsEmpty(){
		super.assertEquals("digit field not empty","", digitField.getText().toString());
	}
	
	@SmallTest
	//checks if stack is initialized empty
	public void testStackInitializedEmpty(){
		assertTrue(activity.isEmpty());
	}
	
	@SmallTest
	//checks if stack is initialized empty
	public void testInputFieldClearedAfterPushSingleIntAccepted(){
		//simulate an input of a number using keyboard
		TouchUtils.tapView(this, digitField);
		sendKeys("1");
				
		TouchUtils.clickView(this, pushButton);
		
		super.assertEquals("Digit field not cleared","",digitField.getText().toString());
	}

	@SmallTest
	//checks if push button functions the way it should
	public void testPushbutton(){
		digitField.clearComposingText();
		
		//simulate a tap
		TouchUtils.tapView(this, digitField);
		sendKeys("1");
		int numberInField;
		numberInField = Integer.parseInt(digitField.getText().toString());
		
		TouchUtils.clickView(this, pushButton);
			
		Object digit;
		digit = activity.stack[0];
		
		assertEquals("Number in stack is different than in field", Integer.parseInt(digit.toString()), numberInField);
	}
	
	@SmallTest
	//checks if push method's logic is right
	public void testPush(){
		int digit1 = 2;
		int digit2 = 3;
		int digit3 = 5;
		int digit4 = 9;
		int stack[] = new int[3];
		StringBuffer SB = new StringBuffer();
		SB.append(" [ ");
		
		activity.push(digit1);
		stack[0] = digit1;
		assertFalse(activity.isEmpty());
		assertEquals(activity.view(),SB.append(stack[0]+ " ").append("_ _ ] ").toString());
		assertEquals(activity.top, 1);
		
		SB.delete(5, SB.length());
		activity.push(digit2);
		stack[0] = digit1;
		stack[1] = digit2;
		assertFalse(activity.isEmpty());
		assertEquals(activity.view(),SB.append(stack[1]+ " ").append("_ ] ").toString());
		assertEquals(activity.top,2);
		
		SB.delete(7, SB.length());
		activity.push(digit3);
		stack[0] = digit1;
		stack[1] = digit2;
		stack[2] = digit3;
		assertFalse(activity.isEmpty());
		assertEquals(activity.view(),SB.append(stack[2]+ " ").append("] ").toString());
		assertEquals(activity.top,3);
		
		
		activity.push(digit4);
		stack[0] = digit1;
		stack[1] = digit2;
		stack[2] = digit3;
		assertFalse(activity.isEmpty());
		assertEquals(activity.InfoMessage, "Stack is full");
		assertEquals(activity.view(),SB.toString());
		assertEquals(activity.top,3);
		
	}
}
