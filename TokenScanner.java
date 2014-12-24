
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Provides a Token Iterator for a given Reader.
 * <p>
 * Revised slightly to only return word tokens. 
 *
 * @see Token
 */
public class TokenScanner implements Iterator<String> {

	private Reader r;
	private int c; 
	
  /**
   * Creates a TokenScanner for a given Reader.
   * <p>
   * As an Iterator, the TokenScanner should only read from the Reader as much
   * as is necessary to determine getNext() and next(). The TokenScanner should
   * NOT read the entire stream and compute all of the Tokens in advance.
   * <p>
   *
   * @param in the source Reader for character data
   * @throws IOException when the provided Reader is null
   */
  public TokenScanner(java.io.Reader in) throws IOException {
	  
	  if (in == null) {
	      throw new NullPointerException();
	    }
	  else { 
		  this.r = in; 
		  c = r.read(); //starts off as first character of line
		  }
  }

  /**
   * Determines whether a given character is a valid word character.
   * <p>
   * Valid word characters are letters (according to
   * Character.isLetter) and single quote '\''.
   *
   * @param c the character to check
   * @return true if the character is a word character
   */
  public static boolean isWordCharacter(int c) {
    
	  char ch = (char) c; 
	  return (Character.isLetter(ch) || ch == '\'');
    
  }


   /**
   * Determines whether a given String is a valid word
   * <p>
   * Valid words are not null or the empty string. They 
   * only contain word characters.
   *
   * @param s the string to check
   * @return true if the string is a word
   */
  public static boolean isWord(String s) {
		
	  if (s == null || s.equals("")) { 
			return false; 
		
		}
		
		else {
			boolean wurd = true; 
		
			for (int i = 0; i < s.length(); i ++) {
				int w = (int) s.charAt(i);  
				wurd = wurd && isWordCharacter(w); 
			}
		
		return wurd;
		
		}
  }

  /**
   * Determines whether there is another token available.
   */
  public boolean hasNext() {
	return (c != -1); 
	
  }

  /**
   * Returns the next token, or throws a NoSuchElementException if none remain.
   */
  public String next() {
	  
	  char ch = (char) c;
	  String token = "" + ch; 
  
		StringWriter writer = new StringWriter(); 
		
		try {
		
			
		
		
		
		if (isWordCharacter(c)) {
			while (isWordCharacter(c) && hasNext()){
				
				 writer.write(c); 
				 c = r.read(); 
			}
			
			token = writer.toString(); 
			return token;
		}
			
		
		else {
			while (!(isWordCharacter(c)) && hasNext()){
				writer.write(c);
				c = r.read(); 
			} 
			
			token = writer.toString();
			if (isWord(token)) { 
				return token;
			}
			else { 
				if (hasNext()) {//System.out.println(next()); 
					return next(); 
				}
				
				else return "heyyy"; 
			}
		
		}
	}
		
		catch (IOException e) {
			throw new NoSuchElementException(); 
		}

}
		
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
	