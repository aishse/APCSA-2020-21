import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *	MVCipher - Add your description here
 *	Requires Prompt and FileUtils classes.
 *
 *  Prompts user to enter a key for decription. The program does not accept numbers, spaces, or any special characters other than letters.
 *  The user is asked to choose to encrypt or decrypt by entering either 1 or 2. If encrypt, the user is prompted for an input file name,
 *	which is stored and opened up for reading. The user is then prompted for a output file where the message will be displayed, which is
 *  also stored and created for writing. A Scanner instance sends each line of the input file to readEachUpperCase() and/or readEachLowerCAse(),
 *	which calculates an integer for shifting and shifts each upper or lowercase letter. The line is then printed to the output file until there is no more
 *	left in the input file. If decrypt, the user is prompted for the same information as encryption, but the methods decryptEachLowerCase() and decryptEachUpperCase()
 *	are used to calculate the amount of shift and move back each character in the line.
 *
 *	@author	Anishka Chauhan
 *	@since	9/21/2020
 */
public class MVCipher {

	// fields go here
	// Strings for storing input and output files and key
	private String inputFile, outputFile, key;
	private boolean decrypt, encrypt; // boolean for user choice
	private int keyShift; // the amount forwards or backwards the charcters have to be shifted


	/** Constructor */
	// sets default values to fields
	public MVCipher() {
		inputFile = outputFile = key = "";
		decrypt = encrypt = false;
		keyShift = 0;
	}

	public static void main(String[] args) {
		MVCipher mvc = new MVCipher();
		mvc.run();
	}

	/**
	 *	Runs the program. First prompts user for either encryption or decryption, then prompts for a input/output file name and calls methods for opening and
	 * 	encrypting/decrypting the contents of the files.
	 */
	public void run() {

		boolean badInput = false; // boolean for if the input from the Prompt class is not good
		int choice = 0; // the user's choice (1, 2)
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		Scanner keyboard = new Scanner(System.in);

		/* Prompt for a key and change to uppercase
		   Do not let the key contain anything but alpha
		   Use the Prompt class to get user input */
		do
		{
			badInput = false;
			key = Prompt.getString("Please enter a word to use as a key (letters only)");
			key = key.toUpperCase();
			for (int i = 0; i < key.length(); i++)
			{
				if (key.charAt(i) < 65 || key.charAt(i) > 90)
				{
					badInput = true; // if not a letter, then badInput is true and the loop runs again
				}
			}
			if (key.length() <= 0)
			{
				badInput = false;
			}
		}
		while (key.length() <= 0 || badInput == true); // loop runs again if badInput is true or if the key is null.
	//	System.out.println("Key: " + key);


		/* Prompt for encrypt or decrypt */
		do
		{
			badInput = false; // resets boolean to check for new input
			choice = 0;
			choice = Prompt.getInt("Encrypt or Decrypt? (1 - 2)");

			if (choice != 1 && choice != 2) // badInput is true if the choice isn't 1 or 2
			{
				badInput = true;
			}

		}
		while (badInput);
		/*
			Sets either encrypt or decrypt booleans true depending on user choice
	                                                                    	*/
		if (choice == 1)
		{
			encrypt = true;
		}
		if (choice == 2)
		{
			decrypt = true;
		}

		/* Prompt for an input file name depending on user choice */
		if (encrypt)
		{
			inputFile = Prompt.getString("Name of file to encrypt");
		}
		else if (decrypt)
		{
			inputFile = Prompt.getString("Name of file to decrypt");

		}
		/* Prompt for an output file name */
		outputFile = Prompt.getString("Name of output file");

		/* Read input file, encrypt or decrypt, and print to output file */
		if (encrypt)
		{
			encryptFile(); // method that starts encrypting
		}
		if (decrypt)
		{
			decryptFile(); // method that starts decrypting
		}

		/* Don't forget to close your output file */
	}
	/*
		* Opens input file using FileUtils class to read and creates an output file to write to. Reads each line of input file and sends it to readEachUpperCase() and
		* readEachLowerCase() to shift the characters.
		*/
	public void encryptFile()
	{
		Scanner inFile = FileUtils.openToRead(inputFile); // uses FileUtils class to open and write to files
		PrintWriter outfile = FileUtils.openToWrite(outputFile);

		while (inFile.hasNext())
		{
			String temp = inFile.nextLine(); // reads in each line and passes it into readEachLowerCase() and readEachUpperCase()
			temp = readEachUpperCase(temp);
			temp = readEachLowerCase(temp);
			outfile.println(temp); // prints to file
		}
		System.out.println();
		outfile.close();
		System.out.println("The encrypted file " + outputFile + " has been created using the keyword -> " + key + "\n");
	}

	/*
		* Shifts forward each upper case character in the string by calculating the key shift amount and shifting at a certain index. Ignores lowercase letters and wraps around
		* alphabet if characters exceed those boundaries. Does not affect any spaces or special characters.
		* @param str                       one full line of each file for shifting
		* @return                          the file line with all the upppercase characters shifted
		*/

	public String readEachUpperCase (String str)
	{

		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) > 64 && str.charAt(i) < 91)
			{
				// calculates the amount of key shift
				if (i >= key.length()-1)
				{
					keyShift = (int)((key.charAt(0)) - 64);
				}
				else
				{
					keyShift = (int)(key.charAt(i)) - 64;

				}
      	// new character that is created after shifting
				char newChar = (char)(str.charAt(i) + keyShift);

				if (newChar > 90)
				{
					newChar = (char)((newChar - 90) + 64); // wraps around if new character is not a letter
				}

				// adds the character to the full string
				if ((i + 1) >= str.length())
				{

					str = str.substring(0, i) + newChar + "";
				}
				if ((i + 1) < str.length())
				{

					str = str.substring(0,i) + newChar + str.substring((i + 1));
				}
			}
			if (str.charAt(i) == ' ')  // adds a space if there is a space in the string
			{
				char space = ' ';
				if ((i + 1) >= str.length())
				{

					str = str.substring(0, i) + space + "";
				}
				if ((i + 1) < str.length())
				{

					str = str.substring(0,i) + space + str.substring((i + 1));
				}

			}
		}
		return str;
	}
	/*
		* Shifts forward each lower case character in the string by turning the key to lowercase and calculating the amount for characters to shift. Wraps around
		* alphabet if characters exceed those boundaries. Does not affect any spaces or special characters.
		* @param str                       one full line of input file for shifting
		* @return                          the line with all the lowercase characters shifted.
		*/
		public String readEachLowerCase (String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) > 96 && str.charAt(i) < 123)
			{
				// makes the key all lowercase in order to accurately calcualte shift
				String temp = key.toLowerCase();

					// calculates the amount of key shift using temporary key
				if (i >= temp.length()-1)
				{
					keyShift = (int)((temp.charAt(0)) - 96);
				}
				else
				{
					keyShift = (int)(temp.charAt(i)) - 96;
				}
					// new character that is created after shifting
				char newChar = (char)(str.charAt(i) + keyShift);

				if (newChar > 122)
				{
					newChar = (char)((newChar - 122) + 96); // wraps around if new character is not a letter
				}
				// adds the character to the full string depending on where it is
				if ((i + 1) >= str.length())
				{
					str = str.substring(0, i) + newChar + "";
				}
				if ((i + 1) < str.length())
				{
					str = str.substring(0,i) + newChar + str.substring((i + 1));
				}
			}
			if (str.charAt(i) == ' ') // adds a space if there is a space in the string
			{
				char space = ' ';
				if ((i + 1) >= str.length())
				{
					str = str.substring(0, i) + space + "";
				}
				if ((i + 1) < str.length())
				{
					str = str.substring(0,i) + space + str.substring((i + 1));
				}
			}
		}
		return str;
	}

	/*
	* Method that is run if the user chooses to decrypt a file. Uses FileUtils to open input and output files for reading, then calls decryptEachLowerCase() and
	* decryptEachUpperCase() to shift back the characters of the input file, and finally writes all the information to the output file.
	*/
	public void decryptFile ()
	{
		Scanner inFile = FileUtils.openToRead(inputFile);
		PrintWriter outfile = FileUtils.openToWrite(outputFile);

		while (inFile.hasNext())
		{
			String temp = inFile.nextLine();
			temp = decryptEachUpperCase(temp);
			temp = decryptEachLowerCase(temp);
			outfile.println(temp);
		}
		System.out.println();
		outfile.close();
		System.out.println("The decrypted file " + outputFile + " has been created using the keyword -> " + key + "\n");
	}
	/*
		* Shifts back each upper case character in the string by using the key and calculating the amount for characters to shift. Wraps around
		* alphabet if characters exceed those boundaries. Does not affect any spaces or special characters.
		* @param str                       one full line of input file for shifting
		* @return                          the line with all the uppercase characters shifted back.
		*/
	public String decryptEachUpperCase (String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) > 64 && str.charAt(i) < 91)
			{
				if (i >= key.length()-1)
				{
					keyShift = (int)((key.charAt(0)) - 64);
				}
				else
				{
					keyShift = (int)(key.charAt(i)) - 64;
				}
					// new character that is created after shifting
				char newChar = (char)(str.charAt(i) - keyShift);

				if (newChar <= 64)
				{
					newChar = (char)(91 - (65 - newChar)); // wraps around if new character is not a letter
				}
				// adds new character to full string depending on location
				if ((i + 1) >= str.length())
				{
					str = str.substring(0, i) + newChar + "";
				}
				if ((i + 1) < str.length())
				{
					str = str.substring(0,i) + newChar + str.substring((i + 1));
				}
			}

			}

		return str;

	}
	/*
		* Shifts back each lower case character in the string by turning the key to lowercase and calculating the amount for characters to shift. Wraps around
		* alphabet if characters exceed those boundaries. Does not affect any spaces or special characters.
		* @param str                       one full line of input file for shifting
		* @return                          the line with all the lowercase characters shifted back.
		*/
	public String decryptEachLowerCase (String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) > 96 && str.charAt(i) < 123)
			{
				String temp = key.toLowerCase();
				// calculates keyShift by making the key lowercase and subtracting
				if (i >= temp.length()-1)
				{
					keyShift = (int)((temp.charAt(0)) - 96);
				}
				else
				{
					keyShift = (int)(temp.charAt(i)) - 96;
				}
				// new character that is created after shifting
				char newChar = (char)(str.charAt(i) - keyShift);

				if (newChar < 97)
				{
					newChar = (char)(123 - (97 - newChar)); // wraps around if new character is not a letter
				}
				// adds new character to the full string depending on where it is located

				if ((i + 1) >= str.length())
				{
					str = str.substring(0, i) + newChar + "";
				}
				if ((i + 1) < str.length())
				{
					str = str.substring(0,i) + newChar + str.substring((i + 1));
				}
			}
		}
		return str;
	}



}
