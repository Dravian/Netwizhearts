package Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.View.Language;
import ComObjects.WarningMsg;

/**
 * Diese Klasse ist dafuer zustaendig, je nach Sprache eine entsprechende
 * Warnmeldung zu generieren
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
			ret = "(" + ret + ") " + germanWarning(warning);
			break;
		case English:
			ret = "(" + ret + ") " + englishWarning(warning);
			break;
		case Bavarian:
			ret = "(" + ret + ") " + bavarianWarning(warning);
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
			ret = "Diese Aktion ist in disem Spiel nicht gültig.";
			break;
		case WrongPlayer:
			ret = "Du bist nicht am Zug!";
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
	private String englishWarning(WarningMsg warning) {
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
			ret = "Du kannst diesen Spieler nicht entfernen.";
			break;
		case CouldntStart:
			ret = "Couldn't start Game.";
			break;
		case BeenKicked:
			ret = "You have been kicked out of the Game.";
			break;
		case WrongPassword:
			ret = "Joining failed. Password is incorrekt.";
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
			ret = "This colour is not valid.";
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
	 *             falls es eine unbekannte Warnmeldung bekommt
	 */
	private String bavarianWarning(WarningMsg warning) {
		String ret;
		switch (warning) {
		case LoginError:
			ret = "Den Nam hod scho eba gnumma. Muasd da an andern aussuacha.";
			break;
		case GameNotExistent:
			ret = "Des Spui in des du geh woidst gibts nimma. Muasd in a andas geh.";
			break;
		case GameDisbanded:
			ret = "De andern draun se olle ned mit dir zum spuin.";
			break;
		case GameFull:
			ret = "Do ko koaner mehr midspuin, weil koa Stui mehr frei is.";
			break;
		case CouldntKick:
			ret = "Bist du depperd und mechsd di seiba auseschmeißn oder wos?";
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
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}
}
