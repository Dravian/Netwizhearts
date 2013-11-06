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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Das Fenster CreateGame dient dem Benutzer zur Erstellung eines neuen Spieles.
 * Es bietet alle Komponenten, um ein Regelwerk zu wählen, einen Spielnamen festzulegen 
 * und das Spiel durch ein Passwort zu schützen. In der Spielerstellung wird ein 
 * Titelbild des ausgewählten Spiels und eine kurze Beschreibung angezeigt. 
 * Über 'Leave' kehrt der Spieler in die Lobby zurück 
 * und mit 'Create' wird das Spiel erstellt.
 * 
 * @author M4nkey
 */
public class CreateGame extends JFrame implements Observer {

	
	private static final long serialVersionUID = -2893031560688870723L;
	
	private JTextField textField;
	private BufferedImage image;
	private JTextField textField_1;
	
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
	 * 
	 * @throws IOException 
	 */
	public CreateGame() throws IOException {
		setTitle("Game Creation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 279);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select Game");
		lblNewLabel.setBounds(12, 12, 95, 24);
		getContentPane().add(lblNewLabel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(12, 39, 188, 24);
		comboBox.addItem("Wizard");
		getContentPane().add(comboBox);
		
		JCheckBox chckbxPassword = new JCheckBox("Set Password:");
		chckbxPassword.setBounds(12, 140, 128, 23);
		getContentPane().add(chckbxPassword);
		
		JButton btnNewButton = new JButton("Leave");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(12, 215, 117, 25);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(12, 171, 188, 32);
		getContentPane().add(textField);
		textField.setColumns(10);

	    image = ImageIO.read(new File("src/wizard.jpg"));
		
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        g.drawImage(image, 0, 0, null);          
			    }
		};
		panel.setToolTipText("Wizard is a trick-taking card game for 3 to 6 players.");

		panel.setBounds(235, 12, 139, 171);
		getContentPane().add(panel);
		
		JButton btnNewButton_1 = new JButton("Create");
		btnNewButton_1.setBounds(257, 215, 117, 25);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblGameName = new JLabel("Game Name:");
		lblGameName.setBounds(12, 75, 95, 24);
		getContentPane().add(lblGameName);
		
		textField_1 = new JTextField();
		textField_1.setBounds(12, 100, 188, 32);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * übergebenen Befehl wird ein Update des Fensters ausgeführt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: windowChangeAcknowledged, windowChangeDenied
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
