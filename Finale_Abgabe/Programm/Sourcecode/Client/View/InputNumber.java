/**
 * 
 */
package Client.View;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Client.ClientModel;
import Client.ViewNotification;

/** 
 * InputNumber. In diesem Fenster, kann der Benutzer eine Zahl eingeben.
 */
public class InputNumber extends JFrame implements Observer{
	
	private JTextArea rsMessageArea;
	private JTextField numberField;
	private JButton btnOK;
	
	/**
	 * Erstellt ein neues InputNumber-Fenster
	 */
	public InputNumber() {
		setBounds(100, 100, 345, 154);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		rsMessageArea = new JTextArea();
		rsMessageArea.setEditable(false);
		rsMessageArea.setBounds(10, 11, 320, 72);
		getContentPane().add(rsMessageArea);
		
		numberField = new JTextField();
		numberField.setBounds(98, 94, 111, 26);
		getContentPane().add(numberField);
		numberField.setColumns(10);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(219, 94, 111, 23);
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
	 * Gibt den String zurueck, der vom User eingegeben wurde.
	 * 
	 * @return Eingabe des Users, -1 wenn keine korrekte Zahl eingegeben wurde
	 */
	public int getUserInput() {
		int n = 0;
		try {
			n = Integer.parseInt(numberField.getText());
		} catch (NumberFormatException e){
			n = -1;
		}
		return n;
	}
	
	private void setMessageText(final String text) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				rsMessageArea.setText(text);
			}
			
		});
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openInputNumber, windowChangeForced, quitGame
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case openInputNumber:
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					setMessageText(observed.getWindowText());
					numberField.setText("");
					setVisible(true);
				}
				
			});
			
			break;
		case windowChangeForced:
			this.setVisible(false);
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