/**
 * 
 */
package Client.View;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import Client.ClientModel;
import Client.ViewNotification;

/** 
 * Warning. Das Warning-Fenster zeigt dem Benutzer Fehlermeldungen bzw. Hinweise an, 
 * welche vom ClientModel uebergeben wurden. Es wird nur im Fehlerfall angezeigt.
 */
public class Warning extends JFrame implements Observer{
	
	private JTextArea warningTextArea;
	private JButton okButton;
	
	public Warning() {
		setTitle("Warning");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		warningTextArea = new JTextArea();
		warningTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
		warningTextArea.setEditable(false);
		warningTextArea.setLineWrap(true);
		warningTextArea.setBounds(10, 11, 406, 118);
		getContentPane().add(warningTextArea);
		
		okButton = new JButton("OK");
		okButton.setBounds(162, 140, 89, 23);
		getContentPane().add(okButton);
	}
	
	//setWarningText() entfernt
	//setOKButtonListener() hinzugefügt
	
	/**
	 * Setzt den Listener für den OK Button des Fensters
	 * 
	 * @param a ActionListener
	 */
	public void addOKButtonListener(ActionListener a) {
		okButton.addActionListener(a);
	}
	
	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * übergebenen Befehl wird ein Update des Fensters ausgeführt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openWarning
	 */
	@Override
	public void update(Observable o, Object arg) {
		ViewNotification message = (ViewNotification) arg;
		final ClientModel observed = (ClientModel) o;
		switch (message) {
		case openWarning:
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					warningTextArea.setText(observed.getWarningText());
				}
			});
			
			this.setVisible(true);
			break;
		default:
			break;
		}
	}
}