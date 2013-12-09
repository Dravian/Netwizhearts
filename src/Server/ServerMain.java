package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ComObjects.WarningMsg;

/**
 * ServerMain. Diese Klasse startet den Server und ist fuer die Konfiguration
 * des Servers verantwortlich.
 */
public class ServerMain {
	/**
	 * LobbyServer, der beim Start erstellt wird
	 */
	private static LobbyServer lobbyServer;

	/**
	 * LoginServer, der beim Start erstellt wird
	 */
	private static LoginServer loginServer;

	/**
	 * Die main-Methode erstellt einen neuen LobbyServer
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.print("Input port> ");
		String input;
		int port;
		try {
			input = in.readLine();
			if (input.isEmpty()){
				port = 4567;
				lobbyServer = new LobbyServer();
				loginServer = new LoginServer(lobbyServer, port);
			} else {
				port = Integer.parseInt(input);
				if (port < 1025 || port > 49151) {
					System.err.println("Invalid Portnumber, try again.");
					main(args);	
				} else {
					lobbyServer = new LobbyServer();
					loginServer = new LoginServer(lobbyServer, port);
				}
			}		
		} catch (NumberFormatException e) {
			System.err.println("Please input a number!");
			main(args);
		} catch (IOException e) {
			System.err.println("IO was lost");
			e.printStackTrace();
		}						
	}
}