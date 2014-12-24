

import java.awt.Color;
import java.awt.Graphics;

public class Word {
	
	public String word; 
	
	
	//every word has a private set of its own coordinates
	private int[][] points; 
	public int wordlength; 
	
	//public Board board; 
	public int boardWidth; 
	public int boardHeight; 
	
	
	
	//one "unit" in a game where every square is 20 pixels on a side
	public int sidelength; 
	
	
	//direction
	public double ori = 3; 
	
 
	
	
	
	public Word (String word, int boardWidth, int boardHeight) {
		
		//WORD CAN ONLY BE FOUR LETTERS LONG
		this.word = word;
		

		points = new int[word.length()][2];
		wordlength = points.length; 
		
		this.boardWidth = boardWidth; 
		this.boardHeight = boardHeight;
		
		//PURELY FOR GRAPHICS DO NOT USE FOR MOVING
		sidelength = 20;	
		
		
		int[][][] shape = new int[][][] {
				{ { 0, 0 },  { 1, 0 },   { 2, 0 },  { 3, 0 } }, // 0: I 
	            { { 0, 0 },  { 1, 0 },   { 2, 0 },   { 2, 1 } },//1: J  
	            { { 0, 1 },  { 0, 0 },   { 1, 0 },   { 2, 0 } }, //2: L
	            { { 0, 0 },  { 1, 0 },   { 1, 1 },   { 2, 0 } }, //3: K
	            { { 0, 1 },   { 1, 1 },   { 1, 0 },   { 2, 0 } }, //4: S
	            { { 0, 0 }, { 1, 0 },  { 1, 1 },   { 2, 1 } }, //5: inv(S)
	            { { 0,0 },  { 1,0 },  { 1, 1 },   { 0, 1 } } //^2
	        };

		
        int random = (int) (Math.random() * 7);
        //System.out.println("random" + random); 
		
        
        for (int i = 0; i < points.length; i++) {
       	//System.out.println("random" + random + "  " + shape[random][i][0] + shape[random][i][1]); 
        	points[i][0] = shape[random][i][0]; 
        	points[i][1] = shape[random][i][1];
        }
        
        
		/*//initialize points  
		for (int i = 0; i < points.length; i++) {
				points[i][0] = i; 
				points[i][1] = 0; 
			
		}*/	
		
		
	}
	
	public boolean containsPoints(int x, int y){
		boolean contains = false; 
		
		
		
		for (int i = 0; i < wordlength; i++) {
			
				 contains = contains 
						 	|| ((points[i][0] == x) && (points[i][1] == y)); 
				  
			 
		}
		
		return contains; 
	}
	
	public boolean isBlocked(int dx, int dy, String[][] ch) {
		
	
		int[][] pointsToCheck = points; 
				
		
		boolean spotTaken = false; 
		boolean atBoundary = false;
		
		for (int i = 0; i < wordlength; i++) {
		
			int checkX = pointsToCheck[i][0];
			int checkY = pointsToCheck[i][1]; 
			
			checkX +=dx; 
			checkY += dy; 
			
			//at the boundary of the board
			atBoundary = atBoundary || checkX < 0 //left boundary 
						|| checkY < 0 //top boundary
						|| checkX + 1 > boardWidth //right boundary
						|| checkY + 1 > boardHeight; //bottom boundary
						
		//	System.out.println(checkY + " " + boardHeight); 						
			if (atBoundary) {
				//System.out.println("Out of bounds: (" + checkX + ", " + checkY + ")"); 
				return true; }
			
			//blocked by another word
			//if there is already a char at the new points
			//which is not part of our original word
			
	
			
		spotTaken = (spotTaken || (ch[checkX][checkY] != " ") && !containsPoints(checkX, checkY)); 
		
	 
		}

	
		
		return (spotTaken || atBoundary); 
	}
	
	
	public void shiftDown () {
		//System.out.println("My points are now:"); 
	
		for (int i = 0; i < wordlength; i++) {
			
			points[i][1] ++;
			//System.out.print(points[i][1] + "  "); 
	
		}
		
		//System.out.println(); 
		
		
		
	}
	
	//move down, left or right in response to the keyboard
	//take care of when this makes you go out of bounds
	public void move (int dx, int dy) {
		
		for (int i = 0; i < wordlength; i++) {
		
				points[i][0] += dx; 
				points[i][1] += dy; 
			
			}
		
	}
	
	
	public void hardDrop(String[][] ch) {
		
		
		if (!isBlocked(0, 1, ch)) {
			shiftDown(); 
			hardDrop(ch); 
			
		}
		
		else {
			return; 
			
		}
		 
		
	
	}
	
	public void shuffle() {
		
		String newWord = ""; 
		
		for (int i = 0; i < 4; i++) {
			if (i == 3) {
				i = -1; 
			}
			
			newWord += word.charAt(i + 1);
			
			if (i == -1) {
				i = 3; 
			}
		}
		
		word = newWord; 
	}
	
	
	//if the next shift means that you hit the bottom
	public boolean hasReachedBottom(String[][] ch) {
		
		boolean hitBottom = false; 
		
		
		int firstY = points[0][1]; 

		
		if (isBlocked(0, 1, ch)){
			return true; 
		}
		
		if (ori == 3)  {
			hitBottom = (firstY  + 1 >= boardHeight);
			
		}
		
		else if (ori == 6) {
			hitBottom = (firstY + wordlength >= boardHeight); 
					
		}
		
		else if (ori == 9) {
			hitBottom = (firstY + 1 >= boardHeight); 
			
		}
		
		else if (ori == 12) {
			hitBottom = (firstY > boardHeight);  
		}
		
		
		return (hitBottom || isBlocked(0, 1, ch));
		
		  
	}
	
	
	public void rotateLeft(String[][] ch){
	
		
		int t = 0; 
		
		int smallestX = getX(0); 
		int smallestY = getY(0); 
 
		
		for (int i = 0; i < wordlength; i++) {
			//translate to origin
			int newX = getX(i) - smallestX; 
			int newY = getY(i) - smallestY; 
	 
		
			//swap x's and y's 
			t = newX; 
			newX = newY;
			
			
			newY = -t; 
			
			
			//translate back
			newX = newX + smallestX; 
			newY = newY + smallestY; 
			
			
			
			//try new points
			setX(i, newX); 
			setY(i, newY); 	
			
	
		
			
		}
		
	
		//wall kicking madness
		double oldori = ori;
		
		double newori = oldori - 3; 
		if (newori < 3) {
			newori = 12; 
		}
		
		double statechange = oldori/newori;
		
		if (statechange == 4.0/3.0 || statechange == 2.0/3.0) {
		
			if (isBlocked(0, 0, ch)) {
				
				move(wordlength -1, 0);
				
				if (isBlocked(0, 0, ch)) {
								
				
					move(-2 * wordlength, 0);
					
					if (isBlocked(0, 0, ch)) {
						
						
						move(3 * wordlength, 1);
						 
					}
					 
				}
			}
		}
		
		
		if (statechange == 1.0/4.0 || statechange == 3.0/4.0) {
			
			if (isBlocked(0, 0, ch)) {
				
				move(0, wordlength - 1);
				 if (isBlocked(0, 0, ch)) {
					move(0, wordlength); 
					
					if (isBlocked(0, 0, ch)) {
						
						
						move(1, -2 * wordlength);
						 
					}
				}
			}
		}
		
		if (statechange == 4.0 || statechange == 2.0) {
			if (isBlocked(0, 0, ch)) {
				move(-wordlength + 1, 0);
				
				if (isBlocked(0, 0, ch)) {
					move(2 * wordlength, 0);
					
					if (isBlocked(0, 0, ch)) {
						
						
						move(- 2* wordlength, 1);
						
						 
					}
				}
			}
			
		}
		if (statechange == 0.5  || statechange == 3.0/2.0) {
			if (isBlocked(0, 0, ch)) {
				move(0, 0);
				
				if (isBlocked(0, 0, ch)) {
					move(0, - wordlength + 2); 
				}
			}
		}
		
		
		ori = newori;
		
		
		
		}
	
	public void rotateRight(String[][] ch){
		
		
		int t = 0; 
		
		int smallestX = getX(0); 
		int smallestY = getY(0); 
		
		
		for (int i = 0; i < wordlength; i++) {
			//translate to origin
			int newX = getX(i) - smallestX; 
			int newY = getY(i) - smallestY; 
	 
		
			//swap x's and y's 
			t = newX; 
			newX = -newY;
			
			
			newY = t; 
			
			
			//translate back
			newX = newX + smallestX; 
			newY = newY + smallestY; 
			
			
			
			//try new points
			setX(i, newX); 
			setY(i, newY); 	
			
	
			
		}
		
		
		//wall kicking madness
		double oldori = ori;
		
		double newori = oldori + 3; 
		if (newori > 12) {
			newori = 3; 
		}
		
		double statechange = oldori/newori;
		//System.out.println("HELLO" + statechange);
		if (statechange == 4.0/3.0 || statechange == 2.0/3.0) {
			
			if (isBlocked(0, 0, ch)) {
				move(wordlength -1, 0);
				
				if (isBlocked(0, 0, ch)) {
					move(-2 * wordlength, 0);
					
					if (isBlocked(0, 0, ch)) {
						move(3 * wordlength, 1);
						 
					}
					 
				}
			}
		}
		
		
		if (statechange == 1.0/4.0 || statechange == 3.0/4.0) {
			
			if (isBlocked(0, 0, ch)) {
				
				move(0, wordlength - 1);
				
				if (isBlocked(0, 0, ch)) {
					move(0, wordlength); 
					
					if (isBlocked(0, 0, ch)) {		
						move(-wordlength, -2 * wordlength);
					}
					
					
				}
			}
		}
		
		if (statechange == 4.0 || statechange == 2.0) {
		//	System.out.println("BLoCKEDDDDD");
			if (isBlocked(0, 0, ch)) {
				move(-wordlength, 0);
				
				if (isBlocked(0, 0, ch)) {
					
					move(2, 0);
					
					if (isBlocked(0, 0, ch)) {
						move(- 2* wordlength, 1);
						
						 
					}
				}
				
				
			}
			
		}
		
		if (statechange == 0.5  || statechange == 3.0/2.0) {
			if (isBlocked(0, 0, ch)) {
				
				move(0, 0);
				 if (isBlocked(0, 0, ch)) {
					move(0, - wordlength + 2); 
				}
			}
		}
		
		
	
		ori = newori;
		
		
		
}
	

	
	public void draw(Graphics g) {
		
		
		for (int i = 0; i < wordlength; i++) {
		
			int px = points[i][0]; 
			int py = points[i][1]; 
			
			
			g.setColor(Color.WHITE);
			g.fillRect(px * sidelength, py * sidelength, sidelength, sidelength);
			g.setColor(Color.BLACK);
			g.drawRect(px * sidelength, py * sidelength, sidelength, sidelength);
			g.drawString("" + word.charAt(i), px * sidelength + sidelength/3, py * sidelength + 2 * sidelength/3);
		}
		
		
	}
	
	
	//interacting with the char array
	//getters
	public int getX(int index) {
		return points[index][0];
	}

	public int getY(int index) {
		return points[index][1];
	}
	
	public char charAt(int index) {
		
		if (index >= wordlength) {
			return '*'; 
		}
		
		return word.charAt(index); 
	}


	//setters
	public void setX(int index, int newX) {
		points[index][0] = newX; 
	}

	public void setY(int index, int newY) {
		points[index][1] = newY; 
	}
	
	
	public void printMe(int i) {
		System.out.println("(" + getX(i) + ", " + getY(i) + "): "  + word.charAt(i));
	}
	
}
