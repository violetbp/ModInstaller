package mooklabs.launch;


/*
 * ALL
 * HAIL
 * FISHY
 * FISH
 */



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author mooklabs
 */
public class GuiWindow extends JPanel {

	


	/**
	 * checks to see what button was press and does approprate action
	 * 
	 * @author mooklabs
	 */
	// {{button handleing class
	private static class ButtonHandler implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String command = e.getActionCommand();
			// if clik exit end program
			if (command.equals("end")) {
				System.exit(0);
			} else if (command.equals("guessAgain") ) {
				
			} else if (command.equals("guessAgain")){

			}

		}

	}// }}end button handle class

	
	// Textbox output
	static JTextField textInput = new JTextField();
	static JLabel textOutput = new JLabel();

	// make most stuff global
	static JFrame window;

	static JButton end = new JButton("Rage Quit");
	static JButton guessAgain = new JButton("Guess Again");

	
	//I dont knwo if a panel is needed but it worked with it well
	static JPanel content = new JPanel();

	public static void init() {
		window = new JFrame("WHEN ARE YOU COMING TO VISIT");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// inits the listener
		ButtonHandler listener = new ButtonHandler();

		// inits buttons

		// button listeners
		end.addActionListener(listener);
		guessAgain.addActionListener(listener);

		// action commands --- string passed to action listener to deside what to do
		end.setActionCommand("end");
		guessAgain.setActionCommand("guessAgain");


		// idk
		content.setLayout(new GridBagLayout());

		
		textInput.setMinimumSize(new Dimension(10,20));
		
		content.add(textOutput, constrain(0,0,c.ABOVE_BASELINE));		
		
		//content.add(end, constrain(0,1,c.SOUTHWEST));
		//content.add(guessAgain, constrain(1,1,c.SOUTHEAST));
		
		c.ipadx = 70;
		//content.add(textInput, constrain(1,0,c.BELOW_BASELINE));

		// creates the gui, kinda really importaint
		window.setContentPane(content);
		window.setSize(400, 300);
		window.setLocation(400, 400);
		//window.setCursor(Cursor.WAIT_CURSOR);
		window.setVisible(true);// make it visible

		// /stuff i just added
		textOutput.setText("WHEN ARE YOU COMING TO VISIT?");
		
		//content.setForeground()
		window.getRootPane().setDefaultButton(guessAgain);

	}
	static GridBagConstraints c = new GridBagConstraints();
	
	public static GridBagConstraints constrain(int row, int col,int cons) {
		c.gridx = row;
		c.gridy = col;
		c.anchor = cons;
		return c;
	}

}