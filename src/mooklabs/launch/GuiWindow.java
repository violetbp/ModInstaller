package mooklabs.launch;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import mooklabs.rss.ReadXMLFromURl;

/**
 * @author mooklabs
 */
public class GuiWindow {

	static String nflink = "http://m.simplepie.org/?feed=http%3A%2F%2Fapocalypsead.enjin.com%2Fhome%2Fm%2F23148107%2Frss%2Ftrue";
	//purly for testing static String nfmplink = "https://gist.githubusercontent.com/mookie1097/2ab755c62a5a6daa47b5/raw/00e7006a957b754185d98ffd4ff7b0ffbb5b2cf1/modpackList";

	/**
	 * checks to see what button was press and does approprate action
	 * 
	 * @author mooklabs
	 */
	// {{button handleing class
	private static class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			// if clik exit end program
			if (command.equals("exit")) {
				System.exit(0);
			} else if (command.equals("m1")) {
				infoSP.setViewportView(m1Pane);

			} else if (command.equals("m2")) {
				infoSP.setViewportView(m2Pane);

			} else if (command.equals("m3")) {
				infoSP.setViewportView(m3Pane);

			} else if (command.equals("friend")) {
				//TODO friend gui and stuff for it to do
			} else if (command.equals("edit")) {
				//gui to edit modpack
			} else if (command.equals("play")) {
				//avery's job
			}else if (command.equals("login")) {
				Launch.login();
			}

		}

	}// }}end button handle class

	// make most stuff global
	public static JFrame window;

	public static JPanel sidebarTop = new JPanel();
	public static JPanel sidebarBot = new JPanel();

	public static JEditorPane title = new JEditorPane("", "TitleBar");

	public static JPanel packs = new JPanel();
	public static JPanel info = new JPanel();
	public static JScrollPane infoSP = new JScrollPane(info);

	public static JEditorPane infoPane;

	public static JButton m1 = new JButton("M1");
	public static JButton m2 = new JButton("M2");
	public static JButton m3 = new JButton("M3");

	public static JTextPane username = new JTextPane();
	public static JTextPane password = new JTextPane();
	public static JButton loginButton = new JButton("Login");

	public static JButton friends = new JButton("Friends");
	public static JButton editPack = new JButton("Edit");
	public static JButton playPack = new JButton("Play");

	public static JTextArea m1Pane = new JTextArea("NausicaaMod");
	public static JTextArea m2Pane = new JTextArea("Nightfall|ReDux");
	public static JTextArea m3Pane = new JTextArea("Nightfall: 2018");
	{
		m1Pane.setEditable(false);
		m2Pane.setEditable(false);
		m3Pane.setEditable(false);
	}




	public static void init() {
		window = new JFrame("ModLauncher");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			infoPane = new JEditorPane(nflink);
			infoPane.setAutoscrolls(true);
		} catch (IOException e) {
			System.err.println("bad info link");
		}
		title.setAlignmentX(title.CENTER_ALIGNMENT);
		title.setEditable(false);

		// inits the listener
		ButtonHandler listener = new ButtonHandler();

		ReadXMLFromURl.getRssData("http://apocalypsead.enjin.com/home/m/23148107/rss/true");
		// inits buttons

		// button listeners
		m1.addActionListener(listener);
		m2.addActionListener(listener);
		m3.addActionListener(listener);
		friends.addActionListener(listener);
		editPack.addActionListener(listener);
		playPack.addActionListener(listener);
		loginButton.addActionListener(listener);



		// action commands --- string passed to action listener to deside what to do
		m1.setActionCommand("m1");
		m2.setActionCommand("m2");
		m3.setActionCommand("m3");
		friends.setActionCommand("friends");
		editPack.setActionCommand("edit");
		playPack.setActionCommand("play");
		loginButton.setActionCommand("login");


		// layouts
		window.setLayout(new GridBagLayout());
		info.setLayout(new BorderLayout());

		c.weightx = 0;
		c.weighty = .1;

		sidebar();

		buttons();
		info.add(title);
		info.add(infoPane);
		infoSP.setViewportView(info);// scroling
		c.fill = c.HORIZONTAL;

		window.add(sidebarBot, constrain(0, 1, 1, c.REMAINDER, c.SOUTHWEST));
		window.add(sidebarTop, constrain(0, 0, 1, c.RELATIVE, c.NORTHWEST));
		c.fill = c.BOTH;

		c.weightx = 1;
		c.weighty = 1;

		c.insets = new Insets(0, 5, 0, 0);
		window.add(infoSP, constrain(1, 0, 2, 2, c.NORTHEAST));
		c.weighty = 0;

		c.insets = new Insets(0, 0, 0, 0);

		// will make packs go to far left
		c.fill = c.NONE;
		window.add(packs, constrain(1, 3, c.REMAINDER, c.REMAINDER, c.SOUTHWEST));

		// creates the gui, kinda really importaint
		window.setSize(700, 500);
		window.setLocation(400, 400);

		// window.getRootPane().setDefaultButton(guessAgain);

	}

	static GridBagConstraints c = new GridBagConstraints();

	public static GridBagConstraints constrain(int row, int col, int cons) {
		c.gridx = row;
		c.gridy = col;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = cons;
		return c;
	}

	public static GridBagConstraints constrain(int row, int col, int wid, int hei, int cons) {
		c.gridx = row;
		c.gridy = col;
		c.gridwidth = wid;
		c.gridheight = hei;
		c.anchor = cons;
		return c;
	}

	static ButtonGroup buttons() {
		ButtonGroup b = new ButtonGroup();
		b.add(m1);
		b.add(m2);
		b.add(m3);
		packs.add(m1);
		packs.add(m2);
		packs.add(m3);
		return b;
	}

	static void sidebar() {
		sidebarTop.setLayout(new GridLayout(3, 1, 1, 3));
		sidebarBot.setLayout(new GridLayout(3, 1, 1, 3));
		sidebarTop.add(username);
		sidebarTop.add(password);
		sidebarTop.add(loginButton);

		sidebarBot.add(friends);
		sidebarBot.add(editPack);
		sidebarBot.add(playPack);
	}

}