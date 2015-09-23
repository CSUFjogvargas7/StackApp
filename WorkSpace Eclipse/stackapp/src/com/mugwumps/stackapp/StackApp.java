package com.mugwumps.stackapp;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

public class StackApp extends Activity {

  int item;
  private Stack undoStack = new Stack();
  private Stack objStack = new Stack();
  Object objPopped = null;
  Object funcPopped = null;
  
  private EditText digitField;
  public int digit;
  private String stackString;
  TextView stackDisplay;
  Button pushButton;
  Button popButton;
  Button clearButton;
  Button quitButton;
  Button undoButton;
  Toast toast;

  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stack_app);
    
    stackDisplay = (TextView)findViewById(R.id.StackContents);
    pushButton = (Button)findViewById(R.id.buttonPush);
    popButton = (Button)findViewById(R.id.buttonPop);
    clearButton = (Button)findViewById(R.id.buttonClear);
    quitButton = (Button)findViewById(R.id.buttonQuit);
    undoButton = (Button)findViewById(R.id.buttonUndo);
    stackDisplay = (TextView)findViewById(R.id.StackContents);

    pushButton.setOnClickListener(pushStack);
    popButton.setOnClickListener(popStack);
    clearButton.setOnClickListener(clearStack);
    quitButton.setOnClickListener(quitApplication);
    undoButton.setOnClickListener(undoFunction);
  }

  private OnClickListener pushStack = new OnClickListener(){

    @Override
    public void onClick(View pushButton) {
      digitField = (EditText)findViewById(R.id.editText1);
      String digitString = digitField.getText().toString();
      if (TextUtils.isEmpty(digitString)) {
        infoMessage = "Nothing to push";
        digitField.setError(infoMessage);
        return;
      } else {
        digit = Integer.parseInt(digitString);
        push(digit);
     //   startAnimation(pushButton);
        stackString = view(); 
        stackDisplay.setText(stackString);
      }
      showToast(infoMessage);
      digitField.setText("");
    }
  };

  private OnClickListener undoFunction = new OnClickListener(){

    @Override
    public void onClick(View undoButton) {
      if (undoStack.isEmpty()){
        infoMessage = "Nothing to Undo";
        showToast(infoMessage);
        return;
      } else {
        undo();
      }
      stackString = view(); 
      stackDisplay.setText(stackString);

    }
  };
  
  private OnClickListener quitApplication = new OnClickListener(){

    @Override
    public void onClick(View quitButton) {
      ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
      toneG.startTone(ToneGenerator.TONE_PROP_BEEP); 
      objStack = null;
      undoStack = null;
      finish();
    }
  };

  private OnClickListener clearStack = new OnClickListener(){

    @Override
    public void onClick(View clearButton) {
      clear();
      showToast(infoMessage);
      stackString = view(); 
      stackDisplay.setText(stackString);
    }
  };

  private OnClickListener popStack = new OnClickListener(){

    @Override
    public void onClick(View popButton) {
      pop();
      //startAnimation(popButton);
      stackString = view(); 
      stackDisplay.setText(stackString);
      showToast(infoMessage);
    }
  };
  
  private void showToast(String msg){
    toast = new Toast(getApplicationContext());
    toast.setGravity(Gravity.TOP, 0, 0);
    Toast.makeText(StackApp.this, msg, Toast.LENGTH_SHORT).show();
  }

  private int maxSize = 3;
  public Object [] stack = new Object[maxSize]; 
  public int top = 0;
  public String infoMessage;
  private int size = 3;
  
  public void push(Object item) {
    if (top == stack.length) {
      infoMessage = "Stack is full";
      return;
    } else {
      stack[top++] = item;
      infoMessage = String.valueOf(item) + " is pushed to the stack";
      undoStack.push(pushButton);
      objStack.push(item);
      return;
    }
  }

  public String view() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(" [ ");
    for (int i = 0; i < size; i++) {
      if (stack[i] == null) {
        stringBuffer.append("_ ");
      } else {
        stringBuffer.append(stack[i] + " ");
      }
    }
    stringBuffer.append("] ");
    return stringBuffer.toString();
  }

  public void pop() {
    if (isEmpty()) {
      infoMessage = "Stack is Empty";
      return;
    }
    Object obj = stack[top - 1];
    stack[--top] = null;
    infoMessage = String.valueOf(obj) + " is popped from the stack";
    undoStack.push(popButton);
    objStack.push(obj);
    return;
  }

  public void clear() {
    for (int i = 0; i < size; i++ ) {
      stack[i] = null;
    }
    top = 0;
    infoMessage = "Stack is clear";
    return;
  }

  public boolean isEmpty() {
    return top == 0;
  }
  
  public void undo() { 
    
    if (undoStack.peek() == pushButton) {
      objPopped = objStack.pop();
      funcPopped = pushButton;
      pop();
      
      undoStack.pop();
      objStack.push(objPopped);
      undoStack.push(undoButton);
      
    } else if (undoStack.peek() == popButton) {
      objPopped = objStack.pop();
      funcPopped = popButton;
      push(objPopped);
      
      undoStack.pop();
      objStack.push(objPopped);
      undoStack.push(undoButton);
      
    } else if (undoStack.peek() == undoButton) {
      objPopped = objStack.pop();
      if (funcPopped == pushButton) {
        push(objPopped);
        undoStack.pop();
        objStack.pop();
      } else if (funcPopped == popButton) {
        pop();
        undoStack.pop();
        objStack.pop();
      }
      undoStack.push("");
      objStack.push("");
    } else {
      infoMessage = "Nothing to Undo";
      showToast(infoMessage);
      return;
    }
    
  }
}
