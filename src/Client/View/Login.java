/**
 * 
 */
package Client.View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
/**
 * Das Login-Fenster repräsentiert den initialen Dialog zwischen Benutzer und Client.
 * In diesem Fenster kann der Benutzer seinen Namen und die Adresse des Servers eingeben. 
 * Außerdem ist über den Login die Auswahl der Sprache möglich. 
 * Über den Login-Button wird die Verbindung zum Server hergestellt.
 * 
 * @author M4nkey
 */
public class Login extends JFrame implements Observer{

	
	private static final long serialVersionUID = -2516577977746181978L;
	
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField serverField;
	private JComboBox<Language> languageComboBox;
	private JButton btnConnect;
	private Language lang;
	private JLabel lblNickname;
	private JLabel lblHostIp;
	private JLabel lblLanguage;


	
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Erstellt das Login Fenster
	 */
	public Login() {
		lang = Language.English;
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblHostIp = new JLabel("Host IP:");
		lblHostIp.setBounds(13, 71, 110, 23);
		contentPane.add(lblHostIp);
		
		serverField = new JTextField();
		serverField.setBounds(133, 44, 155, 20);
		contentPane.add(serverField);
		serverField.setColumns(10);
		
		lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(13, 43, 110, 23);
		contentPane.add(lblNickname);
		
		nameField = new JTextField();
		nameField.setBounds(133, 72, 155, 20); 
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(108, 103, 104, 23);
		contentPane.add(btnConnect);
		
		lblLanguage = new JLabel("Language:");
		lblLanguage.setBounds(13, 8, 121, 23);
		contentPane.add(lblLanguage);
		
		languageComboBox = new JComboBox<Language>(new Language[] {Language.English, Language.German, Language.Bavarian});
		//languageComboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Deutsch", "Boarisch"}));
		languageComboBox.setSelectedIndex(0);
		languageComboBox.setBounds(133, 7, 104, 24);
		contentPane.add(languageComboBox);
		
		this.updateLanguage();
	}
	
	/**
	 * Fügt einen Listener für den 'Connect' Button des Login Fensters hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addConnectButtonListener(ActionListener a) {
		btnConnect.addActionListener(a);
	}
	
	/**
	 * Fügt einen Listener für die Sprachauswahl des Login Fensters hinzu
	 * 
	 * @param i ein ItemListener
	 */
	public void addLanguageSelectionListener(ItemListener i) {
		languageComboBox.addItemListener(i);
	}
	
	/**
	 * Ändert die Sprache des Fensters
	 * 
	 * @param l Sprache in Form des Language-Enums
	 */
	public void setLanguage(Language l) {
		lang = l;
		updateLanguage();
	}
	
	private void updateLanguage() {
		switch (lang) {
		case German: 
			btnConnect.setText("Verbinden");
			this.setTitle("Anmelden");
			lblNickname.setText("Spitzname:");
			lblHostIp.setText("Host IP:");
			lblLanguage.setText("Sprache");
			break;
		case English:
			btnConnect.setText("Connect");
			this.setTitle("Login");
			lblNickname.setText("Nickname:");
			lblHostIp.setText("Host IP:");
			lblLanguage.setText("Language");
			break;
		case Bavarian:
			btnConnect.setText("Dua zua");
			this.setTitle("Nam eigem und weidadrucka");
			lblNickname.setText("Spidsnam:");
			lblHostIp.setText("So a Nummer:");
			lblLanguage.setText("Gschmatz");
			break;
		}
		
	}
	

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * übergebenen ViewNotification-Befehl wird ein Update des Fensters ausgeführt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: windowChangeAcknowledged, windowChangeDenied
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub		
	}
}