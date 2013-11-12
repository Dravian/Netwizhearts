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
	private Set<Login> login;
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
}