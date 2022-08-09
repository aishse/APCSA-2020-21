import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Test the FileUtils class.
 *
 *	@author Mr Greenstein
 *	@since  June 26, 2018
 */

public class FileUtilsTester {
	
	public static void main(String[] args) {
		Scanner infile = FileUtils.openToRead("panama.txt");
		PrintWriter outfile = FileUtils.openToWrite("output.txt");
		while (infile.hasNext()) {
			String line = infile.nextLine();
			System.out.println(line);
			outfile.println(line);
		}
		outfile.close();
	}
}
