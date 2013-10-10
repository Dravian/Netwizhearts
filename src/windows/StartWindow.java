package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class StartWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow frame = new StartWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartWindow() {
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
		
		textField_1 = new JTextField();
		textField_1.setBounds(109, 45, 155, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(13, 43, 79, 23);
		contentPane.add(lblNickname);
		
		textField = new JTextField();
		textField.setBounds(109, 73, 155, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(109, 105, 104, 23);
		contentPane.add(btnConnect);
		
		JLabel lblNewLabel = new JLabel("Language:");
		lblNewLabel.setBounds(13, 8, 79, 23);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "Deutsch", "Boarisch"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(109, 7, 104, 24);
		contentPane.add(comboBox);
	}
}
