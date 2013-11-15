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
	private Set<Warning> warning;
	
	public ClientController() {
		clientModel = new ClientModel(new MessageListenerThread());
		
		login = new Login();
		login.addConnectButtonListener(new ConnectButtonListener());
		login.addLanguageSelectionListener(new LanguageSelectionListener());
		login.setVisible(true);
	}
	
	
	class LanguageSelectionListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			language = (Language) arg0.getItem();
			clientModel.setLanguage(language);
			login.setLanguage(language);
		}
		
	}
	
	
	class ConnectButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clientModel.createConnection(login.getUsername(), login.getServerAdress(), 1337);//FIXME Port hart codiert
		}
		
	}
}