package Client.View;

import java.awt.EventQueue;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Dieses Fenster ermöglicht die Eingabe eines Passwortes 
 * um einem Passwortgeschütztem Spiel beizutreten 
 * oder per 'Leave' wieder in die Lobby zurückzukehren.
 * 
 * @author M4nkey
 */
public class Password extends JFrame implements Observer{

	private static final long serialVersionUID = 7994797823893327272L;
	
	private JPanel contentPane;
	private JTextField textField;

	
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
		
		JLabel lblEnterPasswordPlease = new JLabel("Enter Password:");
		lblEnterPasswordPlease.setBounds(12, 12, 199, 22);
		contentPane.add(lblEnterPasswordPlease);
		
		textField = new JTextField();
		textField.setBounds(12, 39, 246, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.setBounds(12, 80, 117, 25);
		contentPane.add(btnLeave);
		
		JButton btnJoin = new JButton("Join");
		btnJoin.setBounds(141, 80, 117, 25);
		contentPane.add(btnJoin);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
