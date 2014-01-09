/**
 * 
 */
package Client.View;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import Client.ClientModel;
import Client.ViewNotification;
import Ruleset.OtherData;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


/** 
 * ScoreWindow. Dieses Fenster zeigt den momentanen Punktestand 
 * nach jeder Runde und den Gesamtpunktestand am Ende des Spieles an.
 */
public class ScoreWindow extends JFrame implements Observer{

	private JTextArea textArea;
	private JButton btnPlayAgain;
	private JButton btnLeave;
	private Language lang;
	
	public ScoreWindow() {
		lang = Language.English;
		setBounds(100, 100, 450, 290);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(10, 11, 414, 202);
		getContentPane().add(textArea);
		
		btnPlayAgain = new JButton("Play again");
		btnPlayAgain.setBounds(272, 224, 152, 23);
		getContentPane().add(btnPlayAgain);
		
		btnLeave = new JButton("Leave");
		btnLeave.setBounds(10, 224, 115, 23);
		getContentPane().add(btnLeave);
		
	}
	
	/**
	 * Fuegt dem Play-Again-Button einen ActionListener hinzu
	 * 
	 * @param l ein ActionListener
	 */
	public void addPlayAgainButtonListener(ActionListener l) {
		btnPlayAgain.addActionListener(l);
	}
	
	/**
	 * Fuegt dem Leave-Button einen ActionListener hinzu
	 * 
	 * @param l ein ActionListener
	 */
	public void addLeaveButtonListener(ActionListener l) {
		btnLeave.addActionListener(l);
	}
	
	/**
	 * Aendert die Sprache des Fensters
	 * 
	 * @param l Sprache in Form des Language-Enums
	 */
	public void setLanguage(Language l) {
		lang = l;
		updateLanguage();
	}
		
	private void updateLanguage() {
		switch (lang) {
		case German:
			this.setTitle("Sieger und Punkte");
			btnLeave.setText("Verlassen");
			btnPlayAgain.setText("Nochmal spielen");
			break;
		case English:
			this.setTitle("Winners and Score");
			btnLeave.setText("Leave");
			btnPlayAgain.setText("Play again");
			break;
		case Bavarian:
			this.setTitle("Wer gwunga hod und d'Aung vo am jedn");
			btnLeave.setText("Wegadgeh");
			btnPlayAgain.setText("Numoi spuin");
			break;
		}		
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: showScore, windowChangeForced, quitGame
	 */
	@Override
	public void update(Observable o, Object arg) {
		final ClientModel observed = (ClientModel) o;
		try {
			ViewNotification message = (ViewNotification) arg;
		switch (message) {
		case showScore:
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						String win = "";
						switch (lang) {
						case German:
							win = "Gewinner: ";
							break;
						case English:
							win = "Winner: ";
							break;
						case Bavarian:
							win = "Gwinna: ";
							break;
						default:
							break;
						}
						textArea.append(win + "\n");
						List<String> winners = observed.getWinner();
						for (int i = 0; i < winners.size(); i++) {
							textArea.append(winners.get(i) + "\n");
						}
						textArea.append("\n");
						OtherData myself = observed.getGameUpdate()
								.getOwnData();
						List<OtherData> players = observed.getGameUpdate()
								.getOtherPlayerData();
						textArea.append(myself.getOtherDataName() + ": "
								+ myself.getPoints() + "\n");
						for (OtherData p : players) {
							textArea.append(p.getOtherDataName() + ": "
									+ p.getPoints() + "\n");
						}
						setVisible(true);
					}
				});
			
			break;
		case windowChangeForced:
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					textArea.setText("");
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
}