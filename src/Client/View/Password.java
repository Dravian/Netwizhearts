package Client.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Client.ViewNotification;

/**
 * Password. Dieses Fenster ermoeglicht die Eingabe eines Passwortes 
 * um einem Passwortgeschuetztem Spiel beizutreten 
 * oder per 'Leave' wieder in die Lobby zurueckzukehren.
 */
public class Password extends JFrame implements Observer{
	
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnJoin;
	private JLabel lblEnterPasswordPlease;
	private JButton btnLeave;
	private Language lang;

	/**
	 * Erstellt das Passwort Fenster
	 */
	public Password() {
		setTitle("Closed Game");
		lang = Language.English;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 286, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblEnterPasswordPlease = new JLabel("Enter Password:");
		lblEnterPasswordPlease.setBounds(12, 12, 246, 22);
		contentPane.add(lblEnterPasswordPlease);
		
		textField = new JTextField();
		textField.setBounds(12, 39, 246, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(12, 80, 117, 25);
		btnLeave.addActionListener(new LeaveButtonListener());
		contentPane.add(btnLeave);
		
		btnJoin = new JButton("Join");
		btnJoin.setBounds(141, 80, 117, 25);
		contentPane.add(btnJoin);
	}
	
	/**
	 * Gibt das eingegebene Passwort zurueck.
	 * 
	 * @return das eingegebene Passwort
	 */
	public String getPassword() {
		return textField.getText();
	}
	
	/**
	 * Fuegt einen ActionListener fuer den 'Join' Button hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addJoinButtonListener(ActionListener a) {
		btnJoin.addActionListener(a);
	}
	
	/**
	 * Aendert die Sprache des Fensters
	 * 
	 * @param l Sprache in Form des Language-Enums
	 */
	public void setLanguage(Language l) {
		lang = l;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				updateLanguage();
			}
		});
	}
	
	private void updateLanguage() {
		switch (lang) {
		case German:
			this.setTitle("Spiel ist Passwort-geschützt");
			btnJoin.setText("Beitreten");
			btnLeave.setText("Verlassen");
			lblEnterPasswordPlease.setText("Passwort eingeben:");
			break;
		case English:
			this.setTitle("Game is password protected");
			btnJoin.setText("Join");
			btnLeave.setText("Leave");
			lblEnterPasswordPlease.setText("Enter Password:");
			break;
		case Bavarian:
			this.setTitle("Do derf ned a jeda eine");
			btnJoin.setText("Midspuin");
			btnLeave.setText("Wida geh");
			lblEnterPasswordPlease.setText("Gib as Geheimwort ei:");
			break;
		default:
			break;

		}
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: passwordAccepted
	 */
	@Override
	public void update(Observable o, Object arg) {
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case joinGameSuccessful:
			this.setVisible(false);
			break;
		case quitGame:
			this.dispose();
			break;
		default:
			break;
		}
		} catch (ClassCastException e) {
		}
		
		
	}
	
	class LeaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
			
		}
		
	}
}
