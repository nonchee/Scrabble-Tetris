


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;




import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class Board extends JPanel {
	
	int boardWidth = 20; 
	int boardHeight = 20; 
	int sidelength = 20; 
	public int shift = 1; 
	
	private WordStatus wordstatus;
	private boolean gameOver = false; 
	private Timer timer; 
	
	
	public boolean paused; 
	//store of words that are valid for this game
	private Dictionary dict; 
	String [][] ch;
	

	//the active word
	Word w;  
	

	public Board (WordStatus ws){
		paused = false; 
		
		this.wordstatus = ws; 

		ch = new String[boardWidth][boardHeight];
		
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j< boardHeight; j++) {
			ch[i][j] = " "; 
			}
		}
		
	
		
		w = new Word("MERP", boardWidth, boardHeight);
		
		for (int i = 0; i < w.wordlength; i++) {
			ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
		}
		
		
	
		try {
			dict = Dictionary.make("scrabblefours.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		
		
		setPreferredSize(new Dimension(20 * boardWidth, 20 * boardHeight)); 
		
		//place the wordstatus at the bottom of the board
		setLayout(null); 
		wordstatus.setLocation(0, boardHeight);
		
		
		setFocusable(true); 
		
addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
				
				
			if (gameOver) {
				 
				newGame(); 
				
			}
			
			
			//save previous location of word			
			int[][] prevpoints = new int[4][2]; 
			for (int i = 0; i <  4; i++) {
				prevpoints[i][0] = w.getX(i); 
				prevpoints[i][1] = w.getY(i); 
			}
			
			
			
			
			 if (!w.hasReachedBottom(ch)) {
				  	
				 if (e.getKeyChar() == 'c') {
					 w.shuffle(); 
					 
				 }
				 
				 else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					
					
					 
					if (!w.isBlocked(-shift, 0, ch)) {
						w.move(-shift, 0);
						
						//clear out old characters
						for (int i = 0; i < w.wordlength; i++) {
							
							int oldX = prevpoints[i][0]; 
							int oldY = prevpoints[i][1];
							//System.out.println(oldX + " "  + oldY); 
							ch[oldX][oldY] = " "; 
						
						}
			
				
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
						
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
					}
					
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (!w.isBlocked(shift, 0, ch)) {
						
						
					 
						w.move(shift, 0);
					
						for (int i = 0; i < w.wordlength; i++) {
							
							int oldX = prevpoints[i][0]; 
							int oldY = prevpoints[i][1];
							ch[oldX][oldY] = " "; 
						
						}
			
				
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
							//System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
					}
					
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (!w.isBlocked(0, shift, ch) && !w.hasReachedBottom(ch)) {
						//System.out.println("DOWNKEY"); 
						
						w.move(0, shift);
						
						 //clear the board of the old characters.
						for (int i = 0; i < w.wordlength; i++) {
							
									int oldX = prevpoints[i][0]; 
									int oldY = prevpoints[i][1];
									//System.out.println(oldX + " "  + oldY); 
									ch[oldX][oldY] = " "; 
								
							}
						
							
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
							//System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
						
						
					}
					
					else {
						
						newWord();
						 
						
						w.shiftDown(); 
					}
					
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyChar() == 'z') {
					for (int i = 0; i < w.wordlength; i++) {
						
						int oldX = prevpoints[i][0]; 
						int oldY = prevpoints[i][1];
						
						ch[oldX][oldY] = " "; 
					
					}
		
					
					
					w.rotateLeft(ch);
						
					
				
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
						//	System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
				
				}
				else if (e.getKeyChar() == 'x') {
					
					w.rotateRight(ch);

				}
				
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			
					w.hardDrop(ch); 
					//System.out.println("SPACEBAR");
					
					for (int i = 0; i < w.wordlength; i++) {
						
						int oldX = prevpoints[i][0]; 
						int oldY = prevpoints[i][1];
						//System.out.println(oldX + " "  + oldY); 
						ch[oldX][oldY] = " "; 
					
				}
			
					//printPoints(); 
					newWord(); 
				}
				
			}
			
			//has reached bottom 
			else {
				//add back new characters
				for (int i = 0; i < w.wordlength; i++) {
					ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
				}
				
				newWord(); 
			
			}
				
			 
			// ch.update(allWords); 

			 
					for (int i = 0; i < w.wordlength; i++) {
					
						int	oldX = prevpoints[i][0]; 
						int	oldY = prevpoints[i][1];
						//	System.out.println("prevpoints " + oldX + " "  + oldY);
						//	System.out.println(!(w.getY(i) != 0 && w.getX(i) != 0)); 
						//	System.out.println(oldY != 19); 
							if (w.getY(0) != 0 && w.getX(0) !=0) {
								//System.out.println("clear" + w.charAt(i)); 
								ch[oldX][oldY] = " ";
							}
						//	System.out.println("cleared"); 
						
					}
			
				
					
				//add back new characters
				for (int i = 0; i < w.wordlength; i++) {
				//	System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
					ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
				
				}
				
			
				
				//System.out.println(); 
		
		 
			 
			 repaint(); 
			}
		
		//have the active word respond to keys
		/*addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
				
				
			if (gameOver) {
				 
				newGame(); 
				
			}
			
			
			//save previous location of word			
			int[][] prevpoints = new int[4][2]; 
			for (int i = 0; i <  4; i++) {
				prevpoints[i][0] = w.getX(i); 
				prevpoints[i][1] = w.getY(i); 
			}
			
			
			
			
			 if (!w.hasReachedBottom(ch)) {
				  			 
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (!w.isBlocked(-shift, 0, ch)) {
						w.move(-shift, 0);
						
						//clear out old characters
						for (int i = 0; i < w.wordlength; i++) {
							
							int oldX = prevpoints[i][0]; 
							int oldY = prevpoints[i][1];
							System.out.println(oldX + " "  + oldY); 
							ch[oldX][oldY] = " "; 
						
						}
			
				
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
						//	System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
					}
					
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (!w.isBlocked(shift, 0, ch)) {
						
						
					 
						w.move(shift, 0);
					
						for (int i = 0; i < w.wordlength; i++) {
							
							int oldX = prevpoints[i][0]; 
							int oldY = prevpoints[i][1];
						//	System.out.println(oldX + " "  + oldY); 
							ch[oldX][oldY] = " "; 
						
						}
			
				
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
							//System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
					}
					
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (!w.isBlocked(0, shift, ch) && !w.hasReachedBottom(ch)) {
						//System.out.println("DOWNKEY"); 
						
						w.move(0, shift);
						
						 //clear the board of the old characters.
						for (int i = 0; i < w.wordlength; i++) {
							
									int oldX = prevpoints[i][0]; 
									int oldY = prevpoints[i][1];
									//System.out.println(oldX + " "  + oldY); 
									ch[oldX][oldY] = " "; 
								
							}
						
							
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
							//System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
						
						
					}
					
					else {
						
						newWord();
						 
						
						w.shiftDown(); 
					}
					
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyChar() == 'z') {
			
					w.rotateLeft(ch);
						
						for (int i = 0; i < w.wordlength; i++) {
							
							int oldX = prevpoints[i][0]; 
							int oldY = prevpoints[i][1];
							//System.out.println(oldX + " "  + oldY); 
							ch[oldX][oldY] = " "; 
						
						}
			
				
						//add back new characters
						for (int i = 0; i < w.wordlength; i++) {
						//	System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
							ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
						
						}
				
				}
				else if (e.getKeyChar() == 'x') {
					
					w.rotateRight(ch);

				}
				
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			
					w.hardDrop(ch); 
					//System.out.println("SPACEBAR");
					
					for (int i = 0; i < w.wordlength; i++) {
						
						int oldX = prevpoints[i][0]; 
						int oldY = prevpoints[i][1];
						//System.out.println(oldX + " "  + oldY); 
						ch[oldX][oldY] = " "; 
					
				}
			
					//printPoints(); 
					newWord(); 
				}
				
			}
			
			//has reached bottom 
			else {
				//add back new characters
				for (int i = 0; i < w.wordlength; i++) {
					ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
				}
				
				newWord(); 
			
			}
				
			 
			// ch.update(allWords); 

			 
					for (int i = 0; i < w.wordlength; i++) {
					
						int	oldX = prevpoints[i][0]; 
						int	oldY = prevpoints[i][1];
						//	System.out.println("prevpoints " + oldX + " "  + oldY);
						//	System.out.println(!(w.getY(i) != 0 && w.getX(i) != 0)); 
						//	System.out.println(oldY != 19); 
							if (w.getY(0) != 0 && w.getX(0) !=0) {
								//System.out.println("clear" + w.charAt(i)); 
								ch[oldX][oldY] = " ";
							}
						//	System.out.println("cleared"); 
						
					}
			
				
					
				//add back new characters
				for (int i = 0; i < w.wordlength; i++) {
				//	System.out.println("add back " + w.charAt(i) + " at " + w.getX(i) + "  " + w.getY(i)); 
					ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
				
				}
				
			
				
				//System.out.println(); 
		
		 
			 
			 repaint(); 
			}

			*/
		
		@Override
			public void keyReleased(KeyEvent arg0) {
				
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		}); 
		
		
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//System.out.println("rightbefore saving points"); 
				//printPoints(); 
				
				//System.out.println("save previous points:"); 
				//System.out.println("these points belong to " + w.word); 
				//save previous location of word			
				int[][] prevpoints = new int[4][2]; 
				for (int i = 0; i <  4; i++) {
					prevpoints[i][0] = w.getX(i); 
					prevpoints[i][1] = w.getY(i);
					//System.out.print(prevpoints[i][0] + " and " + prevpoints[i][1]); 
				}
				
				
				
				if (!w.isBlocked(0, 1, ch) && !w.hasReachedBottom(ch)) {
					//System.out.println("has not reached bottom"); 
					w.shiftDown();
					
					//printPoints(); 
			
				}
				
				else if (w.hasReachedBottom(ch)) {
					
				//	System.out.println("has reached bottom");
					
					
					//add back new characters
					for (int i = 0; i < w.wordlength; i++) {
					//	System.out.println("ADD BACK"); 
						//w.printMe(i); 
					//	System.out.println("this character" + ch[w.getX(i)][w.getY(i)]); 
						ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
					}
					
					//System.out.println("has reached bottom! print these points"); 
				
					//printPoints(); 
					
					//System.out.println("call new word");
					newWord();
					
					for (int i = 0; i <  4; i++) {
						prevpoints[i][0] = w.getX(i); 
						prevpoints[i][1] = w.getY(i);
						System.out.print(prevpoints[i][0] + " and " + prevpoints[i][1]); 
					}
					
					
					//System.out.println("hiii now you're out here"); 
					//printPoints();  
				
				}
				
			//	System.out.println("hello hello"); 
			//	printPoints(); 
				
				//clear the board of the old characters.
				for (int i = 0; i < w.wordlength; i++) {
						
						int	oldX = prevpoints[i][0]; 
						int	oldY = prevpoints[i][1];
						//System.out.println("this is what you're clearing" + oldX + " " + oldY + " "); 
							ch[oldX][oldY] = " "; 	
					}
				
			//	System.out.println("omg here"); 
			//	printPoints(); 
				
				
				//add back new characters
				for (int i = 0; i < w.wordlength; i++) {
					//System.out.println("adddd back!" + w.charAt(i)); 
					ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
				}
			//	System.out.println("PRINT AGAIN"); 
			//	printPoints(); 
				
				 
				repaint(); 	
			}
		});
		timer.start();
		

		 
		
	}

	
	public void newWord() {
		
	
		//System.out.println("hi now you're in new word"); 
		 

		
		String scrabble = "EEEEEEEEEEEEAAAAAAAAAAIIIIIIIIIOOOOOOOONNNNNNRRRRRR"
				+ "TTTTTTLLLLSSSSSUUUUDDDDGGGBBCCMMPPFFHHVVWWYYKJXQZ"; 
		
		String word =""; 
		
		for (int i = 0; i < 4; i++) {
			word += scrabble.charAt((int) (100 * Math.random()));
		}
		
		Word nw = new Word (word, boardWidth, boardHeight);
		
		//System.out.println("initiate new word"); 
		for (int i = 0; i < w.wordlength; i++) {
			ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i);
		//	System.out.println("add this again?" + w.charAt(i) + w.getX(i) + " " + w.getY(i)); 
			} 
		
		checkForWords();		
		
		//System.out.println("and now reassign " + w.word + "to " + nw.word); 
		w = nw;
		
	//	System.out.println("AAAND print points again"); 
	//	printPoints(); 
		
	//	System.out.println("and now add characters  of" + w.word); 
		
		//add back new characters
		for (int i = 0; i < w.wordlength; i++) {
			ch[w.getX(i)][w.getY(i)] = "" + w.charAt(i); 
		} 
		
		//System.out.println("WHYY WON'T YOU WORK");
	//	System.out.println("how are things now?"); 
		//printPoints();
	
		
		if (nw.isBlocked(0, 1, ch)) {
			//System.out.println("GAAAME OVEr"); 
			gameOver = true; 
			timer.stop();
			
			wordstatus.setGameOver(gameOver); 
			wordstatus.repaint(); 
			
		}
		
	//	System.out.println("done with new word"); 
		
		
	}
	
	
	public void newGame() {
		
		
	//	allWords = new ArrayList<Word>();
		newWord(); 
		
		gameOver = false; 
		
		timer.start(); 
		
		//ch.clearMap();
		ch = new String[boardWidth][boardHeight];
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j< boardHeight; j++) {
				//System.out.println("INITIATE BOARD");
				ch[i][j] = " "; 
			}
		}
		
			
		wordstatus.setGameOver(gameOver);
	
		 
		wordstatus.repaint();
		
	}

	
	
	public boolean gameOver() {
	
		return gameOver; 
	}
	
	
	public boolean checkForWords() {
		
		
		
		//traverse the board
		String maybeWord = "";
		for (int row = boardHeight - 1; row > 0; row--) {
			for (int col = 0; col < boardWidth - 4; col++) {
				maybeWord = ""; 
				maybeWord += ch[col][row] + ch[col + 1][row] + ch[col + 2][row] + ch[col + 3][row];
			//	System.out.println(col + "  " + row + maybeWord); 
			
				//System.out.println(row + maybeWord);
				
				//change this to exists in dictionary
				if (dict.isWord(maybeWord)) {
					//System.out.println(row + " " + col); 
					//System.out.println(row + " " + (col + 1)); 
					//System.out.println(row + " " + (col + 2)); 
					//System.out.println(row + " " + (col + 3));
					
					//remove(maybeWord); 
				
					wordstatus.lastWordSpelled(maybeWord);
					
					
					//clearLine(row); 
					 
					return true; } 
				
			}
		}
	
	
		return false; 
	}
	
	

	
	
	
	
	
	public void paintComponent(Graphics g) {
	
		super.paintComponent(g);
		
		setBackground(new Color(188, 246, 246));
		
		
		//draw all the words
		/*for (Word word : allWords) {
			
			word.draw(g);
		}*/
		
		
		//ch.draw(g); 
		
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < boardHeight; j++) {
				g.setColor(Color.WHITE);
			if (!ch[i][j].equals(" ") ) {	
				g.fillRect(i * sidelength, j * sidelength, sidelength, sidelength);
				g.setColor(Color.BLACK);
				g.drawRect(i * sidelength, j * sidelength, sidelength, sidelength);
				g.drawString("" + ch[i][j], i * sidelength + sidelength/3, j * sidelength + 2 * sidelength/3);
				}
			}
		}
		
	
		}
	
	public void printPoints() {
		System.out.println("CharArray Points" + "!" + w.word + "!"); 
		
		for (int i = 0; i < 20; i++){
			System.out.print("Row " + i);
			for (int j = 0; j < 20; j++) {
						System.out.print(ch[j][i] + " " + j); 
				}
			System.out.println(); 
		}
			
	}
	
	public void pause() {
		paused = !paused; 
		
		if (paused) { 
			timer.stop(); 
		}
		
		else timer.start(); 
	}
	
		
}


