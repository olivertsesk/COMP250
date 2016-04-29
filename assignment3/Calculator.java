package a3;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/* ACADEMIC INTEGRITY STATEMENT
*
* By submitting this file, we state that all group members associated
* with the assignment understand the meaning and consequences of cheating,
* plagiarism and other academic offenses under the Code of Student Conduct
* and Disciplinary Procedures (see www.mcgill.ca/students/srr for more information).
*
* By submitting this assignment, we state that the members of the group
* associated with this assignment claim exclusive credit as the authors of the
* content of the file (except for the solution skeleton provided).
*
* In particular, this means that no part of the solution originates from:
* - anyone not in the assignment group
* - Internet resources of any kind.
*
* This assignment is subject to inspection by plagiarism detection software.
*
* Evidence of plagiarism will be forwarded to the Faculty of Science's disciplinary
* officer.
*/

@SuppressWarnings("serial")
public class Calculator extends JFrame implements ActionListener
{
	private static final Color LIGHT_RED = new Color(214,163,182);
	
	private final JTextField aText = new JTextField(40);
	
	public Calculator()
	{
		setTitle("COMP 250 Calculator");
		setLayout(new GridLayout(2, 1));
		setResizable(false);
		add(aText);
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				aText.setText("");		
				aText.requestFocusInWindow();
			}
		});
		add(clear);
		
		aText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		aText.addActionListener(this);

		aText.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				aText.getHighlighter().removeAllHighlights();	
			}
			
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				aText.getHighlighter().removeAllHighlights();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				aText.getHighlighter().removeAllHighlights();
			}
		});
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Run this main method to start the calculator
	 * @param args Not used.
	 */
	public static void main(String[] args)
	{
		new Calculator();
	}
	
	/* 
	 * Responds to events by the user. Do not modify this method.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if( aText.getText().contains("="))
		{
			aText.setText("");		
		}
		else
		{
			Queue<Token> expression = processExpression(aText.getText());
			if( expression != null )
			{
				String result = evaluateExpression(expression, aText.getText());
				if( result != null )
				{
					aText.setText(aText.getText() + " = " + result);
				}
			}
		}
	}
	
	/**
	 * Highlights a section of the text field with a color indicating an error.
	 * Any change to the text field erase the highlights.
	 * Call this method in your own code to highlight part of the equation to 
	 * indicate an error.
	 * @param pBegin The index of the first character to highlight.
	 * @param pEnd The index of the first character not to highlight.
	 */
	public void flagError( int pBegin, int pEnd )
	{
		assert pEnd > pBegin;
		try
		{
			aText.getHighlighter().addHighlight(pBegin, pEnd, new DefaultHighlighter.DefaultHighlightPainter(LIGHT_RED));
		}
		catch(BadLocationException ignored)
		{
			
		}
	}
	
	/******************** BEGIN ASSIGNMENT CODE ********************/
	
	/**
	 * Tokenizes pExpression (see Tokenizer, below), and 
	 * returns a Queue of Tokens that describe the original 
	 * mathematical expression in reverse Polish notation (RPN).
	 * Flags errors and returns null if the expression
	 * a) contains any symbol except spaces, digits, round parentheses, or operators (+,-,*,/)
	 * b) contains unbalanced parentheses
	 * c) contains invalid combinations of parentheses and numbers, e.g., 2(3)(4)
	 * 
	 * @param pExpression A string.
	 * @return The tokenized expression transformed in RPN
	 */
	private Queue<Token> processExpression(String pExpression)
	{
        Tokenizer tokenizer = new Tokenizer();
        try {
            tokenizer.tokenize(pExpression);
            tokenizer.parse(pExpression);
        } catch (InvalidExpressionException e) {
            System.out.println("flag error");
            flagError(e.getPosition(), e.getPosition()+1);
            return null;
        }
        return tokenizer.getTokenList();
	}
	
	/**
	 * Assumes pExpression is a Queue of tokens produced as the output of processExpression.
	 * Evaluates the answer to the expression. The division operator performs a floating-point 
	 * division. 
	 * Flags errors and returns null if the expression is an invalid RPN expression e.g., 1+-
	 * @param pExpression The expression in RPN
	 * @return A string representation of the answer)
	 */
	private String evaluateExpression(Queue<Token> pExpression, String pString)
	{
        try {
            Stack<Integer> tempStack = new Stack<Integer>();
            while (!pExpression.isEmpty()){
                Token token = pExpression.remove();

                if (token.getType() == Token.TYPE.INTEGER) {
                    try {
                        tempStack.push(
                                Integer.parseInt(
                                        pString.substring(token.getStart(), token.getEnd()).replaceAll(" ", "")));
                    }
                    catch (NumberFormatException e){
                        flagError(token.getStart(), token.getEnd());
                    }

                } else if (token.getType() == Token.TYPE.OPERATOR){
                    String opString = pString.substring(token.getStart(), token.getEnd());
                    opString = opString.replaceAll(" ", "");
                    if (opString.length()!=1){
                        //consuming failed
                        return null;
                    }

                    int b = tempStack.pop();
                    int a = tempStack.pop();

                    switch (opString.charAt(0)){
                        case '+':
                            tempStack.push(a+b);
                            break;
                        case '-':
                            tempStack.push(a-b);
                            break;
                        case '*':
                            tempStack.push(a*b);
                            break;
                        case '/':
                            tempStack.push(a/b);
                            break;
                        default:
                            //not one of the elementary operations
                            flagError(token.getStart(), token.getEnd());
                            return null;
                    }
                } else {
                    flagError(token.getStart(), token.getEnd());
                    return null;
                }
            }
            int result = tempStack.pop();
            if (!tempStack.empty()){
                //more entry in the temp Stack than can be treated
                return null;
            }
            return String.format("%s", result);
        }
        catch (Exception e){
            return null;
        }
	}
}

/**
 * Use this class as the root class of a hierarchy of token classes
 * that can represent the following types of tokens:
 * a) Integers (e.g., "123" "4", or "345") Negative numbers are not allowed as inputs
 * b) Parentheses '(' or ')'
 * c) Operators '+', '-', '/', '*' Hint: consider using the Comparable interface to support
 * comparing operators for precedence
 */
class Token
{
    public enum TYPE {INTEGER, PARENTHESE_OPEN, PARENTHESE_CLOSE, OPERATOR, NONE}
	private int aStart;
	private int aEnd;
	private TYPE aType;
	/**
	 * @param pStart The index of the first character of this token
	 * in the original expression.
	 * @param pEnd The index + 1 of the last character of this token in
	 * the original expression
	 */
	public Token( int pStart, int pEnd )
	{
		aStart = pStart;
		aEnd = pEnd;
        aType = TYPE.NONE;
	}


    public Token( int pStart, int pEnd, TYPE pType )
    {
        aStart = pStart;
        aEnd = pEnd;
        this.aType = pType;
    }

    public void extendByOne(){
        aEnd++;
    }

    public TYPE getType(){
        return this.aType;
    }

	public int getStart()
	{
		return aStart;
	}
	
	public int getEnd()
	{
		return aEnd;
	}
	
	public String toString()
	{
		return "{" + aStart + "," + aEnd + "}";
	}
}

/**
 * Partial implementation of a tokenizer that can convert any valid string
 * into a stream of tokens, or detect invalid strings. Do not change the signature
 * of the public methods, but you can add private helper methods. The signature of the
 * private methods is there to help you out with basic ideas for a design (it is strongly 
 * recommended to use them). Hint: consider making your Tokenizer an Iterable<Token>
 */
class Tokenizer
{
    private LinkedList<Token> tokenList;

    public Tokenizer (){
        tokenList = new LinkedList<Token>();
    }
	/**
	 * Converts pExpression into a sequence of Tokens that are retained in
	 * a data structure in this class that can be made available to other objects.
	 * Every call to tokenize should clear the structure and start fresh.
	 * White spaces are tolerated but should be ignored (not converted into tokens).
	 * The presence of any illegal character should raise an exception.
	 * 
	 * @param pExpression An expression to tokenize. Can contain invalid characters.
	 * @throws InvalidExpressionException If any invalid character is detected or if parentheses
	 * are misused as operators.
	 */
	public void tokenize(String pExpression) throws InvalidExpressionException
	{
        tokenList = new LinkedList<Token>();
	    for (char c : pExpression.toCharArray()){
            consume(c);
        }
    }

    /**
     * create or extend token according to the nature of the new  character
     * @param pChar the character to add to the token list
     * @throws InvalidExpressionException is the character is not supported
     */
	private void consume(char pChar) throws InvalidExpressionException
	{
        int ascii = (int) pChar;
        int currentIndex;
        try {
            currentIndex = tokenList.getLast().getEnd();
        }
        catch (Exception e) {
            currentIndex = 0;
        }
        if (pChar == ' '){
            try{
                tokenList.getLast().extendByOne();
            }
            catch(Exception e){
                tokenList.add(new Token(0,1));
            }
        }
        else if (ascii > 47 && ascii < 58 ) {
            //number
            try{
                Token.TYPE lastType = tokenList.getLast().getType();
                //not the first token created
                if (lastType.equals(Token.TYPE.INTEGER)){
                    //last token was integer too
                    tokenList.getLast().extendByOne();
                }
                else {
                    tokenList.add(new Token(currentIndex, currentIndex+1, Token.TYPE.INTEGER));
                }
            }
            catch (Exception e){
                tokenList.add(new Token(currentIndex, currentIndex+1, Token.TYPE.INTEGER));
            }
        }
        else if(pChar=='+' || pChar=='-' || pChar=='*' || pChar=='/') {
            tokenList.add(new Token(currentIndex,currentIndex+1, Token.TYPE.OPERATOR));
        }
        else if (pChar=='(' ){
            tokenList.add(new Token(currentIndex,currentIndex+1, Token.TYPE.PARENTHESE_OPEN));
        }
        else if ( pChar==')'){
            tokenList.add(new Token(currentIndex,currentIndex+1, Token.TYPE.PARENTHESE_CLOSE));
        }
        else {
            throw new InvalidExpressionException(currentIndex);
        }


    }

    /**
     * aka RPNize
     */
    public void parse(String pExpression) throws InvalidExpressionException {
        Stack<Token> operatorStack = new Stack<Token>();
        LinkedList<Token> outputQueue = new LinkedList<Token>();

        while (!tokenList.isEmpty()){
            switch (tokenList.element().getType()){
                case INTEGER:
                    outputQueue.add(tokenList.pop());
                    break;

                case OPERATOR:
                    String opString = pExpression.substring(tokenList.element().getStart(),tokenList.element().getEnd());
                    if (opString.contains("+") || opString.contains("-")){
                        while (!operatorStack.empty() &&
                                (! operatorStack.peek().getType().equals(Token.TYPE.PARENTHESE_OPEN))){

                            outputQueue.add(operatorStack.pop());
                        }
                    }
                    else if (opString.contains("*") || opString.contains("/")){
                        while (!operatorStack.empty() &&
                                (pExpression.substring(operatorStack.peek().getStart(), operatorStack.peek().getEnd()).contains("*") ||
                                 pExpression.substring(operatorStack.peek().getStart(), operatorStack.peek().getEnd()).contains("/"))){
                            outputQueue.add(operatorStack.pop());
                        }
                    }
                    operatorStack.push(tokenList.pop());
                    break;

                case PARENTHESE_OPEN:
                    operatorStack.push(tokenList.pop());
                    break;

                case PARENTHESE_CLOSE:
                    try {
                        if (operatorStack.peek().getType()!= Token.TYPE.PARENTHESE_OPEN) {
                            while (operatorStack.peek().getType() != Token.TYPE.PARENTHESE_OPEN) {
                                outputQueue.add(operatorStack.pop());
                            }
                            operatorStack.pop(); // pop the opening parenthesis
                            tokenList.pop();
                        } else
                            throw new InvalidExpressionException(tokenList.element().getStart());
                    }
                    catch (EmptyStackException e){
                        throw new InvalidExpressionException(tokenList.element().getStart());
                    }
                    break;
                case NONE:
                    tokenList.pop();
                    break;
            }
        }
        while (!operatorStack.empty()){
            if (operatorStack.peek().getType() == Token.TYPE.PARENTHESE_OPEN){
                throw new InvalidExpressionException(operatorStack.peek().getStart());
            }
            else {
                outputQueue.add(operatorStack.pop());
            }
        }
        tokenList = outputQueue;
    }

    public LinkedList<Token> getTokenList() {
        return tokenList;
    }
}

/**
 * Thrown by the Tokenizer if an expression is detected to be invalid.
 * You don't need to modify this class.
 */
@SuppressWarnings("serial")
class InvalidExpressionException extends Exception
{
	private int aPosition;
	
	public InvalidExpressionException( int pPosition )
	{
		aPosition = pPosition;
	}
	
	public int getPosition()
	{
		return aPosition;
	}
}