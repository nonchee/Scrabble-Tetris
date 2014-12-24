

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WordStatus extends JPanel {
	
	//private Board board; 
	public int boardWidth; 
	public int boardHeight; 
	
	
	private String compliment = ""; 
	private boolean gameOver = false; 
	private String lastword = ""; 
	private int score = 0; 
	public WordStatus () {
		//this.board = board; 
		this.boardWidth = 400; 
		this.boardHeight = 100; 
		setPreferredSize(new Dimension(400, 100)); 
		
 
		addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
			

				repaint();  
			
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			
			
			
	});
	}
	
	
	public void setGameOver(boolean b) {
		gameOver = b;
		lastWordSpelled(""); 
	}
	
	public void lastWordSpelled(String s) {
		
		if (s.equals("")) {
			score = 0; 
			
		}
		
		if (!lastword.equals(s)) {
			compliment = randomCompliment(); 
			score++; 
		}
		
		lastword = s;
		
		

		System.out.println("your new score" + score); 
		
		repaint(); 
	}
	
	public String randomCompliment() {
		
		String compliment = ""; 
		
		String[] compliments = {"Your spelling is the bee's knees!", 
				"Are you a wizard? You're quite the speller!", 
				"You have quite a way with words!", 
				"What a bee-utiful vocabulary!"};
		
		int random = (int) Math.round((compliments.length - 1) * Math.random());
		
		compliment = compliments[random];
		
		return compliment; 
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(Color.GRAY);
		if (gameOver) {
			System.out.println("YEAH"); 
			g.setColor(Color.WHITE);
			g.drawString("Ooopsh, you hit the top.", boardWidth/3, boardHeight/2);
			g.drawString("lol that's okay, just press space to start again.", boardWidth/5, boardHeight/2 + 20); 
		}
		
		else if (score > 0) {
			g.setColor(Color.WHITE);
			g.drawString("HURRAY! You've spelled " + lastword, boardWidth/5, boardHeight/2 - 10);
			g.drawString(compliment, boardWidth/5, boardHeight/2 + 20);
		}
		g.drawString("Your vocab is " + score + " words.", boardWidth/3, boardHeight/2 + 10);
		g.drawString("(Lost? Click anywhere to get you some instructions!)", boardWidth/8, boardHeight/2 + 40);
		
		
	}

}
