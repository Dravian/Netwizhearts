package Client.View;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Das Fenster CreateGame dient dem Benutzer zur Erstellung eines neuen Spieles.
 * Es bietet alle Komponenten, um ein Regelwerk zu w�hlen, einen Spielnamen festzulegen 
 * und das Spiel durch ein Passwort zu sch�tzen. In der Spielerstellung wird ein 
 * Titelbild des ausgew�hlten Spiels und eine kurze Beschreibung angezeigt. 
 * �ber 'Leave' kehrt der Spieler in die Lobby zur�ck 
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
	private JComboBox<String> rulesetBox;
	private JCheckBox chckbxPassword;
	private JButton btnLeave;
	private JButton btnCreate;
	private JLabel lblGameName;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGame frame = new CreateGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt das CreateGame Fenster
	 */
	public CreateGame() {
		setTitle("Game Creation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 279);
		getContentPane().setLayout(null);
		
		lblSelect = new JLabel("Select Game");
		lblSelect.setBounds(12, 12, 95, 24);
		getContentPane().add(lblSelect);
		
		rulesetBox = new JComboBox<String>();
		rulesetBox.setBounds(12, 39, 188, 24);
		rulesetBox.addItem("Wizard");
		rulesetBox.addItem("Hearts");
		getContentPane().add(rulesetBox);
		
		chckbxPassword = new JCheckBox("Set Password:");
		chckbxPassword.setBounds(12, 140, 128, 23);
		getContentPane().add(chckbxPassword);
		
		btnLeave = new JButton("Leave");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLeave.setBounds(12, 215, 117, 25);
		getContentPane().add(btnLeave);
		
		nameField = new JTextField();
		nameField.setBounds(12, 171, 188, 32);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		try {
	    image = ImageIO.read(new File("src/wizard.jpg"));
		} catch (IOException e) {
			//TODO
		}
		
		imagePanel = new JPanel() {
			protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        g.drawImage(image, 0, 0, null);          
			    }
		};
		imagePanel.setToolTipText("Wizard is a trick-taking card game for 3 to 6 players.");

		imagePanel.setBounds(235, 12, 139, 171);
		getContentPane().add(imagePanel);
		
		btnCreate = new JButton("Create");
		btnCreate.setBounds(257, 215, 117, 25);
		getContentPane().add(btnCreate);
		
		lblGameName = new JLabel("Game Name:");
		lblGameName.setBounds(12, 75, 95, 24);
		getContentPane().add(lblGameName);
		
		passwordField = new JTextField();
		passwordField.setBounds(12, 100, 188, 32);
		getContentPane().add(passwordField);
		passwordField.setColumns(10);
	}
	
	/**
	 * F�gt  einen MouseListener zum ImagePanel des CreateGame Fensters hinzu,
	 * der zur Anzeige des MouseOver-Texts verwendet wird.
	 * 
	 * @param m ein MouseListener
	 */
	public void addPanelMouseListener(MouseListener m) {
		imagePanel.addMouseListener(m);
	}
	
	/**
	 * F�gt einen Listener f�r die Regelwerk-Auswahl des CreateGame Fensters hinzu.
	 * 
	 * @param i ein ItemListener
	 */
	public void addRulesetSelectionListener(ItemListener i) {
		rulesetBox.addItemListener(i);
	}
	
	/**
	 * F�gt einen ActionListener f�r den 'Create' Button hinzu.
	 * 
	 * @param a ein ActionListener
	 */
	public void addCreateButtonListener(ActionListener a) {
		btnCreate.addActionListener(a);
	}
	
	/**
	 * F�gt einen ActionListener f�r den 'Leave' Button hinzu.
	 * 
	 * @param a ein ActionListener
	 */
	public void addLeaveButtonListener(ActionListener a) {
		btnLeave.addActionListener(a);
	}
	
	/**
	 * �ndert die Sprache des Fensters
	 * 
	 * @param l Sprache in Form des Language-Enums
	 */
	public void setLanguage(Language l) {
		lang = l;
		updateLanguage();
	}
	
	private void updateLanguage() {
		//TODO
	}
}
