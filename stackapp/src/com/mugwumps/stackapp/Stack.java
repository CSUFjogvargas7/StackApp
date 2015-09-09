package com.mugwumps.stackapp;


//Create a small stack interface
public interface Stack {

	void push(int item);
	
	int pop();
	
	int[] view();
	
	int size();
	
	boolean isEmpty();
}
