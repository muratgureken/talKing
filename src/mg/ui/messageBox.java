package mg.ui;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class messageBox extends JFrame{
	public messageBox() {
		getContentPane().setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(25, 23, 386, 200);
		getContentPane().add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(25, 236, 300, 31);
		getContentPane().add(textPane_1);
		
		JButton btnGonder = new JButton("G\u00F6nder");
		btnGonder.setBounds(335, 236, 76, 31);
		getContentPane().add(btnGonder);
	}
}
