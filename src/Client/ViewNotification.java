package Client;

/** 
 * ViewNotification. Enum, das vom ClientModel ueber notify an
 * seine Observer geschickt wird, um mitzuteilen, welche Veraenderung
 * stattgefunden hat.
 */
public enum ViewNotification {
	gameUpdate,

	trumpUpdate,

	turnUpdate,

	playerListUpdate,

	gameListUpdate,

	chatMessage,

	loginSuccessful,

	joinGameSuccessful,

	gameStarted,

    windowChangeForced,

	openChooseCards,

	openChooseItem,

	openInputNumber,

	openWarning,

	showScore,

	quitGame
}
