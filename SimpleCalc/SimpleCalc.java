import java.util.List;		// used by expression evaluator
import java.util.Scanner;
import java.util.ArrayList;

/**
 *	A simple calculator that utilises ArrayStack class to perform operations. Uses
 *	2 stacks, a Double stack to hold operands and a String stack to hold operators, which pop out
 *	values and operators and perform the specified operations until the operator stack is empty. Utilises
 *	the Identifier class to store variables which can be used in equations and overwritten.
 *
 *	@author Anishka Chauhan
 *	@since 3/28/2021
 */
public class SimpleCalc {

	private ExprUtils utils;	// expression utilities

	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack
	private List<Identifier> identifiers; // identifier database
	private String input; // inputted equation
	private boolean end; // for ending the program

	// constructor
	public SimpleCalc() {
		end = false;
		input = "";
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		identifiers = new ArrayList<Identifier>();
	}

	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}

	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}

	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		Identifier naturalE = new Identifier("e", Math.E); // adds pi and e identifiers
		identifiers.add(naturalE);

		Identifier pi = new Identifier("pi", Math.PI);
		identifiers.add(pi);

		do
		{
		  input = Prompt.getString("");
			List <String> tokens = utils.tokenizeExpression(input);
			if (Character.isLetter(tokens.get(0).charAt(0)) && tokens.size() == 1) // handles single letters and single identifier names
			{
				if (tokens.get(0).equals("h"))
					printHelp();
				else if (tokens.get(0).equals("q"))
					end = true;
				else if (tokens.get(0).equals("i"))
					printIdentifiers();
				else
					System.out.println("" + getIdentifierVal(tokens.get(0)));

		 	 }
			 else if (setsIdentifier(tokens)) // checks if identifier is being set
 			 {
	 				if (!inDatabase(tokens.get(0))) // creates new identifier if name is unique
	 				{
						if (tokens.get(1).equals("=")) {
							double val = 0.0;
							String n1 =  tokens.remove(0); // saves name and removes it from tokens
							tokens.remove(0); // removes '=' sign
			      	val = evaluateExpression(tokens); // evaluates rest of expression

							Identifier identity = new Identifier(n1, val); // adds to Identifier class
				    	identifiers.add(identity);
			 		  	System.out.println("  " + n1 + " = " + val);
						}
						else {
							System.out.println("0.0");
						}

 					}
					else // overwrites value of already existing identifier
					{
						double val = 0.0;
						String n1 =  tokens.remove(0);
						int index = getIdentifierIndex(n1);
						val = evaluateExpression(tokens);
						identifiers.get(index).setValue(val);
						System.out.println("  " + n1 + " = " + val);
					}
 			}
			else if (!containsOperators(tokens) && containsDigits(tokens)) { // if it has digits but no operators then invalid identifier name
				System.out.println("0.0");
			}
			else // evaluates any expressions that are not identifiers
			{
				System.out.println(evaluateExpression(tokens));
			}
		}
		while (!end);
	}
	/**
	 * Helper method for checking if array contains operators
	 * @method containsOperators
	 * @param  tokens             ArrayList of strings
	 * @return                    true if operators found, false otherwise.
	 */
	private boolean containsOperators (List<String> tokens)
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			if (isBinaryOperator(tokens.get(i)))
				return true;
		}
		return false;
	}
	/**
	 * Gets index of identifer in identifiers database that has the same name.
	 * @method getIdentifierIndex
	 * @param  str                 name of identifier to be found
	 * @return                     index of identifier in identfiers database
	 */
	private int getIdentifierIndex (String str)
	{
		for (int i = 0; i < identifiers.size(); i++)
		{
			if (identifiers.get(i).getName().equals(str))
				return i;
		}
		return -1;
	}
	/**
	 * Checks if list of Strings has digits in it
	 * @method containsDigits
	 * @param  str            list of tokens to be tested
	 * @return                true if there are digits, false otherwise.
	 */
	public boolean containsDigits (List<String> str)
	{
		for (int i = 0; i < str.size(); i++)
		{
			if (Character.isDigit(str.get(i).charAt(0)))
				return true;
		}
		return false;
	}
	/**
	 * Prints out the contents of the identifiers database.
	 * @method printIdentifiers
	 */
	public void printIdentifiers ()
	{
		System.out.println("\nVariables:");
		for (int i = 0; i < identifiers.size(); i++)
		{
			System.out.printf("      %-15s =      %-5.2f%n", identifiers.get(i).getName(), identifiers.get(i).getValue());
		}
	}

	/**
	 * Gets value of identifier with given name
	 * @method getIdentifierVal
	 * @param  str              name of identifier to be found
	 * @return                  value of identifer
	 */
	private double getIdentifierVal (String str)
	{
		for (int i = 0; i < identifiers.size(); i++)
		{
			if (str.equals(identifiers.get(i).getName()))
				return identifiers.get(i).getValue();
		}
		return 0.0;
	}
	/**
	 * Checks if a list is trying to set an identifier value
	 * @method isIdentifier
	 * @param  str           list to be tested
	 * @return               true if "=" is found, false otherwise.
	 */
	public boolean setsIdentifier(List<String> str)
	{
		for (int i = 0; i < str.size(); i++)
		{
			if (str.get(i).equals("="))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * Traverses through identifer database and checks if chosen name is in database
	 * @method inDatabase
	 * @param  str              Name to be tested
	 * @return                  true if found, false otherwise.
	 */
	public boolean inDatabase (String str)
	{
		for (int i = 0; i < identifiers.size(); i++)
		{
			if (str.equals(identifiers.get(i).getName()))
				return true;
		}
		return false;
	}

	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n  i - show identifiers\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	/**
	 * Replaces all identifiers with their double values
	 * @method removeAllIdentifiers
	 * @param  tokens               list to be traversed 
	 */
	public void removeAllIdentifiers(List<String> tokens)
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			if (Character.isLetter(tokens.get(i).charAt(0)))
			{
				if (inDatabase(tokens.get(i)))
				{
					double value = getIdentifierVal(tokens.get(i));
					tokens.set(i, "" + value);
				}
			}
		}
	}
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {

		// resets the two stacks
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		//removes all identifiers and replaces them with their values
		removeAllIdentifiers(tokens);
		double value = 0;
		String pointer = tokens.get(0);
		for (int i = 0; i < tokens.size(); i++)
		{
		  pointer = tokens.get(i);
			if (isOperand(pointer)) // pushes all operands to the value stack
			{
				valueStack.push(Double.parseDouble(pointer));
			}
			else if (isBinaryOperator(pointer))
			{
				if (operatorStack.isEmpty() || pointer.equals("(")) { // pushes operator if stack is empty or if it is a (
					operatorStack.push(pointer);
				}
				else if (pointer.equals(")")) // evaluates anything below the ) until it reaches (
				{
					double val = 0.0;
					while (!operatorStack.peek().equals("("))
					{
						val = evaluate(valueStack.pop(), valueStack.pop(), operatorStack.pop());
						valueStack.push(val);
					}
					operatorStack.pop();
				}
				else if (hasPrecedence(pointer, operatorStack.peek())) // if the current op has precedence over the incoming, then evaluate and then push
				{
						valueStack.push(evaluate(valueStack.pop(), valueStack.pop(), operatorStack.pop()));
				 		operatorStack.push(pointer);

				}
				else
				{
					operatorStack.push(pointer);
				}
			 }
		 }
		 // evaluates operations
		 while (!operatorStack.isEmpty())
		 {
			 valueStack.push(evaluate(valueStack.pop(), valueStack.pop(), operatorStack.pop()));
		 }
		 value = valueStack.peek();

		return value;
	}
	/**
	 * Performs specified operation on two operands depending on operator
	 * @method evaluate
	 * @param  op1       operand 1
	 * @param  op2       operand 2
	 * @param  operator  operator to evaluate operands with
	 * @return          result of operation
	 */
	private double evaluate (double op1, double op2, String operator)
	{
		switch (operator) {
			case "+":
				return op2 + op1;
			case "-":
				return op2 - op1;
			case "*":
				return op2 * op1;
			case "/":
				return op2 / op1;
			case "%":
				return op2 % op1;
			case "^":
				return Math.pow(op2, op1);
		}
		return 0.0;
	}
	/**
	 * Checks if string is an operator or not
	 *
	 * Preecondition: can only have a length of 1
	 * @method isBinaryOperator
	 * @param  str               string to be tested
	 * @return                   true if binary operator, false otherwise.
	 */
	private boolean isBinaryOperator(String str) {
		char c = str.charAt(0);
		switch (c) {
			case '+': case '-': case '*': case '/':
			case '%': case '^': case '(': case ')':
				return true;
		}
		return false;
	}
	/**
	 * Checks if string is an operand/digit
	 *
	 * Preecondition: can only have a length of 1
	 * @method isOperand
	 * @param  str       string to be tested
	 * @return           true if operand, false otherwise.
	 */
	private boolean isOperand (String str) {
	 char c = str.charAt(0);
		if (Character.isDigit(c))
				return true;
		return false;
	}

	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%"))
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}

}
