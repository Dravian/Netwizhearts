/**
 *
 */
package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ComObjects.ComObject;

/**
 * MessageListenerThread. Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie enthaelt den dazu noetigen Socket und ObjektStream Reader und Writer.
 */
public class MessageListenerThread extends Thread {

	private Socket connection;

	private ObjectInput in;

	private ObjectOutput out;

	private boolean run = false;

	private ClientModel model;

	private boolean isConnected = false;

	/**
	 * Implementiert die Netzwerkverbindung
	 * zum Server und ermoeglicht das empfangen
	 * und versenden von Nachrichten �ber einen TCP Socket.
	 */
	public MessageListenerThread() {

	}

	/**
	 * Initialisiert die ObjectStreams und speichert den TCP Socket im Thread.
	 * @param model ClientModel, Das Model das den Spielablauf und Serverkommunikation
	 * steuert.
	 * @param connection Socket, der Socket �ber den die TCP Verbindung laeuft.
	 * @throws IllegalArgumentException Wird geworfen bei falschen ClientModel
	 * oder Socket Argumenten.
	 * @throws IOException Wird geworfen beim fehlerbehafteten Erstellen der
	 * ObjectStreams.
	 */
	public void startConnection(final ClientModel model,
								final Socket connection) throws IllegalArgumentException, IOException {
		if (model == null || connection == null) {
			throw new IllegalArgumentException();
		}
		this.model = model;
		this.connection = connection;
		out = new ObjectOutputStream(connection.getOutputStream());
		out.flush();
		in = new ObjectInputStream(connection.getInputStream());
	}

	/**
	 * Hilfsmethode die eine bestehende Verbindung abbaut
	 * und deren Ressourcen freigibt.
	 */
	public void closeConnection() {
		try {
			run = false;
			out.close();
			in.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �ber diese Methode koennen Nachrichten an den Server versendet werden.
	 *
	 * @param object Das zu Versendende ComObjekt.
	 */
	public void send(ComObject object) {
		try {
			if (run) {
				out.writeObject(object);
				out.flush();
			}
		} catch (IOException e) {
			if (run) {
				e.printStackTrace();
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
			run = true;
			ComObject object;
			while (run) {
				object = (ComObject) in.readObject();
				object.process(model);
			}
		} catch (ClassNotFoundException e) {
			closeConnection();
			e.printStackTrace();
		} catch (EOFException e) {
			if (run) {
				closeConnection();
				e.printStackTrace();
			}
		} catch (IOException e) {
			if (run) {
				closeConnection();
				e.printStackTrace();
			}
		}
	}
  }