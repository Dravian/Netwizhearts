package Client;

/** 
 * ClientState. Dieser Enumerator enthält alle Zustaende in denen sich der Client
 * befinden kann.
 */
public enum ClientState {

	/** 
	 * Zeigt an, dass der Client sich in der Login-Phase befindet.
	 */
	LOGIN,
	
	/** 
	 * Zeigt an, dass der Client mit der ServerLobby verbunden ist.
	 */
	SERVERLOBBY,
	
	ENTERGAMELOBBY,
	
	/** 
	 * Zeigt an, dass sich der Client in der Spiellobby befindet.
	 */
	GAMELOBBY,
	
	/** 
	 * Zeigt an, dass sich der Client im Spiel befindet.
	 */
	GAME,
	
	/** 
	 * Es wird eine Eingabe vom Benutzer verlangt.
	 */
	USERREQUEST,
	
	/** 
	 * Das Spiel befindet sich in der Auflösung.
	 */
	ENDING,
}
