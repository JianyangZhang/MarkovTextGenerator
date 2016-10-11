/* @author: Jianyang Zhang */

package projectD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/** Generate text based on user's command */
public class GenText {
	
	public static void main(String[] args) {
		int argsCount = 0; // Count the index of command line arguments array			
		boolean debugMode = false;	
		int prefixLength = 0;
		int numWords = 0;
		int numArguments = 5;
		ArrayList<String> input = new ArrayList<String>();
		// Check if debug mode is requested
		if(args[argsCount].equals("-d")){
			debugMode = true;
			argsCount++;				
		}	
		// Check if missing command-line arguments
		if((args.length < (numArguments-1) && debugMode == false)||(args.length < numArguments && debugMode == true) ){
			System.out.println("Missing command-line arguments");
			System.exit(0);
		}
		// Check if prefixLength is integer
		try{		
			prefixLength = Integer.parseInt(args[argsCount]);
			argsCount++;
		}catch(Exception e){
			System.out.println("The length of prefix must be integer");
			System.exit(0);
		}
		// Check if prefixLength is at least 1
		if(prefixLength<1){
			System.out.println("The length of prefix must be at least 1");
			System.exit(0);
		}
		// Check if numWords is integer
		try{	
			numWords = Integer.parseInt(args[argsCount]);
			argsCount++;
		}catch(Exception e){
			System.out.println("The number of words must be integer");
			System.exit(0);
		}
		// Check if numWords is at least 0
		if(numWords<0){
			System.out.println("The number of words must not be negative");
			System.exit(0);
		}
		
		String sourceFile = args[argsCount];
		argsCount++;
		String outFile = args[argsCount];
		// Open/close the input file and save each word to an ArrayList in order
		try{
			File inputFile = new File(sourceFile);
			Scanner in = new Scanner(inputFile);
			while(in.hasNext()){				
				input.add(in.next());			
			}
			in.close();
		}catch(FileNotFoundException e){
			System.out.println("Input file does not exist");
			System.exit(0);
		}
		// Check if the number of words in source file is greater than the prefixLength
		if(prefixLength >= input.size()){
			System.out.println("Number of words in source file must be larger than the prefix length");
			System.exit(0);
		}
		// Create the output file, print the generated text, then close it
		try{			
			PrintWriter outputFile = new PrintWriter(outFile);
			RandomTextGenerator user = new RandomTextGenerator(prefixLength, numWords, input, outputFile, debugMode);
			user.generateText();
			user.printText();
			outputFile.close();
		}catch(IOException e){
			System.out.println("Can't write to output file");
			System.exit(0);
		}
	}
}
