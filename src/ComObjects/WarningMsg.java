package ComObjects;

import java.io.Serializable;

/**
 * Identifiziert eine Warnmeldung
 */
public enum WarningMsg implements Serializable {
	LoginError, GameNotExistent, GameFull, WrongPassword, BeenKicked, CouldntKick, GameDisbanded, CouldntStart,
	UnvalidMove, WrongCard, WrongPhase, WrongNumber, WrongColour, WrongTradeCards, WrongMethodCalled, WrongPlayer;
}
