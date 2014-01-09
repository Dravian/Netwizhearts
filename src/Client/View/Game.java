package Client.View;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Client.ClientModel;
import Client.ViewNotification;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;

/**
 * Game. Im Game Fenster laeuft das Spiel ab.Es enthaelt den Spielchat und ein GamePanel.
 * Ausserdem koennen ueber ein Dropdown-Menue Aenderungen an Hintergrundbild 
 * und Kartenhintergruenden vorgenommen werden. Schliesseen beendet das Spiel 
 * und der Spieler wird in die Lobby zurueckgeleitet.
 */
public class Game extends JFrame implements Observer{
	protected static String IMAGEPATH = "Data/";
	protected static String BACKSIDE = "backside.jpg.";
	protected static String BACKGROUND = "background.jpg";
	
	private JPanel contentPane;
	private JTextField messageField;
	private JScrollPane scrollPaneChat;
	private JTextArea chatlog;
	private GamePanel gamePanel;
	private MouseListener cardMouseListener;
	private JMenuBar menuBar;
	private JMenu mnBackground;
	private JMenu mnCards;
	private Language lang;
	
	/**
	 * Erstellt das Game Fenster
	 * 
	 */
	public Game() {
		lang = Language.English;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1024, 695);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setDoubleBuffered(true);
		setContentPane(contentPane);
		
		gamePanel = null;
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 133, 21);
		contentPane.add(menuBar);
		try {
		mnBackground = new JMenu("Background");
		File backgroundFolder = new File(IMAGEPATH + "backgrounds/");
		File[] backgrounds = backgroundFolder.listFiles();
		if (backgrounds != null) {
		for (int i = 0; i < backgrounds.length; i++) {
				Image icon = ImageIO.read(backgrounds[i]);
				JMenuItem item = new JMenuItem(new bgMenuAction(backgrounds[i].getName()));
				item.setIcon(new ImageIcon(icon.getScaledInstance(80, 45, -1)));
				mnBackground.add(item);
			} 
		}
		}catch (IOException e) {
				// TODO
		}
		menuBar.add(mnBackground);
		
		try {
		mnCards = new JMenu("Cards");
		File cardsFolder = new File(IMAGEPATH + "cards/");
		File[] cards = cardsFolder.listFiles();
		if (cards != null) {
		for (int i = 0; i < cards.length; i++) {
			
				Image icon = ImageIO.read(cards[i]);
				JMenuItem item = new JMenuItem(new cardsMenuAction(cards[i].getName()));
				item.setIcon(new ImageIcon(icon.getScaledInstance(35, 50, -1)));
				mnCards.add(item);
			} 
		}
		}catch (IOException e) {
				// TODO 
		}
		menuBar.add(mnCards);
		
		chatlog = new JTextArea();
		chatlog.setBounds(10, 505, 998, 112);
		chatlog.setEditable(false);
		chatlog.setLineWrap(true);
		
		scrollPaneChat = new JScrollPane();
		scrollPaneChat.setBounds(10, 494, 998, 123);
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
	 * @param playerCount Anzahl der Mitspieler
	 */
	public void makeTrickGameBoard(final int playerCount) {
		gamePanel = new GamePanel(playerCount);
		gamePanel.setBounds(10, 0, 998, 547);
		gamePanel.addCardMouseListener(cardMouseListener);
		contentPane.add(gamePanel);

	}
	
	/**
	 * Aendert die Sprache des Fensters
	 * 
	 * @param l Sprache in Form des Language-Enums
	 */
	public void setLanguage(Language l) {
		lang = l;
		updateLanguage();
	}
		
	private void updateLanguage() {
		switch (lang) {
		case German:
			this.setTitle("Spiel");
			mnBackground.setText("Hintergrund");
			mnCards.setText("Karten");
			break;
		case English:
			this.setTitle("Game");
			mnBackground.setText("Background");
			mnCards.setText("Cards");
			break;
		case Bavarian:
			this.setTitle("Spui");
			mnBackground.setText("Hindagrund");
			mnCards.setText("Kortn");
			break;
		}		
	}
	
	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: gameStarted, gameUpdate, windowChangeForced
	 * 						trumpUpdate, turnUpdate, quitGame
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case gameStarted:
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					makeTrickGameBoard(observed.getPlayerlist().size()-1);
					chatlog.setText("");
					setVisible(true);
				}
			});
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
			break;
		case turnUpdate:
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//TODO Anzeigen, dass man an der Reihe ist
				}
			});
			break;
		case windowChangeForced:
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
						if (isVisible()) {
							contentPane.remove(gamePanel);
							gamePanel = null;
							setVisible(false);
						}
				}
			});
			
			
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
	public void update(Observable o, final String arg) {
		if (this.isVisible()) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					chatlog.append(arg);
					scrollPaneChat.validate();
					scrollPaneChat.getVerticalScrollBar().setValue(scrollPaneChat.getVerticalScrollBar().getMaximum());
				}
				
			});
		}
	}
	
	class bgMenuAction extends AbstractAction {

		String name;
		
		public bgMenuAction (String s) {
			name = s;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Game.BACKGROUND = name;
			gamePanel.updateBackground();
			repaint();
		}

	}
	
	class cardsMenuAction extends AbstractAction {

		String name;
		
		public cardsMenuAction (String s) {
			name = s;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Game.BACKSIDE = name;
			ViewCard.updateBackside();
			repaint();

		}

	}
}
