package Client.View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * GameLobby. Die GameLobby modelliert das Wartefenster, in dem beigetretene Spieler auf den Start 
 * des Spieles durch den Spielleiter warten. Der Spielleiter kann Spieler 
 * mit dem Remove Player Button entfernen. ueber Leave kehren die Spieler 
 * in die Lobby zurueck. Der spielinterne Chat ist ab hier verfuegbar.
 */
public class GameLobby extends JFrame implements Observer{

	private static final long serialVersionUID = -1899311213351027436L;
	
	private JPanel contentPane;
	private JTextField messageField;
	private Language lang;
	private JButton btnRemovePlayer;
	private JButton btnLeave;
	private JTextArea chatlog;
	private JButton btnStartGame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameLobby frame = new GameLobby();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt das GameLobby Fenster
	 */
	public GameLobby() {
		setTitle("Game Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 403, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Player1", "Player2", "Player3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(12, 12, 211, 130);
		contentPane.add(list);
		
		chatlog = new JTextArea();
		chatlog.setBounds(12, 154, 367, 84);
		contentPane.add(chatlog);
		
		messageField = new JTextField();
		messageField.setBounds(12, 250, 367, 31);
		contentPane.add(messageField);
		messageField.setColumns(10);
		
		btnRemovePlayer = new JButton("Remove Player");
		btnRemovePlayer.setBounds(235, 117, 138, 25);
		contentPane.add(btnRemovePlayer);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(12, 293, 117, 25);
		contentPane.add(btnLeave);
		
		btnStartGame = new JButton("Start Game");
		btnStartGame.setBounds(262, 293, 117, 25);
		contentPane.add(btnStartGame);
	}

	/**
	 * Fuegt einen ActionListener fuer den 'Start Game' Button hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addStartButtonListener(ActionListener a) {
		btnStartGame.addActionListener(a);
	}
	
	/**
	 * Fuegt einen ActionListener fuer den 'Remove Player' Button hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addRemoveButtonListener(ActionListener a) {
		btnRemovePlayer.addActionListener(a);
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
	 * Fuet einen KeyListener fuer das Nachricht-Senden-Feld der Lobby hinzu
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
	 * @param arg erwartet: joinGameSuccessful, playerListUpdate, windowChangeForced,
	 * 						gameStarted
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
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
	}
}
