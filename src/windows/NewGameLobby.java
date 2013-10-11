package windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class NewGameLobby extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGameLobby frame = new NewGameLobby();
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
	public NewGameLobby() {
		setTitle("Game Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 403, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Player1", "Player2", "Player3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(12, 12, 211, 130);
		contentPane.add(list);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 154, 367, 84);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(12, 250, 367, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Remove Player");
		btnNewButton_1.setBounds(235, 117, 138, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Leave");
		btnNewButton_2.setBounds(12, 293, 117, 25);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Start Game");
		btnNewButton_3.setBounds(262, 293, 117, 25);
		contentPane.add(btnNewButton_3);
	}
}
