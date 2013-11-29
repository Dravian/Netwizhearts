package Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Client.View.Language;
import ComObjects.WarningMsg;

public class LanguageInterpreter {

	private Language language;

	public LanguageInterpreter(Language lang) {
		language = lang;
	}

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
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}

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
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}

	private String bavarianWarning(WarningMsg warning) {
		// TODO Ich kann kein Bayerisch!
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
		default:
			IllegalArgumentException e = new IllegalArgumentException();
			throw e;
		}
		return ret;
	}
}
