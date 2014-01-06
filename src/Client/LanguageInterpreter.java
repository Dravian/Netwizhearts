package Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.View.Language;
import ComObjects.WarningMsg;
import Ruleset.UserMessages;

/**
 * Diese Klasse ist dafuer zustaendig, je nach Sprache eine entsprechende
 * Stringausgabe zu generieren
 */
public class LanguageInterpreter {
	/**
	 * Die Sprache in der das Spiel laufen soll
	 */
	private Language language;

	/**
	 * Konstruktor des LanguageInterpreter, die Sprache language wird mit dem
	 * uebergebenen Wert initialisiert
	 * 
	 * @param lang
	 *            ist die Sprache, die für das Spiel ausgewählt wurde
	 */
	public LanguageInterpreter(Language lang) {
		language = lang;
	}

	/**
	 * Erhaelt einen Warntyp und gibt je nach Sprache eine Stringmeldung
	 * zurueck, die in einem Warnfenster ausgegeben werden kann
	 * 
	 * @param warning
	 *            ist der Typ der Warnung, die aufgetreten ist
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es eine unbekannte Warnmeldung bekommt
	 */
	public String resolveWarning(WarningMsg warning)
			throws IllegalArgumentException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String ret = sdf.format(Calendar.getInstance().getTime());
		switch (language) {
		case German:
			ret = "(" + ret + ") " + germanWarning(warning)+"\n";
			break;
		case English:
			ret = "(" + ret + ") " + englishWarning(warning)+"\n";
			break;
		case Bavarian:
			ret = "(" + ret + ") " + bavarianWarning(warning)+"\n";
			break;
		}
		return ret;
	}

	/**
	 * Hilfsmethode, für den Fall, dass die Sprache Deutsch ist
	 * 
	 * @param warning
	 *            ist der Typ der Warnung, die aufgetreten ist
	 * 
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es eine unbekannte Warnmeldung bekommt
	 */
	private String germanWarning(WarningMsg warning)
			throws IllegalArgumentException {
		String ret;
		switch (warning) {
		case LoginError:
			ret = "Login fehlgeschlagen. Der Benutzername ist bereits vergeben.";
			break;
		case GameNotExistent:
			ret = "Beitreten fehlgeschlagen. Das Spiel existiert nicht mehr.";
			break;
		case GameDisbanded:
			ret = "Das Spiel wurde aufgelöst.";
			break;
		case GameFull:
			ret = "Beitreten fehlgeschlagen. Das Spiel ist bereits voll.";
			break;
		case CouldntKick:
			ret = "Du kannst diesen Spieler nicht entfernen.";
			break;
		case CouldntStart:
			ret = "Das Spiel konnte nicht gestartet werden.";
			break;
		case BeenKicked:
			ret = "Du wurdest aus dem Spiel geworfen.";
			break;
		case WrongPassword:
			ret = "Zutritt verweigert. Das Passwort ist inkorrekt.";
			break;
		case UnvalidMove:
			ret = "Der Zug ist nicht gültig!";
			break;
		case WrongCard:
			ret = "Diese Karte gehört nicht zum Spiel.";
			break;
		case WrongPhase:
			ret = "Deine Aktion stimmt nicht mit der momentanen Spielphase überein.";
			break;
		case WrongNumber:
			ret = "Diese Zahl ist nicht erlaubt.";
			break;
		case WrongColour:
			ret = "Diese Farbe ist nicht vorhanden.";
			break;
		case WrongTradeCards:
			ret = "Du kannst diese Karten nicht tauschen.";
			break;
		case WrongMethodCalled:
			ret = "Diese Aktion ist in diesem Spiel nicht gültig.";
			break;
		case WrongPlayer:
			ret = "Du bist nicht am Zug!";
			break;
		case EmptyUsername:
			ret = "Bitte gib einen Benutzernamen ein.";
			break;
		case EmptyAddress:
			ret = "Bitte gib eine gültige IP-Adresse ein.";
			break;
		case WrongAddress:
			ret = "Diese Adresse ist unbekannt.";
			break;
		case Portnumber:
			ret = "Portnummer außer Reichweite.";
			break;
		case UnknownHost:
			ret = "Der Host ist unbekannt.";
			break;
		case ConnectionLost:
			ret = "Die Verbindung zum Server ist verlorengegangen!";
			break;
		case CouldntJoin:
			ret = "Du kannst diesem Spiel nicht beitreten!";
			break;	
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}

	/**
	 * Hilfsmethode, für den Fall, dass die Sprache Englisch ist
	 * 
	 * @param warning
	 *            ist der Typ der Warnung, die aufgetreten ist
	 * 
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es eine unbekannte Warnmeldung bekommt
	 */
	private String englishWarning(WarningMsg warning) throws IllegalArgumentException {
		String ret;
		switch (warning) {
		case LoginError:
			ret = "Login failed. Name is already in use.";
			break;
		case GameNotExistent:
			ret = "Joining failed. The Game does not exist.";
			break;
		case GameDisbanded:
			ret = "The Game has been disbanded.";
			break;
		case GameFull:
			ret = "Joining failed. Game is already full";
			break;
		case CouldntKick:
			ret = "You cannot kick this Player.";
			break;
		case CouldntStart:
			ret = "Couldn't start Game.";
			break;
		case BeenKicked:
			ret = "You have been kicked out of the Game.";
			break;
		case WrongPassword:
			ret = "Joining failed. Password is incorrect.";
			break;
		case UnvalidMove:
			ret = "This move is not valid!";
			break;
		case WrongCard:
			ret = "This Card isn't in the game.";
			break;
		case WrongPhase:
			ret = "Your action does not match with the gamephase.";
			break;
		case WrongNumber:
			ret = "Your number is not valid.";
			break;
		case WrongColour:
			ret = "This color is not valid.";
			break;
		case WrongTradeCards:
			ret = "You cannot switch these cards.";
			break;
		case WrongMethodCalled:
			ret = "This action is not valid in this game.";
			break;
		case WrongPlayer:
			ret = "It is not your move!";
			break;
		case EmptyUsername:
			ret = "Please input a username.";
			break;
		case EmptyAddress:
			ret = "Please input a valid Host Address.";
			break;
		case WrongAddress:
			ret = "This Host Address is unrecognizable .";
			break;
		case Portnumber:
			ret = "Portnumber out of Range.";
			break;
		case UnknownHost:
			ret = "Unknown Host.";
			break;
		case ConnectionLost:
			ret = "Lost connection to the Server!";
			break;
		case CouldntJoin:
			ret = "You cannot join this Game!";
			break;	
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}

	/**
	 * Hilfsmethode, für den Fall, dass die Sprache Bayerisch ist
	 * 
	 * @param warning
	 *            ist der Typ der Warnung, die aufgetreten ist
	 * 
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es einen unbekannten Nachrichtentyp bekommt
	 */
	private String bavarianWarning(WarningMsg warning)throws IllegalArgumentException  {
		String ret;
		switch (warning) {
		case LoginError:
			ret = "Den Nam hod scho eba gnumma. Muasd da an andern aussuacha.";
			break;
		case GameNotExistent:
			ret = "Des Spui in des du geh woidst gibts nimma. Muasd in a andas geh.";
			break;
		case GameDisbanded:
			ret = "As Spui is aufglöst worn";
			break;
		case GameFull:
			ret = "Do ko koaner mehr midspuin, weil koa Stui mehr frei is.";
			break;
		case CouldntKick:
			ret = "Den kosd ned auseschmeißen.";
			break;
		case CouldntStart:
			ret = "Du kosd nuned afanga, hetz ned aso.";
			break;
		case BeenKicked:
			ret = "Der Hund hod di einfach ausegschmissn, wersd wida recht bläd dahergred hom.";
			break;
		case WrongPassword:
			ret = "Des is ned des Geheimwort des hom meng.";
			break;
		case UnvalidMove:
			ret = "Des kannst ned spuin.";
			break;
		case WrongCard:
			ret = "De Karten bassd jetzt überhaupt ned.";
			break;
		case WrongPhase:
			ret = "Du bist a weng duracheinand ha? Des is jetzt nuned dra.";
			break;
		case WrongNumber:
			ret = "De Zoi gehd neda.";
			break;
		case WrongColour:
			ret = "De Farb gibts goned.";
			break;
		case WrongTradeCards:
			ret = "Du kosd de Kartn ned dauschn.";
			break;
		case WrongMethodCalled:
			ret = "Wos treibstn du überhaupt für an Grampf?";
			break;
		case WrongPlayer:
			ret = "Du bisd do überhaupt ned dra.";
			break;
		case EmptyUsername:
			ret = "Du muasd nu eigem wiasd hoasd.";
			break;
		case EmptyAddress:
			ret = "Du hosd do nix eigem, wo 'so a Nummer' steht, de brauchma aber.";
			break;
		case WrongAddress:
			ret = "Do wo 'so a Nummer' steht, steht wos foisch drin. Do brauchst so a 'IP' dings.";
			break;
		case Portnumber:
			ret = "Do wo 'so a Nummer' steht, steht am Schluss wos foisch drin. Hoasd 'Port' oder so.";
			break;
		case UnknownHost:
			ret = "Wia kimma den Horst ..ääh Host ned finden.";
			break;
		case ConnectionLost:
			ret = "Wia hamma d'Verbindung verlorn, I fiachd, des wars.";
			break;
		case CouldntJoin:
			ret = "Du kosd do ned midspuin.";
			break;	
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}
	
	/**
	 * Erhaelt einen Nachrichtentyp und gibt je nach Sprache eine entsprechende Stringmeldung
	 * zurueck
	 * 
	 * @param message
	 *            ist der Typ der Nachricht, die aufgetreten ist
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es eine unbekannte Warnmeldung bekommt
	 */
	public String resolveWarning(UserMessages message)
			throws IllegalArgumentException {
		String ret = new String();
		switch (language) {
		case German:
			ret = germanMessage(message)+"\n";
			break;
		case English:
			ret = englishMessage(message)+"\n";
			break;
		case Bavarian:
			ret = bavarianMessage(message)+"\n";
			break;
		}
		return ret;
	}
	
	/**
	 * Hilfsmethode, für den Fall, dass die Sprache Deutsch ist
	 * 
	 * @param message
	 *            ist der Typ der Nachricht, die aufgetreten ist
	 * 
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es einen unbekannten Nachrichtentyp bekommt
	 */
	private String germanMessage(UserMessages message)throws IllegalArgumentException  {
		String ret;
		switch (message) {
		case ChooseColour:
			ret = "Wähle die Trumpffarbe aus.";
			break;
		case ChooseCards:
			ret = "Wähle drei Karten, die einem anderen Spieler gegeben werden.";
			break;
		case ChooseNumber:
			ret = "Gib ein wie viele Stiche du machen willst..";
			break;
		case GameEnd:
			ret = "Das Spiel ist vorbei.";
			break;
		case TrumpColour:
			ret = "Es wurde eine neue Trumpffarbe ausgewählt.";
			break;
		case PlayCard:
			ret = "Du bist dran. Spiele eine Karte.";
			break;
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}
	
	/**
	 * Hilfsmethode, für den Fall, dass die Sprache Englisch ist
	 * 
	 * @param message
	 *            ist der Typ der Nachricht, die aufgetreten ist
	 * 
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es einen unbekannten Nachrichtentyp bekommt
	 */
	private String englishMessage(UserMessages message) throws IllegalArgumentException {
		String ret;
		switch (message) {
		case ChooseColour:
			ret = "Choose the trump colour";
			break;
		case ChooseCards:
			ret = "Choose three cards to give to another player.";
			break;
		case ChooseNumber:
			ret = "Enter how many tricks you think you will make.";
			break;
		case GameEnd:
			ret = "The game has ended.";
			break;
		case TrumpColour:
			ret = "A new trump colour has been chosen.";
			break;
		case PlayCard:
			ret = "It is your turn. Play a card.";
			break;
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;	
	}
	
	/**
	 * Hilfsmethode, für den Fall, dass die Sprache Bayerisch ist
	 * 
	 * @param message
	 *            ist der Typ der Nachricht, die aufgetreten ist
	 * 
	 * @return gibt eine entsprechende Stringmeldung zurück
	 * @throws IllegalArgumentException
	 *             falls es einen unbekannten Nachrichtentyp bekommt
	 */
	private String bavarianMessage(UserMessages message) throws IllegalArgumentException {
		String ret;
		switch (message) {
		case ChooseColour:
			ret = "Suach da d'Trumpffarb aus.";
			break;
		case ChooseCards:
			ret = "Suach da drei Kartn aus, de am andan zuagschom wern.";
			break;
		case ChooseNumber:
			ret = "Gib ei wiavui Stich dassd moansd, dassd machsd.";
			break;
		case GameEnd:
			ret = "As Spui is vorbei.";
			break;
		case TrumpColour:
			ret = "A neie Farb is Trumpf.";
			break;
		case PlayCard:
			ret = "Du bist dra. Spui a Kartn.";
			break;
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}
}
