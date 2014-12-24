
import java.io.*;
import java.util.TreeSet;

/**
 * A dictionary manages a collection of known, correctly-spelled words. 
 *
 * The dictionary is case insensitive and only stores "valid" word. 
 * A valid word is any sequence of letters (as determined by Character.isLetter) 
 * or apostrophes characters.
 */
public class Dictionary {

	
	private TreeSet<String> index;
	@SuppressWarnings("unused")
	private int numWords; 
  /**
   * Constructs a dictionary from words provided by a TokenScanner.
   * <p>
   * A word is any sequence of letters (see Character.isLetter) or apostrophe 
   * characters. All non-word tokens provided by the TokenScanner should be ignored.
   *
   * <p>
   *
   * @param ts sequence of words to use as a dictionary
   * @throws IOException if error while reading
   * @throws IllegalArgumentException if the provided reader is null
   */
    public Dictionary(TokenScanner ts) throws IOException {
    	//use TreeSet for fast "contains" and "add methods
    	//as well as no-duplicate invariant
    	index = new TreeSet<String>();  
    	numWords = 0; 
    	//int duplicates = 0; 
    	
    	//make the dictionary by iterating through the file
    	//System.out.println(ts.hasNext()); 
    	if (ts == null) { 
    		throw new IllegalArgumentException (); 
    	}
    	
    	else {
    		while (ts.hasNext()) {
    		
    			String word = ts.next();
    			 		
    			
    			
    			if (TokenScanner.isWord(word) && !(index.contains(word))) {
    				word = word.toLowerCase();
    				
    				index.add(word);
    				numWords++; 
    				
    		}
    			
    		
    	}
    		
    	
    }
    }
  

   /**
   * Constructs a dictionary from words from a file.
   *
   *
   * @param filename location of file to read words from
   * @throws FileNotFoundException if the file does not exist
   * @throws IOException if error while reading
   */
   public static Dictionary make(String filename) throws IOException {
	  Reader r = new FileReader(filename);
	  Dictionary d = new Dictionary(new TokenScanner(r));
	  
	   
	  r.close();
  	  return d;
   }

  /**
   * Returns the number of unique words in this dictionary. This count is
   * case insensitive: if both "DOGS" and "dogs" appeared in the file, it
   * should only be counted once in the returned sum.
   * 
   * @return number of unique words in the dictionary
   */
  public int getNumWords() {
     return index.size();
  }

  /**
   * Test whether the input word is present in the Dictionary.
   * <p>
   * This check should be case insensitive.  For example, if the
   * Dictionary contains "dog" or "dOg" then isWord("DOG") should return true.
   * <p>
   * The argument to this method should only contain letters or apostrophe characters.
   * The empty string, or strings containing only whitespace characters, are not in the dictionary. 
   * If the argument is null, the method returns false.
   * <p>
   * Calling this method should not re-open or re-read the source file.
   *
   * @param word a string token to check. Assume any leading or trailing
   *    whitespace has already been removed.
   * @return whether the word is in the dictionary
   */
  public boolean isWord(String word) {
    word = word.toLowerCase(); 

    return index.contains(word);
  }
}
