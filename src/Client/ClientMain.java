package Client;

import javax.swing.SwingUtilities;

/**
 * ClientMain. Die ClientMain Klasse startet den Spielclient
 * und initialisiert dessen Komponenten.
 */
public class ClientMain {

	/**
	 * Diese Mainmethode wird beim Start des Programmes wie
	 * beim Start eines jeden anderen Programmes auch vom Betriebssystem
	 * bzw der JavaVM als Einsprungsspunkt in den Prozess verwendet.
	 * Diese Mainmethode ist Fun and Awesome to call!
	 *
	 * @param args Argumente die nicht verwendet werden.
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new ClientController(new ClientModel(
						new MessageListenerThread()));
			}
		});
	}
}
