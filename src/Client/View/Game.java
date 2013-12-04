package Client.View;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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

import Client.ClientModel;
import Client.ViewNotification;
import Ruleset.Card;
import Ruleset.HeartsCard;

import java.awt.Color;

/**
 * Game. Im Game Fenster laeuft das Spiel ab.Es enthaelt den Spielchat und ein GamePanel.
 * Ausserdem koennen ueber ein Dropdown-Menue Aenderungen an Hintergrundbild 
 * und Kartenhintergruenden vorgenommen werden. Schliesseen beendet das Spiel 
 * und der Spieler wird in die Lobby zurueckgeleitet.
 */
public class Game extends JFrame implements Observer{

	private static final long serialVersionUID = -2655520138213745249L;
	
	private JPanel contentPane;
	private JTextField messageField;
	private JScrollPane scrollPane;
	private JTextArea chatlog;
	private GamePanel gamePanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	 */
	public Game() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO change after testing
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//		// TEST
//		LinkedList<String> players = new LinkedList<String>();
//		players.add("Mr. Blue");
//		players.add("Mr. White");
//		players.add("Mr. Orange");
//		players.add("Mr. Pink");
//		players.add("Mr. Brown");
//		LinkedList<String> data = new LinkedList<String>();
//		data.add("1 Stiche");
//		data.add("2 Stiche");
//		data.add("3 Stiche");
//		data.add("4 Stiche");
//		data.add("5 Stiche");
//		List<Card> karten = new LinkedList<Card>();
//		karten.add(HeartsCard.HerzAss);
//		karten.add(HeartsCard.Herz2);
//		karten.add(HeartsCard.Herz3);
//		karten.add(HeartsCard.Herz4);
//		karten.add(HeartsCard.Herz5);
//		karten.add(HeartsCard.Herz6);
//
//		gamePanel = new GamePanel(players, data, contentPane);
//		gamePanel.setBounds(10, 11, 998, 547);
//		gamePanel.updateCardsPlayed(karten);
//		gamePanel.updateOwnCards(karten, new CardMouseListener());
//		// gamePanel.setOpaque(true);
//		contentPane.add(gamePanel);
//		// TEST
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 569, 998, 105);
		scrollPane.setViewportView(chatlog);
		contentPane.add(scrollPane);
		
		chatlog = new JTextArea();
		chatlog.setEditable(false);
		chatlog.setLineWrap(true);
		
		messageField = new JTextField();
		messageField.setBounds(10, 685, 998, 44);
		contentPane.add(messageField);
		messageField.setColumns(10);
	}
	
	/**
	 * Gibt die eingetippte Chatnachricht zurueck
	 * 
	 * @return Chatnachricht
	 */
	public String getChatMessage() {
		String r = messageField.getText();
		messageField.setText("");
		return r;
	}
	
	/**
	 * Fuegt einen KeyListener fuer das Nachricht-Senden-Feld der Lobby hinzu
	 * 
	 * @param k ein KeyListener
	 */
	public void addChatMessageListener(KeyListener k) {
		messageField.addKeyListener(k);
	}
	
	/**
	 * Fuegt einen MouseListener fuer die anklickbaren Karten hinzu
	 * 
	 * @param m  ein MouseListener
	 */
	public void addCardMouseListener(MouseListener m) {
		//gamePanel.addCardMouseListener(m);
	}
	
	/**
	 * Setzt alle anklickbaren Karten auf 'nicht angeklickt'.
	 * Ruft dazu die unclickAll() Methode des GamePanels auf.
	 */
	public void unclickAll() {
		gamePanel.unclickAll();
	}
	
	/**
	 * Arrangiert die Elemente der Spielfeld-Oberflaeche für ein Kartenspiel, 
	 * bei dem Stiche gemacht werden. Hierfuer hat jeder Spieler einen eigenen
	 * Ablagestapel vor sich.
	 * 
	 * @param players Liste der Spieler
	 * @param data Liste der Spielerdaten
	 */
	public void makeTrickGameBoard(final List<String> players, final List<String> data) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gamePanel = new GamePanel(players, data, contentPane);
					gamePanel.setBounds(10, 11, 998, 547);
					contentPane.add(gamePanel);
				} catch (Exception e) {
					//TODO
				}
			}
		});
		
	}
	
	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: playedCardsUpdate, otherDataUpdate,
	 * 					  	moveAcknowledged, gameStarted, windowChangeForced
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case gameStarted:
			List<String> players = observed.getPlayerlist();
			players.remove(observed.getPlayerName());
			makeTrickGameBoard(players, observed.getOtherPlayerData());
			this.setVisible(true);
			break;
		case playedCardsUpdate:
			gamePanel.updateCardsPlayed(observed.getPlayedCards());
			break;
		case otherDataUpdate:
			gamePanel.updateOtherData(observed.getOtherPlayerData());
			break;
		case moveAcknowledged:
			gamePanel.updateOwnCards(observed.getOwnHand());
			gamePanel.updateCardsPlayed(observed.getPlayedCards());
			break;
		case windowChangeForced:
			this.setVisible(false);
			break;
		default:
			break;
		}
		} catch (ClassCastException e) {
			this.update(observed, (String) arg);
		}
		
	}
	
	/**
	 * Wird durch notify() im ClientModel aufgerufen, wenn
	 * eine Chatnachricht uebergeben wird.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet eine Chatnachricht in String-Form
	 */
	public void update(Observable o, String arg) {
		if (this.isVisible()) {
			chatlog.append(arg);
		}
	}
}
