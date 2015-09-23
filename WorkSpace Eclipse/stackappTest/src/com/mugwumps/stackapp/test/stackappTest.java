package com.mugwumps.stackapp.test;

import junit.framework.TestCase;
import android.widget.*;

import java.util.Arrays;

import com.mugwumps.stackapp.*;
import com.mugwumps.stackapp.R;

import android.app.Activity;
import android.content.Context;
import android.media.ToneGenerator;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class stackappTest extends ActivityInstrumentationTestCase2 {
	
	TextView stackLabel, stackDisplay;
	EditText digitField;
	Button pushButton;
	Button popButton;
	Button clearStack;
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
		pushButton = (Button)activity.findViewById(R.id.buttonPush);
		digitField = (EditText)activity.findViewById(R.id.editText1);
		popButton = (Button)activity.findViewById(R.id.buttonPop);
		clearStack = (Button)activity.findViewById(R.id.buttonClear);
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
		assertNotNull(popButton);
		assertNotNull(clearStack);
		
	}
	
	@SmallTest
	//checks if views are visible
	public void testStackAppViewsVisible(){
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),stackDisplay);
		ViewAsserts.assertOnScreen(stackDisplay.getRootView(),stackLabel);
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),pushButton);
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),digitField);
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),popButton);
		ViewAsserts.assertOnScreen(stackLabel.getRootView(),clearStack);
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
		digitField.clearComposingText();
		
		//simulate an input of a number using keyboard
		TouchUtils.tapView(this, digitField);
		sendKeys("1");
				
		TouchUtils.clickView(this, pushButton);
		super.assertEquals("Digit field not cleared","",digitField.getText().toString());
	}

	@SmallTest
	//checks if push button functions the way it should
	public void testPushbutton(){
		//Arrange
		digitField.clearComposingText();
		Object digit;
		int numberInField;
		
		//simulate a blank push
		TouchUtils.clickView(this, pushButton);
		super.assertTrue(TextUtils.isEmpty(digitField.getText().toString()));
		
		//simulate a key entered
		TouchUtils.tapView(this, digitField);
		sendKeys("1");
		numberInField = Integer.parseInt(digitField.getText().toString());
		TouchUtils.clickView(this, pushButton);
		digit = activity.stack[0];
		super.assertEquals("Number in stack is different than in field", Integer.parseInt(digit.toString()), numberInField);
	}
	
	@SmallTest
	//checks if push button functions the way it should
	public void testPopbutton(){
		
		String stacktest = " [ 1 2 _ ] ";
		activity.push(1);
		activity.push(2);
		activity.push(3);
		
		//simulate a blank pop
		TouchUtils.clickView(this, popButton);
		
		super.assertEquals(stacktest, activity.view());
	}
	
	@SmallTest
	//checks if clear button functions the way it should
	public void testClearButton(){
		
		String stacktest = " [ _ _ _ ] ";
		activity.push(1);
		activity.push(2);
		activity.push(3);
		
		//simulate a blank pop
		TouchUtils.clickView(this, clearStack);
		
		super.assertEquals(stacktest, activity.view());
	}
	
	@SmallTest
	//checks if maxLength of stack is 1
	public void testMaxLengthOfInputIsOneDigit(){
		//simulate an input of a number using keyboard
		TouchUtils.tapView(this, digitField);
		sendKeys("1");
		TouchUtils.tapView(this, digitField);
		sendKeys("2");
		TouchUtils.tapView(this, digitField);
		sendKeys("3");

		super.assertEquals("1", digitField.getText().toString());
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
		assertEquals(activity.infoMessage, "Stack is full");
		assertEquals(activity.view(),SB.toString());
		assertEquals(activity.top,3);
		
	}
	
	@SmallTest
	//checks if clear method's logic is right
	public void testClear(){
		activity.push(1);
		activity.push(2);
		activity.push(3);
		
		activity.clear();
		
		assertTrue(activity.isEmpty());
	}
	
	@SmallTest
	//checks if pop method's logic is right
	public void testPop(){
		Object digit1 = 2;
		Object digit2 = 3;
		Object digit3 = 5;
		activity.push(digit1);
		activity.push(digit2);
		activity.push(digit3);
		StringBuffer SB = new StringBuffer();
		SB.append(" [ ");
		
		activity.pop();
		assertEquals(activity.view(),SB.append(digit1+ " ").append(digit2+" ").append("_ ] ").toString());
		assertEquals(activity.top, 2);
		
		SB.delete(5, SB.length());
		activity.pop();
		assertEquals(activity.view(),SB.append("_ _ ] ").toString());
		assertEquals(activity.top,1);
		
		SB.delete(3, SB.length());
		activity.pop();
		assertEquals(activity.view(),SB.append("_ _ _ ] ").toString());
		assertEquals(activity.top,0);
		
		activity.pop();;
		assertTrue(activity.isEmpty());
		assertEquals(activity.infoMessage, "Stack is Empty");
		assertEquals(activity.view(),SB.toString());
		assertEquals(activity.top,0);
		
	}
}