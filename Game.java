

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("unused")
public class Game implements Runnable {
	
	
	private boolean playing; 
	
	public Game () {
		playing = false; 
		
	}
	
	public void run () {
		final JFrame frame = new JFrame("Scrabble Tetris!"); 
		final Instructions instructions = new Instructions(); 
		
		frame.add(instructions);
		
		final JPanel display = new JPanel(); 
	
		
	

		final WordStatus wordstatus = new WordStatus(); 	
		
		final Board board = new Board(wordstatus);
		display.setLayout(new BorderLayout());
		
		display.add(board, BorderLayout.CENTER); 
	
		display.add(wordstatus, BorderLayout.SOUTH); 
		
		frame.setLocation(500, 100); 
		
		
		frame.addMouseListener(new MouseAdapter () {
			
			public void mouseClicked(MouseEvent e) {
			 
				playing = !playing; 
				
				
				if (playing) { 
					board.pause(); 
					frame.setContentPane(display);
				
				}
				
				else {
					board.pause(); 
					frame.setContentPane(instructions); 
				}
				
				wordstatus.requestFocusInWindow(); 
				board.requestFocusInWindow();
				frame.pack(); 
				
				
			}

			
		});
		
		
		frame.pack(); 
		frame.setVisible(true); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

}
