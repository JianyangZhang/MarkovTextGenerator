/* @author: Jianyang Zhang */

package projectD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinalTest {

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<String> input = new ArrayList<String>();
		File file = new File("blue");
		Scanner in = new Scanner(file);
		while(in.hasNext()){
			input.add(in.next());
		}
		in.close();
		
		PrintWriter outputFile = new PrintWriter("out.txt");
		RandomTextGenerator user = new RandomTextGenerator(5, 100, input, outputFile, false);
		user.generateText();
		user.printText();
		outputFile.close();
	}

}

//the end