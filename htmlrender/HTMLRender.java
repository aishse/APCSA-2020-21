/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Anishka Chauhan
 *	@version 11/28/2020
 */
import java.util.Scanner;
public class HTMLRender {

	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 100000;	// size of array
	private int lineCounter, arrayPos, headNum; // the counter for the line, a field for the token array's position
																							// and the heading tag number

	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	private HTMLUtilities util; // the html utilities util for tokenizing
	/* enums for determining state of the program:
		RenderState is for the non-nested tags in the program and NestedState is for the nested tags.
	*/
	private enum RenderState {NONE, BODY, PRGPH, BLD, BRK, QT, ITL, HEAD, PRE};
	private enum NestedState {NONE, BLD, BRK, ITL, HR};

	private RenderState state;
	private NestedState nested;

	public HTMLRender() {
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		util = new HTMLUtilities();
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
		state = RenderState.NONE;
		nested = NestedState.NONE;
		lineCounter = arrayPos = headNum = 0;
	}


	public static void main(String[] args) {
		HTMLRender hf = new HTMLRender();
		hf.run(args);
	}

	public void run(String[] args) {

		Scanner input = null;
		String fileName = "";
		// if the command line contains the file name, then store it
		if (args.length > 0)
			fileName = args[0];
		// otherwise print out usage message
		else {
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}

		// Open the HTML file
		input = FileUtils.openToRead(fileName);

		// Read each line of the HTML file, tokenize, then print tokens
		while (input.hasNext()) {
			String line = input.nextLine();
		//	System.out.println("\n" + line);
			String [] tempArray = util.tokenizeHTMLString(line);
			addToTokenArray(tempArray); // adds each line to the entire token array
		}
		input.close();
		tokens = shortenArray(tokens); // shortens the array to get rid of extra null strings
		renderTokens(); // renders the tokens
	//	printArray(); // for debugging

	}
	/**
		* Renders the tokens by going token by token in the tokens array and rendering based off of the specified state.
		*	Does not go over 80 characters on each line. Uses several helper methods to determine state or type of tag.
		*
	*/
	private void renderTokens ()
	{
		String currentToken = "";
		for (int i = 0; i < tokens.length; i++)
		{
			currentToken = tokens[i];
		//	System.out.println(currentToken);
		//	System.out.println(lineCounter);
			if (!isHTMLToken(currentToken))
			{
				if (i + 1 < tokens.length && isPunctuation(tokens[i + 1]))
				{
					lineCounter += currentToken.length();
				}
				else if (isPunctuation(currentToken))
				{
					lineCounter += currentToken.length();
				}
				else
				{
					lineCounter += currentToken.length() + 1;
				}

			}
			if (lineCounter > 80 || (lineCounter > 60 && state == RenderState.HEAD))
			{
				browser.println();
				lineCounter = 0;
			}
			// checks what state the program is in (paragraph, bold, italic, etc.)
			if (state == RenderState.NONE && isHTMLToken(currentToken))
			{
				checkToken(currentToken);
			}
			// handles the text between the paragraph tags
			if (state == RenderState.PRGPH)
			{
				if (currentToken.equalsIgnoreCase("<p>"))
				{
					browser.println();
					browser.println();
					lineCounter = 0;
				}
				else if (isHTMLToken(currentToken) && !isEndToken(currentToken)) // checks for nested tags
				{
					switch (currentToken)
					{
						case "<b>":
							nested = NestedState.BLD;
							break;
						case "<i>":
							nested = NestedState.ITL;
							break;
						case "<br>":
							nested = NestedState.BRK;
							break;
						case "<hr>":
							nested = NestedState.HR;
							break;
						default:
							nested = NestedState.NONE;

					}
				}
				else if (isEndToken(currentToken) && nested == NestedState.NONE) // handles paragraph end
				{
					state = RenderState.NONE;
					browser.println();
					browser.println();
					lineCounter = 0;
				}
				else if (isEndToken(currentToken) && nested != NestedState.NONE) // handles nested tags end
				{
					nested = NestedState.NONE;
				}
				else // prints out the tags depending on the nested state
				{
					if (i + 1 < tokens.length && isPunctuation(tokens[i + 1]))
					{
						switch (nested)
						{
							case BLD:
								browser.printBold(currentToken);
								break;
							case ITL:
								browser.printItalic(currentToken);
								break;
							case BRK:
								browser.printBreak();
								lineCounter = 0;
								break;
							case HR:
								browser.printHorizontalRule();
								lineCounter = 0;
								break;
							default:
								browser.print(currentToken);
						}
					}
					else
					{
						switch (nested)
						{
							case BLD:
								browser.printBold(currentToken + " ");
								break;
							case ITL:
								browser.printItalic(currentToken + " ");
								break;
							case BRK:
								browser.printBreak();
								lineCounter = 0;
								break;
							case HR:
								browser.printHorizontalRule();
								lineCounter = 0;
								break;
							default:
								browser.print(currentToken + " ");
						}
					}

				}
			}
			// handles the text between the italic tags
			else if (state == RenderState.ITL)
			{
				if (isEndToken(currentToken))
				{
					state = RenderState.NONE;
				}
				else if (!isHTMLToken(currentToken))
				{
					if (i + 1 < tokens.length && isPunctuation(tokens[i + 1]))
					{
						browser.printItalic(currentToken);
					}
					else
					{
						browser.printItalic(currentToken + " ");
					}
				}
			}
			// handles the text between the quote tags
			else if (state == RenderState.QT)
			{

				if (isHTMLToken(currentToken) && !isEndToken(currentToken)) // checks for nested tags
				{
					switch (currentToken)
					{
						case "<br>":
							browser.printBreak();
							lineCounter = 0;
							nested = NestedState.NONE;
							break;
						case "<hr>":
							browser.printHorizontalRule();
							lineCounter = 0;
							nested = NestedState.NONE;
							break;
						case "<q>":
							browser.print("\"");
						default:
							nested = NestedState.NONE;

					}
				}
				else if (isEndToken(currentToken)) // handles paragraph end
				{
					state = RenderState.NONE;
					browser.print("\" ");
					lineCounter = 0;
				}
				else
				{
					if (i + 1 < tokens.length && isHTMLToken(tokens[i + 1]))
						browser.print(currentToken);
					else
						browser.print(currentToken + " ");
				}

			}
			// handles the text between the bold tags
			else if (state == RenderState.BLD)
			{
				if (isEndToken(currentToken))
				{
					state = RenderState.NONE;
				}
				else if (!isHTMLToken(currentToken))
				{
					if (i + 1 < tokens.length && isPunctuation(tokens[i + 1]))
					{
						browser.printBold(currentToken);
					}
					else
					{
						browser.printBold(currentToken + " ");
					}
				}
			}
			// handles the text between the header tags
			else if (state == RenderState.HEAD)
			{
		//		System.out.println("token!" + currentToken);
				if (isHeadToken(currentToken)) // checks to see if token is a beginning head token
				{
					headNum = getHeadNum(currentToken); // gets the heading number
				//	System.out.println(headNum);
					browser.println();
					lineCounter = 0;

				}
				else if (isEndToken(currentToken)) // checks to see if token is a end token with a "/"
				{
					browser.println();
					state = RenderState.NONE;
				}
				else // renders tokens depending on the heading number and takes into account punctuation
				{
					if (i + 1 < tokens.length && isPunctuation(tokens[i + 1]))
					{
						switch (headNum)
						{
							case 1:
								browser.printHeading1(currentToken);
								break;
							case 2:
								browser.printHeading2(currentToken);
								break;
							case 3:
								browser.printHeading3(currentToken);
								break;
							case 4:
								browser.printHeading4(currentToken);
								break;
							case 5:
								browser.printHeading5(currentToken);
								break;
							case 6:
								browser.printHeading6(currentToken);
								break;
						}
					}
					else
					{
						switch (headNum)
						{
							case 1:
								browser.printHeading1(currentToken + " ");
								break;
							case 2:
								browser.printHeading2(currentToken + " ");
								break;
							case 3:
								browser.printHeading3(currentToken + " ");
								break;
							case 4:
								browser.printHeading4(currentToken + " ");
								break;
							case 5:
								browser.printHeading5(currentToken + " ");
								break;
							case 6:
								browser.printHeading6(currentToken + " ");
								break;
						}
					}

				}
			}
			else if (state == RenderState.PRE) // renders preformatted text
			{
				if (!isHTMLToken(currentToken))
				{
					browser.printPreformattedText(currentToken);
					browser.println();
					lineCounter = 0;
				}
				else if (isEndToken(currentToken))
				{
					state = RenderState.NONE;
				}
			}
			else // for any body text
			{
				if (!isHTMLToken(currentToken))
				{
					if (i + 1 < tokens.length && isPunctuation(tokens[i + 1]))
					{
						browser.print(currentToken);
					}
					else
					{
						browser.print(currentToken + " ");
					}
				}
			}


		}
	}
	/**
	* Gets heading number by going character by character until digit is found.
	* Prerequisite: token is already a heading tag.
	*
	*	@param           token the html token to be tested
	* @return          the digit's numeric value
	*/
	private int getHeadNum (String token)
	{
		char current = 0;

		int i = 0;
		while (i < token.length())
		{
			current = token.charAt(i);
			if (Character.isDigit(current))
			{
				return Character.getNumericValue(current);
			}
			i++;
		}
		return -1;
	}
	/**
	*	Helper method for checking if a character is a form of punctuation.
	*
	*	@param   c 							string to be tested
	*	@return  true or false, depending on if the string is punctuation or not.
	*/
	private boolean isPunctuation (String c)
	{
		char [] punctuation = new char[] {'.', ',', ';', ':', '(', ')', '?', '!', '=', '~', '+', '-', '&'};
		for (int i = 0; i < punctuation.length; i++)
		{
			String temp = punctuation[i] + "";
			if (c.equals(temp))
			{
				return true;
			}
		}
		return false;
	}
	/**
	* Sets the render state for the program in order for the token to be rendered correctly.
	*	Prerequisite: string must be a token.
	* @param        token the html token to be checked
	*/
	private void checkToken (String token)
	{
		token = token.toLowerCase();
		switch (token)
		{
			case "<b>":
				state = RenderState.BLD;
				break;
			case "<p>":
				state = RenderState.PRGPH;
				break;
			case "<q>":
				state = RenderState.QT;
				break;
			case "<i>":
				state = RenderState.ITL;
				break;
			case "<h1>":
				state = RenderState.HEAD;
				break;
			case "<h2>":
				state = RenderState.HEAD;
				break;
			case "<h3>":
				state = RenderState.HEAD;
				break;
			case "<h4>":
				state = RenderState.HEAD;
				break;
			case "<h5>":
				state = RenderState.HEAD;
				break;
			case "<h6>":
				state = RenderState.HEAD;
				break;
			case "<br>":
				browser.printBreak();
				lineCounter = 0;
				break;
			case "<pre>":
				state = RenderState.PRE;
				break;
			case "<hr>":
				browser.printHorizontalRule();
				lineCounter = 0;
				break;
		}
	}
/**
*	Checks to see if token is a beginning header token.
*
*	@param         token the token to be tested
* @return        true/false, depending on whether token is a header token
*/
	private boolean isHeadToken (String token)
	{
		token = token.toLowerCase();
		switch (token)
		{
			case "<h1>":
				return true;
			case "<h2>":
				return true;
			case "<h3>":
				return true;
			case "<h4>":
				return true;
			case "<h5>":
				return true;
			case "<h6>":
				return true;
			default:
				return false;
		}
	}
	/**
	* Evaluates whether token is an end token or not.
	*
	* @param      token the token to be tested
	* @return     true/false
	*/
	private boolean isEndToken (String token)
	{
		if (token.charAt(0) == '<')
		{
			for (int i = 1; i < token.length(); i++)
			{
				if (token.charAt(i) == '/')
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	* Evaluates whether token is an HTML token or not.
	*
	* @param      token the token to be tested
	* @return     true/false
	*/
	private boolean isHTMLToken (String token)
	{
			if (token.charAt(0) == '<')
			{
				for (int i = 1; i < token.length(); i++)
				{
					if (token.charAt(i) == '>')
					{
						return true;
					}
				}
			}
			return false;
	}
	/**
	* Adds the given array of tokens the big tokens array .
	*
	* @param      line the array to be added to tokens
	*/
	private void addToTokenArray (String [] line)
	{
		int i = 0;
		while (i < line.length)
		{
			tokens[arrayPos] = line[i];
		//	System.out.println(line[i]);
			i++;
			arrayPos++;
		}
	}

	/**
	* Evaluates whether token is an end token or not.
	*
	* @param      result the array to be shortened
	* @return     the shortened array
	*/
	private String [] shortenArray(String [] result)
	{
		int counter = 0;
		for (int i = 0; i < result.length; i++)
		{
			//System.out.println(result[i]);
			String currentToken = result[i];
			if (currentToken != null)
				counter++;
		}
	//	System.out.println(counter);
		String [] tempArray = new String [counter];
		for (int i = 0; i < tempArray.length; i++)
		{
			if (!result[i].isEmpty())
			{
				tempArray[i] = result[i];
			}
		}
		result = tempArray;
		return result;

	}
	/*
		private void printArray()
		{
			for (int i = 0; i < tokens.length; i++)
			{
				System.out.println(tokens[i]);
			}
		}
		*/
}
