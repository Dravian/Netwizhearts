/**
 * 
 */
package Client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import ComObjects.ComObject;

/** 
 * Diese Klasse implementiert die Netzwerkanbindung des Clients an den Server.
 * Sie ist eine innere Klasse des ClientModels und wird vom selbigen instanziert.
 * Sie enthält den dazu nötigen Socket und ObjektStream Reader und Writer.
 */
class MessageListenerThread extends Thread {
	
	private ObjectInput in;
	
	private ObjectOutput out;
	
	private boolean run = false;
	
	private ClientModel model;

	/**
	 * Erstellt die Verbindung zum Server.
	 * @param connection TCP Socket über den die Verbindung erstellt wird.
	 * @throws IOException Diese Exception wird an den Aufrufenden weitergeleitet.
	 */
	protected MessageListenerThread(ClientModel model, ObjectInput in, ObjectOutput out) throws IOException {
		this.out = out;
		out.flush();
		this.in = in;
		
	}
	
	/**
	 * Hilfsmethode die eine bestehende Verbindung abbaut
	 * und deren Ressourcen freigibt.
	 */
	protected void closeConnection() {
		
	}
	
	/**
	 * Über diese Methode können Nachrichten an den Server versendet werden.
	 */
	protected void send(ComObject object) {
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