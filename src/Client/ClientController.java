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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
		//login.addWindowListener(new windowCloseListener());
		login.addConnectButtonListener(new ConnectButtonListener());
		login.addLanguageSelectionListener(new LanguageSelectionListener());
		login.setVisible(true);
		clientModel.addObserver(login);
		
		lobby = new Lobby();
		lobby.addWindowListener(new windowCloseListener());
		lobby.addJoinButtonListener(new JoinButtonListenerLobby());
		lobby.addLeaveButtonListener(new LeaveButtonListenerLobby());
		lobby.addHostButtonListener(new HostButtonListener());
		lobby.addChatMessageListener(new ChatListener());
		clientModel.addObserver(lobby);
		
		password = new Password();
		password.addJoinButtonListener(new JoinButtonListenerPassword());
		clientModel.addObserver(password);
		
		warning = new Warning();
		warning.addOKButtonListener(new okButtonListener());
		clientModel.addObserver(warning);
		
		createGame = new CreateGame();
		createGame.setRulesetTypes(clientModel.getRulesets());
		createGame.addCreateButtonListener(new CreateButtonListener());
	}
	
	class windowCloseListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// not needed
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// not needed
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			clientModel.closeProgram();	
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// not needed
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// not needed
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// not needed
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// not needed
			
		}
		
	}
	
	
	class LanguageSelectionListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			language = (Language) arg0.getItem();
			clientModel.setLanguage(language);
			login.setLanguage(language);
			lobby.setLanguage(language);
			password.setLanguage(language);
			createGame.setLanguage(language);
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
			clientModel.closeProgram();
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
	
	class CreateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clientModel.hostGame(createGame.getGameName(), createGame.hasPassword(), 
						createGame.getPassword(), createGame.getSelectedRulesetType());
			createGame.setVisible(false);
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
			// not needed
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// not needed
			
		}
		
	}
}