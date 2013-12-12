/**
 * 
 */
package Client.View;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import Client.ViewNotification;
/**
 * Login. Das Login-Fenster repraesentiert den initialen Dialog zwischen Benutzer und Client.
 * In diesem Fenster kann der Benutzer seinen Namen und die Adresse des Servers eingeben. 
 * Ausserdem ist ueber den Login die Auswahl der Sprache moeglich. 
 * ueber den Login-Button wird die Verbindung zum Server hergestellt.
 */
public class Login extends JFrame implements Observer{
	
	private JPanel contentPane;
	private JTextField serverField;
	private JTextField nameField;
	private JComboBox<Language> languageComboBox;
	private JButton btnConnect;
	private Language lang;
	private JLabel lblNickname;
	private JLabel lblHostIp;
	private JLabel lblLanguage;

	/**
	 * Erstellt das Login Fenster
	 */
	public Login() {
		lang = Language.English;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 169);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblHostIp = new JLabel("Host IP:");
		lblHostIp.setBounds(13, 71, 110, 23);
		contentPane.add(lblHostIp);
		
		nameField = new JTextField();
		nameField.setBounds(133, 44, 155, 20);
		nameField.setColumns(10);
		nameField.setText("TestSpieler");//FIXME Nur fuer Testzwecke
		contentPane.add(nameField);
		
		lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(13, 43, 110, 23);
		contentPane.add(lblNickname);
		
		serverField = new JTextField();
		serverField.setBounds(133, 72, 155, 20); 
		serverField.setColumns(10);
		serverField.setText("localhost");//FIXME Nur fuer Testzwecke
		contentPane.add(serverField);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(108, 103, 104, 23);
		contentPane.add(btnConnect);
		
		lblLanguage = new JLabel("Language:");
		lblLanguage.setBounds(13, 8, 121, 23);
		contentPane.add(lblLanguage);
		
		languageComboBox = new JComboBox<Language>(new Language[] {Language.English, Language.German, Language.Bavarian});
		languageComboBox.setSelectedIndex(0);
		languageComboBox.setBounds(133, 7, 104, 24);
		contentPane.add(languageComboBox);
		
		this.updateLanguage();
	}
	
	/**
	 * Gibt den Namen zurueck, der im nameField eingegeben wurde
	 * 
	 * @return Name des Spielers
	 */
	public String getUsername() {
		return nameField.getText();
	}
	
	/**
	 * Gibt die Adresse des Servers zurueck, die im serverField eingegeben wurde
	 * 
	 * @return IP Adresse des Servers als String
	 */
	public String getServerAdress() {
		return serverField.getText();
	}
	
	/**
	 * Fuegt einen Listener fuer den 'Connect' Button des Login Fensters hinzu
	 * 
	 * @param a ein ActionListener
	 */
	public void addConnectButtonListener(ActionListener a) {
		btnConnect.addActionListener(a);
	}
	
	/**
	 * Fuegt einen Listener fuer die Sprachauswahl des Login Fensters hinzu
	 * 
	 * @param i ein ItemListener
	 */
	public void addLanguageSelectionListener(ItemListener i) {
		languageComboBox.addItemListener(i);
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
			this.setTitle("Anmelden");
			btnConnect.setText("Verbinden");
			lblNickname.setText("Spitzname:");
			lblHostIp.setText("Host IP:");
			lblLanguage.setText("Sprache");
			break;
		case English:
			this.setTitle("Login");
			btnConnect.setText("Connect");
			lblNickname.setText("Nickname:");
			lblHostIp.setText("Host IP:");
			lblLanguage.setText("Language");
			break;
		case Bavarian:
			this.setTitle("Nam eigem und weidadrucka");
			btnConnect.setText("Dua zua");
			lblNickname.setText("Spidsnam:");
			lblHostIp.setText("So a Nummer:");
			lblLanguage.setText("Gschmatz");
			break;
		}
		
	}
	

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen ViewNotification-Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: windowChangeForced
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub		
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
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
			
		}
		
	}
}