/**
 * 
 */
package Client.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** 
 * OhterPlayer. Zeigt die Informationen über die anderen Spieler an, also den Namen, 
 * ein Symbol für die verdeckte Hand und das Label für zusaetzliche Angaben.
 */
public class OtherPlayer extends JPanel{
	
	private static String IMAGEPATH = "Data/";
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String info;
	
	private int xPos;
	
	private int yPos;
	
	private BufferedImage image;
	
	
	/**
	 * Erstellt einen neuen OtherPlayer
	 * 
	 * @param n Name des Spielers
	 * @param i Informationen ueber den Spieler
	 */
	public OtherPlayer(String n, String i) {
		name = n;
		info = i;
		xPos = 0;
		yPos = 0;
		try {
			image = ImageIO.read(new File(IMAGEPATH + "hand.jpg"));
		} catch (IOException e) {
			image = null;
		}
	}
	
	/**
	 * Setzt die Position des Objekts
	 * 
	 * @param x X-Position
	 * @param y Y-Position
	 */
	public void setPosition(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	/**
	 * Setzt die Information, die in OtherPlayer mitgespeichert wird
	 * 
	 * @param s Neue Information
	 */
	public void setInfo(String s) {
		info = s;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, xPos, yPos, null);
		Font font = new Font("Arial",Font.BOLD,16);
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(name, xPos + 40, yPos + 50);
        g.drawString(info, xPos + 40, yPos + 70);
        super.paint(g);
    }
}