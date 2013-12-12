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
import javax.swing.border.LineBorder;

/** 
 * OhterPlayer. Zeigt die Informationen über die anderen Spieler an, also den Namen, 
 * ein Symbol für die verdeckte Hand und das Label für zusaetzliche Angaben.
 */
public class OtherPlayer extends JPanel{
	
	private static String IMAGEPATH = "Data/";

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
	public OtherPlayer() {
		name = "";
		info = "";
		this.setOpaque(true);
		try {
			image = ImageIO.read(new File(IMAGEPATH + "hand.png"));
		} catch (IOException e) {
			image = null;
		}
	}
	
//	/**
//	 * Setzt die Position des Objekts
//	 * 
//	 * @param x X-Position
//	 * @param y Y-Position
//	 */
//	public void setPosition(int x, int y) {
//		xPos = x;
//		yPos = y;
//	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public void setMyTurn(boolean b) {
		if (b) {
			this.setBorder(new LineBorder(Color.RED, 3));
		} else {
			this.setBorder(null);
		}
	}
	
	@Override
	public int getWidth() {
		return image.getWidth();
	}
	@Override
	public int getHeight() {
		return image.getHeight();
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		Font font = new Font("Arial",Font.BOLD,16);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(name, 35, 50);
        g.drawString(info, 35, 70);
    }
}