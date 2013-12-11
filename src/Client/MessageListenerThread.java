/**
 *
 */
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

	private Socket connection;

	private ObjectInputStream in;

	private ObjectOutputStream out;

	private boolean run;
	
	private boolean socketSet;

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
	 * @param model ClientModel, Das Model das den Spielablauf und Serverkommunikation
	 * steuert.
	 * @param connection Socket, der Socket über den die TCP Verbindung laeuft.
	 * @throws IllegalArgumentException Wird geworfen bei falschen ClientModel
	 * oder Socket Argumenten.
	 * @throws IOException Wird geworfen beim fehlerbehafteten Erstellen der
	 * ObjectStreams.
	 */
	public void startConnection(ClientModel model,
								String host,
								int port) throws IllegalArgumentException, IOException {
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
				System.out.println("ERROR: While closing network ressources.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Über diese Methode koennen Nachrichten an den Server versendet werden.
	 *
	 * @param object Das zu Versendende ComObjekt.
	 */
	public void send(ComObject object) {
		if (run) {
			try {
				out.writeUnshared(object);
				out.flush();
				out.reset();
			} catch (IOException e) {
				System.out.println("ERROR: Write to Object Stream failed.");
				e.printStackTrace();
				closeConnection();
				model.closeView();
			}
		}
	}

	/**
	 * Initialisiert den In- und OutputStream
	 * und liest ComObjekte solange der Thread
	 * lebt von seinem InputStream.
	 */
	public void run() {
		try {
			ComObject object;
			while (run) {
				object = (ComObject) in.readUnshared();
				object.process(model);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Unknown Object received.");
			e.printStackTrace();
		} catch (EOFException e) {
			if (run) {
				System.err.println("ERROR: Object Stream emty.");
				e.printStackTrace();
			}
		} catch (SocketException e) {
			if (run) {
				System.err.println("ERROR: Connection reset.");
				e.printStackTrace();
			}
		} catch (IOException e) {
			if (run) {
				System.err.println("ERROR: Network IO failed.");
				e.printStackTrace();
			}
		} finally {
			if(run) {
				closeConnection();
				model.closeView();
			}
		}
	}
  }