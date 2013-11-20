package Client.View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import Ruleset.RulesetType;
import Server.GameServerRepresentation;
import Client.ClientModel;
import Client.ViewNotification;

/**
 * Lobby. Diese Klasse erzeugt die Ansicht der ServerLobby auf der Client Seite, 
 * in der die Spieler neue Spiele erstellen oder offenen beitreten koennen.
 * In der Lobby werden die Benutzernamen der sich in der Lobby befindenden Spieler, 
 * sowie offene Spiele angezeigt. In der Lobby koennen Chatnachrichten gesendet 
 * und empfangen werden. ueber 'Leave' verlaesst der Spieler das Spiel. 
 * ueber 'Host Game' wird der Spieler zum CreateGame-Fenster weiter geleitet 
 * und mit 'Join Game' kann einem bereits erstellten Spiel beigetreten werden.
 */
public class Lobby extends JFrame implements Observer{

	
	private static final long serialVersionUID = 1L;
	
	List<GameServerRepresentation> gameRepList;
	private JPanel contentPane;
	private JTextField messageField;
	private JList<String> playerList;
	private JList<String> gameList;
	private JScrollPane scrollPane;
	private JButton btnHostGame;
	private JButton btnJoinGame;
	private JButton btnLeave;
	private JTextArea chatlog;
	private Language lang;
	
	/**
	 * Erstellt das Lobby-Fenster
	 */
	public Lobby() {		
		setTitle("Server Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playerList = new JList<String>();
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setBounds(10, 11, 272, 191);
		contentPane.add(playerList);
		
		chatlog = new JTextArea();
		chatlog.setLineWrap(true);
		chatlog.setEditable(false);
		chatlog.setBounds(10, 213, 564, 94);
		chatlog.setRows(5);
		
		gameList = new JList<String>();
		gameList.setBounds(292, 11, 282, 191);
		contentPane.add(gameList);
		
		scrollPane = new JScrollPane(chatlog);
		scrollPane.setBounds(10, 213, 564, 96);
		contentPane.add(scrollPane);
		
		messageField = new JTextField();
		messageField.setBounds(10, 320, 564, 31);
		contentPane.add(messageField);
		messageField.setColumns(10);
		
		btnHostGame = new JButton("Host Game");
		btnHostGame.setBounds(213, 363, 162, 25);
		contentPane.add(btnHostGame);
		
		btnJoinGame = new JButton("Join Game");
		btnJoinGame.setBounds(457, 363, 117, 25);
		contentPane.add(btnJoinGame);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(10, 364, 117, 25);
		contentPane.add(btnLeave);
	}
	
	/**
	 * Gibt den Namen des Spielleiters, des ausgewählten Spiels zurueck
	 * 
	 * @return Name des Spielleiters
	 */
	public String getChosenGameName() {
		return gameRepList.get(gameList.getSelectedIndex()).getGameMasterName();
	}
	
	/**
	 * Gibt die eingetippte Chatnachricht zurueck
	 * 
	 * @return Chatnachricht
	 */
	public String getChatMessage() {
		return messageField.getText();
	}
	
	/**
	 * Fuegt einen ActionListener fuer den 'Join' Button hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addJoinButtonListener(ActionListener a) {
		btnJoinGame.addActionListener(a);
	}
	
	/**
	 * Fuegt einen ActionListener fuer den 'Host' Button hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addHostButtonListener(ActionListener a) {
		btnHostGame.addActionListener(a);
	}
	
	/**
	 * Fuegt einen ActionListener fuer den 'Leave' Button hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addLeaveButtonListener(ActionListener a) {
		btnLeave.addActionListener(a);
	}
	
	/**
	 * Fuegt einen KeyListener fuer das Nachricht-Senden-Feld der Lobby hinzu
	 * @param k
	 */
	public void addChatMessageListener(KeyListener k) {
		messageField.addKeyListener(k);
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
			}
		});
	}
	
	private void updateLanguage() {
		switch (lang) {
		case German:
			this.setTitle("Spielübersicht");
			btnLeave.setText("Verlassen");
			btnJoinGame.setText("Beitreten");
			btnHostGame.setText("Erstellen");
			break;
		case English:
			this.setTitle("Lobby");
			btnLeave.setText("Leave");
			btnJoinGame.setText("Join");
			btnHostGame.setText("Host");
			break;
		case Bavarian:
			this.setTitle("Wer do is und wos spuin");
			btnLeave.setText("Wegadgeh");
			btnJoinGame.setText("Midspuin");
			btnHostGame.setText("Spui aufmacha");
			break;
		}		
	}
	
	private void updateGameList(List<GameServerRepresentation> gameRepresentationList) {
		int length = gameRepresentationList.size();
		String[] games = new String[length];
		for (int i = 0; i < length; i++) {
			String s = "";
			if (gameRepresentationList.get(i).hasPassword()) {
				s = "P";
			}
			games[i] = gameRepresentationList.get(i).getName() + " (" + gameRepresentationList.get(i).getCurrentPlayers()
						+ "/" + gameRepresentationList.get(i).getMaxPlayers() + ") " + s;
		}
		gameList.setListData(games);
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: loginSuccessful, joinGameSuccessful, windowChangeForced,
	 * 			  playerListUpdate, gameListUpdate, chatMessage
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		ViewNotification message = (ViewNotification) arg;
		final ClientModel observed = (ClientModel) o;
		switch (message) {
		case loginSuccessful:
		case windowChangeForced:
			this.setVisible(true);
			break;
		case playerListUpdate:
			SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				playerList.setListData((String[]) observed.getPlayerlist().toArray());
			}
		});
			break;
		case gameListUpdate:
			gameRepList = observed.getLobbyGamelist();
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					updateGameList(gameRepList);
				}
			});
			
			break;
		case joinGameSuccessful:
			this.setVisible(false);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Wird aufgerufen, wenn eine String-Nachricht im notify() 
	 * uebergeben wird. Dieser wird als Chatnachricht interpretiert und
	 * dem Chatlog angefuegt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet einen String, der eine Chatnachricht darstellt
	 */
	public void update(Observable o, String arg) {
		//TODO
		if (this.isVisible()) {
			chatlog.append(arg);;
		}
	}
}
