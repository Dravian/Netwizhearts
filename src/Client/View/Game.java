package Client.View;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Client.ViewNotification;

import java.awt.Color;

/**
 * Im Game Fenster l�uft das Spiel ab.Es enth�lt den Spielchat und ein GamePanel.
 * Au�erdem k�nnen �ber ein Dropdown-Men� �nderungen an Hintergrundbild 
 * und Kartenhintergr�nden vorgenommen werden. Schlie�en beendet das Spiel 
 * und der Spieler wird in die Lobby zur�ckgeleitet.
 */
public class Game extends JFrame implements Observer{

	private static final long serialVersionUID = -2655520138213745249L;
	
	private JPanel contentPane;
	private JTextField textField;

	
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt das Game Fenster
	 * 
	 * @throws IOException 
	 */
	public Game() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new GamePanel();
		panel.setBounds(10, 11, 764, 370);
		panel.setOpaque(false);
		contentPane.add(panel); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 391, 764, 105);
		contentPane.add(scrollPane);
		
		JTextArea txtrSpielerHfGl = new JTextArea();
		txtrSpielerHfGl.setLineWrap(true);
		txtrSpielerHfGl.setText("Meow: hf gl\r\nDoc Holliday: u2\r\nMark: gl gl");
		txtrSpielerHfGl.setEditable(false);
		scrollPane.setViewportView(txtrSpielerHfGl);
		
		textField = new JTextField();
		textField.setBounds(10, 507, 764, 44);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//ImageIcon image = new ImageIcon(getClass().getResource("kartenspiel.png"));
		//JLabel lblNewLabel_1 = new JLabel(new ImageIcon("./src/game.png"));
		//lblNewLabel_1.setBounds(10, 11, 764, 369);
		//lblNewLabel_1.setOpaque(false);
		
		
		JLabel lblNewLabel = new JLabel("Score    106");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(768, 373, 56, 55);
		//lblNewLabel_1.add(lblNewLabel);
		//lblNewLabel_1.add(button);
	//contentPane.add(lblNewLabel_1);
	
/*	JButton btnNewButton = new JButton("New button");
	btnNewButton.setBounds(237, 183, 89, 23);
	lblNewLabel_1.add(btnNewButton);
	
	JLabel lblNewLabel_2 = new JLabel("SCORE 106");
	lblNewLabel_2.setBackground(new Color(192, 192, 192));
	lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel_2.setBounds(69, 102, 70, 70);
	lblNewLabel_2.setOpaque(false);
	contentPane.add(lblNewLabel_2); */
	}
	
	/**
	 * Arrangiert die Elemente der Spielfeld-Oberfl�che f�r ein Kartenspiel, 
	 * bei dem Stiche gemacht werden. Hierf�r hat jeder Spieler einen eigenen
	 * Ablagestapel vor sich. Es k�nnen 3, 4, 5, oder 6 Spieler gew�hlt werden.
	 * 
	 * @param playercount Anzahl der Spieler, wobei 3 <= playercount <=6 einzuhalten ist
	 */
	public void makeTrickGameBoard(int playercount) {
		switch (playercount) {
		case 3:
			makeTrickGameBoardThreePlayers();
			break;
		case 4:
			makeTrickGameBoardFourPlayers();
			break;
		case 5:
			makeTrickGameBoardFivePlayers();
			break;
		case 6:
			makeTrickGameBoardSixPlayers();
			break;
		default:
			break;
		}
	}
	
	private void makeTrickGameBoardThreePlayers() {
		
	}
	
	private void makeTrickGameBoardFourPlayers() {
		
	}

	private void makeTrickGameBoardFivePlayers() {
	
	}

	private void makeTrickGameBoardSixPlayers() {
	
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * �bergebenen ViewNotification-Befehl wird ein Update des Fensters ausgef�hrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: playedCardsUpdate, otherDataUpdate,
	 * 					  	moveAcknowledged, gameStarted
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Wird durch notify() im ClientModel aufgerufen, wenn
	 * eine Chatnachricht �bergeben wird.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet eine Chatnachricht in String-Form
	 */
	public void update(Observable o, String arg) {
		
	}
}
