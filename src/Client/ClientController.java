/**
 * 
 */
package Client;

import Client.View.ChooseCards;
import Client.View.ScoreWindow;
import Client.View.InputNumber;
import Client.View.ChooseItem;
import Client.View.Lobby;

import java.util.Set;

import Client.View.Login;
import Client.View.CreateGame;
import Client.View.GameLobby;
import Client.View.Game;
import Client.View.Password;
import Client.View.Language;
import Client.View.Warning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** 
 * ClientController. Der ClientController enthaelt alle ActionListener der View und 
 * leitet durch diese Benutzereingaben an das ClientModel weiter. Sobald der 
 * ClientController von der ClientMain-Klasse erzeugt wird, erzeugt er wiederum
 * das ClientModel und die Fenster der ClientView, wobei zunaechst nur das Login
 * Fenster sichtbar ist.
 */
public class ClientController {
	/** 
	 */
	private ClientModel clientModel;
	/** 
	 */
	private ChooseCards chooseCards;
	/** 
	 */
	private ScoreWindow scoreWindow;
	/** 
	 */
	private InputNumber inputNumber;
	/** 
	 */
	private ChooseItem chooseColour;
	/** 
	 */
	private Lobby lobby;
	/** 
	 */
	private Login login;
	/** 
	 */
	private CreateGame createGame;
	/** 
	 */
	private GameLobby gameLobby;
	/** 
	 */
	private Game game;
	/** 
	 */
	private Password password;
	/** 
	 */
	private Language language;
	/** 
	 */
	private Warning warning;
	
	public ClientController() {
		clientModel = new ClientModel(new MessageListenerThread());
		
		login = new Login();
		login.addConnectButtonListener(new ConnectButtonListener());
		login.addLanguageSelectionListener(new LanguageSelectionListener());
		login.setVisible(true);
		clientModel.addObserver(login);
		
		lobby = new Lobby();
		lobby.addJoinButtonListener(new JoinButtonListenerLobby());
		lobby.addLeaveButtonListener(new LeaveButtonListenerLobby());
		lobby.addHostButtonListener(new HostButtonListener());
		clientModel.addObserver(lobby);
		
		password = new Password();
		password.addJoinButtonListener(new JoinButtonListenerPassword());
		clientModel.addObserver(password);
		
		warning = new Warning();
		warning.addOKButtonListener(new okButtonListener());
		clientModel.addObserver(warning);
		
		createGame = new CreateGame();
	}
	
	
	class LanguageSelectionListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			language = (Language) arg0.getItem();
			clientModel.setLanguage(language);
			login.setLanguage(language);
			lobby.setLanguage(language);
			password.setLanguage(language);
		}
		
	}
	
	class ConnectButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clientModel.createConnection(login.getUsername(), login.getServerAdress(), 1337);//FIXME Port hart codiert
		}
		
	}
	
	class JoinButtonListenerLobby implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			password.setVisible(true);
			
		}
		
	}
	
	class JoinButtonListenerPassword implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clientModel.joinGame(lobby.getChosenGameName(), password.getPassword());
		}
		
	}
	
	class LeaveButtonListenerLobby implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clientModel.terminateProgram();;
		}
		
	}
	
	class HostButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			createGame.setVisible(true);			
		}
		
	}
	
	class okButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			warning.setVisible(false);			
		}
		
	}
	
	class ChatListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				clientModel.sendChatMessage(lobby.getChatMessage());
				break;
			default:
				break;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}