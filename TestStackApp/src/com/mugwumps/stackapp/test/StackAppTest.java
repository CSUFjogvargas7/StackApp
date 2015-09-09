package com.mugwumps.stackapp.test;

import junit.framework.TestCase;

import java.util.Stack;

import com.mugwumps.stackapp.*;

import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

public class StackAppTest extends TestCase {
	
	StackApp activity;
	Button PushButton;
	
	public StackAppTest(String name) {
		super("com.mugwumps.stackapp");
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		//Find views

	}
	
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//checks if launching app creates the right views
	public void StackAppViewsCreated(){
		assertNotNull(R.layout.activity_main);
	}
	
}
