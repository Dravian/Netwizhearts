package windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PasswdWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswdWindow frame = new PasswdWindow();
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
	public PasswdWindow() {
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
}
