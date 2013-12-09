package Client.View;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Ruleset.RulesetType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * CreateGame. Das Fenster CreateGame dient dem Benutzer zur Erstellung eines neuen Spieles.
 * Es bietet alle Komponenten, um ein Regelwerk zu waehlen, einen Spielnamen festzulegen 
 * und das Spiel durch ein Passwort zu schuetzen. In der Spielerstellung wird ein 
 * Titelbild des ausgewaehlten Spiels und eine kurze Beschreibung angezeigt. 
 * ueber 'Leave' kehrt der Spieler in die Lobby zurueck 
 * und mit 'Create' wird das Spiel erstellt.
 */
public class CreateGame extends JFrame{

	
	private static final long serialVersionUID = -2893031560688870723L;
	
	private Language lang;
	private JTextField nameField;
	private BufferedImage image;
	private JTextField passwordField;
	private JPanel imagePanel;
	private JLabel lblSelect;
	private JComboBox<RulesetType> rulesetBox;
	private JCheckBox chboxPassword;
	private JButton btnLeave;
	private JButton btnCreate;
	private JLabel lblGameName;
	private JTextArea tooltipArea;
	private static String IMAGEPATH = "Data/";

	/**
	 * Erstellt das CreateGame Fenster
	 */
	public CreateGame() {
		setTitle("Game Creation");
		lang = Language.English;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 403, 279);
		setResizable(false);
		getContentPane().setLayout(null);
		
		lblSelect = new JLabel("Select Game");
		lblSelect.setBounds(12, 12, 188, 24);
		getContentPane().add(lblSelect);
		
		rulesetBox = new JComboBox<RulesetType>();
		rulesetBox.setBounds(12, 39, 188, 24);
		rulesetBox.addItemListener(new RulesetSelectionListener());
		getContentPane().add(rulesetBox);
		
		chboxPassword = new JCheckBox("Set Password:");
		chboxPassword.setBounds(12, 140, 188, 23);
		chboxPassword.addActionListener(new PWCheckedListener());
		getContentPane().add(chboxPassword);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(12, 215, 117, 25);
		btnLeave.addActionListener(new LeaveButtonListener());
		getContentPane().add(btnLeave);
		
		nameField = new JTextField();
		nameField.setBounds(12, 100, 188, 32);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		image = null;
		
		btnCreate = new JButton("Create");
		btnCreate.setBounds(257, 215, 117, 25);
		getContentPane().add(btnCreate);
		
		lblGameName = new JLabel("Game Name:");
		lblGameName.setBounds(12, 75, 188, 24);
		getContentPane().add(lblGameName);
		
		passwordField = new JTextField();
		passwordField.setBounds(12, 171, 188, 32);
		passwordField.setEditable(false);
		getContentPane().add(passwordField);
		passwordField.setColumns(10);

		tooltipArea = new JTextArea();
		tooltipArea.setBounds(210, 12, 177, 192);
		getContentPane().add(tooltipArea);
		tooltipArea.setLineWrap(true);

		imagePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					g.drawImage(image, 0, 0, null);
				}
			}
		};

		imagePanel.setBounds(235, 12, 139, 171);
		imagePanel.addMouseListener(new MouseOverListener());
		getContentPane().add(imagePanel);
		tooltipArea.setVisible(false);
		tooltipArea.addMouseListener(new MouseOverListener());
	}
	
	/**
	 * Setzt die Regelwerke, die in der Auswahlbox gezeigt werden
	 * 
	 * @param types Liste von Regelwerken
	 */
	public void setRulesetTypes(List<RulesetType> types) {
		rulesetBox.removeAllItems();
		for(RulesetType t : types) {
			rulesetBox.addItem(t);
		}
		updateImage();
	}
	
	/**
	 * Gibt den gewaehlten Spielnamen zurueck
	 * 
	 * @return Spielname
	 */
	public String getGameName() {
		return nameField.getText();
	}
	
	/**
	 * Gibt zurueck ob ein Passwort gewaehlt wurde
	 * 
	 * @return true, wenn ein Passwort gewaehlt wurde, false sonst
	 */
	public boolean hasPassword() {
		return chboxPassword.isSelected();
	}
	
	/**
	 * Gibt das Passwort zurueck
	 * 
	 * @return Passwort
	 */
	public String getPassword() {
		return passwordField.getText();
	}
	
	/**
	 * Gibt zurueck welches Regelwerk in der Auswahlbox ausgewaehlt wurde
	 * 
	 * @return Regelwerk
	 */
	public RulesetType getSelectedRulesetType() {
		return (RulesetType) rulesetBox.getSelectedItem();
	}
	
	/**
	 * Fuegt  einen MouseListener zum ImagePanel des CreateGame Fensters hinzu,
	 * der zur Anzeige des MouseOver-Texts verwendet wird.
	 * 
	 * @param m ein MouseListener
	 */
	public void addPanelMouseListener(MouseListener m) {
		imagePanel.addMouseListener(m);
	}
	
		
	/**
	 * Fuegt einen ActionListener fuer den 'Create' Button hinzu.
	 * 
	 * @param a ein ActionListener
	 */
	public void addCreateButtonListener(ActionListener a) {
		btnCreate.addActionListener(a);
	}
		
	/**
	 * Aendert die Sprache des Fensters
	 * 
	 * @param l Sprache in Form des Language-Enums
	 */
	public void setLanguage(Language l) {
		lang = l;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				updateLanguage();
				updateImage();
			}
		});
	}
	
	private void updateLanguage() {
		switch (lang) {
		case German:
			setTitle("Spiel erstellen");
			btnCreate.setText("Erstellen");
			btnLeave.setText("Verlassen");
			lblGameName.setText("Spielname");
			lblSelect.setText("Wähle Spieltyp");
			chboxPassword.setText("Passwort");
			break;
		case English:
			setTitle("Game Creation");
			btnCreate.setText("Create");
			btnLeave.setText("Leave");
			lblGameName.setText("Game Name");
			lblSelect.setText("Select Gametype");
			chboxPassword.setText("Password");
			break;
		case Bavarian:
			setTitle("A Spui aufmacha");
			btnCreate.setText("Mach auf");
			btnLeave.setText("Wida geh");
			lblGameName.setText("Spuinam");
			lblSelect.setText("Suach da a Spui aus");
			chboxPassword.setText("Ned für an jedn");
			break;
		default:
			break;

		}
	}
	
	private String getTooltip(RulesetType ruleset) {
		String ret = "";
		switch (ruleset) {
		case Wizard:
			switch (lang) {
			case German:
				ret = "Bei Wizard geht es darum vor jeder Runde richtig anzusagen "
						+ "wie viele Stiche man machen wird. Nur wenn die angesagt Anzahl "
						+ "der Stiche genau stimmt, bekommt man Punkte. Ansonsten verliert man "
						+ "so viele Punkte, wie man daneben gelegen hat";
				break;
			case English:
				ret = "In Wizard you try to correctly announce the amount of tricks "
						+ "you will make in the upcoming round. Only if you predict the exact "
						+ "amount you will receive points. Otherwise you lose as many points "
						+ "as you were off.";
				break;
			case Bavarian:
				ret = "Eiso, Wizard is eigntlich fast wia Schofkopfa, bloß dass jeda oiwei a Solo "
						+ "spuid und es geht drum, dassd  genau de richtige Zoi an Stich asogst, "
						+ "des moansd dassd machsd. Bloß wennsd de genau richtig asogst griagst "
						+ "Aung, sunst griagst sovui Aung obzogn wiasd damengleng bisd.";
				break;
			default:
				break;
			}
			break;
		case Hearts:
			switch (lang) {
			case German:
				ret = "Bei Hearts geht es darum möglichst wenige Stiche zu machen. %)";
				break;
			case English:
				ret = "In Hearts you try to get as few tricks as possible. %)";
				break;
			case Bavarian:
				ret = "Ja, des is a bissal wia Ramsch beim Schofkopfa.";
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return ret;
	}
	
	private void updateImage() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					RulesetType t = (RulesetType)(rulesetBox.getSelectedItem());
				    image = ImageIO.read(new File(IMAGEPATH + t.toString().toLowerCase() + ".jpg"));
				    tooltipArea.setText(getTooltip(t));
				    imagePanel.repaint();
					} catch (IOException e) {
						//TODO
					}
			}
		});
		
	}
	
	class LeaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
			
		}
		
	}
	
	class RulesetSelectionListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			updateImage();
		}
		
	}
	
	class PWCheckedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			passwordField.setEditable(chboxPassword.isSelected());
			
		}
		
	}
	
	class MouseOverListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			tooltipArea.setVisible(true);
			//repaint();
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			tooltipArea.setVisible(false);
			//repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			//not needed
			
		}
		
	}
}
