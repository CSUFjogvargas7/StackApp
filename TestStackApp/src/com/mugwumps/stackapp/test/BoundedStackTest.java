package com.mugwumps.stackapp.test;

import junit.framework.TestCase;
import com.mugwumps.stackapp.*;
import java.util.*;

import org.junit.Test;


public class BoundedStackTest extends TestCase {

	protected BoundedStack s;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testStackInitializedEmpty(){
		assertTrue(s.isEmpty());
		assertEquals(s.size(),0);
	}
	
	@Test
	public void testPush(){
		int digit1 = 2;
		int digit2 = 10;
		int digit3 = 59;
		int digit4 = 3;
		int digit5 = 5;
		int digit6 = 9;
		int stack[] = new int[3];
		
		s.push(digit1);
		stack[0] = digit1;
		assertFalse(s.isEmpty());
		assertEquals(s.view(),stack);
		assertEquals(s.size(), 1);
		
		s.push(digit2);
		assertFalse(s.isEmpty());
		assertEquals(s.view(),stack);
		assertEquals(s.size(),1);
		
		s.push(digit3);
		assertFalse(s.isEmpty());
		assertEquals(s.view(),stack);
		assertEquals(s.size(),1);
		
		s.push(digit4);
		stack[1] = digit2;
		assertFalse(s.isEmpty());
		assertEquals(s.view(),stack);
		assertEquals(s.size(),1);
		
		s.push(digit5);
		stack[2] = digit3;
		assertFalse(s.isEmpty());
		assertEquals(s.view(),stack);
		assertEquals(s.size(),1);
		
		s.push(digit6);
		assertFalse(s.isEmpty());
		assertEquals(BoundedStack.InfoMessage, "Stack is full");
		assertEquals(s.view(),stack);
		assertEquals(s.size(),1);
		
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	

}
