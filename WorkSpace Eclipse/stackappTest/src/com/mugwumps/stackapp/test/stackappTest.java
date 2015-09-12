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
	//checks if push button functions the way it should
	public void testPushbutton(){
		digitField.clearComposingText();
		
		//simulate a tap
		TouchUtils.tapView(this, digitField);
		sendKeys("1");
		int numberInField;
		numberInField = Integer.parseInt(digitField.getText().toString());
		
		TouchUtils.clickView(this, pushButton);
		//activity.push(numberInField);
			
		int digit;
		digit = activity.stack[0];
		
		assertEquals("Number in stack is different than in field", digit, numberInField);
	}
	
	@SmallTest
	//checks if push method's logic is right
	public void testPush(){
		int digit1 = 2;
		int digit2 = 10;
		int digit3 = 59;
		int digit4 = 3;
		int digit5 = 5;
		int digit6 = 9;
		int stack[] = new int[3];
		
		activity.push(digit1);
		stack[0] = digit1;
		assertFalse(activity.isEmpty());
		assertEquals(Arrays.toString(stack),activity.view());
		assertEquals(activity.size, 1);
		
		activity.push(digit2);
		stack[0] = digit1;
		assertFalse(activity.isEmpty());
		assertEquals(Arrays.toString(stack),activity.view());
		assertEquals(activity.size,1);
		
		activity.push(digit3);
		stack[0] = digit1;
		assertFalse(activity.isEmpty());
		assertEquals(activity.view(),Arrays.toString(stack));
		assertEquals(activity.size,1);
		
		activity.push(digit4);
		stack[0] = digit1;
		stack[1] = digit4;
		assertFalse(activity.isEmpty());
		assertEquals(activity.view(),Arrays.toString(stack));
		assertEquals(activity.size,2);
		
		activity.push(digit5);
		stack[0] = digit1;
		stack[1] = digit4;
		stack[2] = digit5;
		assertFalse(activity.isEmpty());
		assertEquals(activity.view(),Arrays.toString(stack));
		assertEquals(activity.size,3);
		
		activity.push(digit6);
		stack[0] = digit1;
		stack[1] = digit4;
		stack[2] = digit5;
		assertFalse(activity.isEmpty());
		assertEquals(activity.InfoMessage, "Stack is full");
		assertEquals(activity.view(),Arrays.toString(stack));
		assertEquals(activity.size,3);
		
	}
}
