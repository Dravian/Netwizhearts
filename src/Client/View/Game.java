package Client.View;

import java.awt.EventQueue;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Client.ClientModel;
import Client.ViewNotification;
import Ruleset.Card;
import Ruleset.Colour;
import Ruleset.DiscardedCard;
import Ruleset.HeartsCard;

/**
 * Game. Im Game Fenster laeuft das Spiel ab.Es enthaelt den Spielchat und ein GamePanel.
 * Ausserdem koennen ueber ein Dropdown-Menue Aenderungen an Hintergrundbild 
 * und Kartenhintergruenden vorgenommen werden. Schliesseen beendet das Spiel 
 * und der Spieler wird in die Lobby zurueckgeleitet.
 */
public class Game extends JFrame implements Observer{
	
	private JPanel contentPane;
	private JTextField messageField;
	private JScrollPane scrollPaneChat;
	private JTextArea chatlog;
	private GamePanel gamePanel;
	private MouseListener cardMouseListener;
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Game frame = new Game();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Erstellt das Game Fenster
	 * 
	 */
	public Game() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1024, 695);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		gamePanel = null;
		
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
////		List<DiscardedCard> diskarten= new LinkedList<DiscardedCard>();
////		diskarten.add(new DiscardedCard("Mr. Blue", HeartsCard.HerzAss));
////		diskarten.add(new DiscardedCard("Mr. White", HeartsCard.Herz2));
////		diskarten.add(new DiscardedCard("Mr. Orange", HeartsCard.Herz3));
////		diskarten.add(new DiscardedCard("Mr. Pink", HeartsCard.Herz4));
////		diskarten.add(new DiscardedCard("Mr. Brown", HeartsCard.Herz5));
////		diskarten.add(new DiscardedCard("Myself", HeartsCard.Herz6));
//
//		gamePanel = new GamePanel(players, contentPane);
//		gamePanel.setBounds(10, 11, 998, 495);
////		gamePanel.updateCardsPlayed(diskarten);
////		gamePanel.updateOwnCards(karten);
//		gamePanel.updateTrumpColour(Colour.RED);
//		contentPane.add(gamePanel);
//		// TEST
		chatlog = new JTextArea();
		chatlog.setBounds(10, 505, 998, 112);
		chatlog.setEditable(false);
		chatlog.setLineWrap(true);
		
		scrollPaneChat = new JScrollPane();
		scrollPaneChat.setBounds(10, 505, 998, 112);
		scrollPaneChat.setViewportView(chatlog);
		contentPane.add(scrollPaneChat);
		
		messageField = new JTextField();
		messageField.setBounds(10, 617, 998, 44);
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
		cardMouseListener = m;
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
	 */
	public void makeTrickGameBoard(final List<String> players) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					gamePanel = new GamePanel(players, contentPane);
					gamePanel.setBounds(10, 11, 998, 547);
					gamePanel.addCardMouseListener(cardMouseListener);
					contentPane.add(gamePanel);
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
	 * 					  	moveAcknowledged, gameStarted, windowChangeForced,
	 * 						quitGame
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
			makeTrickGameBoard(players);
			chatlog.setText("");
			this.setVisible(true);
			break;
		case gameUpdate:
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					gamePanel.updateGame(observed.getGameUpdate());
				}
			});
			break;
		case trumpUpdate:
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					gamePanel.updateTrumpColour(observed.getTrumpColour());
				}
			});
			
			repaint();
			break;
		case turnUpdate:
			//TODO Anzeigen, dass man an der Reihe ist
			chatlog.append("Du bist an der Reihe\n");
			break;
		case windowChangeForced:
			gamePanel = null;
			this.setVisible(false);
			break;
		case quitGame:
			this.dispose();
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
