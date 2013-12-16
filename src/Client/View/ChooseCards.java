/**
 * 
 */
package Client.View;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Client.ClientModel;
import Client.ViewNotification;
import Ruleset.Card;


/** 
 * ChooseCards. In diesem Fenster muss der Benutzer eine vorbestimmte Menge Karten auswaehlen.
 */
public class ChooseCards extends JFrame implements Observer{
	
	private JPanel contentPane;
	private OwnHand playerHand;
	private JTextArea rsMessageArea;
	private JButton btnOK;
	private List<Card> chosenCards;
	private int shownCardsCount;
	
	
	public ChooseCards() {
		shownCardsCount = 20;
		setBounds(100, 100, 1004, 330);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rsMessageArea = new JTextArea();
		rsMessageArea.setLineWrap(true);
		rsMessageArea.setEditable(false);
		rsMessageArea.setBounds(10, 11, 980, 115);
		contentPane.add(rsMessageArea);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(440, 253, 132, 40);
		
		contentPane.add(btnOK);
		
		playerHand = new OwnHand();
		playerHand.setBounds(10, 137, (ViewCard.WIDTH+1)*shownCardsCount, 105);
		playerHand.addCardMouseListener(new CardMouseListener());
		contentPane.add(playerHand);
		
		chosenCards = new LinkedList<Card>();
	}
	
	public void clearHand() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				chosenCards.clear();;
				playerHand.unclickAll();
			}
			
		});
	}
	
	/**
	 * Fuegt dem OK-Button einen ActionListener hinzu
	 * 
	 * @param l ein ActionListener
	 */
	public void addOKButtonListener(ActionListener l) {
		btnOK.addActionListener(l);
	}
	
	/**
	 * Gibt alle Karten zurueck, die vom Spieler ausgewahlt wurden.
	 * 
	 * @return Liste aller ausgewahlten Karten
	 */
	public List<Card> getChosenCards() {
		return chosenCards;
	}
	
	private void setMessageText(final String text) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				rsMessageArea.setText(text);
			}
			
		});
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openChooseCards, chooseCardsSuccessful
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case openChooseCards:
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					List<Card> handCards = observed.getGameUpdate().getOwnHand();
					playerHand.setHand(handCards);
					setMessageText(observed.getWindowText());
					repaint();
					setVisible(true);
				}
				
			});
			
			break;
		case windowChangeForced:
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					chosenCards.clear();
					playerHand.unclickAll();
					setVisible(false);
				}
				
			});
			break;
		case quitGame:
			this.dispose();
			break;
		default:
			break;
		}
		} catch (ClassCastException e) {
			//TODO
		}
		
	}
	
	
	class CardMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			//not needed
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			ViewCard vc = (ViewCard)arg0.getSource();
			if (vc.isClicked()) {
				chosenCards.remove(vc.getCard());
				vc.setClicked(false);
				repaint();
			} else {
				chosenCards.add(vc.getCard());
				vc.setClicked(true);
				repaint();
			}
			
		}
		
	}
}