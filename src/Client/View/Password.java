package Client.View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Password. Dieses Fenster ermoeglicht die Eingabe eines Passwortes 
 * um einem Passwortgeschuetztem Spiel beizutreten 
 * oder per 'Leave' wieder in die Lobby zurueckzukehren.
 */
public class Password extends JFrame implements Observer{

	private static final long serialVersionUID = 7994797823893327272L;
	
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnJoin;
	private JLabel lblEnterPasswordPlease;
	private JButton btnLeave;
	private Language lang;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Password frame = new Password();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt das Passwort Fenster
	 */
	public Password() {
		setTitle("Closed Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 286, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblEnterPasswordPlease = new JLabel("Enter Password:");
		lblEnterPasswordPlease.setBounds(12, 12, 199, 22);
		contentPane.add(lblEnterPasswordPlease);
		
		textField = new JTextField();
		textField.setBounds(12, 39, 246, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(12, 80, 117, 25);
		contentPane.add(btnLeave);
		
		btnJoin = new JButton("Join");
		btnJoin.setBounds(141, 80, 117, 25);
		contentPane.add(btnJoin);
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
		updateLanguage();
	}
	
	private void updateLanguage() {
		//TODO
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openWarning, passwordAccepted
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
