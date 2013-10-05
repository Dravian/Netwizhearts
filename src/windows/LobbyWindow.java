package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

public class LobbyWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LobbyWindow frame = new LobbyWindow();
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
	public LobbyWindow() {
		setTitle("Server Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Player1", "Player2", "Player3", "Player4"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(10, 11, 272, 191);
		contentPane.add(list);
		
		JTextArea txtrSpielerHelloSpieler = new JTextArea();
		txtrSpielerHelloSpieler.setLineWrap(true);
		txtrSpielerHelloSpieler.setEditable(false);
		txtrSpielerHelloSpieler.setText("Spieler2: hello\r\nSpieler1: hi\r\nSpieler3: morning!\r\nSpieler2: wanna play a game of hearts?");
		txtrSpielerHelloSpieler.setBounds(10, 213, 564, 94);
		txtrSpielerHelloSpieler.setRows(5);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"Server1   (3/4)", "Server2   (2/6)"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setBounds(292, 11, 282, 191);
		contentPane.add(list_1);
		
		JScrollPane scrollPane = new JScrollPane(txtrSpielerHelloSpieler);
		scrollPane.setBounds(10, 213, 564, 96);
		contentPane.add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(10, 320, 564, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnHostGame = new JButton("Host Game");
		btnHostGame.setBounds(234, 363, 117, 25);
		contentPane.add(btnHostGame);
		
		JButton btnJoinGame = new JButton("Join Game");
		btnJoinGame.setBounds(457, 363, 117, 25);
		contentPane.add(btnJoinGame);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.setBounds(10, 364, 117, 25);
		contentPane.add(btnLeave);
	}
}
