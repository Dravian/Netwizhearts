package Client.View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField messageField;
	private JList playerList;
	private JList gameList;
	private JScrollPane scrollPane;
	private JButton btnHostGame;
	private JButton btnJoinGame;
	private JButton btnLeave;
	private JTextArea chatlog;
	private Language lang;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby frame = new Lobby();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		playerList = new JList(); //TODO
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		playerList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Player1", "Player2", "Player3", "Player4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		playerList.setBounds(10, 11, 272, 191);
		contentPane.add(playerList);
		
		chatlog = new JTextArea();
		chatlog.setLineWrap(true);
		chatlog.setEditable(false);
		chatlog.setText("Spieler2: hello\r\nSpieler1: hi\r\nSpieler3: morning!\r\nSpieler2: wanna play a game of hearts?");
		chatlog.setBounds(10, 213, 564, 94);
		chatlog.setRows(5);
		
		gameList = new JList(); //TODO
		gameList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Server1   (3/4)", "Server2   (2/6)"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
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
		btnHostGame.setBounds(234, 363, 117, 25);
		contentPane.add(btnHostGame);
		
		btnJoinGame = new JButton("Join Game");
		btnJoinGame.setBounds(457, 363, 117, 25);
		contentPane.add(btnJoinGame);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(10, 364, 117, 25);
		contentPane.add(btnLeave);
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
		updateLanguage();
	}
	
	private void updateLanguage() {
		//TODO
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: joinGameSuccessful, windowChangeForced,
	 * 			  playerListUpdate, gameListUpdate, chatMessage
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		ViewNotification message = (ViewNotification) arg;
		ClientModel observed = (ClientModel) o;
		switch (message) {
		case loginSuccessful:
		case windowChangeForced:
			this.setVisible(true);
			break;
		case playerListUpdate:
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
