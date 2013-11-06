/**
 * 
 */
package Client.View;

import java.awt.EventQueue;
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
		setTitle("Login");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHostIp = new JLabel("Host IP:");
		lblHostIp.setBounds(13, 71, 79, 23);
		contentPane.add(lblHostIp);
		
		serverField = new JTextField();
		serverField.setBounds(109, 45, 155, 20);
		contentPane.add(serverField);
		serverField.setColumns(10);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(13, 43, 79, 23);
		contentPane.add(lblNickname);
		
		nameField = new JTextField();
		nameField.setBounds(109, 73, 155, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(109, 105, 104, 23);
		contentPane.add(btnConnect);
		
		JLabel lblNewLabel = new JLabel("Language:");
		lblNewLabel.setBounds(13, 8, 79, 23);
		contentPane.add(lblNewLabel);
		
		languageComboBox = new JComboBox<Language>(new Language[] {Language.German, Language.Bavarian, Language.English});
		//comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Deutsch", "Boarisch"}));
		languageComboBox.setSelectedIndex(0);
		languageComboBox.setBounds(109, 7, 104, 24);
		contentPane.add(languageComboBox);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub		
	}
}