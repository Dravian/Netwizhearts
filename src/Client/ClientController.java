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
import Client.View.ViewCard;
import Client.View.Warning;
import Ruleset.Colour;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		language = Language.English;
		
		clientModel = new ClientModel(new MessageListenerThread());
		
		login = new Login();
		login.addConnectButtonListener(new ConnectButtonListener());
		login.addLanguageSelectionListener(new LanguageSelectionListener());
		login.setVisible(true);
		clientModel.addObserver(login);
		
		lobby = new Lobby();
		lobby.addWindowListener(new LobbyCloseListener());
		lobby.addJoinButtonListener(new JoinButtonListenerLobby());
		lobby.addLeaveButtonListener(new LeaveButtonListenerLobby());
		lobby.addHostButtonListener(new HostButtonListener());
		lobby.addChatMessageListener(new ChatListenerLobby());
		clientModel.addObserver(lobby);
		
		password = new Password();
		password.addJoinButtonListener(new JoinButtonListenerPassword());
		clientModel.addObserver(password);
		
		warning = new Warning();
		warning.addOKButtonListener(new OKButtonListenerWarning());
		clientModel.addObserver(warning);
		
		createGame = new CreateGame();
		createGame.setRulesetTypes(clientModel.getRulesets());
		createGame.addCreateButtonListener(new CreateButtonListener());
		
		gameLobby = new GameLobby();
		gameLobby.addWindowListener(new GameLobbyCloseListener());
		gameLobby.addLeaveButtonListener(new LeaveButtonListenerGameLobby());
		gameLobby.addStartButtonListener(new StartGameButtonListener());
		gameLobby.addRemoveButtonListener(new KickPlayerButtonListener());
		gameLobby.addChatMessageListener(new ChatListenerGameLobby());
		clientModel.addObserver(gameLobby);
		
		game = new Game();
		game.addChatMessageListener(new ChatListenerGame());
		game.addCardMouseListener(new CardMouseListener());
		game.addWindowListener(new GameCloseListener());
		clientModel.addObserver(game);
		
		inputNumber = new InputNumber();
		inputNumber.addOKButtonListener(new OKButtonListenerInputNumber());
		clientModel.addObserver(inputNumber);
		
		chooseColour = new ChooseItem();
		chooseColour.addOKButtonListener(new OKButtonListenerChooseColour());
		clientModel.addObserver(chooseColour);
		
		chooseCards = new ChooseCards();
		chooseCards.addOKButtonListener(new OKButtonListenerChooseCards());
		clientModel.addObserver(chooseCards);
		
		scoreWindow = new ScoreWindow();
		scoreWindow.addOKButtonListener(new OKButtonListenerScoreWindow());
		clientModel.addObserver(scoreWindow);
	}
	
	class LobbyCloseListener implements WindowListener {

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
	
	class GameLobbyCloseListener implements WindowListener {

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
			clientModel.returnToLobby();
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
	
	class GameCloseListener implements WindowListener {

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
			clientModel.returnToLobby();
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
			gameLobby.setLanguage(language);
		}
		
	}
	
	class ConnectButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				clientModel.createConnection(login.getUsername(), login.getServerAdress());//FIXME
			} catch (IllegalArgumentException i) {
				//TODO
			}
		}
		
	}
	
	class JoinButtonListenerLobby implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
			String selectedGame = lobby.getChosenGameName();
			if (lobby.hasPWChosenGame()) {
				password.setVisible(true);
			} else {
				try {
					clientModel.joinGame(selectedGame, null);
				} catch (IllegalArgumentException i) {
					//TODO
				}
			}
			} catch (IndexOutOfBoundsException e) {
				//TODO
			}
		}
		
	}
	
	class JoinButtonListenerPassword implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				clientModel.joinGame(lobby.getChosenGameName(), password.getPassword());
			} catch (IllegalArgumentException i) {
				//TODO
			} catch (NullPointerException n) {
				//TODO
			}
		}
		
	}
	
	class LeaveButtonListenerLobby implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lobby.dispatchEvent(new WindowEvent(lobby, WindowEvent.WINDOW_CLOSING));
		}
		
	}
	
	class HostButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			createGame.setVisible(true);			
		}
		
	}
	
	class OKButtonListenerWarning implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			warning.setVisible(false);			
		}
		
	}
	
	class CreateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				String gameName = createGame.getGameName();
				if(gameName.compareTo("")==0) {
					String s = "";
					switch (language) {
					case German:
						s = "Spiel";
						break;
					case Bavarian:
						s = "Spui";
						break;
					case English:
						s = "Game";
						break;
					default:
						break;
					}
					gameName = clientModel.getPlayerName() + "'s " + s;
				}
				clientModel.hostGame(gameName, createGame.hasPassword(), 
				createGame.getPassword(), createGame.getSelectedRulesetType());
				createGame.setVisible(false);
			} catch (IllegalArgumentException i) {
				//TODO
			}			
		}
		
	}
	
	class ChatListenerLobby implements KeyListener {

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
	
	class ChatListenerGameLobby implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				clientModel.sendChatMessage(gameLobby.getChatMessage());
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
	
	class ChatListenerGame implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				clientModel.sendChatMessage(game.getChatMessage());
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
	
	class LeaveButtonListenerGameLobby implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clientModel.returnToLobby();
			gameLobby.setVisible(false);
		}
		
	}
	
	class StartGameButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clientModel.startGame();			
		}
		
	}
	
	class KickPlayerButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				clientModel.kickPlayer(gameLobby.getSelectedPlayer());
			} catch (IllegalArgumentException i) {
				//TODO
			}
		}

	}
	
	class CardMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			final ViewCard vc = (ViewCard)arg0.getSource();
			if (vc.isClicked()) {
				clientModel.makeMove(vc.getCard());
				vc.setClicked(false);
				game.repaint();
			} else {
				game.unclickAll();
				vc.setClicked(true);
				game.repaint();
			}
			
		}
		
	}
	
	class OKButtonListenerInputNumber implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int n = inputNumber.getUserInput();
			if (n != -1) {
				clientModel.giveInputNumber(n); 
				inputNumber.setVisible(false);
			}
			
		}
		
	}
	
	class OKButtonListenerChooseColour implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
			clientModel.giveColourSelection((Colour)chooseColour.getItemSelection());
			chooseColour.setVisible(false);
			} catch (ClassCastException e) {
				//TODO
			}
		}
		
	}
	
	class OKButtonListenerChooseCards implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
			clientModel.giveChosenCards(chooseCards.getChosenCards());
			chooseCards.setVisible(false);
			} catch (ClassCastException e) {
				//TODO
			}
		}
		
	}
	
	class OKButtonListenerScoreWindow implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
			scoreWindow.setVisible(false);
			} catch (ClassCastException e) {
				//TODO
			}
		}
		
	}
	
	
}