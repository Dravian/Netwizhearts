/**
 * 
 */
package Client.View;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import Client.ClientModel;
import Client.ViewNotification;

import javax.swing.JTextArea;
import javax.swing.JButton;


/** 
 * ScoreWindow. Dieses Fenster zeigt den momentanen Punktestand 
 * nach jeder Runde und den Gesamtpunktestand am Ende des Spieles an.
 */
public class ScoreWindow extends JFrame implements Observer{
	
	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private JButton btnOK;
	
	public ScoreWindow() {
		getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(10, 11, 414, 202);
		getContentPane().add(textArea);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(172, 224, 89, 23);
		getContentPane().add(btnOK);
		
	}
	
	/**
	 * Fuegt dem OK-Button einen ActionListener hinzu
	 * 
	 * @param l ein ActionListener
	 */
	public void addOKButtonListener(ActionListener l) {
		btnOK.addActionListener(l);
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: showScore
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case showScore:
			textArea.setText(observed.getWindowText());//FIXME
			break;
		case quitGame:
			this.dispose();
			break;
		default:
			break;
		}
		} catch (ClassCastException e) {
			//TODO
		}
		
	}
}