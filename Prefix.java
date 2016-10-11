/* @author: Jianyang Zhang */

package projectD;

import java.util.ArrayList;
import java.util.Random;
/** This class is to manage the prefix for Markov word generation process */
public class Prefix {
	
	/**
	length is the length of prefix 
 	input is an ArrayList containing all the input words in order
 	prefix is an ArrayList containing the prefix words
 	nextIndex is the index of the word that firstly appeared after the prefix during the search
 	nextWord is the word that firstly appeared after the prefix during the search
 	isDebug is to control if the debug messages will be printed to the console
 	
 	length must be integer and at least 1 (prefix must have at least 1 element)
 	size of input must be greater than the length 	
	*/
	
	private int length; 	
	private ArrayList<String> input; 
	private ArrayList<String> prefix; 
	private Random rand;
	private int randomIndex; 
	private int nextIndex = -1;
	private String nextWord = "";
	private boolean isDebug = false;
	
	// Default constructor
	public Prefix(int length, ArrayList<String> input){
		this.length = length;
		this.input = input;
		this.prefix = new ArrayList<String>();
		this.rand = new Random(); 
		this.randomIndex = rand.nextInt(input.size() - length);			
		int flag = randomIndex;
		while(prefix.size() < length){ // initiate the prefix
			prefix.add(input.get(flag));
			flag++;
		}
	}
	// Constructor with debug mode option
	public Prefix(int length, ArrayList<String> input, boolean isDebug){
		this.length = length;
		this.input = input;
		this.prefix = new ArrayList<String>();
		this.isDebug = isDebug;		
		if(isDebug){
			this.rand = new Random(1);			
		}else{
			this.rand = new Random();		
		}		
		this.randomIndex = rand.nextInt(input.size() - length);			
		int flag = randomIndex;
		while(prefix.size() < length){ // initiate the prefix
			prefix.add(input.get(flag));
			flag++;
		}		
		if(isDebug){
			System.out.println("DEBUG: chose a new initial prefix: " + toString());
			System.out.println("DEBUG: prefix: " + toString());
		}		
	}	
	
	// Reset the prefix (when reaches the end of file)
	public void reset(){
		prefix.clear();
		randomIndex = rand.nextInt(input.size() - length);		
		int flag = randomIndex;
		while(prefix.size() < length){
			prefix.add(input.get(flag));
			flag++;
		}				
		if(isDebug){
			System.out.println("DEBUG: chose a new initial prefix: " + toString());
			System.out.println("DEBUG: prefix: " + toString());
		}	
	}
	// Update the prefix (when a new word is generated)
	public void update(String nextWord){
		prefix.remove(0);
		prefix.add(nextWord);
		if(isDebug){
			System.out.println("DEBUG: prefix: " + toString());
		}
	}
	/** Search the index of the word that follows the prefix, return -1 if no result is found
	    This method needs a start index to search from
	    This method only returns the first result */
	public int searchNextIndex(int startIndex){	
		for(int i = startIndex; i < input.size() - length; i++){
			int inputIndex = i;
			int prefixIndex = 0;			
			// Try to match the words with the prefix
			while(prefix.get(prefixIndex).equals(input.get(inputIndex))){ 				
				inputIndex++;
				prefixIndex++;	
				if(prefixIndex == length){ // Enough consecutive matches found
					nextIndex = inputIndex;
					nextWord = input.get(inputIndex);
					return nextIndex;
				}
			}			
		}
		return -1;
	}
	/** Search the word that follows the prefix, return empty string if no result is found
	    This method needs a start index to search from
	    This method only returns the first result */
	public String searchNextWord(int startIndex){
		if(searchNextIndex(startIndex)!=-1){			
			return nextWord;
		}else{
			return "";
		}
	}	
	// Return the nextIndex (only search once for efficiency)
	public int getNextIndex(){
		return nextIndex;
	}
	// Return the nextWord (only search once for efficiency)
	public String getNextWord(){
		return nextWord;
	}	
	// Return the current prefix
	public ArrayList<String> getPrefix(){
		return prefix;
	}
	// Transfer the prefix to String
	public String toString(){
		String prefixString = "";
		for(int i = 0; i < prefix.size(); i++){
			prefixString = prefixString + prefix.get(i) + " "; 
		}
		return prefixString;		
	}
	// Return the current randomIndex
	public int getRandomIndex(){
		return randomIndex;
	}	
}