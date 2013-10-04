package windows;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Graphics;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateGameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private BufferedImage image;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public CreateGameWindow() throws IOException {
		setTitle("Game Creation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select Game");
		lblNewLabel.setBounds(12, 12, 95, 24);
		getContentPane().add(lblNewLabel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(12, 39, 188, 24);
		comboBox.addItem("Wizard");
		getContentPane().add(comboBox);
		
		JCheckBox chckbxPassword = new JCheckBox("Password:");
		chckbxPassword.setBounds(12, 151, 117, 23);
		getContentPane().add(chckbxPassword);
		
		JButton btnNewButton = new JButton("Leave");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(12, 236, 117, 25);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(12, 182, 188, 32);
		getContentPane().add(textField);
		textField.setColumns(10);

	    image = ImageIO.read(new File("src/wizard.jpg"));
		
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        g.drawImage(image, 0, 0, null);          
			    }
		};

		panel.setBounds(235, 12, 139, 171);
		getContentPane().add(panel);
		
		JButton btnNewButton_1 = new JButton("Create");
		btnNewButton_1.setBounds(257, 236, 117, 25);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblPlayers = new JLabel("Players");
		lblPlayers.setBounds(12, 75, 70, 28);
		getContentPane().add(lblPlayers);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(12, 104, 53, 24);
		comboBox_1.addItem("3");
		getContentPane().add(comboBox_1);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGameWindow frame = new CreateGameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
