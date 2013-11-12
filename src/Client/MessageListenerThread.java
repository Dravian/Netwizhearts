/**
 * 
 */
package Client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;

import ComObjects.ComObject;

/** 
 * Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie enthält den dazu nötigen Socket und ObjektStream Reader und Writer.
 */
public class MessageListenerThread extends Thread {
	
	private Socket socket;
	
	private ObjectInput in;
	
	private ObjectOutput out;
	
	private boolean run = false;
	
	private ClientModel model;

	/**
	 * Implementiert die Netzwerkverbindung
	 * zum Server und ermöglicht das empfangen
	 * und versenden von Nachrichten über einen TCP Socket.
	 */
	public MessageListenerThread() {
		
	}
	
	/**
	 * Initialisiert die ObjectStreams und speichert den TCP Socket im Thread.
	 * @param model ClientModel, Das Model das den Spielablauf und Serverkommunikation
	 * steuert.
	 * @param connection Socket, der Socket über den die TCP Verbindung läuft.
	 * @throws IllegalArgumentException Wird geworfen bei falschen ClientModel
	 * oder Socket Argumenten.
	 * @throws IOException Wird geworfen beim fehlerbehafteten Erstellen der
	 * ObjectStreams.
	 */
	public void startConnection(ClientModel model, Socket connection) throws IllegalArgumentException, IOException {
		
	}
	
	/**
	 * Hilfsmethode die eine bestehende Verbindung abbaut
	 * und deren Ressourcen freigibt.
	 */
	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Über diese Methode können Nachrichten an den Server versendet werden.
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
			ComObject object;
			while(run) {
				object = (ComObject) in.readObject();
				object.process(model);
			}
		} catch (ClassNotFoundException e) {
			
		} catch (IOException e) {
			if(run) {
				e.printStackTrace();
			}
		}
	}
  }