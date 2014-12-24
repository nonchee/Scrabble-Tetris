

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class Instructions extends JPanel {
	
	int boardWidth = 400; 
	int boardHeight = 500; 
	
	
	public enum gameMode {
		SCRABBLE, //random chracters from scrabble dictionary 
		MUSE, //real words after which you keep track of what you've written
		READMYOWNFILE, //read in your own file
	}
	
	gameMode mode = gameMode.SCRABBLE; 
	
	public Instructions () {
		setSize(new Dimension(boardWidth, boardHeight)); 
		setPreferredSize(new Dimension(boardWidth, boardHeight)); 
		
		
		setLayout(null); 
		
		ButtonGroup buttons = new ButtonGroup(); 
		final JRadioButton SCRABBLE = new JRadioButton("Scrabble", true);
        final JRadioButton MUSE = new JRadioButton("Muse", false);
        final JRadioButton STUDY = new JRadioButton("Study", false);
      
        buttons.add(SCRABBLE);
        buttons.add(MUSE);
        buttons.add(STUDY);
        
        SCRABBLE.setLocation(30, 30);
       // MUSE.setLocation(100, 200);
       // STUDY.setLocation(170, 200);
        add(SCRABBLE);
        add(MUSE);
        add(STUDY);
		//RadioButton //buttons.add();
	}
	
	
	public gameMode mode() {
		return mode; 
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		g.drawString("Welcome to Scrabble Tetris!", boardWidth/3, 50);
		
		g.drawString("Stack the tetris blocks into horizontal words", 30, 70);
		g.drawString("to clear them from the board.", 50, 80);
	
		
		g.drawString("Z", boardWidth/5, 130); 
		g.drawString("Rotate your word left.", boardWidth/2, 130);
		
		g.drawString("X or UP", boardWidth/5, 150); 
		g.drawString("Rotate your word right.", boardWidth/2, 150);
		
		g.drawString("SPACE", boardWidth/5, 170);
		g.drawString("Drop your word to the bottom.", boardWidth/2, 170);
		
		g.drawString("LEFT", boardWidth/5, 190);
		g.drawString("Shift your word left.", boardWidth/2, 190);
		g.drawString("RIGHT", boardWidth/5, 210);
		g.drawString("Shift your word right.", boardWidth/2, 210);
		
		g.drawString("C", boardWidth/5, 230); 
		g.drawString("Shuffle the word for", boardWidth/2, 225); 
		g.drawString("a more convenient order.", boardWidth/2, 235);
		
		g.drawString("Click anywhere to start.", boardWidth/3, 300);	
		
		
	}
	
	
	

}
