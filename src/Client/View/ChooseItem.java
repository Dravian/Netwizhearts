/**
 * 
 */
package Client.View;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import Client.ClientModel;
import Client.ViewNotification;
import Ruleset.Colour;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


/** 
 * ChooseItem. Dieses Fenster ermoeglicht es dem Spieler aus einer Liste von Items eines auszuwaehlen.
 */
public class ChooseItem extends JFrame implements Observer{
	
	private JComboBox<Colour> comboBox;
	private JTextArea rsMessageArea;
	private JButton btnOK;
	
	public ChooseItem() {
		setBounds(100, 100, 370, 190);
		setResizable(false);
		getContentPane().setLayout(null);
		
		comboBox = new JComboBox<Colour>();
		comboBox.setBounds(10, 127, 200, 27);
		getContentPane().add(comboBox);
		
		rsMessageArea = new JTextArea();
		rsMessageArea.setEditable(false);
		rsMessageArea.setLineWrap(true);
		rsMessageArea.setBounds(10, 11, 344, 105);
		getContentPane().add(rsMessageArea);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(244, 127, 110, 27);
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
	 * Gibt das in der ComboBox ausgewahlte Objekt zurueck
	 * 
	 * @return ausgewahltes Item der ComboBox
	 */
	public Colour getItemSelection() {
		Colour ret = (Colour) comboBox.getSelectedItem();
		return ret;
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
	 * @param arg erwartet: openChooseItem, chooseItemSuccessful
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case openChooseItem:
			List<Colour> items = observed.getColoursToChooseFrom();
			for (Colour col : items) {
				comboBox.addItem(col);
			}
			setMessageText(observed.getWindowText());
			this.setVisible(true);
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