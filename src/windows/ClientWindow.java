package windows;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;

public class ClientWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public ClientWindow() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	/*	JPanel panel = new ImagePanel();
		panel.setBounds(10, 11, 764, 370);
		panel.setOpaque(false);
		contentPane.add(panel); */
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 391, 764, 105);
		contentPane.add(scrollPane);
		
		JTextArea txtrSpielerHfGl = new JTextArea();
		txtrSpielerHfGl.setLineWrap(true);
		txtrSpielerHfGl.setText("Meow: hf gl\r\nDoc Holliday: u2\r\nMark: gl gl");
		txtrSpielerHfGl.setEditable(false);
		scrollPane.setViewportView(txtrSpielerHfGl);
		
		textField = new JTextField();
		textField.setBounds(10, 507, 764, 44);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//ImageIcon image = new ImageIcon(getClass().getResource("kartenspiel.png"));
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon("./src/game.png"));
		lblNewLabel_1.setBounds(10, 11, 764, 369);
		//lblNewLabel_1.setOpaque(false);
		
		
		JButton button = new JButton("Button");
		
		JLabel lblNewLabel = new JLabel("Score    106");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(768, 373, 56, 55);
		lblNewLabel_1.add(lblNewLabel);
		lblNewLabel_1.add(button);
	contentPane.add(lblNewLabel_1);
	
/*	JButton btnNewButton = new JButton("New button");
	btnNewButton.setBounds(237, 183, 89, 23);
	lblNewLabel_1.add(btnNewButton);
	
	JLabel lblNewLabel_2 = new JLabel("SCORE 106");
	lblNewLabel_2.setBackground(new Color(192, 192, 192));
	lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel_2.setBounds(69, 102, 70, 70);
	lblNewLabel_2.setOpaque(false);
	contentPane.add(lblNewLabel_2); */
	}
	
	class ImagePanel extends JPanel{

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

	    public ImagePanel() {
	       try {                
	          image = ImageIO.read(new File("kartenspiel.png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	    }

	    @Override
		public void paintComponent(Graphics g) {
	        //super.paintComponent(g);
	        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters 
	        super.paintComponent(g);
	    }

	}
}
