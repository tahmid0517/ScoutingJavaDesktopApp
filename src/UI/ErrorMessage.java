package UI;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class ErrorMessage {
	static JDialog window;
	static Font font;
	public static void init(){
		font = new Font("Optimus",Font.BOLD,25);
	}
	public static void popUp(String s){
		window = new JDialog();
		window.setTitle("ERROR");
		JLabel label = new JLabel();
		label.setText(s);
		label.setFont(font);
		window.add(label);
		window.setVisible(true);
		window.pack();
	}
}
