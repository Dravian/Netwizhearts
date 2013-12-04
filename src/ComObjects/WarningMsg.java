package ComObjects;

import java.io.Serializable;

/**
 * Identifiziert eine Warnmeldung
 */
public enum WarningMsg implements Serializable {
	LoginError, GameNotExistent, GameFull, WrongPassword, BeenKicked, CouldntKick, GameDisbanded, CouldntStart, CouldntJoin,
	UnvalidMove, WrongCard, WrongPhase, WrongNumber, WrongColour, WrongTradeCards, WrongMethodCalled, WrongPlayer,
	EmptyUsername, EmptyAddress, WrongAddress, Portnumber, UnknownHost, ConnectionLost
}
