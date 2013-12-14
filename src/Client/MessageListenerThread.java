package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import ComObjects.ComObject;

/**
 * MessageListenerThread.
 * Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie enthaelt den dazu noetigen Socket und ObjektStream Reader und Writer.
 */
public class MessageListenerThread implements Runnable {

	/**
	 * Der TCP Socket.
	 */
	private Socket connection;

	/**
	 * Referenz auf den Ausgabestrom.
	 */
	private ObjectInputStream in;

	/**
	 * Referenz auf den Eingabestrom.
	 */
	private ObjectOutputStream out;

	/**
	 * Signalisiert den Zustand des Threads.
	 */
	private boolean run;

	/**
	 * Signalisiert ob ein TCP Socket aktiv ist.
	 */
	private boolean socketSet;

	/**
	 * Referenz auf das zugrundeliegende Model des Clients.
	 */
	private ClientModel model;

	/**
	 * Implementiert die Netzwerkverbindung
	 * zum Server und ermoeglicht das empfangen
	 * und versenden von Nachrichten über einen TCP Socket.
	 */
	public MessageListenerThread() {

	}

	/**
	 * Initialisiert die ObjectStreams und speichert den TCP Socket im Thread.
	 *
	 * @param model ClientModel, Das Model das den Spielablauf und
	 * Serverkommunikation steuert.
	 * oder Socket Argumenten.
	 * @throws IOException Wird geworfen beim fehlerbehafteten Erstellen der
	 * ObjectStreams.
	 */
	public void startConnection(ClientModel model,
								String host,
								int port) throws IllegalArgumentException,
												 IOException {
		if (model == null || host == null) {
			throw new IllegalArgumentException();
		}
		this.model = model;
		this.connection = new Socket(host, port);
		out = new ObjectOutputStream(connection.getOutputStream());
		out.flush();
		in = new ObjectInputStream(connection.getInputStream());
		run = true;
		socketSet = true;
	}

	/**
	 * Hilfsmethode die eine bestehende Verbindung abbaut
	 * und deren Ressourcen freigibt.
	 */
	public void closeConnection() {
		run = false;
		if (socketSet) {
			socketSet = false;
			try {
				out.close();
				in.close();
				connection.close();
			} catch (IOException e) {
				System.out.println("Fehler beim schließen des Netzwerkes");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Über diese Methode koennen Nachrichten an den Server versendet werden.
	 * Nimmt ein ComObjekt und schreibt es in den Objektstrom.
	 *
	 * @param object Das zu Versendende ComObjekt.
	 */
	public void send(final ComObject object) {
		if (run) {
			try {
				out.writeObject(object);
				out.flush();
			} catch (IOException e) {
				System.out.println("Schreibvorgang fehlgeschlagen");
				e.printStackTrace();
				closeConnection();
				model.closeView();
			}
		}
	}

	/**
	 * Liest ComObjekte solange der Thread
	 * lebt von seinem InputStream und schließt bei 
	 * einem Verbindungsabbruch die Verbindung.
	 */
	public void run() {
		try {
			ComObject object;
			while (run) {
				object = (ComObject) in.readObject();
				object.process(model);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Unbekanntes Objekt empfangen");
			e.printStackTrace();
		} catch (EOFException e) {
			if (run) {
				System.err.println("Ende des Objektstroms");
				e.printStackTrace();
			}
		} catch (SocketException e) {
			if (run) {
				System.err.println("Verbindung verloren");
				e.printStackTrace();
			}
		} catch (IOException e) {
			if (run) {
				System.err.println("Netzwerkfehler");
				e.printStackTrace();
			}
		} finally {
			if (run) {
				closeConnection();
				model.closeView();
			}
		}
	}
  }