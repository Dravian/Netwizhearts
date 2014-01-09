package Client.View;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import Client.ClientModel;
import Client.ViewNotification;
import Ruleset.RulesetType;

/**
 * GameLobby. Die GameLobby modelliert das Wartefenster, in dem beigetretene Spieler auf den Start 
 * des Spieles durch den Spielleiter warten. Der Spielleiter kann Spieler 
 * mit dem Remove Player Button entfernen. ueber Leave kehren die Spieler 
 * in die Lobby zurueck. Der spielinterne Chat ist ab hier verfuegbar.
 */
public class GameLobby extends JFrame implements Observer{
	
	private JPanel contentPane;
	private JTextField messageField;
	private Language lang;
	private JButton btnRemovePlayer;
	private JButton btnLeave;
	private JScrollPane scrollPaneChat;
	private JScrollPane scrollPanePlayers;
	private JTextArea chatlog;
	private JButton btnStartGame;
	private JList<String> playerList;
	private RulesetType ruleset;

	/**
	 * Erstellt das GameLobby Fenster
	 */
	public GameLobby() {
		setTitle("GameLobby");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 438, 358);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playerList = new JList<String>();
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setBounds(12, 12, 211, 130);
		playerList.addListSelectionListener(new PlayerSelectedListener());
		
		scrollPanePlayers = new JScrollPane(playerList);
		scrollPanePlayers.setBounds(12, 12, 211, 130);
		scrollPanePlayers.setViewportView(playerList);
		contentPane.add(scrollPanePlayers);
		
		chatlog = new JTextArea();
		chatlog.setBounds(12, 154, 400, 84);
		chatlog.setEditable(false);
		
		scrollPaneChat = new JScrollPane(chatlog);
		scrollPaneChat.setBounds(12, 154, 400, 84);
		scrollPaneChat.setViewportView(chatlog);
		contentPane.add(scrollPaneChat);
		
		messageField = new JTextField();
		messageField.setBounds(12, 250, 400, 31);
		contentPane.add(messageField);
		messageField.setColumns(10);
		
		btnRemovePlayer = new JButton("Kick Player");
		btnRemovePlayer.setBounds(235, 117, 177, 25);
		btnRemovePlayer.setEnabled(false);
		contentPane.add(btnRemovePlayer);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(12, 293, 117, 25);
		contentPane.add(btnLeave);
		
		btnStartGame = new JButton("Start Game");
		btnStartGame.setBounds(295, 293, 117, 25);
		btnStartGame.setEnabled(false);
		contentPane.add(btnStartGame);
	}
	
	/**
	 * Gibt den Spieler zurueck, der in der Spielerliste ausgewaehlt ist
	 * 
	 * @return Name des Spielers
	 */
	public String getSelectedPlayer() {
		return playerList.getSelectedValue();
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
		switch (lang) {
		case German:
			this.setTitle("Wartefenster");
			btnLeave.setText("Verlassen");
			btnStartGame.setText("Spiel Starten");
			btnRemovePlayer.setText("Spieler entfernen");
			break;
		case English:
			this.setTitle("Gamelobby");
			btnLeave.setText("Leave");
			btnStartGame.setText("Start Game");
			btnRemovePlayer.setText("Kick Player");
			break;
		case Bavarian:
			this.setTitle("Miasma nu auf den andan wartn");
			btnLeave.setText("Wida geh");
			btnStartGame.setText("Spui afanga");
			btnRemovePlayer.setText("Gschwerl rausschmeiﬂn");
			break;
		}		
	}
	
	private void updatePlayerList(final List<String> list) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				synchronized (list) {
					int length = list.size();
					String[] players = new String[length];
					for (int i = 0; i < length; i++) {
						players[i] = list.get(i);
					}
					playerList.setListData(players);
					if (ruleset.getMinPlayer() <= length) {
						btnStartGame.setEnabled(true);
					} else {
						btnStartGame.setEnabled(false);
					}
				}
			}
		});
		
	}
	
	/**
	 * Macht Kick und Start Game Buttons sichtbar oder unsichtbar
	 * 
	 * @param gm Name des Spielleiters
	 * @param pl Name des Spielers
	 */
	private void updateUI(final String gm, final String pl) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (gm.compareTo(pl) == 0) {
					btnRemovePlayer.setVisible(true);
					btnStartGame.setVisible(true);
				} else {
					btnRemovePlayer.setVisible(false);
					btnStartGame.setVisible(false);
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
	 * @param arg erwartet: joinGameSuccessful, playerListUpdate, windowChangeForced,
	 * 						gameStarted, quitGame
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case windowChangeForced:
			chatlog.setText("");
			this.setVisible(false);
			break;
		case playerListUpdate:
			updatePlayerList(observed.getPlayerlist());
			break;
		case joinGameSuccessful:
			updateUI(observed.getGameMaster(), observed.getPlayerName());
			updatePlayerList(observed.getPlayerlist());
			ruleset = observed.getCurrentRuleset();
			chatlog.setText("");
			this.setVisible(true);
			break;
		case gameStarted:
			this.setVisible(false);
			break;
		case quitGame:
			this.dispose();
			break;
		default:
			break;
		}
		} catch (ClassCastException e) {
			this.update(observed, (String)arg);
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
	
	class PlayerSelectedListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (playerList.isSelectionEmpty()) {
				btnRemovePlayer.setEnabled(false);
			} else {
				btnRemovePlayer.setEnabled(true);
			}
		}
		
	}
	
}
