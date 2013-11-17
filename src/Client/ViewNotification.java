/**
 * 
 */
package Client;

/** 
 * ViewNotification. Enum, das vom ClientModel ueber notify an
 * seine Observer geschickt wird, um mitzuteilen, welche Veraenderung
 * stattgefunden hat.
 */
public enum ViewNotification {	
	moveAcknowledged,
	
	chooseCardsSuccessful,
	
	InputNumberSuccessful,
	
	chooseItemSuccessful,
	
	playerListUpdate,
	
	gameListUpdate,
	
	chatMessage,
	
	loginSuccessful,
	
	joinGameSuccessful,
	
	gameStarted,
	
	passwordAccepted,
	
	playedCardsUpdate,
	
	otherDataUpdate,
	
    windowChangeForced,
	
	openChooseCards,
	
	openChooseItem,
	
	openInputNumber,
	
	openWarning,
	
	showScore
	
}