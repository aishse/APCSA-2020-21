/**
*	Utilities for handling HTML
*
*	@author Anishka Chauhan
*	@since 11/15/2020
*/
public class HTMLUtilities {
	// NONE = not nested in a block, COMMENT = inside a comment block
	// PREFORMAT = inside a pre-format block
	private enum TokenState { NONE, COMMENT, PREFORMAT};

	// the current tokenizer state
	private TokenState state;

	public HTMLUtilities ()
	{
		state = TokenState.NONE;
	}


	/**
	*	Break the HTML string into tokens. The array returned is
	*	exactly the size of the number of tokens in the HTML string.
	*	Example:	HTML string = "Goodnight moon goodnight stars"
	*				returns { "Goodnight", "moon", "goodnight", "stars" }
	*	@param str			the HTML string
	*	@return				the String array of tokens
	*/
	public String[] tokenizeHTMLString(String str) {
		int temp = 0; // this value is used for the index of the array
		char current = 0; // current character
		String token = ""; // the token

		String[] result = new String[100]; // big array to start with (resized later)

		// Goes through character by character and creates a token.
		for (int i = 0; i < str.length(); i++)
		{
			current = str.charAt(i);
			// This part is for checking whether the token is a comment or not
			if (i + 3 < str.length() && str.charAt(i) == '<')
			{
				if (str.charAt(i + 1) == '!')
				{
					if (str.charAt(i + 2) == '-')
					{
						state = TokenState.COMMENT;
						//System.out.println("comment!!");
					}
				}
			}
			// checks if the token is a html tag (the preformat tag checking is also done here)
			if ((state == TokenState.NONE)&& current == '<')
			{
				token = "";
				token += current;
				while (current != '>' || state != TokenState.NONE)
				{
					i++;
					current = str.charAt(i);
					token += current;
				}
				if (token.equals("<pre>")) // this checks if the token is a preformat token
				{
					state = TokenState.PREFORMAT; // changes state and sends token to the if block handling it

				}
				else
				{
					result[temp] = token; // adds tag to the array and incrememnts the index value
					temp++;
				}
			}
			if (state == TokenState.NONE && Character.isLetter(current)) // evaluates if the character is an alpha
			{
				token = "";
				token += current;
				// loop runs until i is out of bounds or if the character after the alpha is not a letter
				while (i < str.length()-1 && (Character.isLetter(str.charAt(i + 1)) || str.charAt(i + 1) == '-')) // also continues if there is a hyphen in between
				{
					i++;
					current = str.charAt(i);
					token += current;

				}
				result[temp] = token;
				temp++;

			}
			// uses the helper method isPunctuation() to determine if the character is a type of punctuation
			if (state == TokenState.NONE && isPunctuation(current))
			{
				token = "";
				if (current == '-') // to check if the number is a negative sign or a punctuation
				{
					if (i + 1 < str.length() && !isNumber(str.charAt(i + 1)))
					{
						token += current;
						result[temp] = token;
						temp++;
					}
				}
				else
				{
					token += current;
					result[temp] = token;
					temp++;
				}
			}
			// uses isNumber() method to determine if the character is part of a number or not
			if (state == TokenState.NONE && (isNumber(current) || (current == '-' && isNumber(str.charAt(i + 1)))))
			{
				boolean endNum = false; // boolean for checking if the number is ending
				token = "";
				token += current;
				if (i + 1 < str.length()) // checks for single numbers
				{
					if (!isNumber(str.charAt(i+1)) && str.charAt(i + 1) != '-' && str.charAt(i + 1) != 'e' && str.charAt(i + 1) != '.')
					{
						endNum = true;
						result[temp] = token;
						temp++;
					}
				}
				while (i < str.length() - 1 && endNum == false)
				{
					i++;
					current = str.charAt(i);
					// this checks to see if the character is part of a number or not
					if (i + 1 >= str.length() || !(isNumber(str.charAt(i + 1)) || str.charAt(i + 1) == '-' || str.charAt(i + 1) == 'e') || str.charAt(i+1) == '.')
					{
						if (i + 2 < str.length())
						{
							if (!isNumber(str.charAt(i + 2)))
							{
								endNum = true;
								if (isNumber(current))
								token += current;
								result[temp] = token;
								temp++;
							}
						}
						else
						{
							endNum = true;
							if (isNumber(current))
							token += current;
							result[temp] = token;
							temp++;
						}
					}
					token += current;
				}
			}
			// handles comments by going character by character until the end comment is detected (does not stop at html tag)
			if (state == TokenState.COMMENT)
			{

				if (current == '-')
				{
					if (i + 1 < str.length())
					{
						if (str.charAt(i + 1) == '>')
						{
							state = TokenState.NONE;
						}
					}
				}
			}
			// handles preformats by setting token equal to the whole string and changing state depending on what the token is. The
			// token is added to the array regardless.
			if (i < str.length() && state == TokenState.PREFORMAT)
			{
				token = str;
				if (token.equals("</pre>"))
				{
					state = TokenState.NONE;
				}
				result[temp] = token;
				temp++;
				i = str.length();
			}

		}

		result = shortenArray(result); // the array is shortened in order for there to not be null.
		return result;
	}
	/**
	*	Helper method for checking if a character is a form of punctuation.
	*
	*	@param   c 							character to be tested
	*	@return  true or false, depending on if the character is punctuation or not.
	*/
	private boolean isPunctuation (char c)
	{
		char [] punctuation = new char[] {'.', ',', ';', ':', '(', ')', '?', '!', '=', '~', '+', '-'};
		for (int i = 0; i < punctuation.length; i++)
		{
			if (c == punctuation[i])
			{
				return true;
			}
		}
		return false;
	}
	/**
	* Helper method for checking if character is a number.
	*
	*	@param c              character to be tested.
	*	@return  true/false, depending on if character is number or not
	*/
	private boolean isNumber (char c)
	{
		if (c >= 48 && c <= 57)
		{
			return true;
		}
		return false;
	}

  /**
	*	Helper method for shortening the array by traversing the array and counting how many non-null indexes there are. A new array
	* is created in which the non-null indexes are added and the array is returned.
  *
	*	@param result 							the array to be shortened
	* @return the shortened array 
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
	/**
	*	Print the tokens in the array to the screen
	*	Precondition: All elements in the array are valid String objects.
	*				(no nulls)
	*	@param tokens		an array of String tokens
	*/
	public void printTokens(String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}

}
