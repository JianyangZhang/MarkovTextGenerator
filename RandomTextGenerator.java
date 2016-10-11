/* @author: Jianyang Zhang */

package projectD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/** This class is to generate the Word-level Markov text*/
public class RandomTextGenerator {
	private static final int CHARACTERS_PER_LINE = 80; // The maximum characters that can be printed per line 
	
    /**
	   prefixLength is the length of prefix
	   numWords is the number of words need to be generated
       input is an ArrayList containing all the input words in order
       output is an ArrayList containing all the generated words
       outputFile is used to manage the output file and print the text
       isDebug is to control if the debug messages will be printed to the console
       
       prefixLength must be integer and at least 1
       numWords must be integer and at least 0
       size of input must be greater than the prefixLength 
	*/	
	
	private int prefixLength;
	private int numWords;
	private ArrayList<String> input;
	private ArrayList<String> output;
	private PrintWriter outputFile;
	private Prefix prefix;
	private Random rand;	
	private boolean isDebug = false;	
	
	// Default constructor
	public RandomTextGenerator(int prefixLength, int numWords, ArrayList<String> input, PrintWriter outputFile) throws FileNotFoundException{		
		this.prefixLength = prefixLength;
		this.numWords = numWords;	
		this.input = input;
		this.output = new ArrayList<String>();
		this.rand = new Random();		
		this.prefix = new Prefix(prefixLength,input);			
		this.outputFile = outputFile;	

	}	
	// Constructor with debug mode option
	public RandomTextGenerator(int prefixLength, int numWords, ArrayList<String> input, PrintWriter outputFile, boolean isDebug) throws FileNotFoundException{		
		this.prefixLength = prefixLength;
		this.numWords = numWords;	
		this.input = input;
		this.output = new ArrayList<String>();
		this.isDebug = isDebug;
		if(isDebug){
			this.rand = new Random(1);
		}else{
			this.rand = new Random();
		}		
		this.prefix = new Prefix(prefixLength,input,isDebug);			
		this.outputFile = outputFile;		
	}
	// Generate the text by Markov algorithm 
	public void generateText(){
		ArrayList<String> successor = new ArrayList<String>(); // Save all successors to an ArrayList
		int startIndex = 0;
		while(output.size() < numWords){ // Check if reached the required number of words
			// If there are more result exists, add it to successor
			if(startIndex < input.size() - prefixLength && prefix.searchNextIndex(startIndex)!=-1){
				successor.add(prefix.getNextWord());
				startIndex = prefix.getNextIndex() - prefixLength + 1;
			}else{ // Otherwise, randomly pick one from the successors as the next word and update the prefix 						
				if(successor.size()!=0){ // Before picking, make sure the successor has at least one element
				int choiceIndex = rand.nextInt(successor.size());	
					if(isDebug){
						System.out.print("DEBUG: successors: ");
						for(int i = 0; i < successor.size(); i++){
							System.out.print(successor.get(i)+" ");
						}
						System.out.println();
						System.out.println("DEBUG: word generated: " + successor.get(choiceIndex));
					}
				output.add(successor.get(choiceIndex));
				prefix.update(successor.get(choiceIndex));			
				successor.clear();
				startIndex = 0;
				}else{ // Empty successor means it has reached the end of file. Reset the prefix then search from the first word. 
					if(isDebug){
						System.out.println("DEBUG: successors: <END OF FILE>");
					}
					prefix.reset();
					startIndex = 0;
				}
			}
		}		
	}	
	// Print the generated text to output file
	public void printText(){
		String outputString = "";
		int countChar = 0; // Record the current number of characters in this line 
		for(int i = 0; i < output.size(); i++){
			countChar = outputString.length();
			// If the current number of characters plus the number of characters in next word exceeds the limit,
			// print the current string, go to a newline and reset
			if((countChar + output.get(i).length()) > CHARACTERS_PER_LINE){ 			
				outputString = outputString.substring(0,outputString.length()-1);
				outputFile.println(outputString);
				outputString = "";	
				countChar = 0;
			}			
			outputString += output.get(i) + " ";			
		}
		outputFile.println(outputString);	
	}
}
